package com.plantventure.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ObjectMap;
import com.plantventure.component.BackgroundParallax;
import com.plantventure.component.CameraMovement;
import com.plantventure.component.Collider2D;
import com.plantventure.component.Component;
import com.plantventure.component.RigidBody2D;
import com.plantventure.utilities.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComponentManager {
    private final ObjectMap<Class<? extends Component>, Integer> priorities = new ObjectMap<Class<? extends Component>, Integer>(){};

    private ObjectMap<Class<? extends Component>, ArrayList<Component>> componentsByType; // sorted by priority for easy sort
    private ArrayList<Component> components; // sorted by priority
    private ArrayList<com.plantventure.utilities.Operation> pendingOperations = new ArrayList<>();

    private boolean initialized = false;

    public ComponentManager(){
        components = new ArrayList<Component>();
        componentsByType = new ObjectMap<>();

        priorities.put(RigidBody2D.class, -10);
        priorities.put(Collider2D.class, -5);
        // ...
        priorities.put(CameraMovement.class, 5);
        priorities.put(BackgroundParallax.class, 10);

    }

    /**
     * Add a component
     * @param c component
     */
    public void addComponent(Component c){

        if(!initialized){ // add components if not running
            ArrayList<Component> cs = componentsByType.get(c.getClass());
            if(cs != null)
                cs.add(c);
            else{
                cs = new ArrayList<Component>();
                cs.add(c);
                componentsByType.put(c.getClass(), cs);
            }

            components.add(c);
            Collections.sort(components, new PriorityComparator());
        }
        else pendingOperations.add(new AddComponentOperation(c)); // add components and start the component otherwise
    }

    public void removeComponent(Component c){
        components.remove(c);
        componentsByType.get(c.getClass()).remove(c);
    }

    /**
     * Find a component
     * @param component type of the component
     * @return component
     */
    public final <T extends Component> T findComponent(Class<T> component){
        if(componentsByType.get(component) != null && componentsByType.get(component).size() > 0)
            return (T) componentsByType.get(component).get(0);
        else
            return null;

    }

    /**
     * Find all components of a certain type
     * @param component type of the component
     * @return components
     */
    public final <T extends Component> ArrayList<T> findComponents(Class<T> component){
        if(componentsByType.get(component) != null && componentsByType.get(component).size() > 0) return (ArrayList<T>) componentsByType.get(component);
        return null;
    }

    /**
     * Start all components
     */
    protected void start(){
        for (Component c : components)
            c.start();
        initialized = true;
    }

    /**
     * Update all components
     * @param deltaTime time between the current and the last frame
     */
    protected void update(float deltaTime){
        while (pendingOperations.size() > 0) {
            pendingOperations.get(0).execute();
            pendingOperations.remove(0);
        }

        for (Component c : components)
            c.update(deltaTime);
    }

    /**
     * Fixed update all components (called on fixed rate default: 50fps)
     */
    protected void fixedUpdate(){
        for (Component c : components)
            c.fixedUpdate();
    }

    /**
     * Render all components
     * @param batch batch
     */
    protected void render(Batch batch){
        for (Component c : components)
            c.draw(batch);
    }

    /**
     * Dispose all components
     */
    protected void dispose(){
        for (Component c : components)
            c.dispose();
        componentsByType.clear();
        components.clear();
        pendingOperations.clear();
    }

    private class PriorityComparator implements Comparator<Component> {

        @Override
        public int compare(Component c1, Component c2) {
            Integer p1 = priorities.get(c1.getClass());
            Integer p2 = priorities.get(c2.getClass());
            if(p1 == null) p1 = 0;
            if(p2 == null) p2 = 0;

            if(p1 < p2) return -1;
            if(p1 > p2) return 1;
            return 0;
        }
    }

    private class AddComponentOperation implements Operation {
        private Component component;

        public AddComponentOperation(Component c){
            this.component = c;
        }

        @Override
        public void execute() {
            components.add(component);
            Collections.sort(components, new PriorityComparator());
            component.start();
        }
    }

}
