package states.menustate;

public enum MainMenuProposals {
	PLAY("Play"),
	SETTINGS("Settings"),
	EXIT("Exit"), 
	OTHER(" ");
	
	private String menu;
	
	private MainMenuProposals(String menu) {
		this.menu = menu;
	}
	
	public String getName() {
		return menu;
	}
}
