package Tiles;
import java.awt.Image;

import Items.Item;

//abstract superclass
public abstract class Tile {
	
	//some tiles are only walkable by players (doors), and some are not walkable at all (walls) and some are occupied by a player or an npc
	//each tile has a texture, a position, and potential for an item on it (when an NPC is killed)
	protected boolean playerWalkable, npcWalkable, isOccupied;
	protected Image texture;
	protected int x,y;
	protected Item itemOnGround = null;

	public Tile(int x, int y, boolean playerWalkable, boolean npcWalkable) {
		this.playerWalkable= playerWalkable;
		this.npcWalkable=npcWalkable;
		this.x = x;
		this.y = y;
		isOccupied=false;
	}

	public boolean getIsWalkable(){
		return playerWalkable;
	}
	
	public boolean getNpcWalkable(){
		return npcWalkable;
	}
	public void itemDropped(Item i){
		itemOnGround = i;
	}
	public Item itemPickedUp(){
		Item i;
		i = itemOnGround;
		itemOnGround = null;
		return i;
	}
	public boolean isItem(){
		if (itemOnGround != null){
			return true;
		}else{
			return false;
		}
	}
	
	//abstract methods for when a tile is walked on, walked towards and for the texture of each tile
	public abstract void steppedOn();
	public abstract void walkedTowards();
	public abstract Image getTexture();
	
	public boolean checkOccupied(){
		return isOccupied;
	}
	
	public void setOccupied(){
		isOccupied=true;
	}
	
	public void setUnoccupied(){
		isOccupied=false;
	}
}
