package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Is used for managing vertex attributes and indices in a 3D rendering context. It
 * provides methods for binding and unbinding vertex arrays, enabling and disabling
 * attribute arrays, and loading data from an array into the model. The class also
 * includes utility methods for creating and manipulating buffer objects and vertex
 * array objects.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a Vertex Array Object (VAO), enables vertex attributes, and renders a
	 * collection of triangles using `GLDrawElements`.
	 */
	public void draw() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Enables two vertex attribute arrays using OpenGL API.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables two vertex attributes of a graphical object using the `glDisableVertexAttribArray`
	 * method.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Returs the value of the `vao` field.
	 * 
	 * @returns an integer value representing the `vao` field.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns the current size of an object's internal storage.
	 * 
	 * @returns the current size of the data structure, represented as an integer.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Loads a 3D model from an array of vertices and an array of indices, creates a
	 * Vulkan object and stores the data, then returns a new `Model` instance with the
	 * loaded data.
	 * 
	 * @param vertices 3D model's vertices data, which is stored in an array and passed
	 * to the `storeData()` method for storage in the VAO.
	 * 
	 * @param indices 3D model's vertex indices, which are used to bind the vertices to
	 * the appropriate geometry.
	 * 
	 * @returns a `Model` object representing the loaded 3D model.
	 */
	public static Model load(float[] vertices, int[] indices) {
		int vao = createVAO();
		storeIndices(indices);
		storeData(0, vertices);
		unbindVAO();
		int v_count = indices.length;
		return new Model(vao, v_count);
	}
	
	/**
	 * Generates a new vertex array object (VAO) using the `glGenVertexArrays` method,
	 * binds it with `glBindVertexArray`, and returns the VAO handle.
	 * 
	 * @returns an integer value representing a unique vertex array object (Vao) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * 1) creates a new VBO, 2) binds it to store data, and 3) sets vertex attributes for
	 * 3D graphics rendering.
	 * 
	 * @param attrib attribute index of the data to be stored in the vertex buffer object
	 * (VBO).
	 * 
	 * @param data 3D data to be stored in the vertex buffer object (VBO) for further rendering.
	 */
	private static void storeData(int attrib, float[] data) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.createFlippedBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 5 * 4,     0);
		GL20.glVertexAttribPointer(attrib + 1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Generates a new buffer object and binds it to an element array buffer slot, then
	 * stores the provided indices in the buffer using the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D indices of vertices in a buffer that will be stored in the element
	 * array buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Disconnects a Vertex Array Object (VAO) from the GPU by passing the ID of the VAO
	 * to `glBindVertexArray`.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
