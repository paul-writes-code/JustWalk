package Tiles;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import Main.Game;

//subclass of Tile - Door on a vertical wall (ie the castle in level 3)
public class DoorVertical extends Tile{
	
	//has position and texture
	public DoorVertical(int x, int y) {
		super (x, y, false , false);
		try {
			texture= ImageIO.read(new File ("DoorClosedVertical.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void steppedOn() {
		//do nothing	
	}
	public Image getTexture(){
		return texture;
	}

	@Override
	public void walkedTowards() {
		if (!playerWalkable){
			playerWalkable = true;
			try {
				texture= ImageIO.read(new File ("DoorOpenVertical.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Game.sound.doorOpen();
			Game.redrawLevel();
		}
	}
	
}
