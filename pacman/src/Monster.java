// Monster.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.Location;
import src.GameItems.GameItem;
import src.GameItems.Portal;

import java.awt.*;
import java.util.*;

public abstract class Monster extends GameCharacter {
  private final MonsterType type;
  private boolean stopMoving = false;
  private int numTimersRunning = 0; // for the stopMoving funciton

  public Monster(MonsterType type) {
    super(type.getImageName());
    this.type = type;
  }

  public void stopMoving(int seconds) {
    this.stopMoving = true;
    Timer timer = new Timer(); // Instantiate Timer Object
    numTimersRunning ++;
    int SECOND_TO_MILLISECONDS = 1000;
    final Monster monster = this;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        // if there is another timer running, don't let them move again yet
        if(numTimersRunning < 2) monster.stopMoving = false;
        numTimersRunning --;
      }
    }, (long) seconds * SECOND_TO_MILLISECONDS);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

  // Implementation of abstract method in gameCharacter
  public void act() {
    if (stopMoving) {
      return;
    }
    walkApproach();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  public abstract void walkApproach();

  public MonsterType getType() {
    return type;
  }
  public boolean getStopMoving(){
    return stopMoving;
  }
  
  protected Location randomWalk(double oldDirection) {
    
    int sign = this.getRandom().nextDouble() < 0.5 ? 1 : -1;
    setDirection(oldDirection);
    turn(sign * 90);  // Try to turn left/right
    Location next = getNextMoveLocation();
    if (canMove(next))
    {
      setLocation(next);
    }
    else
    {
      setDirection(oldDirection);
      next = getNextMoveLocation();
      if (canMove(next)) // Try to move forward
      {
        setLocation(next);
      }
      else
      {
        setDirection(oldDirection);
        turn(-sign * 90);  // Try to turn right/left
        next = getNextMoveLocation();
        if (canMove(next))
        {
          setLocation(next);
        }
        else
        {
          
          setDirection(oldDirection);
          turn(180);  // Turn backward
          next = getNextMoveLocation();
          setLocation(next);
        }
      }
    }
    itemCollision(next);
    return next;
  }

  private void itemCollision(Location location) {
    // find the object stored at that location
    GameItem item = null;
    for(GameItem gameItem: gameManager.getGameItems()){
      if(gameItem.getLocation().equals(location)){
        item = gameItem;
      }
    }

    Color c = getBackground().getColor(location);

    if(c.equals(Color.red)){
      ((Portal) item).collide(item, this);
    }
  }

}