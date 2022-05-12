package cs1302.omega;

import cs1302.game.DemoGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class OmegaApp extends Application {

    // grid square size
    private final int gridSize = 25;

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {}

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // menu
        HBox menu = new HBox();

        // buttons
        Button rst  = new Button ("Restart");
        Button exit  = new Button ("Exit");

        // some labels to display information
        Label title = new Label("Snake!");
        Label instructions
            = new Label("Arrow Keys: Move");

        // line that does show up
        Separator line = new Separator();
        line.setOrientation(Orientation.HORIZONTAL);

        menu.getChildren().addAll(title, rst, exit);
        menu.setSpacing(10.0);

        // demo game provided with the starter code
        DemoGame game = new DemoGame(gridSize * 10, gridSize * 10);





        // setup scene
        VBox root = new VBox(menu, instructions, line, game);

        root.setSpacing(15.0);
        Scene scene = new Scene(root);

        // setup stage
        stage.setTitle("OmegaApp!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();


        // button handlers
        EventHandler<ActionEvent> rstHandler = event -> {
            start(stage);
        };
        EventHandler<ActionEvent> exitHandler = event -> System.exit(0);

        rst.setOnAction(rstHandler);
        exit.setOnAction(exitHandler);

        // play the game
        game.play();

    } // start

} // OmegaApp
