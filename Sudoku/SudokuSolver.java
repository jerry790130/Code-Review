/*
    Solving Sudoku by using Backtracking 
*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuSolver {

	public static void main(String[] args) throws IOException {

		// Read a Sudoku puzzle from input files
		int[][] grid = readAPuzzle(args[0]);
		int[][] freeCellList = getFreeCellList(grid);

		// start to run
		if (search(grid, freeCellList)) {
			printGrid(grid,args[1]);
		}
	}
    /** Search for solutions */
	public static boolean search(int[][] grid, int[][] freeCellList) {

		int k = 0; // Start from the first free cell
		boolean found = false; // Solution found?

		while (!found) {

			int i = freeCellList[k][0];
			int j = freeCellList[k][1];

			if (grid[i][j] == 0) {
				grid[i][j] = 1; // Start with 1
			}

			if (isValid(i, j, grid)) {
				if (k + 1 == freeCellList.length) { // No more free cells
					found = true; // A solution is found
				} else { // Move to the next free cell
					k++;
				}

			} else if (grid[i][j] < 9) {
				grid[i][j] = grid[i][j] + 1; // Check the next possible value
			} else { // grid[i][j] is 9, backtrack
				while (grid[i][j] == 9) {

					grid[i][j] = 0; // Reset to free cell
					if (k == 0) {
						return false; // No possible value
					}

					k--; // Backtrack
					i = freeCellList[k][0];
					j = freeCellList[k][1];
				}
				grid[i][j] = grid[i][j] + 1; // Check the next possible value
			}
		}
		return true; // A solution is found
	}

	/** Check whether grid[i][j] is valid in the grid */
	public static boolean isValid(int i, int j, int[][] grid) {
		// Check whether grid[i][j] is valid at the i's row
		for (int column = 0; column < 9; column++) {
			if (column != j && grid[i][column] == grid[i][j]) {
				return false;
			}
		}

		// Check whether grid[i][j] is valid at the j's column
		for (int row = 0; row < 9; row++) {
			if (row != i && grid[row][j] == grid[i][j]) {
				return false;
			}
		}

		// Check whether grid[i][j] is valid in the 3 by 3 box
		for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {
			for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
				if (row != i && col != j && grid[row][col] == grid[i][j]) {
					return false;
				}
			}
		}

		return true; // The current value at grid[i][j] is valid
	}

	/** Check whether the fixed cells are valid in the grid */
	public static boolean isValid(int[][] grid) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] != 0 && !isValid(i, j, grid)) {
					return false;
				}
			}
		}

		return true; // The fixed cells are valid
	}

	/**Read a Sudoku puzzle from the txt file */
	public static int[][] readAPuzzle(String file) {
		int[][] grid = new int[9][9];
		java.io.File reader = new java.io.File(file);
		Scanner input = null;
		try {
			input = new Scanner(reader);
		} catch (Exception e) {
			System.out.println("The file 'sudokuInput' is not found!");
			System.exit(0);
		}

		input.useDelimiter("[, \\r\\n]+");

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String tmp = input.next();
				if (tmp.equalsIgnoreCase("-"))
					grid[i][j] = 0;
				else
					grid[i][j] = Integer.parseInt(tmp);
			}
		}
		return grid;
	}

	/* Obtain a list of free cells from the puzzle */
	public static int[][] getFreeCellList(int[][] grid) {

		// Determine the number of free cells
		int numberOfFreeCells = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					numberOfFreeCells++;
				}
			}
		}

		// Store free cell positions into freeCellList
		int[][] freeCellList = new int[numberOfFreeCells][2];
		int count = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					freeCellList[count][0] = i;
					freeCellList[count++][1] = j;

				}
			}
		}
		return freeCellList;

	}

    /* Print Results */
	public static void printGrid(int[][] grid,String file) throws FileNotFoundException {
		PrintWriter outfile = new PrintWriter(file);

		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 9; j++) {
				outfile.print(grid[i][j]);
				if (j != 8)
					outfile.print(" ");
			}

			if (i != 8)
				outfile.println();
		}
		outfile.close();
	}


}
