package com.plantventure.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;

public class TextRenderer extends Component {

    private String fontPath = "";
    private BitmapFont font;
    private String text;
    private int alignmentX;
    private int alignmentY;
    private int fontSize;

    Camera camera;
    Matrix4 windowMatrix;

    public TextRenderer(String text, String fontPath, int alignmentX, int alignmentY){
        setText(text);
        setFont(fontPath);
        setAlignment(alignmentX, alignmentY);
    }

    @Override
    public void start() {
        camera = entity.getScene().getViewport().getCamera();
    }

    @Override
    public void draw(Batch batch) {
        if(font == null) return;
        batch.setProjectionMatrix(windowMatrix);
        font.draw(batch, text, entity.getScene().getViewport().getScreenWidth() * entity.position.x, (alignmentY*fontSize/2f) + entity.getScene().getViewport().getScreenHeight() * entity.position.y, 0, alignmentX, true);
        batch.setProjectionMatrix(camera.combined);
    }

    public void setAlignment(int alignmentX, int alignmentY){
        this.alignmentX = alignmentX;
        this.alignmentY = alignmentY + 1;
    }

    public void setFont(String fontPath){
        this.fontPath = fontPath;
    }

    public void setText(String text){
        this.text = text;
    }

    public void resize(int width, int height){
        if(fontPath.isEmpty()) return;
        windowMatrix = new Matrix4().setToOrtho2D(0, 0, entity.getScene().getViewport().getScreenWidth(), entity.getScene().getViewport().getScreenHeight());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontSize = (int)((60f / 1024) * entity.getScene().getViewport().getScreenWidth());
        parameter.size = fontSize;
        parameter.color = new Color(1, 1, 0, 0.75f);
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = (int)((3f / 1024) * entity.getScene().getViewport().getScreenWidth());
        font = generator.generateFont(parameter);
        generator.dispose();
    }
}
