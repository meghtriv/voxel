package com.ch;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL20;

import com.ch.math.Matrix4f;

/**
 * Is designed to load and manipulate shaders in OpenGL. It provides methods for
 * binding the shader program, uniform floats and matrices, and validating the program.
 * The class also loads shaders from files and compiles them into programs.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Sets the active program object reference to the `program` field, allowing access
	 * to its resources and functions.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves the value of a `program` field within its own class.
	 * 
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Is a method that sets floating-point values for a named location in a GL context,
	 * based on the length of the input array of values.
	 * 
	 * @param name location of the uniform variable in the program, which is used to
	 * determine the appropriate method to call for setting the uniform value.
	 * 
	 * @param vals 0 or more float values that will be passed to the `glUniform*` function
	 * calls, depending on the length of the array.
	 * 
	 * * Length: 1 to 4
	 * * Content: A sequence of floating-point values
	 */
	public void uniformf(String name, float ...vals) {
		switch (vals.length) {
		case 1:
			GL20.glUniform1f(getLoaction(name), vals[0]);
			break;
		case 2:
			GL20.glUniform2f(getLoaction(name), vals[0], vals[1]);
			break;
		case 3:
			GL20.glUniform3f(getLoaction(name), vals[0], vals[1], vals[2]);
			break;
		case 4:
			GL20.glUniform4f(getLoaction(name), vals[0], vals[1], vals[2], vals[3]);
			break;
		}
	}
	
	/**
	 * Updates a uniform matrix with the specified name using the `glUniformMatrix4`
	 * method from OpenGL.
	 * 
	 * @param name 0-based index of the uniform location where the matrix is to be stored.
	 * 
	 * @param mat 4x4 matrix to be uniformed.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform in a program using the `GL20` class and method
	 * `glGetUniformLocation`.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the program's
	 * uniform buffer object.
	 * 
	 * @returns an integer representing the location of a uniform in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader program from a file, comprising of a vertex and fragment shader.
	 * It creates a program object using `glCreateProgram()`, loads the shaders using
	 * `glCreateShader()` and validates the program using `validateProgram()`. Finally,
	 * it returns a new Shader object representing the loaded program.
	 * 
	 * @param filename path to a file containing the vertex and fragment shaders that
	 * will be loaded into the program.
	 * 
	 * @returns a `Shader` object representing the compiled shader program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates a shader program and attaches it to a program handle, loading a shader
	 * source from a string parameter.
	 * 
	 * @param target type of shader being created, with values of 0 for a vertex shader
	 * and non-zero values for a fragment shader or other types of shaders.
	 * 
	 * @param src 1:1 mapping between the source code of the shader and the actual code
	 * that will be compiled by the `glShaderSource()` method.
	 * 
	 * @param program 3D graphics program that will be used to link the generated shader
	 * with.
	 */
	private static void loadShader(int target, String src, int program) {
		int shader = GL20.glCreateShader(target);
		
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(program, shader);
	}
	
	/**
	 * Validates a program by checking its linking and validation status, and prints any
	 * error messages if there are any.
	 * 
	 * @param program 3D graphics program that needs to be validated for linking and
	 * validation errors.
	 */
	private static void validateProgram(int program) {
		GL20.glLinkProgram(program);
		
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(program);
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
	/**
	 * Reads the contents of a given file as a string, handling potential exceptions gracefully.
	 * 
	 * @param file path to a file containing the text to be read.
	 * 
	 * @returns a string containing the contents of the specified file.
	 */
	private static String getText(String file) {
		String text = "";
		try {
			InputStream is = new FileInputStream(file);
			int ch;
			while ((ch = is.read()) != -1)
				text += (char) ch;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return text;
	}

}
