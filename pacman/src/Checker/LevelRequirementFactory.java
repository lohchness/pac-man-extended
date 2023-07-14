package src.Checker;

public class LevelRequirementFactory {
    private static LevelRequirementFactory instance;
    private LevelRequirementFactory() {}

    public static LevelRequirementFactory getInstance() {
        if (instance == null) {
            instance = new LevelRequirementFactory();
        }
        return instance;
    }

    public LevelRequirement makeLevelCheck(String name) {
        switch(name){
            case ("GoldnPillInLevel"):
                return new GoldnPillInLevelCheck(name);
            case ("PacMan"):
                return new PacManCheck(name);
            case ("PortalPair"):
                return new PortalCheck(name);
            case ("GoldnPillAccessible"):
                return new GoldnPillAccessibleCheck(name);
            case ("GameFolder"):
                return new GameFolderCheck(name);
            case ("FolderSequence"):
                return new FolderSequenceCheck(name);
        }
        return null;
    }
}
