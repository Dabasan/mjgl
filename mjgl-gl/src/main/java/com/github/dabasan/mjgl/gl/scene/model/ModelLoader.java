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
	 *             Thrown when this function has failed to load the model
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
	 *             Thrown when this function has failed to load the model
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
	 *             Thrown when this function has failed to load the model
	 */
	public static List<ModelBuffer> loadBD1(String filepathBD1, boolean invertZ, boolean flipV)
			throws IOException {
		return loadBD1(new File(filepathBD1), invertZ, flipV);
	}
}
