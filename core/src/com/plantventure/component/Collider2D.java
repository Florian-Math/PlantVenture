package com.plantventure.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class Collider2D extends Component {

    private Fixture fixture;

    private Shape shape;
    private float density;
    private float restitution;
    private float friction;
    private boolean isSensor;
    private boolean isComposite;
    private Filter filter;

    private float width, height;

    @Override
    public void start() {
        if(!isComposite){
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = this.shape;
            fixtureDef.density = density;
            fixtureDef.restitution = restitution;
            fixtureDef.friction = friction;
            fixtureDef.isSensor = isSensor;
            if(filter != null) fixtureDef.filter.set(filter);

            fixture = entity.getComponent(RigidBody2D.class).getBody().createFixture(fixtureDef);

            shape.dispose();
            shape = null;
        }
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void setShapeAsBox(float hx, float hy){
        if(shape != null) shape.dispose();

        PolygonShape p = new PolygonShape();
        p.setAsBox(hx, hy);
        width = hx*2;
        height = hy*2;

        shape = p;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void setSensor(boolean isSensor){
        this.isSensor = isSensor;
    }

    public void setComposite(boolean isComposite){
        this.isComposite = isComposite;
    }

    public void setFilterData(Filter filter){
        this.filter = filter;
    }

    public Shape getShape() {
        return shape;
    }

    public float getDensity() {
        return density;
    }

    public float getRestitution() {
        return restitution;
    }

    public float getFriction() {
        return friction;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public boolean isComposite(){
        return isComposite;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture){
        if(this.fixture != null) return;
        this.fixture = fixture;
    }


}
