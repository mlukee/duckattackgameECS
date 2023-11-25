package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.duckattackecs.si.common.Mappers;
import com.duckattackecs.si.ecs.component.MovementComponentXYR;
import com.duckattackecs.si.ecs.component.WormComponent;
import com.duckattackecs.si.config.GameConfig;


public class WormInputSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            WormComponent.class,
            MovementComponentXYR.class
    ).get();

    public WormInputSystem() {
        super(FAMILY);
    }

    // we don't need to override the update Iterating system method because there is no batch begin/end

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponentXYR movement = Mappers.MOVEMENT.get(entity);
        movement.xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.xSpeed = GameConfig.MAX_WORM_X_SPEED * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.xSpeed = -GameConfig.MAX_WORM_X_SPEED * deltaTime;
        }

    }
}
