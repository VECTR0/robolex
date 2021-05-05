package robolex;

import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;

public class IK { /* Inverse Kinematics Estimation Algorithm IKEA */
	public Element target;
	private final Element armRoot;
	private Element end;
	private Element j1, j2;
	
	public IK(Element armRoot) {
		this.armRoot = armRoot;
		j1 = armRoot.findInChildren("Joint1");
		j2 = armRoot.findInChildren("Joint2");
		end = armRoot.findInChildren("Joint3");
		
		target = Environment.center.findInChildren("Target");
		if(target == null)target = Environment.center;
	}

	public double error() {
		return (target.group.localToScene(Point3D.ZERO).subtract(end.group.localToScene(Point3D.ZERO))).magnitude();
	}
	
	public double cost() {
		return error() ;//+ (float)(Math.abs(armRoot.getRotateX()) + Math.abs(j1.getRotateX()) + Math.abs(j2.getRotateX()))*0.0001f;
	}
	public void Foo() {
		double d0=0, d1=0, d2=0, d3=0;
		double c, dx = 0.1f;
		double alfa = 100f;
		for (int n = 0; n < 100; n++) {
			armRoot.limitedRotation = false;
			c = cost();
			armRoot.setRotateY(armRoot.getRotateY() + dx);
			d0 = cost() - c;
			armRoot.setRotateY(armRoot.getRotateY() - dx);

			c = cost();
			armRoot.setRotateX(armRoot.getRotateX() + dx);
			d1 = cost() - c;
			armRoot.setRotateX(armRoot.getRotateX() - dx);
			armRoot.limitedRotation = true;
			
			j1.limitedRotation = false;
			c = cost();
			j1.setRotateX(j1.getRotateX() + dx);
			d2 = cost() - c;
			j1.setRotateX(j1.getRotateX() - dx);
			j1.limitedRotation = true;
			
			j2.limitedRotation = false;
			c = cost();
			j2.setRotateX(j2.getRotateX() + dx);
			d3 = cost() - c;
			j2.setRotateX(j2.getRotateX() - dx);
			j2.limitedRotation = true;
			
			//if(error() > 0.1) alfa *= 10;
			armRoot.setRotateY(armRoot.getRotateY() - d0 * alfa);
			armRoot.setRotateX(armRoot.getRotateX() - d1 * alfa);
			j1.setRotateX(j1.getRotateX() - d2 * alfa);
			j2.setRotateX(j2.getRotateX() - d3 * alfa);

			if(error()<0.01f)break;
		}
		//System.out.println(d3 +" " +j2.getRotateX() + " " +j2.minRotX +" "+ j2.maxRotX);
	}
}
