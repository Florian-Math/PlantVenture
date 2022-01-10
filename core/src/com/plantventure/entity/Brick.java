package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Collider2D;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;

public class Brick extends Entity {

    public enum BrickType {
        UP,
        MIDDLE,
        RIGHT,
        LEFT,
        UP_RIGHT,
        UP_LEFT,
        DOWN_LEFT,
        DOWN,
        DOWN_RIGHT
    }

    private BrickType type;

    public Brick(BrickType type){
        this.type = type;
    }

    @Override
    public void create() {
        this.tag = Tag.GROUND;
        //this.addComponent(new RigidBody2D());

        Collider2D collider2D = new Collider2D();

        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);

        collider2D.setShape(box);
        collider2D.setDensity(1f);
        collider2D.setRestitution(0.1f);
        collider2D.setFriction(0.25f);
        collider2D.setComposite(true);

        this.addComponent(collider2D);

        Texture texture = null;
        switch (type){
            case UP:
                texture = scene.getAssetManager().get("images/Brick_B.png", Texture.class);
                break;
            case MIDDLE:
                texture = scene.getAssetManager().get("images/Brick_E.png", Texture.class);
                break;
            case RIGHT:
                texture = scene.getAssetManager().get("images/Brick_F.png", Texture.class);
                break;
            case LEFT:
                texture = scene.getAssetManager().get("images/Brick_D.png", Texture.class);
                break;
            case UP_RIGHT:
                texture = scene.getAssetManager().get("images/Brick_C.png", Texture.class);
                break;
            case UP_LEFT:
                texture = scene.getAssetManager().get("images/Brick_A.png", Texture.class);
                break;
            case DOWN_LEFT:
                texture = scene.getAssetManager().get("images/Brick_G.png", Texture.class);
                break;
            case DOWN:
                texture = scene.getAssetManager().get("images/Brick_H.png", Texture.class);
                break;
            case DOWN_RIGHT:
                texture = scene.getAssetManager().get("images/Brick_I.png", Texture.class);
                break;
        }
        this.addComponent(new TextureRenderer(texture));
    }
}
