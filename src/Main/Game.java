package Main;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import Items.Item;
import NPC.Boss;
import NPC.Enemy;
import NPC.NPC;
import NPC.Shopkeeper;
import Tiles.*;
import Windows.*;


import java.awt.Image;

public class Game{
	
	//height of the taskbar, in pixels
	protected final int TASKBAR_HEIGHT=85;
	
	//side length of each individual tile (wall, floor, character, etc.)
	public static int BLOCK_SIDE_LENGTH;
	
	protected final static int BLOCK_COLUMNS=66;
	protected final static int BLOCK_ROWS=38;
	protected final static int INVENTORY_COLUMNS=6;
	protected final static int INVENTORY_ROWS=5;
	
	private static Tile[][] tiles;
	
	public static JFrame frame;
	private static Graphics g;	
	private DrawThing d;	
	private static Person player;
	private static InvWindow inv;
	private CharWindow charWin;
	private LevelWindow lvlWin;
	
	//dimension 1 is the level that the NPCs are on, and dimension 2 is the NPCs in that level
	private static NPC[][] npcs;
	private static BufferedImage backgroundImage;
	static Image dropImg = null;
	public static Sounds sound;
	private static Clip background;
	
	private boolean initializing = true;
	
	/*
	 * Initializes the game
	 * 	frames
	 * 	tiles
	 * 	windows
	 * 	sound
	 */
	
	//builds the game
	public Game(){
		//Main Frame
		frame=new JFrame("Just Walk.");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyboardInput(g));
		d = new DrawThing();
		frame.add(d);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		player = new Person(8,23,"Stan");
		
		//Initializes all the sound effects
		try {
			sound = new Sounds();
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e2) {
			e2.printStackTrace();
		}
		
		//Create all the player windows
		inv = new InvWindow(frame.getWidth(),frame.getHeight());
		charWin = new CharWindow(frame.getWidth(),frame.getHeight());
		lvlWin = new LevelWindow(frame.getWidth(),frame.getHeight());
		
		//Initializes and starts background music
		AudioInputStream stream;
		try {
			stream = AudioSystem.getAudioInputStream(new File("Sounds/TFM.wav"));
			background = AudioSystem.getClip();
			background.open(stream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
		background.loop(10);
		background.start();
		
		//Initializes level tiles and npcs
		BLOCK_SIDE_LENGTH=frame.getWidth()/BLOCK_COLUMNS;
		
		tiles=new Tile[BLOCK_COLUMNS][BLOCK_ROWS];
	
		npcs=new NPC[Level.getNumLevels()+1][];
		
		npcs[1]=new NPC[4];
		npcs[2]=new NPC[5];
		npcs[3]=new NPC[1];
				
		npcs[1][0]=new Shopkeeper(7,5);
		npcs[1][1]=new Enemy(32,10,1);
		npcs[1][2]=new Enemy(30,4,1);
		npcs[1][3]=new Enemy(61,33,1);
		
		npcs[2][0]=new Enemy(6,7,2);
		npcs[2][1]=new Enemy(14,23,2);
		npcs[2][2]=new Enemy(8,33,2);
		npcs[2][3]=new Enemy(40,8,2);
		npcs[2][4]=new Shopkeeper(53,32);
		
		npcs[3][0]=new Boss(62,16,5);
				
		Level.clearLevel();
		Level.levelOne();
		
		for (int i=0;i<npcs[1].length;i++){
			npcs[1][i].start();
		}
		
		for (int i=0;i<npcs[2].length;i++){
			npcs[2][i].start();
		}
		
		for (int i=0;i<npcs[3].length;i++){
			npcs[3][i].start();
		}
		
		tiles[player.getX()][player.getY()].setOccupied();
		
		//loads drop image
		try {
			dropImg = ImageIO.read(new File ("drop.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		backgroundImage = new BufferedImage(BLOCK_COLUMNS*BLOCK_SIDE_LENGTH, 
												BLOCK_ROWS*BLOCK_SIDE_LENGTH, BufferedImage.TYPE_INT_ARGB);
		
		drawLevel(backgroundImage.getGraphics());

		initializing = false;

		//Repaints frame while running to keep updated with npc and player positions
		Timer timer = new Timer(33,new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				frame.repaint();
			}
		});
		timer.start();
		frame.repaint();
	}
	
	//drops an item on the ground
	public static void drop(Item drop, int x,int y){
		tiles[x][y].itemDropped(drop);
	}
	
	public static Person getCharacter(){
		return player;
	}
	
	public Graphics getGraphics(){
		return g;
	}
	/*
	 * Draws level to a single image to reduce lag
	 */
	public static void drawLevel(Graphics g){
		for (int i=0;i<BLOCK_COLUMNS;i++){
			for (int j=0;j<BLOCK_ROWS;j++){
				try {
					g.drawImage(tiles[i][j].getTexture(), 
							i*BLOCK_SIDE_LENGTH,
							j*BLOCK_SIDE_LENGTH,
							BLOCK_SIDE_LENGTH,
							BLOCK_SIDE_LENGTH, 
							Color.black, frame);
				}
				
				catch (NullPointerException e) {
					System.err.println(i + " error " + j + " " );
				}
			}
		}
	}

	//updates and redraws background
	public static void redrawLevel() {
		BufferedImage newBG = new BufferedImage(BLOCK_COLUMNS*BLOCK_SIDE_LENGTH, 
									BLOCK_ROWS*BLOCK_SIDE_LENGTH, BufferedImage.TYPE_INT_ARGB);
		
		drawLevel(newBG.getGraphics());
		
		backgroundImage = newBG;
	}
		
	public static int getInventorySize(){
		return INVENTORY_COLUMNS*INVENTORY_ROWS;
	}
	
	public static int getInventoryRows(){
		return INVENTORY_ROWS;
	}
	
	public static int getInventoryColumns(){
		return INVENTORY_COLUMNS;
	}
	
	public static int getBlockRows(){
		return BLOCK_ROWS;
	}
	
	public static int getBlockColumns(){
		return BLOCK_COLUMNS;
	}
	
	public static Tile[][] getTiles(){
		return tiles;
	}
	
	public static NPC[][] getNpcs(){
		return npcs;
	}
	
	public static Person getPlayer(){
		return player;
	}
	
	public static JFrame getFrame(){
		return frame;
	}
	
	/*
	 * Paints player, npc, and background to frame
	 */
	@SuppressWarnings("serial")
	private class DrawThing extends JPanel{
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			if (!initializing) {
				this.setBackground(Color.WHITE);
				
				g.setColor(Color.BLACK);
				
				g.drawImage(backgroundImage, 0, 0, frame);
		
				for (int i=0;i<npcs[player.getMapLevel()].length;i++){
					if (npcs[player.getMapLevel()][i].getIsAlive()&&npcs[player.getMapLevel()][i].getVisible()){
						if (npcs[player.getMapLevel()][i] instanceof Enemy){
							g.setColor(Color.RED);
						} else if (npcs[player.getMapLevel()][i] instanceof Shopkeeper){
							g.setColor(Color.BLUE);
						}
						npcs[player.getMapLevel()][i].drawNpc(g, frame);
					}
					else{
						tiles[npcs[player.getMapLevel()][i].getX()][npcs[player.getMapLevel()][i].getY()].setUnoccupied();
					}
				}
				for (int i=0;i<BLOCK_COLUMNS;i++){
					for (int j=0;j<BLOCK_ROWS;j++){
						if (tiles[i][j].isItem()){
							g.drawImage(dropImg,i*BLOCK_SIDE_LENGTH,
									j*BLOCK_SIDE_LENGTH,
									BLOCK_SIDE_LENGTH,
									BLOCK_SIDE_LENGTH, 
									null, frame);
						}
					}
				}
				g.setColor(Color.GREEN);
				player.drawPerson(g, frame);				
			}
		}		
	}
	
	/*
	 * master key listener for game window
	 */
	private class KeyboardInput implements KeyListener{
		
		//key listener is "disabled" when in combat so player can not escape
		private boolean isDisabled = false;
		
		public KeyboardInput(Graphics g) {
			//do nothing
		}
		
		//determines whether or not the player can move in the given direction
		//if they can not, it performs actions according to the circumstances ie puts the player in combat, opens trade with a shopkeeper, etc
		public boolean move(int changeX, int changeY){
			
			boolean canWalk=false;

			if (tiles[player.getX()+changeX][player.getY()+changeY].getIsWalkable()){
				if (!tiles[player.getX()+changeX][player.getY()+changeY].checkOccupied()){
					canWalk=true;
					tiles[player.getX()][player.getY()].setUnoccupied();
					tiles[player.getX()+changeX][player.getY()+changeY].setOccupied();
				} else{
					for (int i=0;i<npcs[player.getMapLevel()].length;i++){
						if (npcs[player.getMapLevel()][i].getX()==player.getX()+changeX&&npcs[player.getMapLevel()][i].getY()==player.getY()+changeY){
							isDisabled = true;
							
							if (npcs[player.getMapLevel()][i].isTradable()){
								((Shopkeeper)npcs[player.getMapLevel()][i]).trade();
							} else{
								player.combat(npcs[player.getMapLevel()][i]);
							}					
						}
					}					
				}				
			} else {
				tiles[player.getX()+changeX][player.getY()+changeY].walkedTowards();
			}
			return canWalk;
		}
		
		public void keyPressed(KeyEvent event){
			
			if (isDisabled) {
				if (!player.getInCombat()){
					if (player.getCurrentHealth()>0){
						isDisabled = false;
					} 								
				}			
				else {
					return;
				}			
			}
			
			if (event.getKeyCode()==Menu.getKey(0)){ //Player move up
				
				if (player.getY()>0&&move(0,-1)){
					player.moveUp();
				}
				
			} else if (event.getKeyCode()==Menu.getKey(1)){	//Player move down
				
				if (player.getY()<BLOCK_ROWS-1&&move(0,1)){
					player.moveDown();
				}
				
			} else if (event.getKeyCode()==Menu.getKey(2)){	//Player move left
		
				if (player.getX()>0){				
					if (move(-1,0)){
						player.moveLeft();
					}				
				} else{
					
					//changes the level if possible
					if (player.getMapLevel()>1){						
						Level.changeLevel(player.getMapLevel(),player.getMapLevel()-1,false);
						player.changeMapLevel(-1);
					}
				}
				
			} else if (event.getKeyCode()==Menu.getKey(3)){	//Player move right		
				
				if (player.getX()<BLOCK_COLUMNS-1){					
					if (move(1,0)){
						player.moveRight();
					}
				} else{			
					
					//changes the level if possible
					if (player.getMapLevel()<Level.getNumLevels()){
						Level.changeLevel(player.getMapLevel(),player.getMapLevel()+1,true);
						player.changeMapLevel(1);
					}
				}
				
			} else if (event.getKeyCode()==Menu.getKey(5)){	//Inventory Key
				
				inv.open();
								
			} else if (event.getKeyCode()==Menu.getKey(6)){	//Char win Key
				
				charWin.open();
				
			} else if (event.getKeyCode()==Menu.getKey(7)){	//Traits win key
				
				lvlWin.open();
				lvlWin.requestFocusInWindow();
				
			} else if (event.getKeyCode()==Menu.getKey(4)){	//Pick up item key
				if (tiles[player.getX()][player.getY()].isItem()){
					Item i = tiles[player.getX()][player.getY()].itemPickedUp();
					player.add(i);
				}
			} else if (event.getKeyCode()== KeyEvent.VK_ESCAPE){	//Open menu key
				Menu.open();
				frame.setVisible(false);
			}			
			frame.repaint();
		}
		
		public void keyReleased(KeyEvent event){
			//do nothing
		}		
		
		public void keyTyped(KeyEvent event){
			//do nothing
		}	
	}

	//stops the music
	public static void stopMusic() {
		background.stop();		
	}
}
