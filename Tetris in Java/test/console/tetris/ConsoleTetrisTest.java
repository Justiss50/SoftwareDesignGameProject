/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.tetris;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Justis
 */
public class ConsoleTetrisTest {

    public ConsoleTetrisTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    public static ConsoleCanvas canvas = ConsoleCanvas.getObject();
    public ConsoleTetris ConsoleTetris = new ConsoleTetris();

    @Test // creating full line and checking if they are removed properly
    public void testFullLineRemover() {
        System.out.println("fullLineRemover");
        int counter = 0;
        for (int i = 9; i < 10; i++) {
            for (int j = 0; j < canvas.length(i); j++) {
                canvas.gridSet(i, j, 1);
                counter++;
            }
        }
        ConsoleTetris.fullLineRemover();
        for (int i = 9; i < 10; i++) {
            for (int j = 0; j < canvas.length(i); j++) {
                if (canvas.grid(i, j) == 0) {
                    counter--;
                }
            }
        }
        assertEquals(0, counter);
    }

    @Test // creating line with 7 elements and cheking if it is not removed
    public void testFullLineRemover2() {
        System.out.println("fullLineRemover");
        int counter = 0;
        for (int i = 9; i < 10; i++) {
            for (int j = 0; j < canvas.length(i) - 1; j++) {
                canvas.gridSet(i, j, 1);
            }
        }
        ConsoleTetris.fullLineRemover();
        for (int i = 9; i < 10; i++) {
            for (int j = 0; j < canvas.length(i); j++) {
                if (canvas.grid(i, j) == 1) {
                    counter++;
                }
            }
        }
        assertEquals(7, counter);
    }

}
