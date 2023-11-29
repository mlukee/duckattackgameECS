package com.duckattackecs.si.common;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private int result;
    private int health;

    private boolean doublePoints = false;

    private GameManager() {
    }

    public int getResult() {
        return result;
    }

    public void resetResult() {
        result = 0;
        health = 100;
    }

    public void incResult() {
        if(doublePoints)
            result+=2;
        else
            result++;
    }

    public boolean isGameOver() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    public void damage() {
        health-=10;
    }

    public boolean isDoublePoints() {
        return doublePoints;
    }
    public void activatePowerUp() {
        doublePoints = true;
    }
    public void deactivatePowerUp() {
        doublePoints = false;
    }
}
