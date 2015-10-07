package awktal.mule;

public class Mule {

    private MuleType type;
    private Player owner;
    private Tile tile;

    public Mule() {
        this.type = MuleType.NONE;
        this.owner = null; // store
        this.tile = null; // none
    }

    public Mule(MuleType type, Player owner) {
        this.type = type;
        this.owner = owner;
        this.tile = tile;
    }

    public void outfit(MuleType type) {
        this.type = type;
    }

    public MuleType getType() {
        return this.type;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}