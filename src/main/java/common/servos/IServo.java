package common.servos;

import common.pid.interfaces.ITargetOffsetAngle;
import common.pid.interfaces.ITargetOffesetPosition;
import common.pid.interfaces.ITargetVelocity;

public interface IServo extends ITargetOffesetPosition, ITargetVelocity, ITargetOffsetAngle {

}