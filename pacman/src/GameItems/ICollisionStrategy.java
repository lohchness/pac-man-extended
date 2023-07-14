package src.GameItems;

import src.GameCharacter;

public interface ICollisionStrategy {
    public void collide(GameItem item, GameCharacter traveller);
}
