package engine.renderitems;

import java.util.Optional;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import engine.EngineApplication;
import game.map.Color;

public class Cube extends RenderItem {
	// Position is the center of the box
	public Cube(float width, float height, Vector3f position, Color color, Optional<TextureSet> textureSet) {
		super();
		Box b = new Box(width*0.5f, height*0.5f, 0.5f);
		Geometry cube = new Geometry("Box", b);
		Material mat = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		
		textureSet.ifPresent(ts -> { 
			Texture cubeColorText = EngineApplication.getInstance().getAssetManager().loadTexture(ts.colorMap);
			mat.setTexture("ColorMap", cubeColorText); 
//			Texture cubeNormalText = EngineApplication.getInstance().getAssetManager().loadTexture(ts.normalMap);
//			mat.setTexture("NormalMap", cubeNormalText); 
		} );
		
		mat.setColor("Color", new ColorRGBA(color.R/255.f, color.G/255.f, color.B/255.f, color.A/255.f));
		cube.setMaterial(mat);
		cube.move(position);
		node.attachChild(cube);
	}


}
