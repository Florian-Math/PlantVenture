package com.plantventure.component;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;

public class WaterComponent extends Component {

    private EndingComponent endingComponent;

    @Override
    public void start() {
        endingComponent = entity.getScene().findComponent(EndingComponent.class);
    }

    @Override
    public void onCollisionEnter(Entity e, WorldManifold manifold) {
        if(e.getTag() == Entity.Tag.PLAYER && !e.getComponent(PlayerMovement.class).isDead()){
            e.getComponent(PlayerMovement.class).kill();
            entity.getScene().getAssetManager().get("sounds/plouf.ogg", Sound.class).play();
            endingComponent.showDelayedLostSequence(0.3f);
        }
    }
}
