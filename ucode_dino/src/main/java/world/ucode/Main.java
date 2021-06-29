package world.ucode;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


public class Main extends Application {
    public static Pane root1;
    public static Pane gameRoot = new Pane();
    public static ImageView iv1;
    public static AnimationTimer timer;
    public static Scene scene;
    public static Image end_game;
    public static Image replay;
    public static ImageView iv;
    public static Button btn;
    public static boolean check;
    @Override
    public void start(Stage primaryStage) throws Exception {
        root1 = new Pane();
        Image image = new Image(new FileInputStream("src/main/resources/1.gif"));
        Image image_icon = new Image(new FileInputStream("src/main/resources/dino_icon.png"));
        end_game = new Image(new FileInputStream("src/main/resources/gameover_text.png"));
        replay = new Image(new FileInputStream("src/main/resources/replay_button.png"));
        iv1 = new ImageView();
        root1.getChildren().add(iv1);
        iv1.setImage(image);
        scene = new Scene(createContent());
        root1.setStyle("-fx-background-color:#ffffff;");
        //scene.setOnMouseClicked(event->dino.jump());
        primaryStage.setTitle("T-REX");
        Menu menu = new Menu(root1);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image_icon);
        primaryStage.show();
        ImageView iv12 = new ImageView(replay);
        btn =  new Button("", iv12);
        iv = new ImageView(end_game);
        iv.setTranslateY(175);
        iv.setTranslateX(300);
        btn.setTranslateY(275);
        btn.setTranslateX(370);
        btn.setVisible(false);
        iv.setVisible(false);
        root1.getChildren().add(iv);
        btn.setOnMouseClicked(event -> restartGame());
        root1.getChildren().add(btn);
        timer =new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
    }

    public Parent createContent() throws FileNotFoundException {
        root1.setPrefSize(800,532);
        root1.getChildren().addAll(gameRoot);
        return root1;
    }
    public void update() {
        if (Cactus.check(Dino.dino) == true) {
            Ground.start_ground.stop();
            Dino.timeline_run.stop();
            Dino.dino_iv.setImage(Dino.dino_bg_eyes);
            timer.stop();
            btn.setVisible(true);
            iv.setVisible(true);
        }
        if (Cactus.check(Dino.dino) == false && Menu.start_game == true) {
            Cactus.start_clouds.play();
            Ground.start_ground.play();
            Dino.timeline_run.play();
            btn.setVisible(false);
            iv.setVisible(false);
            System.out.println("1234567_ha_ha");
            Menu.score += 1;
            Menu.label_score.setText("Score : " + Menu.score);
        }
    }

    public void restartGame() {
        Menu.label_score.setText(" ");
        btn.setVisible(false);
        iv.setVisible(false);
        Dino.dino.setVisible(false);
        Ground.ground1.setVisible(false);
        Ground.ground2.setVisible(false);
        for (StackPane c : Cactus.cactus_arr) {
            c.setVisible(false);
        }
        for (StackPane cl : Cactus.clouds_arr) {
            cl.setVisible(false);
        }
        Menu menu = new Menu(root1);
        iv1.setVisible(true);
    }


    public static void main (String[]args){
        launch(args);
    }
}

