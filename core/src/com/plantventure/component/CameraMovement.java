package com.plantventure.component;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.plantventure.entity.Entity;
import com.plantventure.entity.Player;

public class CameraMovement extends Component {

    private Camera camera;
    private Entity player;

    private Vector2 mapSize = new Vector2();
    private Vector2 cameraSize = new Vector2(16, 12);

    @Override
    public void start() {
        camera = entity.getScene().getViewport().getCamera();
        player = entity.getScene().getEntity(Player.class);

        WorldInfo info = entity.getScene().findComponent(WorldInfo.class);
        mapSize.set(info.getWidth(), info.getHeight());

        update(0);
    }

    @Override
    public void update(float deltaTime) {
        if(player != null) moveCamera(deltaTime);
        else player = entity.getScene().getEntity(Player.class);
    }

    float[] vx = new float[]{0};
    float[] vy = new float[]{0};

    public void moveCamera(float deltaTime){
        float x = smoothDamp(entity.position.x, player.position.x, vx, 0.15f, Float.MAX_VALUE, deltaTime);
        float y = smoothDamp(entity.position.y, player.position.y, vy, 0.15f, Float.MAX_VALUE, deltaTime);
        entity.position.set(x, y);

        if(entity.position.x - cameraSize.x/2 < 0) entity.position.x = cameraSize.x / 2;
        if(entity.position.x + cameraSize.x/2 > mapSize.x) entity.position.x = mapSize.x - cameraSize.x / 2;

        if(entity.position.y - cameraSize.y/2 < 0) entity.position.y = cameraSize.y / 2;
        if(entity.position.y + cameraSize.y/2 > mapSize.y) entity.position.y = mapSize.y - cameraSize.y / 2;

        camera.position.set(entity.position.x, entity.position.y, 0);
        camera.update();
    }

    public float smoothDamp(float current, float target, float[] currentVelocity, float smoothTime, float maxSpeed, float deltaTime){
        smoothTime = Math.max(0.0001f, smoothTime);
        float omega = 2.0f / smoothTime;
        float x = omega * deltaTime;
        float exp = 1.0f / (1.0f + x + 0.48f * x * x + 0.235f * x * x * x);
        float deltaX = current - target;
        float maxDelta = maxSpeed * smoothTime;

        // ensure we do not exceed our max speed
        deltaX = MathUtils.clamp(deltaX, -maxDelta, maxDelta);
        float temp = (currentVelocity[0] + omega * deltaX) * deltaTime;
        float result = current - deltaX + (deltaX + temp) * exp;
        currentVelocity[0] = (currentVelocity[0] - omega * temp) * exp;

        // ensure that we do not overshoot our target
        if (target - current > 0.0f == result > target) {
            result = target;
            currentVelocity[0] = 0.0f;
        }
        return result;
    }
}
