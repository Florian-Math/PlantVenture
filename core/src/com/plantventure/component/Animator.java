package com.plantventure.component;

import com.badlogic.gdx.utils.ObjectMap;
import com.plantventure.utilities.Animation;

public class Animator extends Component {

    private TextureRenderer renderer;

    private final ObjectMap<String, Animation> animations = new ObjectMap<>();
    private Animation currentAnimation;
    private String currentAnimationName = "";

    private float speed = 1;

    private boolean stopped;
    private float stateTime = 0f;

    @Override
    public void start() {
        renderer = entity.getComponent(TextureRenderer.class);
    }

    public Animator(){

    }

    public Animator(Animation animation){
        playAnimation(animation);
    }

    @Override
    public void update(float deltaTime) {
        if(!stopped && currentAnimation != null){
            stateTime += deltaTime*speed;
            renderer.setTexture(currentAnimation.getKeyFrame(stateTime));
        }
    }

    public void registerAnimation(String animationName, Animation animation){
        animations.put(animationName, animation);
    }

    public void playAnimation(){
        stopped = false;
        stateTime = 0;
    }

    public void playAnimation(Animation animation){
        currentAnimation = animation;
        playAnimation();
    }

    public void playAnimation(String animation){
        setAnimation(animation);
        playAnimation();
    }

    public void setAnimation(String animation){
        if(currentAnimationName.equals(animation)) return;
        currentAnimationName = animation;
        currentAnimation = animations.get(animation);
        stateTime = 0;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public String getCurrentAnimationName(){
        return currentAnimationName;
    }

    public void stopAnimation(){
        stopped = true;
    }
}
