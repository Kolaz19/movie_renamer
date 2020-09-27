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
import javafx.stage.Stage;

public class ScanStage {

    //Root elements
    Stage mr_stageRoot;
    Button mr_buttonRoot;
    //Elements of second window
    Stage mr_stageScan;
    Scene mr_sceneScan;
    BorderPane mr_borderPaneMain, mr_borderPaneRight;
    Label mr_labelFolder, mr_labelFile, mr_labelSpacing;
    VBox mr_vBoxLeft, mr_vBoxRightMid;
    HBox mr_hBoxRightBottom;
    Button mr_buttonManSearch, mr_buttonBack, mr_buttonForward, mr_buttonConfirm;


    ScanStage (Stage ir_stageRoot, Button ir_buttonScan) {
        mr_stageRoot = ir_stageRoot;
        mr_buttonRoot = ir_buttonScan;

        //Preparation for Stage
        mr_stageScan = new Stage();
        mr_borderPaneMain = new BorderPane();
        mr_borderPaneRight = new BorderPane();
        mr_hBoxRightBottom = new HBox();
        mr_vBoxRightMid = new VBox();
        mr_vBoxRightMid.setPadding(new Insets(30,10,0,10));
        mr_hBoxRightBottom.setSpacing(10);
        mr_hBoxRightBottom.setPadding(new Insets(5,10,10,5));
        mr_vBoxLeft = new VBox();
        mr_sceneScan = new Scene(mr_borderPaneMain,480,290);
        mr_borderPaneMain.setLeft(mr_vBoxLeft);
        mr_borderPaneMain.setRight(mr_borderPaneRight);
        mr_borderPaneRight.setBottom(mr_hBoxRightBottom);
        mr_borderPaneRight.setCenter(mr_vBoxRightMid);

        //Configuration Buttons
        mr_buttonManSearch = new Button("Manual Search");
        mr_buttonBack = new Button("<");
        mr_buttonForward = new Button(">");
        mr_buttonConfirm = new Button("Confirm");

        //Configuration Labels
        Image lr_imageFolderIcon = new Image("file:FolderIcon.png",30,30,false,true);
        mr_labelFolder = new Label ("hey",new ImageView(lr_imageFolderIcon));
        Image lr_imageFileIcon = new Image("file:FileIcon.png",30,30,false,true);
        mr_labelFile = new Label ("hey",new ImageView(lr_imageFileIcon));
        mr_labelSpacing = new Label (" \n ");



        //Add elements
        mr_hBoxRightBottom.getChildren().addAll(mr_buttonManSearch,mr_buttonBack,mr_buttonForward,mr_buttonConfirm);
        mr_vBoxRightMid.getChildren().addAll(mr_labelFolder,mr_labelSpacing, mr_labelFile);
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
