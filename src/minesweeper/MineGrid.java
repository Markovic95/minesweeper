/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minesweeper;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *  Grid of the Game, having the utilities of the game,
 *  such as settings
 *
 * @author Dalibor markovic ana tasos kremmidas 
 */
public final class MineGrid extends JFrame implements ActionListener{
    private JMenuBar menubar; //menubar
    private JPanel gridpanel; //main panel of the frame
    private JMenu Game; //menu for setting
    private JButton Scores; //button for showing the hishscores
    private JMenuItem New; //Menu Item for going back to menu
    
    
    int rows;
    int columns;
    int mines;
    JFrame StartMenu;
    Logic_Grid board;
    private int Mines;
    private int X;
    private int Y;
    
    /**
     * Constructor of the class.
     * Calls initCompo to initialiaze the panel. 
     * @param rows, total rows of the board
     * @param columns, total columns of the board
     * @param mines, total mines in the board
     */
    public MineGrid(int rows, int columns,int mines ){
        super();
       
        this.rows = rows;
        this.columns = columns;
        this.mines=mines;
        initCompo(this.rows, this.columns,this.mines);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /**
     * Initializes the main panel of the game.
     * Calls GUI_Grid for the panel of minesweeper(the actual grid of the game).
     * @param x, total rows of the board
     * @param y, total columns of the board
     * @param mines, total mines of the board
     */
    public void initCompo(int x,int y,int mines) {
        setLayout(new BorderLayout());
        
        this.Mines=mines;
        this.X=x;
        this.Y=y;
        menubar = new JMenuBar();
        gridpanel = new JPanel();

        GUI_Grid grid = new GUI_Grid(this.X,this.Y,this.Mines); //creates the grid of the main game of minesweeper
        gridpanel.add(grid , BorderLayout.NORTH);
        
        Game = new JMenu("Game");
        
        Scores = new JButton("Scores");
        Scores.addActionListener(this);//adds actionListener
        Scores.setActionCommand("Scores"); //sets actionCommand when called
        
        
        New = new JMenuItem("Settings");
        New.addActionListener(this); //adds actionListener
        New.setActionCommand("Settings"); //sets actionCommand when called
        add(menubar,BorderLayout.NORTH);
        
        
        New.setToolTipText("Create new Default Game");
        menubar.add(Game);
        menubar.add(Scores);
        Game.add(New);
        
        add(gridpanel,BorderLayout.CENTER);
     
        add(new JPanel(),BorderLayout.EAST);
        add(new JPanel(),BorderLayout.WEST);
        //setSize(x+y*40,x+y*45);
        pack();
        setLocationRelativeTo(null);
    }
    /**
     * Action Performed when a button is pressed.
     * Settings: goes back to the main menu when pressed
     * Scores: button that shows Highscores in an MessageDialog
     */
   @Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
//		using switch statement for every button that we use
		switch(command) {
		
		case "Settings": dispose();
				         removeAll();
				         JFrame newmenu = new StartMenu();
				         newmenu.setVisible(true); break; 
		case "Scores":   
					   ArrayList<String> scores2 = new ArrayList<String>();
					   try {
					   	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("highScores.txt")); 
					   	scores2.addAll( (ArrayList<String>) ois.readObject()); // adds all objects from the file being called
					   	
					   	JOptionPane.showMessageDialog(null, scores2.get(0)+"\n"+scores2.get(1)+"\n"+scores2.get(2)+"\n","HighScores",
									 JOptionPane.INFORMATION_MESSAGE); 
					   }catch(HeadlessException | IOException | ClassNotFoundException ex) {
						   ex.getMessage();
					   } 
					  
		
		}
	
    }
    
   
   
}

