package com.plantventure.component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.plantventure.entity.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TileMap extends Component {

    private ArrayList<Entity> entities;

    public TileMap(ArrayList<Entity> entities){
        this.entities = entities;
    }

    @Override
    public void start() {
        long time = System.nanoTime();
        ArrayList<FixtureDef> fixtures = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++) {
            Collider2D col = entities.get(i).getComponent(Collider2D.class);
            if(col == null) continue;
            if(!col.isComposite()) continue;

            FixtureDef fixtureDef = new FixtureDef();

            // move shape to entity
            PolygonShape shape = (PolygonShape)col.getShape();
            Vector2[] vs = new Vector2[shape.getVertexCount()];
            for (int j = 0; j < shape.getVertexCount(); j++) {
                vs[j] = new Vector2();
                shape.getVertex(j, vs[j]);
                vs[j].add(entities.get(i).position);
            }
            shape.set(vs);
            fixtureDef.shape = shape;

            fixtureDef.density = col.getDensity();
            fixtureDef.restitution = col.getRestitution();
            fixtureDef.friction = col.getFriction();
            fixtureDef.isSensor = col.isSensor();

            fixtures.add(fixtureDef);
        }

        // merge colliders
        while(mergeColliders(fixtures));

        for (int i = 0; i < fixtures.size(); i++) {
            entity.getComponent(RigidBody2D.class).getBody().createFixture(fixtures.get(i));
            fixtures.get(i).shape.dispose();
        }

        long newTime = System.nanoTime() - time;
        System.out.println("Colliders merge time : " + newTime / 1000000000f);
    }

    public boolean mergeColliders(ArrayList<FixtureDef> fixtures){
        boolean hasMerge = false;

        for (int i = 0; i < fixtures.size(); i++) {
            FixtureDef fixtureA = fixtures.get(i);
            PolygonShape shapeA = (PolygonShape)fixtureA.shape;

            for (int j = i+1; j < fixtures.size(); j++) {
                FixtureDef fixtureB = fixtures.get(j);
                PolygonShape shapeB = (PolygonShape)fixtureB.shape;

                if(fixtureA.density != fixtureB.density || fixtureA.restitution != fixtureB.restitution || fixtureA.friction != fixtureB.friction || fixtureA.isSensor != fixtureB.isSensor)
                    continue;

                if(areSharingEdge(shapeA, shapeB)){
                    mergeShape(shapeA, shapeB);

                    fixtures.remove(j);
                    j--;
                    hasMerge = true;
                }

            }
        }
        return hasMerge;
    }

    public boolean areSharingEdge(PolygonShape shapeA, PolygonShape shapeB){
        Vector2 vA = new Vector2();
        Vector2 vB = new Vector2();
        int nbEdgeShared = 0;

        for (int i = 0; i < shapeA.getVertexCount(); i++) {
            shapeA.getVertex(i, vA);
            for (int j = 0; j < shapeB.getVertexCount(); j++) {
                shapeB.getVertex(j, vB);

                if(vA.equals(vB) && (++nbEdgeShared) >= 2) return true;
            }
        }

        return false;
    }

    public void mergeShape(PolygonShape shapeA, PolygonShape shapeB){
        Vector2 vA = new Vector2();
        Vector2 vB = new Vector2();

        ArrayList<Vector2> vertices = new ArrayList<>();

        for (int i = 0; i < shapeA.getVertexCount(); i++) {
            shapeA.getVertex(i, vA);
            vertices.add(vA.cpy());
        }
        for (int j = 0; j < shapeB.getVertexCount(); j++) {
            shapeB.getVertex(j, vB);
            vertices.add(vB.cpy());
        }

        for (int i = 0; i < vertices.size(); i++) {
            Vector2 v1 = vertices.get(i);
            for (int j = i+1; j < vertices.size(); j++) {
                if(v1.equals(vertices.get(j))){
                    vertices.remove(j);
                    j--;
                    vertices.remove(i);
                }
            }
        }

        shapeA.set(vertices.toArray(new Vector2[0]));
        shapeB.dispose();
    }

}
