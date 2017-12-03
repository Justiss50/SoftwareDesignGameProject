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
    public static ConsoleCanvas canvas = new ConsoleCanvas();
    public static int MinoeXpos;//left upper corner minoe grid coordinates (in canvas)
    public static int MinoeYpos;
    public static boolean exit = false;

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) throws IOException {
        Gameplay();
    }*/
    public void Gameplay(CanvasManager GUI) throws IOException {
        Start(GUI);
        Update(GUI);
    }

    public void Start(CanvasManager GUI) {
        canvas.Reset();
        currentTetrominoe.generate();
        nextTetrominoe.generate();
        AddTetrominoeToCanvas(currentTetrominoe, canvas);
        GUI.Update();
        //canvas.print();
    }

    public void Update(CanvasManager GUI) throws IOException {
        boolean isNewTetraminoeNeeded = false;
        isNewTetraminoeNeeded = FrameUpdate();
        if (isNewTetraminoeNeeded) {
            currentTetrominoe = nextTetrominoe;
            nextTetrominoe.generate();
            AddTetrominoeToCanvas(currentTetrominoe, canvas);
            //canvas.print();
        }
        GUI.Update();
    }

    public static void UserInput() throws IOException {
        char option = Character.toUpperCase((char) System.in.read()); // Scans the next token of the input as an int
        switch (option) {
            case InputKey.w:
                Movements.Rotate(currentTetrominoe, canvas);
                break;
            case InputKey.a:
                Movements.MoveLeft(canvas);
                break;
            case InputKey.s:
                Movements.Drop(canvas);
                break;
            case InputKey.d:
                Movements.MoveRight(canvas);
                break;
            case 32:
                exit = true;
                break;

        }
    }

    public static boolean FrameUpdate() {
        if (!IsCollision()) {
            for (int i = canvas.grid.length - 2; i >= 0; i--) {
                for (int j = 0; j < canvas.grid[i].length; j++) {
                    if (canvas.grid[i][j] == 2) {
                        canvas.grid[i][j] = 0;
                        canvas.grid[i + 1][j] = 2;
                    }
                }
            }
            ConsoleTetris.MinoeYpos++;
            return false;
        } else {
            for (int i = canvas.grid.length - 1; i >= 0; i--) {
                for (int j = 0; j < canvas.grid[i].length; j++) {
                    if (canvas.grid[i][j] == 2) {
                        canvas.grid[i][j] = 1;
                    }
                }
            }
            return true;
        }
    }

    public static boolean IsCollision() {
        int pelletsWithoutCollision = 0;
        for (int i = 0; i < canvas.grid.length - 1; i++) {
            for (int j = 0; j < canvas.grid[i].length; j++) {
                if (canvas.grid[i][j] == 2 && canvas.grid[i + 1][j] != 1) {
                    pelletsWithoutCollision++;
                }
            }
        }
        //System.out.println("Collision Check: " + pelletsWithoutCollision);
        return (pelletsWithoutCollision == 4) ? false : true;
    }

    public static void AddTetrominoeToCanvas(Tetrominoe tetrominoe, ConsoleCanvas canvas) {
        int startXIndex = 3;
        int YOffset = 0;// 0 by default
        ConsoleTetris.MinoeXpos = startXIndex;
        ConsoleTetris.MinoeYpos = YOffset;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                canvas.grid[i + YOffset][j + startXIndex] = tetrominoe.grid[i][j];
            }
        }
    }
}
