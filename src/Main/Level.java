package Main;

import Tiles.*;

public class Level {
	
	private static Tile[][] tiles=Game.getTiles();
	private static int numLevels=3;
	
	public static int getNumLevels(){
		return numLevels;
	}
	
	//generates a house based on given position and size
	public static void makeHouse (int x, int y, int s){
		for (int i=x;i<x+s;i++){
			for (int j=y;j<y+s;j++){
				tiles[i][j] = new Floor(i,j);
			}
		}
		for (int i=x;i<x+s;i++){
			tiles[i][y] = new Wall (i,y);
			tiles[i][y+s] = new Wall (i,y+s);
		}
		for (int i=y;i<=y+s;i++){
			tiles[x][i] = new Wall (x,i);
			tiles[x+s][i] = new Wall (x+s,i);
		}
		if (y>Game.getBlockRows()/2){
			tiles[x+s/4][y] = new DoorHorizontal (x+s/4, y);
		}else {
			tiles[x+s/4][y+s] = new DoorHorizontal (x+s/4, y+s);
		}
	}
	
	//changes the level displayed on the screen when the player moves to the left or right of the screen (if a level exists in that direction)
	public static void changeLevel(int previousLevel, int newLevel, boolean forward){
		
		for (int i=0; i<Game.getNpcs()[previousLevel].length; i++){
			tiles[Game.getNpcs()[previousLevel][i].getX()][Game.getNpcs()[previousLevel][i].getY()].setUnoccupied();
			Game.getNpcs()[previousLevel][i].setVisible(false);
			if (forward){
				Game.getPlayer().setX(0);
			} else{
				Game.getPlayer().setX(Game.getBlockColumns()-1);
			}
		}
		
		clearLevel();
		
		switch (newLevel){
			case 1:
				levelOne();
				break;
			case 2:
				levelTwo();
				break;
			case 3:
				levelThree();
				break;
				
		}
		
		Game.redrawLevel();
		
	}
	
	//changes everything to grass so a new level can be drawn
	public static void clearLevel(){
		
		for (int i=0;i<Game.getBlockColumns();i++){
			for (int j=0;j<Game.getBlockRows();j++){
				tiles[i][j] = new Grass(i,j);
			}
		}	
	}
	
	//generates level one
	public static void levelOne(){
		
		for (int i=1;i<Game.getBlockColumns();i++){
			tiles[i][16] = new Path(i,16);
		}
		
		makeHouse(2,2,11);
		makeHouse(5,20,15);	
		makeHouse(58,29,6);		
		makeHouse(25,2,12);
		
		tiles[4][14]=new Path(4,14);
		tiles[4][15]=new Path(4,15);
		
		tiles[8][17]=new Path(8,17);
		tiles[8][18]=new Path(8,18);
		tiles[8][19]=new Path(8,19);
		
		tiles[28][15]=new Path(28,15);
		
		for (int i=17;i<29;i++){
			tiles[59][i]=new Path(59,i);
		}
		
		for (int i=0;i<Game.getNpcs()[1].length;i++){
			Game.getNpcs()[1][i].setVisible(true);
			tiles[Game.getNpcs()[1][i].getX()][Game.getNpcs()[1][i].getY()].setOccupied();
		}
	}
	
	//generates level two
	public static void levelTwo(){
		
		for (int i=0;i<Game.getBlockColumns();i++){
			tiles[i][16] = new Path(i,16);
		}
		
		makeHouse(3,2,8);		
		makeHouse(2,20,15);	
		makeHouse(25,25,6);
		makeHouse(37,5,8);//shopkeeper
		makeHouse(52,30,5);
		
		for (int i=11;i<20;i++){
			tiles[5][i]=new Path(5,i);
		}
		
		for (int i=17;i<25;i++){
			tiles[26][i]=new Path(26,i);
		}
		
		tiles[39][15]=new Path(39,15);
		tiles[39][14]=new Path(39,14);
		
		for (int i=17;i<30;i++){
			tiles[53][i]=new Path(53,i);
		}
		
		for (int i=0;i<Game.getNpcs()[2].length;i++){
			Game.getNpcs()[2][i].setVisible(true);
			tiles[Game.getNpcs()[2][i].getX()][Game.getNpcs()[2][i].getY()].setOccupied();
		}
	}
	
	//generates level three
	public static void levelThree(){
		
		for (int i=0;i<42;i++){
			tiles[i][16] = new Path(i,16);
		}
		
		makeHouse(3,2,9);
		makeHouse(7,25,12);	
		
		for (int i=12;i<16;i++){
			tiles[5][i]=new Path(5,i);
		}
		
		for (int i=17;i<25;i++){
			tiles[10][i]=new Path(8,i);
		}
		
		for (int i=4;i<Game.getBlockRows()-9;i++){
			tiles[42][i]=new Wall(42,i);
			tiles[64][i]=new Wall(64,i);
		}
		
		for (int i=42;i<65;i++){
			tiles[i][4]=new Wall(i,4);
			tiles[i][Game.getBlockRows()-9]=new Wall(i,Game.getBlockRows()-9);
		}
		
		for (int i=43;i<64;i++){
			for (int j=5;j<Game.getBlockRows()-9;j++){
				tiles[i][j]=new Floor(i,j);
			}
		}
		
		tiles[42][16]=new DoorVertical(42,16);
		
		for (int i=0;i<Game.getNpcs()[3].length;i++){
			Game.getNpcs()[3][i].setVisible(true);
			tiles[Game.getNpcs()[3][i].getX()][Game.getNpcs()[3][i].getY()].setOccupied();
		}
		
	}
}
