package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.GameItems.*;
import src.levels.*;
import src.utility.GameCallback;

import javax.sound.sampled.Port;
import java.util.ArrayList;

public class GameManager {
    private static GameManager instance;
    private static ArrayList<Level> levels;
    private static Level currentLevel;
    private static int levelNum;
    private static GameCallback gameCallback = null;

    private static boolean autoPlay = false;

    private int nbHorz;
    private int nbVert;

    private GameManager() {
        gameCallback = new GameCallback();
    }
    
    public static GameManager getInstance(){
        if(instance == null){
            instance = new GameManager();
        }
        return instance;
    }
    
    public void setLevels(ArrayList<Level> levels){
        this.levels = levels;
        currentLevel = levels.get(0);
        levelNum = 0;
    }
    public void setAutoPlay(boolean autoPlay){
        this.autoPlay = autoPlay;
    }

    public ArrayList<Location> getPillAndGoldLocation() {
        ArrayList<Location> pillAndGoldLocations = new ArrayList<>();
        for(GameItem item: this.getGameItems()){
            if (item.getType() == ItemType.Pill || item.getType() == ItemType.Gold) {
                pillAndGoldLocations.add(item.getLocation());
            }
        }
        return pillAndGoldLocations;
    }

    public ArrayList<GameItem> getPortals() {
        ArrayList<GameItem> portals = new ArrayList<>();
        for(GameItem item: this.getGameItems()){
            if (item.getType() == ItemType.PortalWhiteTile || item.getType() == ItemType.PortalYellowTile ||
                item.getType() == ItemType.PortalDarkGoldTile || item.getType() == ItemType.PortalDarkGrayTile) {
                portals.add(item);
            }
        }
        return portals;
    }

    public void removeItem(Actor item){
        item.hide();
        this.currentLevel.getActors().remove(item);
//        this.getGameItems().remove(item);
    }

    public GameCallback getGameCallback() {
        return gameCallback;
    }

    public ArrayList<GameItem> getGameItems() {
        ArrayList<GameItem> gameItems = new ArrayList<GameItem>();
        for(Actor actor: currentLevel.getActors()){
            if(actor instanceof GameItem){
                gameItems.add((GameItem) actor);
            }
        }
        return gameItems;
    }

    public int countGoldAndPills() {
        int maxPillsAndGold = 0;
        for(Actor item: getGameItems()){
            if(item instanceof Gold || item instanceof Pill){
                maxPillsAndGold ++;
            }
        }
        return maxPillsAndGold;
    }

    public boolean pacActorHit() {
        for(Monster monster: currentLevel.getMonsters()){
            if (monster.getLocation().equals(currentLevel.getPacActor().getLocation())) {
                return true;
            }
        }
        return false;
    }

    public void stopGameCharacters() {
        for(Monster monster: currentLevel.getMonsters()){
            monster.setStopMoving(true);
        }
        currentLevel.getPacActor().removeSelf();
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public Level getCurrentLevel(){
        return currentLevel;
    }

    // returns the number of the next level, -1 if there is no more levels
    public int moveToNextLevel(){
        if(levelNum + 1 == levels.size()){
            return -1;
        }

        currentLevel = levels.get(levelNum + 1);
        levelNum += 1;
        return levelNum;
    }

    public void setNbHorz(int nbHorz) {
        this.nbHorz = nbHorz;
    }

    public int getNbHorz() {
        return nbHorz;
    }

    public void setNbVert(int nbVert) {
        this.nbVert = nbVert;
    }

    public int getNbVert() {
        return nbVert;
    }
}
