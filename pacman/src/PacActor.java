// PacActor.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import src.AutoPlayer.AutoPlayer;
import src.AutoPlayer.ClosestPill;
import src.AutoPlayer.ClosestPillBFS;
import src.GameItems.GameItem;
import src.GameItems.Portal;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PacActor extends GameCharacter implements GGKeyRepeatListener
{
  private static final int NB_SPRITES = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;

  private final static String imageName = "pacpix.gif";
  
  private List<String> propertyMoves = new ArrayList<>();
  private int propertyMoveIndex = 0;
  private boolean isAuto = false;

  public PacActor() {
    super(true, imageName, NB_SPRITES);
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public void keyRepeated(int keyCode) {
    if (isAuto) {
      return;
    }
    if (isRemoved())  // Already removed
      return;
    Location next = null;
    switch (keyCode) {
      case KeyEvent.VK_LEFT -> {
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
      }
      case KeyEvent.VK_UP -> {
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
      }
      case KeyEvent.VK_RIGHT -> {
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
      }
      case KeyEvent.VK_DOWN -> {
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
      }
    }
    if (next != null && canMove(next))
    {
      setLocation(next);
      itemCollision(next);
    }
  }
  
  // Implementation of abstract method in gameCharacter
  public void act() {
    show(idSprite);
    idSprite++;
    if (idSprite == NB_SPRITES)
      idSprite = 0;

    if (isAuto) {
      moveInAutoMode();
    }
    // TODO - Replace when GameGrid is fixed according to new diagram
    this.gameManager.getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }

  private void followPropertyMoves() {
    String currentMove = propertyMoves.get(propertyMoveIndex);
    switch (currentMove) {
      case "R" -> turn(90);
      case "L" -> turn(-90);
      case "M" -> {
        Location next = getNextMoveLocation();
        if (canMove(next)) {
          setLocation(next);
          itemCollision(next);
        }
      }
    }
    propertyMoveIndex++;
  }

  private void moveInAutoMode() {
    //////////////////choose auto player///////////////////
    //AutoPlayer autoPlayer = new ClosestPill();
     AutoPlayer autoPlayer = new ClosestPillBFS();
    //////////////////////////////////////////////////////

    Location next = autoPlayer.moveTo(gameManager);
    this.setLocation(next);
    itemCollision(next);
    addVisitedList(next);
  }
  
  public int getNbPills() {
    return nbPills;
  }


  // handles collision with items, formerly eatPill
  private void itemCollision(Location location) {
    // find the object stored at that location
    GameItem item = null;
    for(GameItem gameItem: gameManager.getGameItems()){
      if(gameItem.getLocation().equals(location)){
        item = gameItem;
      }
    }

    Color c = getBackground().getColor(location);

    if (c.equals(Color.white)) {
      nbPills++;
      score++;
      item.collide(item, this);
    } else if (c.equals(Color.yellow)) {
      nbPills++;
      score+= 5;
      item.collide(item, this);
    } else if (c.equals(Color.blue)) {
      item.collide(item, this);
    } else if(c.equals(Color.red)){
      ((Portal) item).collide(item, this);
    }
    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }

}
