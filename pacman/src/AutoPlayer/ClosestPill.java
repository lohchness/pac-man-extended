package src.AutoPlayer;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.GameManager;
import src.PacActor;

import java.util.List;

public class ClosestPill implements AutoPlayer {
    @Override
    public Location moveTo(GameManager gameManager) {
        PacActor pacActor = gameManager.getCurrentLevel().getPacActor();
        Location pacActorLocation = pacActor.getLocation();
        Location closestPill = closestPillLocation(gameManager, pacActorLocation);
        double oldDirection = pacActor.getDirection();


        Location.CompassDirection compassDir =
                pacActorLocation.get4CompassDirectionTo(closestPill);
        Location next = pacActorLocation.getNeighbourLocation(compassDir);
        pacActor.setDirection(compassDir);
        if (!pacActor.isVisited(next) && pacActor.canMove(next)) {
            pacActor.setLocation(next);
        } else {
            // normal movement
            int sign = pacActor.getRandom().nextDouble() < 0.5 ? 1 : -1;
            pacActor.setDirection(oldDirection);
            pacActor.turn(sign * 90);  // Try to turn left/right
            next = pacActor.getNextMoveLocation();
            if (pacActor.canMove(next)) {
                pacActor.setLocation(next);
            } else {
                pacActor.setDirection(oldDirection);
                next = pacActor.getNextMoveLocation();
                if (pacActor.canMove(next)) // Try to move forward
                {
                    pacActor.setLocation(next);
                } else {
                    pacActor.setDirection(oldDirection);
                    pacActor.turn(-sign * 90);  // Try to turn right/left
                    next = pacActor.getNextMoveLocation();
                    if (pacActor.canMove(next)) {
                        pacActor.setLocation(next);
                    } else {
                        pacActor.setDirection(oldDirection);
                        pacActor.turn(180);  // Turn backward
                        next = pacActor.getNextMoveLocation();
                        pacActor.setLocation(next);
                    }
                }
            }
        }
        return next;
    }

    private Location closestPillLocation(GameManager gameManager, Location givenLocation) {
        int currentDistance = 1000;
        Location currentLocation = null;

        List<Location> pillAndItemLocations = gameManager.getPillAndGoldLocation();
        for (Location location: pillAndItemLocations) {
            int distanceToPill = location.getDistanceTo(givenLocation);
            if (distanceToPill < currentDistance) {
                currentLocation = location;
                currentDistance = distanceToPill;
            }
        }

        return currentLocation;
    }
}
