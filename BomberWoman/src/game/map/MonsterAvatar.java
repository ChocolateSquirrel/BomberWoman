package game.map;

import game.Action;
import game.MoveAction;
import game.PoseBombAction;

public class MonsterAvatar extends Avatar {
	private Action currentAction;
	private double timeOfBegginingCurrentAction; // Save the numbers of seconds flowed out in the game when the current action is created

	public MonsterAvatar(Entity entity, float x, float y) {
		super(entity, x, y);
		int choice = (int) Math.floor(Math.random()*5);
		switch (choice) {
		case 0:
			this.setCurrentAction(new MoveAction("Right"), 0.0);
			break;
		case 1:
			this.setCurrentAction(new MoveAction("Left"), 0.0);
			break;
		case 2:
			this.setCurrentAction(new MoveAction("Up"), 0.0);
			break;
		case 3:
			this.setCurrentAction(new MoveAction("Down"), 0.0);
			break;
		case 4:
			this.setCurrentAction(new PoseBombAction(), 0.0);
			break;
		default : break; //No action
		}
		addActionToDo(currentAction);
	}
	
	public Action getCurrentAction() {
		return currentAction;
	}
	
	public void setCurrentAction(Action action, double timeOfBegginingAction) {
		currentAction = action;
		timeOfBegginingCurrentAction = timeOfBegginingAction;
		addActionToDo(currentAction);
	}
	
	public double getTimeOfBegginingCurrentAction() {
		return timeOfBegginingCurrentAction;
	}
	
}
