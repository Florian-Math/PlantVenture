package com.plantventure.component;

public class WorldInfo extends Component {

    private int width, height;

    public WorldInfo(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
