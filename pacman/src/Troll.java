package src;

import ch.aplu.jgamegrid.Location;

import java.util.Properties;

public class Troll extends Monster {
    public Troll(Properties properties) {
        super(MonsterType.Troll);
        String[] trollLocations = properties.getProperty("Troll.location").split(",");
        setStartX(Integer.parseInt(trollLocations[0]));
        setStartY(Integer.parseInt(trollLocations[1]));
    }

    public Troll() {
        super(MonsterType.Troll);
    }

    public void walkApproach() {
        Location pacLocation = gameManager.getCurrentLevel().getPacActor().getLocation();
        double oldDirection = getDirection();

        // Walking approach:
        // TX5: Determine direction to pacActor and try to move in that direction. Otherwise, random walk.
        // Troll: Random walk.
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        // Random walk
        randomWalk(oldDirection);
        
        // TODO - Replace when GameGrid is fixed according to new diagram
        gameManager.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }


}
