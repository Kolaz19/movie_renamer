package TheMovieDB;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScanStage {

    //Root elements
    Stage mr_stageRoot;
    Button mr_buttonRoot;
    //Elements of second window
    Stage mr_stageScan;
    Scene mr_sceneScan;
    BorderPane mr_borderPaneMain;
    BorderPane mr_borderPaneRight;
    VBox mr_vBoxLeft;

    ScanStage (Stage ir_stageRoot, Button ir_buttonScan) {
        mr_stageRoot = ir_stageRoot;
        mr_buttonRoot = ir_buttonScan;
        //Preparation for Stage
        mr_stageScan = new Stage();
        mr_borderPaneMain = new BorderPane();
        mr_borderPaneRight = new BorderPane();
        mr_vBoxLeft = new VBox();
        mr_sceneScan = new Scene(mr_borderPaneMain,500,300);
        mr_borderPaneMain.setLeft(mr_vBoxLeft);
        mr_borderPaneMain.setRight(mr_borderPaneRight);
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
