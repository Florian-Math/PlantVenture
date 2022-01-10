package com.plantventure.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.plantventure.component.TextRenderer;
import com.plantventure.entity.MainMenu;
import com.plantventure.utilities.Operation;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	private static Game instance;

	// --- MANAGERS
	private AssetManager assetManager;
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer;
	private final LevelLoader levelLoader = new LevelLoader();

	// --- SCENE
	private Scene scene;
	private int sceneIndex;
	private int maxSceneIndex;

	// OPERATIONS
	private final ArrayList<com.plantventure.utilities.Operation> pendingOperations = new ArrayList<>();

	private Game(){}

	@Override
	public void create () {
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		assetManager = new AssetManager();

		long timer = System.nanoTime();
		// load resources
		FileHandle assets;

		// images
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) assets = Gdx.files.internal("android/assets/images");
		else assets = Gdx.files.internal("images");

		loadImages(assets, "images");

		// sound
		if(Gdx.app.getType() == Application.ApplicationType.Desktop) assets = Gdx.files.internal("android/assets/sounds");
		else assets = Gdx.files.internal("sounds");

		for (FileHandle f : assets.list()) {
			if(f.extension().equals("ogg")) assetManager.load("sounds/" + f.name(), Sound.class);
		}

		assetManager.finishLoading();
		timer = System.nanoTime() - timer;
		System.out.println("AssetLoading : " + timer / 1000000000f + "s");

		loadScene(0);

		maxSceneIndex = 2;
	}

	private void loadImages(FileHandle folder, String folderName){
		for (FileHandle f : folder.list()) {
			if(f.extension().equals("png")) assetManager.load(folderName + "/" + f.name(), Texture.class);
			if(f.isDirectory()) loadImages(f, folderName + "/" + f.name());
		}
	}

	@Override
	public void render () {
		// clear pending operations
		while (pendingOperations.size() > 0){
			pendingOperations.get(0).execute();
			pendingOperations.remove(0);
		}

		// update scene
		scene.update();

		// render scene
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(scene.getViewport().getCamera().combined);
		batch.begin();

		scene.render(batch);

		batch.end();

		//debugRenderer.render(scene.getPhysicsManager().getWorld(), scene.getViewport().getCamera().combined);
	}

	/**
	 * Load a scene
	 * @param level level of the scene
	 */
	public void loadScene(int level){
		if(level == 0) pendingOperations.add(new LoadMainMenuOperation());
		else pendingOperations.add(new LoadSceneOperation(level));
	}

	/**
	 * Get the index of the current scene
	 * @return index of the current scene
	 */
	public int getCurrentSceneIndex(){
		return sceneIndex;
	}

	/**
	 * Get the index of the last scene
	 * @return index of the last scene
	 */
	public int getMaxSceneIndex(){
		return maxSceneIndex;
	}

	@Override
	public void resize(int width, int height) {
		if(scene == null) return;

		scene.getViewport().update(width, height);
		ArrayList<TextRenderer> texts = scene.getComponentManager().findComponents(TextRenderer.class);
		if(texts != null){
			for (TextRenderer t : texts) t.resize(width, height);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		scene.dispose();
	}

	public static Game getInstance(){
		if(instance == null) instance = new Game();
		return instance;
	}

	private class LoadMainMenuOperation implements Operation {

		@Override
		public void execute() {
			if(scene != null) scene.dispose();
			scene = new Scene(assetManager);
			scene.addEntity(new MainMenu());

			scene.start();
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sceneIndex = 0;
		}
	}

	private class LoadSceneOperation implements Operation {

		private final int level;

		public LoadSceneOperation(int level){
			this.level = level;
		}

		@Override
		public void execute() {
			long timer = System.nanoTime();
			System.out.println("Loading Scene ...");

			if(scene != null) scene.dispose();
			scene = new Scene(assetManager);
			scene.addAllEntities(levelLoader.loadLevel(level));

			scene.start();
			resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			sceneIndex = level;

			timer = System.nanoTime() - timer;
			System.out.println("Scene Loading : " + timer / 1000000000f + "s");
		}
	}
}
