package com.duckattackecs.si;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.duckattackecs.si.assets.AssetDescriptors;
import com.duckattackecs.si.common.GameManager;
import com.duckattackecs.si.config.GameConfig;
import com.duckattackecs.si.ecs.system.AppleSpawnSystem;
import com.duckattackecs.si.ecs.system.BoundsSystem;
import com.duckattackecs.si.ecs.system.CleanUpSystem;
import com.duckattackecs.si.ecs.system.CollisionSystem;
import com.duckattackecs.si.ecs.system.DuckSpawnSystem;
import com.duckattackecs.si.ecs.system.GoldenAppleSpawnSystem;
import com.duckattackecs.si.ecs.system.HudRenderSystem;
import com.duckattackecs.si.ecs.system.MovementSystem;
import com.duckattackecs.si.ecs.system.ParticleSystem;
import com.duckattackecs.si.ecs.system.PowerUpSystem;
import com.duckattackecs.si.ecs.system.RenderSystem;
import com.duckattackecs.si.ecs.system.WorldWrapSystem;
import com.duckattackecs.si.ecs.system.WormInputSystem;
import com.duckattackecs.si.ecs.system.debug.DebugCameraSystem;
import com.duckattackecs.si.ecs.system.debug.DebugInputSystem;
import com.duckattackecs.si.ecs.system.debug.DebugRenderSystem;
import com.duckattackecs.si.ecs.system.debug.GridRenderSystem;
import com.duckattackecs.si.ecs.system.debug.support.ViewportUtils;
import com.duckattackecs.si.ecs.system.passive.EntityFactorySystem;
import com.duckattackecs.si.ecs.system.passive.SoundSystem;
import com.duckattackecs.si.ecs.system.passive.StartUpSystem;

public class DuckAttackECS extends ApplicationAdapter {

	private static final Logger log = new Logger(DuckAttackECS.class.getSimpleName(), Logger.DEBUG);

	private AssetManager assetManager;
	private SpriteBatch batch;

	private OrthographicCamera camera;
	private Viewport viewport;
	private Viewport hudViewport;

	private ShapeRenderer renderer;
	private PooledEngine engine;
	private BitmapFont font;
	private boolean debug = false;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
		hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		renderer = new ShapeRenderer();

		assetManager = new AssetManager();
		assetManager.getLogger().setLevel(Logger.DEBUG);

		assetManager.load(AssetDescriptors.GAME_PLAY);
		assetManager.load(AssetDescriptors.SHOOT);
		assetManager.load(AssetDescriptors.HIT);
		assetManager.load(AssetDescriptors.QUACK);
		assetManager.load(AssetDescriptors.GAME_OVER);
		assetManager.load(AssetDescriptors.EATING);
		assetManager.load(AssetDescriptors.COLLECTED);
		assetManager.load(AssetDescriptors.GOLDEN_APPLE_PE);
//		assetManager.load(AssetDescriptors.BULLET_PE);
		assetManager.finishLoading();
		engine = new PooledEngine();

		font = new BitmapFont();
		//'passive' systems
		engine.addSystem(new EntityFactorySystem(assetManager));
		engine.addSystem(new SoundSystem(assetManager));
		engine.addSystem(new StartUpSystem()); //called only at the start, to generate first entities

		//'active' systems
		engine.addSystem(new PowerUpSystem());
		engine.addSystem(new WormInputSystem());
		engine.addSystem(new MovementSystem());
		engine.addSystem(new WorldWrapSystem());
		engine.addSystem(new BoundsSystem());
		engine.addSystem(new DuckSpawnSystem());
		engine.addSystem(new AppleSpawnSystem());
		engine.addSystem(new ParticleSystem());
		engine.addSystem(new GoldenAppleSpawnSystem());
		engine.addSystem(new CleanUpSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new RenderSystem(batch, viewport));
		engine.addSystem(new HudRenderSystem(batch, hudViewport, font));

		// debug systems
		if (debug) {
			ViewportUtils.DEFAULT_CELL_SIZE = 32;
			engine.addSystem(new GridRenderSystem(viewport, renderer));
			engine.addSystem(new DebugRenderSystem(viewport, renderer));
			engine.addSystem(new DebugCameraSystem(
					GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2,
					camera
			));
			engine.addSystem(new DebugInputSystem());   // show/hide grid on 'F5'
		}

		GameManager.INSTANCE.resetResult();
		logAllSystemsInEngine();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height,true);
		hudViewport.update(width, height,true);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Color.BLACK);

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

		if (GameManager.INSTANCE.isGameOver()) {
			engine.update(0);
		} else {
			engine.update(Gdx.graphics.getDeltaTime());
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
		renderer.dispose();
		engine.removeAllEntities();
	}

	public void logAllSystemsInEngine() {
		for (EntitySystem system : engine.getSystems()) {
			log.debug(system.getClass().getSimpleName());
		}
	}
}
