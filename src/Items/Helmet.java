package Items;

import javax.swing.ImageIcon;

//subclass of armour
public class Helmet extends Armour{
	
	//gets a tier, icon, name and type
	//the type corresponds to its identity as a helmet, chestplate, leggings or boots
	public Helmet(int tier){
		super (tier);
		icon = new ImageIcon ("Helm.png");
		name = "Tier "+tier+" Helmet";
		type = 1;
	}
}

