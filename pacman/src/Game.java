// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;

import src.Checker.*;
import src.GameItems.Portal;
import src.levels.*;


import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Game extends GameGrid
{
    // class setting up the game, containing the game loop and all essential variables, communicates via the gameManager

    private final int nbHorzCells;
    private final int nbVertCells;
    private final static int CELL_SIZE = 20;

    private static final GameManager gameManager = GameManager.getInstance();

    private LevelItemDecorator levelItemDecorator;
    private LevelCharacterDecorator levelCharacterDecorator;
    private CompositeChecker levelChecker;
    private int seed = 30006;

    private final String GAME_OVER_TITLE = "GAME OVER";
    private final String YOU_WIN_TITLE = "YOU WIN";

    // Launched from level editor
    public Game(int nbHorzCells, int nbVertCells, String grid, Properties properties, boolean autoPlay) {
        super(nbHorzCells, nbVertCells, CELL_SIZE, false);
        this.nbHorzCells = nbHorzCells;
        this.nbVertCells = nbVertCells;

        while (grid.length() < (this.nbHorzCells * this.nbVertCells)) {
            grid += 'b';
        }
        grid = grid.replace("\n", "");

        gameManager.setNbHorz(nbHorzCells);
        gameManager.setNbVert(nbVertCells);

        levelChecker = new CompositeChecker();
        addLevelChecks(levelChecker);
        PacManCheck p = new PacManCheck("pacMan");

        boolean isPacMan = true;
        if (!p.isValidLevel(grid)) {
            isPacMan = false;
        }
//        levelChecker.displayRequirements(); // display the checks that leveler checker will check

        String title = GAME_OVER_TITLE;
        if (isPacMan) {
            Level level1 = new ConcreteLevel(nbHorzCells, nbVertCells, properties, grid, this);
            ArrayList<Level> levels = new ArrayList<Level>();
            levels.add(level1);

            gameManager.setLevels(levels);
            gameManager.setAutoPlay(autoPlay);

            setSimulationPeriod(100);
            setTitle("[PacMan in the Multiverse]");

            GGBackground bg = getBg();

            for (Level level : levels) {
                levelItemDecorator = new LevelItemDecorator(level);
                levelCharacterDecorator = new LevelCharacterDecorator(level);

                setSimulationPeriod(100);
                setTitle("[PacMan in the Multiverse]");


                bg.clear(Color.gray);
                level.addAllGameItems();
                level.addAllGameCharacters();
                Portal.setPartners(level);

                levelCharacterDecorator.drawGrid();
                levelItemDecorator.drawGrid();  // MUST DO ITEM DECORATOR DRAW GRID SECOND!!!

                level.getPacActor().setAuto(gameManager.isAutoPlay());

                //Setup Random seeds
                level.setSeedGameCharacters();
                addKeyRepeatListener(level.getPacActor());
                setKeyRepeatPeriod(150);

                level.getPacActor().setSlowDown(3);
                setupActorLocations(level);
            }

            //// level checking ////////////////////////////////////////////////

            boolean levelValid = true;
            if (!levelChecker.isValidLevel(grid)) {
                levelValid = false;
            }
            //////////////////////////////////////////////////////////////////////

            //Run the game
            doRun();
            show();
            // Loop to look for collision in the application thread
            // This makes it improbable that we miss a hit
            boolean hasPacmanBeenHit = false;
            boolean hasPacmanEatAllPills = false;

            // if all pills and gold are eaten then win
            int maxPillsAndGold = gameManager.countGoldAndPills();

            if (levelValid) {
                do {
                    hasPacmanBeenHit = gameManager.pacActorHit();

                    hasPacmanEatAllPills = gameManager.getCurrentLevel().getPacActor().getNbPills() >= maxPillsAndGold;
                    delay(10);
                } while (!hasPacmanBeenHit && !hasPacmanEatAllPills);
                delay(120);

            }

                Location loc = gameManager.getCurrentLevel().getPacActor().getLocation();


                gameManager.stopGameCharacters();

                if (hasPacmanBeenHit) {
                    bg.setPaintColor(Color.red);
                    title = "GAME OVER";
                    addActor(new Actor("sprites/explosion3.gif"), loc);
                } else if (hasPacmanEatAllPills) {
                    bg.setPaintColor(Color.yellow);
                    title = "YOU WIN";
                }

        }
        setTitle(title);
        gameManager.getGameCallback().endOfGame(title);

        doPause();
    }
    
    // For playing .xml levels a folder
    public Game(int nbHorzCells, int nbVertCells, Properties properties, ArrayList<XMLParser> XMLLevels, boolean autoPlay) {
        super(nbHorzCells, nbVertCells, CELL_SIZE, false);
        this.nbHorzCells = nbHorzCells;
        this.nbVertCells = nbVertCells;

        levelChecker = new CompositeChecker();
        addLevelChecks(levelChecker);
        PacManCheck p = new PacManCheck("pacMan");

        ArrayList<Level> levels = new ArrayList<Level>();
        for (XMLParser parsed: XMLLevels) {
            String grid = parsed.getMaze();
            while (grid.length() < (this.nbHorzCells * this.nbVertCells)) {
                grid += 'b';
            }
            grid = grid.replace("\n", "");

            if (!p.isValidLevel(grid)) {
                continue;
            }

            boolean isAuto = properties.getProperty("PacMan.isAuto").equals("true");
            Level new_level = new ConcreteLevel(this.nbHorzCells, this.nbVertCells, properties, grid, this);
            levels.add(new_level);
        }

        gameManager.setLevels(levels);
        boolean pacManDead = false;
        while(true){
            removeAllActors();
            doReset();
            Level level = gameManager.getCurrentLevel();
            gameManager.setAutoPlay(autoPlay);

            levelItemDecorator = new LevelItemDecorator(level);
            levelCharacterDecorator = new LevelCharacterDecorator(level);

            setSimulationPeriod(100);
            setTitle("[PacMan in the Multiverse]");

            GGBackground bg = getBg();


            bg.clear(Color.gray);
            level.addAllGameItems();
            level.addAllGameCharacters();
            Portal.setPartners(level);

            levelCharacterDecorator.drawGrid();
            levelItemDecorator.drawGrid();  // MUST DO ITEM DECORATOR DRAW GRID SECOND!!!

            level.getPacActor().setAuto(gameManager.isAutoPlay());
            //Setup Random seeds
            level.setSeedGameCharacters();
            addKeyRepeatListener(level.getPacActor());
            setKeyRepeatPeriod(150);

            level.getPacActor().setSlowDown(3);
            setupActorLocations(level);


            //// level checking ////////////////////////////////////////////////
            boolean levelValid = true;
//            levelChecker.displayRequirements();
            if (!levelChecker.isValidLevel(level.getGridAsString())) {
                levelValid = false;
            }
            levelChecker.setCurrLevel(levelChecker.getCurrLevel() + 1);
            //////////////////////////////////////////////////////////////////////

            //Run the game
            doRun();
            show();
            // Loop to look for collision in the application thread
            // This makes it improbable that we miss a hit
            boolean hasPacmanBeenHit = false;
            boolean hasPacmanEatAllPills = false;

            // if all pills and gold are eaten then win
            int maxPillsAndGold = gameManager.countGoldAndPills();

            String title = GAME_OVER_TITLE;
            if (levelValid && !pacManDead) {

                do {
                    hasPacmanBeenHit = gameManager.pacActorHit();

                    hasPacmanEatAllPills = level.getPacActor().getNbPills() >= maxPillsAndGold;
                    delay(10);
                } while (!hasPacmanBeenHit && !hasPacmanEatAllPills);
                delay(120);
                if (hasPacmanBeenHit) pacManDead = true;
            }

            Location loc = level.getPacActor().getLocation();


            gameManager.stopGameCharacters();

            if (hasPacmanBeenHit) {
                bg.setPaintColor(Color.red);
                title = GAME_OVER_TITLE;
                addActor(new Actor("sprites/explosion3.gif"), loc);
            } else if (hasPacmanEatAllPills) {
                bg.setPaintColor(Color.yellow);
                title = YOU_WIN_TITLE;
            }


            // move to the next level but if theres none left then leave
            if(gameManager.moveToNextLevel() == -1){
                setTitle(title);
                gameManager.getGameCallback().endOfGame(title);
                break;
            }
        }

        doPause();
    }

    private void addLevelChecks(CompositeChecker Checker) {
        LevelRequirementFactory levelCheckFactory = LevelRequirementFactory.getInstance();

        List<String> levelRequirements = new ArrayList<>();
        levelRequirements.add("PacMan");
        levelRequirements.add("GoldnPillInLevel");
        levelRequirements.add("PortalPair");
        levelRequirements.add("GoldnPillAccessible");
        // add more level checking here //

        for(String levelRequirement: levelRequirements) {
            Checker.add(levelCheckFactory.makeLevelCheck(levelRequirement));
        }
    }

    private void setupActorLocations(Level level) {

        for(Monster monster: level.getMonsters()){
            addActor(monster, new Location(monster.getStartX(), monster.getStartY()), Location.NORTH);
        }
        addActor(level.getPacActor(),
                new Location(level.getPacActor().getStartX(), level.getPacActor().getStartY()));
    }

    public GGBackground getGameBg(){
        return getBg();
    }

}
