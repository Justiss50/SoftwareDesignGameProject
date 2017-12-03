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

    public int grid[][] = new int[19][8];// 19x8 (8 + 4 to y axis, to add whole tetriminoe instantly)

    public void Reset() {
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public void print() {
        System.out.println("---XPOS: " + ConsoleTetris.MinoeXpos);
        System.out.println("---YPOS: " + ConsoleTetris.MinoeYpos);
        for (int i = 4; i < 19; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("");
        }
    }

    public void addKeyListener(KeyListener keyListener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
