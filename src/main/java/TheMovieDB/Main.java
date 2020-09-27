package TheMovieDB;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private static final File gr_configFile = new File ("config.txt");
    Image mr_bImage;
    BackgroundImage mr_backgroundImage;
    Background mr_backgroundMain;
    VBox mr_vBox;
    BorderPane mr_borderPaneTitle, mr_borderPaneButton;
    Scene mr_scene;
    Label mr_labelTitle, mr_labelCurrent, mr_labelPath;
    Button mr_buttonPath, mr_buttonScan;
    DropShadow mr_dropShadow;
    Insets mr_insetButton;
    ScanStage mr_scanStage;


    public static void main(String[] args) {
        BasicConfigurator.configure();
        launch(args);
    }

    @Override
    public void start(Stage ir_primaryStage) throws Exception {

        //Background Image
        mr_bImage = new Image("file:BackgroundMain.jpg",400,300,false,true);
        mr_backgroundImage = new BackgroundImage(mr_bImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        mr_backgroundMain = new Background(mr_backgroundImage);

        //Layout and Scene
        mr_vBox = new VBox();
        mr_vBox.setPadding(new Insets(10,10,10,25));
        mr_vBox.setBackground(mr_backgroundMain);
        mr_borderPaneTitle = new BorderPane();
        mr_borderPaneButton = new BorderPane();
        mr_borderPaneButton.setPadding(new Insets(0,10,0,0));
        mr_scene = new Scene(mr_vBox,300,200);

        //Labels for showing path
        mr_labelTitle = new Label ("Settings");
        mr_labelTitle.setPadding(new Insets(3,10,30,0));
        mr_labelCurrent = new Label ("Current path:");
        mr_labelPath = new Label (readFromConfig("[Path]"));
        mr_labelPath.setMinHeight(60);
        mr_labelPath.setWrapText(true);
        mr_labelPath.setMaxWidth(245);
        mr_labelPath.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        //Buttons
        mr_buttonPath = new Button("Choose another Path");
        mr_buttonPath.setMaxSize(110,10);
        mr_buttonPath.setStyle("-fx-font-size:10");
        mr_buttonScan = new Button ("Scan");
        mr_buttonScan.setMaxSize(60,10);
        mr_buttonScan.setStyle("-fx-font-size:10");

        //Buttons Listener
        mr_buttonPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    saveToConfig("[Path]",chooseFolder());
                    mr_labelPath.setText(readFromConfig("[Path]"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        //Effects for label
        mr_dropShadow = new DropShadow();
        mr_dropShadow.setOffsetY(3.0f);
        mr_dropShadow.setColor(Color.color(0.4f, 0.4f, 0.4f));

        //Labels configuration
        mr_labelTitle.setEffect(mr_dropShadow);
        mr_labelTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        mr_labelCurrent.setBackground(new Background(new BackgroundFill(Color.GOLD,null,null)));

        //Add components
        mr_borderPaneTitle.setCenter(mr_labelTitle);
        mr_vBox.getChildren().addAll(mr_borderPaneTitle, mr_labelCurrent, mr_labelPath, mr_borderPaneButton);
        mr_borderPaneButton.setLeft(mr_buttonPath);
        mr_borderPaneButton.setCenter(mr_buttonScan);

        //Button Row Configuration
        BorderPane.setAlignment(mr_buttonScan,Pos.CENTER_LEFT);
        mr_insetButton = new Insets(20);
        BorderPane.setMargin(mr_buttonPath, mr_insetButton);

        //Set up and start
        mr_scanStage = new ScanStage(ir_primaryStage, mr_buttonScan);
        mr_scanStage.setRootHandler();
        ir_primaryStage.setWidth(300);
        ir_primaryStage.setHeight(245);
        ir_primaryStage.setTitle("Main Menu");
        ir_primaryStage.setScene(mr_scene);
        ir_primaryStage.setResizable(false);
        ir_primaryStage.show();
    }

    public static String readFromConfig(String iv_keyWord) throws FileNotFoundException {
        Scanner lr_scanner = new Scanner(gr_configFile);
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

    public static void saveToConfig (String iv_keyWord,String iv_parameter) throws FileNotFoundException {
        //Read content of config.txt
        List<String> la_configContent = new ArrayList<String>();
        Scanner lr_scanner = new Scanner(gr_configFile);
        while(lr_scanner.hasNextLine()) {
            String lv_singleLine = lr_scanner.nextLine();
            la_configContent.add(lv_singleLine);
        }
        //Clear config.txt
        try {
            BufferedWriter lr_writer = new BufferedWriter(new FileWriter(gr_configFile));
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


