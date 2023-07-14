package src.AutoPlayer;

import ch.aplu.jgamegrid.Location;
import src.GameItems.GameItem;
import src.GameItems.Portal;
import src.GameManager;
import src.PacActor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClosestPillBFS implements AutoPlayer{

    public class Node {
        Location location;
        Node parent;

        Location.CompassDirection parentToCurrentDirection;

        public Node(Location l, Node p, Location.CompassDirection dir) {
            location = l;
            parent = p;
            parentToCurrentDirection = dir;
        }
    }
    @Override
    public Location moveTo(GameManager gameManager) {
        Queue<Node> BFSTree = new LinkedList<>();


        PacActor pacActor = gameManager.getCurrentLevel().getPacActor();
        Location pacActorLocation = pacActor.getLocation();

        List<Location> locationsChecked = new ArrayList<>();
        List<GameItem> portals = gameManager.getPortals();

        locationsChecked.add(pacActorLocation);
//
        Node startNode = new Node(pacActorLocation, null, null);
        BFSTree.add(startNode);

        List<Location.CompassDirection> directions = new ArrayList<>();
        directions.add(Location.CompassDirection.NORTH);
        directions.add(Location.CompassDirection.EAST);
        directions.add(Location.CompassDirection.SOUTH);
        directions.add(Location.CompassDirection.WEST);

        List<Location> goldAndPillLocations = gameManager.getPillAndGoldLocation();
//
        while (!BFSTree.isEmpty()) {
            Node current = BFSTree.remove();
            if (goldAndPillLocations.contains(current.location)) {
                Node next = backPropagate(current);
                pacActor.setDirection(next.parentToCurrentDirection);
                return next.location;
            }

            for (GameItem portal: portals) {
                if (portal.getLocation().equals(current.location)) {
                    Portal p = (Portal) portal;
                    Portal partner = p.getPartner();
                    Node portalPartnerNode = new Node(partner.getLocation(), current.parent, current.parentToCurrentDirection);
                    current = portalPartnerNode;
                    break;
                }
            }
//
           // if (current.location == portal)
            // add 4 directions around the portals partner.
            for (Location.CompassDirection dir: directions) {
                Location next = current.location.getNeighbourLocation(dir);

                if (locationsChecked.contains(next) || !pacActor.canMove(next)) {
                    continue;
                }
                locationsChecked.add(next);
                Node nextNode = new Node(next, current, dir);
                BFSTree.add(nextNode);
            }
        }
        return null;
    }

    private Node backPropagate(Node node) {
        Node current = node;
        Node next = null;
        while(current.parent != null) {
            next = current;
            current = current.parent;
        }
        return next;
    }

}
