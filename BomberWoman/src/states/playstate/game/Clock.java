package states.playstate.game;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.EngineApplication;
import engine.renderitems.Text;

public class Clock {
	private double elapsedTime;
	private Text text;
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
	 * Add seconds to elapsedTime and modify text.
	 * @param time time to add in seconds.
	 */
	public void addTime(double time) {
		elapsedTime += time;
		text.changeStringInText(getTimeAsString());
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
		text = new Text(new Vector3f( 5.5f, -1, 0 ), getTimeAsString(), new ColorRGBA(0, 1, 0, 1));
	}
	
	// Transform Time in a String and give the number of Frames
	private String getTimeAsString() {
		double timeInSeconds = getTimeInSeconds();
		int i = (int)timeInSeconds;
		int minuts = i/60;
		int seconds = i%60;
		int hours = minuts/60;
		minuts = minuts%60;
		StringBuilder str = new StringBuilder();
		str.append(" ");
		if (hours<10) {
			str.append("0");
			str.append(hours);
		}
		else {
			str.append(hours);
		}
		str.append(" : ");
		if (minuts<10) {
			str.append("0");
			str.append(minuts);
		}
		else {
			str.append(minuts);
		}
		str.append(" : ");
		if (seconds<10) {
			str.append("0");
			str.append(seconds);
		}
		else {
			str.append(seconds);
		}
		
		return str.toString();
	}	
}
