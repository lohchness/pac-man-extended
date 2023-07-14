package src.Checker;

import src.GameManager;

import java.util.ArrayList;
import java.util.List;

public class CompositeChecker implements LevelRequirement {
    private List<LevelRequirement> requirements;


    private static Integer currLevel = 1;

    public CompositeChecker() {
        requirements = new ArrayList<>();
    }

    @Override
    public boolean isValidLevel(String level) {
        boolean valid = true;

        String ErrorMessage = "Level ";
        ErrorMessage += currLevel.toString();
        ErrorMessage += " ERRORS!!";
        GameManager.getInstance().getGameCallback().writeString(ErrorMessage);

        for(LevelRequirement requirement: requirements) {
            if (!requirement.isValidLevel(level)) {
                valid = false;
            }
        }
        if (valid) GameManager.getInstance().getGameCallback().writeString("NO LEVEL ERRORS");;
        return valid;
    }

    @Override
    public String getNameAndDescription() {
        return null;
    }

    public void add(LevelRequirement requirement) {
        requirements.add(requirement);
    }

    public void remove(LevelRequirement requirement) {
        requirements.remove(requirement);
    }

    public void displayRequirements() {
        System.out.println("Requirements:");
        for(LevelRequirement requirement: requirements) {
            System.out.println(requirement.getNameAndDescription());
        }
    }

    public static Integer getCurrLevel() {
        return currLevel;
    }

    public void setCurrLevel(Integer currLevel) {
        this.currLevel = currLevel;
    }
}
