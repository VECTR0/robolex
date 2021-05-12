package robolex;

import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;

public class IK {
	public Element target;
	private final Element armRoot;
	private Element end;
	private Element j1, j2;
	public boolean interpolating = false;
	private long oldTime, interpolationTime = 100000000;
	Point3D old = new Point3D(0, 0, 0);
	
	
	public IK(Element armRoot, Element target) {
		this.armRoot = armRoot;
		j1 = armRoot.findInChildren("Joint1");
		j2 = armRoot.findInChildren("Joint2");
		end = armRoot.findInChildren("Joint3");
		
		this.target = target;
	}

	public double error(long now) {
		Point3D cur = end.group.localToScene(Point3D.ZERO);
		Point3D tar = (target.group.localToScene(Point3D.ZERO));
		double ix=0, iy=0, iz=0;
		if(!interpolating && (old.getX() != tar.getX() || old.getY() != tar.getY() || old.getZ() != tar.getZ())) {
			interpolating = true;
			oldTime = now;
		}
		
		if(interpolating){
			ix = 1.0*(old.getX()*(interpolationTime - (now-oldTime)) + tar.getX()*((now-oldTime))) / interpolationTime;
			iy = 1.0*(old.getY()*(interpolationTime - (now-oldTime)) + tar.getY()*((now-oldTime))) / interpolationTime 
					+((1.0*(now-oldTime)/interpolationTime))*((1.0*(now-oldTime)/interpolationTime) - 1)*0.5;
			iz = 1.0*(old.getZ()*(interpolationTime - (now-oldTime)) + tar.getZ()*((now-oldTime))) / interpolationTime;
			if(now >= oldTime + interpolationTime) {
				interpolating = false;
				old = new Point3D(tar.getX(), tar.getY(), tar.getZ());
			}
			return ((new Point3D(ix,iy,iz)).subtract(cur)).magnitude();
		}	
		return (tar.subtract(cur)).magnitude();
	}
	
	public double cost(long now) {
		return error(now);
	}
	
	public void calculate(long now) {
		double d0=0, d1=0, d2=0, d3=0;
		double c, dx = 0.1f;
		double alfa = 100f;
		for (int n = 0; n < 100; n++) {
			armRoot.limitedRotation = false;
			c = cost(now);
			armRoot.setRotateY(armRoot.getRotateY() + dx);
			d0 = cost(now) - c;
			armRoot.setRotateY(armRoot.getRotateY() - dx);

			c = cost(now);
			armRoot.setRotateX(armRoot.getRotateX() + dx);
			d1 = cost(now) - c;
			armRoot.setRotateX(armRoot.getRotateX() - dx);
			armRoot.limitedRotation = true;
			
			j1.limitedRotation = false;
			c = cost(now);
			j1.setRotateX(j1.getRotateX() + dx);
			d2 = cost(now) - c;
			j1.setRotateX(j1.getRotateX() - dx);
			j1.limitedRotation = true;
			
			j2.limitedRotation = false;
			c = cost(now);
			j2.setRotateX(j2.getRotateX() + dx);
			d3 = cost(now) - c;
			j2.setRotateX(j2.getRotateX() - dx);
			j2.limitedRotation = true;
			
			armRoot.setRotateY(armRoot.getRotateY() - d0 * alfa);
			armRoot.setRotateX(armRoot.getRotateX() - d1 * alfa);
			j1.setRotateX(j1.getRotateX() - d2 * alfa);
			j2.setRotateX(j2.getRotateX() - d3 * alfa);

			if(error(now)<0.01f)break;
		}
	}
}
