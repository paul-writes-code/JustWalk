package Windows;

import Main.*;
import Main.Menu;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LevelWindow {
	
	private JFrame lvlWin;	
	private JLabel lblStrength,  lblAttack, lblDefence, lblStrengthLvl, lblAttackLvl, lblDefenceLvl, lblSkillPoints;
	private JButton btnStrength, btnAttack, btnDefence;
	private JProgressBar pbStrength, pbAttack, pbDefence;
	private Graphics g;
	private GridBagConstraints c = new GridBagConstraints();
	private Person player;
	private Font font=new Font("Helvetica", 1, 20);
	private int maxLevel=20;
	
	//builds the level window, which contains the player's attributes and allows them to level up
	public LevelWindow(int x, int y){
		
		player=Game.getCharacter();
		
		lvlWin =  new JFrame("Skills");
		lvlWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		lvlWin.setLayout(new GridBagLayout());
		lvlWin.addKeyListener(new KeyboardInput(g));
		lvlWin.setSize(600,300);
		lvlWin.setUndecorated(true);
		lvlWin.setLocation(x/2-300, y/2-250);
		
		c.insets = new Insets(10,10,0,0);
		c.anchor = GridBagConstraints.WEST;
		
		lblStrength=new JLabel("Strength Level:");
		lblStrength.setFont(font);			
		c.gridx=0;
		c.gridy=1;
		lvlWin.add(lblStrength,c);
		
		lblStrengthLvl=new JLabel(player.getStrength()+"");
		lblStrengthLvl.setFont(font);
		c.gridx=1;
		lvlWin.add(lblStrengthLvl,c);
		
		pbStrength=new JProgressBar();
		pbStrength.setForeground(Color.BLUE);	
		pbStrength.setMaximum(maxLevel);
		pbStrength.setValue(player.getStrength());
		c.gridx=2;
		lvlWin.add(pbStrength,c);
		
		btnStrength=new JButton("+");
		btnStrength.setActionCommand("Strength");
		btnStrength.addActionListener(new ButtonListener());
		btnStrength.setFont(font);
		c.gridx=3;
		lvlWin.add(btnStrength,c);
		
		lblAttack=new JLabel("Attack Level:");
		lblAttack.setFont(font);
		c.gridx=0;
		c.gridy=3;
		lvlWin.add(lblAttack,c);
		
		lblAttackLvl=new JLabel(player.getAttack()+"");
		lblAttackLvl.setFont(font);
		c.gridx=1;
		lvlWin.add(lblAttackLvl,c);
		
		pbAttack=new JProgressBar();
		pbAttack.setForeground(Color.BLUE);	
		pbAttack.setMaximum(maxLevel);
		pbAttack.setValue(player.getAttack());
		c.gridx=2;
		lvlWin.add(pbAttack,c);
		
		btnAttack=new JButton("+");
		btnAttack.setActionCommand("Attack");
		btnAttack.addActionListener(new ButtonListener());
		btnAttack.setFont(font);
		c.gridx=3;
		lvlWin.add(btnAttack,c);
		
		lblDefence=new JLabel("Defence Level:");
		lblDefence.setFont(font);
		c.gridx=0;
		c.gridy=4;
		lvlWin.add(lblDefence,c);
		
		lblDefenceLvl=new JLabel(player.getDefence()+"");
		lblDefenceLvl.setFont(font);
		c.gridx=1;
		lvlWin.add(lblDefenceLvl,c);
		
		pbDefence=new JProgressBar();
		pbDefence.setForeground(Color.BLUE);	
		pbDefence.setMaximum(maxLevel);
		pbDefence.setValue(player.getDefence());
		c.gridx=2;
		lvlWin.add(pbDefence,c);
		
		btnDefence=new JButton("+");
		btnDefence.setActionCommand("Defence");
		btnDefence.addActionListener(new ButtonListener());
		btnDefence.setFont(font);
		c.gridx=3;
		lvlWin.add(btnDefence,c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth=2;
		
		lblSkillPoints=new JLabel("Skill Points: "+player.getSP());
		lblSkillPoints.setFont(font);
		c.gridx=2;
		c.gridy=5;
		lvlWin.add(lblSkillPoints,c);
		
		lvlWin.repaint();		
	}
	
	//opens the window
	public void open(){
		update();
		lvlWin.setVisible(true);
	}
	
	//closes the window
	public void close(){
		lvlWin.setVisible(false);
	}
	
	//updates the window
	public void update(){
		lblStrengthLvl.setText(player.getStrength()+"");
		lblAttackLvl.setText(player.getAttack()+"");
		lblDefenceLvl.setText(player.getDefence()+"");
		
		pbStrength.setValue(player.getStrength());
		pbAttack.setValue(player.getAttack());
		pbDefence.setValue(player.getDefence());
		
		lblSkillPoints.setText("Skill Points: "+player.getSP());
	}
	
	//key listener for the level window
	private class KeyboardInput implements KeyListener{
		
		public KeyboardInput(Graphics g) {
			//do nothing
		}
		
		public void keyPressed(KeyEvent event){
			
			if (event.getKeyCode()==Menu.getKey(7)){
				close();
			}else if (event.getKeyCode()==KeyEvent.VK_ESCAPE){
				close();
			}
		}
		
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub		
		}
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub		
		}
	}
	
	//button listener for the level window
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			String eventName=event.getActionCommand();
			
			if (player.getSP()>0){
				if (eventName.equals("Strength")){
					player.strengthUp();
				} else if (eventName.equals("Attack")){
					player.attackUp();
				} else if (eventName.equals("Defence")){
					player.defenceUp();
				}
				
				lblStrengthLvl.setText(player.getStrength()+"");
				lblAttackLvl.setText(player.getAttack()+"");
				lblDefenceLvl.setText(player.getDefence()+"");
				lblSkillPoints.setText("Skill Points: "+player.getSP());
				
				pbStrength.setValue(player.getStrength());
				pbAttack.setValue(player.getAttack());
				pbDefence.setValue(player.getDefence());
			}
			
			lvlWin.repaint();
			lvlWin.requestFocusInWindow();
		}		
	}

	//requests focus to the frame rather than the buttons; once the buttons are pressed, 
	//the frame loses focus and the key listener does not function until the window is reselected
	public void requestFocusInWindow() {
		lvlWin.requestFocusInWindow();		
	}
}
