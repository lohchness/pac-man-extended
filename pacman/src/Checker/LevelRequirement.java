package src.Checker;

public interface LevelRequirement {
    String getNameAndDescription();
    boolean isValidLevel(String level);
}
