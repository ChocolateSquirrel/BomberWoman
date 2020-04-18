package engine.renderitems;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.TangentBinormalGenerator;

import engine.EngineApplication;
import game.map.Color;

public class Ball extends RenderItem {
	
	public Ball(Vector3f v, Color color) {
		super();
		Sphere s = new Sphere(64, 64, .5f);
		Geometry sphere = new Geometry("Sphere", s);
		s.setTextureMode(Sphere.TextureMode.Projected);
		TangentBinormalGenerator.generate(s);
		Material mat = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setTexture("ColorMap", EngineApplication.getInstance().getAssetManager().loadTexture("Textures/concreteColor.png"));
		mat.setColor("Color", new ColorRGBA(color.R/255.f, color.G/255.f, color.B/255.f, color.A/255.f));
		sphere.setMaterial(mat);
		sphere.move(v);
		node.attachChild(sphere);
	}

}
