package src.GameItems;

import ch.aplu.jgamegrid.*;
import src.Game;
import src.GameCharacter;
import src.GameManager;

import java.awt.*;


public abstract class GameItem extends Actor implements ICollisionStrategy{
    private final ItemType type;
    public static final GameManager gameManager = GameManager.getInstance();
    public ICollisionStrategy adapter;

    public GameItem(ItemType type, Location location) {
        super(type.getImageName());
        super.setLocation(location);
        this.type = type;
    }

    // Custom constructor for Pill, since Pill doesn't have an image
    public GameItem(Location location) {
        super();
        super.setLocation(location);
        this.type = ItemType.Pill;
    }

    public void putItem(Game game, GGBackground bg, Location location){
        bg.fillCircle(game.toPoint(location), 5);
    }

    public ItemType getType() {
        return type;
    }

    public void collide(GameItem item, GameCharacter traveller){
        adapter.collide(item, traveller);
    }

    public void setAdapter(ICollisionStrategy adapter){
        this.adapter = adapter;
    }

}
