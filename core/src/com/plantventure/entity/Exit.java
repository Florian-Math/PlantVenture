package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.component.Collider2D;
import com.plantventure.component.ExitComponent;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextRenderer;
import com.plantventure.component.TextureRenderer;

public class Exit extends Entity {

    private boolean isRightSided;

    public Exit(boolean isRightSided){
        this.isRightSided = isRightSided;
    }

    @Override
    public void create() {

        this.addComponent(new ExitComponent());
        this.addComponent(new RigidBody2D());

        Collider2D collider2D = new Collider2D();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);

        collider2D.setSensor(true);
        collider2D.setShape(shape);
        this.addComponent(collider2D);

        TextureRenderer texture = new TextureRenderer(scene.getAssetManager().get("images/Exit_Z.png", Texture.class));
        texture.setFlipX(!isRightSided);
        this.addComponent(texture);
    }
}
