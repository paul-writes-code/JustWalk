package Tiles;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import Main.Game;

//subclass of Tile - Door on horizontal wall (levels 1 and 2)
public class DoorHorizontal extends Tile{
	
	//has position and texture
	public DoorHorizontal(int x, int y) {
		super (x, y, false , false);
		try {
			texture= ImageIO.read(new File ("DoorClosedHorizontal.png"));
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
				texture= ImageIO.read(new File ("DoorOpenHorizontal.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Game.sound.doorOpen();
			Game.redrawLevel();
		}
	}
	
}
