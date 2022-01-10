package com.plantventure.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.utils.ObjectMap;
import com.plantventure.component.Component;
import com.plantventure.game.Scene;

import java.util.ArrayList;

public abstract class Entity {
    public enum Tag{
        NONE,
        PLAYER,
        GEM,
        GROUND,
        WATER,
        ENEMY
    }

    private ObjectMap<Class<? extends Component>, ArrayList<Component>> componentsByTypes;
    private ArrayList<Component> components;

    protected Scene scene;
    protected Tag tag = Tag.NONE;

    public final Vector2 position = new Vector2();
    public final Vector2 scale = new Vector2(1, 1);
    public final Vector2 origin = new Vector2(0.5f, 0.5f);


    public Entity(){
        componentsByTypes = new ObjectMap<Class<? extends Component>, ArrayList<Component>>();
        components = new ArrayList<Component>();
    }

    /**
     * Create the entity (add components here)
     */
    public abstract void create();

    /**
     * Add a component
     * @param c component
     */
    public final void addComponent(Component c){
        c.setEntity(this);

        // add to components
        components.add(c);

        // add to componentsByTypes
        ArrayList<Component> cs = componentsByTypes.get(c.getClass());
        if(cs == null){
            cs = new ArrayList<Component>();
            cs.add(c);
            componentsByTypes.put(c.getClass(), cs);
        }else{
            cs.add(c);
        }

        // add to componentManager
        scene.getComponentManager().addComponent(c);
    }

    /**
     * Get a component based on his type
     * @param c type of the component
     * @return component
     */
    public final <T extends Component> T getComponent(Class<T> c){
        ArrayList<Component> cs = componentsByTypes.get(c);
        if(cs == null) return null;
        if(cs.isEmpty()) return null;

        return (T) componentsByTypes.get(c).get(0);
    }

    /**
     * Get a all components based on his type
     * @param c type of components
     * @return components
     */
    public final <T extends Component> ArrayList<T> getComponents(Class<T> c){
        return (ArrayList<T>) componentsByTypes.get(c);
    }

    /**
     * Get all components link to this entity
     * @return component
     */
    public final ArrayList<Component> getComponents(){
        return components;
    }

    public final void onCollisionEnter(Entity e, WorldManifold manifold){
        for (Component c : components)
            c.onCollisionEnter(e, manifold);
    }

    public final void onCollisionExit(Entity e, WorldManifold manifold){
        for (Component c : components)
            c.onCollisionExit(e, manifold);
    }

    public final void removeComponent(Component component){
        component.dispose();
        components.remove(component);
        ArrayList<Component> cs = componentsByTypes.get(component.getClass());
        if(cs != null) cs.remove(component);
        scene.getComponentManager().removeComponent(component);
    }

    public final void destroy(){
        for (Component c : components)
            c.dispose();
        scene.removeEntity(this);
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }
    public Scene getScene(){
        return scene;
    }
    public Tag getTag() {
        return tag;
    }

}
