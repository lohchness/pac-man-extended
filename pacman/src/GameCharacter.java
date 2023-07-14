package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class GameCharacter extends Actor {
	private int seed;
	private final Random random = new Random();
	private final ArrayList<Location> visitedList = new ArrayList<>();
	private final int LISTLENGTH = 10;
	private int startX;
	private int startY;

	//private final PacManGameGrid grid;
	public final static GameManager gameManager = GameManager.getInstance();
	
	// different constructors for pacActor and Monster
	public GameCharacter(boolean isRotatable, String imageName, int nbSprites) {
		super(true, "sprites/" + imageName, nbSprites);
	}
	
	public GameCharacter(String imageName) {
		super("sprites/" + imageName);
	}
	
	// if location has been visited already
	public boolean isVisited(Location location) {
		for (Location loc : visitedList) {
			if (loc.equals(location)) return true;
		}
		return false;
	}
	
	protected void addVisitedList(Location location) {
		visitedList.add(location);
		if (visitedList.size() == LISTLENGTH) {
			visitedList.remove(0);
		}
	}
	
	// Randomiser
	public void setSeed(int seed) {
		this.seed = seed;
		random.setSeed(seed);
	}
	
	// Must be implemented in child class - dependent on whether Player or Monster
	public abstract void act();
	
	// If gameCharacter can move
	public boolean canMove(Location location) {
		Color c = getBackground().getColor(location);
		if ( c.equals(Color.gray) || location.getX() >= gameManager.getCurrentLevel().getGrid().getNumHorzCells()
				|| location.getX() < 0 || location.getY() >= gameManager.getCurrentLevel().getGrid().getNumVertCells() || location.getY() < 0)
			return false;
		else
			return true;
	}
	
	public Random getRandom() {
		return random;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
}
