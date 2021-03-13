package Windows;

import javax.swing.*;

import java.awt.*;

import Main.*;
import NPC.Boss;
import NPC.Enemy;
import NPC.NPC;

//creates a combat window with the player and the npc they are fighting
public class CombWindow {
	
	private JFrame combWin;	
	private JLabel lblPlayer,  lblEnemy, lblHealth, lblEnemyHealth;
	private JProgressBar pbHealth, pbEnemyHealth;
	private GridBagConstraints c = new GridBagConstraints();
	private Person player;
	private NPC npc;
	private Font font=new Font("Helvetica", 1, 20);
	private Icon imgPlayer, imgEnemy;
	
	public CombWindow(int x, int y, NPC npc){
		
		player=Game.getCharacter();
		this.npc=npc;
		
		imgPlayer=new ImageIcon("playercombat.png");
		
		//sets the colour based on the type of NPC
		if (npc instanceof Enemy){
			imgEnemy=new ImageIcon("enemycombat.png");
		} else if (npc instanceof Boss){
			imgEnemy=new ImageIcon("bosscombat.png");
		}		
		
		//builds the combat window
		combWin =  new JFrame("Combat");
		combWin.setAlwaysOnTop(true);
		combWin.setLocationByPlatform(true);
		combWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		combWin.setLayout(new GridBagLayout());
		combWin.setSize(800,600);
		combWin.setUndecorated(true);
		combWin.setLocation(x/2-400, y/2-300);

		c.weightx=1;
		c.weighty=1;
		
		lblPlayer=new JLabel(imgPlayer);
		lblPlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblPlayer.setPreferredSize(new Dimension(300,200));
		c.gridheight=2;
		c.gridx=0;
		c.gridy=0;
		combWin.add(lblPlayer,c);
		
		lblEnemy=new JLabel(imgEnemy);
		lblEnemy.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		lblEnemy.setPreferredSize(new Dimension(300,200));
		c.gridx=1;
		combWin.add(lblEnemy,c);
		
		c.gridheight=1;
		lblHealth=new JLabel("Health: "+player.getCurrentHealth()+"/"+player.getMaxHealth());
		lblHealth.setFont(font);
		c.insets=new Insets(0,100,0,0);
		c.anchor=GridBagConstraints.WEST;
		c.gridx=0;
		c.gridy=2;
		combWin.add(lblHealth,c);
		
		pbHealth=new JProgressBar();
		pbHealth.setForeground(Color.GREEN);
		pbHealth.setMaximum(player.getMaxHealth());
		pbHealth.setValue(player.getCurrentHealth());
		c.gridy=3;
		combWin.add(pbHealth,c);	
		
		lblEnemyHealth=new JLabel("Health: "+npc.getCurrentHealth()+"/"+npc.getMaxHealth());
		lblEnemyHealth.setFont(font);
		c.insets=new Insets(0,0,0,100);
		c.anchor=GridBagConstraints.EAST;
		c.gridx=1;
		c.gridy=2;
		combWin.add(lblEnemyHealth,c);
			
		pbEnemyHealth=new JProgressBar();
		pbEnemyHealth.setForeground(Color.GREEN);
		pbEnemyHealth.setMaximum(npc.getMaxHealth());
		pbEnemyHealth.setValue(npc.getCurrentHealth());
		c.gridy=3;
		combWin.add(pbEnemyHealth,c);
		
	}
	
	//opens the combat window
	public void open(){
		combWin.setVisible(true);
	}
	
	//closes the combat window
	public void close(){
		combWin.setVisible(false);
	}
	
	//updates the combat window with new values of the player and opponent's health every hit
	public void update(){
		
		if (player.getCurrentHealth()>0){
			lblHealth.setText("Health: "+player.getCurrentHealth()+"/"+player.getMaxHealth());
			pbHealth.setValue(player.getCurrentHealth());
		} else{
			lblHealth.setText("Health: 0"+"/"+player.getMaxHealth());
			pbHealth.setValue(0);
		}
		
		if (npc.getCurrentHealth()>0){
			lblEnemyHealth.setText("Health: "+npc.getCurrentHealth()+"/"+npc.getMaxHealth());
			pbEnemyHealth.setValue(npc.getCurrentHealth());
		} else{
			lblEnemyHealth.setText("Health: 0"+"/"+npc.getMaxHealth());
			pbEnemyHealth.setValue(0);
		}
		
		pbHealth.setMaximum(player.getMaxHealth());
		pbEnemyHealth.setMaximum(npc.getMaxHealth());
		
		combWin.repaint();
	}
	
}
