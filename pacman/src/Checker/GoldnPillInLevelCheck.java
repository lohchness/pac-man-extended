package src.Checker;

import src.GameManager;

public class GoldnPillInLevelCheck implements LevelRequirement{

    private String name;
    private final String description = "Check if at least two gold and pill in total are in the level";

    public GoldnPillInLevelCheck(String name){
        this.name = name;
    }
    @Override
    public boolean isValidLevel(String level) {
        boolean goldIn = false;
        boolean pillIn = false;
        for (int i = 0; i < level.length(); i++) {
            char c = level.charAt(i);
            if (c == 'c') pillIn = true;
            else if (c == 'd') goldIn = true;
        }

        if (goldIn == true && pillIn == true) return true;
        GameManager.getInstance().getGameCallback().writeString("less than 2 Gold and Pill");
        return false;
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
