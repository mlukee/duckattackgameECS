package com.duckattackecs.si.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class MovementComponentXYR implements Component, Pool.Poolable {

    public float xSpeed;  // dX
    public float ySpeed;  // dY

    @Override
    public void reset() {
        xSpeed = 0;
        ySpeed = 0;
    }
}
