/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.util.*;

/**
 * Grid of the Game, having the utilities of the game,
 * such as settings
 *
 * @author Dalibor markovic ana tasos kremmidas
 */
public final class MineGrid extends JFrame implements ActionListener {
    private JMenuBar menubar; // menubar
    private JPanel gridpanel; // main panel of the frame
    private JMenu Game; // menu for setting
    private JButton Scores; // button for showing the hishscores
    private JMenuItem New, exit; // Menu Item for going back to menu

    int rows;
    int columns;
    int mines;
    JFrame StartMenu;
    Logic_Grid board;
    private int Mines;
    private int X;
    private int Y;
    Timer closeGame = new Timer(10,null);
    /**
     * Constructor of the class.
     * Calls initCompo to initialiaze the panel.
     * 
     * @param rows,    total rows of the board
     * @param columns, total columns of the board
     * @param mines,   total mines in the board
     */
    public MineGrid(int rows, int columns, int mines) {
        super();

        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        initCompo(this.rows, this.columns, this.mines);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Initializes the main panel of the game.
     * Calls GUI_Grid for the panel of minesweeper(the actual grid of the game).
     * 
     * @param x,     total rows of the board
     * @param y,     total columns of the board
     * @param mines, total mines of the board
     */
    public void initCompo(int x, int y, int mines) {
        setLayout(new BorderLayout());

        this.Mines = mines;
        this.X = x;
        this.Y = y;
        menubar = new JMenuBar();
        gridpanel = new JPanel();

        GUI_Grid grid = new GUI_Grid(this.X, this.Y, this.Mines); // creates the grid of the main game of minesweeper
        gridpanel.add(grid, BorderLayout.NORTH);

        Game = new JMenu("Game");

        Scores = new JButton("Scores");
        Scores.addActionListener(this);// adds actionListener
        Scores.setActionCommand("Scores"); // sets actionCommand when called

        New = new JMenuItem("Settings");
        New.addActionListener(this); // adds actionListener
        New.setActionCommand("Settings"); // sets actionCommand when called

        exit = new JMenuItem("Exit");
        exit.addActionListener(this); // adds actionListener
        exit.setActionCommand("exit"); // sets actionCommand when called

        add(menubar, BorderLayout.NORTH);

        New.setToolTipText("Create new Default Game");
        menubar.add(Game);
        menubar.add(Scores);
        Game.add(New);
        Game.add(exit);

        add(gridpanel, BorderLayout.CENTER);

        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
        // setSize(x+y*40,x+y*45);
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

        // using switch statement for every button that we use
        switch (command) {

            case "Settings":
                dispose();
                removeAll();
                JFrame newmenu = new StartMenu();
                newmenu.setVisible(true);
                break;
            case "exit":
                    doClose();
                    break;
            case "Scores":
                try {
                    String filepath = "./highScores.txt";

                    // ###############//#endregion
                    FileReader myFileread = null;
                    BufferedReader buffreader = null;
                    ArrayList<String> readfilearray = new ArrayList<>();
                    // ###############//#endregion
                    ArrayList<String> arr = readfile(myFileread, buffreader, readfilearray, filepath);
                    // ##############//#endregion

                    /*
                     * Just printing the return value to make sure!
                     */
                    // arr.forEach(line -> System.out.println(line));

                    JDialog popupMenu = new JDialog(StartMenu);
                    popupMenu.setSize(new Dimension(250, 100));
                    popupMenu.setLayout(new BorderLayout());
                    popupMenu.setLocationRelativeTo(StartMenu);
                    JButton close = new JButton("Close");
                    close.addActionListener(new ActionListener() {
                        private int secs = 0;
                        private int seconds = 0;
                        private int min = 0;
                        Timer timer = new Timer(50, null);

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            timer = new Timer(1, new ActionListener() {
                                float opacity = 1;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    opacity -= 0.1;
                                    if (opacity < 0) {
                                        timer.stop();
                                        popupMenu.setVisible(false);
                                    } else {
                                        popupMenu.setSize(new Dimension(popupMenu.getWidth(),
                                                (int) (popupMenu.getHeight() * opacity)));
                                    }

                                }
                            });
                            timer.start();
                        }

                    });
                    JTextArea area = new JTextArea();
                    area.append(arr.toString());
                    popupMenu.add(close, BorderLayout.SOUTH);
                    popupMenu.add(area, BorderLayout.CENTER);
                    popupMenu.setVisible(true);

                } catch (HeadlessException ex) {
                    ex.getMessage();
                }
                

        }

    }

    private void doClose() {
        
        // TODO Auto-generated method stub
        try {

             closeGame = new Timer(10, new ActionListener() {
                  float opacity = 1;

                  @Override
                  public void actionPerformed(ActionEvent ee) {
                      opacity -= 0.1;
                      if (opacity < 0) {
                          closeGame.stop();
                          dispose();
                          removeAll();
                          System.exit(0);
                      } else {
                          gridpanel.setSize(
                                  new Dimension(gridpanel.getWidth(), (int) (gridpanel.getHeight() * opacity)));
                      }

                  }
              });
              closeGame.start();

          } catch (Exception ee) {
              // TODO: handle exception
              System.out.println(ee.getMessage());
          }
    }

    private ArrayList<String> readfile(FileReader myFileread, BufferedReader buffreader,
            ArrayList<String> readfilearray, String filepath) {
        ArrayList<Integer> bestscoresEasy = new ArrayList<>();
        ArrayList<Integer> bestscoresMedium = new ArrayList<>();
        ArrayList<Integer> bestscoresHard = new ArrayList<>();
        ArrayList<Integer> bestscoresExtreme = new ArrayList<>();
        ArrayList<String> finalistScore = new ArrayList<>();

        try {
            myFileread = new FileReader(filepath);
            buffreader = new BufferedReader(myFileread);

            // String strtofile = dif + ":" + name + ":" + score;
            // System.out.println("Number of lines : " + numoflines.count());
            // readfilearray.add(strtofile);
            System.out.println("READING....");

            boolean terminate = true;
            while (terminate) {
                String li = buffreader.readLine();
                if (li != null) {
                    String[] parts = li.split(":");
                    if (parts[0].equalsIgnoreCase("Easy")) {
                        bestscoresEasy.add(Integer.valueOf(parts[2]));
                        Collections.sort(bestscoresEasy);

                    } else if (parts[0].equalsIgnoreCase("Medium")) {
                        bestscoresMedium.add(Integer.valueOf(parts[2]));
                        Collections.sort(bestscoresMedium);
                        bestscoresMedium.get(0);
                    } else if (parts[0].equalsIgnoreCase("Hard")) {
                        bestscoresHard.add(Integer.valueOf(parts[2]));
                        Collections.sort(bestscoresHard);
                        bestscoresHard.get(0);
                    }
                    else if (parts[0].equalsIgnoreCase("Extreme")) {
                        bestscoresExtreme.add(Integer.valueOf(parts[2]));
                        Collections.sort(bestscoresExtreme);
                        bestscoresExtreme.get(0);
                    }

                } else if (li == null) {

                    terminate = false;

                }

            }
            try {
                if (bestscoresEasy.size() != 0) {

                    finalistScore.add("Easy:" + bestscoresEasy.get(0).toString());
                }
                if (bestscoresMedium.size() != 0) {

                    finalistScore.add("Medium:" + bestscoresMedium.get(0).toString());
                }
                if (bestscoresHard.size() != 0) {

                    finalistScore.add("Hard:" + bestscoresHard.get(0).toString());
                }
                if (bestscoresExtreme.size() != 0) {

                    finalistScore.add("Extreme:" + bestscoresExtreme.get(0).toString());
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

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
        return finalistScore;
    }

}
