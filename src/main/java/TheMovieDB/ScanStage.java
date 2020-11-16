package TheMovieDB;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

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
    Image mr_imageMovie, mr_bImage;
    ArrayList<File> ma_directoryList;
    MovieHandler mr_movieHandler;
    BackgroundImage mr_backgroundImage;
    Background mr_background;
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
        mr_sceneScan = new Scene(mr_borderPaneMain, 430, 435);

        //Configuration Buttons
        mr_buttonManSearch = new Button("Manual Search");
        mr_buttonBack = new Button("<");
        mr_buttonForward = new Button(">");
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png", 20, 20, false, true);
        mr_buttonConfirm = new Button("", new ImageView(lr_imageCheckmarkIcon));
        mr_buttonSkip = new Button("Skip");
        mr_buttonSkip.setPadding(new Insets(3, 5, 3, 5));

        //Configuration Labels
        mr_labelFolderName = new Label("");
        mr_labelFolderName.setAlignment(Pos.TOP_LEFT);
        mr_labelFolderName.setWrapText(true);
        mr_labelFolderName.setMaxWidth(170);
        mr_labelFolderName.setMinHeight(34);
        mr_labelFolderName.setFont(new Font("Arial",15));
        mr_labelContentName = new Label("");
        mr_labelContentName.setAlignment(Pos.TOP_LEFT);
        mr_labelContentName.setWrapText(true);
        mr_labelContentName.setMaxWidth(170);
        mr_labelContentName.setMinHeight(34);
        mr_labelContentName.setFont(new Font("Arial",15));
        mr_labelSuggestion = new Label("Suggestion:");
        mr_labelSuggestion.setFont(new Font("Arial", 15));
        mr_labelSuggestion.setStyle("-fx-font-weight: bold;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_labelMovieTitle = new Label("Movie Title");
        mr_labelMovieTitle.setStyle("-fx-font-weight: bold;-fx-background-color: #cd5c5c");
        mr_labelMovieTitle.setFont(new Font("Arial", 15));
        mr_labelMovieTitle.setMaxWidth(200);
        mr_labelMovieTitle.setMinHeight(40);
        mr_labelMovieTitle.setMaxHeight(40);
        mr_labelMovieTitle.setWrapText(true);
        mr_labelMovieTitle.setPadding(new Insets(0,0,0,5));
        Image lr_imageFolderIcon = new Image("file:FolderIcon.png", 20, 20, false, true);
        mr_labelFolder = new Label("Current Folder ", new ImageView(lr_imageFolderIcon));
        mr_labelFolder.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        Image lr_imageFileIcon = new Image("file:FileIcon.png", 20, 20, false, true);
        mr_labelFile = new Label("File in Folder ", new ImageView(lr_imageFileIcon));
        mr_labelFile.setStyle("-fx-font-weight: bold;-fx-background-color: abc;-fx-border-color: black, transparent, black; -fx-border-width: 0 0 1 0, 0 0 1 0, 0 0 1 0; -fx-border-insets: 0 0 1 0, 0 0 2 0, 0 0 3 0");
        mr_labelMoviePoster = new Label("");
        Image lr_imageArrow = new Image("file:BlackArrowDownward.png", 25, 150, false, true);
        mr_labelArrow = new Label("", new ImageView(lr_imageArrow));
        mr_labelArrow.setPadding(new Insets(0, 0, 0, 42));
        mr_labelSpacing = new Label(" \n ");

        //Background
        mr_bImage = new Image("file:BackgroundScan.jpg",430,430,false,true);
        mr_backgroundImage = new BackgroundImage(mr_bImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        mr_background = new Background(mr_backgroundImage);
        mr_borderPaneMain.setBackground(mr_background);

        //Add elements
        mr_hBoxRightMidSmall.getChildren().addAll(mr_labelFolder, mr_buttonSkip);
        mr_hBoxRightBottom.getChildren().addAll(mr_buttonManSearch);
        mr_vBoxRightMid.getChildren().addAll(mr_hBoxRightMidSmall, mr_labelFolderName, mr_labelSpacing, mr_labelFile, mr_labelContentName, mr_labelArrow);
        mr_hBoxLeftBottom.getChildren().addAll(mr_buttonBack, mr_buttonForward, mr_buttonConfirm);
        mr_vBoxLeftMid.getChildren().addAll(mr_labelMovieTitle, mr_labelMoviePoster);
        mr_hBoxLeftTop.getChildren().addAll(mr_labelSuggestion);

        //Add ActionHandlers
        mr_stageScan.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                mr_stageScan.close();
                mr_stageRoot.show();
            }
        });

        mr_buttonForward.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_movieHandler.nextMovie();
                mr_labelMovieTitle.setText(mr_movieHandler.getMovieName(false));
                mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
            }
        });

        mr_buttonBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mr_movieHandler.previousMovie();
                mr_labelMovieTitle.setText(mr_movieHandler.getMovieName(false));
                mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
            }
        });

        mr_buttonConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setDirectoryName();
                try {
                    nextFolder();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        mr_buttonSkip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    nextFolder();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        mr_buttonManSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setMovieListThroughWindow(true);
            }
        });

        mr_stageScan.setWidth(420);
        mr_stageScan.setHeight(455);
        mr_stageScan.setTitle("Scanner");
        mr_stageScan.setScene(mr_sceneScan);
        mr_stageScan.setResizable(false);
    }


    public void setFolderList() throws FileNotFoundException {
        File lr_directoryPath = new File(mr_labelPathRoot.getText());
        File[] lr_filesList = lr_directoryPath.listFiles();
        if (lr_filesList == null) {
            this.mr_stageScan.close();
            this.mr_stageRoot.show();
            this.allScanned(false);
            return;
        }

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
            mr_stageScan.hide();
            this.allScanned(true);
            return;
        }

        String lv_movieName = ma_directoryList.get(mv_currentFolderCount).getName();
        //Initialize MovieHandler with ALL movies for every new folder
        mr_movieHandler = new MovieHandler(lv_movieName);
        if (mr_movieHandler.isCorrectlyNamed()) {
            this.nextFolder();
            return;
        }

        //Set name of folder and content (label)
        mr_labelContentName.setText("");
        mr_labelFolderName.setText(ma_directoryList.get(mv_currentFolderCount).getName());
        ArrayList<File> la_contentInFolder = new ArrayList<File>(Arrays.asList(ma_directoryList.get(mv_currentFolderCount).listFiles()));
        for (File lr_file : la_contentInFolder) {
            if (lr_file.isFile()) {
                String lv_end = lr_file.getName();
                if ((lv_end.endsWith(".mkv")) || (lv_end.endsWith(".avi")) || (lv_end.endsWith(".mp4")) || (lv_end.endsWith(".flv")) || (lv_end.endsWith(".vob")) || (lv_end.endsWith(".ogv")) || (lv_end.endsWith(".ogg")) || (lv_end.endsWith(".mov")) || (lv_end.endsWith(".wmv")) || (lv_end.endsWith(".m4p")) || (lv_end.endsWith(".m4v")) || (lv_end.endsWith(".mpg")) || (lv_end.endsWith(".mpeg")) || (lv_end.endsWith("."))) {
                    mr_labelContentName.setText(lr_file.getName());
                    break;
                }
            }
        }

        if (!mr_movieHandler.hasEntries()) {
            setMovieListThroughWindow(false);
            return;
        }

        mr_labelMovieTitle.setText(mr_movieHandler.getMovieName(false));
        mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
    }

    public void setDirectoryName () {
        String lv_nameOfNewPath = ma_directoryList.get(mv_currentFolderCount).getAbsolutePath();
        String lv_movieName = mr_movieHandler.getMovieName(true);
        //Replace chars that are not allowed
        String[] la_chars = {"<",">",":","\"","/","\\","|","?","*"};
        for (String lv_char : la_chars) {
            lv_movieName = lv_movieName.replace(" " + lv_char + " "," ");
            lv_movieName = lv_movieName.replace(lv_char + " "," ");
            lv_movieName = lv_movieName.replace(lv_char," ");
        }
        lv_nameOfNewPath = lv_nameOfNewPath.replace(ma_directoryList.get(mv_currentFolderCount).getName(),lv_movieName);
        File lr_fileRename = new File (lv_nameOfNewPath);
        ma_directoryList.get(mv_currentFolderCount).renameTo(lr_fileRename);
    }

    public void setMovieListThroughWindow(boolean iv_manualSearch) {
        Stage lr_stageInput = new Stage();
        lr_stageInput.setOnCloseRequest(e -> mr_stageRoot.show());
        lr_stageInput.setHeight(70);
        lr_stageInput.setWidth(300);
        lr_stageInput.setHeight(120);
        lr_stageInput.setTitle("Enter movie name");
        BorderPane lr_borderPaneInput = new BorderPane();
        Scene lr_sceneInput = new Scene(lr_borderPaneInput,300,70);

        //Elements of window
        String lv_noData;
        Label lr_labelNoDataFound = new Label ();
        if (!iv_manualSearch) {
            lv_noData = "No movies found to \"" + ma_directoryList.get(mv_currentFolderCount).getName() + "\"" + "\nEnter a better movie name:";
            lr_borderPaneInput.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #FFB6C1, #DCDCDC);");
            lr_labelNoDataFound.setStyle("-fx-background-color: lightcoral;");
        } else {
            lv_noData = "Search for a better movie name for \nfolder \"" + mr_labelFolderName.getText() + "\"";
            lr_borderPaneInput.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #B0E0E6, #F5DEB3);");
            lr_labelNoDataFound.setStyle("-fx-background-color: khaki;");
        }
        lr_labelNoDataFound.setText(lv_noData);
        lr_labelNoDataFound.setPadding(new Insets(4,4,4,10));
        TextField lr_inputField = new TextField();
        lr_inputField.setPromptText("Enter better movie name");
        lr_inputField.setMaxSize(150,5);
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png", 20, 20, false, true);
        Button lr_buttonConfirm = new Button("",new ImageView(lr_imageCheckmarkIcon));
        Button lr_buttonSkip = new Button("Skip folder");

        lr_borderPaneInput.setPadding(new Insets(5,10,5,10));
        lr_borderPaneInput.setLeft(lr_buttonSkip);
        lr_borderPaneInput.setTop(lr_labelNoDataFound);
        lr_borderPaneInput.setRight(lr_buttonConfirm);
        lr_borderPaneInput.setCenter(lr_inputField);
        BorderPane.setAlignment(lr_labelNoDataFound,Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(lr_inputField,Pos.CENTER);
        BorderPane.setAlignment(lr_buttonSkip,Pos.CENTER_LEFT);
        BorderPane.setAlignment(lr_buttonConfirm,Pos.CENTER_RIGHT);

        lr_stageInput.setScene(lr_sceneInput);
        lr_stageInput.setResizable(false);
        lr_stageInput.show();
        mr_stageScan.hide();
        //Event Handler
        lr_buttonSkip.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lr_stageInput.hide();
                mr_stageScan.show();
                try {
                    nextFolder();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        lr_buttonConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    mr_movieHandler = new MovieHandler(lr_inputField.getText());
                    if (mr_movieHandler.hasEntries()) {
                        mr_labelMoviePoster.setGraphic(new ImageView(mr_movieHandler.getMoviePoster()));
                        mr_labelMovieTitle.setText(mr_movieHandler.getMovieName(false));
                        lr_stageInput.hide();
                        mr_stageScan.show();
                    } else {
                        lr_labelNoDataFound.setText("No movie found for " + lr_inputField.getText());
                        lr_inputField.clear();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void allScanned(boolean iv_foldersExist) {
        Stage lr_stageSuccess = new Stage();
        lr_stageSuccess.setOnCloseRequest(e -> mr_stageRoot.show());
        lr_stageSuccess.setHeight(80);
        lr_stageSuccess.setWidth(250);
        if (iv_foldersExist) {
            lr_stageSuccess.setTitle("Successful");
        } else {
            lr_stageSuccess.setTitle("Try again");
        }
        BorderPane lr_borderPaneSuccess = new BorderPane();
        Scene lr_sceneSuccess = new Scene(lr_borderPaneSuccess,300,140);
        Label lr_labelAllScanned = new Label ();
        if (iv_foldersExist) {
            lr_labelAllScanned.setText("Every folder was scanned!");
        } else {
            lr_labelAllScanned.setText("Directory does not exist!");
        }
        Image lr_imageCheckmarkIcon = new Image("file:CheckMark.png", 20, 20, false, true);
        Button lr_buttonConfirm = new Button("",new ImageView(lr_imageCheckmarkIcon));

        lr_borderPaneSuccess.setCenter(lr_labelAllScanned);
        lr_borderPaneSuccess.setRight(lr_buttonConfirm);
        lr_stageSuccess.setScene(lr_sceneSuccess);
        BorderPane.setAlignment(lr_buttonConfirm,Pos.CENTER);
        lr_stageSuccess.setResizable(false);
        lr_borderPaneSuccess.setPadding(new Insets(0,5,0,0));
        lr_labelAllScanned.setFont(new Font("Serif", 15));
        lr_labelAllScanned.setStyle("-fx-font-weight: bold;");

        lr_borderPaneSuccess.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #FFFAE6, #8FBC8F);");
        lr_stageSuccess.show();

        lr_buttonConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lr_stageSuccess.close();
                mr_stageRoot.show();
            }
        });
    }
}
