package com.plantventure.entity;

import com.plantventure.component.RigidBody2D;

import java.util.ArrayList;

public class TileMap extends Entity {

    private ArrayList<Entity> entities = new ArrayList<>();

    @Override
    public void create() {
        this.tag = Tag.GROUND;
        this.addComponent(new RigidBody2D());
        this.addComponent(new com.plantventure.component.TileMap(entities));
    }

    public void addEntity(Entity e){
        entities.add(e);
    }
}
