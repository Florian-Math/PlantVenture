package com.plantventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.plantventure.component.Component;
import com.plantventure.entity.Entity;
import com.plantventure.utilities.Operation;

import java.util.ArrayList;

public class Scene {
    public static final float FIXED_TIMESTEP = 1/50f;

    private boolean initialized = false;

    private ArrayList<Entity> entities;
    private ArrayList<Operation> pendingOperations = new ArrayList<>();

    private Viewport viewport;
    private PhysicsManager physicsManager;
    private ComponentManager componentManager;
    private AssetManager assetManager;

    private float accumulator = 0;

    public Scene(AssetManager assetManager){
        physicsManager = new PhysicsManager();
        componentManager = new ComponentManager();
        this.assetManager = assetManager;

        entities = new ArrayList<Entity>();

        viewport = new FitViewport(16, 12);
    }

    /**
     * Start the scene, all entities registered and all components
     */
    public void start(){
        // create registered entities
        for (Entity e : entities)
            e.create();
        initialized = true;

        // start components
        componentManager.start();
    }

    int fpsCounter = 0;
    float timePassed = 0;

    /**
     * Update all components (called every frame)
     */
    public void update(){
        float deltaTime = Math.min(Gdx.graphics.getDeltaTime(), 0.25f);

        // FPS Counter
        fpsCounter ++;
        timePassed += deltaTime;
        while(timePassed >= 1){
            timePassed --;
            Gdx.graphics.setTitle("PlantVenture (fps : " + fpsCounter + ")");
            //System.out.println(fpsCounter);
            fpsCounter = 0;
        }

        // Fixed update loop
        accumulator += deltaTime;
        while (accumulator >= FIXED_TIMESTEP) {
            fixedUpdate();
            accumulator -= FIXED_TIMESTEP;
        }

        // handle pending operations
        while (pendingOperations.size() > 0) {
            pendingOperations.get(0).execute();
            pendingOperations.remove(0);
        }

        // update components
        componentManager.update(deltaTime);
    }

    /**
     * Update physics (fixed rate default : 50 fps see FIXED_TIMESTEP)
     */
    public void fixedUpdate(){
        componentManager.fixedUpdate();
        physicsManager.update();
    }

    /**
     * Render all components
     * @param batch batch
     */
    public void render(Batch batch){
        componentManager.render(batch);
    }

    /**
     * Dispose the scene
     */
    public void dispose(){
        componentManager.dispose();
        physicsManager.dispose();
        entities.clear();
    }

    // -------

    /**
     * Add an entity
     * @param e entity
     */
    public void addEntity(Entity e){
        e.setScene(this);

        if(!initialized) entities.add(e); // add entity if not running
        else pendingOperations.add(new AddEntityOperation(e)); // add to pending operation otherwise
    }

    /**
     * Add all the entities
     * @param entities entities
     */
    public void addAllEntities(ArrayList<Entity> entities){
        for(Entity e : entities){
            addEntity(e);
        }
    }

    /**
     * Remove an entity
     * @param e entity
     */
    public void removeEntity(Entity e){
        entities.remove(e);
        for (Component c : e.getComponents())
            componentManager.removeComponent(c);
    }

    /**
     * Get an entity
     * @param entity type of entity
     * @return entity
     */
    public final <T extends Entity> T getEntity(Class<T> entity) {
        for (Entity e : entities) {
            if(e.getClass().isAssignableFrom(entity)) return (T)e;
        }
        return null;
    }

    // -----

    /**
     * Find a component in the scene
     * @param component type of component searched
     * @return component
     */
    public final <T extends Component> T findComponent(Class<T> component){
        return componentManager.findComponent(component);
    }

    public Viewport getViewport(){
        return viewport;
    }

    public PhysicsManager getPhysicsManager(){
        return physicsManager;
    }
    public ComponentManager getComponentManager(){
        return componentManager;
    }
    public AssetManager getAssetManager(){
        return assetManager;
    }
    public boolean isInitialized(){
        return initialized;
    }


    /**
     * Operation that adds and creates the entity
     */
    private class AddEntityOperation implements Operation {
        private Entity entity;

        public AddEntityOperation(Entity e){
            this.entity = e;
        }

        @Override
        public void execute() {
            entities.add(entity);
            entity.create();
        }
    }
}
