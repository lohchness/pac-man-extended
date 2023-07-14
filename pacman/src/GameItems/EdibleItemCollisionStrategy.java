package src.GameItems;

import src.GameCharacter;

import java.awt.*;

import static src.GameItems.GameItem.gameManager;

public class EdibleItemCollisionStrategy implements ICollisionStrategy{

    public EdibleItemCollisionStrategy(){

    }

    public void collide(GameItem item, GameCharacter traveller){
        eat(item);
    }

    public void eat(GameItem item){
        item.getBackground().fillCell(item.getLocation(), Color.lightGray);
        // TODO - Replace when GameGrid is fixed according to new diagram
        gameManager.getGameCallback().pacManEatPillsAndItems(item.getLocation(), item.getType().getTypeName());
        gameManager.removeItem(item);
    }
}
