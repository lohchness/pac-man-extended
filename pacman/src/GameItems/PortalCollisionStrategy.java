package src.GameItems;

import ch.aplu.jgamegrid.Location;
import src.GameCharacter;

public class PortalCollisionStrategy implements ICollisionStrategy{

    public PortalCollisionStrategy(){

    }

    public void collide(GameItem item, GameCharacter traveller){
        teleport((Portal) item, traveller);
    }

    public void teleport(Portal portal, GameCharacter traveller){
        Location location = portal.getPartner().getLocation();
        traveller.setLocation(location);
    }
}
