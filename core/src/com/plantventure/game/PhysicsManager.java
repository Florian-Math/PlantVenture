package com.plantventure.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.plantventure.entity.Entity;
import com.plantventure.utilities.Operation;

import java.util.ArrayList;

public class PhysicsManager {

    private ArrayList<DestroyOperation> destroyOperations;

    private World world;

    public PhysicsManager(){
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Entity e1 = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity e2 = (Entity) contact.getFixtureB().getBody().getUserData();

                e1.onCollisionEnter(e2, contact.getWorldManifold());
                e2.onCollisionEnter(e1, contact.getWorldManifold());
            }

            @Override
            public void endContact(Contact contact) {
                Entity e1 = (Entity) contact.getFixtureA().getBody().getUserData();
                Entity e2 = (Entity) contact.getFixtureB().getBody().getUserData();

                e1.onCollisionExit(e2, contact.getWorldManifold());
                e2.onCollisionExit(e1, contact.getWorldManifold());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        destroyOperations = new ArrayList<DestroyOperation>();
    }

    /**
     * Update the physics (call this on fixed rate)
     */
    public void update(){
        world.step(Scene.FIXED_TIMESTEP, 6, 2);

        // remove bodies
        if(!world.isLocked()){
            while (!destroyOperations.isEmpty()){
                destroyOperations.get(0).execute();
                destroyOperations.remove(0);
            }
        }
    }

    public World getWorld(){
        return world;
    }

    public void dispose(){
        world.dispose();
    }

    public void destroyBody(Body body) {
        destroyOperations.add(new DestroyOperation(body));
    }

    private class DestroyOperation implements Operation {
        private Body body;

        public DestroyOperation(Body body){
            this.body = body;
        }

        @Override
        public void execute() {
            world.destroyBody(body);
        }
    }
}
