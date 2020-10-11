package TheMovieDB;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class ScanStage {

    //Root elements
    Stage mr_stageRoot;
    Button mr_buttonRoot;
    Label mr_labelPathRoot;
    //Elements of second window
    Stage mr_stageScan;
    Scene mr_sceneScan;
    BorderPane mr_borderPaneMain, mr_borderPaneRight, mr_borderPaneLeft;
    Label mr_labelFolder, mr_labelFile, mr_labelSpacing, mr_labelMovieTitle, mr_labelSuggestion, mr_labelFolderName, mr_labelContentName, mr_labelMoviePoster, mr_labelArrow;
    VBox mr_vBoxRightMid, mr_vBoxLeftMid;
    HBox mr_hBoxRightBottom, mr_hBoxLeftBottom, mr_hBoxLeftTop, mr_hBoxRightMidSmall;
    Button mr_buttonManSearch, mr_buttonBack, mr_buttonForward, mr_buttonConfirm, mr_buttonSkip;
    Image mr_imageMovie;
    ArrayList<File> ma_directoryList;
    MovieHandler mr_movieHandler;
    int mv_currentFolderCount;


    ScanStage(Stage ir_stageRoot, Button ir_buttonScan, Label ir_labelPath) {
        mr_stageRoot = ir_stageRoot;
        mr_buttonRoot = ir_buttonScan;
        mr_labelPathRoot = ir_labelPath;
        ma_directoryList = new ArrayList<>();

        //Preparation for Stage
        //For MANUAL SEARCH
        mr_hBoxRightBottom = new HBox();
        mr_hBoxRightBottom.setSpacing(10);
        mr_hBoxRightBottom.setPadding(new Insets(5, 10, 55, 20));
        //For FOLDER and SKIP
        mr_hBoxRightMidSmall = new HBox();
        mr_hBoxRightMidSmall.setSpacing(3);
        //For RIGHT LEFT and CONFIRM
        mr_hBoxLeftBottom = new HBox();
        mr_hBoxLeftBottom.setSpacing(10);
        mr_hBoxLeftBottom.setPadding(new Insets(5, 10, 55, 50));
        //For FOLDER and FILE name
        mr_vBoxRightMid = new VBox();
        mr_vBoxRightMid.setPadding(new Insets(64, 10, 0, 10));
        //For MOVIE TITLE and MOVIE IMAGE
        mr_vBoxLeftMid = new VBox();
        mr_vBoxLeftMid.setPadding(new Insets(10, 0, 5, 5));
        mr_vBoxLeftMid.setSpacing(1);
        //For HEADLINE
        mr_hBoxLeftTop = new HBox();
        mr_hBoxLeftTop.setPadding(new Insets(10, 5, 5, 55));

        //LEFT BorderPane
        mr_borderPaneLeft = new BorderPane();
        mr_borderPaneLeft.setBottom(mr_hBoxLeftBottom);
        mr_borderPaneLeft.setCenter(mr_vBoxLeftMid);
        mr_borderPaneLeft.setTop(mr_hBoxLeftTop);
        mr_borderPaneLeft.setPrefWidth(220);
        //RIGHT BorderPane
        mr_borderPaneRight = new BorderPane();
        mr_borderPaneRight.setBottom(mr_hBoxRightBottom);
        mr_borderPaneRight.setCenter(mr_vBoxRightMid);
        mr_borderPaneRight.setPrefWidth(200);
        //MAIN Borderpane
        mr_borderPaneMain = new BorderPane();
        mr_borderPaneMain.setLeft(mr_borderPaneLeft);
        mr_borderPaneMain.setRight(mr_borderPaneRight);
        mr_stageScan = new Stage();
        mr_sceneScan = new Scene(mr_borderPaneMain, 430, 430);

        //Configuration Buttons
        mr_buttonManSearch = new Button("Manual Search");
        mr_buttonBack = new Button("<");
        mr_buttonForward = new Button(">");
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png", 20, 20, false, true);
        mr_buttonConfirm = new Button("", new ImageView(lr_imageCheckmarkIcon));
        mr_buttonSkip = new Button("Skip");
        mr_buttonSkip.setPadding(new Insets(3, 5, 3, 5));

        //Configuration Labels
        mr_labelFolderName = new Label("Space");
        mr_labelFolderName.setAlignment(Pos.TOP_LEFT);
        mr_labelFolderName.setWrapText(true);
        mr_labelFolderName.setMaxWidth(170);
        mr_labelFolderName.setMinHeight(34);
        mr_labelContentName = new Label("Space");
        mr_labelContentName.setAlignment(Pos.TOP_LEFT);
        mr_labelContentName.setWrapText(true);
        mr_labelContentName.setMaxWidth(170);
        mr_labelContentName.setMinHeight(34);
        mr_labelSuggestion = new Label("Suggestion:");
        mr_labelSuggestion.setFont(new Font("Arial", 15));
        mr_labelSuggestion.setStyle("-fx-font-weight: bold;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_labelMovieTitle = new Label("Movie Title");
        mr_labelMovieTitle.setStyle("-fx-font-weight: bold;-fx-background-color: #cd5c5c");
        mr_labelMovieTitle.setFont(new Font("Arial", 15));
        mr_labelMovieTitle.setMaxWidth(200);
        Image lr_imageFolderIcon = new Image("file:FolderIcon.png", 20, 20, false, true);
        mr_labelFolder = new Label("Current Folder ", new ImageView(lr_imageFolderIcon));
        mr_labelFolder.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        Image lr_imageFileIcon = new Image("file:FileIcon.png", 20, 20, false, true);
        mr_labelFile = new Label("File in Folder ", new ImageView(lr_imageFileIcon));
        mr_labelFile.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_imageMovie = new Image("file:te.jpg", 210, 280, false, true);
        mr_labelMoviePoster = new Label("", new ImageView(mr_imageMovie));
        Image lr_imageArrow = new Image("file:BlackArrowDownward.png", 25, 125, false, true);
        mr_labelArrow = new Label("", new ImageView(lr_imageArrow));
        mr_labelArrow.setPadding(new Insets(0, 0, 0, 42));
        mr_labelSpacing = new Label(" \n ");

        //Add elements
        mr_hBoxRightMidSmall.getChildren().addAll(mr_labelFolder, mr_buttonSkip);
        mr_hBoxRightBottom.getChildren().addAll(mr_buttonManSearch);
        mr_vBoxRightMid.getChildren().addAll(mr_hBoxRightMidSmall, mr_labelFolderName, mr_labelSpacing, mr_labelFile, mr_labelContentName, mr_labelArrow);
        mr_hBoxLeftBottom.getChildren().addAll(mr_buttonBack, mr_buttonForward, mr_buttonConfirm);
        mr_vBoxLeftMid.getChildren().addAll(mr_labelMovieTitle, mr_labelMoviePoster);
        mr_hBoxLeftTop.getChildren().addAll(mr_labelSuggestion);

        //Add ActionHandlers
        mr_buttonForward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_movieHandler.nextMovie();
                mr_labelMovieTitle.setText(mr_movieHandler.getMovieName());
                mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
            }
        });

        mr_buttonBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_movieHandler.previousMovie();
                mr_labelMovieTitle.setText(mr_movieHandler.getMovieName());
                mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
            }
        });

        mr_stageScan.setWidth(420);
        mr_stageScan.setHeight(430);
        mr_stageScan.setTitle("Scanner");
        mr_stageScan.setScene(mr_sceneScan);
        mr_stageScan.setResizable(false);
        //TODO closing window should open the Main
    }

    public void setRootHandler() {
        mr_buttonRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    setFolderList();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                mr_stageRoot.hide();
                mr_stageScan.show();
            }
        });
    }

    public void setFolderList() throws FileNotFoundException {
        File lr_directoryPath = new File(mr_labelPathRoot.getText());
        File lr_filesList[] = lr_directoryPath.listFiles();
        ma_directoryList.clear();

        //TODO directory empty or non existent
        for (File lr_file : lr_filesList) {
            if (lr_file.isDirectory()) {
                ma_directoryList.add(lr_file);
            }
        }
        mv_currentFolderCount = -1; //because nextFolder is called immediately, so we need -1 for first call
        nextFolder();

    }

    //does set label for folder and file name and image
    public void nextFolder() throws FileNotFoundException {
        if (mv_currentFolderCount+1 < ma_directoryList.size()) {
            mv_currentFolderCount++;
        } else {
            //TODO send message - Every folder was scanned!
        }
        //Set name of folder and content (label)
        mr_labelFolderName.setText(ma_directoryList.get(mv_currentFolderCount).getName());
        ArrayList<File> la_contentInFolder = new ArrayList<File>(Arrays.asList(ma_directoryList.get(mv_currentFolderCount).listFiles()));
        for (File lr_file : la_contentInFolder) {
            if (lr_file.isFile()) {
                String lv_f = lr_file.getName();
                if ((lv_f.endsWith(".mkv")) || (lv_f.endsWith(".avi")) || (lv_f.endsWith(".mp4")) || (lv_f.endsWith(".flv")) || (lv_f.endsWith(".vob")) || (lv_f.endsWith(".ogv")) || (lv_f.endsWith(".ogg")) || (lv_f.endsWith(".mov")) || (lv_f.endsWith(".wmv")) || (lv_f.endsWith(".m4p")) || (lv_f.endsWith(".m4v")) || (lv_f.endsWith(".mpg")) || (lv_f.endsWith(".mpeg")) || (lv_f.endsWith("."))) {
                    mr_labelContentName.setText(lr_file.getName());
                    break;
                }
            }
        }

        String lv_movieName = ma_directoryList.get(mv_currentFolderCount).getName();
        //Initialize MovieHandler with ALL movies for every new folder
        mr_movieHandler = new MovieHandler(lv_movieName);
        //TODO is MovieList empty? -> Open Window
        mr_labelMovieTitle.setText(mr_movieHandler.getMovieName());
        mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
    }

    public void setDirectoryName () {

    }
}
