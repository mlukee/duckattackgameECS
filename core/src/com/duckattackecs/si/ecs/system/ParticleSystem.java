package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.duckattackecs.si.common.Mappers;
import com.duckattackecs.si.ecs.component.ParticleComponent;
import com.duckattackecs.si.ecs.component.PositionComponent;

public class ParticleSystem extends IteratingSystem {
    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            ParticleComponent.class
    ).get();

    public ParticleSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleComponent particleComponent = Mappers.PARTICLE.get(entity);
        PositionComponent positionComponent = Mappers.POSITION.get(entity);

        ParticleEffect particleEffect = particleComponent.particleEffect;
        particleEffect.setPosition(positionComponent.x, positionComponent.y);
        particleEffect.update(deltaTime); // Update the particle effect
    }
}
