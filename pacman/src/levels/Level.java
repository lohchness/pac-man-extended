package src.levels;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import src.*;
import src.GameItems.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public abstract class Level {
    private final PacManGameGrid grid;
    private final String gridAsString;
    private Properties properties;
    private int seed;
    private ArrayList<Actor> actors;
    private PacActor pacActor;
    private Game game;
    private LevelItemDecorator levelItemDecorator;
    private LevelCharacterDecorator levelCharacterDecorator;

    private int nbGridHorzCells;
    private int nbGridVertCells;

    public Level(int nbGridHorzCells, int nbGridVertCells, Properties properties, String grid, Game game) {
        this.actors = new ArrayList<Actor>();
        this.grid = new PacManGameGrid(nbGridHorzCells, nbGridVertCells, grid);
        this.properties = properties;
        this.game = game;
        this.levelItemDecorator = new LevelItemDecorator(this);
        this.levelCharacterDecorator = new LevelCharacterDecorator(this);
        this.gridAsString = grid;
        this.nbGridHorzCells = nbGridHorzCells;
        this.nbGridVertCells = nbGridVertCells;
    }

    public PacManGameGrid getGrid() {
        return grid;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setSeedGameCharacters() {
        seed = Integer.parseInt(properties.getProperty("seed"));
        getPacActor().setSeed(seed);

        for(Monster monster: getMonsters()){
            monster.setSeed(seed);
            monster.setSlowDown(3);
            if (monster.getType() == MonsterType.TX5) {
                monster.stopMoving(5);
            }
        }
    }

    public PacActor getPacActor() {
        return pacActor;
    }

    public ArrayList<Monster> getMonsters(){
        ArrayList<Monster> monsters = new ArrayList<Monster>();
        for(Actor actor: actors){
            if(actor instanceof Monster){
                monsters.add((Monster) actor);
            }
        }
        return monsters;
    }

    public ArrayList<GameCharacter> getGameCharacters(){
        ArrayList<GameCharacter> characters = new ArrayList<GameCharacter>();
        for(Actor actor: actors){
            if(actor instanceof GameCharacter){
                characters.add((GameCharacter) actor);
            }
        }
        return characters;
    }

    public void addGameItem(GameItem gameItem){
        getActors().add(gameItem);
    }

    public ArrayList<GameItem> getGameItems(){
        ArrayList<GameItem> items = new ArrayList<GameItem>();
        for(Actor actor: actors){
            if(actor instanceof GameItem){
                items.add((GameItem) actor);
            }
        }
        return items;
    }

    public ArrayList<Portal> getPortals(){
        ArrayList<Portal> portals = new ArrayList<Portal>();
        for(Actor actor: actors){
            if(actor instanceof Portal){
                portals.add((Portal) actor);
            }
        }
        return portals;
    }

    public Game getGame(){
        return game;
    }

    public void drawGrid() {
        String temp = "";
        for (int y = 0; y < game.getNbVertCells(); y++) {
            for (int x = 0; x < game.getNbHorzCells(); x++) {
                game.getGameBg().setPaintColor(Color.white);
                Location location = new Location(x, y);
                char a = grid.getCell(location);

                temp += grid.getCell(location);
                if (a > 0)
                    game.getBg().fillCell(location, Color.lightGray);
                if (a == 'b') {
                    game.getGameBg().fillCell(location, Color.gray);
                }
            }
        }
    }

    public void addAllGameItems(){
        String temp = "";
        for (int y = 0; y < game.getNbVertCells(); y++) {
            for (int x = 0; x < game.getNbHorzCells(); x++) {
                game.getGameBg().setPaintColor(Color.white);
                Location location = new Location(x, y);
                char a = grid.getCell(location);
                temp += grid.getCell(location);

                GameItem item = null;

                switch(a){
                    case 'c':
                        item = new Pill(location);
                        break;
                    case 'd':
                        item = new Gold(location);
                        break;
                    case 'e':
                        item = new Ice(location);
                        break;
                    case 'i':
                        item = new Portal(ItemType.PortalWhiteTile, location);
                        break;
                    case 'j':
                        item = new Portal(ItemType.PortalYellowTile, location);
                        break;
                    case 'k':
                        item = new Portal(ItemType.PortalDarkGoldTile, location);
                        break;
                    case 'l':
                        item = new Portal(ItemType.PortalDarkGrayTile, location);
                        break;
                }
                // add the item
                if(item != null) {
                    levelItemDecorator.addGameItem(item);
                    game.addActor(item, location);
                }

            }
        }
    }

    public void addAllGameCharacters(){
        String temp = "";
        for (int y = 0; y < game.getNbVertCells(); y++) {
            for (int x = 0; x < game.getNbHorzCells(); x++) {
                game.getBg().setPaintColor(Color.white);
                Location location = new Location(x, y);
                char a = grid.getCell(location);
                temp += grid.getCell(location);

                GameCharacter actor = null;
                switch(a){
                    case 'f':
                        actor =  new PacActor();
                        actor = (PacActor) actor;
                        this.pacActor = (PacActor) actor;
                        break;
                    case 'g':
                        actor = new Troll();
                        break;
                    case 'h':
                        actor = new TX5();
                        break;

                }
                // add the character
                if(actor != null) {
                    levelCharacterDecorator.addCharacter(actor);
                    actor.setStartX(x);
                    actor.setStartY(y);
                }

            }
        }
    }

    public int getNbGridHorzCells() {
        return nbGridHorzCells;
    }

    public int getNbGridVertCells() {
        return nbGridVertCells;
    }

    public String getGridAsString() {
        return gridAsString;
    }
}
