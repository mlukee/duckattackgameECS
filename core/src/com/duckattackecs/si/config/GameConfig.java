package com.duckattackecs.si.config;

public class GameConfig {

    public static final float WORM_WIDTH = 48f;
    public static final float WORM_HEIGHT = 48f;
    public static final float MAX_WORM_X_SPEED = 90f * 4;

    public static final float APPLE_WIDTH = 32f;
    public static final float APPLE_HEIGHT = 32f;
    public static final float APPLE_SPAWN_TIME = 1f;
    public static final float APPLE_SPEED_X_MIN = 2f;

    public static final float GOLDEN_APPLE_WIDTH = 32f;
    public static final float GOLDEN_APPLE_HEIGHT = 32f;
    public static final float GOLDEN_APPLE_SPAWN_TIME = 30f; // 30 seconds
    public static final float GOLDEN_APPLE_DURATION = 10f; // 10 seconds
    public static final float GOLDEN_APPLE_SPEED_X_MIN = 2f;

    public static final float DUCK_SIZE = 48f;
    public static final float DUCK_SPAWN_TIME = 2f;

    public static float WIDTH = 500f;
    public static float HEIGHT = 800f;

    private GameConfig() {
    }
}
