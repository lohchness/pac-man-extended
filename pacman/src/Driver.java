package src;

import src.Checker.LevelRequirement;
import src.Checker.LevelRequirementFactory;
import src.mapeditor.editor.Controller;
import src.utility.PropertiesLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test.properties";
    public static final String DEFAULT_SAMPLE_MAP = "pacman/samplemaps/sample_map1.xml";
    public static final String DEFAULT_MAPS = "pacman/samplemaps/";
    private static final String FOLDER_CHECK = "GameFolder";
    private static final String SEQUENCE_CHECK = "FolderSequence";
    
    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        /**
         * CHANGE THE PATH BELOW FOR CHANGING BETWEEN EDIT MODE AND TEST MODE
         */
        String path = DEFAULT_MAPS; // Playing multiple levels
        //String path = DEFAULT_SAMPLE_MAP; // Editing a level
        
        if (args.length > 0) {
            path = args[0];
        }

        File file = new File(path);

        if (file.exists() && file.isFile()) {
            new Controller(file);
            // controller load map from given .xml file
        }
        else {
            assert file.exists();
            assert file.isDirectory();

            ArrayList<XMLParser> levels = new ArrayList<>();
            int width = 0;
            int height = 0;

            Properties properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);
            boolean isAuto = properties.getProperty("PacMan.isAuto").equals("true");
    
            // no maps found
            LevelRequirementFactory levelRequirementFactory = LevelRequirementFactory.getInstance();
            LevelRequirement requirement = levelRequirementFactory.makeLevelCheck(FOLDER_CHECK);
            if(!requirement.isValidLevel(file.toString())) {
                GameManager.getInstance().getGameCallback().writeString("[Game " + file.toString() + " - no maps found]");
                return;
            }
            // sequence checking
            requirement = levelRequirementFactory.makeLevelCheck(SEQUENCE_CHECK);
            if(!requirement.isValidLevel(file.toString())) {
                return;
            }
            
            File[] files = file.listFiles();
            Arrays.sort(files);
            
            for (File xml : files) {
                XMLParser parsed = new XMLParser(xml);
                levels.add(parsed);
                if (width == 0) width = parsed.getWidth();
                if (height == 0) height = parsed.getHeight();
            }
            Game g = new Game(width, height, properties, levels, isAuto);
            g.setVisible(false);
            new Controller();

//            }
        }
    }
}
