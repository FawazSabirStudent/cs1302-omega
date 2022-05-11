package cs1302.game;

import java.util.Random;
import java.util.logging.Level;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;


/**
 * An example of a simple game in JavaFX. The play can move the rectangle left/right
 * with the arrow keys or teleport the rectangle by clicking it!
 */
public class DemoGame extends Game {

    private int time = 0;
    private int score = 0;
    private Text scoreT = new Text("Score: " + Integer.toString(score));
    private int size = 25;
    private static int foodX = 0;
    private static int foodY = 0;
    private static boolean eaten = false;
    private ArrayList<Box> snake = new ArrayList<>();
    private Dir face = Dir.RIGHT;
    private static Random rnd = new Random();
    boolean gameOver = false;


    /**
     * Determines the direction that the snake is going in.
     */
    public enum Dir {
        LEFT, RIGHT, UP, DOWN;
    }


    /**
     * spawns a new food when the old one gets eaten.
     */
    public static void spawnFood() {
        if (eaten) {
            foodX = rnd.nextInt(10);
            foodY = rnd.nextInt(10);
            eaten = false;
        }
    }

    /**
     * when snake eats food, this method extends it and calls spawn food.
     */
    public void eatFood() {
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Box(-1, -1));
            spawnFood();
        }
    }


    /**
     * Checks to see if the snake has hit itself, which initiates a loss.
     */
    public void selfHit() {
        for (int i = 0; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }
    }

    /**
     * Updates the score.
     */
    public void updateScore() {
        scoreT.setText("Score: " + Integer.toString(snake.size() - 3));
    }


    /**
     * This class makes up the different segments of the snake.
     */
    public class Box {
        int x;
        int y;


        /**
         * Constructs a {@code Box} object.
         * @param x the x coordinate of the segment
         * @param y the y coordinate of the segment
         */
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
        snake.add(new Box(5, 5));
        snake.add(new Box(5, 5));
        snake.add(new Box(5, 5));

    } // init


    /**
     * Checks to see if the game should be ended.
     * The game is ended when the player hits themself or hits a wall.
     * @return whether the game is over or not.
     */
    protected boolean checkGameOver() {
        if (snake.get(0).y < 0 || snake.get(0).y > 10 || snake.get(0).x < 0 ||
            snake.get(0).x > 10) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Resets the game.
     */
    public void reset() {

    }

    /** {@inheritDoc} */
    @Override
    protected void update() {
        if (checkGameOver()) {
            time++;
            eatFood();
            selfHit();
            updateScore();
        } else {
            time = 0;
            scoreT.setText("Game Over! Your score was " +
                           (Integer.toString(snake.size() - 3) + "!"));
        }
        if (time == 25) {
            time = 0;
            spawnFood();
            isKeyPressed(KeyCode.LEFT, () -> face = Dir.LEFT);
            isKeyPressed(KeyCode.RIGHT, () -> face = Dir.RIGHT);
            isKeyPressed(KeyCode.UP, () -> face = Dir.UP);
            isKeyPressed(KeyCode.DOWN, () -> face = Dir.DOWN);
        }


    } // update

} // DemoGame
