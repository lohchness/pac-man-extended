package src.levels;

import src.Game;

import java.util.Properties;

public class ConcreteLevel extends Level{

    public ConcreteLevel(int nbGridHorzCells, int nbGridVertCells, Properties properties, String grid, Game game) {
        super(nbGridHorzCells, nbGridVertCells, properties, grid, game);
    }
}
