package com.github.dabasan.mjgl.gl.scene.model;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.github.dabasan.jassimp.AiColor;
import com.github.dabasan.jassimp.AiMaterial;
import com.github.dabasan.jassimp.AiMesh;
import com.github.dabasan.jassimp.AiPostProcessSteps;
import com.github.dabasan.jassimp.AiScene;
import com.github.dabasan.jassimp.AiTextureType;
import com.github.dabasan.jassimp.Jassimp;
import com.github.dabasan.mjgl.gl.Color;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Model loader backed by JAssimp<br>
 * Native libraries are required to use this class.
 * 
 * @author Daba
 *
 */
class AssimpLoader {
	public static List<ModelBuffer> loadModel(String modelFilepath, Set<AiPostProcessSteps> flags)
			throws IOException {
		AiScene scene = Jassimp.importFile(modelFilepath, flags);
		List<AiMaterial> materials = scene.getMaterials();
		List<AiMesh> meshes = scene.getMeshes();

		if (materials.size() == 0) {
			throw new IOException("There are no materials in this model.");
		}
		if (meshes.size() == 0) {
			throw new IOException("There are no meshes in this model.");
		}
		if (materials.size() != meshes.size()) {
			throw new IOException("material_num != mesh_num");
		}

		String modelDir = new File(modelFilepath).getParent();

		var buffers = new ArrayList<ModelBuffer>();

		for (var material : materials) {
			var buffer = new ModelBuffer();

			String textureFilename = material.getTextureFile(AiTextureType.DIFFUSE, 0);
			if (textureFilename != null && textureFilename.equals("") == false) {
				var textureFile = new File(modelDir, textureFilename);
				Texture texture = TextureIO.newTexture(textureFile, true);
				buffer.setTexture(texture);
			} else {
				buffer.setTexture(null);
			}

			AiColor colorAmbient = material.getAmbientColor(null);
			AiColor colorDiffuse = material.getDiffuseColor(null);
			AiColor colorSpecular = material.getSpecularColor(null);
			float shininess = material.getShininess();
			float opacity = material.getOpacity();

			buffer.setColorAmbient(new Color(colorAmbient.getRed(), colorAmbient.getGreen(),
					colorAmbient.getBlue(), colorAmbient.getAlpha()));
			buffer.setColorDiffuse(new Color(colorDiffuse.getRed(), colorDiffuse.getGreen(),
					colorDiffuse.getBlue(), colorDiffuse.getAlpha()));
			buffer.setColorSpecular(new Color(colorSpecular.getRed(), colorSpecular.getGreen(),
					colorSpecular.getBlue(), colorSpecular.getAlpha()));
			buffer.setSpecularExponent(shininess);
			buffer.setDissolve(opacity);

			buffers.add(buffer);
		}

		for (int i = 0; i < meshes.size(); i++) {
			AiMesh mesh = meshes.get(i);
			ModelBuffer buffer = buffers.get(i);

			IntBuffer indexBuffer = mesh.getIndexBuffer();
			FloatBuffer posBuffer = mesh.getPositionBuffer();
			FloatBuffer uvBuffer = mesh.getTexCoordBuffer(0);
			FloatBuffer normBuffer = mesh.getNormalBuffer();

			buffer.setIndexBuffer(indexBuffer);
			buffer.setPosBuffer(posBuffer);
			buffer.setUVBuffer(uvBuffer);
			buffer.setNormBuffer(normBuffer);
		}

		return buffers;
	}

	public static List<ModelBuffer> loadModel(String modelFilepath) throws IOException {
		return loadModel(modelFilepath,
				EnumSet.of(AiPostProcessSteps.TRIANGULATE, AiPostProcessSteps.GEN_NORMALS,
						AiPostProcessSteps.FIND_INVALID_DATA,
						AiPostProcessSteps.FIX_INFACING_NORMALS, AiPostProcessSteps.OPTIMIZE_MESHES,
						AiPostProcessSteps.VALIDATE_DATA_STRUCTURE,
						AiPostProcessSteps.REMOVE_REDUNDANT_MATERIALS));
	}
}
