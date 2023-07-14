package src.GameItems;

import ch.aplu.jgamegrid.*;
import src.Game;

import java.awt.*;

public class Pill extends GameItem{


    public Pill(Location location){
        super(location);
        EdibleItemCollisionStrategy adapter = new EdibleItemCollisionStrategy();
        super.setAdapter(adapter);
    }
    public void putItem(Game game, GGBackground bg, Location location){
        bg.setPaintColor(Color.white);
        super.putItem(game, bg, location);
    }

}
