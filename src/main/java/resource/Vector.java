package resource;

import java.awt.geom.Point2D;

/**
 * Represents a mathematical vector in rectangular form. Contains methods to
 * represent polar form, and other mathematical manipulations of a vector.
 * 
 * @author Alex Cohen
 */
public class Vector {

	private double mXComp;
	private double mYComp;

	/**
	 * Create vector with x and y components.
	 * 
	 * @param pXComp x-component of vector.
	 * @param pYComp y-component of vector.
	 */
	public Vector(double pXComp, double pYComp) {
		this.mXComp = pXComp;
		this.mYComp = pYComp;
	}

	/**
	 * Create vector equal to parameter vector.
	 * 
	 * @param pOther vector to copy.
	 */
	public Vector(Vector pOther) {
		this(pOther.getX(), pOther.getY());
	}

	/**
	 * Creates null vector.
	 */
	public Vector() {
		this(0, 0);
	}

	/**
	 * Creates polar vector
	 * 
	 * @param pAngle angle of vector
	 * @param pTotal magnitude of vector
	 * @return Vector with the given characteristics.
	 */
	public static Vector createPolar(double pAngle, double pTotal) {
		Vector v = new Vector();
		v.setPolar(pAngle, pTotal);
		return v;
	}

	/**
	 * Adds two vectors
	 * 
	 * @param pV1 vector
	 * @param pV2 vector
	 * @return sum vector
	 */
	public static Vector add(Vector pV1, Vector pV2) {
		Vector v = new Vector();
		v.addCartesian(pV1);
		v.addCartesian(pV2);
		return v;
	}

	/**
	 * x component getter
	 * 
	 * @return x value of vector
	 */
	public double getX() {
		return this.mXComp;
	}

	/**
	 * y component getter
	 * 
	 * @return y component of vector
	 */
	public double getY() {
		return this.mYComp;
	}

	/**
	 * magnitude getter
	 * 
	 * @return magnitude of vector
	 */
	public double getMagnitude() {
		return Math.hypot(mXComp, mYComp);
	}

	/**
	 * angle getter
	 * 
	 * @return angle of the vector
	 */
	public double getAngle() {
		return ResourceFunctions.putAngleInRange(Math.toDegrees(Math.atan2(mYComp, mXComp)));
	}

	/**
	 * vector as the point from the origin
	 * 
	 * @return point (x component, y component)
	 */
	public Point2D.Double asPoint() {
		return new Point2D.Double(this.getX(), this.getY());
	}

	/**
	 * x component setter
	 * 
	 * @param pXComp value to set x
	 */
	private void setX(double pXComp) {
		this.mXComp = pXComp;
	}

	/**
	 * y component setter
	 * 
	 * @param pYComp value to set y
	 */
	private void setY(double pYComp) {
		this.mYComp = pYComp;
	}

	/**
	 * set x and y components
	 * 
	 * @param pXComp value to set x
	 * @param pYComp value to set y
	 */
	public void setCartesian(double pXComp, double pYComp) {
		this.setX(pXComp);
		this.setY(pYComp);
	}

	/**
	 * add vector to this one
	 * 
	 * @param pXComp x component of vector to add
	 * @param pYComp y component of vector to add
	 */
	public void addCartesian(double pXComp, double pYComp) {
		this.setCartesian(this.mXComp + pXComp, this.mYComp + pYComp);
	}

	/**
	 * add vector to this one
	 * 
	 * @param pV vector to add
	 */
	public void addCartesian(Vector pV) {
		this.addCartesian(pV.getX(), pV.getY());
	}

	/**
	 * set polar coordinates
	 * 
	 * @param pAngle angle of vector
	 * @param pTotal magnitude of vector
	 */
	public void setPolar(double pAngle, double pTotal) {
		this.setCartesian(Math.cos(Math.toRadians(pAngle)) * pTotal, Math.sin(Math.toRadians(pAngle)) * pTotal);
	}

	/**
	 * set angle
	 * 
	 * @param pAngle angle to set vector
	 */
	public void setAngle(double pAngle) {
		this.setPolar(pAngle, this.getMagnitude());
	}

	/**
	 * set magnitude
	 * 
	 * @param pTotal magnitude of vector
	 */
	public void setTotal(double pTotal) {
		this.setPolar(this.getAngle(), pTotal);
	}

	/**
	 * add polar vector to this one
	 * 
	 * @param pAngle angle of polar vector to add
	 * @param pTotal magnitude of polar vector to add
	 */
	public void addPolar(double pAngle, double pTotal) {
		double x = this.getX() + pTotal * Math.cos(Math.toRadians(pAngle));
		double y = this.getY() + pTotal * Math.sin(Math.toRadians(pAngle));
		this.setCartesian(x, y);
	}

	/**
	 * multiply vector by scalar
	 * 
	 * @param pScaleAmount scalar to multiply vector
	 */
	public void scaleTotal(double pScaleAmount) {
		this.mXComp *= pScaleAmount;
		this.mYComp *= pScaleAmount;
	}

	/**
	 * dot product of two vectors
	 * 
	 * @param pV vector to dot this one
	 * @return dot product
	 */
	public double dot(Vector pV) {
		return dot(this, pV);
	}

	/**
	 * dot product of two vectors
	 * 
	 * @param pV1 first vector
	 * @param pV2 second vector
	 * @return dot product
	 */
	public static double dot(Vector pV1, Vector pV2) {
		return pV1.getX() * pV2.getX() + pV1.getY() * pV2.getY();
	}

	/**
	 * projection length of some Vector onto this
	 * 
	 * @param pV Vector to project onto this
	 * @return projection length of Vector v onto this
	 */
	public double projectionLengthFrom(Vector pV) {
		return projectionLength(pV, this);
	}

	/**
	 * projection length of this onto some other Vector
	 * 
	 * @param pV Vector to project this onto
	 * @return projection length of this onto Vector v
	 */
	public double projectionLengthOnto(Vector pV) {
		return projectionLength(this, pV);
	}

	/**
	 * projection length of Vector a onto Vector b
	 * 
	 * @param pV1 Vector to project
	 * @param pV2 Vector projected onto
	 * @return length of the projection
	 */
	public static double projectionLength(Vector pV1, Vector pV2) {
		return dot(pV1, pV2) / pV2.getMagnitude();
	}

	/**
	 * angle between two vectors
	 * 
	 * @param pV vector to find angle between
	 * @return angle
	 */
	public double angleBetween(Vector pV) {
		return angleBetween(this, pV);
	}

	/**
	 * angle between two vectors
	 * 
	 * @param pV1 first vector
	 * @param pV2 second vector
	 * @return angle between them
	 */
	public static double angleBetween(Vector pV1, Vector pV2) {
		double cosTheta = dot(pV1, pV2) / (pV1.getMagnitude() * pV2.getMagnitude());
		double angle = Math.toDegrees(Math.acos(cosTheta));
		angle = ResourceFunctions.putAngleInRange(angle);

		return Math.min(angle, 360 - angle);
	}

	/**
	 * Angle: --, Total: --, X: --, Y: --
	 */
	public String toString() {
		return String.format("Angle: %f, Total: %f, X: %f, Y: %f", this.getAngle(), this.getMagnitude(), this.getX(),
				this.getY());
	}

	/**
	 * Creates normalized version of the passed vector (gives it a magnitude of one)
	 * 
	 * @param pV vector
	 * @return vector with magnitude of one
	 */
	public static Vector normalized(Vector pV) {
		Vector newVec = new Vector(pV);
		newVec.setTotal(1.0);
		return newVec;
	}

}
