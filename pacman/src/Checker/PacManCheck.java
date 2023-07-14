package src.Checker;

import src.GameManager;

import java.util.ArrayList;
import java.util.List;

public class PacManCheck implements LevelRequirement {
    private String name;
    private final String description = "Check if only one pacman is in the level";

    public PacManCheck(String name){
        this.name = name;
    }
    @Override
    public boolean isValidLevel(String level) {
        Integer currLevel = CompositeChecker.getCurrLevel();
        String Begin = "Level ";
        Begin += currLevel.toString();
        Begin += " ERRORS!!";

        int numPacMan = 0;
        List<Integer> pacManLocations = new ArrayList<>();

        for (int i = 0; i < level.length(); i++) {
            if (level.charAt(i) == 'f') {
                numPacMan++;
                pacManLocations.add(i);
            }
        }

        if (numPacMan == 0) {
            GameManager.getInstance().getGameCallback().writeString(Begin);
            GameManager.getInstance().getGameCallback().writeString("no start for PacMan");
            return false;
        }
        else if (numPacMan > 1) {
            GameManager.getInstance().getGameCallback().writeString(Begin);
            String ErrorMessage = "more than one start for Pacman: ";
            int columns = GameManager.getInstance().getNbHorz();

            for (Integer pacManLocation: pacManLocations) {
                ErrorMessage += "(";

                Integer x = ((pacManLocation) % columns) + 1;

                Integer y = ((pacManLocation) / columns) + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += "); ";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }
        else return true;
    }

    @Override
    public String getNameAndDescription() {
        String NameAndDescription = "";
        NameAndDescription += name;
        NameAndDescription += ": description: ";
        NameAndDescription += description;
        return NameAndDescription;
    }
}
