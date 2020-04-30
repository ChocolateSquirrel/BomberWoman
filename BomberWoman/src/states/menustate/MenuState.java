package states.menustate;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.audio.AudioData.DataType;
import com.jme3.renderer.RenderManager;
import com.jme3.ui.Picture;

import engine.EngineApplication;
import engine.EngineListener;
import main.BomberWomanMain;
import states.StateBase;
import states.playstate.PlayState;

public class MenuState extends StateBase {
	private boolean isInitialized;
	private boolean isEnabled = true;
	private EngineApplication engineApp;
	private AudioNode audioMenu;
	private MainMenuProposals menuSelected = MainMenuProposals.PLAY;
	private MainMenuProposals menuStatus = MainMenuProposals.OTHER;
	private MenuRules menuRules;
	private List<PseudoButton> buttons = new ArrayList<>();
	
	private Picture greyBackground;

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		engineApp = (EngineApplication) app;
		engineApp.getFlyByCamera().setEnabled(false);
		initAudio();
		initKey();

		greyBackground = new Picture("fond gris");
		greyBackground.setImage(engineApp.getAssetManager(), "Pictures/fondGris.png", true);
		greyBackground.setWidth(engineApp.getContext().getSettings().getWidth()/2);
		greyBackground.setHeight(engineApp.getContext().getSettings().getHeight());
		greyBackground.setPosition(engineApp.getContext().getSettings().getWidth()/4, 0);
		engineApp.getGuiNode().attachChild(greyBackground);
		
		buttons.add(new PseudoButton(engineApp, MainMenuProposals.PLAY, 2));
		buttons.add(new PseudoButton(engineApp, MainMenuProposals.SETTINGS, 1));
		buttons.add(new PseudoButton(engineApp, MainMenuProposals.EXIT, 0));
		buttons.get(0).changeColor(ColorRGBA.Orange);
		for (PseudoButton button : buttons)
			engineApp.getGuiNode().attachChild(button.getNode());
	
		menuRules = new MenuRules(this);
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
		if (menuStatus == MainMenuProposals.PLAY) {
			audioMenu.stop();
			engineApp.getGuiNode().detachAllChildren();
			engineApp.changeState(new PlayState());
		}
		System.out.println("selectionne : " + menuSelected);
		System.out.println("status : " + menuStatus);
		
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
		// TODO Auto-generated method stub
		
	}
	
	public void changeMenuSelection(MainMenuProposals newMenu) {
		menuSelected = newMenu;
	}
	
	public MainMenuProposals getMenuSelected() {
		return menuSelected;
	}
	
	public void setMenuStatus(MainMenuProposals newMenu) {
		menuStatus = newMenu;
	}
	
	public List<PseudoButton> getButtons() {
		return buttons;
	}

	@Override
	protected void doManageContinousInputs(String name, float value, float tpf) {
		// TODO Auto-generated method stub	
	}

	@Override
	protected void doManageDiscreteInput(String name, boolean isPressed, float tpf) {
		menuRules.manageDiscreteInputs(name, isPressed, tpf);
		
	}
	
	private void initAudio() {
		audioMenu = new AudioNode(engineApp.getAssetManager(),"Sounds/ambient/foret.ogg", DataType.Stream);
		audioMenu.setLooping(true);
		audioMenu.setPositional(true);
		audioMenu.setVolume(3);
		engineApp.getRootNode().attachChild(audioMenu);
		audioMenu.play();
	}
	
	private void initKey() {
		engineApp.getInputManager().addMapping(BomberWomanMain.CONTROL_OK, new KeyTrigger(KeyInput.KEY_RETURN));
		engineApp.getInputManager().addMapping(BomberWomanMain.CONTROL_UP, new KeyTrigger(KeyInput.KEY_UP));
		engineApp.getInputManager().addMapping(BomberWomanMain.CONTROL_DOWN, new KeyTrigger(KeyInput.KEY_DOWN));
		
		EngineListener listener = new EngineListener();
		engineApp.getInputManager().addListener(listener, BomberWomanMain.CONTROL_OK,
				BomberWomanMain.CONTROL_UP,
				BomberWomanMain.CONTROL_DOWN);
	}

}
