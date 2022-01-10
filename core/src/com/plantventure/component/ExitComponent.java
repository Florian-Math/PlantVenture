package com.plantventure.component;

import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;

public class ExitComponent extends Component {

    private WorldInfo worldInfo;
    private EndingComponent endingComponent;
    private TextureRenderer renderer;

    @Override
    public void start() {
        worldInfo = entity.getScene().findComponent(WorldInfo.class);
        endingComponent = entity.getScene().findComponent(EndingComponent.class);
        renderer = entity.getComponent(TextureRenderer.class);
    }

    @Override
    public void onCollisionExit(Entity e, WorldManifold manifold) {
        if(renderer.getFlipX()) return;

        if(e.getTag() == Entity.Tag.PLAYER){
            if(e.position.x < 0 || e.position.x > worldInfo.getWidth()) endingComponent.showWinSequence();
        }
    }
}
