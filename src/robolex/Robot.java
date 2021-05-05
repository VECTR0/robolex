package robolex;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Box;

public class Robot extends Element {
	static double sizeX = 0.4;
	static double sizeY = 0.3;
	static double sizeZ = 0.9;
	static Group arms;
	static IK ik, ik2, ik3, ik4;
	
	public Robot(String name, Element center) {
		super(name, center);
		Box body = new Box(sizeX, sizeY, sizeZ);
		group.getChildren().add(body);

		addArm("ArmLF", -sizeX/2, -sizeY/2, sizeZ/2, -45);
		addArm("ArmLB", -sizeX/2, -sizeY/2, -sizeZ/2, -180+45);
		addArm("ArmRF", +sizeX/2, -sizeY/2, sizeZ/2, 45);
		addArm("ArmRB", +sizeX/2, -sizeY/2, -sizeZ/2, 180-45);
		
		Element a = findInChildren("ArmLF");
		ik = new IK(a);
		a.limitedRotation = true;
		a.minRotY = -110;
		a.maxRotY = 0;
		
		a = findInChildren("ArmLB");
		ik2 = new IK(a);
		a.limitedRotation = true;
		a.minRotY = -180;
		a.maxRotY = -70;
		
		a = findInChildren("ArmRF");
		ik3 = new IK(a);
		a.limitedRotation = true;
		a.minRotY = 0;
		a.maxRotY = 110;
		
		a = findInChildren("ArmRB");
		ik4 = new IK(a);
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
		arm.minRotX = 0;
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
		a.minRotX = -45;
		a.maxRotX = -135;
		
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
			
			if(MainController.keysDown.contains(KeyCode.N))setTranslateY(getTranslateY() + .004);
			if(MainController.keysDown.contains(KeyCode.M))setTranslateY(getTranslateY() - .004);
			ik.Foo();
			//ik2.Foo();

			//ik3.Foo();

			//ik4.Foo();
			
		/*	Element armLF = this.findInChildren("ArmLF");
			armLF.setRotateY(armLF.getRotateY()+speed);

			Element armLB = this.findInChildren("ArmLB");
			armLB.setRotateY(armLB.getRotateY()-speed);

			Element armRF = this.findInChildren("ArmRF");
			armRF.setRotateY(armRF.getRotateY()+speed);

			Element armRB = this.findInChildren("ArmRB");
			armRB.setRotateY(armRB.getRotateY()-speed);
			
			Element joint1 = armRB.findInChildren("Joint1");
			joint1.setRotateX(joint1.getRotateX()-speed*2);
			
			current += speed;
			if(current <= -30 || current >= 30) speed = -speed;*/
			
			lastTime = now;
		}
	}
}
