package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Has a constructor that takes fov, aspect, zNear, and zFar parameters to initialize
 * its values. It also has calculateProjectionMatrix() method that calculates the
 * projection matrix based on the input values. The class also has an adjustToViewport()
 * method that adjusts the projection matrix based on the viewport dimensions.
 * Additionally, it has a processInput() method that processes keyboard and mouse
 * inputs to move the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Takes a `CameraStruct` object as input and returns the projection matrix as a
	 * `Matrix4f` object.
	 * 
	 * @param data 3D camera data that is used to calculate the projection matrix.
	 * 
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection and view matrices to fit within the bounds of a
	 * viewport with specified dimensions. It also sets the camera's aspect ratio based
	 * on the viewport width and height.
	 * 
	 * @param width 2D viewport width for which the `adjustToViewport` method is adjusting
	 * the 3D model's projection matrix.
	 * 
	 * @param height 2D viewport size of the canvas in pixels, which is used to calculate
	 * the appropriate projection and view matrices for the 3D scene.
	 */
	@Override
	public void adjustToViewport(int width, int height) {
		((CameraStruct3D) this.values).aspect = (float) width / height;
		calculateProjectionMatrix(values);
		try {
			calculateViewMatrix();
		} catch (NullPointerException e) {
		}
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Is a customized extension of the Camera Struct, adding additional fields and methods
	 * to handle 3D camera functionality. The class provides a matrix4f object for
	 * perspectives calculations and includes methods for adjusting to viewport size,
	 * processing input, and moving the camera position.
	 */
	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

		/**
		 * Initializes a `Matrix4f` object with a perspective projection matrix, setting its
		 * fields according to the provided field values.
		 * 
		 * @returns a matrix representing a perspective projection, with fields for field of
		 * view, aspect ratio, near and far planes.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes input events from the mouse and keyboard, applying rotations and
	 * translations to an object based on user input.
	 * 
	 * @param dt 3D space time interval over which the game world is updated, and it
	 * multiplies the movement speed of the object.
	 * 
	 * @param speed 2D movement speed of the object being controlled, which is multiplied
	 * by the time interval `dt` to determine the distance traveled during each frame.
	 * 
	 * @param sens sensitivity of the character's movement to mouse input, which determines
	 * how much the character will move in response to small changes in mouse position.
	 */
	public void processInput(float dt, float speed, float sens) {

		float dx = Mouse.getDX();
		float dy = Mouse.getDY();
		float roty = (float)Math.toRadians(dx * sens);
		getTransform().rotate(new Vector3f(0, 1, 0), (float) roty);
		getTransform().rotate(getTransform().getRot().getRight(), (float) -Math.toRadians(dy * sens));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			speed *= 10;
		
		float movAmt = speed * dt;

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			move(getTransform().getRot().getForward(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			move(getTransform().getRot().getRight(), movAmt);
		
	}

	/**
	 * Updates the position of an object by adding a directional vector multiplied by a
	 * scalar amount to its current position.
	 * 
	 * @param dir 3D direction to move the object in the game world.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the object.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
