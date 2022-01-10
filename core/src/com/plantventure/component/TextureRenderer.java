package com.plantventure.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TextureRenderer extends Component {
    private TextureRegion textureRegion;
    private final Vector2 realTextureSize = new Vector2(1, 1);

    private boolean flipX = false;

    public TextureRenderer(){}

    public TextureRenderer(Texture texture){
        setTexture(texture);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(textureRegion,
                entity.position.x - (1-entity.origin.x) * realTextureSize.x, entity.position.y - (1-entity.origin.y) * realTextureSize.y,
                entity.origin.x, entity.origin.y,
                realTextureSize.x, realTextureSize.y,
                entity.scale.x, entity.scale.y,
                0);
    }

    public void setTexture(Texture texture){
        textureRegion = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
        if(textureRegion.isFlipX() != flipX) textureRegion.flip(true, false);
        fixRatio();
    }

    public void setTexture(TextureRegion texture){
        this.textureRegion = texture;
        if(textureRegion.isFlipX() != flipX) textureRegion.flip(true, false);
        fixRatio();
    }

    public void setFlipX(boolean flipX){
        if(textureRegion == null) return;
        this.flipX = flipX;
        if(textureRegion.isFlipX() != flipX) textureRegion.flip(true, false);
    }

    public boolean getFlipX(){
        return flipX;
    }

    private void fixRatio(){
        if(textureRegion.getRegionWidth() > textureRegion.getRegionHeight()){
            realTextureSize.x = 1;
            realTextureSize.y = (float)textureRegion.getRegionHeight() / textureRegion.getRegionWidth();
        }
        else if(textureRegion.getRegionWidth() < textureRegion.getRegionHeight()){
            realTextureSize.x = (float)textureRegion.getRegionWidth() / textureRegion.getRegionHeight();
            realTextureSize.y = 1;
        }else{
            realTextureSize.set(1, 1);
        }
    }
}
