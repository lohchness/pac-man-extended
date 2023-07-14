package src.Checker;

import java.io.File;

/**
 * Class checks validity of Folder
 */
public class GameFolderCheck implements LevelRequirement {
	private String name;
	private final String description = "Check validity of game folder";
	
	public GameFolderCheck(String name) {this.name = name;}
	
	// input is folder
	public boolean isValidLevel(String folderPath) {
		File directory = new File(folderPath);
		assert directory.isDirectory();
		if (!directory.exists()) return false;
		if ((directory.list().length) <= 0) return false;
		
		boolean hasXML = false;
		for (File file : directory.listFiles()) {
			String name = file.getName();
			int lastIndex = name.lastIndexOf(".");
			if (lastIndex == -1) continue; // no extension
			if (name.substring(lastIndex).equals(".xml")) {
				hasXML = true;
				break;
			}
		}
		
		return hasXML;
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
