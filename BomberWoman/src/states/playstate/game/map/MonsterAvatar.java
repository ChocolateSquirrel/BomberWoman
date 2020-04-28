package states.playstate.game.map;

import states.playstate.game.Direction;
import states.playstate.game.actions.Action;
import states.playstate.game.actions.MoveAction;
import states.playstate.game.actions.PoseBombAction;

public class MonsterAvatar extends Avatar {
	private Action currentAction;
	private double timeOfBegginingCurrentAction; // Save the numbers of seconds flowed out in the game when the current action is created

	public MonsterAvatar(Entity entity, float x, float y) {
		super(entity, x, y);
		int choice = (int) Math.floor(Math.random()*5);
		switch (choice) {
		case 0:
			setCurrentAction(new MoveAction(Direction.RIGHT), 0.0);
			break;
		case 1:
			setCurrentAction(new MoveAction(Direction.LEFT), 0.0);
			break;
		case 2:
			setCurrentAction(new MoveAction(Direction.UP), 0.0);
			break;
		case 3:
			setCurrentAction(new MoveAction(Direction.DOWN), 0.0);
			break;
		case 4:
			setCurrentAction(new PoseBombAction(), 0.0);
			break;
		default : 
			break; //No action
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
