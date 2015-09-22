package awktal.mule;

public enum TileType {

	PLAIN ("map_pictures/grass.png"),
  	BUILDING ("map_pictures/building.png"),
  	MOUNTAIN1 ("map_pictures/mount1.png"),
  	MOUNTAIN2 ("map_pictures/mount2.png"),
  	MOUNTAIN3 ("map_pictures/mount3.png"),
  	RIVER ("map_pictures/water.png");

  	private String path;
	
	private TileType(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}