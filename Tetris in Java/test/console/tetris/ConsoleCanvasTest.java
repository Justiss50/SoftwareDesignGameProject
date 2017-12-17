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
public class ConsoleCanvasTest {

    public ConsoleCanvas canvas;
    public ConsoleCanvas ConsoleCanvas;
    public ConsoleCanvas ConsoleCanvas1;

    public ConsoleCanvasTest() {
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

    @Test // checks if hashcode is identical
    public void testGetObject() {

        ConsoleCanvas = ConsoleCanvas.getObject();
        ConsoleCanvas1 = ConsoleCanvas1.getObject();
        assertEquals(ConsoleCanvas.hashCode(), ConsoleCanvas1.hashCode());
    }

    @Test// checks if reset works
    public void testReset() {
        canvas = ConsoleCanvas.getObject();
        canvas.gridSet(5, 5, 1);
        canvas.reset();
        int counter = 0;
        for (int i = 0; i < canvas.length(); i++) {
            for (int j = 0; j < canvas.length(i); j++) {
                if (canvas.grid(i, j) != 0) {
                    counter++;
                }
            }
        }
        assertEquals(0, counter);
    }
}
