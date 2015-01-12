/*
    Solving Hyper Sudoku by using heuristic and backtracking  
    
    First, find all possibility of each cell.
    Second, sort the possibility of each cell.
    Third, fill in the solution from the least possibility and using backtracking.
*/
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HyperSudokuSolverHeuristic {

	static int node=0;
	public static void main(String[] args) throws IOException {

		// Read a Sudoku puzzle
		int[][] grid = readAPuzzle(args[0]);
		int[][] freeCellList = getFreeCellList(grid);
		int[][] newCellList = new int[freeCellList.length][2];
		int[] possible_solution = new int[freeCellList.length];
		// start to run		
		possible_solution = findmostcontrains(grid, freeCellList);
		newCellList = BubbbleSort(possible_solution, freeCellList);
		
		if (search(grid, newCellList)) {
			printGrid(grid,args[1]);
		}
	
	}

	/** Search for  solutions */
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
					node++;
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

		// Check whether grid[i][j] is valid in the 3 by 3 box
		if (((i >= 1 && i <= 3) && (j >= 1 && j <= 3))
				|| ((i >= 5 && i <= 7) && (j >= 1 && j <= 3))
				|| ((i >= 1 && i <= 3) && (j >= 5 && j <= 7))
				|| ((i >= 5 && i <= 7) && (j >= 5 && j <= 7))) {
			int r = (i / 4) * 4;
			r += 1;
			int c = (j / 4) * 4;
			c += 1;
			for (int row = r; row < r + 3; row++)
				for (int col = c; col < c + 3; col++) {
					if (row != i && col != j && grid[row][col] == grid[i][j]) {
						// System.out.println("i=  " + i + " j= " + j);
						// printGrid(grid);
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

	public static int[] findmostcontrains(int[][] grid, int[][] freeCellList) {
		int[] possible_solution = new int[freeCellList.length];
		for (int k = 0; k < freeCellList.length; k++) {
			int i = freeCellList[k][0];
			int j = freeCellList[k][1];

			possible_solution[k] = find_contrains(i, j, grid);

		}
		return possible_solution;
	}

	public static int find_contrains(int i, int j, int[][] grid) {
		// TODO Auto-generated method stub
		int count = 0;
		boolean check = false;

		for (int k = 1; k <= 9; k++) {
			check = false;
			for (int column = 0; column < 9; column++) {
				if (check)
					break;
				if (column != j && grid[i][column] == k) {
					count++;
					check = true;
					break;
				}
			}

			// Check whether grid[i][j] is valid at the j's column
			for (int row = 0; row < 9; row++) {
				if (check)
					break;
				if (row != i && grid[row][j] == k) {
					count++;
					check = true;
					break;
				}
			}

			// Check whether grid[i][j] is valid in the 3 by 3 box
			if ((((i >= 1 && i <= 3) && (j >= 1 && j <= 3))
					|| ((i >= 5 && i <= 7) && (j >= 1 && j <= 3))
					|| ((i >= 1 && i <= 3) && (j >= 5 && j <= 7)) || ((i >= 5 && i <= 7) && (j >= 5 && j <= 7)))
					&& !check) {
				int r = (i / 4) * 4;
				r += 1;
				int c = (j / 4) * 4;
				c += 1;

				// System.out.println("!!!!" + r + " " + c);

				for (int row = r; row < (r + 3); row++) {
					for (int col = c; col < (c + 3); col++) {
						if ((row != i && col != j) && grid[row][col] == k) {
							count++;
							check = true;
							break;
						}

					}
				}
			}
		}
		return 9 - count; // The current value at grid[i][j] is valid

	}
	private static int[][] BubbbleSort(int[] possible_solution,
			int[][] freeCellList) {
		// TODO Auto-generated method stub
		int[][] newCellList = new int[freeCellList.length][2];
		int n = possible_solution.length;

		for (int i = 0; i < freeCellList.length; i++) {
			newCellList[i][0] = freeCellList[i][0];
			newCellList[i][1] = freeCellList[i][1];
		}

		int tmp;
		for (int c = 0; c < (n - 1); c++) {
			for (int d = 0; d < n - c - 1; d++) {
				if (possible_solution[d] > possible_solution[d + 1]) {
					tmp = possible_solution[d];
					possible_solution[d] = possible_solution[d + 1];
					possible_solution[d + 1] = tmp;

					tmp = newCellList[d][0];
					newCellList[d][0] = newCellList[d + 1][0];
					newCellList[d + 1][0] = tmp;

					tmp = newCellList[d][1];
					newCellList[d][1] = newCellList[d + 1][1];
					newCellList[d + 1][1] = tmp;

				}
			}
		}

		return newCellList;
	}
	/** Read a Sudoku puzzle from the keyboard */
	public static int[][] readAPuzzle(String file) {
		int[][] grid = new int[9][9];
		// Create a Scanner
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
				// System.out.println(tmp);
				if (tmp.equalsIgnoreCase("-"))
					grid[i][j] = 0;
				else
					grid[i][j] = Integer.parseInt(tmp);
			}
		}

		return grid;

	}

	/** Obtain a list of free cells from the puzzle */
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
