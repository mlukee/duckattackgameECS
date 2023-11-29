package com.duckattackecs.si.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import com.duckattackecs.si.assets.RegionNames;
import com.duckattackecs.si.assets.AssetDescriptors;
import com.duckattackecs.si.config.GameConfig;
import com.duckattackecs.si.ecs.component.AppleComponent;
import com.duckattackecs.si.ecs.component.BoundsComponent;
import com.duckattackecs.si.ecs.component.BulletComponent;
import com.duckattackecs.si.ecs.component.CleanUpComponent;
import com.duckattackecs.si.ecs.component.DimensionComponent;
import com.duckattackecs.si.ecs.component.DuckComponent;
import com.duckattackecs.si.ecs.component.GoldenAppleComponent;
import com.duckattackecs.si.ecs.component.MovementComponentXYR;
import com.duckattackecs.si.ecs.component.ParticleComponent;
import com.duckattackecs.si.ecs.component.PositionComponent;
import com.duckattackecs.si.ecs.component.WorldWrapComponent;
import com.duckattackecs.si.ecs.component.WormComponent;
import com.duckattackecs.si.ecs.component.TextureComponent;
import com.duckattackecs.si.ecs.component.ZOrderComponent;

public class EntityFactorySystem extends EntitySystem {

    private static final int BACKGROUND_Z_ORDER = 0;
    private static final int WORM_Z_ORDER = 1;
    private static final int GOLDEN_APPLE_Z_ORDER = 2;
    private static final int DUCK_Z_ORDER = 3;
    private static final int APPLE_Z_ORDER = 4;
    private static final int GOLDEN_APPLE_PE_Z_ORDER = 5;
    private static final int BULLET_Z_ORDER = 5;

    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false);   // passive system
        init();
    }

    private void init() {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createWorm() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WIDTH / 2f - GameConfig.WORM_WIDTH / 2f;
        position.y = 20;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WORM_WIDTH;
        dimension.height = GameConfig.WORM_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponentXYR movement = engine.createComponent(MovementComponentXYR.class);

        WormComponent worm = engine.createComponent(WormComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.WORM);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = WORM_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(worm);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createBackground(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WIDTH;
        dimension.height = GameConfig.HEIGHT;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = BACKGROUND_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createGoldenApple(){
        PositionComponent position = engine.createComponent(PositionComponent.class);

        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.GOLDEN_APPLE_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = GameConfig.GOLDEN_APPLE_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.GOLDEN_APPLE_SPEED_X_MIN * MathUtils.random(1f, 2f);

        float randFactor = MathUtils.random(1f, 2f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.GOLDEN_APPLE_WIDTH * randFactor;
        dimension.height = GameConfig.GOLDEN_APPLE_HEIGHT * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        GoldenAppleComponent goldenApple = engine.createComponent(GoldenAppleComponent.class);

        ParticleComponent particle = engine.createComponent(ParticleComponent.class);
        particle.particleEffect = new ParticleEffect(assetManager.get(AssetDescriptors.GOLDEN_APPLE_PE));
        particle.particleEffect.start();

        ZOrderComponent particleZOrder = engine.createComponent(ZOrderComponent.class);
        particleZOrder.z = GOLDEN_APPLE_PE_Z_ORDER;

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.GOLDEN_APPLE);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = GOLDEN_APPLE_Z_ORDER;

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(goldenApple);
        entity.add(particle);
        entity.add(particleZOrder);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createApple() {
        PositionComponent position = engine.createComponent(PositionComponent.class);

        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.APPLE_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(1f, 2f);

        float randFactor = MathUtils.random(1f, 2f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.APPLE_WIDTH * randFactor;
        dimension.height = GameConfig.APPLE_HEIGHT * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        AppleComponent appleComponent = engine.createComponent(AppleComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.APPLE);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = APPLE_Z_ORDER;

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(appleComponent);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createDuck() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.DUCK_SIZE);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.APPLE_SPEED_X_MIN * MathUtils.random(1f, 2f);

        DuckComponent duck = engine.createComponent(DuckComponent.class);

        float randFactor = MathUtils.random(1f, 2f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.DUCK_SIZE * randFactor;
        dimension.height = GameConfig.DUCK_SIZE * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.DUCK);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = DUCK_Z_ORDER;

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(duck);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }
}
