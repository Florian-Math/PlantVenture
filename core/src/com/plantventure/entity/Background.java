package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.plantventure.component.BackgroundParallax;
import com.plantventure.component.TextureRenderer;

public class Background extends Entity {

    private String imagePath;

    @Override
    public void create() {
        scale.set(20, 20);
        this.addComponent(new BackgroundParallax(1));
        this.addComponent(new TextureRenderer(scene.getAssetManager().get(imagePath, Texture.class)));
    }

   public void setBackgroundImage(String imagePath){
        this.imagePath = imagePath;
    }
}
