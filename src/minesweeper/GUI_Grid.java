package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * In this class we create the main panel of the game, we create the interface
 * of the game, with all the buttons and menu items, labels of the number of
 * mines,flags,time and lives left.
 *
 * @author Dalibor markovic and tasos kremmidas
 */
final class GUI_Grid extends JPanel {

    public int minecount;
    public int x;
    public int y;
    public int mins;

    boolean gameOver = false; // state of the game
    int lifecounter = 3; // life counter
    int flagcounter = 0; // flag counter
    int minecounter = 0; // mine counter

    JButton[][] button; // array of buttons in the board

    char[][] mines;

    Color background = new Color(236, 236, 236); // color for background
    Color defaultBg = new JButton().getBackground();// gets background of a JButton

    public String mine;
    public Logic_Grid logic_grid;
    private JPanel status; // panel for time and lives
    private JPanel status2; // panel for mines and flags

    private JLabel time, live, narkes, FlagS; // JLabel for time,lives,mines,flags
    private Timer timer; // timer for the game
    private int secs = 0;
    private int seconds = 0;
    private int min = 0;
    private int validatecounter = 0;
    private int indexer, trueminecount = 0;
    private int invalid = 0;
    private int facingdowncount = 0;
    private int facingdown = 0;

    // ImageIcon for all the images of the buttons
    ImageIcon icon0 = new ImageIcon((getClass().getResource("/images/0.png")));
    ImageIcon icon1 = new ImageIcon((getClass().getResource("/images/1.png")));
    ImageIcon icon2 = new ImageIcon((getClass().getResource("/images/2.png")));
    ImageIcon icon3 = new ImageIcon((getClass().getResource("/images/3.png")));
    ImageIcon icon4 = new ImageIcon((getClass().getResource("/images/4.png")));
    ImageIcon icon5 = new ImageIcon((getClass().getResource("/images/5.png")));
    ImageIcon icon6 = new ImageIcon((getClass().getResource("/images/6.png")));
    ImageIcon icon7 = new ImageIcon((getClass().getResource("/images/7.png")));
    ImageIcon icon8 = new ImageIcon((getClass().getResource("/images/8.png")));
    ImageIcon bomb = new ImageIcon((getClass().getResource("/images/bomb.png")));
    ImageIcon facingDown = new ImageIcon((getClass().getResource("/images/facingDown.png")));
    ImageIcon flagged = new ImageIcon((getClass().getResource("/images/flagged.png")));

    // Array ImageIcon to add all the icons
    ImageIcon icontable[] = new ImageIcon[10];

    /**
     * Constructor of the Class depending of the choice of the player, calls
     * initialiaze to create all the buttons and their abilities
     *
     * @param x, width of the board
     * @param y, height of the board
     * @param mines , number of mines
     */
    public GUI_Grid(int x, int y, int mines) {
        super();
        this.x = x;
        this.y = y;
        this.minecount = mines;

        icontable();
        initialize(this.x, this.y, this.minecount);

    }

    /**
     * Create an array of all the available images for the game, scales image
     * for facing down buttons
     */
    public void icontable() {
        icontable[0] = icon0;
        icontable[1] = icon1;
        icontable[2] = icon2;
        icontable[3] = icon3;
        icontable[4] = icon4;
        icontable[5] = icon5;
        icontable[6] = icon6;
        icontable[7] = icon7;
        icontable[8] = icon8;
        icontable[9] = bomb;

        Image img = facingDown.getImage();
        Image newimg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        facingDown = new ImageIcon(newimg);
    }

    /**
     * Initialize creates the board as buttons using gridlayout, adding
     * actionlistener and mouselistener to its button, adding two panels, one
     * top of the board for the number of mines and flags and one on the bottom
     * for the time for the beginning of the game and lives left
     *
     * @param x, width of the board
     * @param y. height of the board
     * @param mines , number of mines on the board
     */
    public void initialize(int x, int y, int mines) {
        this.x = x;
        this.y = y;
        this.mins = mines;

        int i, j;
        // creates an object type Logic_Grid with x =width , y= height, mins = mines
        logic_grid = new Logic_Grid(x, y, this.mins);
        mineindexervalue();
        // main panel of the board, as borderlayout
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        // panel for all the buttons,as gridlayout with x= width , y= height
        JPanel grid = new JPanel();

        grid.setLayout(new GridLayout(this.x, this.y));
        // two status panels with flowlayout, one with labels: time and lives, and the
        // other one with labels: mines and flags
        // status South on the main panel, and status2 north of the main panel
        status = new JPanel();
        status2 = new JPanel();
        status.setLayout(new FlowLayout());
        status.setLayout(new FlowLayout());

        time = new JLabel("Time:");
        live = new JLabel("Lives:" + lifecounter);
        narkes = new JLabel("Mines:" + trueminecount);
        FlagS = new JLabel("Flags" + flagcounter);
        status.add(time);
        status.add(live);

        status2.add(narkes);
        status2.add(FlagS);

        // 2d array of buttons x = rows , y = columns
        button = new JButton[this.x][this.y];

        // mins takes the values of the mines in the board
        mins = this.x * this.y - (logic_grid.getmines());

        // 2 nested for loops of x = rows , y= columns
        // so we can add to its button in the board a size,
        // mouselistener, actionlistener, setting the icon as a facing down cell(we dont
        // know whats underneath)
        // and adding its button to the panel
        for (i = 0; i < this.x; i++) {
            for (j = 0; j < this.y; j++) {
                button[i][j] = new JButton();
                button[i][j].setPreferredSize(new Dimension(20, 20));

                button[i][j].addMouseListener(new MyMouseListener());

                button[i][j].addActionListener(new MyActionListener());

                button[i][j].setIcon(facingDown);
                grid.add(button[i][j]);

            }
        }
        main.add(grid, BorderLayout.CENTER);
        main.add(status, BorderLayout.SOUTH);
        main.add(status2, BorderLayout.NORTH);
        keeptime();
        add(main);
        // setPreferredSize(new Dimension((this.x+this.Y)*8, (this.x+this.Y)*12));

    }

    /**
     * This method creates the timer of the game, and updating the label "Time:"
     * its time its changes
     */
    public void keeptime() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                secs++;
                seconds++;

                if (seconds == 60) {
                    min++;
                    seconds = 0;

                }
                time.setText("Time:" + String.valueOf(min) + "." + seconds);

            }
        });
    }

    /**
     * Gets the values of cells without a mine and how many mines are in the
     * board, substracting (width*height) - (sum of cells without a mine)
     */
    public void mineindexervalue() {

        // get the number of cells without a mine on them
        indexer = logic_grid.getmines();

        // true mine count of the Math.floorDiv gave us
        trueminecount = (this.x * this.y) - indexer;

    }

    /**
     * Method keeping a counter of how many cells are facing down and have a
     * mine underneath them so we can cross check it with a sum of cells opened
     * without mine
     */
    public void facingdownmeter() {

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                if (button[i][j].getIcon().equals(facingDown) && logic_grid.valueofcell(i, j, logic_grid.getMine())) {

                    this.facingdowncount++;

                }

            }
        }
    }

    /**
     * Method that keeps track of how many cells are still not opened
     */
    public void facingblank() {

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                if (button[i][j].getIcon().equals(facingDown)) {

                    this.facingdown++;

                }

            }

        }
    }

    /**
     * Keeps track of a counter when a cell with coordinates [x][y], is a mine
     * and if its not adds on the attribute invalid
     *
     * @param x, specific row of the board
     * @param y, specific column of the board
     */
    public void invalidcounteradd(int x, int y) {

        if (!logic_grid.valueofcell(x, y, logic_grid.getMine())) {
            invalid++;

        }

    }

    /**
     * Keeps track of a counter when a cell with coordinates [x][y], is a mine
     * and if it is, it substracts one from the attribute invalid
     *
     * @param x, specific row of the board
     * @param y, specific column of the board
     */
    public void invalidcounterremove(int x, int y) {
        if (!logic_grid.valueofcell(x, y, logic_grid.getMine())) {
            invalid--;

        }

    }

    /**
     * Gets called when we put a flag on a cell to check if its a mine
     * underneath it. If it is, it adds one to validatecounter(counter for how
     * many flags are correctly placed on top of bombs). If validatecounter is
     * equals to how many mines are in the board, player wins the game and timer
     * stops
     *
     * @param x , specific row of the board
     * @param y, specific column of the board
     */
    public void validcounteradd(int x, int y) {

        if (logic_grid.valueofcell(x, y, logic_grid.getMine())) {
            validatecounter++;

            if (validatecounter == trueminecount && invalid == 0) {
                timer.stop();
                win_end();
            }
        }
    }

    /**
     * Gets called when we remove a flag on a cell and its a mine underneath it.
     * Then we substract oen from the validatecounter(counter for how many flags
     * are correctly placed on top of bombs).
     *
     * @param x , specific row of the board
     * @param y, specific column of the board
     */
    public void validcounterremove(int x, int y) {

        if (logic_grid.valueofcell(x, y, logic_grid.getMine())) {
            validatecounter--;

        }
    }

    /**
     * Reveals the button in the specific coordinates if its not a mine.
     *
     * @param row, row of the board
     * @param col, column of the board
     */
    public void show_neighbors(int row, int col) {

        if (logic_grid.valueofcell(row, col, ' ')) {
            emptycell(row, col);

        } else {

            if (logic_grid.border(row, col) && !logic_grid.valueofcell(row, col, logic_grid.getMine())) {
                button[row][col].setText(logic_grid.numOfneighbors(row, col));
                setneighbors(row, col);
            }
        }
    }

    /**
     * Reveals all mines in the board. Is called when game is over and player
     * cause lost all lives.
     */
    public void narkes() {
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                if (logic_grid.valueofcell(i, j, logic_grid.mine)) {
                    // if the buttons is flagged sets icons as bomb
                    if (button[i][j].getIcon().equals(logic_grid.flag) == false) {
                        button[i][j].setIcon(bomb);
                        // if the buttons is not flagged sets icon as bomb
                    } else if (logic_grid.valueofcell(i, j, logic_grid.getMine())
                            && button[i][j].getIcon().equals(logic_grid.flag)) {

                        button[i][j].setIcon(bomb);
                    }
                }
            }
        }
    }

    /**
     * When called it reveals all the blank buttons and outer layer of hints
     *
     * @param row, row of the board
     * @param col, column of the boar
     */
    public void emptycell(int row, int col) {
        if (!button[row][col].getBackground().equals(defaultBg)) {
            button[row][col].setIcon(icon0);
            button[row][col].setEnabled(false);
            return;
        }
        if (logic_grid.valueofcell(row, col, ' ')) {
            button[row][col].setBackground(background);
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (logic_grid.valueofcell(i, j, ' ')) {
                        emptycell(i, j);
                    } else {
                        show_neighbors(i, j);
                    }
                }
            }
        }
    }

    /**
     * We call this method when player opens all the cells without a mine on
     * them or flags all the mines in the board. When called, an InputDialog
     * thats asks as our name so it can save it in the file of highscores. If
     * statements to check depending on the width of the board if the difficulty
     * is easy , medium or hard.
     *
     */
    public void win_end() {
        JFrame f = new JFrame();
        String name = JOptionPane.showInputDialog(f, "Enter Name");

        if (x == 9 && y == 9) {
            min += min + secs;
            String dif = "Easy";
            highScores(dif, name, min);

        }
        if (x == 16 && y == 16) {
            min += min + secs;
            String dif = "Medium";
            highScores(dif, name, min);

        }
        if (y == 30 && x==16) {
            min += min + secs;
            String dif = "Hard";
            highScores(dif, name, min);

        }
        System.out.print("##########################\n"
                + "YOU ARE THE WINNER\n"
                + "##########################\n");

        gameOver = true;

    }

    /**
     * Method that check the number of neighbors of a cell in [row][col] and
     * then sets a specific image using switch/case statements
     *
     * @param row, row of the board
     * @param col. columns of the board
     */
    public void setneighbors(int row, int col) {
        String currentPiece = logic_grid.numOfneighbors(row, col);
        switch (currentPiece) {
            case "1":
                button[row][col].setIcon(icon1);
                button[row][col].setText("");
                break;
            case "2":
                button[row][col].setIcon(icon2);
                button[row][col].setText("");
                break;
            case "3":
                button[row][col].setIcon(icon3);
                button[row][col].setText("");
                break;
            case "4":
                button[row][col].setIcon(icon4);
                button[row][col].setText("");
                break;
            case "5":
                button[row][col].setIcon(icon5);
                button[row][col].setText("");
                break;
            case "6":
                button[row][col].setIcon(icon6);
                button[row][col].setText("");
                break;
            case "7":
                button[row][col].setIcon(icon7);
                button[row][col].setText("");
                break;
            case "8":
                button[row][col].setIcon(icon8);
                button[row][col].setText("");
                break;
        }
    }

    /**
     * Set or unsets a flag depending if the cell in [row][col] is flagged or no
     *
     * @param row, row of the board
     * @param col. column of the board
     */
    public void set_unset_flag(int row, int col) {
        // checks if a button of the board is not a flag
        if (!button[row][col].getIcon().equals(logic_grid.flag)) {
            for (int i = 0; i < button.length; i++) {
                for (int j = 0; j < button[0].length; j++) {

                    for (int h = 0; h < icontable.length; h++) {
                        if (button[row][col].getIcon().equals(icontable[h])) // check if button is equal an image 1 to 8 or mine
                        // if it is return nothing, exits the method
                        {
                            return;
                        }
                    }
                }

            }
            // sets icon of the button a flag, increments flag counter, decrements mines
            button[row][col].setIcon(logic_grid.flag);
            flagcounter++;
            mins--;

            // increments invalid counter
            invalidcounteradd(row, col);
            // increments valid counter
            validcounteradd(row, col);

            // shows the flags and mines on the board
            FlagS.setText("Flags" + String.valueOf(flagcounter));
            narkes.setText("Mines:" + String.valueOf(mins));
            button[row][col].setForeground(Color.RED);

            // checks if the button is flagged
        } else if (button[row][col].getIcon().equals(logic_grid.flag)) {
            // sets icon of button facing down
            button[row][col].setIcon(facingDown);

            // decrements the flag counter when we take out a flag
            flagcounter--;

            // decrements invalid counter
            invalidcounterremove(row, col);

            // decrements valid counter
            validcounterremove(row, col);

            /**
             * increments mines
             *  */ 
            mins++;

            /**
             * Sets the flag as flag counter
             * */ 
            FlagS.setText("Flags " + String.valueOf(flagcounter));

            /**
             * Sets the mines as mines
             * 
             * */ 
            narkes.setText("Mines:" + String.valueOf(mins));

        }
    }

    /**
     * MouseListener for flagging the cells
     *
     */
    public class MyMouseListener implements MouseListener {

        /**
         * Called when the right click is pressed
         *
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {

            // on mouse event check if game is over before executing
            if (gameOver == false) {
                timer.start();

                for (int i = 0; i < button.length; i++) {
                    for (int j = 0; j < button[0].length; j++) {
                        ImageIcon buttonIcon = (ImageIcon) button[i][j].getIcon();
                        if (e.getButton() == 3 && e.getSource() == button[i][j]) {

                            if (!buttonIcon.equals(flagged) || buttonIcon.equals(flagged)) {
                                // Sets or unsets flag on button
                                set_unset_flag(i, j);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * ActionListener when the left click is pressed on the button. Whenever
     * left click is pressed, check if game is over.
     *
     */
    public class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver == false) {

                for (int i = 0; i < logic_grid.getHeight(); i++) {
                    for (int j = 0; j < logic_grid.getWidth(); j++) {
                        facingdowncount = 0;
                        facingdown = 0;
                        facingdownmeter();
                        facingblank();
                        // Checks if ActionEvent source is button and button is not flagged
                        if (e.getSource() == button[i][j] && !button[i][j].getIcon().equals(logic_grid.flag)) {
                            // if the button is a mine when clicked
                            // player lives go down by 1
                            // button icon sets to mob
                            // and number of mines decrements by 1
                            if (logic_grid.valueofcell(i, j, logic_grid.getMine())) {
                                button[i][j].setBackground(Color.YELLOW);
                                mins--;
                                narkes.setText("Mines:" + mins);
                                lifecounter--;
                                button[i][j].setIcon(bomb);
                                validcounteradd(i, j);

                                if (lifecounter > 0) {

                                    live.setText("Lives:" + String.valueOf(lifecounter));

                                    gameOver = false;

                                    // if lifecounter is equals to 0
                                    // timer stops and game is over as a loss
                                } else {
                                    timer.stop();
                                    lifecounter = 0;
                                    live.setText("Lives:" + String.valueOf(lifecounter));
                                    gameOver = true;

                                    narkes();

                                    button[i][j].setBackground(Color.RED);
                                }

                            } else {

                                show_neighbors(i, j);

                            }
                        }
                    }
                }
                // if counter of buttons not turned is equals to buttons not being flipped and
                // have a mine
                // player wins and timer stops
                if (facingdowncount == facingdown) {
                    timer.stop();
                    win_end();
                }
            }
        }
    }

    /**
     * Method called and method win_end is called. Saves existing scores to an
     * ArrayList<String>. Add its entry splitted of spaces, to an array of
     * strings so we can check the diffuculty being the same and if highscore
     * needs to be changed. Calls save to file to save everything back to the
     * file.
     *
     *
     * @param dif, difficulty of the game
     * @param name, name of player
     * @param score, time taken to win
     * @param filepath, file destination to append new win records
     */
    public void highScores(String dif, String name, int score) {
        System.out.println(dif + " " + " " + name + " " + score);
        /**
         * change filepath String to be your destination based on where the highScores.txt is located or feel free to change the code!
         */
        String filepath = "./highScores.txt";

        //################
        FileReader myFileread = null;
        BufferedReader buffreader = null;
        ArrayList<String> readfilearray = new ArrayList<>();
        //################
        readfile(myFileread, buffreader, readfilearray, filepath, dif, name, score);

    }

    private void readfile(FileReader myFileread, BufferedReader buffreader, ArrayList<String> readfilearray, String filepath, String dif, String name, int score) {
        try {
            myFileread = new FileReader(filepath);
            buffreader = new BufferedReader(myFileread);

            // String strtofile = dif + ":" + name + ":" + score;
            //  System.out.println("Number of lines : " + numoflines.count());
            // readfilearray.add(strtofile);
            System.out.println("READING....");
            int i = 0;
            boolean terminate = true;
            while (terminate) {
                String li = buffreader.readLine();
                if (li != null) {
                    readfilearray.add(li);
                    System.out.println(readfilearray.get(i));
                    i++;
                } else if (li == null) {
                    readfilearray.add(dif + ":" + name + ":" + score);
                    terminate = false;

                }

            }
            writefile(filepath, readfilearray);
            gameOver = true;

            // writefile(filepath, readfilearray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffreader.close();
                myFileread.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writefile(String filepath, ArrayList<String> readfilearray) throws IOException {
        //################
        FileWriter myFile = null;
        BufferedWriter buff = null;
        ArrayList<String> filearray = readfilearray;
        //################

        myFile = new FileWriter(filepath);
        buff = new BufferedWriter(myFile);

        for (int i = 0; i < filearray.size(); i++) {
            System.out.println(filearray.get(i));
            buff.write(filearray.get(i));
            buff.write("\n");

        }

        try {
            buff.flush();
            buff.close();
            myFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
