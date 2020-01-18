package common.servos;

import common.pid.interfaces.ITargetAngle;
import common.pid.interfaces.ITargetPercentOutput;
import common.pid.interfaces.ITargetPosition;

public interface IServo extends ITargetPosition, ITargetPercentOutput, ITargetAngle {

}