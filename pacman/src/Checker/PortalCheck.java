package src.Checker;

import src.GameManager;

import java.util.ArrayList;
import java.util.List;

public class PortalCheck implements LevelRequirement{

    private String name;
    private final String description = "Check if exactly two tiles for each portal in the level";

    public PortalCheck(String name){
        this.name = name;
    }
    @Override
    public boolean isValidLevel(String level) {

        List<Integer> white = new ArrayList<>();
        List<Integer> yellow = new ArrayList<>();
        List<Integer> darkGold = new ArrayList<>();
        List<Integer> darkGrey = new ArrayList<>();

        for (int i = 0; i < level.length(); i++) {
            char c = level.charAt(i);
            if (c == 'i') white.add(i);
            else if (c == 'j') yellow.add(i);
            else if (c == 'k') darkGold.add(i);
            else if (c == 'l') darkGrey.add(i);
        }


        if (!(white.size() == 0 || white.size() == 2)) {
            String ErrorMessage = "portal White count is not 2: ";
            int columns = GameManager.getInstance().getNbHorz();
            for (Integer location: white) {
                ErrorMessage += "(";

                Integer x = ((location) % columns) + 1;

                Integer y = ((location) / columns) + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += ");";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }

        if (!(yellow.size() == 0 || yellow.size() == 2)) {
            String ErrorMessage = "portal Yellow count is not 2: ";
            int columns = GameManager.getInstance().getNbHorz();
            for (Integer location: yellow) {
                ErrorMessage += "(";

                Integer x = ((location) % columns) + 1;

                Integer y = ((location) / columns) + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += ");";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }
        if (!(darkGold.size() == 0 || darkGold.size() == 2)) {
            String ErrorMessage = "portal darkGold count is not 2: ";
            int columns = GameManager.getInstance().getNbHorz();
            for (Integer location: darkGold) {
                ErrorMessage += "(";

                Integer x = ((location) % columns) + 1;

                Integer y = ((location) / columns) + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += ");";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }
        if (!(darkGrey.size() == 0 || darkGrey.size() == 2)) {
            String ErrorMessage = "portal darkGrey count is not 2: ";
            int columns = GameManager.getInstance().getNbHorz();
            for (Integer location: darkGrey) {
                ErrorMessage += "(";

                Integer x = ((location) % columns) + 1;

                Integer y = ((location) / columns) + 1;

                ErrorMessage += x.toString();
                ErrorMessage += ",";
                ErrorMessage += y.toString();
                ErrorMessage += ");";
            }
            GameManager.getInstance().getGameCallback().writeString(ErrorMessage);
            return false;
        }
        return true;
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
