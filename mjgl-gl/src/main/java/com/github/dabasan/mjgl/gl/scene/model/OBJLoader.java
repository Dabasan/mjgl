package com.github.dabasan.mjgl.gl.scene.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.dabasan.mjgl.gl.Color;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import de.javagl.obj.FloatTuple;
import de.javagl.obj.Mtl;
import de.javagl.obj.MtlReader;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjSplitting;
import de.javagl.obj.ObjUtils;

/**
 * OBJ loader
 * 
 * @author Daba
 *
 */
class OBJLoader {
	public static List<ModelBuffer> loadOBJ(InputStream isObj, String objDir) throws IOException {
		Obj obj = ObjReader.read(isObj);
		obj = ObjUtils.convertToRenderable(obj);

		var allMtls = new ArrayList<Mtl>();
		for (String mtlFilename : obj.getMtlFileNames()) {
			var mtlFile = new File(objDir, mtlFilename);
			try (var fis = new FileInputStream(mtlFile)) {
				List<Mtl> mtls = MtlReader.read(fis);
				allMtls.addAll(mtls);
			}
		}

		var buffers = new ArrayList<ModelBuffer>();

		Map<String, Obj> objGroups = ObjSplitting.splitByMaterialGroups(obj);
		for (var entry : objGroups.entrySet()) {
			var buffer = new ModelBuffer();

			String materialName = entry.getKey();
			Obj materialGroup = entry.getValue();

			Mtl mtl = findMtlByName(allMtls, materialName);
			if (mtl == null) {
				continue;
			}

			FloatTuple ftColorAmbient = mtl.getKa();
			FloatTuple ftColorDiffuse = mtl.getKd();
			FloatTuple ftColorSpecular = mtl.getKs();

			var colorAmbient = new Color(ftColorAmbient.getX(), ftColorAmbient.getY(),
					ftColorAmbient.getZ(), 1.0f);
			var colorDiffuse = new Color(ftColorDiffuse.getX(), ftColorDiffuse.getY(),
					ftColorDiffuse.getZ(), 1.0f);
			var colorSpecular = new Color(ftColorSpecular.getX(), ftColorSpecular.getY(),
					ftColorSpecular.getZ(), 1.0f);

			float specularExponent = mtl.getNs();
			float dissolve = mtl.getD();

			buffer.setColorAmbient(colorAmbient);
			buffer.setColorDiffuse(colorDiffuse);
			buffer.setColorSpecular(colorSpecular);
			buffer.setSpecularExponent(specularExponent);
			buffer.setDissolve(dissolve);

			IntBuffer indexBuffer = ObjData.getFaceVertexIndices(materialGroup, 3);
			FloatBuffer posBuffer = ObjData.getVertices(materialGroup);
			FloatBuffer uvBuffer = ObjData.getTexCoords(materialGroup, 2);
			FloatBuffer normBuffer = ObjData.getNormals(materialGroup);
			buffer.setIndexBuffer(indexBuffer);
			buffer.setPosBuffer(posBuffer);
			buffer.setUVBuffer(uvBuffer);
			buffer.setNormBuffer(normBuffer);

			String diffuseTextureMap = mtl.getMapKd();
			if (diffuseTextureMap == null) {
				buffer.setTexture(null);
			} else if (diffuseTextureMap.equals("")) {
				buffer.setTexture(null);
			} else {
				var diffuseTextureFile = new File(objDir, diffuseTextureMap);
				Texture diffuseTexture = TextureIO.newTexture(diffuseTextureFile, true);
				buffer.setTexture(diffuseTexture);
			}

			buffers.add(buffer);
		}

		return buffers;
	}
	private static Mtl findMtlByName(Iterable<? extends Mtl> mtls, String name) {
		for (final Mtl mtl : mtls) {
			if (mtl.getName().equals(name)) {
				return mtl;
			}
		}

		return null;
	}
}
