package states.menustate;

import engine.renderitems.Sound;
import main.BomberWomanMain;

public class MenuRules {
	private MenuState menuState;
	private PseudoButton previousButton;
	private int nbButtons;
	private Sound okSound;
	private Sound changeSound;
	
	public MenuRules(MenuState menuState) {
		this.menuState = menuState;
		previousButton = menuState.getButtons().get(0);
		nbButtons = menuState.getButtons().size();
		okSound = new Sound("Sounds/ambient/clochette.ogg");
		changeSound = new Sound("Sounds/ambient/page.ogg");
		
	}
	
	public void manageDiscreteInputs(String name, boolean isPressed, float tpf) {
		int indexPreviousButton = menuState.getButtons().indexOf(previousButton);
		System.out.println(indexPreviousButton);
		if (!isPressed)
			return;
		else {
			
			switch (name) {
			case BomberWomanMain.CONTROL_DOWN:
			{
				PseudoButton newButton = indexPreviousButton == nbButtons -1 ? menuState.getButtons().get(0) : menuState.getButtons().get(indexPreviousButton + 1);
				previousButton.delightButton();
				newButton.highlightButton();
				menuState.setMenuHighlighted(newButton.getMenuOfButton());
				previousButton = newButton;
				changeSound.play();
				break;
			}
				
			case BomberWomanMain.CONTROL_UP:
				PseudoButton newButton = indexPreviousButton == 0 ? menuState.getButtons().get(nbButtons - 1) : menuState.getButtons().get(indexPreviousButton - 1);
				previousButton.delightButton();
				newButton.highlightButton();
				menuState.setMenuHighlighted(newButton.getMenuOfButton());
				previousButton = newButton;
				changeSound.play();
				break;
				
			case BomberWomanMain.CONTROL_OK:
				if (menuState.getMenuHighlighted() == MainMenuProposals.PLAY) {
					menuState.setMenuSelected(MainMenuProposals.PLAY);
					okSound.play();
				}
				if (menuState.getMenuHighlighted() == MainMenuProposals.EXIT) {
					System.exit(0);
				}
				break;
	
			default:
				break;
			
			}		
		}
	}
}
