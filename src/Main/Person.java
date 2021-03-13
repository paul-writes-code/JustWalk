package Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import Items.*;
import NPC.NPC;

public class Person{

	//player member variables
	private int x,y;
	private String name;
	private int xp, strength, attack, defence, level, skillPoints, currentHealth, maxHealth, gold, mapLevel, maxLevel=20;
	private Chestplate curChest;
	private Helmet curHelmet;
	private Leggings curLegs;
	private Boots curBoots;
	private Item[] inventory;
	private TimerEvent timerEvent;
	private NPC npc;
	private boolean inCombat=false;
	
	Timer timer;
	
	/*
	 * Initialize player to default
	 */
	public Person(int x, int y, String name){
		this.x=x;
		this.y=y;
		this.name = name;
		xp = 10;
		level = 0;
		strength = 4;
		attack = 4;
		defence = 4;
		skillPoints = 0;
		curHelmet = new Helmet(1);
		curChest = new Chestplate(1);
		curLegs = new Leggings(1);
		curBoots = new Boots(1);
		maxHealth = level*10;
		currentHealth=maxHealth;
		gold = 100;
		inventory = new Item[30];
		
		//mapLevel refers to the player's "level" in terms of which level they are on (1,2,3) which is different than their actual combat level
		mapLevel=1;
		for (int i =0; i<30; i++){
			inventory[i]=null;
		}	
	}
	
	public void drawPerson(Graphics g, JFrame frame){
		g.fillOval(x*Game.BLOCK_SIDE_LENGTH,y*Game.BLOCK_SIDE_LENGTH,Game.BLOCK_SIDE_LENGTH,Game.BLOCK_SIDE_LENGTH);
	}
	
	//determines whether or not an item can be added to the players inventory
	public boolean canAdd(){
		int i = 0;
		while (inventory[i] != null && i<30){
			i++;
		}
		if (i >= 30){
			return false;
		}else{
			return true;
		}
	}
	
	//adds an item to the inventory
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
	
	//returns an item at a given index
	public Item itemAt(int i){
		try{
			return inventory[i];
		} catch (java.lang.ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}	
	
	//changes the players position
	public void setX(int newX){
		x=newX;
	}
	
	public void setY(int newY){
		y=newY;
	}
	
	//moves the player to a new level
	public void changeMapLevel(int change){
		mapLevel+=change;
	}
	
	//removes an item from the inventory
	public void removeItem(int i){
		inventory[i] = null;
	}
	
	public int getMapLevel(){
		return mapLevel;
	}
	
	public Item[] getInv(){
		return inventory;
	}
	
	public boolean getInCombat(){
		return inCombat;
	}
	
	//puts the player in combat with a particular NPC
	public void combat(NPC npc){
		
		this.npc=npc;
		timerEvent=new TimerEvent(500);
		
		npc.enterCombat();
		
		timer=new Timer(500, timerEvent);		
		timer.start();	
		
		checkLevel();	
	}
	
	//generates an amount of damager that the player deals to the enemy
	public int hit (){	
		int hit;
		hit = ((int)(Get.random((2*strength+attack),0)))/5;
		checkLevel();
		xp += hit/3;
		if (hit>0){
			Game.sound.hit();
		}else{
			Game.sound.miss();
		}
		return hit;
	}
	
	//when the player is victorious in combat
	public void victory(){
		xp+=npc.getLevel()*12;
		checkLevel();
		gold+=npc.getLevel()*Get.random(13, 7);
	}
	
	//possibility for NPCs damage to be reduced
	public void takeHit(int hit){
		
		double reduceFactor=0;
		
		if (curHelmet != null){
			reduceFactor+=curHelmet.getTier();
		} if (curChest != null){
			reduceFactor+=curChest.getTier();
		} if(curLegs != null){
			reduceFactor+=curLegs.getTier();
		} if (curBoots != null){
			reduceFactor+=curBoots.getTier();
		}
		
		reduceFactor/=40;
		
		currentHealth-=Get.random(hit, (int)((hit-0.1*defence)*(1-reduceFactor)));
	}
	
	//when the player is defeated
	public void death(){
		Game.stopMusic();
		JFrame death = new JFrame();
		JLabel image = new JLabel();
		ImageIcon i = new ImageIcon("Game_Over.png");
		Clip gameOverSound = null;
		AudioInputStream stream;
		
		death.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		death.add(image);
		image.setIcon(i);
		
		death.pack();
		death.setLocation(Game.getFrame().getWidth()/2-death.getWidth()/2,Game.getFrame().getHeight()/2-death.getHeight()/2);
		death.setVisible(true);
		try {
			stream = AudioSystem.getAudioInputStream(new File("Sounds/game_over.wav"));
			gameOverSound = AudioSystem.getClip();
			gameOverSound.open(stream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		gameOverSound.start();
		
		Menu.close();
	}
	
	/*
	 * Returns level and checks if player leveled up
	 */
	public int checkLevel(){
		do{
			if(level<=(int) (Math.log(xp/10)/Math.log(2))){
				levelUp();
			}
		}while(level<=(int) (Math.log(xp/10)/Math.log(2)));
		return level;
	}
	
	public int xpForLevel(){
		return (int) (10*Math.pow(2, level));
	}
	public int xpForPrev(){
		return (int) (10*Math.pow(2, level-1));
	}
	
	public int getXp(){
		return xp;
	}
	
	/*
	 * Levels up player!
	 * To Do:
	 * 		Add Level up celebration!
	 */
	private void levelUp(){		
			skillPoints+=5;
			level++;
			maxHealth = level*10;
			currentHealth=maxHealth;
			
			if (strength<maxLevel){
				strength++;
			}
			
			if (defence<maxLevel){
				defence++;
			}
			
			if (attack<maxLevel){
				attack++;
			}
			
			Game.sound.levelUp();	
	}
	
	/*
	 * Returns SP (Skill Points)
	 */
	public int getSP(){
		return skillPoints;
	}
	
	/*
	 * Upgrades Skills by 1 level and decreases SP (Skill Points) by 1
	 */
	public void strengthUp (){
		if (strength<maxLevel){
			strength++;
			skillPoints--;
		}	
	}
	public void defenceUp (){
		if (defence<maxLevel){
			defence++;
			skillPoints--;
		}	
	}
	public void attackUp (){
		if (attack<maxLevel){
			attack++;
			skillPoints--;
		}	
	}
		
	//returns various attributes
	public String getName(){
		return name;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getCurrentHealth(){
		return currentHealth;
	}
	
	public int getStrength(){
		return strength;
	}
	
	public int getDefence(){
		return defence;
	}
	
	public int getAttack(){
		return attack;
	}	
	
	public Helmet getHelmet(){
		return curHelmet;
	}
	
	public Chestplate getChest(){
		return curChest;
	}
	
	public Leggings getLegs(){
		return curLegs;
	}
	
	public Boots getBoots(){
		return curBoots;
	}
	
	//equips armour
	public void addHelmet(Helmet i){
		curHelmet = i;
	}
	public void addChest(Chestplate i){
		curChest = i;
	}
	public void addLegs(Leggings i){
		curLegs = i;
	}
	public void addBoots(Boots i){
		curBoots = i;
	}
	
	//removes armour
	public void removeHelmet(){		
		add(curHelmet);
		curHelmet = null;
	}
	public void removeChest(){
		add(curChest);
		curChest = null;
	}
	public void removeLegs(){
		add(curLegs);
		curLegs = null;
	}
	public void removeBoots(){
		add(curBoots);
		curBoots = null;
	}
	
	public int getGold(){
		return gold;
	}
	
	public void changeGold(int goldChange){
		gold+=goldChange;
	}
	
	//methods for player movement
	public void moveUp(){
		y--;
	}
	public void moveLeft(){
		x--;
	}
	public void moveDown(){
		y++;
	}
	public void moveRight(){
		x++;
	}

	//a Timer Class for combat
	public class TimerEvent implements ActionListener{
		
		int delay;
		boolean playerTurn=true;
		
		public TimerEvent(int delay){
			this.delay=delay;
			inCombat=true;
		}
		
		public void actionPerformed(ActionEvent tc){

			if (npc.getCurrentHealth()>0&&currentHealth>0){
				
				if (playerTurn){
					npc.takeHit(hit());
					npc.getCombWindow();
					playerTurn=false;
				} else{
					takeHit(npc.hit());
					npc.getCombWindow();
					playerTurn=true;
				}
			
			} else{
				
				if (currentHealth>0){
					victory();
					npc.death();
				} else{
					death();
				}
				
				npc.exitCombat();
				timer.stop();
				inCombat=false;
			}
		}
	}
}
