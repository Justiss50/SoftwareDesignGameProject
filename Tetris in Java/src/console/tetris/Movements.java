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

    //Behavioral state design pattern, when its rotation depends on its current position
    //and available rotation actions. at this point is current tetrominoe rotation position
    // https://www.journaldev.com/1751/state-design-pattern-java
    public static void rotate(Tetrominoe Tetrominoe, ConsoleCanvas canvas) {
        boolean isRotationAvailable;
        int partOfTetrominoeIn3x3Matrix = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Tetrominoe.grid(i, j) == 2) {
                    partOfTetrominoeIn3x3Matrix++;
                }
            }
        }
        if (Tetrominoe.grid(1, 1) == 2 && Tetrominoe.grid(1, 2) == 2 && Tetrominoe.grid(2, 1) == 2 && Tetrominoe.grid(2, 2) == 2) {
            partOfTetrominoeIn3x3Matrix = 0;
        }
        Tetrominoe rotated = new Tetrominoe();
        int matrixLength = (partOfTetrominoeIn3x3Matrix == 4) ? 3 : Tetrominoe.length();
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (partOfTetrominoeIn3x3Matrix == 4) {
                    rotated.gridSet(i, j, Tetrominoe.grid(matrixLength - j - 1, i));
                } else {
                    rotated.gridSet(i, j, Tetrominoe.grid(j, i));
                }
            }
        }

        isRotationAvailable = true;
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (i + ConsoleTetris.minoeYpos() < canvas.length() || ConsoleTetris.minoeXpos() + matrixLength < 0 || ConsoleTetris.minoeXpos() + matrixLength > canvas.length(i) - 1) {
                    if (canvas.grid(i + ConsoleTetris.minoeYpos(), j + ConsoleTetris.minoeXpos()) == 1 && rotated.grid(i, j) == 2 && isRotationAvailable == true) {
                        isRotationAvailable = false;
                    }
                } else {
                    isRotationAvailable = false;
                }
            }
        }
        if (isRotationAvailable) {
            Tetrominoe.assignGrid(rotated.fullGrid());// = rotated.grid;
            for (int i = 0; i < matrixLength; i++) {
                for (int j = 0; j < matrixLength; j++) {
                    if (canvas.grid(i + ConsoleTetris.minoeYpos(), j + ConsoleTetris.minoeXpos()) != 1) {
                        canvas.gridSet(i + ConsoleTetris.minoeYpos(), j + ConsoleTetris.minoeXpos(), 0);
                        canvas.gridSet(i + ConsoleTetris.minoeYpos(), j + ConsoleTetris.minoeXpos(), rotated.grid(i, j));//) = rotated.grid[i][j];
                    }
                }
            }
        }
    }

    public static void moveLeft(ConsoleCanvas canvas) {
        boolean isMovementAvailable = true;
        for (int i = 0; i < canvas.length(); i++) {
            if (isMovementAvailable) {
                for (int j = 0; j < canvas.length(i); j++) {
                    if (canvas.grid(i, j) == 2 && isMovementAvailable) {
                        if (j > 0) {
                            if (canvas.grid(i, j - 1) == 1) {
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
            for (int i = 0; i < canvas.length(); i++) {
                for (int j = 0; j < canvas.length(i); j++) {
                    if (canvas.grid(i, j) == 2) {
                        canvas.gridSet(i, j, 0);// = 0;
                        canvas.gridSet(i, j - 1, 2);// = 2;
                    }
                }
            }
            ConsoleTetris.setMinoeXpos(ConsoleTetris.minoeXpos() - 1);
        }
    }

    public static void moveRight(ConsoleCanvas canvas) {
        boolean isMovementAvailable = true;
        for (int i = 0; i < canvas.length(); i++) {
            if (isMovementAvailable) {
                for (int j = canvas.length(i) - 1; j >= 0; j--) {
                    if (canvas.grid(i, j) == 2 && isMovementAvailable) {
                        if (j < canvas.length(i) - 1) {
                            if (canvas.grid(i, j + 1) == 1) {
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
            for (int i = 0; i < canvas.length(); i++) {
                for (int j = canvas.length(i) - 1; j >= 0; j--) {
                    if (canvas.grid(i, j) == 2) {
                        canvas.gridSet(i, j, 0);
                        canvas.gridSet(i, j + 1, 2);
                    }
                }
            }
            ConsoleTetris.setMinoeXpos(ConsoleTetris.minoeXpos() + 1);
        }
    }

    public static void drop(ConsoleCanvas canvas) {
        /* To Do: • immediate
     * drop
         */
    }
}
