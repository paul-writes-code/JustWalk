package Main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/*
 * Loads all the game sound effects to clips and the clips are played through
 * accessor methods.
 */
public class Sounds {
	private Clip doorSound,hit, levelUp, miss;
	
	public Sounds() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream stream = null;
		
		stream = AudioSystem.getAudioInputStream(new File("Sounds/door_open.wav"));
		doorSound = AudioSystem.getClip();
		doorSound.open(stream);
		
		stream = AudioSystem.getAudioInputStream(new File("Sounds/hit3.wav"));
		hit = AudioSystem.getClip();
		hit.open(stream);
		
		stream = AudioSystem.getAudioInputStream(new File("Sounds/levelUp.wav"));
		levelUp = AudioSystem.getClip();
		levelUp.open(stream);
		
		stream = AudioSystem.getAudioInputStream(new File("Sounds/SSB_Sword1.wav"));
		miss = AudioSystem.getClip();
		miss.open(stream);
		
		
	}
	
	public void doorOpen(){
		doorSound.setFramePosition(0);
		doorSound.start();
	}
	
	public void hit(){
		hit.setFramePosition(0);
		hit.start();
	}
	
	public void levelUp(){
		levelUp.setFramePosition(0);
		levelUp.start();
	}
	
	public void miss(){
		miss.setFramePosition(0);
		miss.start();
	}
	
	public void setVol(Float i){
		FloatControl gainControl = 
			    (FloatControl) miss.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(i);
	}	
}
