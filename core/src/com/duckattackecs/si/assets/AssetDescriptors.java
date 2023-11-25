package com.duckattackecs.si.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Sound> SHOOT = new AssetDescriptor<>(AssetPaths.SHOOT, Sound.class);
    public static final AssetDescriptor<Sound> HIT = new AssetDescriptor<>(AssetPaths.HIT, Sound.class);
    public static final AssetDescriptor<Sound> QUACK = new AssetDescriptor<>(AssetPaths.QUACK, Sound.class);
    public static final AssetDescriptor<Sound> GAME_OVER = new AssetDescriptor<>(AssetPaths.GAME_OVER, Sound.class);
    public static final AssetDescriptor<Sound> EATING = new AssetDescriptor<>(AssetPaths.POWER_UP, Sound.class);
    public static final AssetDescriptor<Sound> COLLECTED = new AssetDescriptor<>(AssetPaths.COLLECTED, Sound.class);
    public static final AssetDescriptor<ParticleEffect> GOLDEN_APPLE_PE = new AssetDescriptor<>(AssetPaths.GOLDEN_APPLE_PE, ParticleEffect.class);
    public static final AssetDescriptor<ParticleEffect> BULLET_PE = new AssetDescriptor<>(AssetPaths.BULLET_PE, ParticleEffect.class);

    private AssetDescriptors() {
    }
}
