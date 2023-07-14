package src;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import src.mapeditor.editor.Constants;

import java.io.File;
import java.util.List;

public class XMLParser {
	private final int WIDTH = 0;
	private final int HEIGHT = 1;
	
	private File file;
	int width, height;
	String maze;
	
	public XMLParser(File file) {
		this.file = file;
		width = getSize(WIDTH);
		height = getSize(HEIGHT);
		maze = loadXML();
	}
	
	/*
        Get height or width from the given XML.
   */
	private int getSize(int axis) {
		int length = 0;
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(file);
			Element root = document.getRootElement();
			Element size = root.getChild("size");
			Element len;
			if (axis == WIDTH) {
				len = size.getChild("width");
				length = Integer.parseInt(len.getTextTrim());
			}
			else if (axis == HEIGHT) {
				len = size.getChild("height");
				length = Integer.parseInt(len.getTextTrim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}
	
	
	private String loadXML() {
		StringBuilder output = new StringBuilder();
		
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(file);
			Element root = document.getRootElement();
			
			List<Element> rows = root.getChildren("row" );
			for (Element row : rows) {
				List<Element> cells = row.getChildren("cell");
				for (Element cell : cells) {
					String tileName = cell.getText();
					
					switch (tileName) {
						case "PathTile" -> output.append(Constants.PATH);
						case "WallTile" -> output.append(Constants.WALL);
						case "PillTile" -> output.append(Constants.PILL);
						case "PortalWhiteTile" -> output.append(Constants.PORTALWHITE);
						case "PortalYellowTile" -> output.append(Constants.PORTALYELLOW);
						case "PortalDarkGrayTile" -> output.append(Constants.PORTALGRAY);
						case "PortalDarkGoldTile" -> output.append(Constants.PORTALGOLD);
						case "TX5Tile" -> output.append(Constants.TX5);
						case "TrollTile" -> output.append(Constants.TROLL);
						case "PacTile" -> output.append(Constants.PAC);
						case "IceTile" -> output.append(Constants.ICE);
						case "GoldTile" -> output.append(Constants.GOLD);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output.toString();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getMaze() {
		return maze;
	}
}
