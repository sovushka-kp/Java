package world.ucode;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.awt.*;
import java.io.FileNotFoundException;

public class Menu {
    public static int score;
    public static Label label_score;
    public static boolean start_game = false;
    public Button btn;
    public Label lbl;
    public Text text;
    public Image image;
    public ImageView iv;

    public Menu(Pane root1) {
        Menu.MenuItem newGame = new Menu.MenuItem("NEW GAME");
        Menu.MenuItem options = new Menu.MenuItem("RULES");
        Menu.MenuItem exitGame = new Menu.MenuItem("EXIT");
        Menu.SubMenu mainMenu = new Menu.SubMenu(
                newGame, options, exitGame
        );
        Menu.MenuBox menuBox = new Menu.MenuBox(mainMenu);
        exitGame.setOnMouseClicked(event -> System.exit(0));
        newGame.setOnMouseClicked(event -> {
            try {
                startGame(menuBox);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        options.setOnMouseClicked(event -> {
            try {
                rules(menuBox);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        root1.getChildren().addAll(menuBox);
    }

    private void rules(MenuBox MenuBox) throws FileNotFoundException {
        //image = new Image(new FileInputStream("C:\\Users\\admin\\IdeaProjects\\screentest1\\src\\sample\\Beb80JH.gif"));
        btn = new Button("MENU");
        btn.setTranslateY(300);
        btn.setTranslateX(20);
        Main.iv1.setVisible(false);
        MenuBox.setVisible(false);
        lbl = new Label("\n Rules \n ");
        lbl.setTranslateX(345);
        lbl.setTranslateY(15);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        text = new Text("Привіт, любий друже! Рада бачити тебе у грі:)" +
                "\n Почнемо з того, що гра починається тоді, коли ти натискаєш кнопку \"NEW GAME\"" +
                "\n По - друге, чим більше пригаєш - тим більше перешкод." +
                "\n По - третє, чим вище рахунок - ти швидше рухається світ." +
                "\n Ну, і керуєш своїм Діно ти через клавішу пробілу." +
                "\n Бажаю удачі та приємної гри!:)");
        text.setTranslateX(100);
        text.setTranslateY(100);
        text.setFont(Font.font("Arial", 17));
        btn.setOnMouseClicked(event -> open(MenuBox));
        Main.root1.getChildren().add(btn);
        Main.root1.getChildren().add(lbl);
        Main.root1.getChildren().add(text);
    }

    public void open(MenuBox menuBox) {
        menuBox.setVisible(true);
        btn.setVisible(false);
        text.setVisible(false);
        lbl.setVisible(false);
        Main.iv1.setVisible(true);
    }

    public void startGame(MenuBox MenuBox) throws FileNotFoundException {
        start_game = true;
        score = 0;
        label_score = CreateScore();
        label_score.setVisible(true);
        MenuBox.setVisible(false);
        Dino dino = new Dino(Main.scene);
        Cactus cactus1 = new Cactus();
        Cactus.cactus_arr.clear();
        Cactus.clouds_arr.clear();
        Ground ground = new Ground();
        Main.btn.setVisible(false);
        Main.iv.setVisible(false);
        Main.gameRoot.getChildren().addAll(ground);
        Main.gameRoot.getChildren().addAll(cactus1);
        Main.gameRoot.getChildren().addAll(dino);
        Main.root1.getChildren().add(label_score);
        Main.iv1.setVisible(false);
        Main.timer.start();
    }


    private Label CreateScore () {
        Label lbl = new Label("Score:" + score);
        lbl.setTranslateY(10);
        lbl.setTranslateX(50);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setTextFill(Color.BLACK);
        return lbl;
    }

    private static class MenuItem extends StackPane {
        public MenuItem(String name) {
            Rectangle bg = new Rectangle(200, 20, Color.WHITE);
            bg.setOpacity(0.5);

            Text text = new Text(name);
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            FillTransition st = new FillTransition(Duration.seconds(0.5), bg);
            setOnMouseEntered(event -> {
                st.setFromValue(Color.DARKBLUE);
                st.setToValue(Color.DARKGOLDENROD);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            });
            setOnMouseExited(event -> {
                st.stop();
                bg.setFill(Color.WHITE);
            });
        }
    }

    private static class MenuBox extends Pane {
        static Menu.SubMenu subMenu;

        public MenuBox(Menu.SubMenu subMenu) {
            Menu.MenuBox.subMenu = subMenu;
            setVisible(true);
            Rectangle bg = new Rectangle(900, 600, Color.LIGHTBLUE);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, subMenu);
        }

        public void setSubMenu(Menu.SubMenu subMenu) {
            getChildren().remove(Menu.MenuBox.subMenu);
            Menu.MenuBox.subMenu = subMenu;
            getChildren().add(Menu.MenuBox.subMenu);
        }
        public void setVisibleMenu(Menu.SubMenu subMenu) {
            subMenu.setVisible(false);
        }
    }

    private static class SubMenu extends VBox {
        public SubMenu(Menu.MenuItem... items) {
            setSpacing(15);
            setTranslateY(100);
            setTranslateX(50);
            for (Menu.MenuItem item : items) {
                getChildren().addAll(item);
            }
        }
    }
}
