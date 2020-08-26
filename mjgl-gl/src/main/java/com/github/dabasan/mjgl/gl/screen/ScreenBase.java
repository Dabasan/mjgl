package com.github.dabasan.mjgl.gl.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.gl.transferrer.QuadTransferrerWithUV;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Base class for screens
 * 
 * @author Daba
 *
 */
public class ScreenBase {
	private Logger logger = LoggerFactory.getLogger(ScreenBase.class);

	private int screenWidth;
	private int screenHeight;

	private int fbo;
	private int texture;

	private QuadTransferrerWithUV transferrer;

	public ScreenBase(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		this.setupTexture();
		this.setupFramebuffer();

		transferrer = new QuadTransferrerWithUV(true);
	}
	private void setupTexture() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(1);
		gl.glGenTextures(textures.capacity(), textures);
		texture = textures.get(0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texture);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGBA, screenWidth, screenHeight, 0,
				GL3ES3.GL_RGBA, GL3ES3.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_REPEAT);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_REPEAT);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(1);
		gl.glGenFramebuffers(fbos.capacity(), fbos);
		fbo = fbos.get(0);

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_TEXTURE_2D, texture, 0);
		IntBuffer draw_buffers = Buffers.newDirectIntBuffer(new int[]{GL3ES3.GL_COLOR_ATTACHMENT0});
		gl.glDrawBuffers(1, draw_buffers);
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(new int[]{fbo});
		IntBuffer textures = Buffers.newDirectIntBuffer(new int[]{texture});
		gl.glDeleteFramebuffers(fbos.capacity(), fbos);
		gl.glDeleteTextures(textures.capacity(), textures);
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}

	protected int getFBO() {
		return fbo;
	}
	protected int getTexture() {
		return texture;
	}
	protected QuadTransferrerWithUV getTransferrer() {
		return transferrer;
	}

	public void enable() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glViewport(0, 0, screenWidth, screenHeight);
	}
	public void clear() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glClear(GL3ES3.GL_DEPTH_BUFFER_BIT | GL3ES3.GL_COLOR_BUFFER_BIT
				| GL3ES3.GL_STENCIL_BUFFER_BIT);
	}

	public Texture wrap() {
		return new Texture(texture, GL3ES3.GL_TEXTURE_2D, screenWidth, screenHeight, screenWidth,
				screenHeight, false);
	}

	public void draw(ShaderProgram program, String samplerName, int textureUnit) {
		program.enable();
		program.setTexture(samplerName, textureUnit, GL3ES3.GL_TEXTURE_2D, texture);
		transferrer.transfer();
	}
	public void draw(ShaderProgram program) {
		this.draw(program, "textureSampler", 0);
	}

	public void bindColorTexture() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texture);
	}

	public int takeScreenshot(OutputStream os, String format) {
		int ret = 0;

		try {
			this.takeScreenshotBase(os, format);
		} catch (IOException e) {
			logger.error("Error", e);
			ret = -1;
		}

		return ret;
	}
	public int takeScreenshot(File file) {
		String format = this.getFileExtension(file.getName());
		if (format == null) {
			logger.error("Failed to get the target image format. filepath={}",
					file.getAbsolutePath());
			return -1;
		}

		try (var fos = new FileOutputStream(file)) {
			this.takeScreenshotBase(fos, format);
		} catch (IOException e) {
			logger.error("Error", e);
			return -1;
		}

		return 0;
	}
	public int takeScreenshot(String filepath) {
		String format = this.getFileExtension(filepath);
		if (format == null) {
			logger.error("Failed to get the target image format. filepath={}", filepath);
			return -1;
		}

		try (var fos = new FileOutputStream(filepath)) {
			this.takeScreenshotBase(fos, format);
		} catch (IOException e) {
			logger.error("Error", e);
			return -1;
		}

		return 0;
	}
	private String getFileExtension(String filename) {
		int lastDotPos = filename.lastIndexOf('.');
		if (lastDotPos == -1) {
			return null;
		}

		return filename.substring(lastDotPos + 1);
	}
	private void takeScreenshotBase(OutputStream os, String format) throws IOException {
		GL3 gl = GLContext.getCurrentGL().getGL3();

		ByteBuffer data = Buffers.newDirectByteBuffer(screenWidth * screenHeight * 4);
		gl.glBindTexture(GL3.GL_TEXTURE_2D, texture);
		gl.glGetTexImage(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, data);
		gl.glBindTexture(GL3.GL_TEXTURE_2D, 0);

		var image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_3BYTE_BGR);
		int pos = 0;
		for (int y = screenHeight - 1; y >= 0; y--) {
			for (int x = 0; x < screenWidth; x++) {
				int r = Byte.toUnsignedInt(data.get(pos));
				int g = Byte.toUnsignedInt(data.get(pos + 1));
				int b = Byte.toUnsignedInt(data.get(pos + 2));
				int a = Byte.toUnsignedInt(data.get(pos + 3));
				int rgb = (a << 24) | (r << 16) | (g << 8) | b;
				image.setRGB(x, y, rgb);

				pos += 4;
			}
		}

		ImageIO.write(image, format, os);
	}
}
