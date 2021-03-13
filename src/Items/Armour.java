package Items;

/*
 * Super class for all armour objects
 */

public abstract class Armour extends Item{
	
	private int tier;
	
	//gives the armour object a currency value of 50 gold per tier
	public Armour(int tier){
		super(tier*50);
	}
	
	public int getTier(){
		return tier;
	}
}
