package com.github.dabasan.mjgl.gl.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 * Default material loader
 * 
 * @author Daba
 *
 */
public class DefaultMaterialLoader {
	private List<ShaderProgram> programs2D;
	private List<ShaderProgram> programs3D;
	private List<Texture> textures;

	public DefaultMaterialLoader(String baseDir) throws IOException {
		programs2D = new ArrayList<>();
		programs3D = new ArrayList<>();
		textures = new ArrayList<>();

		// Search in the directories.
		Path shader2DBaseDir = Paths.get(baseDir, "Shader", "Default", "2D");
		Path shader3DBaseDir = Paths.get(baseDir, "Shader", "Default", "3D");
		Path textureBaseDir = Paths.get(baseDir, "Texture", "Default");

		var fvShader2D = new ShaderFileVisitor();
		var fvShader3D = new ShaderFileVisitor();
		var fvTexture = new TextureFileVisitor();

		Files.walkFileTree(shader2DBaseDir, fvShader2D);
		Files.walkFileTree(shader3DBaseDir, fvShader3D);
		Files.walkFileTree(textureBaseDir, fvTexture);

		List<Path> shader2DDirs = fvShader2D.getDirs();
		List<Path> shader3DDirs = fvShader3D.getDirs();
		List<Path> textureFilepaths = fvTexture.getFilepaths();

		// Load material.
		for (var shader2DDir : shader2DDirs) {
			var fileVShader = new File(shader2DDir.toString(), "vshader.glsl");
			var fileFShader = new File(shader2DDir.toString(), "fshader.glsl");
			var program = new ShaderProgram(fileVShader, fileFShader);
			programs2D.add(program);
		}
		for (var shader3DDir : shader3DDirs) {
			var fileVShader = new File(shader3DDir.toString(), "vshader.glsl");
			var fileFShader = new File(shader3DDir.toString(), "fshader.glsl");
			var program = new ShaderProgram(fileVShader, fileFShader);
			programs3D.add(program);
		}
		for (var textureFilepath : textureFilepaths) {
			var fileTexture = new File(textureFilepath.toString());
			Texture texture = TextureIO.newTexture(fileTexture, true);
			textures.add(texture);
		}
	}

	public List<ShaderProgram> getPrograms2D() {
		return new ArrayList<>(programs2D);
	}
	public List<ShaderProgram> getPrograms3D() {
		return new ArrayList<>(programs3D);
	}
	public List<Texture> getTextures() {
		return new ArrayList<>(textures);
	}
}
