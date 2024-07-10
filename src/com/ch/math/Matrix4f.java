package com.ch.math;

/**
 * Is a representation of a 4x4 matrix, providing methods for initialization,
 * transformation, multiplication with other matrices, and accessing linear data. It
 * also provides methods for setting individual elements and transposing the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Sets the elements of a matrix to identity values, making it the identity matrix.
	 * 
	 * @returns a matrix with identity values for all elements.
	 * 
	 * The `Matrix4f` object returned by the function is an instance of the `Matrix4f`
	 * class, which represents a 4x4 homogeneous transformation matrix.
	 * 
	 * All elements of the matrix are set to their default or identity values, indicating
	 * that the matrix has not been transformed yet.
	 * 
	 * Therefore, the output of the function is a blank slate for further transformations,
	 * allowing users to apply arbitrary transformations to the matrix without any
	 * pre-existing values affecting the result.
	 */
	public Matrix4f initIdentity() {
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * Sets the translation components (x, y, z) for a matrix. It updates the matrix's
	 * data elements accordingly.
	 * 
	 * @param x 3D translation component along the x-axis in the returned matrix.
	 * 
	 * @param y 2nd coordinate of the translation vector, which is added to the corresponding
	 * element in the matrix's data array.
	 * 
	 * @param z 3rd coordinate of the translation vector, which is used to set the
	 * z-component of the matrix's data array.
	 * 
	 * @returns a modified version of the original matrix, with elements representing the
	 * translation vectors for x, y, and z.
	 * 
	 * 1/ The object returned by the function is an instance of the `Matrix4f` class,
	 * indicating that it is a 4x4 homogeneous transformation matrix.
	 * 2/ The elements of the matrix have been modified to represent a translation in the
	 * x, y, and z directions, respectively. Specifically, the element at position (0,
	 * 0) has been set to 1, while the elements at positions (0, 1), (1, 0), (0, 2), and
	 * (1, 1) have been set to the corresponding translation values in the x, y, and z directions.
	 * 3/ The remaining elements of the matrix have been left unchanged, indicating that
	 * the transformation is a pure translation without any other manipulation of the
	 * input matrix.
	 */
	public Matrix4f initTranslation(float x, float y, float z) {
//        x = -x;
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = x;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = y;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = z;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * Initializes a rotation matrix based on three Euler angles (`x`, `y`, and `z`). It
	 * returns a new rotation matrix object.
	 * 
	 * @param x 3D rotation around the x-axis, which is applied to the resulting matrix
	 * through the multiplication operation.
	 * 
	 * @param y 2D rotation angle around the z-axis, which is used to calculate the rotated
	 * matrix.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and its value
	 * determines the angle of the rotation about that axis.
	 * 
	 * @returns a new Matrix4f object representing the rotation matrix based on the given
	 * x, y, and z angles.
	 * 
	 * * `data`: A 16x4 floating-point matrix representing the rotation matrix. The
	 * elements of the matrix represent the rotations around the three axes (x, y, and
	 * z) respectively.
	 * * Each row of the matrix represents a rotation around one of the axes, with the
	 * first column representing the x-axis rotation, the second column representing the
	 * y-axis rotation, and the third column representing the z-axis rotation.
	 * * The elements of each row are arranged in a 4x4 grid, with the top-left element
	 * representing the amount of rotation around that axis, and the bottom-right element
	 * representing the angle of rotation (in radians).
	 */
	public Matrix4f initRotation(float x, float y, float z) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		rz.data[0][0] = (float) Math.cos(z);
		rz.data[0][1] = -(float) Math.sin(z);
		rz.data[0][2] = 0;
		rz.data[0][3] = 0;
		rz.data[1][0] = (float) Math.sin(z);
		rz.data[1][1] = (float) Math.cos(z);
		rz.data[1][2] = 0;
		rz.data[1][3] = 0;
		rz.data[2][0] = 0;
		rz.data[2][1] = 0;
		rz.data[2][2] = 1;
		rz.data[2][3] = 0;
		rz.data[3][0] = 0;
		rz.data[3][1] = 0;
		rz.data[3][2] = 0;
		rz.data[3][3] = 1;

		rx.data[0][0] = 1;
		rx.data[0][1] = 0;
		rx.data[0][2] = 0;
		rx.data[0][3] = 0;
		rx.data[1][0] = 0;
		rx.data[1][1] = (float) Math.cos(x);
		rx.data[1][2] = -(float) Math.sin(x);
		rx.data[1][3] = 0;
		rx.data[2][0] = 0;
		rx.data[2][1] = (float) Math.sin(x);
		rx.data[2][2] = (float) Math.cos(x);
		rx.data[2][3] = 0;
		rx.data[3][0] = 0;
		rx.data[3][1] = 0;
		rx.data[3][2] = 0;
		rx.data[3][3] = 1;

		ry.data[0][0] = (float) Math.cos(y);
		ry.data[0][1] = 0;
		ry.data[0][2] = -(float) Math.sin(y);
		ry.data[0][3] = 0;
		ry.data[1][0] = 0;
		ry.data[1][1] = 1;
		ry.data[1][2] = 0;
		ry.data[1][3] = 0;
		ry.data[2][0] = (float) Math.sin(y);
		ry.data[2][1] = 0;
		ry.data[2][2] = (float) Math.cos(y);
		ry.data[2][3] = 0;
		ry.data[3][0] = 0;
		ry.data[3][1] = 0;
		ry.data[3][2] = 0;
		ry.data[3][3] = 1;

		data = rz.mul(ry.mul(rx)).getData();

		return this;
	}

	/**
	 * Sets the scale factors for a matrix, where the x, y, and z components of the
	 * returned matrix represent the scale factors along the corresponding axes.
	 * 
	 * @param x 3D scaling factor along the x-axis of the matrix.
	 * 
	 * @param y 2D scaling factor for the x-axis of the matrix.
	 * 
	 * @param z 3rd coordinate of the scaling vector, which is applied to the matrix's
	 * second row.
	 * 
	 * @returns a matrix with the given scale factors applied to each component.
	 * 
	 * * The returned object is a matrix with four elements.
	 * * Each element in the matrix represents a scaling factor for one of the coordinate
	 * axes (x, y, or z).
	 * * The values of the elements are set to the input parameters passed to the function.
	 */
	public Matrix4f initScale(float x, float y, float z) {
		data[0][0] = x;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = y;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = z;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * Initializes a Matrix4f object for a perspective projection, with aspect ratio and
	 * field of view specified by the caller. It sets the elements of the matrix accordingly.
	 * 
	 * @param fov Field of View (FOV) of the perspective projection, which determines the
	 * angle of the visible hemisphere in radians.
	 * 
	 * @param aspectRatio 2D aspect ratio of the viewport, which is used to scale the
	 * perspective projection matrix accordingly.
	 * 
	 * @param zNear near plane distance of the perspective projection, which is used to
	 * calculate the z-range for the matrix.
	 * 
	 * @param zFar far distance of the perspective projection, which is used to calculate
	 * the corresponding z-value for the near distance in the returned matrix.
	 * 
	 * @returns a matrix that represents the perspective projection of a 3D scene.
	 * 
	 * * The matrix has four elements, representing the homogeneous coordinates for each
	 * point in the image plane.
	 * * The data array has dimensions 4x4, where each element is a floating-point value
	 * between 0 and 1.
	 * * The matrix is used to transform 3D points from world space to image space, with
	 * the perspective transformation applied.
	 */
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov) / 2);
		float zRange = zNear - zFar;

		data[0][0] = 1.0f / (tanHalfFOV * aspectRatio);
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1.0f / tanHalfFOV;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = (-zNear - zFar) / zRange;
		data[2][3] = 2 * zFar * zNear / zRange;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 1;
		data[3][3] = 0;

		return this;
	}

	/**
	 * Initializes a matrix for orthographic projection with specified dimensions and
	 * near and far distances. It sets the matrix elements to facilitate perspective transformation.
	 * 
	 * @param left left coordinate of the orthographic projection, which is used to
	 * determine the scale factor for the x-axis of the projection matrix.
	 * 
	 * @param right right side of the orthographic projection, which is used to calculate
	 * the coordinates of the vertices in the resulting matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection's
	 * viewport.
	 * 
	 * @param top 2D coordinate of the top of the orthographic projection, which determines
	 * the position of the viewpoint relative to the 3D space.
	 * 
	 * @param near near plane of the orthographic projection, and it is used to calculate
	 * the values of the matrix's elements for the near clipping plane.
	 * 
	 * @param far 3D distance from the near plane to which the orthographic projection
	 * is applied, and it determines the scale of the far objects in the resulting image.
	 * 
	 * @returns a reference to the original Matrix4f object.
	 * 
	 * * The output is a matrix with 4 rows and 4 columns (denoted as `data`).
	 * * Each element in the matrix has a value between 0 and 1.
	 * * The matrix represents an orthographic projection matrix, which means it maps
	 * points in 3D space to 2D screen coordinates.
	 */
	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		data[0][0] = 2 / width;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = -(right + left) / width;
		data[1][0] = 0;
		data[1][1] = 2 / height;
		data[1][2] = 0;
		data[1][3] = -(top + bottom) / height;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = -2 / depth;
		data[2][3] = -(far + near) / depth;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * Initializes a rotation matrix from three vectors: `forward`, `up`, and a third
	 * vector computed as their cross product. The resulting rotation matrix is returned.
	 * 
	 * @param forward 3D direction in which the rotation will be applied.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis, which is used to compute
	 * the cross product with the `forward` parameter to determine the rotation axis.
	 * 
	 * @returns a Matrix4f object representing a rotation matrix based on the provided
	 * forward and up vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a rotation matrix based on three vectors: `forward`, `up`, and `right`.
	 * It sets the elements of the matrix according to the dot product of these vectors
	 * and returns the modified matrix.
	 * 
	 * @param forward 3D direction in which the rotation will occur.
	 * 
	 * * It is a 3D vector representing a direction in space.
	 * 
	 * @param up 3D vector that indicates the up direction and is used to set the y-component
	 * of the rotation matrix.
	 * 
	 * * `up`: A vector representing the upward direction. Its main components are 0, 0,
	 * and 1.
	 * 
	 * @param right 3D rightward vector component of the rotation axis, which is used to
	 * initialize the matrix elements for the rotational transformation.
	 * 
	 * * The `Vector3f` object `right` represents a 3D vector that points in the rightward
	 * direction relative to the coordinate system.
	 * 
	 * @returns a matrix representing the rotation of a 3D object around its local axis.
	 * 
	 * * The `Matrix4f` object returned by the function is the same as the original input
	 * matrix, with some elements modified to represent the rotation.
	 * * The elements in the first three rows and columns of the matrix represent the
	 * rotation axis (right), orientation (up), and forward direction, respectively. These
	 * elements are set to non-zero values.
	 * * The element in the fourth row and third column is set to 1, indicating that the
	 * matrix represents a rotation.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		data[0][0] = r.getX();
		data[0][1] = r.getY();
		data[0][2] = r.getZ();
		data[0][3] = 0;
		data[1][0] = u.getX();
		data[1][1] = u.getY();
		data[1][2] = u.getZ();
		data[1][3] = 0;
		data[2][0] = f.getX();
		data[2][1] = f.getY();
		data[2][2] = f.getZ();
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * Takes a `Vector3f` argument `r` and returns a new `Vector3f` instance with the
	 * result of multiplying each element of the input vector by the corresponding elements
	 * of a given array `data`, followed by adding the result of multiplying the three
	 * array elements by the input vector's `z` component.
	 * 
	 * @param r 4D vector that the function transforms the input `Vector3f` by multiplying
	 * its components with the corresponding components of the `r` vector, and then
	 * returning the resulting 4D vector.
	 * 
	 * @returns a new vector with the result of multiplying each component of the input
	 * vector by a set of coefficients and adding them together.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies a matrix by another matrix, component-wise, and returns the result as
	 * a new matrix.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix, resulting in a new
	 * 4x4 matrix that contains the product of the two matrices.
	 * 
	 * `r` is a `Matrix4f` object representing a 4x4 homogeneous matrix.
	 * 
	 * @returns a matrix that represents the result of multiplying the input matrix by
	 * another matrix.
	 * 
	 * The returned value is a `Matrix4f` object representing the matrix product of the
	 * input matrices. The dimensions of the matrix are 4x4, and the elements are calculated
	 * by multiplying the corresponding elements of the input matrices.
	 */
	public Matrix4f mul(Matrix4f r) {
		Matrix4f res = new Matrix4f();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, data[i][0] * r.get(0, j) + data[i][1] * r.get(1, j) + data[i][2] * r.get(2, j) + data[i][3] * r.get(3, j));
			}
		}

		return res;
	}

	/**
	 * Returns an array of arrays, where each inner array represents a 4x4 matrix and
	 * contains values from the `data` array.
	 * 
	 * @returns an array of arrays, each element of which contains the value of `data[i][j]`.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns an array of floats representing the linear data associated with a given dataset.
	 * 
	 * @returns an array of 12 floats representing the linear data.
	 */
	public float[] getLinearData() {
		return new float[] {
			data[0][0],
			data[1][0],
			data[2][0],
			data[3][0],
			data[0][1],
			data[1][1],
			data[2][1],
			data[3][1],
			data[0][2],
			data[1][2],
			data[2][2],
			data[3][2],
			data[0][3],
			data[1][3],
			data[2][3],
			data[3][3],
		};
	}

	/**
	 * Retrieves a value from an array at position (x, y).
	 * 
	 * @param x 1D position of the pixel in the grid.
	 * 
	 * @param y 2nd dimension of the data array that is being accessed by the function.
	 * 
	 * @returns a floating-point value representing the data at the specified position
	 * in the 2D array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the value of the class member `data` to a provided float array array `data`.
	 * 
	 * @param data 2D array of floating-point values that will be stored and manipulated
	 * by the `SetM` function.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Sets a value at a specific position in a 2D array of integers and floats, by
	 * updating the corresponding element with the given float value.
	 * 
	 * @param x 0-based index of the row in the 2D array where the value should be stored.
	 * 
	 * @param y 2nd dimension of the data array being manipulated by the function, which
	 * is indexed in the code as `data[x][y]`.
	 * 
	 * @param value 3D coordinate's value that is assigned to the corresponding element
	 * in the `data` array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transforms an array of floats into its transposed form, which is then assigned
	 * back to the original array.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
