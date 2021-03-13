package Tiles;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//subclass of Tile
public class Wall extends Tile{	
	
	//Tiles all have a position and a texture
	public Wall(int x, int y) {
		super (x, y, false , false);
		
		try {
			texture= ImageIO.read(new File ("Wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getTexture(){
		return texture;
	}
	
	@Override
	public void steppedOn() {
		//do nothing
	}

	@Override
	public void walkedTowards() {	
		//do nothing
	}
}
