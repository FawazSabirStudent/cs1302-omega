package cs1302.game;

import java.util.Random;
import java.util.logging.Level;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ArrayList;

/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private int time = 0;
    private int score = 0;
    private Text scoreT = new Text(Integer.toString(score));
    private int size = 25;
    private static int foodX = 0;
    private static int foodY = 0;
    private static boolean eaten = false;
    private ArrayList<Box> snake = new ArrayList<>();
    private Dir face = Dir.RIGHT;
    private static Random rnd = new Random();
    boolean gameOver = false;


    public enum Dir{
        LEFT, RIGHT, UP, DOWN;
    }

    public static void spawnFood() {
        if (eaten) {
            foodX = rnd.nextInt(10);
            foodY = rnd.nextInt(10);
            eaten = false;
        }
    }

    public class Box{
        int x;
        int y;

        public Box(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Construct a {@code DemoGame} object.
     * @param width scene width
     * @param height scene height
     */
    public DemoGame(int width, int height) {
        super(width, height, 60);            // call parent constructor
        setLogLevel(Level.INFO);             // enable logging
    } // DemoGame

    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component
        getChildren().addAll(scoreT);         // add to main container
    } // init

    /** {@inheritDoc} */
    @Override
    protected void update() {
        time++;
        if (time == 25) {
            time = 0;
        }

    } // update

} // DemoGame
