package com.plantventure.component;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;

public abstract class Component {

    protected Entity entity;

    public void start(){}
    public void update(float deltaTime){}
    public void fixedUpdate(){}
    public void draw(Batch batch){}
    public void dispose(){}

    public void onCollisionEnter(Entity e, WorldManifold manifold){}
    public void onCollisionExit(Entity e, WorldManifold manifold) {}

    public void setEntity(Entity entity){
        this.entity = entity;
    }
}
