/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.tetris;

import java.io.IOException;
import java.util.Scanner;
import tetris.in.java.CanvasManager;

/**
 *
 * @author Justis
 */
public class ConsoleTetris {

    public static Tetrominoe currentTetrominoe = new Tetrominoe();
    public static Tetrominoe nextTetrominoe = new Tetrominoe();
    public static ConsoleCanvas canvas = ConsoleCanvas.getObject();
    static int minoeXpos;//left upper corner minoe grid coordinates (in canvas)
    static int minoeYpos;
    static boolean exit = false;

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) throws IOException {
        Gameplay();
    }*/
    public void gameplay() throws IOException, InterruptedException {
        start();
        update();
    }

    public void start() {
        canvas.reset();
        currentTetrominoe.generate();
        nextTetrominoe.generate();
        addTetrominoeToCanvas(currentTetrominoe, canvas);
        // GUI.Update();
        //canvas.print();
    }

    public void update() throws IOException, InterruptedException {
        int length = 19;
        int width = 8;
        if (!exit) {
            boolean isNewTetraminoeNeeded = false;
            fullLineRemover();
            isNewTetraminoeNeeded = frameUpdate();
            if (isNewTetraminoeNeeded) {
                currentTetrominoe = nextTetrominoe;
                nextTetrominoe.generate();
                addTetrominoeToCanvas(currentTetrominoe, canvas);
            }
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(ConsoleTetris.canvas.grid(i, j));
                }
                System.out.println("");
            }
            System.out.println("---------------------");
            Thread.sleep(250);
            update();
        }
    }

    public static void userInput() throws IOException {
        char option = Character.toUpperCase((char) System.in.read()); // Scans the next token of the input as an int
        switch (option) {
            case InputKey.w:
                Movements.rotate(currentTetrominoe, canvas);
                break;
            case InputKey.a:
                Movements.moveLeft(canvas);
                break;
            case InputKey.s:
                Movements.drop(canvas);
                break;
            case InputKey.d:
                Movements.moveRight(canvas);
                break;
            case 32:
                exit = true;
                break;

        }
    }

    public static boolean frameUpdate() {
        if (!isCollision()) {
            for (int i = canvas.length() - 2; i >= 0; i--) {
                for (int j = 0; j < canvas.length(i); j++) {
                    if (canvas.grid(i, j) == 2) {
                        canvas.gridSet(i, j, 0);
                        canvas.gridSet(i + 1, j, 2);
                    }
                }
            }
            ConsoleTetris.minoeYpos++;
            return false;
        } else {
            for (int i = canvas.length() - 1; i >= 0; i--) {
                for (int j = 0; j < canvas.length(i); j++) {
                    if (canvas.grid(i, j) == 2) {
                        canvas.gridSet(i, j, 1);
                    }
                }
            }
            return true;
        }
    }

    public static boolean isCollision() {
        int pelletsWithoutCollision = 0;
        for (int i = 0; i < canvas.length() - 1; i++) {
            for (int j = 0; j < canvas.length(i); j++) {
                if (canvas.grid(i, j) == 2 && canvas.grid(i + 1, j) != 1) {
                    pelletsWithoutCollision++;
                }
            }
        }
        //System.out.println("Collision Check: " + pelletsWithoutCollision);
        return (pelletsWithoutCollision == 4) ? false : true;
    }

    public static void addTetrominoeToCanvas(Tetrominoe tetrominoe, ConsoleCanvas canvas) {
        int startXIndex = 3;
        int YOffset = 0;// 0 by default
        ConsoleTetris.minoeXpos = startXIndex;
        ConsoleTetris.minoeYpos = YOffset;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                canvas.gridSet(i + YOffset, j + startXIndex, tetrominoe.grid(i, j));
            }
        }
    }

    public static void fullLineRemover() {
        int numbOfNonZeroes = 0;
        for (int i = canvas.length() - 1; i > 4; i--) {
            for (int j = 0; j < canvas.length(i); j++) {
                if (canvas.grid(i, j) == 1) {
                    numbOfNonZeroes++;
                }
            }
            if (numbOfNonZeroes == 8) {
                for (int k = i; k > 0; k--) {
                    for (int j = 0; j < canvas.length(i); j++) {
                        canvas.gridSet(k, j, canvas.grid(k - 1, j));
                        canvas.gridSet(k - 1, j, 1);
                    }
                }

                for (int k = 0; k < 5; k++) { // cleaning insert space
                    for (int j = 0; j < canvas.length(i); j++) {
                        canvas.gridSet(k, j, 0);
                    }
                }
            }
            numbOfNonZeroes = 0;
        }

    }

    public boolean exit() {
        return exit;
    }

    public void setExit(boolean val) {
        exit = val;
    }

    public static int minoeXpos() {
        return minoeXpos;
    }

    public static int minoeYpos() {
        return minoeYpos;
    }

    public static void setMinoeXpos(int val) {
        minoeXpos = val;
    }

    public static void setMinoeYpos(int val) {
        minoeYpos = val;
    }
}
