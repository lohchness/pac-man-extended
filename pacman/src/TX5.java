package src;

import ch.aplu.jgamegrid.Location;

import java.util.Properties;

public class TX5 extends Monster{

    public TX5(Properties properties) {
        super(MonsterType.TX5);
        String[] tx5Locations = properties.getProperty("TX5.location").split(",");
        setStartX(Integer.parseInt(tx5Locations[0]));
        setStartY(Integer.parseInt(tx5Locations[1]));
    }

    public TX5() {
        super(MonsterType.TX5);
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

        if (!isVisited(next) && canMove(next))
        {
            setLocation(next);
        }
        else
        {
            // Random walk
            randomWalk(oldDirection);
        }
        // TODO - Replace when GameGrid is fixed according to new diagram
        gameManager.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }



}
