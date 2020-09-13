package TheMovieDB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
        Group lr_root = new Group();
        Scene lr_scene = new Scene(lr_root,400,300);

        ir_primaryStage.setTitle("Main Menu");
        ir_primaryStage.setScene(lr_scene);
        ir_primaryStage.show();



        TmdbMovies movies = new TmdbApi(readFromConfig("[API Key]")).getMovies();
        MovieDb movie = movies.getMovie(5353,"en");
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


