package robolex;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Robot extends Element {
	static double sizeX = 0.4;
	static double sizeY = 0.3;
	static double sizeZ = 0.9;
	static Group arms;
	
	public Robot(String name, Element center) {
		super(name, center);
		Box body = new Box(sizeX, sizeY, sizeZ);
		group.getChildren().add(body);

		addArm("ArmLF", -sizeX/2, -sizeY/2, sizeZ/2, -45);
		addArm("ArmLB", -sizeX/2, -sizeY/2, -sizeZ/2, 180+45);
		addArm("ArmRF", +sizeX/2, -sizeY/2, sizeZ/2, 45);
		addArm("ArmRB", +sizeX/2, -sizeY/2, -sizeZ/2, 180-45);
		
	}
	
	public void addArm(String name, double x, double y, double z, double ry) {
		Element arm = new Element(name, this);
		arm.setTranslateX(x);
		arm.setTranslateY(y);
		arm.setTranslateZ(z);
		arm.setRotateY(ry);
		arm.setRotateX(10);
		
		
		addJoint("Joint3", addJoint("Joint2", addJoint("Joint1", arm, 0.5, -80), 0.4, 30), 0.2, 0);
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
		if (now > lastTime + 10*1e6) {
			Element armLF = this.findInChildren("ArmLF");
			armLF.setRotateY(armLF.getRotateY()+speed);

			Element armLB = this.findInChildren("ArmLB");
			armLB.setRotateY(armLB.getRotateY()-speed);

			Element armRF = this.findInChildren("ArmRF");
			armRF.setRotateY(armRF.getRotateY()+speed);

			Element armRB = this.findInChildren("ArmRB");
			armRB.setRotateY(armRB.getRotateY()-speed);
			
			
			current += speed;
			if(current <= -30 || current >= 30) speed = -speed;
			lastTime = now;
		}
	}
}
