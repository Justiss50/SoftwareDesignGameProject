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

    static GraphicsContext GraphicsContext;
    public int[][][] Matrix = new int[8][14][2];
    public int[][] Walls = new int[3][2];
    ConsoleTetris ConsoleTetris = new ConsoleTetris();
    public Canvas canvas = new Canvas();
    Text text = new Text();

    public final Runnable UpdateGUIThread;
    public final Runnable RunGameThread;

    public CanvasManager() {
        CanvasManager CanvasManager = this;

        UpdateGUIThread = new Runnable() {
            public void run() {
                while (!ConsoleTetris.exit) {
                    try {
                        Update();
                        Thread.sleep(250);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(CanvasManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("THREAD2 UPDATE");
                }
            }
        };

        RunGameThread = new Runnable() {
            public void run() {
                ConsoleTetris.Start();
                while (!ConsoleTetris.exit) {
                    try {
                        ConsoleTetris.Update();
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
        GraphicsContext = canvas.getGraphicsContext2D();
        //Scene scene = new Scene(root, 400, 600);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        SetUpGrid();
        ClearScreen();

        new Thread(this.UpdateGUIThread).start();
        new Thread(this.RunGameThread).start();
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
            Movements.Rotate(currentTetrominoe, ConsoleTetris.canvas);
        } else if (option.equals(KeyCode.A)) {
            Movements.MoveLeft(ConsoleTetris.canvas);
        } else if (option.equals(KeyCode.S)) {
            Movements.Drop(ConsoleTetris.canvas);
        } else if (option.equals(KeyCode.D)) {
            Movements.MoveRight(ConsoleTetris.canvas);
        } else if (option.equals(KeyCode.ESCAPE)) {
            ConsoleTetris.exit = true;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void SetUpGrid() {
        int offset = 4;
        int length = 36;
        int xPos = offset / 2 + length;
        int yPos = 0;
        for (int i = 0; i < 8; i++) {
            xPos += offset;
            for (int j = 0; j < 14; j++) {
                yPos += offset;
                Matrix[i][j][0] = yPos;
                yPos += length;
                Matrix[i][j][1] = xPos;
            }
            xPos += length;
            yPos = 0;
        }

    }

    public void SetUpWalls() {
        Color[] GradientPallete = new Color[]{Color.PURPLE, Color.BLUE};
        Stop[] stops = new Stop[]{new Stop(0, GradientPallete[0]), new Stop(1, GradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient sideWallsColor = new LinearGradient(0, 1, 1, 0, true, CycleMethod.REFLECT, stops);
        Color bottomColor = GradientPallete[0];
        int xLength = 400;
        int yLength = 600;
        int thickness = 36;
        GraphicsContext.setFill(sideWallsColor);
        GraphicsContext.fillRoundRect(0, 0, thickness, yLength, 0, 0);
        GraphicsContext.fillRoundRect(xLength - thickness, 0, thickness, yLength, 0, 0);
        GraphicsContext.setFill(bottomColor);
        GraphicsContext.fillRoundRect(0, yLength - thickness, xLength, thickness, 0, 0);

    }

    public void TestGrid() {

        Color[] GradientPallete = new Color[]{Color.BLACK, Color.RED};
        Stop[] stops = new Stop[]{new Stop(0, GradientPallete[0]), new Stop(1, GradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient color = new LinearGradient(0, 1, Math.sin(angle), 0, true, CycleMethod.REFLECT, stops);
        GraphicsContext.setFill(color);
        int length = 36;
        int arcLength = 20;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 14; j++) {
                GraphicsContext.fillRoundRect(Matrix[i][j][1], Matrix[i][j][0], length, length, arcLength, arcLength);
            }
        }
    }

    public void ClearScreen() {
        Color[] GradientPallete = new Color[]{Color.BLACK, Color.web("#363d3c")};
        Stop[] stops = new Stop[]{new Stop(0, GradientPallete[0]), new Stop(1, GradientPallete[1])};
        LinearGradient color = new LinearGradient(0, 1, 1, 0, true, CycleMethod.REFLECT, stops);
        GraphicsContext.setFill(color);
        GraphicsContext.fillRoundRect(0, 0, 400, 600, 0, 0);
        SetUpWalls();
    }

    public void Update() {
        int YOffset = 5;
        CanvasManager canvas = this;// new CanvasManager();
        canvas.ClearScreen();
        Color[] GradientPallete = new Color[]{Color.BLACK, Color.RED};
        Stop[] stops = new Stop[]{new Stop(0, GradientPallete[0]), new Stop(1, GradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient color = new LinearGradient(0, 1, Math.sin(angle), 0, true, CycleMethod.REFLECT, stops);
        GraphicsContext.setFill(color);

        int length = 36;
        int arcLength = 20;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 14; j++) {
                if (ConsoleTetris.canvas.grid[j + YOffset][i] != 0) {
                    GraphicsContext.fillRoundRect(Matrix[i][j][1], Matrix[i][j][0], length, length, arcLength, arcLength);
                }
            }
        }
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(ConsoleTetris.canvas.grid[i][j]);
            }
            //System.out.println("");
        }
        System.out.println("---------------------");
    }
}
