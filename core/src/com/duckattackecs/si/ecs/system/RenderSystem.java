package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.duckattackecs.si.common.GameManager;
import com.duckattackecs.si.common.Mappers;
import com.duckattackecs.si.config.GameConfig;
import com.duckattackecs.si.ecs.component.DimensionComponent;
import com.duckattackecs.si.ecs.component.ParticleComponent;
import com.duckattackecs.si.ecs.component.PositionComponent;
import com.duckattackecs.si.ecs.component.TextureComponent;
import com.duckattackecs.si.ecs.component.ZOrderComparator;
import com.duckattackecs.si.ecs.component.ZOrderComponent;


public class RenderSystem extends SortedIteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            DimensionComponent.class,
            TextureComponent.class,
            ZOrderComponent.class
    ).get();

    private final SpriteBatch batch;
    private final Viewport viewport;

    public RenderSystem(SpriteBatch batch, Viewport viewport) {
        super(FAMILY, ZOrderComparator.INSTANCE);
        this.batch = batch;
        this.viewport = viewport;
    }

    @Override
    public void update(float deltaTime) {   // override to avoid calling batch.begin/end for each entity
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        super.update(deltaTime);    // calls processEntity method, which is wrapped with begin/end

        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);
        TextureComponent texture = Mappers.TEXTURE.get(entity);
        ParticleComponent pc = Mappers.PARTICLE.get(entity);

        if (pc != null) {
            if (pc.particleEffect.isComplete()) {
                pc.particleEffect.start();
            }

            pc.particleEffect.update(deltaTime);
            pc.particleEffect.draw(batch, deltaTime);
        }
        batch.draw(texture.region,
                position.x, position.y,
                dimension.width / 2, dimension.height / 2,
                dimension.width, dimension.height,
                1, 1,0);
    }
}
