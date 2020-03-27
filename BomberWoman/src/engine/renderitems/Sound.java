package engine.renderitems;

import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;

import engine.EngineApplication;

public class Sound extends RenderItem {
	private AudioNode audioNode;
	
	public Sound(String path) {
		audioNode = new AudioNode(EngineApplication.getInstance().getAssetManager(), path, DataType.Buffer);
		audioNode.setLooping(false);
		audioNode.setPositional(false);
		audioNode.setVolume(3);
		node.attachChild(audioNode);
	}
	
	public void play() {
		audioNode.playInstance();
	}

}
