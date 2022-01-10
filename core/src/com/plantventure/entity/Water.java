package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Collider2D;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;
import com.plantventure.component.WaterComponent;

public class Water extends Entity {

    @Override
    public void create() {
        origin.y = 0.25f;
        this.tag = Tag.WATER;
        this.addComponent(new RigidBody2D());

        Collider2D collider2D = new Collider2D();
        collider2D.setSensor(true);

        PolygonShape shape = new PolygonShape();
        shape.set(new Vector2[]{
                new Vector2(-0.5f, -0.5f),
                new Vector2(0.5f, -0.5f),
                new Vector2(0.5f, 0.25f),
                new Vector2(-0.5f, 0.25f)
        });

        collider2D.setShape(shape);

        this.addComponent(new WaterComponent());

        this.addComponent(collider2D);

        this.addComponent(new TextureRenderer(scene.getAssetManager().get("images/Water.png", Texture.class)));
    }
}
