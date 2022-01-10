package com.plantventure.component;

import com.badlogic.gdx.audio.Sound;
import com.plantventure.game.Game;
import com.plantventure.utilities.Operation;

public class EndingComponent extends Component {

    private final Operation winOperation = new Operation() {
        @Override
        public void execute() {
            int newSceneIndex = Game.getInstance().getCurrentSceneIndex() + 1;
            if(newSceneIndex > Game.getInstance().getMaxSceneIndex()) newSceneIndex = 1;

            Game.getInstance().loadScene(newSceneIndex);
        }
    };

    private final Operation loseOperation = new Operation() {
        @Override
        public void execute() {
            startTimer(resetSceneOperation, 2);
            text.setText("Vous avez perdu !");
            entity.getScene().getAssetManager().get("sounds/lose.ogg", Sound.class).play(0.2f, 1, 0);
        }
    };

    private final Operation resetSceneOperation = new Operation() {
        @Override
        public void execute() {
            Game.getInstance().loadScene(Game.getInstance().getCurrentSceneIndex());
        }
    };

    private boolean isStarted = false;
    private float timer;
    private Operation operation;

    private TextRenderer text;

    @Override
    public void start() {
        text = entity.getComponent(TextRenderer.class);
    }

    @Override
    public void update(float deltaTime) {
        if(isStarted){
            timer -= deltaTime;
            if(timer < 0){
                isStarted = false;
                operation.execute();
            }
        }
    }

    private void startTimer(Operation operation, float time){
        this.operation = operation;
        isStarted = true;
        timer = time;
    }

    public void showWinSequence(){
        if(isStarted) return;
        text.setText("Vous avez gagné !");
        entity.getScene().getAssetManager().get("sounds/win.ogg", Sound.class).play();
        startTimer(winOperation, 2);
    }

    public void showLostSequence(){
        if(isStarted) return;
        text.setText("Vous avez perdu !");
        entity.getScene().getAssetManager().get("sounds/lose.ogg", Sound.class).play(0.2f, 1, 0);
        startTimer(resetSceneOperation, 2);
    }

    public void showDelayedLostSequence(float delay){
        if(isStarted) return;
        startTimer(loseOperation, delay);
    }
}
