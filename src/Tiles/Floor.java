package Tiles;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//subclass of Tile
public class Floor extends Tile{
	
	//has position and texture
	public Floor(int x, int y) {
		super (x, y, true , true);
		try {
			texture= ImageIO.read(new File ("Floor.png"));
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
