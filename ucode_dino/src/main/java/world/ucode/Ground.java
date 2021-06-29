package world.ucode;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Ground extends Pane {
    public Image image_ground;
    public static StackPane ground1;
    public static StackPane ground2;
    public static Timeline start_ground;
    public Ground() throws FileNotFoundException {
//        ground = new StackPane();
        image_ground = new Image( new FileInputStream("src/main/resources/Ground.png"));
//        ground_iv = new ImageView(image_ground);
//        ground.getChildren().addAll(new Rectangle(800,12, Color.WHITE), ground_iv);
//        ground.setTranslateX(0);
//        ground.setTranslateY(470);
//        ground.setVisible(false);
//        moveX_ground(ground);
//        getChildren().addAll(ground);
        start_ground = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            ground1 = new StackPane();
            ground2 = new StackPane();
            ImageView ground_iv1 = new ImageView(image_ground);
            ImageView ground_iv2 = new ImageView(image_ground);
            ground1.getChildren().addAll(new Rectangle(800,12, Color.WHITE), ground_iv1);
            ground2.getChildren().addAll(new Rectangle(800,12, Color.WHITE), ground_iv2);
            ground1.setTranslateX(0);
            ground1.setTranslateY(470);
            //moveX_ground(ground1);
            ground2.setTranslateX(0);
            ground2.setTranslateY(470);
            //moveX_ground(ground2);
            //timer.start();
            start_ground = timelineGround(ground1, ground2);
            getChildren().addAll(ground1, ground2);
        }));
    }

    private Timeline timelineGround(StackPane ground1, StackPane ground2) {
        Duration startDuration = Duration.ZERO;
        Duration endDuration = Duration.seconds(3);

        KeyFrame startKeyFrame = new KeyFrame(startDuration,
                new KeyValue(ground1.translateXProperty(), 0));
        KeyFrame endKeyFrame = new KeyFrame(endDuration,
                new KeyValue(ground1.translateXProperty(), -1 * 800));

        KeyFrame startKeyFrame_2 = new KeyFrame(startDuration,
                new KeyValue(ground2.translateXProperty(), 800));
        KeyFrame endKeyFrame_2 = new KeyFrame(endDuration,
                new KeyValue(ground2.translateXProperty(), 0));
        Timeline timeline_ground = new Timeline(startKeyFrame, endKeyFrame, startKeyFrame_2,endKeyFrame_2);
        timeline_ground.setCycleCount(Timeline.INDEFINITE);
        return timeline_ground;
    }
}
