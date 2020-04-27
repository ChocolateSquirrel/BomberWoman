package states;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioData.DataType;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

import engine.EngineApplication;
import engine.EngineListener;
import engine.renderitems.Message;
import game.BomberWomanMain;
import game.GameRound;
import game.GameRules;
import game.roundState.GameRoundState;
import game.roundState.State;

public class PlayState implements AppState {
	private GameRules gameRules; 
	private GameRound round;
	private Message message;
	private AudioNode audioWin;
	private AudioNode audioLoose;
	private AudioNode audioHurryUp;
	private boolean isInitialized;
	private boolean isEnabled = true;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		EngineApplication engineApp = (EngineApplication) app;
		round = new GameRound();
		gameRules = new GameRules(round.getMap(), round.getPlayerAvatar());
		message = new Message(round.getMap().getWidth(),
				round.getMap().getHeight() /2,
				new Vector3f((round.getMap().getWidth())/2, (round.getMap().getHeight())/2, BomberWomanMain.Z_MESSAGE),
				new ColorRGBA(0.1f, 0.2f, 0.3f, 0.5f),
				" ");
		engineApp.getRootNode().detachChild(message.getNode());
		initKeys();
		initAudio();
		
		// Camera is fixed
		engineApp.getFlyByCamera().setEnabled(false);
		
		// Place the Camera
		float fov = 45f;
		float h = engineApp.getCamera().getHeight();
		float W = 2*round.getMap().getWidth();
		float H = 2*round.getMap().getHeight();
		float dH = (float) (H / (2*Math.atan(fov/2)));
		float dW = (float) (W / (2*Math.atan(fov/2)));
		float d = (float) (1.75*Math.max(dH, dW));
		System.out.println("d="+d);
		engineApp.getCamera().setFrame(
			new Vector3f(round.getMap().getWidth()/2.f, round.getMap().getHeight()/2.f, d), // location
			new Vector3f(-1, 0, 0), // left
			new Vector3f(0, 1, 0), // up
			new Vector3f(0, 0, -1) ); // direction
		
		// This switches Camera to ortho mode
//		engineApp.getCamera().setParallelProjection(true);
//		renderManager.setEngineApplication.getInstance().getCamera()era(EngineApplication.getInstance().getCamera(), true);
//        float aspect = (float) EngineApplication.getInstance().getCamera().getWidth() / EngineApplication.getInstance().getCamera().getHeight();
//        float size   = 10f;
//        EngineApplication.getInstance().getCamera().setFrustum(-1000, 1000, -aspect * size, aspect * size, size, -size);
        
        // Display number of lives of avatar player.
        round.getPlayerAvatar().getText().changeStringInText("Nb of lives: " + round.getPlayerAvatar().getLivesAvatar());
        
        isInitialized = true;
	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void setEnabled(boolean active) {
		isEnabled = active;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void stateAttached(AppStateManager stateManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float tpf) {
		GameRoundState previousState = round.getGameRoundState();
		GameRoundState newState = gameRules.changeRoundState(round);
		
		switch (newState.getState()) {
			case NOT_FINISHED:
			case NOT_FINISHED_HURRY_UP:
				gameRules.manageTimeUpdate(round, tpf);
				if (newState.getState() == State.NOT_FINISHED_HURRY_UP && previousState.getState() != State.NOT_FINISHED_HURRY_UP)
					audioHurryUp.play();
				break;
				
			case WIN_NO_MORE_ENNEMY:
				message.changeMessage(newState.getState().getName());
				EngineApplication.getInstance().getRootNode().attachChild(message.getNode());
				audioHurryUp.stop();
				if (previousState.getState() != State.WIN_NO_MORE_ENNEMY)
					audioWin.play();
				break;
				
			case LOOSE_NO_MORE_LIFE:
			case LOOSE_TIME_OFF:
				message.changeMessage(newState.getState().getName());
				EngineApplication.getInstance().getRootNode().attachChild(message.getNode());
				audioHurryUp.stop();
				if (previousState.getState() != State.LOOSE_NO_MORE_LIFE && previousState.getState() != State.LOOSE_TIME_OFF)
					audioLoose.play();
				break;
				
			default: 
				throw new UnsupportedOperationException("Unknown gameRoundState");
		}
		
		round.setGameRoundState(newState);
		
	}

	@Override
	public void render(RenderManager rm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		EngineApplication.getInstance().getRootNode().detachAllChildren();
		
	}
	
	private void initKeys() {
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_PAUSE, new KeyTrigger(KeyInput.KEY_P));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_RIGHT, new KeyTrigger(KeyInput.KEY_RIGHT));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_LEFT, new KeyTrigger(KeyInput.KEY_LEFT));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_UP, new KeyTrigger(KeyInput.KEY_UP));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_DOWN, new KeyTrigger(KeyInput.KEY_DOWN));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_BOMB, new KeyTrigger(KeyInput.KEY_SPACE));
		EngineApplication.getInstance().getInputManager().addMapping(BomberWomanMain.CONTROL_RESTART, new KeyTrigger(KeyInput.KEY_SPACE));
		
		EngineListener listener = new EngineListener(gameRules, round);		
		EngineApplication.getInstance().getInputManager().addListener((AnalogListener)listener, BomberWomanMain.CONTROL_RIGHT,
				BomberWomanMain.CONTROL_LEFT,
				BomberWomanMain.CONTROL_UP,
				BomberWomanMain.CONTROL_DOWN);
		EngineApplication.getInstance().getInputManager().addListener((ActionListener)listener, BomberWomanMain.CONTROL_BOMB,
				BomberWomanMain.CONTROL_PAUSE,
				BomberWomanMain.CONTROL_RESTART);
	}
	
	private void initAudio() {
		AudioNode audio_nature = new AudioNode(EngineApplication.getInstance().getAssetManager(), "Sounds/ambient/foret.ogg", DataType.Stream);
		audio_nature.setLooping(true);
		audio_nature.setPositional(true);
		audio_nature.setVolume(3);
		EngineApplication.getInstance().getRootNode().attachChild(audio_nature);
		audio_nature.play();
		
		audioWin = new AudioNode(EngineApplication.getInstance().getAssetManager(), "Sounds/ambient/applaudissements.ogg", DataType.Stream);
		audioWin.setLooping(false);
		audioWin.setPositional(true);
		audioWin.setVolume(3);
		EngineApplication.getInstance().getRootNode().attachChild(audioWin);
		
		audioLoose = new AudioNode(EngineApplication.getInstance().getAssetManager(), "Sounds/ambient/motus-boule-noire-mono.ogg", DataType.Stream);
		audioLoose.setLooping(false);
		audioLoose.setPositional(true);
		audioLoose.setVolume(3);
		EngineApplication.getInstance().getRootNode().attachChild(audioWin);
		
		audioHurryUp = new AudioNode(EngineApplication.getInstance().getAssetManager(), "Sounds/ambient/tictac.ogg", DataType.Stream);
		audioHurryUp.setLooping(true);
		audioHurryUp.setPositional(true);
		audioHurryUp.setVolume(3);
		EngineApplication.getInstance().getRootNode().attachChild(audioHurryUp);
	}

}