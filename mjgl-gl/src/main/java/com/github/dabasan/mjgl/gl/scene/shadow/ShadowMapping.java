package com.github.dabasan.mjgl.gl.scene.shadow;

import java.nio.IntBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.scene.light.DirectionalLight;
import com.github.dabasan.mjgl.gl.scene.light.LightBase;
import com.github.dabasan.mjgl.gl.scene.light.SpotLight;
import com.github.dabasan.mjgl.gl.scene.model.Model;
import com.github.dabasan.mjgl.gl.screen.ScreenBase;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.gl.transferrer.QuadTransferrerWithUV;
import com.github.dabasan.mjgl.math.MatrixFunctions;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Shadow mapping
 * 
 * @author Daba
 *
 */
public class ShadowMapping {
	private Logger logger = LoggerFactory.getLogger(ShadowMapping.class);

	public static final int MAX_NUM_LIGHTS = 16;
	private int numLights;

	private int depthTextureWidth;
	private int depthTextureHeight;
	private int shadowTextureWidth;
	private int shadowTextureHeight;

	private IntBuffer fbosDepth;
	private IntBuffer texsDepth;
	private IntBuffer fbosShadow;
	private IntBuffer rbosShadow;
	private IntBuffer texsShadow;

	private QuadTransferrerWithUV transferrer;

	private Matrix matBias;

	private ShaderProgram pgDepth;
	private ShaderProgram pgShadow;
	private ShaderProgram pgVisualizer;

	public ShadowMapping(int numLights, int depthTextureWidth, int depthTextureHeight,
			int shadowTextureWidth, int shadowTextureHeight) {
		if (numLights > MAX_NUM_LIGHTS) {
			this.numLights = MAX_NUM_LIGHTS;
		} else {
			this.numLights = numLights;
		}

		this.depthTextureWidth = depthTextureHeight;
		this.depthTextureHeight = depthTextureHeight;
		this.shadowTextureWidth = shadowTextureWidth;
		this.shadowTextureHeight = shadowTextureHeight;

		this.setupDepthTextures();
		this.setupDepthFramebuffers();
		this.setupShadowTextures();
		this.setupShadowRenderbuffers();
		this.setupShadowFramebuffers();

		transferrer = new QuadTransferrerWithUV(true);

		matBias = new Matrix();
		matBias.set(0, 0, 0.5);
		matBias.set(0, 3, 0.5);
		matBias.set(1, 1, 0.5);
		matBias.set(1, 3, 0.5);
		matBias.set(2, 2, 0.5);
		matBias.set(2, 3, 0.5);
		matBias.set(3, 3, 1.0);
	}
	private void setupDepthTextures() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		texsDepth = Buffers.newDirectIntBuffer(numLights);
		gl.glGenTextures(texsDepth.capacity(), texsDepth);

		for (int i = 0; i < texsDepth.capacity(); i++) {
			gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texsDepth.get(i));
			gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_DEPTH_COMPONENT16, depthTextureWidth,
					depthTextureHeight, 0, GL3ES3.GL_DEPTH_COMPONENT, GL3ES3.GL_FLOAT, null);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER,
					GL3ES3.GL_LINEAR);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER,
					GL3ES3.GL_LINEAR);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S,
					GL3ES3.GL_CLAMP_TO_EDGE);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T,
					GL3ES3.GL_CLAMP_TO_EDGE);
			gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
		}
	}
	private void setupDepthFramebuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		fbosDepth = Buffers.newDirectIntBuffer(numLights);
		gl.glGenFramebuffers(fbosDepth.capacity(), fbosDepth);

		for (int i = 0; i < fbosDepth.capacity(); i++) {
			gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbosDepth.get(i));
			gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_DEPTH_ATTACHMENT,
					GL3ES3.GL_TEXTURE_2D, texsDepth.get(i), 0);
			int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
			if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
				logger.error("Incomplete framebuffer for depth. status={}", status);
			}
			gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
		}
	}
	private void setupShadowTextures() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		texsShadow = Buffers.newDirectIntBuffer(1);
		gl.glGenTextures(texsShadow.capacity(), texsShadow);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texsShadow.get(0));
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGBA, shadowTextureWidth,
				shadowTextureHeight, 0, GL3ES3.GL_RGBA, GL3ES3.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupShadowRenderbuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		rbosShadow = Buffers.newDirectIntBuffer(1);
		gl.glGenRenderbuffers(rbosShadow.capacity(), rbosShadow);

		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, rbosShadow.get(0));
		gl.glRenderbufferStorage(GL3ES3.GL_RENDERBUFFER, GL3ES3.GL_DEPTH_COMPONENT,
				shadowTextureWidth, shadowTextureHeight);
		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, 0);
	}
	private void setupShadowFramebuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		fbosShadow = Buffers.newDirectIntBuffer(1);
		gl.glGenFramebuffers(fbosShadow.capacity(), fbosShadow);

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbosShadow.get(0));
		gl.glFramebufferRenderbuffer(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_DEPTH_ATTACHMENT,
				GL3ES3.GL_RENDERBUFFER, rbosShadow.get(0));
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_TEXTURE_2D, texsShadow.get(0), 0);
		int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
		if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
			logger.error("Incomplete framebuffer for shadow. status={}", status);
		}
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		gl.glDeleteFramebuffers(fbosDepth.capacity(), fbosDepth);
		gl.glDeleteTextures(texsDepth.capacity(), texsDepth);
		gl.glDeleteFramebuffers(fbosShadow.capacity(), fbosShadow);
		gl.glDeleteRenderbuffers(rbosShadow.capacity(), rbosShadow);
		gl.glDeleteTextures(texsShadow.capacity(), texsShadow);
	}

	public void resizeShadowTexture(int shadowTextureWidth, int shadowTextureHeight) {
		this.shadowTextureWidth = shadowTextureWidth;
		this.shadowTextureHeight = shadowTextureHeight;

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texsShadow.get(0));
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGBA, shadowTextureWidth,
				shadowTextureHeight, 0, GL3ES3.GL_RGBA, GL3ES3.GL_UNSIGNED_BYTE, null);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}

	public void setPrograms(ShaderProgram pgDepth, ShaderProgram pgShadow,
			ShaderProgram pgVisualizer) {
		this.pgDepth = pgDepth;
		this.pgShadow = pgShadow;
		this.pgVisualizer = pgVisualizer;
	}

	public void setNormalOffset(float normalOffset) {
		pgShadow.enable();
		pgShadow.setUniform("normalOffset", normalOffset);
	}
	public void setBiasCoefficient(float biasCoefficient) {
		pgShadow.enable();
		pgShadow.setUniform("biasCoefficient", biasCoefficient);
	}
	public void setMaxBias(float maxBias) {
		pgShadow.enable();
		pgShadow.setUniform("maxBias", maxBias);
	}
	public void setInShadowVisibility(float inShadowVisibility) {
		pgShadow.enable();
		pgShadow.setUniform("inShadowVisibility", inShadowVisibility);
	}
	public void setIntegrationMethod(IntegrationMethod integrationMethod) {
		pgShadow.enable();
		pgShadow.setUniform("integrationMethod", integrationMethod.ordinal());
	}

	public void update(List<LightBase> lights, List<Model> depthModels, List<Model> shadowModels) {
		this.transferLightProperties(lights);

		int bound = Math.min(numLights, lights.size());
		this.generateDepthTextures(bound, depthModels);
		this.generateShadowTexture(bound, shadowModels);
	}
	private void transferLightProperties(List<LightBase> lights) {
		int bound = Math.min(numLights, lights.size());
		for (int i = 0; i < bound; i++) {
			var light = lights.get(i);

			Vector position = light.getPosition();
			Vector target = light.getTarget();
			Vector up = new Vector(0.0, 1.0, 0.0);
			float near = light.getNear();
			float far = light.getFar();

			var direction = target.sub(position).normalize();

			Matrix viewTransformation = MatrixFunctions.getViewTransformationMatrix(position,
					target, up);
			Matrix projection;
			ProjectionType projectionType;
			if (light instanceof DirectionalLight) {
				float size = ((DirectionalLight) light).getSize();
				projection = MatrixFunctions.getOrthogonalMatrix(-size, size, -size, size, near,
						far);
				projectionType = ProjectionType.ORTHOGRAPHIC;
			} else if (light instanceof SpotLight) {
				float phi = ((SpotLight) light).getPhi();
				projection = MatrixFunctions.getPerspectiveMatrix(phi, 1.0, near, far);
				projectionType = ProjectionType.PERSPECTIVE;
			} else {
				return;
			}

			var depthVP = projection.mult(viewTransformation);
			var depthBiasVP = matBias.mult(depthVP);

			pgDepth.enable();
			pgDepth.setUniform("depthVP", depthVP, true);

			String strArray = "lights" + "[" + i + "]";
			pgShadow.enable();
			pgShadow.setUniform(strArray + ".projectionType", projectionType.ordinal());
			pgShadow.setUniform(strArray + ".depthBiasVP", depthBiasVP, true);
			pgShadow.setUniform(strArray + ".direction", direction);
		}
	}
	private void generateDepthTextures(int bound, List<Model> models) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		pgDepth.enable();
		for (int i = 0; i < bound; i++) {
			gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbosDepth.get(i));
			gl.glViewport(0, 0, depthTextureWidth, depthTextureHeight);
			gl.glClear(GL3ES3.GL_COLOR_BUFFER_BIT | GL3ES3.GL_DEPTH_BUFFER_BIT);
			for (var model : models) {
				model.transfer(pgDepth);
			}
			gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
		}
	}
	private void generateShadowTexture(int bound, List<Model> models) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		pgShadow.enable();
		for (int i = 0; i < bound; i++) {
			String strArray = "depthTextures" + "[" + i + "]";
			pgShadow.setTexture(strArray + "[" + i + "]", i + 1, GL3ES3.GL_TEXTURE_2D,
					texsDepth.get(i));
		}

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbosShadow.get(0));
		gl.glViewport(0, 0, shadowTextureWidth, shadowTextureHeight);
		gl.glClear(GL3ES3.GL_COLOR_BUFFER_BIT | GL3ES3.GL_DEPTH_BUFFER_BIT);
		for (var model : models) {
			model.draw(pgShadow, "textureSampler", 0);
		}
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public int getShadowTexture() {
		return texsShadow.get(0);
	}

	public int visualizeDepthTexture(int index, ScreenBase screen) {
		if (!(0 <= index && index < numLights)) {
			return -1;
		}

		pgVisualizer.enable();
		pgVisualizer.setTexture("textureSampler", 0, GL3ES3.GL_TEXTURE_2D, texsDepth.get(index));
		screen.enable();
		screen.clear();
		transferrer.transfer();

		return 0;
	}
}
