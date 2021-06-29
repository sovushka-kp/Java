package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import world.ucode.Menu;

import java.io.FileNotFoundException;
import java.io.FileInputStream;


public class Dino extends Pane {
    public Image dino_stand;
    public Image dino_left;
    public Image dino_right;
    public static Image dino_bg_eyes;
    public static ImageView dino_iv;
    public int gravity;
    public static StackPane dino;
    public static AnimationTimer jumpTimer;
    public  static Timeline timeline_run;
    public static boolean start = false;
    public Dino(Scene scene) throws FileNotFoundException {
        dino = new StackPane();
        dino_stand = new Image(new FileInputStream("src/main/resources/dino_stand.png"));
        dino_left = new Image(new FileInputStream("src/main/resources/dino_left.png"));
        dino_right = new Image(new FileInputStream("src/main/resources/dino_right.png"));
        dino_bg_eyes = new Image(new FileInputStream("src/main/resources/dino_bg_eyes.png"));
        dino_iv = new ImageView();
        dino_iv.setImage(dino_stand);
        dino.getChildren().addAll(new Rectangle(5,5, Color.WHITE), dino_iv);
        dino.setTranslateX(45);
        dino.setTranslateY(450);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE && gravity == 0 && Cactus.check(dino) == false) {
                jump();
                System.out.println("hi");
            }
        });
        timeline_run = run();
        getChildren().addAll(dino);
    }


    public void jump () {
        jumpTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (Menu.start_game == false && Cactus.check(dino) == true) {
                    System.out.println("h2");
                    jumpTimer.stop();
                    gravity = 0;
                }
                if (Menu.start_game == true) {
                    System.out.println("h1");
                    Cactus.start_cactus.play();
                    gravity += 1;
                    timeline_run.pause();
                    dino_iv.setImage(dino_stand);
                    dino.setTranslateY(dino.getTranslateY() - 20 + gravity);
                }
                if (dino.getTranslateY() == 450) {
                    jumpTimer.stop();
                    timeline_run.play();
                    gravity = 0;
                }
            }
        };
        jumpTimer.start();
    }

    private Timeline run() {
        Timeline run = new Timeline(new KeyFrame(Duration.seconds(0.1),
                actionEvent -> dino_iv.setImage(dino_stand)),
                new KeyFrame(Duration.seconds(0.1), actionEvent -> dino_iv.setImage(dino_left)),
                new KeyFrame(Duration.seconds(0.2), actionEvent -> dino_iv.setImage(dino_right)));
        run.setCycleCount(Timeline.INDEFINITE);
        return run;
    }

}
