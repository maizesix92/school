package SC;

class Robot{
	String name;
	IBehaviour behavior;

	public Robot (String name)
	{
		this.name = name;
	}

	public void behave ()
	{
		System.out.println(String.format("%s can move %d", name, behavior.moveCommand()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setBehavior(String behavior) {
		if (behavior.equals("aggressive")){
			this.behavior = new Aggressive();
		}
		else if (behavior.equals("normal")){
			this.behavior = new Normal();
		}
		else if (behavior.equals("defensive")){
			this.behavior = new Defensive();
		}
	}
}

class Aggressive implements IBehaviour{
	

	@Override
	public int moveCommand() {
		return 10;
	}
	
}

class Normal implements IBehaviour{

	@Override
	public int moveCommand() {
		return 5;
	}
	
}

class Defensive implements IBehaviour{

	@Override
	public int moveCommand() {
		return 1;
	}
	
}

interface IBehaviour {
	public int moveCommand();
}

public class RobotGame {

	public static void main(String[] args) {

		Robot r1 = new Robot("Big Robot");
		Robot r2 = new Robot("George v.2.1");
		Robot r3 = new Robot("R2");

		r1.setBehavior("aggressive");
		r2.setBehavior("normal");
		r3.setBehavior("defensive");
		
		r1.behave();
		r2.behave();
		r3.behave();

		//change the behaviors of each robot.
		r2.setBehavior("aggressive");
		r3.setBehavior("normal");
		r1.setBehavior("defensive");
		
		r1.behave();
		r2.behave();
		r3.behave();
	}
}
