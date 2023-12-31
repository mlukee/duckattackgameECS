package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;

import com.duckattackecs.si.config.GameConfig;
import com.duckattackecs.si.ecs.system.passive.EntityFactorySystem;

public class DuckSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public DuckSpawnSystem() {
        super(GameConfig.DUCK_SPAWN_TIME);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
        factory.createDuck();
    }
}
