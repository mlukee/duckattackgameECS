package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.duckattackecs.si.common.GameManager;
import com.duckattackecs.si.common.Mappers;
import com.duckattackecs.si.ecs.component.AppleComponent;
import com.duckattackecs.si.ecs.component.BoundsComponent;
import com.duckattackecs.si.ecs.component.DuckComponent;
import com.duckattackecs.si.ecs.component.GoldenAppleComponent;
import com.duckattackecs.si.ecs.component.WormComponent;
import com.duckattackecs.si.ecs.system.passive.SoundSystem;


public class CollisionSystem extends EntitySystem {

    private static final Family WORM_FAMILY = Family.all(WormComponent.class, BoundsComponent.class).get();
    private static final Family DUCK_FAMILY = Family.all(DuckComponent.class, BoundsComponent.class).get();
    private static final Family APPLE_FAMILY = Family.all(AppleComponent.class, BoundsComponent.class).get();
    private static final Family GOLDEN_APPLE_FAMILY = Family.all(GoldenAppleComponent.class, BoundsComponent.class).get();

    private SoundSystem soundSystem;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> worms = getEngine().getEntitiesFor(WORM_FAMILY);
        ImmutableArray<Entity> ducks = getEngine().getEntitiesFor(DUCK_FAMILY);
        ImmutableArray<Entity> apples = getEngine().getEntitiesFor(APPLE_FAMILY);
        ImmutableArray<Entity> goldenApples = getEngine().getEntitiesFor(GOLDEN_APPLE_FAMILY);

        for (Entity wormEntity : worms) {
            BoundsComponent wormBounds = Mappers.BOUNDS.get(wormEntity);

            // check collision with ducks
            for (Entity duckEntity : ducks) {
                DuckComponent duck = Mappers.DUCK.get(duckEntity);

                if (duck.hit) {
                    continue;
                }

                BoundsComponent duckBounds = Mappers.BOUNDS.get(duckEntity);

                if (Intersector.overlaps(wormBounds.rectangle, duckBounds.rectangle)) {
                    duck.hit = true;
                    GameManager.INSTANCE.damage();
                    soundSystem.hit();
                    getEngine().removeEntity(duckEntity);
                }
            }

            // check collision with apples
            for (Entity appleEntity : apples) {
                AppleComponent apple = Mappers.APPLE.get(appleEntity);

                if (apple.hit) {
                    continue;
                }

                BoundsComponent appleBounds = Mappers.BOUNDS.get(appleEntity);

                if (Intersector.overlaps(wormBounds.rectangle, appleBounds.rectangle)) {
                    apple.hit = true;
                    GameManager.INSTANCE.incResult();
                    soundSystem.collected();
                    getEngine().removeEntity(appleEntity);
                }
            }
            //check collision with golden apples
            for (Entity goldenAppleEntity : goldenApples) {
                GoldenAppleComponent goldenApple = Mappers.GOLDEN_APPLE.get(goldenAppleEntity);

                if (goldenApple.hit) {
                    continue;
                }

                BoundsComponent goldenAppleBounds = Mappers.BOUNDS.get(goldenAppleEntity);

                if (Intersector.overlaps(wormBounds.rectangle, goldenAppleBounds.rectangle)) {
                    goldenApple.hit = true;
                    GameManager.INSTANCE.incResult();
                    GameManager.INSTANCE.activatePowerUp();
                    soundSystem.eating();
                    getEngine().removeEntity(goldenAppleEntity);
                }
            }
        }
    }
}
