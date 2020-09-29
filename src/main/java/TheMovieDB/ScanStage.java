package TheMovieDB;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    Label mr_labelFolder, mr_labelFile, mr_labelSpacing, mr_labelMovieTitle,mr_labelSuggestion;
    VBox mr_vBoxRightMid, mr_vBoxLeftTop;
    HBox mr_hBoxRightBottom, mr_hBoxLeftBottom;
    Button mr_buttonManSearch, mr_buttonBack, mr_buttonForward, mr_buttonConfirm;


    ScanStage (Stage ir_stageRoot, Button ir_buttonScan) {
        mr_stageRoot = ir_stageRoot;
        mr_buttonRoot = ir_buttonScan;

        //Preparation for Stage
        mr_hBoxRightBottom = new HBox();
        mr_hBoxRightBottom.setSpacing(10);
        mr_hBoxRightBottom.setPadding(new Insets(5,10,10,5));

        mr_hBoxLeftBottom = new HBox();
        mr_hBoxLeftBottom.setSpacing(10);
        mr_hBoxLeftBottom.setPadding(new Insets(5,10,10,70));

        mr_vBoxRightMid = new VBox();
        mr_vBoxRightMid.setPadding(new Insets(30,10,0,10));

        mr_vBoxLeftTop = new VBox();
        mr_vBoxLeftTop.setPadding(new Insets(10,0,10,85));
        mr_vBoxLeftTop.setSpacing(5);

        mr_borderPaneLeft = new BorderPane();
        mr_borderPaneLeft.setBottom(mr_hBoxLeftBottom);
        mr_borderPaneLeft.setTop(mr_vBoxLeftTop);
        mr_borderPaneLeft.setPrefWidth(240);

        mr_borderPaneRight = new BorderPane();
        mr_borderPaneRight.setBottom(mr_hBoxRightBottom);
        mr_borderPaneRight.setCenter(mr_vBoxRightMid);
        mr_borderPaneRight.setPrefWidth(240);

        mr_borderPaneMain = new BorderPane();
        mr_borderPaneMain.setLeft(mr_borderPaneLeft);
        mr_borderPaneMain.setRight(mr_borderPaneRight);

        mr_stageScan = new Stage();
        mr_sceneScan = new Scene(mr_borderPaneMain,480,290);

        //Configuration Buttons
        mr_buttonManSearch = new Button("Manual Search");
        mr_buttonBack = new Button("<");
        mr_buttonForward = new Button(">");
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png",20,20,false,true);
        mr_buttonConfirm = new Button("",new ImageView(lr_imageCheckmarkIcon));

        //Configuration Labels
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
        mr_labelSpacing = new Label (" \n ");

        //Add elements
        mr_hBoxRightBottom.getChildren().addAll(mr_buttonManSearch);
        mr_vBoxRightMid.getChildren().addAll(mr_labelFolder,mr_labelSpacing, mr_labelFile);
        mr_hBoxLeftBottom.getChildren().addAll(mr_buttonBack,mr_buttonForward,mr_buttonConfirm);
        mr_vBoxLeftTop.getChildren().addAll(mr_labelSuggestion,mr_labelMovieTitle);

    }

    public void setRootHandler () {
        mr_buttonRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_stageScan.setWidth(500);
                mr_stageScan.setHeight(300);
                mr_stageScan.setTitle("Scanner");
                mr_stageScan.setScene(mr_sceneScan);
                mr_stageScan.setResizable(false);
                mr_stageScan.show();
            }
        });
    }

}
