package com.plantventure.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.plantventure.entity.Entity;

public class EnemyMovement extends Component {

    // --- COMPONENTS
    private TextureRenderer renderer;
    private Animator animator;
    private RigidBody2D rb2d;
    private Collider2D collider;

    // --- OTHER COMPONENTS
    private EndingComponent endingComponent;

    // --- CAST
    private RayCastCallback groundCast;
    private boolean cast;

    // --- MOVEMENT
    private int directionX = 1;
    private Vector2 castOrigin = new Vector2();
    private Vector2 castTarget = new Vector2();

    boolean dead = false;

    @Override
    public void start() {
        renderer = entity.getComponent(TextureRenderer.class);
        animator = entity.getComponent(Animator.class);
        rb2d = entity.getComponent(RigidBody2D.class);
        collider = entity.getComponent(Collider2D.class);

        endingComponent = entity.getScene().findComponent(EndingComponent.class);

        groundCast = new RayCastCallback(){
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(((Entity)fixture.getBody().getUserData()).getTag() != Entity.Tag.GROUND) return 1; // should probably use Fixture FilterData

                cast = true;
                return 0;
            }
        };

        animator.playAnimation("running");
    }

    @Override
    public void fixedUpdate() {
        if(dead) return;
        renderer.setFlipX(rb2d.getBody().getLinearVelocity().x < 0);

        cast = false;
        castOrigin.set(entity.position.x + directionX*collider.getWidth()/2, entity.position.y-collider.getHeight()/2);
        castTarget.set(entity.position.x + directionX*collider.getWidth()/2, entity.position.y-collider.getHeight()/2 - 0.1f);

        entity.getScene().getPhysicsManager().getWorld().rayCast(groundCast, castOrigin, castTarget);

        rb2d.getBody().setLinearVelocity(directionX, rb2d.getBody().getLinearVelocity().y);
        if(!cast) {
            directionX *= -1;
        }
    }

    public void kill(){
        if (dead) return;
        dead = true;
        animator.playAnimation("death");
        Filter filter = new Filter();
        filter.maskBits = 0x0001; // 0001
        collider.getFixture().setFilterData(filter);
    }

    @Override
    public void onCollisionEnter(Entity e, WorldManifold manifold) {
        if(e.getTag() == Entity.Tag.PLAYER){
            Vector2 dir = e.position.cpy().sub(entity.position);
            if(dir.x*directionX > 0 && entity.position.y + collider.getHeight()/2 > e.position.y - 0.3f){
                e.getComponent(PlayerMovement.class).kill();
                endingComponent.showLostSequence();
                e.getComponent(RigidBody2D.class).getBody().setLinearVelocity(new Vector2(directionX*3.5f, 3f));
                e.getComponent(TextureRenderer.class).setFlipX(directionX < 0);
            }
        }
    }
}
