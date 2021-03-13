package NPC;

import Windows.TradeWindow;
import Items.*;
import Main.Game;
import Main.Get;

//subclass of NPCs
public class Shopkeeper extends NPC{
	
	//each shopkeeper has their own inventory of items they sell and can also buy items
	private Item[] inventory;
	
	//each shopkeeper has their own trade window to allow for different shopkeepers to have different items
	private TradeWindow tradeWin;
	
	//has a position with coordinates and a level
	//the "false" is the parameter for the ability of the NPC to trade
	public Shopkeeper(int x, int y){
		super(x,y,true,1000);
		
		inventory=new Item[Game.getInventorySize()];	
		
		//random numbers determine the items that the shopkeeper initially offers
		int random;
		
		for (int i=0;i<6;i++){
			
			random=Get.random(4,1);
			
			if (random==1){
				inventory[i]=new Helmet(Get.random(3,1));
			} else if (random==2){
				inventory[i]=new Chestplate(Get.random(3,1));
			} else if (random==3){
				inventory[i]=new Leggings(Get.random(3,1));
			} else if (random==4){
				inventory[i]=new Boots(Get.random(3,1));
			}
		}
		
		tradeWin = new TradeWindow(Game.frame.getWidth(),Game.frame.getHeight(),this);
	}
	
	//opens the trade window
	public void trade(){
		tradeWin.open();
	}
	
	//returns the shopkeepers inventory
	public Item[] getItems(){
		return inventory;
	}
	
	//returns an item at a specific index
	public Item itemAt(int i){
		try{
			return inventory[i];
		} catch (java.lang.ArrayIndexOutOfBoundsException e){
			return null;
		}		
	}	
	
	//adds an item to the shopkeepers inventory
	public void add (Item item){
		int i =0;
		while (inventory[i] != null && i<30){
			i++;
		}
		if (i >= 30){
			System.err.println("Inventory Full");
		}else{
			inventory[i] = item;
		}
	}
	
	//removes an item from the shopkeepers inventory
	public void removeItem(int i){
		inventory[i] = null;
	}
}
