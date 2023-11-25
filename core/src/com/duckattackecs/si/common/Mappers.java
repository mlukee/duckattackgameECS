package com.duckattackecs.si.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.duckattackecs.si.ecs.component.AppleComponent;
import com.duckattackecs.si.ecs.component.BoundsComponent;
import com.duckattackecs.si.ecs.component.BulletComponent;
import com.duckattackecs.si.ecs.component.DimensionComponent;
import com.duckattackecs.si.ecs.component.DuckComponent;
import com.duckattackecs.si.ecs.component.MovementComponentXYR;
import com.duckattackecs.si.ecs.component.ParticleComponent;
import com.duckattackecs.si.ecs.component.PositionComponent;
import com.duckattackecs.si.ecs.component.WormComponent;
import com.duckattackecs.si.ecs.component.TextureComponent;
import com.duckattackecs.si.ecs.component.ZOrderComponent;

//TODO Explain how Mappers work (see ComponentMapper and Entity implementation)
public final class Mappers {

    public static final ComponentMapper<AppleComponent> APPLE =
            ComponentMapper.getFor(AppleComponent.class);

    public static final ComponentMapper<BulletComponent> BULLET =
            ComponentMapper.getFor(BulletComponent.class);

    public static final ComponentMapper<DuckComponent> DUCK =
            ComponentMapper.getFor(DuckComponent.class);

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<MovementComponentXYR> MOVEMENT =
            ComponentMapper.getFor(MovementComponentXYR.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<WormComponent> WORM =
            ComponentMapper.getFor(WormComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    public static final ComponentMapper<ParticleComponent> PARTICLE =
            ComponentMapper.getFor(ParticleComponent.class);

    private Mappers() {
    }
}
