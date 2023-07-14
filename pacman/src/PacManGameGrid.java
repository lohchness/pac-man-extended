// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

import java.io.File;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import src.mapeditor.editor.Constants;


public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private char[][] mazeArray;

  public PacManGameGrid(int nbHorzCells, int nbVertCells, String grid)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new char[nbVertCells][nbHorzCells];
    String maze = grid;
    // for project 1
//      "xxxxxxxxxxxxxxxxxxxx" + // 0
//      "x....x....g...x....x" + // 1
//      "xgxx.x.xxxxxx.x.xx.x" + // 2
//      "x.x.......i.g....x.x" + // 3
//      "x.x.xx.xx  xx.xx.x.x" + // 4
//      "x......x    x......x" + // 5
//      "x.x.xx.xxxxxx.xx.x.x" + // 6
//      "x.x......gi......x.x" + // 7
//      "xixx.x.xxxxxx.x.xx.x" + // 8
//      "x...gx....g...x....x" + // 9
//      "xxxxxxxxxxxxxxxxxxxx";// 10

    while (maze.length() < (this.nbHorzCells * this.nbVertCells)) {
      maze += 'b';
    }
    // remove new line characters from the controllers grid to string method.
    maze = maze.replace("\n", "");
    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        char value = maze.charAt(nbHorzCells * i + k);
        mazeArray[i][k] = value;
      }
    }
  }
  

  public char getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private int toInt(char c)
  {
    if (c == 'x')
      return 0;
    if (c == '.')
      return 1;
    if (c == ' ')
      return 2;
    if (c == 'g')
      return 3;
    if (c == 'i')
      return 4;
    return -1;
  }

  public int getNumHorzCells() {
    return this.nbHorzCells;
  }
  public int getNumVertCells() {
    return this.nbVertCells;
  }
}
