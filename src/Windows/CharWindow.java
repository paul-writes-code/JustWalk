package Windows;

import Main.*;
import Main.Menu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Window to view character equipment and level
 */
public class CharWindow {
	
	private JFrame charWin;
	private Graphics g;
	private JLabel lblHelmet, lblChest, lblLegs, lblBoots, lblPlayer, lblName, lblLvl;
	private JProgressBar pbLvlBar;
	private GridBagConstraints c = new GridBagConstraints();
	private Person player;
	private Font font=new Font("Helvetica", 1, 20);
	private int selected;
		
	//Create labels for armour, player and level bar
	public CharWindow(int x, int y){
		charWin =  new JFrame("Character");
		charWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		charWin.setLayout(new GridBagLayout());
		charWin.addKeyListener(new KeyboardInput(g));
		charWin.setUndecorated(true);	
			
		player=Game.getCharacter();
		
		lblName = new JLabel(player.getName());
		lblName.setFont(font);	
		c.insets = new Insets(10,10,0,0);
		c.gridx = 1;
		c.gridy = 0;
		charWin.add(lblName, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,10,0,0);
		c.ipady = 60;
		c.ipadx = 45;
		
		lblHelmet= new JLabel("Helmet");
		lblHelmet.setBorder(BorderFactory.createLoweredBevelBorder());
		lblHelmet.addMouseListener(new MouseInput());
		c.gridx = 1;
		c.gridy = 1;
		charWin.add(lblHelmet, c);
		
		lblChest= new JLabel("Chest");
		lblChest.setBorder(BorderFactory.createLoweredBevelBorder());
		lblChest.addMouseListener(new MouseInput());
		c.gridx = 1;
		c.gridy = 2;
		charWin.add(lblChest, c);
		
		lblLegs= new JLabel("Legs");
		lblLegs.setBorder(BorderFactory.createLoweredBevelBorder());
		lblLegs.addMouseListener(new MouseInput());
		c.gridx = 1;
		c.gridy = 3;
		charWin.add(lblLegs, c);
		
		lblBoots= new JLabel("Boots");
		lblBoots.setBorder(BorderFactory.createLoweredBevelBorder());
		lblBoots.addMouseListener(new MouseInput());
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(0,10,10,0);
		charWin.add(lblBoots, c);
		
		//Player Image
		lblPlayer = new JLabel();
		lblPlayer.setBorder(BorderFactory.createRaisedBevelBorder());
		lblPlayer.setOpaque(true);
		lblPlayer.setBackground(Color.GREEN);
		c.insets = new Insets(0,5,50,10);
		c.gridx = 2;
		c.gridy = 1;
		c.ipady = c.ipady*5;
		c.ipadx = c.ipadx*3;
		c.gridheight = 6;
		c.gridwidth = 2;
		charWin.add(lblPlayer, c);
		
		c = new GridBagConstraints();
		player.checkLevel();
		pbLvlBar = new JProgressBar();
		pbLvlBar.setForeground(Color.RED);		
		pbLvlBar.setStringPainted(true);
		pbLvlBar.setMaximum(player.xpForLevel());
		pbLvlBar.setValue(player.getXp());
		c.gridx = 1;
		c.gridy = 5;
		c.ipadx = 125;
		c.gridwidth = 8;
		c.insets = new Insets(0,10,0,10);
		charWin.add(pbLvlBar, c);
		
		lblLvl = new JLabel(player.checkLevel()+"");
		c.gridwidth = 1;
		charWin.add(lblLvl,c);
			
		charWin.pack();
		charWin.setLocation(x/2-(charWin.getWidth()/2), y/2-(charWin.getHeight()/2));
	}
	
	//Opens and updates Character Window
	public void open(){
		
		charWin.setVisible(true);
		pbLvlBar.setMaximum(player.xpForLevel()-player.xpForPrev());
		pbLvlBar.setValue(player.getXp()-player.xpForPrev());
		lblLvl.setText(player.checkLevel()+"");
		
		if (player.getHelmet() != null){
			lblHelmet.setText(player.getHelmet().getName());
			lblHelmet.setIcon(player.getHelmet().getIcon());
		}
		
		if (player.getChest() != null){
			lblChest.setText(player.getChest().getName());
			lblChest.setIcon(player.getChest().getIcon());
		}
		
		if (player.getLegs() != null){
			lblLegs.setText(player.getLegs().getName());
			lblLegs.setIcon(player.getLegs().getIcon());
		}
		
		if (player.getBoots() != null){
			lblBoots.setText(player.getBoots().getName());
			lblBoots.setIcon(player.getBoots().getIcon());
		}
		
		lblHelmet.setBackground(null);
		lblChest.setBackground(null);
		lblLegs.setBackground(null);
		lblBoots.setBackground(null);
		
		charWin.pack();
	}
	
	//closes the character window
	public void close(){
		charWin.setVisible(false);
	}
	
	//key listener for the character window
	private class KeyboardInput implements KeyListener{
		
		public KeyboardInput(Graphics g) {
		}
		
		public void keyPressed(KeyEvent event){
			
			if (event.getKeyCode()==Menu.getKey(6)){
				close();
			}else if (event.getKeyCode()==KeyEvent.VK_ESCAPE){
				close();
			}else if (event.getKeyCode()==Menu.getKey(10)){	//Remove Item
				switch (selected){
					case 1:
						
						if (player.canAdd()){
							player.removeHelmet();
							lblHelmet.setIcon(null);
							lblHelmet.setText("Helmet");
							lblHelmet.setBackground(null);
						}	
						
						break;
						
					case 2:
						
						if (player.canAdd()){
							player.removeChest();
							lblChest.setIcon(null);
							lblChest.setText("Chest");
							lblChest.setBackground(null);
						}	
						
						break;
						
					case 3:
						
						if (player.canAdd()){
							player.removeLegs();
							lblLegs.setIcon(null);
							lblLegs.setText("Legs");
							lblLegs.setBackground(null);
						}	
						
						break;
						
					case 4:
						
						if (player.canAdd()){
							player.removeBoots();
							lblBoots.setIcon(null);
							lblBoots.setText("Boots");
							lblBoots.setBackground(null);
						}
						
						break;					
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			//do nothing			
		}
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			//do nothing			
		}
	}
	
	//Mouse listener to select equipped armour
	private class MouseInput implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			
			lblHelmet.setBackground(null);
			lblChest.setBackground(null);
			lblLegs.setBackground(null);
			lblBoots.setBackground(null);

			if (lblHelmet == event.getComponent() && player.getHelmet() != null){
				selected = 1;
				event.getComponent().setBackground(Color.MAGENTA);
			} else if (lblChest == event.getComponent()&& player.getChest() != null){
				selected = 2;
				event.getComponent().setBackground(Color.MAGENTA);
			} else if (lblLegs == event.getComponent()&& player.getLegs() != null){
				selected = 3;
				event.getComponent().setBackground(Color.MAGENTA);
			} else if (lblBoots == event.getComponent()&& player.getBoots() != null){
				selected = 4;
				event.getComponent().setBackground(Color.MAGENTA);
			} else{
				selected = 0;
			}
		}	

		@Override
		public void mousePressed(MouseEvent e) {
			//DO NOTHING\\		
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//DO NOTHING\\			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//DO NOTHING\\			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//DO NOTHING\\			
		}		
	}
}
