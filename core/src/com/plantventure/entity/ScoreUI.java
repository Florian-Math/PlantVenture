package com.plantventure.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.plantventure.component.TextRenderer;

public class ScoreUI extends Entity {
    @Override
    public void create() {
        position.set(1, 1);
        this.addComponent(new TextRenderer("Score : 0", "fonts/Comic_Sans_MS_Bold.ttf", Align.right, -1));
    }
}
