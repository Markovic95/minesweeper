
package minesweeper;

/**
 * Main method for the application.
 * @author Dalibor and tasos kremidas
 */
public class MineSweeper {

    /**
     * Main method of the programm. Calls startMenu
     * so it can start the app. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                java.awt.EventQueue.invokeLater(new Runnable(){;
        @Override
        public void run(){
            new StartMenu().setVisible(true);
        
        }
    });   
        }
}
