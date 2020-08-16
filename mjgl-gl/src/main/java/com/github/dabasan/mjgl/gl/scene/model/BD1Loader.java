package com.github.dabasan.mjgl.gl.scene.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.dabasan.jxm.bd1.BD1Buffer;
import com.github.dabasan.jxm.bd1.BD1Manipulator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * BD1 loader
 * 
 * @author Daba
 *
 */
class BD1Loader {
	public static List<ModelBuffer> loadBD1(InputStream isBD1, String bd1Dir, boolean invertZ,
			boolean flipV) throws IOException {
		var manipulator = new BD1Manipulator(isBD1);
		if (invertZ == true) {
			manipulator.invertZ();
		}

		Map<Integer, String> textureFilenames = manipulator.getTextureFilenames();
		List<BD1Buffer> bd1Buffers = manipulator.getBuffers(flipV);

		var buffers = new ArrayList<ModelBuffer>();

		for (var bd1Buffer : bd1Buffers) {
			var buffer = new ModelBuffer();

			// Texture
			int textureID = bd1Buffer.getTextureID();
			if (textureFilenames.containsKey(textureID) == false) {
				buffer.setTexture(null);
			} else {
				String textureFilename = textureFilenames.get(textureID);
				var textureFile = new File(bd1Dir, textureFilename);
				Texture texture = TextureIO.newTexture(textureFile, true);

				buffer.setTexture(texture);
			}

			// Other buffers
			IntBuffer indexBuffer = bd1Buffer.getIndexBuffer();
			FloatBuffer posBuffer = bd1Buffer.getPosBuffer();
			FloatBuffer uvBuffer = bd1Buffer.getUVBuffer();
			FloatBuffer normBuffer = bd1Buffer.getNormBuffer();

			buffer.setIndexBuffer(indexBuffer);
			buffer.setPosBuffer(posBuffer);
			buffer.setUVBuffer(uvBuffer);
			buffer.setNormBuffer(normBuffer);

			buffers.add(buffer);
		}

		return buffers;
	}
}
