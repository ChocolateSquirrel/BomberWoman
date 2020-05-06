package engine.renderitems;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import engine.EngineApplication;

public class ImpactedZoneGeometry extends RenderItem {
	public ImpactedZoneGeometry(Vector3f position, int rangeBomb) {
		super();
		
//		TextureSet textureSet = new TextureSet("Textures/increaseRange.png", "Textures/woodNormal.png");
//		Texture colorTexture = EngineApplication.getInstance().getAssetManager().loadTexture(textureSet.colorMap);
//		Texture normalTexture = EngineApplication.getInstance().getAssetManager().loadTexture(textureSet.normalMap);
		
		Box b1 = new Box((rangeBomb*2+1)*0.5f, 0.5f, 0.5f);
		Geometry horizontalCube = new Geometry("Box", b1);
		Material mat1 = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		mat1.setColor("Color", new ColorRGBA(1, 0, 0, 1));
		horizontalCube.setMaterial(mat1);
		horizontalCube.move(position);
		node.attachChild(horizontalCube);
		
		Box b2 = new Box(0.5f, (rangeBomb*2+1)*0.5f, 0.5f);
		Geometry verticalCube = new Geometry("Box", b2);
		Material mat2 = new Material(EngineApplication.getInstance().getAssetManager(),
		          "Common/MatDefs/Misc/Unshaded.j3md");
		mat2.setColor("Color", new ColorRGBA(1, 0, 0, 0));
		verticalCube.setMaterial(mat2);
		verticalCube.move(position);
		node.attachChild(verticalCube);
		
//		mat1.setTexture("ColorMap", colorTexture);
//		mat2.setTexture("ColorMap", colorTexture);
	}
}
