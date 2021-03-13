package Items;

import javax.swing.ImageIcon;

//subclass of armour
public class Chestplate extends Armour{

	//gets a tier, icon, name and type
	//the type corresponds to its identity as a helmet, chestplate, leggings or boots
	public Chestplate(int tier){
		super (tier);
		icon = new ImageIcon ("Chest.png");
		name = "Tier "+tier+" Chestplate";
		type = 2;
	}
}
