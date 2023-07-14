package src.mapeditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.Checker.CompositeChecker;
import src.Checker.LevelRequirementFactory;
import src.Game;
import src.mapeditor.grid.Camera;
import src.mapeditor.grid.Grid;
import src.mapeditor.grid.GridCamera;
import src.mapeditor.grid.GridModel;
import src.mapeditor.grid.GridView;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import src.utility.PropertiesLoader;

/**
 * Controller of the application.
 * 
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 * 
 */
public class Controller implements ActionListener, GUIInformation {

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	private Tile selectedTile;
	private Camera camera;

	private List<Tile> tiles;

	private GridView grid;
	private View view;

	private boolean autoPlayNextGame = false;

	private int gridWith = Constants.MAP_WIDTH;
	private int gridHeight = Constants.MAP_HEIGHT;

	private static int widthForChecker = Constants.MAP_WIDTH;
	private static int heightForChecker = Constants.MAP_HEIGHT;

	private CompositeChecker levelChecker;

	/**
	 * Construct the controller.
	 */
	public Controller() {
		init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);

		this.levelChecker = new CompositeChecker();
		addLevelChecks(this.levelChecker);
	}
	
	public Controller(File file) {
		this.levelChecker = new CompositeChecker();
		addLevelChecks(this.levelChecker);

		assert file.isFile();
		init(Constants.MAP_WIDTH, Constants.MAP_HEIGHT);
		
		try {
			Document document = (Document) new SAXBuilder().build(file);
			loadXML(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		grid.redrawGrid();
	}

	public void init(int width, int height) {
		this.tiles = TileManager.getTilesFromFolder("pacman/data/");
		this.model = new GridModel(width, height, tiles.get(0).getCharacter());
		this.camera = new GridCamera(model, Constants.GRID_WIDTH,
				Constants.GRID_HEIGHT);

		grid = new GridView(this, camera, tiles); // Every tile is
													// 30x30 pixels

		this.view = new View(this, camera, grid, tiles);
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(
					Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
			// view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();
		} else if (e.getActionCommand().equals("load")) {
			loadFile();

		} else if (e.getActionCommand().equals("update")) {
			updateGrid(gridWith, gridHeight);
		}
		else if (e.getActionCommand().equals("Start_game")) {

			//view.close();
			SwingWorker worker = new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					String stringGrid = model.getMapAsString();
					String propertiesPath = "properties/test.properties";
					Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
					Game game = new Game(Constants.MAP_WIDTH, Constants.MAP_HEIGHT, stringGrid, properties, autoPlayNextGame);
					autoPlayNextGame = false;
					System.out.println("Auto play is off");
					game.stopGameThread();
					game.setVisible(false);
					return null;
				}
			};
			worker.execute();
		}
		else if (e.getActionCommand().equals("Auto_play")) {
			System.out.println("Auto play is on");
			autoPlayNextGame = true;
			/// DO AUTO PLAY STUFF
			// PacActor moveInAutoMode()
		}
	}

	public void updateGrid(int width, int height) {
		view.close();
		init(width, height);
		view.setSize(width, height);
	}

	DocumentListener updateSizeFields = new DocumentListener() {

		public void changedUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void removeUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}

		public void insertUpdate(DocumentEvent e) {
			gridWith = view.getWidth();
			gridHeight = view.getHeight();
		}
	};

	private void saveFile() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"xml files", "xml");
		chooser.setFileFilter(filter);
		File workingDirectory = new File(System.getProperty("user.dir"));
		chooser.setCurrentDirectory(workingDirectory);

		int returnVal = chooser.showSaveDialog(null);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				Element level = new Element("level");
				Document doc = new Document(level);
				doc.setRootElement(level);

				Element size = new Element("size");
				int height = model.getHeight();
				int width = model.getWidth();
				size.addContent(new Element("width").setText(width + ""));
				size.addContent(new Element("height").setText(height + ""));
				doc.getRootElement().addContent(size);

				for (int y = 0; y < height; y++) {
					Element row = new Element("row");
					for (int x = 0; x < width; x++) {
						char tileChar = model.getTile(x,y);
						String type = "PathTile";

						if (tileChar == 'b')
							type = "WallTile";
						else if (tileChar == 'c')
							type = "PillTile";
						else if (tileChar == 'd')
							type = "GoldTile";
						else if (tileChar == 'e')
							type = "IceTile";
						else if (tileChar == 'f')
							type = "PacTile";
						else if (tileChar == 'g')
							type = "TrollTile";
						else if (tileChar == 'h')
							type = "TX5Tile";
						else if (tileChar == 'i')
							type = "PortalWhiteTile";
						else if (tileChar == 'j')
							type = "PortalYellowTile";
						else if (tileChar == 'k')
							type = "PortalDarkGoldTile";
						else if (tileChar == 'l')
							type = "PortalDarkGrayTile";

						Element e = new Element("cell");
						row.addContent(e.setText(type));
					}
					doc.getRootElement().addContent(row);
				}
				XMLOutputter xmlOutput = new XMLOutputter();
				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput
						.output(doc, new FileWriter(chooser.getSelectedFile()));
			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file!", "error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
		}
	}

	public void loadFile() {
		SAXBuilder builder = new SAXBuilder();
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;
			File workingDirectory = new File(System.getProperty("user.dir"));
			chooser.setCurrentDirectory(workingDirectory);
			Document document;

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {
					document = (Document) builder.build(selectedFile);
					loadXML(document);

					String mapString = model.getMapAsString();
					grid.redrawGrid();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadXML(Document document) {
		Element rootNode = document.getRootElement();
		
		List sizeList = rootNode.getChildren("size");
		Element sizeElem = (Element) sizeList.get(0);
		int height = Integer.parseInt(sizeElem
				.getChildText("height"));
		int width = Integer
				.parseInt(sizeElem.getChildText("width"));
		updateGrid(width, height);
		
		List rows = rootNode.getChildren("row");
		for (int y = 0; y < rows.size(); y++) {
			Element cellsElem = (Element) rows.get(y);
			List cells = cellsElem.getChildren("cell");
			
			for (int x = 0; x < cells.size(); x++) {
				Element cell = (Element) cells.get(x);
				String cellValue = cell.getText();
				
				char tileNr = switch (cellValue) {
					case "PathTile" -> 'a';
					case "WallTile" -> 'b';
					case "PillTile" -> 'c';
					case "GoldTile" -> 'd';
					case "IceTile" -> 'e';
					case "PacTile" -> 'f';
					case "TrollTile" -> 'g';
					case "TX5Tile" -> 'h';
					case "PortalWhiteTile" -> 'i';
					case "PortalYellowTile" -> 'j';
					case "PortalDarkGoldTile" -> 'k';
					case "PortalDarkGrayTile" -> 'l';
					default -> '0';
				};
				
				model.setTile(x, y, tileNr);
			}
		}
	}

	private void addLevelChecks(CompositeChecker Checker) {
		LevelRequirementFactory levelCheckFactory = LevelRequirementFactory.getInstance();

		List<String> levelRequirements = new ArrayList<>();
		levelRequirements.add("PacMan");
		levelRequirements.add("GoldnPillInLevel");
		levelRequirements.add("PortalPair");
		levelRequirements.add("GoldnPillAccessible");
		// add more level checking here //

		for(String levelRequirement: levelRequirements) {
			Checker.add(levelCheckFactory.makeLevelCheck(levelRequirement));
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}

	public static int getWidthForChecker() {
		return widthForChecker;
	}

	public static int getHeightForChecker() {
		return heightForChecker;
	}
}
