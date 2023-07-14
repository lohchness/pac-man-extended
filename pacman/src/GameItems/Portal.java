package src.GameItems;

import ch.aplu.jgamegrid.*;
import src.Game;
import src.levels.*;

import java.awt.*;

public class Portal extends GameItem implements ICollisionStrategy{
    private Portal partner = null; // portal this one is paired to

    public Portal(ItemType portalType, Location location){
        super(portalType, location);
        PortalCollisionStrategy adapter = new PortalCollisionStrategy();
        super.setAdapter(adapter);
    }

    public void setPartner(Portal partner){
        this.partner = partner;
    }

    public void putItem(Game game, GGBackground bg, Location location){
        bg.setPaintColor(Color.red);
        super.putItem(game, bg, location);
    }

    public static void setPartners(Level level){
        // TODO: assume only 2 of each colour, should be accounted for in level checking
        for(Portal portal1: level.getPortals()){
            boolean partnerFound = false;
            // look for portal of the same ItemType and assign to them
            for(Portal portal2: level.getPortals()) {
                if (portal1 == portal2) continue;
                else if (portal1.getType() == portal2.getType()) {
                    portal1.setPartner(portal2);
                    partnerFound = true;
                    break;
                }
            }
            if(!partnerFound) System.out.println("ERROR: portal does not have partner");
        }
    }
    public Portal getPartner(){
        return partner;
    }

}
