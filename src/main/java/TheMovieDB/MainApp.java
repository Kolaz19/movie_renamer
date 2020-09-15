package TheMovieDB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainApp extends Application {

    private static File mr_configFile;

    public static void main(String[] args) {
        BasicConfigurator.configure();
        mr_configFile = new File ("config.txt");
        launch(args);
    }

    @Override
    public void start(Stage ir_primaryStage) throws Exception {
        //Layout and Scene
        VBox lr_vBox = new VBox();
        lr_vBox.setPadding(new Insets(10,10,10,25));
        VBox lr_vBoxTitle = new VBox();
        lr_vBoxTitle.setPadding(new Insets(5,10,30,128));

        Scene lr_scene = new Scene(lr_vBox,400,300);

        //Labels for showing path
        Label lr_labelTitle = new Label ("Settings");
        lr_labelTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        DropShadow lr_dropShadow = new DropShadow();
        lr_dropShadow.setOffsetY(3.0f);
        lr_dropShadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
        lr_labelTitle.setEffect(lr_dropShadow);

        Label lr_labelCurrent = new Label ("Current path:");
        lr_labelCurrent.setBackground(new Background(new BackgroundFill(Color.GOLD,null,null)));
        Label lr_labelPath = new Label ("Actual path");
        lr_vBoxTitle.getChildren().add(lr_labelTitle);
        lr_vBox.getChildren().addAll(lr_vBoxTitle,lr_labelCurrent,lr_labelPath);



        TmdbMovies movies = new TmdbApi(readFromConfig("[API Key]")).getMovies();
        MovieDb movie = movies.getMovie(5353,"en");

        //Set up and start
        ir_primaryStage.setTitle("Main Menu");
        ir_primaryStage.setScene(lr_scene);
        ir_primaryStage.setResizable(false);
        ir_primaryStage.show();
    }

    public String readFromConfig (String iv_keyWord) throws FileNotFoundException {
        Scanner lr_scanner = new Scanner(mr_configFile);
        String[] lv_result = {""};
        while(lr_scanner.hasNextLine()) {
            lv_result = lr_scanner.nextLine().split("=");
            if (lv_result[0].equals(iv_keyWord)) {
                break;
            }
        }
        return lv_result[1];
    }

    }


