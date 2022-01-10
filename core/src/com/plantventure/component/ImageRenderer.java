package com.plantventure.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

public class ImageRenderer extends Component {

    private TextureRegion texture;

    Matrix4 windowMatrix;

    public ImageRenderer(Texture texture){
        this.texture = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch) {
        windowMatrix = new Matrix4().setToOrtho2D(0, 0, entity.getScene().getViewport().getScreenWidth(), entity.getScene().getViewport().getScreenHeight()); // not optimal
        if(texture == null) return;
        batch.setProjectionMatrix(windowMatrix);
        batch.draw(texture, 0f * entity.getScene().getViewport().getScreenWidth()/2, 0f * entity.getScene().getViewport().getScreenHeight()/2, entity.getScene().getViewport().getScreenWidth() * entity.scale.x, entity.getScene().getViewport().getScreenHeight() * entity.scale.y);
        batch.setProjectionMatrix(entity.getScene().getViewport().getCamera().combined);
    }
}
