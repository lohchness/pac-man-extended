# pac-man-extended

https://github.com/lohchness/pac-man-extended/assets/50405970/8e1d84af-da21-41df-bfb7-6020dbb2d1bb

# Overview

This is an extended version of the classic game Pac-Man. Pac-Man will still have to eat all the pills on the board to win, but there are new enemies and mechanics.

This project refactored an existing codebase to allow for extensibility and to follow GRASP design principles. **The models and justifications are in a report inside `/documentation`.**

## Extension 1 - New enemies, new items

New item types have been added in addition to pills. Ice will freeze enemies for a few seconds, and gold pieces will increase the current score by 5 points (pills increase the score by 1).

New enemy types have been added, each with different behaviours.

- Troll: randomly walk across the map. It will walk straight upon hitting a wall, at which point it will turn and walk in a straight line somewhere else.
- T-X5: Follows Pac-Man in as little turns as possible.
- Orion: Protects the gold pieces to be eaten by Pac-Man.
- Alien: Follows Pac-Man in as shortest distance as possible.
- Wizard: Walks through walls.

- Upon Pac-Man eating a gold piece, the enemies will enter a furious state and move faster.
- Upon Pac-Man eating an ice cube, all enemies will be frozen regardless of state.

This design has been modelled in such a way that it is easy to make further extensions to the game without disrupting any other parts of the model.

## Extension 2 - Map Editor, portals, auto-play

There are multiple modes this can be launched. If a folder is passed as an argument, the driver will play the game levels in that folder. Starting the editor with an existing map as an argument will launch the Level Editor. Starting the editor with no argument will launch the Level Editor with an empty map. Pac-Man being played by the computer (auto-play) is an optional condition.

Portal tiles allow for travel to the corresponding pair. There are multiple colours of portals that Pac-Man can travel through.

The auto-player option will make the computer play Pac-Man and attempt to eat all the pills and gold pieces to complete the level.

