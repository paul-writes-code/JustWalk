package Items;

import javax.swing.ImageIcon;

//subclass of armour
public class Boots extends Armour{
	
	//gets a tier, icon, name and type
	//the type corresponds to its identity as a helmet, chestplate, leggings or boots
	public Boots(int tier){
		super (tier);
		icon = new ImageIcon ("Boots.png");
		name = "Tier "+tier+" Boots";
		type = 4;
	}
}

