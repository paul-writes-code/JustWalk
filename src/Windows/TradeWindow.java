package Windows;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Main.*;
import Main.Menu;
import NPC.Shopkeeper;

public class TradeWindow{
	
	private JFrame tradeWin;
	private Graphics g;	
	private int inventoryRows=Game.getInventoryRows();
	private int inventoryColumns=Game.getInventoryColumns();
	private JLabel[][] slot = new JLabel[inventoryColumns][inventoryRows];
	private JLabel[][] slot2 = new JLabel[inventoryColumns][inventoryRows];
	private JLabel selected, lblGold, lblValue;
	private JTextArea options;
	private JPanel playerInv, shopInv;
	private Person player;
	private int slotSel=31;
	private boolean playerInvSelected=false,shopInvSelected=false;
	GridBagConstraints c=new GridBagConstraints();
	Shopkeeper shopkeeper;
	
	//builds a trade window, unique to each specific shopkeeper
	public TradeWindow(int x, int y,Shopkeeper shopkeeper){
		
		this.shopkeeper=shopkeeper;
		
		tradeWin =  new JFrame();
		tradeWin.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		tradeWin.setLayout(new GridLayout(1,2,20,0));
		tradeWin.addKeyListener(new KeyboardInput(g));
		tradeWin.setUndecorated(true);
		tradeWin.setLocation(x/2-600, y/2-250);
		tradeWin.setSize(1200, 500);
			
		player=Game.getCharacter();

		playerInv = new JPanel();
		playerInv.setLayout(new GridLayout(inventoryRows+1,inventoryColumns,5,5));
		tradeWin.add(playerInv);		
		
		shopInv = new JPanel();
		shopInv.setLayout(new GridLayout(inventoryRows+1,inventoryColumns,5,5));
		tradeWin.add(shopInv);
		
		for (int i=0;i<inventoryColumns;i++){
			for (int j=0;j<inventoryRows;j++){
				
				if (player.itemAt(i*inventoryRows+j)!=null){
					slot[i][j] = new JLabel(player.itemAt(i*inventoryRows+j).getName());
					slot[i][j].setForeground(Color.ORANGE);
					slot[i][j].setIcon(player.itemAt(i*inventoryRows+j).getIcon());

				}else{
					slot[i][j] = new JLabel();
					slot[i][j].setForeground(Color.BLACK);
				}
				
				slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				slot[i][j].setVisible(true);			
				slot[i][j].addMouseListener(new MouseInput());
				playerInv.add(slot[i][j]);
			}
		}
		
		for (int i=0;i<inventoryColumns;i++){
			for (int j=0;j<inventoryRows;j++){
				
				if (shopkeeper.itemAt(i*inventoryRows+j)!=null){
					slot2[i][j] = new JLabel(shopkeeper.itemAt(i*inventoryRows+j).getName());
					slot2[i][j].setForeground(Color.ORANGE);
					slot2[i][j].setIcon(shopkeeper.itemAt(i*inventoryRows+j).getIcon());
				} else{
					slot2[i][j] = new JLabel();
					slot2[i][j].setForeground(Color.BLACK);
				}

				slot2[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				slot2[i][j].setVisible(true);			
				slot2[i][j].addMouseListener(new MouseInput());				
				shopInv.add(slot2[i][j]);
			}
		}
		
		selected = new JLabel("No Item Selected");
		selected.setVisible(true);
		playerInv.add(selected);
		
		options = new JTextArea("");
		options.setBackground(null);
		options.setEditable(false);
		options.setBorder(null);
		options.setFocusable(false);
		playerInv.add(options);
		
		lblValue = new JLabel("Value: ");
		lblValue.setVisible(true);
		playerInv.add(lblValue);
		
		c.gridx=inventoryColumns+1;
		c.gridy=inventoryRows+1;
		lblGold = new JLabel("Gold: "+player.getGold());
		shopInv.add(lblGold,c);	
	}

	//opens the trade window
	public void open(){
		tradeWin.setVisible(true);
		load();
	}
	
	//closes the trade window
	public void close(){
		tradeWin.setVisible(false);
	}
	
	//loads the trade window
	private void load(){
		player=Game.getCharacter();
		for (int i=0; i<inventoryColumns;i++){
			for (int j=0;j<inventoryRows;j++){
				
				slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				slot2[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				
				if(player.itemAt(i*inventoryRows+j)!=null){
					slot[i][j].setText(player.itemAt(i*inventoryRows+j).getName());
					slot[i][j].setForeground(Color.ORANGE);
					slot[i][j].setIcon(player.itemAt(i*inventoryRows+j).getIcon());
				}else{
					slot[i][j].setText("");
					slot[i][j].setForeground(Color.BLACK);
					slot[i][j].setIcon(null);
					slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				}
				
				if (shopkeeper.itemAt(i*inventoryRows+j)!=null){
					slot2[i][j].setText(shopkeeper.itemAt(i*inventoryRows+j).getName());
					slot2[i][j].setForeground(Color.ORANGE);
					slot2[i][j].setIcon(shopkeeper.itemAt(i*inventoryRows+j).getIcon());
				}else{
					slot2[i][j].setText("");
					slot2[i][j].setForeground(Color.BLACK);
					slot2[i][j].setIcon(null);
					slot2[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				}
			}
		}
		lblGold.setText("Gold: "+player.getGold());
		lblValue.setText("Value:");
		exchangedItem();
		tradeWin.repaint();
	}
	
	//when an item is bought or sold
	private void exchangedItem(){
		selected.setText("No Item Selected");
		selected.setIcon(null);	
		options.setText(null);
		slotSel = 31;
	}
	
	//key listener for the trade window
	public class KeyboardInput implements KeyListener{
		
		public KeyboardInput(Graphics g) {
			//do nothing
		}
		
		public void keyPressed(KeyEvent event){
			
			if (event.getKeyCode()==Menu.getKey(5)){
				close();
			}else if (event.getKeyCode()==KeyEvent.VK_ESCAPE){
				close();
			}else if (event.getKeyCode()==Menu.getKey(11)){	//Sell
				if(playerInvSelected){				
					shopkeeper.add(player.itemAt(slotSel));
					
					try{						
						player.changeGold(player.itemAt(slotSel).getValue());
					}catch(NullPointerException err){
						
					}
					
					player.removeItem(slotSel);	
					lblValue.setText("Value:");
					load();
				}
			} else if (event.getKeyCode()==Menu.getKey(12)){	//Buy
				if(shopInvSelected&&player.getGold()>=shopkeeper.itemAt(slotSel).getValue()){
					player.add(shopkeeper.itemAt(slotSel));
					player.changeGold(-shopkeeper.itemAt(slotSel).getValue());
					shopkeeper.removeItem(slotSel);	
					lblValue.setText("Value:");
					load();
				}
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
	
	//mouse listener for trade window
	public class MouseInput implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			if (((JLabel) event.getComponent()).getIcon() != null){
				for (int i=0;i<inventoryColumns;i++){
					for (int j=0;j<inventoryRows;j++){
						slot[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
						slot2[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
						if (slot[i][j] == event.getComponent()){
							slotSel = i*inventoryRows+j;
							options.setText("'S' To Sell");
							lblValue.setText("Value: "+player.itemAt(i*inventoryRows+j).getValue());
							playerInvSelected=true;
							shopInvSelected=false;
						} else if (slot2[i][j] == event.getComponent()){
							slotSel = i*inventoryRows+j;
							options.setText("'B' To Buy");
							lblValue.setText("Value: "+shopkeeper.itemAt(i*inventoryRows+j).getValue());
							playerInvSelected=false;
							shopInvSelected=true;
						}
					}
				} 
				((JLabel)(event.getComponent())).setBorder(BorderFactory.createEtchedBorder());
				selected.setText(((JLabel) event.getComponent()).getText());				
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
