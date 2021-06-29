package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Cactus extends Pane {
    public int random;
    public Image image1;
    public Image image2;
    public Image image3;
    public Image image4;
    public Image image5;
    public Image image_cloud;
    public ImageView image_cactus;
    public int index = 0;
    public static ArrayList<StackPane> clouds_arr = new ArrayList<>();
    public static ArrayList<StackPane> cactus_arr = new ArrayList<>();
    public static AnimationTimer timer_cactus;
    public static AnimationTimer timer_cloud;
    public static Timeline start_cactus;
    public static Timeline start_clouds;

    public Cactus() throws FileNotFoundException {
        image1 = new Image(new FileInputStream("src/main/resources/cactus1.png"));
        image2 = new Image(new FileInputStream("src/main/resources/cactus2.png"));
        image3 = new Image(new FileInputStream("src/main/resources/cactus3.png"));
        image4 = new Image(new FileInputStream("src/main/resources/cactus4.png"));
        image5 = new Image(new FileInputStream("src/main/resources/cactus5.png"));
        image_cloud = new Image(new FileInputStream("src/main/resources/cloud2.png"));
        start_cactus = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            run_cactus();
        }));
        start_clouds = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            run_clouds();
        }));
    }

    private void run_cactus() {
        index++;
        //Timeline run = new Timeline(new KeyFrame(Duration.seconds(3), actionEvent -> {
        random = 1 + (int) (Math.random() * 5);
        System.out.println(random);
        StackPane cactus = new StackPane();
        image_cactus = new ImageView();
        cactus.getChildren().addAll(new Rectangle(20, 20, Color.WHITE), image_cactus);
        switch (random) {
            case 1:
                image_cactus.setImage(image1);
                cactus.setTranslateY(444);
                break;
            case 2:
                image_cactus.setImage(image2);
                cactus.setTranslateY(460);
                break;
            case 3:
                image_cactus.setImage(image3);
                cactus.setTranslateY(459);
                break;
            case 4:
                image_cactus.setImage(image4);
                cactus.setTranslateY(444);
                break;
            case 5:
                image_cactus.setImage(image5);
                cactus.setTranslateY(460);
                break;
        }
        cactus.setTranslateX(770);
        cactus_arr.add(cactus);
        getChildren().addAll(cactus);
        for (StackPane c : cactus_arr)
            moveX_cactus(c);
        timer_cactus.start();
    }

    private void run_clouds() {
        StackPane clouds = new StackPane();
        ImageView cloud_iv = new ImageView(image_cloud);
        clouds.getChildren().addAll(new Rectangle(10, 30, Color.WHITE), cloud_iv);
        random = 1 + (int) (Math.random() * 3);
        switch (random) {
            case 1:
                clouds.setTranslateY(30);
                break;
            case 2:
                clouds.setTranslateY(60);
                break;
            case 3:
                clouds.setTranslateY(90);
                break;
        }
        clouds.setTranslateX(800);
        clouds_arr.add(clouds);
        getChildren().addAll(clouds);
        for (StackPane cl : clouds_arr)
            moveX_clouds(cl);
        timer_cloud.start();
    }

    public void moveX_cactus(StackPane cactus) {
        timer_cactus = new AnimationTimer() {
            @Override
            public void handle(long l) {
                int speed = 5;

                if (check(Dino.dino)) {
                    timer_cactus.stop();
                }
                else
                    if (Menu.score > 5000) {
                        cactus.setTranslateX(cactus.getTranslateX() - 100);
                    }
                    else
                        cactus.setTranslateX(cactus.getTranslateX() - (5 + Menu.score/100));
            }
        };
    }

    public void moveX_clouds(StackPane cloud) {
        timer_cloud = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (check(Dino.dino)) {
                    timer_cloud.stop();
                }
                else
                    cloud.setTranslateX(cloud.getTranslateX() - 5);
            }
        };
    }

    public static boolean check(StackPane dino) {
        for (StackPane c : cactus_arr) {
            if (c.getBoundsInParent().intersects(dino.getBoundsInParent())) {
                c.setTranslateX(c.getTranslateX());
                Dino.timeline_run.stop();
                return true;
            }
        }
        return false;
    }
}
