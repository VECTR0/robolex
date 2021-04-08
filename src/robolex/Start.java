package robolex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Start extends Application {

	public static void main(String[] args) {
		System.out.println("Application started");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			VBox root = (VBox) fxmlLoader.load();
			FXMLController fxmlController = (FXMLController)fxmlLoader.getController();
			Scene scene = new Scene(root, 1270, 795, true);
			scene.getStylesheets().add(getClass().getResource("robolex.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Robolex - Inverse Kinematics simulator 2020");
			primaryStage.show();

			Group sRoot = new Group();
			SubScene sub = new SubScene(sRoot, 1280, 720, true, SceneAntialiasing.BALANCED);
			sub.setFill(Color.ANTIQUEWHITE);
			root.getChildren().add(sub);
		
			MainController mainController = new MainController(primaryStage, scene, sub, sRoot);
			
			System.out.println(fxmlController);
			fxmlController.mainController = mainController;
			mainController.start();
			mainController.buildScene();
			fxmlController.initilize();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
