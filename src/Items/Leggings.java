package Items;

import javax.swing.ImageIcon;

//subclass of armour
public class Leggings extends Armour {

	//gets a tier, icon, name and type
	//the type corresponds to its identity as a helmet, chestplate, leggings or boots
	public Leggings(int tier){
		super (tier);
		icon = new ImageIcon ("Legs.png");
		name = "Tier "+tier+" Leggings";
		type = 3;
	}	
}

