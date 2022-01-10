package com.plantventure.component;

import com.badlogic.gdx.audio.Sound;
import com.plantventure.game.Game;
import com.plantventure.utilities.Operation;

public class MainMenuComponent extends Component {

    private long sound;
    private float timer;
    private Operation operation;

    @Override
    public void start() {
        sound = entity.getScene().getAssetManager().get("sounds/win.ogg", Sound.class).play();
        timer = 3;
        operation = new Operation() {
            @Override
            public void execute() {
                Game.getInstance().loadScene(1);
            }
        };
    }

    @Override
    public void update(float deltaTime) {
        if(timer < 0) return;

        if (sound == -1) {
            sound = entity.getScene().getAssetManager().get("sounds/win.ogg", Sound.class).play();
            timer = 3;
        }
        else sound = -2;

        timer -= deltaTime;
        if(timer < 0){
            operation.execute();
        }
    }
}
