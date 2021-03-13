package Main;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;
public class Menu {
	
	private static JFrame mainFrame;
	private JPanel helpWin, mainWin, optionWin, optionPanel, bisWin;
	private static JButton btnStart;
	private JButton btnOptions;
	private JButton btnHelp;
	private JButton escHelp;
	private JButton escOptions;
	private JButton btnSave;
	private static JTextArea helpArea;
	private JLayeredPane layeredPane;
	private static boolean gameStarted = false;
	private ImageIcon bis, logo;
	private JLabel lblCompany;
	private static JLabel up, down, left, right, pickUp, inventory, character, traits, equip, drop, remove, buy, sell;
	private static JTextField[] keyCode = new JTextField[13];
	private static int[] keys = new int[13];
	
	//builds the menu
	@SuppressWarnings("static-access")
	public Menu(){
		mainFrame = new JFrame("Main Menu");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setExtendedState(mainFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new GridLayout(0,1,5,5));
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(mainFrame.getSize());
		layeredPane.setLayout(new OverlayLayout(layeredPane));
		layeredPane.setBorder(BorderFactory.createTitledBorder(
                "An RGP Java Game"));
		mainFrame.add(layeredPane);

		//MAIN MENU\\
		mainWin = new JPanel();
		mainWin.setVisible(true);
		mainWin.setPreferredSize(mainFrame.getSize());
		mainWin.setLayout(new GridLayout(0,1,5,5));
		layeredPane.add(mainWin,  0);
		
		btnStart = new JButton("Start Game");
		btnStart.addMouseListener(new MouseInput());
		mainWin.add(btnStart);

		btnOptions = new JButton("Options");
		btnOptions.addMouseListener(new MouseInput());
		mainWin.add(btnOptions);

		btnHelp = new JButton("Help");
		btnHelp.setVisible(true);
		btnHelp.addMouseListener(new MouseInput());
		mainWin.add(btnHelp, JLayeredPane.DEFAULT_LAYER);
		
		//Options Window\\
		optionWin = new JPanel();
		optionWin.setVisible(false);
		optionWin.setPreferredSize(mainFrame.getSize());
		optionWin.setLayout(new BorderLayout());
		layeredPane.add(optionWin);
		
		optionPanel = new JPanel();
		optionWin.add(optionPanel, BorderLayout.CENTER);
		for (int i =0; i<13; i++){
			keyCode[i] = new JTextField();
			keyCode[i].addMouseListener(new MouseInput());
			keyCode[i].setBackground(null);
		}
		loadOptions(optionPanel);
		
		btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseInput());
		optionWin.add(btnSave,BorderLayout.LINE_END);
		
		escOptions = new JButton("Back");
		escOptions.addMouseListener(new MouseInput());
		optionWin.add(escOptions,BorderLayout.PAGE_END);
		
		//HELP WINDOW\\
		helpWin = new JPanel();
		helpWin.setVisible(false);
		helpWin.setPreferredSize(mainFrame.getSize());
		helpWin.setLayout(new BorderLayout());
		layeredPane.add(helpWin);
		
		helpArea = new JTextArea();
		helpArea.setEditable(false);
		helpArea.setBackground(null);
		loadHelp();
		helpWin.add(helpArea,BorderLayout.LINE_START);
		
		escHelp = new JButton("Back");
		escHelp.addMouseListener(new MouseInput());
		helpWin.add(escHelp,BorderLayout.PAGE_END);	
		
		logos();		
	}
	
	//opens the menu
	public static void open(){
		mainFrame.setVisible(true);
		btnStart.setText("Resume");
	}
	
	//loads the options
	private static void loadOptions(JPanel pane){
		GridBagConstraints c=new GridBagConstraints();
		pane.setLayout(new GridBagLayout());
		
		c.gridx =0;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 5;
		c.weighty = 12;
		
		up = new JLabel("Up");
		c.gridy = 0;
		pane.add(up,c);
		
		down = new JLabel("Down");
		c.gridy++;
		pane.add(down,c);
		
		left = new JLabel("Left");
		c.gridy++;
		pane.add(left,c);
		
		right = new JLabel("Right");
		c.gridy++;
		pane.add(right,c);

		pickUp = new JLabel("Pick Up");
		c.gridy++;
		pane.add(pickUp,c);
		
		inventory = new JLabel("Inventory");
		c.gridy++;
		pane.add(inventory,c);
		
		character = new JLabel("Character Screen");
		c.gridy++;
		pane.add(character,c);
		
		traits = new JLabel("Traits Screen");
		c.gridy++;
		pane.add(traits,c);
		
		equip = new JLabel("Equip Item");
		c.gridy++;
		pane.add(equip,c);
		
		drop = new JLabel("Drop Item");
		c.gridy++;
		pane.add(drop,c);
		
		remove = new JLabel("Remove Armour");
		c.gridy++;
		pane.add(remove,c);
		
		sell = new JLabel("Sell Item");
		c.gridy++;
		pane.add(sell,c);
		
		buy = new JLabel("Buy Item");
		c.gridy++;
		pane.add(buy,c);

		c.gridx = 1;
		loadKeys();
		String temp;
		for (int i =0; i<13; i++){
			temp = (char)keys[i]+"";
			if ((char)keys[i] == 32){
				temp = "SPACE";
			}
			keyCode[i].setPreferredSize( new Dimension( 200, 24 ) );
			keyCode[i].setText(temp);
			keyCode[i].setBackground(null);
			c.gridy = i;
			pane.add(keyCode[i],c);
		}
	
	}
	
	public static int getKey(int i){
		return keys[i];
	}
	
	//change the key bindings of the game
	private static void saveKeys(){
		int temp = 0;
		boolean inputWorks;
		for (int i =0; i<13; i++){
			if (keyCode[i].getText().equalsIgnoreCase("SPACE")){
				keys[i] = 32;
			}else{
				try {
					inputWorks = true;			
					temp = (int) (keyCode[i].getText().toUpperCase().charAt(0));
				}
				catch (NumberFormatException nfe){
					inputWorks = false;
				}
				
				if (inputWorks == true){
					if (temp<=255){
						keys[i] = temp;
					}
				}
			}
		}		
	}
	
	//initializes the keys to default
	private static void loadKeys(){
		keys[0] = KeyEvent.VK_W;
		keys[1] = KeyEvent.VK_S;
		keys[2] = KeyEvent.VK_A;
		keys[3] = KeyEvent.VK_D;
		keys[4] = KeyEvent.VK_SPACE;
		keys[5] = KeyEvent.VK_I;
		keys[6] = KeyEvent.VK_C;
		keys[7] = KeyEvent.VK_T;
		keys[8] = KeyEvent.VK_E;
		keys[9] = KeyEvent.VK_D;
		keys[10] = KeyEvent.VK_R;
		keys[11] = KeyEvent.VK_S;
		keys[12] = KeyEvent.VK_B;
	}
	
	//loads the help menu
	private static void loadHelp(){
		File inFile = new File("README.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNext()){
			helpArea.append(scanner.nextLine()+"\n");
		}
		
	}
	
	//mouse listener for menu
	public class MouseInput implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			if (event.getSource() == btnHelp){
				helpWin.setVisible(true);
				mainWin.setVisible(false);
			}else if (event.getSource() == escHelp){
				mainWin.setVisible(true);
				helpWin.setVisible(false);
			}else if (event.getSource() == btnOptions){
				updateOptions();
				optionWin.setVisible(true);
				mainWin.setVisible(false);
			}else if (event.getSource() == escOptions){
				optionWin.setVisible(false);
				mainWin.setVisible(true);
			}else if (event.getSource() == btnStart){
				if (gameStarted == false){
					@SuppressWarnings("unused")
					Game game = new Game();
					gameStarted = true;
				}else {
					Game.frame.setVisible(true);
				}
				mainFrame.setVisible(false);
			}else if (event.getSource() == btnSave){
				saveKeys();
			}
		}

		@Override
		public void mousePressed(MouseEvent e){
			//do nothing
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//do nothing
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			//do nothing
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//do nothing
		}
	}

	public void updateOptions() {
		String temp;
		for (int i =0; i<13; i++){
			temp = (char)keys[i]+"";
			if ((char)keys[i] == 32){
				temp = "SPACE";
			}
			keyCode[i].setText(temp);
			keyCode[i].setBackground(null);
		}		
	}
	
	public void logos(){
		mainWin.setVisible(false);
		bisWin = new JPanel();
		bisWin.setPreferredSize(mainFrame.getSize());
		bis = new ImageIcon("logo.png");
		lblCompany = new JLabel();
		bisWin.add(lblCompany);
		layeredPane.add(bisWin);
		logo = new ImageIcon("JustWalk.png");
				
		lblCompany.setIcon(bis);
		bisWin.repaint();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lblCompany.setIcon(logo);
		bisWin.repaint();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bisWin.setVisible(false);
		mainWin.setVisible(true);
	}

	public static void close() {
		Game.frame.setVisible(false);	
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		Menu menu= new Menu();
	}
}
