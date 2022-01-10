package com.plantventure.entity;

import com.badlogic.gdx.utils.Align;
import com.plantventure.component.EndingComponent;
import com.plantventure.component.TextRenderer;

public class EndingText extends Entity {
    @Override
    public void create() {
        this.position.set(0.5f, 0.5f);
        this.addComponent(new TextRenderer("", "fonts/Comic_Sans_MS_Bold.ttf", Align.center, 0));
        this.addComponent(new EndingComponent());
    }
}
