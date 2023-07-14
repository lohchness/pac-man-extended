package src.GameItems;

public enum ItemType {
	Pill,
	Ice,
	Gold,
	PortalWhiteTile,
	PortalYellowTile,
	PortalDarkGoldTile,
	PortalDarkGrayTile;

	public String getImageName() {
		switch (this) {
			case Gold: return "sprites/gold.png";
			case Ice: return "sprites/ice.png";
			case Pill: return null;
			case PortalWhiteTile: return "data/i_portalWhiteTile.png";
			case PortalYellowTile: return "data/j_portalYellowTile.png";
			case PortalDarkGoldTile:return "data/k_portalDarkGoldTile.png";
			case PortalDarkGrayTile: return "data/l_portalDarkGrayTile.png";
			default: {
				assert false;
			}
		}
		return null;
	}
	public String getTypeName(){
		switch (this) {
			case Gold: return "gold";
			case Ice: return "ice";
			case Pill: return "pills";
			case PortalWhiteTile: return "white portal";
			case PortalYellowTile: return "yellow portal";
			case PortalDarkGoldTile:return "dark gold portal";
			case PortalDarkGrayTile: return "dark gray portal";
			default: {
				assert false;
			}
		}
		return null;
	}

}
