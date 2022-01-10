package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Collider2D;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;

public class Platform extends Entity {

    public enum PlatformType {
        LEFT,
        CENTER,
        RIGHT
    }

    private PlatformType type;

    public Platform(PlatformType type){
        this.type = type;
    }

    @Override
    public void create() {
        this.origin.set(0.5f, 0.35f);
        this.tag = Tag.GROUND;
        //this.addComponent(new RigidBody2D());

        PolygonShape shape = new PolygonShape();

        switch (type){
            case LEFT:
                shape.set(new Vector2[]{
                        new Vector2(0.5f, -0.5f),
                        new Vector2(0.5f, 0.25f),
                        new Vector2(-0.5f, 0.25f),
                        new Vector2(-0.5f, -0.5f + (0.75f/2f)),
                        new Vector2(0, -0.5f)
                });
                this.addComponent(new TextureRenderer(scene.getAssetManager().get("images/Platform_J.png", Texture.class)));
                break;
            case CENTER:
                shape.set(new Vector2[]{
                        new Vector2(0.5f, -0.5f),
                        new Vector2(0.5f, 0.25f),
                        new Vector2(-0.5f, 0.25f),
                        new Vector2(-0.5f, -0.5f)
                });
                this.addComponent(new TextureRenderer(scene.getAssetManager().get("images/Platform_K.png", Texture.class)));
                break;
            case RIGHT:
                shape.set(new Vector2[]{
                        new Vector2(-0.5f, -0.5f),
                        new Vector2(-0.5f, 0.25f),
                        new Vector2(0.5f, 0.25f),
                        new Vector2(0.5f, -0.5f + (0.75f/2)),
                        new Vector2(0, -0.5f)
                });
                this.addComponent(new TextureRenderer(scene.getAssetManager().get("images/Platform_L.png", Texture.class)));
                break;
        }

        Collider2D collider2D = new Collider2D();
        collider2D.setShape(shape);
        collider2D.setDensity(1);
        collider2D.setRestitution(0.1f);
        collider2D.setFriction(0.25f);
        collider2D.setComposite(true);

        this.addComponent(collider2D);
    }
}
