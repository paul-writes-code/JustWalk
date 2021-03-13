package Items;

import javax.swing.ImageIcon;

//every item in the game has a name, an icon, a currency value for which it can be traded to a shopkeeper,
//and a 'type" which corresponds to its identity as, for example, a helmet, boots, etc
public class Item{
	
	protected String name;
	protected ImageIcon icon;
	protected int value;
	protected int type;
	
	/*
	 * KEY FOR TYPE
	 * 1 = Helmet
	 * 2 = Chestplate
	 * 3 = Leggings
	 * 4 = Boots
	 * 5 = Weapon
	 * 0 = Other
	 */
	public Item(int value){
		this.value = value;
	}
	public ImageIcon getIcon(){
		return icon;
	}
	public String getName(){
		return name;
	}
	public int getType(){
		return type;
	}
	public int getValue(){
		return value;
	}
	
}
