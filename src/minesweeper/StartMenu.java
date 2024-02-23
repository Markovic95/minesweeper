package minesweeper;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Main Menu of the program.
 * Shows the difficulties as buttons for the player to choose.
 * @author Dalibor markovic and tasos kremidas 
 */
public class StartMenu extends JFrame {

	    
	    private JButton b2; //button of 16x16 grid
	    private JButton b; //button of 9x9 grid
	    private JButton b3; //buutton of 30x16
	    
	    private JPanel input,defaultvaluespanel,toppanel,southpanel,subsouthpanel; //panels for choices of the game
	    
	    private JLabel startlabel,defaultlabel; //labels for main menu
            
	    /**
	     * Constructor of the class.
	     * Calls initComponents to create GUI
	     */
	    public StartMenu() {
	        initComponents();
            setLocationRelativeTo(null);
	    }

	    private void initComponents() {
                toppanel = new JPanel(new BorderLayout());
               
                southpanel = new JPanel(new BorderLayout());
                subsouthpanel = new JPanel(new FlowLayout());
		        defaultvaluespanel = new JPanel(new FlowLayout());
		        input = new JPanel(new FlowLayout());
		        startlabel = new JLabel("Start Menu");
	            defaultlabel = new JLabel("Default Game Values");
	                
	                
                b = new JButton(" 9x9 10 Mines");
                b.addActionListener(new StartGameAction(this, new MineGrid(9, 9,10)));
	        
                b2 = new JButton(" 16x16 30 Mines");
		        b2.addActionListener(new StartGameAction(this, new MineGrid(16, 16,30)));
		        
		        b3 = new JButton(" 30x16 99 Mines");
		        b3.addActionListener(new StartGameAction(this, new MineGrid(16, 30,99)));
		        toppanel.add(input,BorderLayout.NORTH);
                input.add(startlabel,RIGHT_ALIGNMENT);
                toppanel.add(southpanel,BorderLayout.SOUTH);
                southpanel.add(defaultvaluespanel,BorderLayout.SOUTH);
		        defaultvaluespanel.add(b);
		        defaultvaluespanel.add(b2);
		        defaultvaluespanel.add(b3);
                southpanel.add(subsouthpanel,BorderLayout.NORTH);
                subsouthpanel.add(defaultlabel);
                add(toppanel);
		        setSize(500, 500);
		        setDefaultCloseOperation(EXIT_ON_CLOSE);
		        setResizable(true);
                
	            pack();
	        
	    }
	   

	   

}

