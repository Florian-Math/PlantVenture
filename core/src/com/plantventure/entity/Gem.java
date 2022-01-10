package com.plantventure.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.plantventure.component.Animator;
import com.plantventure.component.Collider2D;
import com.plantventure.component.PointsContainer;
import com.plantventure.component.RigidBody2D;
import com.plantventure.component.TextureRenderer;
import com.plantventure.utilities.Animation;

public class Gem extends Entity {

    private int points;

    public Gem(){
        this(1);
    }

    public Gem(int points){
        this.points = points;
    }

    @Override
    public void create() {
        this.tag = Tag.GEM;
        this.scale.set(0.60f, 0.60f);
        this.addComponent(new PointsContainer(points));

        this.addComponent(new RigidBody2D());

        Collider2D collider2D = new Collider2D();
        collider2D.setSensor(true);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);
        collider2D.setShape(shape);

        this.addComponent(collider2D);

        this.addComponent(new TextureRenderer());

        if(points == 1) this.addComponent(new Animator(new Animation(scene.getAssetManager().get("images/Gem_1.png", Texture.class), 1, 6, 0.25f)));
        else this.addComponent(new Animator(new Animation(scene.getAssetManager().get("images/Gem_2.png", Texture.class), 1, 6, 0.25f)));


    }
}
