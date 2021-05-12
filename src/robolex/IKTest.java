package robolex;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point3D;
import robolex.*;

class IKTest {

	@Test
	void test_error() {
		Element r = new Element("root");
		Element t = new Element("target", r);
		Element arm = new Element("arm", r);
		Element j1 = new Element("Joint1", arm);
		j1.setTranslateZ(1);
		Element j2 = new Element("Joint2", j1);
		j2.setTranslateZ(1);
		Element j3 = new Element("Joint3", j2);
		j3.setTranslateZ(1);
		
		IK ik = new IK(arm, t);
		
		assertEquals(3, ik.error(0));
	}
	
	@Test
	void test_cost() {
		Element r = new Element("root");
		Element t = new Element("target", r);
		Element arm = new Element("arm", r);
		Element j1 = new Element("Joint1", arm);
		j1.setTranslateZ(1);
		Element j2 = new Element("Joint2", j1);
		j2.setTranslateZ(1);
		Element j3 = new Element("Joint3", j2);
		j3.setTranslateZ(1);
		
		IK ik = new IK(arm, t);
		
		assertEquals(3, ik.cost(0));
	}
	
	@Test
	void test_calculate() {
		Element r = new Element("root");
		Element t = new Element("target", r);
		t.setTranslateY(0);
		t.setTranslateY(0);
		t.setTranslateX(3);
		Element arm = new Element("arm", r);
		Element j1 = new Element("Joint1", arm);
		j1.setTranslateZ(1);
		Element j2 = new Element("Joint2", j1);
		j2.setTranslateZ(1);
		Element j3 = new Element("Joint3", j2);
		j3.setTranslateZ(1);
		Element a = arm.findInChildren("Joint1");
		a.limitedRotation = false;
		a.minRotX = -360;
		a.maxRotX = 360;
		a = arm.findInChildren("Joint2");
		a.limitedRotation = false;
		a.minRotX = -360;
		a.maxRotX = 360;
		
		IK ik = new IK(arm, t);
		ik.calculate(0);
		
		Point3D p = j3.getWorldPosition();
		assertEquals(0, Math.round(p.getX()));
		assertEquals(0, Math.round(p.getY()));
		assertEquals(3, Math.round(p.getZ()));
	}
}
