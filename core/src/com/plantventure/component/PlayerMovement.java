package com.plantventure.component;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;
import com.plantventure.entity.ScoreUI;

public class PlayerMovement extends Component{

    private RigidBody2D rb2d;
    private Animator animator;
    private TextureRenderer renderer;
    private WorldInfo worldInfo;
    private EndingComponent endingComponent;

    private int score;
    private TextRenderer scoreText;

    private boolean isDead = false;

    private Vector2 input = new Vector2();
    private boolean grounded = true;
    private RayCastCallback groundCast;

    @Override
    public void start() {
        rb2d = entity.getComponent(RigidBody2D.class);
        animator = entity.getComponent(Animator.class);
        renderer = entity.getComponent(TextureRenderer.class);

        worldInfo = entity.getScene().findComponent(WorldInfo.class);
        endingComponent = entity.getScene().findComponent(EndingComponent.class);

        scoreText = entity.getScene().getEntity(ScoreUI.class).getComponent(TextRenderer.class);

        groundCast = new RayCastCallback(){
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(((Entity)fixture.getBody().getUserData()).getTag() != Entity.Tag.GROUND) return 1; // should probably use Fixture FilterData

                //System.out.println(fixture.getBody().getUserData() + " P : " + point + " N : " + normal + " F : " + normal);
                grounded = true;
                return 0;
            }
        };

        animator.playAnimation("idle");
    }

    @Override
    public void update(float deltaTime) {
        if(isDead) return;

        input.set(0, 0);
        if(Gdx.app.getType() == Application.ApplicationType.Android) processAndroidInputs();
        else if(Gdx.app.getType() == Application.ApplicationType.Desktop) processKeyboardInputs();

        if(rb2d.getBody().getLinearVelocity().x > 0) renderer.setFlipX(false);
        if(rb2d.getBody().getLinearVelocity().x < 0) renderer.setFlipX(true);

        processAnimation();

        if(entity.position.x < -0.5f || entity.position.y < 0 || entity.position.x > worldInfo.getWidth() + 0.5f) endingComponent.showLostSequence();
    }

    private void processAndroidInputs(){
        if(!Gdx.input.isTouched()) return;

        if(Gdx.input.getX() > Gdx.graphics.getWidth()/2f) input.x = 1;
        if(Gdx.input.getX() < Gdx.graphics.getWidth()/2f) input.x = -1;

        if(grounded && Gdx.input.isTouched(1)) input.y = 1;
    }

    private void processKeyboardInputs(){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) input.x = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) input.x = -1;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) input.x = 0;

        if(grounded && Gdx.input.isKeyPressed(Input.Keys.UP)) input.y = 1;
    }

    public void processAnimation(){
        if(!grounded) {
            animator.setAnimation("jumping");
            animator.setSpeed(1);
        }
        else if(rb2d.getBody().getLinearVelocity().x != 0) {
            animator.setAnimation("running");
            animator.setSpeed(0.1f + rb2d.getBody().getLinearVelocity().len() / 3);
        }
        else {
            animator.setAnimation("idle");
            animator.setSpeed(1);
        }
    }

    @Override
    public void fixedUpdate() {
        if(isDead) return;
        grounded = false;

        Vector2 origin = new Vector2(entity.position.x-0.1f, entity.position.y-0.45f);
        Vector2 target = new Vector2(entity.position.x-0.1f, entity.position.y-0.55f);
        entity.getScene().getPhysicsManager().getWorld().rayCast(groundCast, origin, target);

        origin.set(entity.position.x+0.1f, entity.position.y-0.45f);
        target.set(entity.position.x+0.1f, entity.position.y-0.55f);
        entity.getScene().getPhysicsManager().getWorld().rayCast(groundCast, origin, target);

        if(grounded && input.x != 0) rb2d.getBody().applyForceToCenter(input.x, 0, true);
        if(grounded && input.y != 0) {
            rb2d.getBody().applyForceToCenter(0, input.y*40, true);
            input.y = 0;
            grounded = false;
        }
    }

    public void addPoints(int points){
        score += points;
        scoreText.setText("Score : " + score);
    }

    public void kill(){
        if(isDead) return;
        isDead = true;
        animator.playAnimation("death");
        animator.setSpeed(1);
    }

    public boolean isDead(){
        return isDead;
    }

    @Override
    public void onCollisionEnter(Entity e, WorldManifold manifold) {
        if(e.getTag() == Entity.Tag.GROUND){
            Vector2 normal = manifold.getNormal().cpy();
            normal.x *= rb2d.getBody().getLinearVelocity().x;
            normal.y *= rb2d.getBody().getLinearVelocity().y;

            if(normal.len() > 3){
                float pitch = 0.8f + (float)Math.random() * 0.4f;
                entity.getScene().getAssetManager().get("sounds/collision.ogg", Sound.class).play(0.7f, pitch, 0);
            }

        }
        else if(e.getTag() == Entity.Tag.ENEMY){
            Collider2D collider = e.getComponent(Collider2D.class);
            if(entity.position.y - 0.3f > e.position.y + collider.getHeight()/2){
                rb2d.getBody().applyForceToCenter(new Vector2(0, 50), true);
                e.getComponent(EnemyMovement.class).kill();
            }
            else if(e.getComponent(RigidBody2D.class).getBody().getLinearVelocity().x * e.position.cpy().sub(entity.position).x > 0){
                e.getComponent(EnemyMovement.class).kill();
                e.getComponent(RigidBody2D.class).getBody().setLinearVelocity(new Vector2(input.x*3, 3f));
            }
        }
    }
}
