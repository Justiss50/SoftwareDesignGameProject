/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris.in.java;

//import java.awt.MultipleGradientPaint.CycleMethod;
import console.tetris.ConsoleCanvas;
import console.tetris.ConsoleTetris;
import static console.tetris.ConsoleTetris.currentTetrominoe;
import console.tetris.Movements;
//import java.awt.event.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
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
public class CanvasManager extends Application implements EventHandler<KeyEvent> {

    static GraphicsContext GraphicsContext;
    public int[][][] Matrix = new int[8][14][2];
    public int[][] Walls = new int[3][2];
    ConsoleTetris ConsoleTetris = new ConsoleTetris();
    Canvas canvas = new Canvas();
    Text text = new Text();

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
        ConsoleTetris.Start(this);
        GameLoop();
    }

    @Override
    public void handle(KeyEvent e) {
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
        /*try {
            ConsoleTetris.Update(this);
        } catch (IOException ex) {
            Logger.getLogger(CanvasManager.class.getName()).log(Level.SEVERE, null, ex);
        }*/
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
        CanvasManager canvas = new CanvasManager();
        Color[] GradientPallete = new Color[]{Color.BLACK, Color.RED};
        Stop[] stops = new Stop[]{new Stop(0, GradientPallete[0]), new Stop(1, GradientPallete[1])};
        double angle = Math.toRadians(45);
        LinearGradient color = new LinearGradient(0, 1, Math.sin(angle), 0, true, CycleMethod.REFLECT, stops);
        GraphicsContext.setFill(color);
        int length = 36;
        int arcLength = 20;
        canvas.ClearScreen();
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
            System.out.println("");
        }
    }

    public void GameLoop() throws InterruptedException, IOException {
        if (!ConsoleTetris.exit) {
            TimeUnit.SECONDS.sleep(1);
            ConsoleTetris.Update(this);
            GameLoop();
        }
    }
}
