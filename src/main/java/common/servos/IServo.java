package common.servos;

import common.pid.interfaces.ITargetAngle;
import common.pid.interfaces.ITargetPosition;
import common.pid.interfaces.ITargetVelocity;

public interface IServo extends ITargetPosition, ITargetVelocity, ITargetAngle {

}