package com.plantventure.component;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.plantventure.game.Scene;

public class RigidBody2D extends Component {

    private Body body;

    private BodyDef.BodyType bodyType = BodyDef.BodyType.StaticBody;
    private boolean fixedRotation = false;

    private final Vector2 lastPosition = new Vector2();
    private float lerpVal = 0;

    @Override
    public void start() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = this.bodyType;
        bodyDef.fixedRotation = this.fixedRotation;

        bodyDef.position.set(entity.position.x, entity.position.y);

        body = entity.getScene().getPhysicsManager().getWorld().createBody(bodyDef);
        body.setUserData(entity);
    }

    @Override
    public void update(float deltaTime) {
        if(bodyType == BodyDef.BodyType.DynamicBody){
            lerpVal += deltaTime / Scene.FIXED_TIMESTEP;
            //System.out.println(lerpVal);
            entity.position.set(MathUtils.lerp(lastPosition.x, body.getPosition().x, lerpVal), MathUtils.lerp(lastPosition.y, body.getPosition().y, lerpVal));
        }
    }

    @Override
    public void fixedUpdate() {
        if(bodyType == BodyDef.BodyType.DynamicBody){
            lerpVal--;
            //System.out.println("Fix " + lerpVal);
            lastPosition.set(body.getPosition().x, body.getPosition().y);
        }
    }

    public Body getBody(){
        return body;
    }

    public void setBodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    @Override
    public void dispose() {
        entity.getScene().getPhysicsManager().destroyBody(body);
    }
}
