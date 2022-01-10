package com.plantventure.component;

public class BackgroundParallax extends Component {

    CameraMovement camera;
    int paralaxValue;

    public BackgroundParallax(int paralaxValue) {
        this.paralaxValue = paralaxValue;
    }

    @Override
    public void start() {
        camera = entity.getScene().findComponent(CameraMovement.class);
    }

    @Override
    public void update(float deltaTime) {
        entity.position.set(camera.entity.position.x, camera.entity.position.y + 1);
    }
}
