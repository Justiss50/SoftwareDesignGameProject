/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.tetris;

/**
 *
 * @author Justis
 */
public class Movements {

    public static void Rotate(Tetrominoe Tetrominoe, ConsoleCanvas canvas) {

        boolean isRotationAvailable;
        int partOfTetrominoeIn3x3Matrix = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Tetrominoe.grid[i][j] == 2) {
                    partOfTetrominoeIn3x3Matrix++;
                }
            }
        }
        if (Tetrominoe.grid[1][1] == 2 && Tetrominoe.grid[1][2] == 2 && Tetrominoe.grid[2][1] == 2 && Tetrominoe.grid[2][2] == 2) {
            partOfTetrominoeIn3x3Matrix = 0;
        }
        Tetrominoe rotated = new Tetrominoe();
        int MatrixLength = (partOfTetrominoeIn3x3Matrix == 4) ? 3 : Tetrominoe.grid.length;
        for (int i = 0; i < MatrixLength; i++) {
            for (int j = 0; j < MatrixLength; j++) {
                if (partOfTetrominoeIn3x3Matrix == 4) {
                    rotated.grid[i][j] = Tetrominoe.grid[MatrixLength - j - 1][i];
                } else {
                    rotated.grid[i][j] = Tetrominoe.grid[j][i];
                }
            }
        }

        isRotationAvailable = true;
        for (int i = 0; i < MatrixLength; i++) {
            for (int j = 0; j < MatrixLength; j++) {
                if (i + ConsoleTetris.MinoeYpos < canvas.grid.length || ConsoleTetris.MinoeXpos + MatrixLength < 0 || ConsoleTetris.MinoeXpos + MatrixLength > canvas.grid[i].length - 1) {
                    if (canvas.grid[i + ConsoleTetris.MinoeYpos][j + ConsoleTetris.MinoeXpos] == 1 && rotated.grid[i][j] == 2 && isRotationAvailable == true) {
                        isRotationAvailable = false;
                    }
                } else {
                    isRotationAvailable = false;
                }
            }
        }
        if (isRotationAvailable) {
            Tetrominoe.grid = rotated.grid;
            for (int i = 0; i < MatrixLength; i++) {
                for (int j = 0; j < MatrixLength; j++) {
                    if (canvas.grid[i + ConsoleTetris.MinoeYpos][j + ConsoleTetris.MinoeXpos] != 1) {
                        canvas.grid[i + ConsoleTetris.MinoeYpos][j + ConsoleTetris.MinoeXpos] = 0;
                        canvas.grid[i + ConsoleTetris.MinoeYpos][j + ConsoleTetris.MinoeXpos] = rotated.grid[i][j];
                    }
                }
            }
        }
    }

    public static void MoveLeft(ConsoleCanvas canvas) {
        boolean isMovementAvailable = true;
        for (int i = 0; i < canvas.grid.length; i++) {
            if (isMovementAvailable) {
                for (int j = 0; j < canvas.grid[i].length; j++) {
                    if (canvas.grid[i][j] == 2 && isMovementAvailable) {
                        if (j > 0) {
                            if (canvas.grid[i][j - 1] == 1) {
                                isMovementAvailable = false;
                            }
                        } else {
                            isMovementAvailable = false;
                        }
                    }
                }
            }
        }
        if (isMovementAvailable) {
            for (int i = 0; i < canvas.grid.length; i++) {
                for (int j = 0; j < canvas.grid[i].length; j++) {
                    if (canvas.grid[i][j] == 2) {
                        canvas.grid[i][j] = 0;
                        canvas.grid[i][j - 1] = 2;
                    }
                }
            }
            ConsoleTetris.MinoeXpos--;
        }
    }

    public static void MoveRight(ConsoleCanvas canvas) {
        boolean isMovementAvailable = true;
        for (int i = 0; i < canvas.grid.length; i++) {
            if (isMovementAvailable) {
                for (int j = canvas.grid[i].length - 1; j >= 0; j--) {
                    if (canvas.grid[i][j] == 2 && isMovementAvailable) {
                        if (j < canvas.grid[i].length - 1) {
                            if (canvas.grid[i][j + 1] == 1) {
                                isMovementAvailable = false;
                            }
                        } else {
                            isMovementAvailable = false;
                        }
                    }
                }
            }
        }
        if (isMovementAvailable) {
            for (int i = 0; i < canvas.grid.length; i++) {
                for (int j = canvas.grid[i].length - 1; j >= 0; j--) {
                    if (canvas.grid[i][j] == 2) {
                        canvas.grid[i][j] = 0;
                        canvas.grid[i][j + 1] = 2;
                    }
                }
            }
            ConsoleTetris.MinoeXpos++;
        }
    }

    public static void Drop(ConsoleCanvas canvas) {
        /*
            To Do:
            • immediate drop
         */
    }

}
