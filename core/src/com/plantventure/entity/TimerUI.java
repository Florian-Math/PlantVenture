package com.plantventure.entity;

import com.badlogic.gdx.utils.Align;
import com.plantventure.component.TextRenderer;
import com.plantventure.component.TimerComponent;

public class TimerUI extends Entity {

    int time;

    public TimerUI(int time){
        this.time = time;
    }

    @Override
    public void create() {
        this.position.set(0.5f, 1);
        this.addComponent(new TextRenderer("0", "fonts/Comic_Sans_MS_Bold.ttf", Align.center, -1));
        this.addComponent(new TimerComponent(time));
    }
}
