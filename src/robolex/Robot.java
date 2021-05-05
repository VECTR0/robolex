package robolex;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class Robot extends Element {
	static double sizeX = 0.4;
	static double sizeY = 0.3;
	static double sizeZ = 0.9;
	static Group arms;
	static IK ikLF, ikLB, ikRF, ikRB;
	public static Target LF, LB, RF, RB;
	private static long oldTime=0, waitingTime = 1000000000;
	static boolean resetLF = false, resetLB = false, resetRF = false, resetRB = false;
	
	public Robot(String name, Element center) {
		super(name, center);
		Box body = new Box(sizeX, sizeY, sizeZ);
		group.getChildren().add(body);
		Sphere s = new Sphere(0.03);
		s.setMaterial(new PhongMaterial(Color.RED));
		s.setTranslateX(sizeX/4);
		s.setTranslateY(-sizeY/2);
		s.setTranslateZ(sizeZ/2);
		group.getChildren().add(s);
		s = new Sphere(0.03);
		s.setMaterial(new PhongMaterial(Color.RED));
		s.setTranslateX(-sizeX/4);
		s.setTranslateY(-sizeY/2);
		s.setTranslateZ(sizeZ/2);
		group.getChildren().add(s);

		LF = new Target("TargetLF", this, Color.CYAN);
		LF.setTranslateX(-1);
		LF.setTranslateZ(1.1);
		LF.setTranslateY(0.5);
		LB = new Target("TargetLB", this, Color.CYAN);
		LB.setTranslateX(-1);
		LB.setTranslateZ(-1.1);
		LB.setTranslateY(0.5);
		RF = new Target("TargetRF", this, Color.CYAN);
		RF.setTranslateX(1);
		RF.setTranslateZ(.9);
		RF.setTranslateY(0.5);
		RB = new Target("TargetRB", this, Color.CYAN);
		RB.setTranslateX(1);
		RB.setTranslateZ(-.9);
		RB.setTranslateY(0.5);
		
		
		addArm("ArmLF", -sizeX/2, -sizeY/2, sizeZ/2, -45);
		addArm("ArmLB", -sizeX/2, -sizeY/2, -sizeZ/2, -180+45);
		addArm("ArmRF", +sizeX/2, -sizeY/2, sizeZ/2, 45);
		addArm("ArmRB", +sizeX/2, -sizeY/2, -sizeZ/2, 180-45);
		
		Element a = findInChildren("ArmLF");
		ikLF = new IK(a, Target.LF);
		a.limitedRotation = true;
		a.minRotY = -110;
		a.maxRotY = 0;
		
		a = findInChildren("ArmLB");
		ikLB = new IK(a, Target.LB);
		a.limitedRotation = true;
		a.minRotY = -180;
		a.maxRotY = -70;
		
		a = findInChildren("ArmRF");
		ikRF = new IK(a, Target.RF);
		a.limitedRotation = true;
		a.minRotY = 0;
		a.maxRotY = 110;
		
		a = findInChildren("ArmRB");
		ikRB = new IK(a, Target.RB);
		a.limitedRotation = true;
		a.minRotY = 70;
		a.maxRotY = 180;
		

		
	}
	
	public void addArm(String name, double x, double y, double z, double ry) {
		Element arm = new Element(name, this);
		arm.setTranslateX(x);
		arm.setTranslateY(y);
		arm.setTranslateZ(z);
		arm.setRotateY(ry);
		arm.setRotateX(10);
		
		arm.limitedRotation = true;
		arm.minRotX = -30;
		arm.maxRotX = 45;

		
		addJoint("Joint3", addJoint("Joint2", addJoint("Joint1", arm, 0.8, 270), 0.7, 45), 0.2, 0);
		
		Box box = new Box(0.05, 0.05, 0.8);
		box.setTranslateZ(0.8/2);
		arm.group.getChildren().add(box);
		
		box = new Box(0.05, 0.05, 0.7);
		box.setTranslateZ(0.7/2);
		arm.findInChildren("Joint1").group.getChildren().add(box);
		
		box = new Box(0.05, 0.05, 0.2);
		box.setTranslateZ(0.2/2);
		arm.findInChildren("Joint2").group.getChildren().add(box);
		
		Element a = arm.findInChildren("Joint1");
		a.limitedRotation = true;
		a.minRotX = 225;
		a.maxRotX = 315;
		
		a = findInChildren("Joint2");
		a.limitedRotation = true;
		a.minRotX = 45;
		a.maxRotX = 135;

	}
	
	public Element addJoint(String name, Element parent, double z, double rx) {
		Element joint = new Element(name, parent);
		joint.setTranslateZ(z);
		joint.setRotateX(rx);
		return joint;
	}
	
	public void moveRobotForward(double distance) {
		group.setTranslateX(group.getTranslateX() - distance);
	}

	private double current = 0, speed = 3;
	static long lastTime = 0;
	
	public void tick(long now) {
		if (lastTime == 0)
			lastTime = now;
		if (now > lastTime + 1*1e4) {
			
			double speed;
			if(MainController.keysDown.contains(KeyCode.CONTROL))speed = 0.02;
			else speed = .005;
			if(MainController.keysDown.contains(KeyCode.I))moveForward(2*speed);
			if(MainController.keysDown.contains(KeyCode.J))moveRight(-speed);
			if(MainController.keysDown.contains(KeyCode.K))moveForward(-2*speed);
			if(MainController.keysDown.contains(KeyCode.L))moveRight(speed);
			
			if(MainController.keysDown.contains(KeyCode.U))setTranslateY(getTranslateY() + speed);
			if(MainController.keysDown.contains(KeyCode.O))setTranslateY(getTranslateY() - speed);
			
			
			if(MainController.keysDown.contains(KeyCode.N))setRotateX(getRotateX() + .4);
			if(MainController.keysDown.contains(KeyCode.M))setRotateX(getRotateX() - .4);

			if(MainController.keysDown.contains(KeyCode.G))setRotateY(getRotateY() - .5);
			if(MainController.keysDown.contains(KeyCode.H))setRotateY(getRotateY() + .5);
			if((ikLF.error(now) > 0.1 || resetLF) && !ikLF.interpolating) {
				ikLF.target.copyWorldPosition(LF, false);
				resetLF = false;
			}
			if((ikLB.error(now) > 0.1 || (resetLB && !ikRF.interpolating && !resetRF)) && !ikLB.interpolating && !ikRB.interpolating) {
				ikLB.target.copyWorldPosition(LB, false);
				resetLB = false;
			}
			if((ikRF.error(now) > 0.1 || (resetRF && !ikRB.interpolating && !resetRB)) && !ikRF.interpolating && !ikLF.interpolating) {
				ikRF.target.copyWorldPosition(RF, false);
				resetRF = false;
			}
			if((ikRB.error(now) > 0.1 || (resetRB && !ikLF.interpolating && !resetLF)) && !ikRB.interpolating && !ikLB.interpolating) {
				ikRB.target.copyWorldPosition(RB, false);
				resetRB = false;
			}
			
			if(now >= oldTime + waitingTime) {
				oldTime = now;
				resetLF = true;
				resetLB = true;
				resetRF = true;
				resetRB = true;
			}
			if(MainController.keysDown.size() > 0)oldTime = now;
			ikLF.Foo(now);
			ikLB.Foo(now);
			ikRF.Foo(now);
			ikRB.Foo(now);
		
			lastTime = now;
		}
	}
}
