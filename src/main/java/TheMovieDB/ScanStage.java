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

public class ScanStage {

    //Root elements
    Stage mr_stageRoot;
    Button mr_buttonRoot;
    //Elements of second window
    Stage mr_stageScan;
    Scene mr_sceneScan;
    BorderPane mr_borderPaneMain, mr_borderPaneRight, mr_borderPaneLeft;
    Label mr_labelFolder, mr_labelFile, mr_labelSpacing, mr_labelMovieTitle,mr_labelSuggestion, mr_labelFolderName, mr_labelContentName, mr_labelMoviePoster, mr_labelArrow;
    VBox mr_vBoxRightMid, mr_vBoxLeftMid;
    HBox mr_hBoxRightBottom, mr_hBoxLeftBottom, mr_hBoxLeftTop, mr_hBoxRightMidSmall;
    Button mr_buttonManSearch, mr_buttonBack, mr_buttonForward, mr_buttonConfirm, mr_buttonSkip;
    Image mr_imageMovie;


    ScanStage (Stage ir_stageRoot, Button ir_buttonScan) {
        mr_stageRoot = ir_stageRoot;
        mr_buttonRoot = ir_buttonScan;

        //Preparation for Stage
        //For MANUAL SEARCH
        mr_hBoxRightBottom = new HBox();
        mr_hBoxRightBottom.setSpacing(10);
        mr_hBoxRightBottom.setPadding(new Insets(5,10,55,20));
        //For FOLDER and SKIP
        mr_hBoxRightMidSmall = new HBox();
        mr_hBoxRightMidSmall.setSpacing(3);
        //For RIGHT LEFT and CONFIRM
        mr_hBoxLeftBottom = new HBox();
        mr_hBoxLeftBottom.setSpacing(10);
        mr_hBoxLeftBottom.setPadding(new Insets(5,10,55,40));
        //For FOLDER and FILE name
        mr_vBoxRightMid = new VBox();
        mr_vBoxRightMid.setPadding(new Insets(64,10,0,10));
        //For MOVIE TITLE and MOVIE IMAGE
        mr_vBoxLeftMid = new VBox();
        mr_vBoxLeftMid.setPadding(new Insets(10,0,5,5));
        mr_vBoxLeftMid.setSpacing(1);
        //For HEADLINE
        mr_hBoxLeftTop = new HBox();
        mr_hBoxLeftTop.setPadding(new Insets(10,5,5,55));
        //LEFT BorderPane
        mr_borderPaneLeft = new BorderPane();
        mr_borderPaneLeft.setBottom(mr_hBoxLeftBottom);
        mr_borderPaneLeft.setCenter(mr_vBoxLeftMid);
        mr_borderPaneLeft.setTop(mr_hBoxLeftTop);
        mr_borderPaneLeft.setPrefWidth(200);
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
        mr_sceneScan = new Scene(mr_borderPaneMain,400 ,400);

        //Configuration Buttons
        mr_buttonManSearch = new Button("Manual Search");
        mr_buttonBack = new Button("<");
        mr_buttonForward = new Button(">");
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png",20,20,false,true);
        mr_buttonConfirm = new Button("",new ImageView(lr_imageCheckmarkIcon));
        mr_buttonSkip = new Button("Skip");
        mr_buttonSkip.setPadding(new Insets(3,5,3,5));

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
        mr_labelSuggestion.setFont(new Font("Arial",15));
        mr_labelSuggestion.setStyle("-fx-font-weight: bold;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_labelMovieTitle = new Label("Movie Title");
        mr_labelMovieTitle.setStyle("-fx-font-weight: bold;-fx-background-color: #cd5c5c");
        mr_labelMovieTitle.setFont(new Font("Arial",15));
        Image lr_imageFolderIcon = new Image("file:FolderIcon.png",20,20,false,true);
        mr_labelFolder = new Label ("Current Folder ",new ImageView(lr_imageFolderIcon));
        mr_labelFolder.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        Image lr_imageFileIcon = new Image("file:FileIcon.png",20,20,false,true);
        mr_labelFile = new Label ("File in Folder ",new ImageView(lr_imageFileIcon));
        mr_labelFile.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_imageMovie = new Image("file:te.jpg",180,250,false,true);
        mr_labelMoviePoster = new Label("",new ImageView(mr_imageMovie));
        Image lr_imageArrow = new Image ("file:BlackArrowDownward.png",25,125,false,true);
        mr_labelArrow = new Label("",new ImageView(lr_imageArrow));
        mr_labelArrow.setPadding(new Insets(0,0,0,42));
        mr_labelSpacing = new Label (" \n ");

        //Add elements
        mr_hBoxRightMidSmall.getChildren().addAll(mr_labelFolder,mr_buttonSkip);
        mr_hBoxRightBottom.getChildren().addAll(mr_buttonManSearch);
        mr_vBoxRightMid.getChildren().addAll(mr_hBoxRightMidSmall,mr_labelFolderName,mr_labelSpacing, mr_labelFile,mr_labelContentName,mr_labelArrow);
        mr_hBoxLeftBottom.getChildren().addAll(mr_buttonBack,mr_buttonForward,mr_buttonConfirm);
        mr_vBoxLeftMid.getChildren().addAll(mr_labelMovieTitle,mr_labelMoviePoster);
        mr_hBoxLeftTop.getChildren().addAll(mr_labelSuggestion);

    }

    public void setRootHandler () {
        mr_buttonRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_stageScan.setWidth(400);
                mr_stageScan.setHeight(400);
                mr_stageScan.setTitle("Scanner");
                mr_stageScan.setScene(mr_sceneScan);
                mr_stageScan.setResizable(false);
                mr_stageRoot.hide();
                mr_stageScan.show();
            }
        });
    }

}
