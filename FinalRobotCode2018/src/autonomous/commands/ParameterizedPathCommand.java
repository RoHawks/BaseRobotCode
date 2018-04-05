package autonomous.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import resource.ResourceFunctions;
import resource.Vector;
import robotcode.driving.DriveTrain;
import robotcode.driving.DriveTrain.LinearVelocity;
import robotcode.driving.DriveTrain.RotationalVelocity;
/**
 * 
 * @author 3419
 *	Robot moves in parameterized path of form (acos(bt+c), asin(bt+c))
 */
public class ParameterizedPathCommand extends BaseAutonomousCommand {
	
	private DriveTrain mDriveTrain;
	private double mCosA;
	private double mCosB;
	private double mCosC;
	private double mSinA;
	private double mSinB;
	private double mSinC;
	private double mTotalTime;
	private long mTimeStart;
	private boolean mTimeStartSet = false;

	public ParameterizedPathCommand(DriveTrain pDriveTrain, double pCosA, double pCosB, double pCosC, double pSinA,
			double pSinB, double pSinC, double pTotalTime) {
		mDriveTrain = pDriveTrain;
		mCosA = pCosA;
		mCosB = pCosB;
		mCosC = pCosC;
		mSinA = pSinA;
		mSinB = pSinB;
		mSinC = pSinC;
		mTotalTime = pTotalTime;
	}
	
	@Override
	public boolean RunCommand() {
		if (!mTimeStartSet) {
			mTimeStart = System.currentTimeMillis();
			mTimeStartSet = true;
		}
		double currentTime = (double) System.currentTimeMillis() - mTimeStart;
		double xComp = ResourceFunctions.cosineDerivative(mCosA, mCosB, mCosC, currentTime);
		double yComp = ResourceFunctions.sineDerivative(mSinA, mSinB, mSinC, currentTime);
		Vector linearV = new Vector(xComp, yComp);
		SmartDashboard.putNumber("X comp", xComp);
		SmartDashboard.putNumber("y comp", yComp);
		SmartDashboard.putNumber("Path angle", -linearV.getAngle() + 90);
		SmartDashboard.putNumber("Path magnitude", linearV.getMagnitude()*100);
		mDriveTrain.enactMovement(0, -linearV.getAngle() + 90, LinearVelocity.NORMAL, RotationalVelocity.NONE, linearV.getMagnitude()*100);
		return currentTime > mTotalTime;
	}

}
