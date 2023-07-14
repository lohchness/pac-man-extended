package src.GameItems;


import ch.aplu.jgamegrid.*;
import src.Game;
import src.GameCharacter;

import java.awt.*;

public class Ice extends GameItem{

    public Ice(Location location){
        super(ItemType.Ice, location);
        EdibleItemCollisionStrategy adapter = new EdibleItemCollisionStrategy();
        super.setAdapter(adapter);
    }

    public void putItem(Game game, GGBackground bg, Location location){
        bg.setPaintColor(Color.blue);
        super.putItem(game, bg, location);
    }

    public void collide(GameItem item, GameCharacter traveller){
        adapter.collide(this, traveller);
    }
}
