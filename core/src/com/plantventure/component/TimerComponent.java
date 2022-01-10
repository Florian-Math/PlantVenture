package com.plantventure.component;

public class TimerComponent extends Component {

    EndingComponent endingComponent;
    TextRenderer timerText;

    boolean started = false;
    float timeLeft;

    public TimerComponent(int time){
        timeLeft = time;
    }

    @Override
    public void start() {
        timerText = entity.getComponent(TextRenderer.class);
        endingComponent = entity.getScene().findComponent(EndingComponent.class);

        timerText.setText("" + timeLeft);
        startTimer();
    }

    @Override
    public void update(float deltaTime) {
        if(started){
            timeLeft -= deltaTime;

            if(timeLeft < 0){
                started = false;
                timeLeft = 0;

                endingComponent.showLostSequence();
            }
            timerText.setText("" + (int)Math.ceil(timeLeft));
        }
    }

    public void startTimer(){
        started = true;
    }
}
