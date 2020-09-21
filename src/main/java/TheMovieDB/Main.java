package TheMovieDB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    private static File mr_configFile;

    public static void main(String[] args) {
        BasicConfigurator.configure();
        mr_configFile = new File ("config.txt");
        launch(args);
    }

    @Override
    public void start(Stage ir_primaryStage) throws Exception {

        //Background Image
        Image lr_bImage = new Image("file:BackgroundMain.jpg",400,300,false,true);
        BackgroundImage lr_backgroundImage = new BackgroundImage(lr_bImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        Background lr_backgroundMain = new Background(lr_backgroundImage);

        //Layout and Scene
        VBox lr_vBox = new VBox();
        lr_vBox.setPadding(new Insets(10,10,10,25));
        lr_vBox.setBackground(lr_backgroundMain);
        BorderPane lr_borderPaneTitle = new BorderPane();
        BorderPane lr_borderPaneButton = new BorderPane();
        lr_borderPaneButton.setPadding(new Insets(20,10,0,0));
        Scene lr_scene = new Scene(lr_vBox,300,200);

        //Labels for showing path
        Label lr_labelTitle = new Label ("Settings");
        lr_labelTitle.setPadding(new Insets(3,10,30,0));
        Label lr_labelCurrent = new Label ("Current path:");
        Label lr_labelPath = new Label (readFromConfig("[Path]"));
        //TODO Path to long

        //Buttons
        Button lr_buttonPath = new Button("Choose another Path");
        lr_buttonPath.setMaxSize(110,10);
        lr_buttonPath.setStyle("-fx-font-size:10");
        Button lr_buttonScan = new Button ("Scan");
        lr_buttonScan.setMaxSize(60,10);
        lr_buttonScan.setStyle("-fx-font-size:10");

        //Buttons Listener
        lr_buttonPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    saveToConfig("[Path]",chooseFolder());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        //Effects for label
        DropShadow lr_dropShadow = new DropShadow();
        lr_dropShadow.setOffsetY(3.0f);
        lr_dropShadow.setColor(Color.color(0.4f, 0.4f, 0.4f));

        //Labels configuration
        lr_labelTitle.setEffect(lr_dropShadow);
        lr_labelTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        lr_labelCurrent.setBackground(new Background(new BackgroundFill(Color.GOLD,null,null)));

        //Add components
        lr_borderPaneTitle.setCenter(lr_labelTitle);
        lr_vBox.getChildren().addAll(lr_borderPaneTitle,lr_labelCurrent,lr_labelPath,lr_borderPaneButton);
        lr_borderPaneButton.setLeft(lr_buttonPath);
        lr_borderPaneButton.setCenter(lr_buttonScan);

        //Button Row Configuration
        BorderPane.setAlignment(lr_buttonScan,Pos.CENTER_LEFT);
        Insets lr_insetButton = new Insets(20);
        BorderPane.setMargin(lr_buttonPath,lr_insetButton);


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
        lr_scanner.close();
        return lv_result[1];

    }

    public void saveToConfig (String iv_keyWord,String iv_parameter) throws FileNotFoundException {
        //Read content of config.txt
        List<String> la_configContent = new ArrayList<String>();
        Scanner lr_scanner = new Scanner(mr_configFile);
        while(lr_scanner.hasNextLine()) {
            String lv_singleLine = lr_scanner.nextLine();
            la_configContent.add(lv_singleLine);
        }
        //Clear config.txt
        try {
            BufferedWriter lr_writer = new BufferedWriter(new FileWriter(mr_configFile));
            lr_writer.write("");

            //Replace content and fill file
            String[] la_contentSplit = {""};
            for (String lv_line : la_configContent) {
                la_contentSplit = lv_line.split("=");
                if (la_contentSplit[0].equals(iv_keyWord)) {
                    lr_writer.append(la_contentSplit[0])
                             .append("=")
                             .append(iv_parameter)
                             .append("\n");
                } else {
                    lr_writer.append(lv_line)
                            .append("\n");
                }
            }
            lr_writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String chooseFolder () {
        Stage lr_temporaryStage = new Stage();
        DirectoryChooser lr_directoryChooser = new DirectoryChooser();
        File lr_choosenDirectory = lr_directoryChooser.showDialog(lr_temporaryStage);
        return lr_choosenDirectory.getAbsolutePath();
    }




    }


