package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Animator;
import com.plantventure.component.Collider2D;
import com.plantventure.component.PlayerMovement;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;
import com.plantventure.utilities.Animation;

public class Player extends Entity {

    @Override
    public void create() {
        this.tag = Tag.PLAYER;

        // ANIMATOR
        {
            Animation idleAnimation = new Animation(0.1f, true,
                    scene.getAssetManager().get("images/Player/Idle__000.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__001.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__002.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__003.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__004.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__005.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__006.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__007.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__008.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Idle__009.png", Texture.class)
            );

            Animation jumpingAnimation = new Animation(0.1f, false,
                    scene.getAssetManager().get("images/Player/Jump__000.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__001.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__002.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__003.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__004.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__005.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__006.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__007.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__008.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Jump__009.png", Texture.class)
            );

            Animation runningAnimation = new Animation(0.1f, true,
                    scene.getAssetManager().get("images/Player/Run__000.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__001.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__002.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__003.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__004.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__005.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__006.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__007.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__008.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Run__009.png", Texture.class)
            );

            Animation deathAnimation = new Animation(0.1f, false,
                    scene.getAssetManager().get("images/Player/Dead__000.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__001.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__002.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__003.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__004.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__005.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__006.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__007.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__008.png", Texture.class),
                    scene.getAssetManager().get("images/Player/Dead__009.png", Texture.class)
            );

            Animator animator = new Animator();
            animator.registerAnimation("idle", idleAnimation);
            animator.registerAnimation("running", runningAnimation);
            animator.registerAnimation("jumping", jumpingAnimation);
            animator.registerAnimation("death", deathAnimation);

            this.addComponent(animator);
        }

        // TEXTURE
        {
            this.addComponent(new TextureRenderer());
        }

        // PLAYER MOVEMENT
        {
            this.addComponent(new PlayerMovement());
        }

        // RIGIDBODY2D
        {
            RigidBody2D rb = new RigidBody2D();
            rb.setBodyType(BodyDef.BodyType.DynamicBody);
            rb.setFixedRotation(true);
            this.addComponent(rb);
        }


        // CIRCLE COLLIDER
        {
            Collider2D collider = new Collider2D();

            CircleShape circle = new CircleShape();
            circle.setRadius(1/8f);
            circle.setPosition(new Vector2(0, -0.25f - 1/8f));

            collider.setShape(circle);
            collider.setDensity(0.5f);
            collider.setRestitution(0.1f);
            collider.setFriction(0.5f);

            Filter filter = new Filter();
            filter.categoryBits = 0x0002; // 0010
            collider.setFilterData(filter);

            this.addComponent(collider);
        }

        // 2nd COLLIDER
        {
            Collider2D collider = new Collider2D();


            PolygonShape shape = new PolygonShape();
            shape.set(new Vector2[]{
                    new Vector2(0, -0.25f),
                    new Vector2(-0.25f, 0f),
                    new Vector2(0, 0.5f),
                    new Vector2(0.25f, 0)
            });

            collider.setShape(shape);
            collider.setDensity(0.5f);
            collider.setRestitution(0.1f);
            collider.setFriction(0.5f);

            Filter filter = new Filter();
            filter.categoryBits = 0x0002; // 0010
            collider.setFilterData(filter);

            this.addComponent(collider);
        }

    }
}
