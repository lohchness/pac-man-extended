package src.GameItems;

import ch.aplu.jgamegrid.*;
import src.Game;

import java.awt.*;

public class Gold extends GameItem {

    private EdibleItemCollisionStrategy adapter;

    public Gold(Location location){
        super(ItemType.Gold, location);
        EdibleItemCollisionStrategy adapter = new EdibleItemCollisionStrategy();
        super.setAdapter(adapter);
    }
    public void putItem(Game game, GGBackground bg, Location location){
        bg.setPaintColor(Color.yellow);
        super.putItem(game, bg, location);
    }

}
