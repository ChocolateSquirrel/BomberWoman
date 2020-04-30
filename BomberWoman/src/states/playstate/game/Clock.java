package states.playstate.game;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.EngineApplication;
import engine.renderitems.Text;

public class Clock {
	private double elapsedTime;
	private long nbrFrames;
	private static Clock instance;
	
	public static Clock getInstance() {
		return instance==null ? instance= new Clock() : instance;
	}
	
	/**
	 * Return time elapsed since instance creation.
	 * @return elapsed time in seconds.
	 */
	public double getTimeInSeconds() {
		return elapsedTime;
	}
	
	/**
	 * Add seconds to elapsedTime.
	 * @param time time to add in seconds.
	 */
	public void addTime(double time) {
		elapsedTime += time;
	}
	
	public void addFrame() {
		nbrFrames += 1;
	}
	
	public long getNbrFrames() {
		return nbrFrames;
	}
	
	public void reset() {
		initialize();
	}

	private Clock() {
		initialize();
	}
	
	private void initialize() {
		elapsedTime = 0;
		nbrFrames = 0;
	}
	
}
