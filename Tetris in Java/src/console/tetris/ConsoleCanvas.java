/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.tetris;

import java.awt.event.KeyListener;

/**
 *
 * @author Justis
 */
public class ConsoleCanvas {

    int x = 8;
    int y = 19;
    int grid[][] = new int[y][x];// 19x8 (8 + 4 to y axis, to add whole tetriminoe instantly)
    int offset = 4;
    private static ConsoleCanvas instance;

    private ConsoleCanvas() {
    }

    ;
    //Sinlgeton creational pattern
    //https://www.javatpoint.com/singleton-design-pattern-in-java
    public static ConsoleCanvas getObject() {
        if (instance == null) {
            instance = new ConsoleCanvas();
        }
        return instance;
    }

    public void reset() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                gridSet(i, j, 0);
            }
        }
    }

    public void print() {
        System.out.println("---XPOS: " + ConsoleTetris.minoeXpos());
        System.out.println("---YPOS: " + ConsoleTetris.minoeYpos());
        for (int i = offset; i < y; i++) {
            for (int j = 0; j < x; j++) {
                System.out.print(grid(i, j));
            }
            System.out.println("");
        }
    }

    /*  public void addKeyListener(KeyListener keyListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int grid(int x, int y) {
        return grid[x][y];
    }

    public void gridSet(int x, int y, int val) {
        grid[x][y] = val;
    }

    public int length() {
        return grid.length;
    }

    public int length(int yAxis) {
        return grid[0].length;
    }

}
