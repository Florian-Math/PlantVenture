package com.plantventure.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;
    private boolean looping;

    public Animation(Texture spriteSheet, int spriteNumberCols, int spriteNumberRows, float frameDur){
        this.looping = true;

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / spriteNumberCols,
                spriteSheet.getHeight() / spriteNumberRows);

        TextureRegion[] frames = new TextureRegion[spriteNumberCols * spriteNumberRows];
        int index = 0;
        for (int i = 0; i < spriteNumberRows; i++) {
            for (int j = 0; j < spriteNumberCols; j++){
                frames[index++] = tmp[i][j];
            }
        }

        animation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(frameDur, frames);
    }

    public Animation(float frameDur, boolean looping, Texture... sprites){
        this.looping = looping;

        TextureRegion[] frames = new TextureRegion[sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            frames[i] = new TextureRegion(sprites[i], 0, 0, sprites[i].getWidth(), sprites[i].getHeight());
        }
        animation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(frameDur, frames);
    }

    public TextureRegion getKeyFrame(float stateTime){
        return animation.getKeyFrame(stateTime, looping);
    }

}
