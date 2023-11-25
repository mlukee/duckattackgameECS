package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.duckattackecs.si.common.Mappers;
import com.duckattackecs.si.ecs.component.CleanUpComponent;
import com.duckattackecs.si.ecs.component.DimensionComponent;
import com.duckattackecs.si.ecs.component.PositionComponent;


public class CleanUpSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            CleanUpComponent.class,
            PositionComponent.class,
            DimensionComponent.class
    ).get();

    public CleanUpSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);
        DimensionComponent dimension = Mappers.DIMENSION.get(entity);

        if (position.y < -dimension.height) {
            getEngine().removeEntity(entity);
        }
    }
}
