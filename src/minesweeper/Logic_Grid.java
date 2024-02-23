/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minesweeper;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;




/**
 * In this class we create the board as a back end of the game interface
 * @author Dalibor markovic ana tasos kremidas 
 */
public final class Logic_Grid {

	private final  int width;
	private final  int height;
	
	private final  int mineCount;
    private int index=0;
        
	ImageIcon flag = new ImageIcon((getClass().getResource("/images/flagged.png")));
        
	public char mine = 'B';
	
	
	private char[][] logic_grid;
    
	
	/**
     * Constructor of the class
     * @param x , height of the board
     * @param y, width of the board
     * @param mins, how many mines are gonna be on the board
     * 
     * Post conditions: creates the board as an array knowing where everything is , mines etc.
     * */  
	public Logic_Grid(int x,int y,int mins) {
            this.height=x;
            this.width=y;
            this.mineCount=mins;
             set_reset();
	}
    
	/**
	 * Getter for Width
	 * @return the width of the board
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Getter for Height
	 * @return height of the board
	 */
	public int getHeight() {
		return this.height;
	}
	
	
	/**
	 * We check if a cell has neighbor mines 
	 * and we return the number of neighbors it has, as it takes that values as an image
	 * @param row, row of the board
	 * @param col, column of the board
	 * @return neighbor, how many neighbor mines a cell has
	 */
	public int neighborsOfMines(int row, int col) {
		int neighbor = 0;

		
		for (int i=row-1; i<=row+1; i++) {
			for (int j=col-1; j<=col+1; j++) {
				if (valueofcell(i, j, this.mine)) {
					neighbor++;
				}
			}
		}
		return neighbor;
	}
	
	
	/**
	 * border checks if the coordinates of [row][col] are within bounds of the board
	 * @param row, row of the board
	 * @param col. column of the board
	 * @return true/false
	 */
	public boolean border(int row, int col) {
		if (row >= 0 && row < this.height && col >= 0 && col < this.width) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Return the number of the cell as a string, which indicates how many mines are its neighbors
	 * @param row, row of the board
	 * @param col, column of the board
	 * @return the character(number of neighbors) of the cell
	 */
	public String numOfneighbors(int row, int col) {
		return Character.toString(this.logic_grid[row][col]);
	}

	
	/**
	 * Sets the board, where the mines are going to be using random and then Math.floorDiv 
	 * and finding the neighbors of its cell, scales image for  
	 * 
	 */
	public void set_reset() {
		
		Image img1 = flag.getImage();
        Image newflag = img1.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        flag = new ImageIcon(newflag);
        
		logic_grid = new char[this.height][this.width];

		int[] mineIndexes = new Random().ints(0, (this.width*this.height-1)).limit(mineCount).distinct().toArray();
		 
		for (int i=0; i<mineIndexes.length; i++) {
			this.logic_grid[Math.floorDiv(mineIndexes[i], this.width)][mineIndexes[i] % this.width] = this.mine;
                        
		}
                
		for (int i=0; i<this.logic_grid.length; i++) {
			for (int j=0; j<this.logic_grid[0].length; j++) {
				if (this.logic_grid[i][j] != mine) {
                                    index++;
                                     
					int neighbor = neighborsOfMines(i, j);
					if (neighbor != 0) {
                        this.logic_grid[i][j] = (char)(neighbor + 48);
					} else {
						this.logic_grid[i][j] = ' ';
					}
				}
			}
		}
               
	}
	
    /**
     * Checks if @param row,col are within the bounds of the board 
     * and if the @param piece is ture in array logic_grid
     * @param row, row of the board
     * @param col, column of the board
     * @param piece, character of the cell(which tell us if its a bomb,empty space, or number )
     * @return true/false
     */
    public boolean valueofcell(int row, int col, char piece) {
		if (border(row, col) && this.logic_grid[row][col] == piece) {
			return true;
		} else {
			return false;
		}
	}
    
   /**
    * getter for mines
    * @return mines
    */
	public char getMine() {
		return this.mine;
	}
	
	/**
	 * Getter for flag
	 * @return flag
	 */
	public ImageIcon getFlag() {
		return this.flag;
	}	
	
	/**
	 * Getter of index, index being the cells without a bomb
	 * @return index
	 */
    public int getmines(){
        return this.index;
    }
        
}

