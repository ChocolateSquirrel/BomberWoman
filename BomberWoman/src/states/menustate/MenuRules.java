package states.menustate;

import com.jme3.math.ColorRGBA;

import main.BomberWomanMain;

public class MenuRules {
	private MenuState menuState;
	private PseudoButton previousButton;
	private PseudoButton newButton;
	private int nbButtons;
	
	public MenuRules(MenuState menuState) {
		this.menuState = menuState;
		previousButton = menuState.getButtons().get(0);
		nbButtons = menuState.getButtons().size();
	}
	
	public void manageDiscreteInputs(String name, boolean isPressed, float tpf) {
		int indexPreviousButton = menuState.getButtons().indexOf(previousButton);
		System.out.println(indexPreviousButton);
		if (!isPressed)
			return;
		else {
			switch (name) {
			case BomberWomanMain.CONTROL_DOWN:
				if (indexPreviousButton == nbButtons -1)
					newButton = menuState.getButtons().get(0);
				else
					newButton = menuState.getButtons().get(indexPreviousButton + 1);
				previousButton.changeColor(ColorRGBA.Blue);
				newButton.changeColor(ColorRGBA.Orange);
				menuState.changeMenuSelection(newButton.getMenuOfButton());
				previousButton = newButton;
				System.out.println(menuState.getMenuSelected());
				break;
				
			case BomberWomanMain.CONTROL_UP:
				if (indexPreviousButton == 0)
					newButton = menuState.getButtons().get(nbButtons - 1);
				else
					newButton = menuState.getButtons().get(indexPreviousButton - 1);
				previousButton.changeColor(ColorRGBA.Blue);
				newButton.changeColor(ColorRGBA.Orange);
				menuState.changeMenuSelection(newButton.getMenuOfButton());
				previousButton = newButton;
				System.out.println(menuState.getMenuSelected());
				break;
				
			case BomberWomanMain.CONTROL_OK:
				if (menuState.getMenuSelected() == MainMenuProposals.PLAY) {
					menuState.setMenuStatus(MainMenuProposals.PLAY);
				}
				if (menuState.getMenuSelected() == MainMenuProposals.EXIT) {
					System.exit(0);
				}
				break;
	
			default:
				break;
			
			}		
		}
	}
}
