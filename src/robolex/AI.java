package robolex;

import java.util.Random;

import javafx.geometry.Point3D;

public class AI {
	public static Element target;
	static Random r = new Random();

	public static void Update() {
		Point3D p = target.getWorldPosition();
		Point3D robot = Environment.robot.getWorldPosition();
		Point3D diff = p.subtract(robot);
		double angle = Math.atan2(diff.getX(), diff.getZ());
		angle = Math.toDegrees(angle);
		
		if (diff.magnitude() > .3 && Math.abs((360+Environment.robot.getRotateY())%360 - (angle+360)%360) > 1) {
			if ((Environment.robot.getRotateY()+360)%360 > (angle+360)%360) Environment.robot.setRotateY(Environment.robot.getRotateY() - .3);
			else 
			Environment.robot.setRotateY(Environment.robot.getRotateY() + .3);
			
		}else {
			
			if(diff.magnitude() > .2)Environment.robot.moveForward(.01);
		}
		if(diff.magnitude() <= .6) {
			target.setTranslateX((r.nextDouble()-0.5)*2*3);
			target.setTranslateZ((r.nextDouble()-0.5)*2*3);
		}
	}
}
