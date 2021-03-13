package NPC;

import java.awt.Graphics;
import javax.swing.JFrame;

import Items.*;
import Main.Game;
import Main.Get;
import Windows.*;

public abstract class NPC extends Thread {

	private int x, y;
	private boolean tradable, isAlive = true, inCombat = false, visible = false;
	private int strength, defence, attack, level, currentHealth, maxHealth;
	private CombWindow combWin;
	private Item drop;

	//NPCS have a position, a level, and some have the ability to trade (for now, just shopkeepers)
	//each NPC has a chance, determined by random numbers, to have an item that they will drop once defeated
	public NPC(int x, int y, boolean tradable, int level) {
		this.x = x;
		this.y = y;
		this.tradable = tradable;
		drop = ranItem();		
		this.level = level;
		strength = level * 2 + 3;
		attack = level * 2 + 3;
		defence = level * 2 + 3;
		maxHealth = 10 * level;
		currentHealth = maxHealth;
		combWin = new CombWindow(Game.frame.getWidth(), Game.frame.getHeight(),this);
	}

	//draws the NPC on the main frame
	public void drawNpc(Graphics g, JFrame frame) {
		g.fillOval(x * Game.BLOCK_SIDE_LENGTH, y * Game.BLOCK_SIDE_LENGTH,
				Game.BLOCK_SIDE_LENGTH, Game.BLOCK_SIDE_LENGTH);
	}

	//Returns a random item
	public Item ranItem() {
		Item i = null;
		int x;
		x = Get.random(4, 0);

		switch (x) {
		case 1:
			if (this instanceof Boss){
				i = new Helmet(4);
			} else{
				i = new Helmet(Get.random(3, 1));
			}		
			break;
		case 2:
			if (this instanceof Boss){
				i = new Chestplate(4);
			} else{
				i = new Chestplate(Get.random(3, 1));
			}		
			break;
		case 3:
			if (this instanceof Boss){
				i = new Leggings(4);
			} else{
				i = new Leggings(Get.random(3, 1));
			}			
			break;
		case 4:
			if (this instanceof Boss){
				i = new Boots(4);
			} else{
				i = new Boots(Get.random(3, 1));
			}
			
			break;
		}
		return i;

	}

	//NPC AI Thread
	public void run() {
		int i;
		while (isAlive) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//this stops them from moving once they are in combat or they are invisible (when the player is on a different level)
			if (!inCombat && visible) {

				i = Get.random(1000, 1);
				if (i == 1) {

					if (y > 0 && Game.getTiles()[x][y - 1].getNpcWalkable()
							&& !Game.getTiles()[x][y - 1].checkOccupied()) {
						Game.getTiles()[x][y].setUnoccupied();
						Game.getTiles()[x][y - 1].setOccupied();
						y--;
					}

				} else if (i == 2) {

					if (y < Game.getBlockRows() - 1
							&& Game.getTiles()[x][y + 1].getNpcWalkable()
							&& !Game.getTiles()[x][y + 1].checkOccupied()) {
						Game.getTiles()[x][y].setUnoccupied();
						Game.getTiles()[x][y + 1].setOccupied();
						y++;
					}

				} else if (i == 3) {

					if (x > 0 && Game.getTiles()[x - 1][y].getNpcWalkable()
							&& !Game.getTiles()[x - 1][y].checkOccupied()) {
						Game.getTiles()[x][y].setUnoccupied();
						Game.getTiles()[x - 1][y].setOccupied();
						x--;
					}

				} else if (i == 4) {

					if (x < Game.getBlockColumns() - 1
							&& Game.getTiles()[x + 1][y].getNpcWalkable()
							&& !Game.getTiles()[x + 1][y].checkOccupied()) {
						Game.getTiles()[x][y].setUnoccupied();
						Game.getTiles()[x + 1][y].setOccupied();
						x++;
					}
				}
			}
		}
	}

	//returns whether or not the NPC is alive
	public boolean getIsAlive() {
		return isAlive;
	}

	//returns their position coordinates
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	//returns their attributes
	public int getStrength() {
		return strength;
	}

	public int getDefence() {
		return defence;
	}

	public int getAttack() {
		return attack;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public int getLevel() {
		return level;
	}
	
	//kills the NPC
	public void death() {
		Game.drop(drop, x, y);
		isAlive = false;
		visible = false;
	}

	//returns the damage that the NPC outputs on one particular attack
	public int hit() {
		int i;
		i = ((int) (Get.random((2 * strength + attack), 0))) / 5;
		if (i > 0) {
			Game.sound.hit();
		} else {
			Game.sound.hit();
		}
		return i;
	}

	//possibility of the player's hit to be lowered based on the NPC's defence
	public void takeHit(int hit) {
		currentHealth -= Get.random(hit, (int) ((hit - 0.1 * defence)));
	}

	//returns whether or not the NPC is in combat
	public boolean getInCombat() {
		return inCombat;
	}

	//puts the NPC in combat
	public void enterCombat() {
		inCombat = true;
		combWin.update();
		combWin.open();
	}

	//removes the NPC from combat
	public void exitCombat() {
		inCombat = false;
		combWin.close();
	}

	//methods for visibility of NPC. NPCs are only invisible when they exist on a different level than the player is on 
	//(ie NPCs on level 2 are invisible when the player is on level 1 or 3)
	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void getCombWindow() {
		combWin.update();
	}

	public boolean isTradable() {
		return tradable;
	}
}
