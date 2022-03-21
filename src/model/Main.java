package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**This class creates an inventory management app.*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

/**This is the main method.
 This launches the program.
 @param args args*/
    public static void main(String[] args) {
        launch(args);
    }

}
