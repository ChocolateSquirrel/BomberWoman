package engine.hud;

import engine.EngineApplication;

public class EndMessage extends Message{
	
	public EndMessage(EngineApplication engineApp) {
		super(engineApp);
	}

	public void changeMessage(String newText) {
		message.setText(newText);
		message.setLocalTranslation(
				(getEngineApp().getContext().getSettings().getWidth() -  message.getLineWidth()) /2,
				(getEngineApp().getContext().getSettings().getHeight() -  message.getLineHeight()) /2,
				0);
	}

}
