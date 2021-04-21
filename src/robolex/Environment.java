package robolex;


import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;


public class Environment{
	
	//private Group subRoot;
	static Element center;
	static Robot robot;
	
	public static void build() {
		/*
		Group newEnvElements = new Group();
		Group robot = Robot.initRobot();
		
		newEnvElements.getChildren().add(robot);
		
		MainController.subRoot.getChildren().add(newEnvElements);*/
		
		
		
		center = new Element("WorldCenter", MainController.subRoot);
		
		
		
		Box ground = new Box(10, 1, 10);
		ground.setTranslateY(0.5);
		PhongMaterial mat = new PhongMaterial(Color.GREENYELLOW);
		mat.setSpecularPower(1);
		ground.setMaterial(mat);
		
		center.group.getChildren().add(ground);
		
		
		robot = new Robot("Robot", center);
		robot.group.setTranslateY(-.5);
		System.out.println(center.toString());

		System.out.println(" ");
		System.out.println(center.findInChildren("ArmRF").toString());

	}
	
	public static void tick(long now) {
		center.tick(now);
	}
}
