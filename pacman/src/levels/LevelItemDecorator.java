package src.levels;

import src.GameItems.GameItem;

public class LevelItemDecorator extends LevelDecorator{

    public LevelItemDecorator(Level level){
        super(level);
    }

    public void addGameItem(GameItem gameItem){
        level.getActors().add(gameItem);
    }

    private void drawOnGrid(GameItem item){
        item.putItem(level.getGame(), level.getGame().getGameBg(), item.getLocation());
    }

    @Override
    public void drawGrid(){
        super.drawGrid();

        for(GameItem item: level.getGameItems()){
            drawOnGrid(item);
        }
    }
}
