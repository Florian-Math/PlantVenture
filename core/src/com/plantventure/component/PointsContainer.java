package com.plantventure.component;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;

public class PointsContainer extends Component {

    private int points;

    public PointsContainer(int points){
        this.points = points;
    }

    public int getPoints(){
        return points;
    }

    @Override
    public void onCollisionEnter(Entity e, WorldManifold manifold) {
        if (e.getTag() == Entity.Tag.PLAYER){
            e.getComponent(PlayerMovement.class).addPoints(points);
            float pitch = 0.9f + (float)Math.random() * 0.2f;
            if(points != 1) pitch += 0.2f;
            entity.getScene().getAssetManager().get("sounds/gem.ogg", Sound.class).play(1, pitch, 0);
            entity.destroy();
        }
    }
}
