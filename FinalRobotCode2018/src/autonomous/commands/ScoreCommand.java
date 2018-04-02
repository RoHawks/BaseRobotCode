package autonomous.commands;

import constants.GrabberConstants;
import robotcode.systems.Grabber;

public class ScoreCommand extends BaseAutonomousCommand {
	private Grabber mGrabber;

	public ScoreCommand(Grabber pGrabber) {
		mGrabber = pGrabber;
	}

	@Override
	public boolean RunCommand() {
		long scoringSequenceElapsedMilliseconds = this.GetMillisecondsSinceStart();
		
		if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME) {
			mGrabber.out();
			mGrabber.grab();
		}
		else if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME
				+ GrabberConstants.GRAB_PISTON_OUT_TIME) {
			mGrabber.out();
			mGrabber.release();
		}
		else if (scoringSequenceElapsedMilliseconds < GrabberConstants.EXTEND_PISTON_OUT_TIME
				+ GrabberConstants.GRAB_PISTON_OUT_TIME + GrabberConstants.EXTEND_PISTON_IN_TIME) {
			mGrabber.in();
			mGrabber.release();
		}
		else {
			return true;
		}
		
		return false;
	}
}
