package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.plantventure.component.ImageRenderer;
import com.plantventure.component.MainMenuComponent;

public class MainMenu extends Entity {
    @Override
    public void create() {
        this.addComponent(new ImageRenderer(scene.getAssetManager().get("images/Intro.png", Texture.class)));
        this.addComponent(new MainMenuComponent());
    }
}
