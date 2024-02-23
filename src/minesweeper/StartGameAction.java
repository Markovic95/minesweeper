package minesweeper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Called in the startMenu hiding the menu frame and making 
 * visible the frame of the game being choosed
 * @author Dalibor markovic and tasos kremidas 
 */
public class StartGameAction  implements ActionListener {
	private final JFrame startMenu;
        
    private final JFrame game;
    
    /**
     * Constructor of the class.
     * Takes the main and the game.
     * @param page, frame of startMenu
     * @param game, frame of Game choosed
     */
    public StartGameAction(JFrame page, JFrame game) {
        this.game = game;
        this.startMenu = page;
    }

    /**
     * When mouse is clicked startMenu is not visible 
     * and main game becomes visible
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.startMenu.setVisible(false);
        this.game.setVisible(true);
    }

}
