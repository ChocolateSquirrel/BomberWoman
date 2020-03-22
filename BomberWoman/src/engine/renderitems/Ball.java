package engine.renderitems;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

import engine.EngineApplication;
import game.map.Color;

public class Ball extends RenderItem {
	
	public Ball(Vector3f v, Color color) {
		super();
		Sphere s = new Sphere(16, 16, .25f);
		Geometry sphere = new Geometry("Sphere", s);
		Material mat = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", new ColorRGBA(color.R/255.f, color.G/255.f, color.B/255.f, color.A/255.f));
		sphere.setMaterial(mat);
		sphere.move(v);
		node.attachChild(sphere);
	}

}
