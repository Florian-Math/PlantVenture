package com.plantventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.plantventure.entity.Background;
import com.plantventure.entity.Brick;
import com.plantventure.entity.CameraEntity;
import com.plantventure.entity.EndingText;
import com.plantventure.entity.Enemy;
import com.plantventure.entity.Entity;
import com.plantventure.entity.Exit;
import com.plantventure.entity.GameManager;
import com.plantventure.entity.Gem;
import com.plantventure.entity.Platform;
import com.plantventure.entity.Player;
import com.plantventure.entity.ScoreUI;
import com.plantventure.entity.TileMap;
import com.plantventure.entity.TimerUI;
import com.plantventure.entity.Water;

import java.util.ArrayList;
import java.util.Scanner;

public class LevelLoader {
    private final Vector2 size = new Vector2();
    private final ArrayList<Entity> entities = new ArrayList<>();

    /**
     * Load the level contained in assets/levels/level_???.txt
     * @param level level to load
     * @return entities loaded
     */
    public ArrayList<Entity> loadLevel(int level){
        entities.clear();

        FileHandle file = Gdx.files.internal("levels/level_00" + level + ".txt");
        Scanner scanner = new Scanner(file.readString());

        String line = scanner.nextLine().replaceAll("\\s+", " ");
        String[] infos = line.split(" ");

        size.x = Integer.parseInt(infos[0]);
        size.y = Integer.parseInt(infos[1]);
        int time =  Integer.parseInt(infos[2]);

        Background background = new Background();
        entities.add(background);

        TileMap tileMap = new TileMap();

        Entity[][] entitiesTab = new Entity[(int) size.x][(int) size.y];
        // i: height, j: width, start: top-left
        for (int i = 0; i < size.y; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < size.x; j++) {
                Entity e = getEntity(line.charAt(j), (int)size.y - i, j);
                if(e != null) {
                    entities.add(e);
                    if(e.getClass() == Platform.class || e.getClass() == Brick.class) tileMap.addEntity(e);
                }
                entitiesTab[j][(int)size.y - (i+1)] = e;
            }
        }

        // fix floating exit
        for (int i = 0; i < size.x; i++) {
            for (int j = 0; j < size.y; j++) {
                if(entitiesTab[i][j] != null && entitiesTab[i][j].getClass() == Platform.class){
                    if(j+1 < size.y && entitiesTab[i][j+1] != null){
                        if(entitiesTab[i][j+1].getClass() != Gem.class) entitiesTab[i][j+1].position.sub(0, 0.25f);
                    }
                }
            }

        }

        entities.add(tileMap);
        entities.add(new GameManager((int)size.x, (int)size.y));
        entities.add(new CameraEntity());
        entities.add(new ScoreUI());
        entities.add(new TimerUI(time));
        entities.add(new EndingText());

        background.setBackgroundImage("images/" + scanner.nextLine());

        scanner.close();

        return entities;
    }

    private Entity getEntity(char c, int i, int j){
        Entity e = null;
        switch (c){
            case 'J': // left platform
                e = new Platform(Platform.PlatformType.LEFT);
                break;
            case 'L': // right platform
                e = new Platform(Platform.PlatformType.RIGHT);
                break;
            case 'K': // middle platform
                e = new Platform(Platform.PlatformType.CENTER);
                break;
            case 'C': // up-right brick
                e = new Brick(Brick.BrickType.UP_RIGHT);
                break;
            case 'F': // right brick
                e = new Brick(Brick.BrickType.RIGHT);
                break;
            case 'B': // up brick
                e = new Brick(Brick.BrickType.UP);
                break;
            case 'E': // middle brick
                e = new Brick(Brick.BrickType.MIDDLE);
                break;
            case 'D': // left brick
                e = new Brick(Brick.BrickType.LEFT);
                break;
            case 'A': // up-left brick
                e = new Brick(Brick.BrickType.UP_LEFT);
                break;
            case 'G': // up-left brick
                e = new Brick(Brick.BrickType.DOWN_LEFT);
                break;
            case 'H': // up-left brick
                e = new Brick(Brick.BrickType.DOWN);
                break;
            case 'I': // up-left brick
                e = new Brick(Brick.BrickType.DOWN_RIGHT);
                break;
            case 'W': // water
                e = new Water();
                break;
            case '1': // Gem1
                e = new Gem(1);
                break;
            case '2': // Gem2
                e = new Gem(2);
                break;
            case 'P': // player
                e = new Player();
                break;
            case 'Z': // player
                e = new Exit(j > size.x/2);
                break;
            case 'M': // player
                e = new Enemy();
                break;
        }

        if(e != null) e.position.set(j + 0.5f, i - 0.5f);

        return e;
    }

    private int getWidth(){
        return (int)size.x;
    }

    private int getHeight(){
        return (int)size.y;
    }
}
