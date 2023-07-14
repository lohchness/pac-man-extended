package src.levels;

import src.GameCharacter;

public class LevelCharacterDecorator extends LevelDecorator{

    public LevelCharacterDecorator(Level level){
        super(level);
    }

    public void addCharacter(GameCharacter actor){
        level.getActors().add(actor);
    }

    public void drawOnGrid(GameCharacter actor){

    }

    @Override
    public void drawGrid(){
        super.drawGrid();

        for(GameCharacter actor: level.getGameCharacters()){
            drawOnGrid(actor);
        }
    }
}
