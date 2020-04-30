package states.playstate.game;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import engine.renderitems.Text;

public class Timer {
	private Text text;
	
	public Timer(double time) {
		text = new Text(new Vector3f( 5.5f, -1, 0 ), changeTimeInString(time), new ColorRGBA(0, 1, 0, 1));
	}
	
	/**
	 * Display a time in a {@link Text} (HH:mm:ss).
	 * @param time time given in seconds.
	 */
	public void displayTime(double time) {
		text.changeStringInText(changeTimeInString(time));
	}

	/**
	 * Transform a number of seconds in a String (HH:mm:ss).
	 * @param timeInSeconds time given in seconds.
	 * @return a String (HH:mm:ss).
	 */
	private String changeTimeInString(double timeInSeconds) {
		StringBuilder str = new StringBuilder();
		
		int i = (int) timeInSeconds;
		int minuts = i/60;
		int seconds = i%60;
		int hours = minuts/60;
		minuts = minuts%60;
		
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
