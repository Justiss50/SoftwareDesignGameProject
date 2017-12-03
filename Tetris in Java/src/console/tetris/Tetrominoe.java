/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.tetris;

import java.util.Arrays;

/**
 *
 * @author Justis
 */
public class Tetrominoe extends Movements {

    public int[][] grid = new int[4][4]; // for optimization boolean can be used here

    public void reset() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = 0;
            }
        }
    }

    public void print() {
        System.out.println("---TETROMINOE-TEST---");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("");
        }
    }

    public void generate() {
        reset();
        ReturnNewTetrominoe(NextMinoeNumbGenerator());
    }

    private int NextMinoeNumbGenerator() {
        int minOption = 1;
        int maxOption = 7;
        int range = maxOption - minOption;
        return (int) (Math.random() * range) + minOption;
    }

    private void ReturnNewTetrominoe(int shapeNumb) {
        switch (shapeNumb) {
            //well yeah, we can set only specific elements here, but personally it is better to see visually who is who
            case 1:
                grid = new int[][]{
                    {0, 0, 2, 0},
                    {0, 0, 2, 0},
                    {0, 0, 2, 0},
                    {0, 0, 2, 0}};
                break;
            case 2:
                grid = new int[][]{
                    {0, 2, 0, 0},
                    {0, 2, 0, 0},
                    {2, 2, 0, 0},
                    {0, 0, 0, 0}};
                break;
            case 3:
                grid = new int[][]{
                    {0, 2, 0, 0},
                    {0, 2, 0, 0},
                    {0, 2, 2, 0},
                    {0, 0, 0, 0}};
                break;
            case 4:
                grid = new int[][]{
                    {0, 0, 0, 0},
                    {0, 2, 2, 0},
                    {0, 2, 2, 0},
                    {0, 0, 0, 0}};
                break;
            case 5:
                grid = new int[][]{
                    {0, 0, 0, 0},
                    {0, 2, 2, 0},
                    {2, 2, 0, 0},
                    {0, 0, 0, 0}};
                break;
            case 6:
                grid = new int[][]{
                    {2, 2, 2, 0},
                    {0, 2, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}};
                break;
            case 7:
                grid = new int[][]{
                    {0, 0, 0, 0},
                    {2, 2, 0, 0},
                    {0, 2, 2, 0},
                    {0, 0, 0, 0}};
                break;
        }
    }
}
