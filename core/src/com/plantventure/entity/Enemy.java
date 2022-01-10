package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Animator;
import com.plantventure.component.Collider2D;
import com.plantventure.component.EnemyMovement;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;
import com.plantventure.utilities.Animation;

public class Enemy extends Entity {
    @Override
    public void create() {
        this.tag = Tag.ENEMY;

        Animation runningAnimation = new Animation(0.1f, true,
                scene.getAssetManager().get("images/Enemy/Run__000.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__001.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__002.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__003.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__004.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__005.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__006.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__007.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__008.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Run__009.png", Texture.class)
        );

        Animation deathAnimation = new Animation(0.1f, false,
                scene.getAssetManager().get("images/Enemy/Dead__000.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__001.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__002.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__003.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__004.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__005.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__006.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__007.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__008.png", Texture.class),
                scene.getAssetManager().get("images/Enemy/Dead__009.png", Texture.class)
        );

        Animator animator = new Animator();
        animator.playAnimation(runningAnimation);
        animator.registerAnimation("running", runningAnimation);
        animator.registerAnimation("death", deathAnimation);

        this.addComponent(animator);

        this.addComponent(new TextureRenderer());



        RigidBody2D rb = new RigidBody2D();
        rb.setBodyType(BodyDef.BodyType.DynamicBody);
        rb.setFixedRotation(true);
        this.addComponent(rb);

        Collider2D collider = new Collider2D();

        collider.setShapeAsBox(0.2f, 0.5f);
        collider.setDensity(0.5f);
        collider.setRestitution(0.1f);
        collider.setFriction(0.5f);

        this.addComponent(collider);

        this.addComponent(new EnemyMovement());
    }
}
