package Windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Items.*;

import Main.*;
import Main.Menu;

/*
 * Class for player inventory
 * Items can be equipped and dropped from this screen
 */
public class InvWindow{
	
	private JFrame inv;
	private Graphics g;	
	private int inventoryRows=Game.getInventoryRows();
	private int inventoryColumns=Game.getInventoryColumns();
	private JLabel[][] slot = new JLabel[inventoryColumns][inventoryRows];
	private JLabel selected, lblGold;
	private JTextArea options;
	private Person player;
	private int slotSel=31;
	GridBagConstraints c=new GridBagConstraints();
	
	//Initializes Inventory window
	public InvWindow(int x, int y){
		inv =  new JFrame("Inventory");
		inv.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		inv.setLayout(new GridLayout(7,6,5,5));
		inv.addKeyListener(new KeyboardInput(g));
		inv.setUndecorated(true);
		inv.setLocation(x/2-300, y/2-250);
		inv.setSize(600, 500);
		
		c.gridx=0;
		c.gridy=0;
		
		for (int i=0;i<inventoryColumns;i++){
			for (int j=0;j<inventoryRows;j++){
				slot[i][j] = new JLabel("Slot "+(i*inventoryRows+j+1));
				slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				slot[i][j].setVisible(true);			
				slot[i][j].addMouseListener(new MouseInput());
				c.gridx=i;
				c.gridy=j;
				inv.add(slot[i][j],c);
			}
		}
		
		selected = new JLabel("No Item Selected");
		selected.setVisible(true);
		inv.add(selected);
		
		options = new JTextArea("");
		options.setBackground(null);
		options.setEditable(false);
		options.setBorder(null);
		options.setFocusable(false);
		inv.add(options);
		
		c.gridx=inventoryColumns+1;
		c.gridy=inventoryRows+1;
		lblGold = new JLabel("Gold: "+null);
		inv.add(lblGold,c);
		
	}

	//opens window
	public void open(){
		inv.setVisible(true);
		load();
	}
	
	//closes window
	public void close(){
		inv.setVisible(false);
	}
	
	//Fills the inventory window with the player items
	private void load(){
		player=Game.getCharacter();
		for (int i=0; i<inventoryColumns;i++){
			for (int j=0;j<inventoryRows;j++){
				
				slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				
				if(player.itemAt(i*inventoryRows+j)!=null){
					slot[i][j].setText(player.itemAt(i*inventoryRows+j).getName());
					slot[i][j].setForeground(Color.ORANGE);
					slot[i][j].setIcon(player.itemAt(i*inventoryRows+j).getIcon());
				}else{
					slot[i][j].setText("Slot "+(i*inventoryRows+j+1));
					slot[i][j].setForeground(Color.BLACK);
					slot[i][j].setIcon(null);
					slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				}
			}
		}
		
		selected.setText("No Item Selected");
		selected.setIcon(null);	
		options.setText(null);
		lblGold.setText("Gold: "+player.getGold());
		inv.repaint();
	}
	
	//when an item is removed
	private void removedItem(){
		selected.setText("No Item Selected");
		selected.setIcon(null);	
		options.setText(null);
		
		player.removeItem(slotSel);
		slotSel = 31;
	}
	
	//Inventory window key listener
	public class KeyboardInput implements KeyListener{
		
		public KeyboardInput(Graphics g) {
			//do nothing
		}
		
		public void keyPressed(KeyEvent event){
			
			if (event.getKeyCode()==Menu.getKey(5)){
				close();
			}else if (event.getKeyCode()==KeyEvent.VK_ESCAPE){
				close();
			}else if (event.getKeyCode()==Menu.getKey(8) && slotSel != 31){	//Equip Item
				/*
				 * Checks if player have item equipped if item is already
				 * equipped it is swapped with the selected item
				 */
				if(player.itemAt(slotSel).getType() == 1){
					
					if (player.getHelmet()!=null){						
						player.removeHelmet();
					}
					
					player.addHelmet((Helmet)player.itemAt(slotSel));
					removedItem();
					
				}else if (player.itemAt(slotSel).getType() == 2){
					
					if (player.getChest()!=null){
						player.removeChest();
					}
					player
					.addChest((Chestplate) player.itemAt(slotSel));
					removedItem();
					
				}else if (player.itemAt(slotSel).getType() == 3){
					
					if (player.getLegs()!=null){
						player.removeLegs();
					}
					
					player.addLegs((Leggings) player.itemAt(slotSel));
					removedItem();
					
				}else if (player.itemAt(slotSel).getType() == 4){
					
					if (player.getBoots()!=null){
						player.removeBoots();
					}
					
					player.addBoots((Boots) player.itemAt(slotSel));
					removedItem();
					
				}
				load();
			}else if (event.getKeyCode()==Menu.getKey(9) && slotSel != 31){		//Drop ITEM
				Game.drop(player.itemAt(slotSel), player.getX(), player.getY());
				removedItem();
				load();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			//DO NOTHING\\			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			//DO NOTHING\\		
		}           
	}
	//Mouse listener to set selected item
	public class MouseInput implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			
			if (((JLabel) event.getComponent()).getIcon() != null){
				for (int i=0;i<inventoryColumns;i++){
					for (int j=0;j<inventoryRows;j++){
						slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
						if (slot[i][j] == event.getComponent()){
							slotSel = i*inventoryRows+j;
						}	
					}
				} 
				
				((JLabel)(event.getComponent())).setBorder(BorderFactory.createEtchedBorder());
				selected.setText(((JLabel) event.getComponent()).getText());
				selected.setIcon(((JLabel) event.getComponent()).getIcon());
				
				options.setText("'E' To Equip\n'D' To Drop");
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			//DO NOTHING\\			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			//DO NOTHING\\			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			//DO NOTHING\\		
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			//DO NOTHING\\			
		}
		
	}	
}
