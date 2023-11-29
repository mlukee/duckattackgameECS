package com.duckattackecs.si.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import com.duckattackecs.si.assets.AssetDescriptors;

public class SoundSystem extends EntitySystem {

    private final AssetManager assetManager;

    private Sound shootSound;
    private Sound hitSound;
    private Sound quackSound;
    private Sound gameOverSound;
    private Sound eatingSound;
    private Sound collectedSound;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); // passive system
        init();
    }

    private void init() {
        shootSound = assetManager.get(AssetDescriptors.SHOOT);
        hitSound = assetManager.get(AssetDescriptors.HIT);
        quackSound = assetManager.get(AssetDescriptors.QUACK);
        gameOverSound = assetManager.get(AssetDescriptors.GAME_OVER);
        eatingSound = assetManager.get(AssetDescriptors.EATING);
        collectedSound = assetManager.get(AssetDescriptors.COLLECTED);
    }

    public void shoot() {
        shootSound.play();
    }

    public void hit() {
        hitSound.play();
    }

    public void quack() {
        quackSound.play();
    }

    public void gameOver() {
        gameOverSound.play();
    }

    public void eating() {
        eatingSound.play();
    }

    public void collected() {
        collectedSound.play();
    }
}
