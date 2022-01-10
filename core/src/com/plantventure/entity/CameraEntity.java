package com.plantventure.entity;

import com.plantventure.component.CameraMovement;

public class CameraEntity extends Entity {

    @Override
    public void create() {
        this.addComponent(new CameraMovement());
    }
}
