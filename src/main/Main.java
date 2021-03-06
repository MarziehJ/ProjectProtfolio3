package main;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.RegisterCourseModel;

public class Main extends Application {
    static String connectionString = "jdbc:sqlite:C:/RUC/SD/DB/ProjectProtfolio3.db";
    static RegisterCourseModel model = new RegisterCourseModel(connectionString);

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/RegisterCourse.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setModel(model);
        primaryStage.setTitle("Student Register Course System");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
