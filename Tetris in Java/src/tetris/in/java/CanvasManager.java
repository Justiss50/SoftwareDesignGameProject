/*
    Tetris_in_JavaFX_b1

    To Do for b2:
        • add scores
        • next tetrominoe should be showed
        • OutOfBounds error when trying to rotate tetrominoe when it's colliding (practically handled, just errors are thrown)
        • add game restart functionality
        • add gameover
 */
package tetris.in.java;

import console.tetris.ConsoleTetris;
import static console.tetris.ConsoleTetris.currentTetrominoe;
import console.tetris.Movements;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.scene.paint.CycleMethod;
import javafx.scene.text.Text;

/**
 *
 * @author Justis
 */
public class CanvasManager extends Application implements EventHandler<KeyEvent> {//, Runnable {

    static GraphicsContext graphicsContext;
    public int[][][] matrix = new int[8][14][2];
    public int[][] walls = new int[3][2];
    ConsoleTetris consoleTetris = new ConsoleTetris();
    public Canvas canvas = new Canvas();
    Text text = new Text();

    public final Runnable updateGUIThread;
    public final Runnable runGameThread;

    public CanvasManager() {
        CanvasManager CanvasManager = this;

        updateGUIThread = new Runnable() {
            public void run() {
                while (!consoleTetris.exit()) {
                    try {
                        update();
                        Thread.sleep(250);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CanvasManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //System.out.println("THREAD2 UPDATE");
                }
            }
        };

        runGameThread = new Runnable() {
            public void run() {
                consoleTetris.start();
                while (!consoleTetris.exit()) {
                    try {
                        consoleTetris.update();
                    } catch (IOException ex) {
                        Logger.getLogger(CanvasManager.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CanvasManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        /*primaryStage.setTitle("Tetris");
        Group root = new Group();
        canvas = new Canvas(400, 600);
        root.getChildren().add(canvas);
        GraphicsContext = canvas.getGraphicsContext2D();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/
        Group root = new Group();
        primaryStage.setTitle("Tetris");
        canvas = new Canvas(400, 600);
        root.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        //Scene scene = new Scene(root, 400, 600);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        setUpGrid();
        clearScreen();

        new Thread(this.updateGUIThread).start();
        new Thread(this.runGameThread).start();
        //new Thread(object.threadB).start();
    }

    @Override
    public void handle(KeyEvent e) {
        /*Note:
          leaving in a separate thread in case that in other platform
          app input will be different, but it'll be able to call right functions.
          GUI input calls game functions without GUI arguments so game logic is not broken
         */
        KeyCode option = e.getCode();
        System.out.println("CODE: " + option);
        if (option.equals(KeyCode.W)) {
            Movements.rotate(currentTetrominoe, consoleTetris.canvas);
        } else if (option.equals(KeyCode.A)) {
            Movements.moveLeft(consoleTetris.canvas);
        } else if (option.equals(KeyCode.S)) {
            Movements.drop(consoleTetris.canvas);
        } else if (option.equals(KeyCode.D)) {
            Movements.moveRight(consoleTetris.canvas);
        } else if (option.equals(KeyCode.ESCAPE)) {
            consoleTetris.setExit(true);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setUpGrid() {
        int matrixLength = 14;
        int matrixWidth = 8;
        int offset = 4;
        int length = 36;
        int xPos = offset / 2 + length;
        int yPos = 0;
        for (int i = 0; i < matrixWidth; i++) {
            xPos += offset;
            for (int j = 0; j < matrixLength; j++) {
                yPos += offset;
                matrix[i][j][0] = yPos;
                yPos += length;
                matrix[i][j][1] = xPos;
            }
            xPos += length;
            yPos = 0;
        }

    }

    public void setUpWalls() {
        Color[] gradientPallete = new Color[]{Color.PURPLE, Color.BLUE};
        Stop[] stops = new Stop[]{new Stop(0, gradientPallete[0]), new Stop(1, gradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient sideWallsColor = new LinearGradient(0, 1, 1, 0, true, CycleMethod.REFLECT, stops);
        Color bottomColor = gradientPallete[0];
        int xLength = 400;
        int yLength = 600;
        int thickness = 36;
        graphicsContext.setFill(sideWallsColor);
        graphicsContext.fillRoundRect(0, 0, thickness, yLength, 0, 0);
        graphicsContext.fillRoundRect(xLength - thickness, 0, thickness, yLength, 0, 0);
        graphicsContext.setFill(bottomColor);
        graphicsContext.fillRoundRect(0, yLength - thickness, xLength, thickness, 0, 0);

    }

    public void testGrid() {

        int matrixLength = 14;
        int matrixWidth = 8;
        Color[] gradientPallete = new Color[]{Color.BLACK, Color.RED};
        Stop[] stops = new Stop[]{new Stop(0, gradientPallete[0]), new Stop(1, gradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient color = new LinearGradient(0, 1, Math.sin(angle), 0, true, CycleMethod.REFLECT, stops);
        graphicsContext.setFill(color);
        int length = 36;
        int arcLength = 20;
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixLength; j++) {
                graphicsContext.fillRoundRect(matrix[i][j][1], matrix[i][j][0], length, length, arcLength, arcLength);
            }
        }
    }

    public void clearScreen() {
        Color[] gradientPallete = new Color[]{Color.BLACK, Color.web("#363d3c")};
        Stop[] stops = new Stop[]{new Stop(0, gradientPallete[0]), new Stop(1, gradientPallete[1])};
        LinearGradient color = new LinearGradient(0, 1, 1, 0, true, CycleMethod.REFLECT, stops);
        graphicsContext.setFill(color);
        graphicsContext.fillRoundRect(0, 0, 400, 600, 0, 0);
        setUpWalls();
    }

    public void update() {
        int YOffset = 5;
        CanvasManager canvas = this;// new CanvasManager();
        canvas.clearScreen();
        Color[] gradientPallete = new Color[]{Color.BLACK, Color.RED};
        Stop[] stops = new Stop[]{new Stop(0, gradientPallete[0]), new Stop(1, gradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient color = new LinearGradient(0, 1, Math.sin(angle), 0, true, CycleMethod.REFLECT, stops);
        graphicsContext.setFill(color);

        int length = 36;
        int arcLength = 20;
        int matrixLength = 14;
        int matrixWidth = 8;

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (consoleTetris.canvas.grid(j + YOffset, i) != 0) {
                    graphicsContext.fillRoundRect(matrix[i][j][1], matrix[i][j][0], length, length, arcLength, arcLength);
                }
            }
        }
        for (int i = 0; i < matrixLength + YOffset; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                System.out.print(consoleTetris.canvas.grid(i, j));
            }
            //System.out.println("");
        }
        //System.out.println("---------------------");
    }
}
