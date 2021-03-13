package NPC;

//subclass of NPCs
public class Boss extends NPC {

	//has a position with coordinates and a level
	//the "false" is the parameter for the ability of the NPC to trade
	public Boss(int x, int y, int level) {
		super(x, y, false, level);		
	}
}
