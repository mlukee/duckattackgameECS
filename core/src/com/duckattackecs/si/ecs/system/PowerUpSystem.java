package com.duckattackecs.si.ecs.system;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.duckattackecs.si.common.GameManager;
import com.duckattackecs.si.config.GameConfig;

public class PowerUpSystem extends EntitySystem {
    private float powerUpTime = 0;
    private float powerUpDuration = GameConfig.GOLDEN_APPLE_DURATION; // Duration of the power-up
    private boolean powerUpActive = false;

    public void activatePowerUp() {
        powerUpActive = true;
        powerUpTime = 0;
    }

    @Override
    public void update(float deltaTime) {
        if (powerUpActive) {
            powerUpTime += deltaTime;
            if (powerUpTime >= powerUpDuration) {
                deactivatePowerUp();
                GameManager.INSTANCE.deactivateDoublePoints();
            }
        }
    }

    private void deactivatePowerUp() {
        powerUpActive = false;
    }

    public boolean isPowerUpActive() {
        return powerUpActive;
    }
}
