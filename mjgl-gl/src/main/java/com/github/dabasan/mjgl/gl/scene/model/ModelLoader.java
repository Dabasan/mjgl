package com.github.dabasan.mjgl.gl.scene.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Model loader
 * 
 * @author Daba
 *
 */
public class ModelLoader {
	/**
	 * Loads a BD1 file.
	 * 
	 * @param isBD1
	 *            InputStream
	 * @param bd1Dir
	 *            Directory of the BD1 file
	 * @param invertZ
	 *            Inverts the level with respect to the Z-axis if true
	 * @param flipV
	 *            Flips texture V-coordinate if true
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadBD1(InputStream isBD1, String bd1Dir, boolean invertZ,
			boolean flipV) throws IOException {
		return BD1Loader.loadBD1(isBD1, bd1Dir, invertZ, flipV);
	}
	/**
	 * Loads a BD1 file.
	 * 
	 * @param fileBD1
	 *            File
	 * @param invertZ
	 *            Inverts the level with respect to the Z-axis if true
	 * @param flipV
	 *            Flips texture V-coordinate if true
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadBD1(File fileBD1, boolean invertZ, boolean flipV)
			throws IOException {
		String bd1Dir = fileBD1.getParent();
		List<ModelBuffer> buffers;
		try (var fis = new FileInputStream(fileBD1)) {
			buffers = BD1Loader.loadBD1(fis, bd1Dir, invertZ, flipV);
		}

		return buffers;
	}
	/**
	 * Loads a BD1 file.
	 * 
	 * @param filepathBD1
	 *            Filepath
	 * @param invertZ
	 *            Inverts the level with respect to the Z-axis if true
	 * @param flipV
	 *            Flips texture V-coordinate if true
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadBD1(String filepathBD1, boolean invertZ, boolean flipV)
			throws IOException {
		return loadBD1(new File(filepathBD1), invertZ, flipV);
	}

	/**
	 * Loads an OBJ file.
	 * 
	 * @param isObj
	 *            InputStream
	 * @param objDir
	 *            Directory of the OBJ file
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadOBJ(InputStream isObj, String objDir) throws IOException {
		return OBJLoader.loadOBJ(isObj, objDir);
	}
	/**
	 * Loads an OBJ file.
	 * 
	 * @param fileObj
	 *            File
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadOBJ(File fileObj) throws IOException {
		String objDir = fileObj.getParent();
		List<ModelBuffer> buffers;
		try (var fis = new FileInputStream(fileObj)) {
			buffers = OBJLoader.loadOBJ(fis, objDir);
		}

		return buffers;
	}
	/**
	 * Loads an OBJ file.
	 * 
	 * @param filepathObj
	 *            Filepath
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadOBJ(String filepathObj) throws IOException {
		return loadOBJ(new File(filepathObj));
	}

	/**
	 * Loads a model.<br>
	 * This method requires native libraries for JAssimp.
	 * 
	 * @param filepath
	 *            Filepath
	 * @return List containing model buffers
	 * @throws IOException
	 *             Thrown when this method has failed to load the model
	 */
	public static List<ModelBuffer> loadModel(String filepath) throws IOException {
		return AssimpLoader.loadModel(filepath);
	}
}
