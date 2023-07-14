package src.levels;

import java.util.Properties;

public abstract class LevelDecorator {
    protected Level level;

    public LevelDecorator(Level level){
        this.level = level;
    }

    public void drawGrid(){
        level.drawGrid();
    }
}
