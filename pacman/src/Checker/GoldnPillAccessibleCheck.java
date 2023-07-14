package src.Checker;

import ch.aplu.jgamegrid.Location;
import src.AutoPlayer.ClosestPillBFS;
import src.GameItems.GameItem;
import src.GameItems.ItemType;
import src.GameItems.Portal;
import src.GameManager;
import src.mapeditor.editor.Controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GoldnPillAccessibleCheck implements LevelRequirement{
    private String name;
    private final String description = "Check if all gold and pills can be collected by pacman";

    public GoldnPillAccessibleCheck(String name){
        this.name = name;
    }
    @Override
    public boolean isValidLevel(String level) {
        GameManager gm = GameManager.getInstance();

        List<Location> locationsChecked = new ArrayList<>();
        List<Location> goldAndPillLocations = gm.getPillAndGoldLocation();

        Queue<Location> availableLocations = new LinkedList<>();

        availableLocations.add(gm.getCurrentLevel().getPacActor().getLocation());

        List<Location.CompassDirection> directions = new ArrayList<>();
        directions.add(Location.CompassDirection.NORTH);
        directions.add(Location.CompassDirection.EAST);
        directions.add(Location.CompassDirection.SOUTH);
        directions.add(Location.CompassDirection.WEST);

        List<GameItem> portals = gm.getPortals();

        while (!availableLocations.isEmpty()) {
            Location currentLocation = availableLocations.remove();

            if (currentLocation.x == 1 & currentLocation.y == 1) {
            }
            for (GameItem portal: portals) {
                if (portal.getLocation().equals(currentLocation)) {
                    Portal p = (Portal) portal;
                    Portal partner = p.getPartner();
                    currentLocation = partner.getLocation();
                    break;
                }
            }

            for (Location.CompassDirection direction: directions) {
                Location next = currentLocation.getNeighbourLocation(direction);
                if (locationsChecked.contains(next) || !gm.getCurrentLevel().getPacActor().canMove(next)) {
                    continue;
                }
                locationsChecked.add(next);
                goldAndPillLocations.remove(next);
                availableLocations.add(next);
            }

        }
        if (goldAndPillLocations.isEmpty())
            return true;
        else {
            String ErrorMessage = "Gold or Pill not accessible: ";
            for (Location location: goldAndPillLocations) {
                ErrorMessage += "(";
                Integer x = (Integer) location.getX() + 1;
                Integer y = (Integer) location.getY() + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += ")";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }
    }



    @Override
    public String getNameAndDescription() {
        String NameAndDescription = "";
        NameAndDescription += name;
        NameAndDescription += ": description: ";
        NameAndDescription += description;
        return NameAndDescription;
    }
}
