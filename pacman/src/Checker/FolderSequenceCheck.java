package src.Checker;

import src.GameManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class checks validity of Folder
 */
public class FolderSequenceCheck implements LevelRequirement {
	private String name;
	private final String description = "Check validity of game folder";
	
	public FolderSequenceCheck(String name) {this.name = name;}
	
	// input is folder
	public boolean isValidLevel(String folderPath) {
		
		Map<Integer, List<String >> startints = getStartingInts(folderPath, ".xml");
		
		for (List<String> fileList : startints.values()) {
			if (fileList.size() > 1) {
				StringBuilder files = new StringBuilder();
				for (String filename : fileList) {
					files.append(" ").append(filename).append(";");
				}
				GameManager.getInstance().getGameCallback().writeString(
						"[Game " + folderPath + " - multiple maps at same level:" + files.toString() + "]"
				);
				return false;
			}
		}
		return true;
	}
	
	private Map<Integer, List<String>> getStartingInts(String directory, String ext) {
		Map<Integer, List<String>> startints = new HashMap<>();
		File dir = new File(directory);
		
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.isFile() || !file.getName().endsWith(ext)) continue;
			String fileName = file.getName();
			
			int start = getStartingInt(fileName);
			
			if (start != 0) {
				startints.computeIfAbsent(start, k -> new ArrayList<>()).add(fileName);
			}
		}
		return startints;
	}
	
	private int getStartingInt(String filename) {
		int index = 0;
		StringBuilder startint = new StringBuilder();
		while(index < filename.length() && Character.isDigit(filename.charAt(index))) {
			startint.append(filename.charAt(index));
			index++;
		}
		
		if (startint.length() > 0) {
			return Integer.parseInt(startint.toString());
		}
		return 0;
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
