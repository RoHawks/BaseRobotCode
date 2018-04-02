package autonomous.sequence;

import autonomous.AutonomousSequence;

public class DoNothingSequence implements AutonomousSequence {

	public DoNothingSequence() {
		
	}
	
	@Override
	public boolean run() {
		return true;
	}

}
