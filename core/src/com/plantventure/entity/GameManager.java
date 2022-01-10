package com.plantventure.entity;

import com.plantventure.component.WorldInfo;

public class GameManager extends Entity {

    int width, height;
    public GameManager(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public void create() {
        this.addComponent(new WorldInfo(width, height));
    }
}
