package cs1302.game;

import java.util.Random;
import java.util.logging.Level;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;
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
    private int foodX = 5 * size;
    private int foodY = 3 * size;
    private static boolean eaten = false;
    private static boolean pause = true;
    private ArrayList<Rectangle> snake = new ArrayList<>();
    private Dir face = Dir.RIGHT;
    private static Random rnd = new Random();
    boolean gameOver = false;
    Rectangle food = new Rectangle(5 * size, 3 * size, size, size);
    Separator line = new Separator();

    /**
     * Determines the direction that the snake is going in.
     */
    public enum Dir {
        LEFT, RIGHT, UP, DOWN;
    }


    /**
     * spawns a new food when the old one gets eaten.
     */
    public void spawnFood() {
        if (eaten) {
            foodX = size * rnd.nextInt(10);
            foodY = size * rnd.nextInt(10);
            for (int i = 0; i < snake.size(); i++) {
                if (foodX == snake.get(i).getX() && foodY == snake.get(i).getY()) {
                    spawnFood();
                }
            }
            eaten = false;
            food.setX(foodX);
            food.setY(foodY);
        }
    }

    /**
     * when snake eats food, this method extends it and calls spawn food.
     */
    public void eatFood() {
        if (foodX == snake.get(0).getX() && foodY == snake.get(0).getY()) {
            addSeg((int) snake.get(snake.size() - 1).getX(),
                   (int) snake.get(snake.size() - 1).getY());
            eaten = true;
            spawnFood();
        }
    }


    /**
     * Checks to see if the snake has hit itself, which initiates a loss.
     * @return if the player has hit themself or not.
     */
    public boolean selfHit() {
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() &&
                snake.get(0).getY() == snake.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the score.
     */
    public void updateScore() {
        scoreT.setText("Score: " + Integer.toString(snake.size() - 3));
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


    /**
     * adds a segment to the snake.
     * @param x the x coordinate of the segment
     * @param y the y coordinate of the segment
     */
    protected void addSeg(int x, int y) {
        this.snake.add(new Rectangle(x * size, y * size, size, size));
        this.getChildren().add(this.snake.get(snake.size() - 1));
    }


    /** {@inheritDoc} */
    @Override
    protected void init() {
        // setup subgraph for this component

        addSeg(5, 5);
        addSeg(4, 5);
        addSeg(3, 5);

        line.setOrientation(Orientation.HORIZONTAL);

        getChildren().addAll(line, scoreT, food);         // add to main container

        pause();

    } // init



    /**
     * Checks to see if the game should be ended.
     * The game is ended when the player hits themself or hits a wall.
     * @return whether the game is over or not.
     */
    protected boolean checkGameOver() {
        if (snake.get(0).getY() < 0 || snake.get(0).getY() > 9 * size || snake.get(0).getX() < 0 ||
            snake.get(0).getX() > 9 * size || selfHit()) {
            gameOver = true;
            return false;
        } else {
            gameOver = false;
            return true;

        }
    }

    /**
     * When the game ends.
     */
    public void gameOver() {
        scoreT.setText("Game Over! Your score was " +
                       (Integer.toString(snake.size() - 3) + "!"));
        getChildren().clear();
        getChildren().add(scoreT);

        pause();
    }

    /** {@inheritDoc} */
    @Override
    protected void update() {
        if (checkGameOver()) {
            time++;
            eatFood();
            updateScore();
            isKeyPressed(KeyCode.LEFT, () -> face = Dir.LEFT);
            isKeyPressed(KeyCode.RIGHT, () -> face = Dir.RIGHT);
            isKeyPressed(KeyCode.UP, () -> face = Dir.UP);
            isKeyPressed(KeyCode.DOWN, () -> face = Dir.DOWN);

        } else {
            gameOver();
        }
        if (time == 10 && gameOver == false) {
            time = 0;

            switch (face) {
            case UP:
                snake.get(snake.size() - 1).setY(snake.get(0).getY() - 25);
                snake.get(snake.size() - 1).setX(snake.get(0).getX());
                snake.add(0, snake.get(snake.size() - 1));
                snake.remove(snake.size() - 1);
                break;
            case DOWN:
                snake.get(snake.size() - 1).setY(snake.get(0).getY() + 25);
                snake.get(snake.size() - 1).setX(snake.get(0).getX());
                snake.add(0, snake.get(snake.size() - 1));
                snake.remove(snake.size() - 1);
                break;
            case LEFT:
                snake.get(snake.size() - 1).setX(snake.get(0).getX() - 25);
                snake.get(snake.size() - 1).setY(snake.get(0).getY());
                snake.add(0, snake.get(snake.size() - 1));
                snake.remove(snake.size() - 1);
                break;
            case RIGHT:
                snake.get(snake.size() - 1).setX(snake.get(0).getX() + 25);
                snake.get(snake.size() - 1).setY(snake.get(0).getY());
                snake.add(0, snake.get(snake.size() - 1));
                snake.remove(snake.size() - 1);
                break;

            }

        }


    } // update

} // DemoGame
