package com.github.dabasan.mjgl.gl.renderer.text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.mjgl.gl.Color;
import com.jogamp.graph.curve.Region;
import com.jogamp.graph.curve.opengl.RegionRenderer;
import com.jogamp.graph.curve.opengl.RenderState;
import com.jogamp.graph.curve.opengl.TextRegionUtil;
import com.jogamp.graph.font.Font;
import com.jogamp.graph.font.FontFactory;
import com.jogamp.graph.geom.SVertex;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.PMVMatrix;

/**
 * Text renderer
 * 
 * @author Daba
 *
 */
public class TextRenderer {
	private Logger logger = LoggerFactory.getLogger(TextRenderer.class);

	private RenderState renderState;
	private RegionRenderer regionRenderer;
	private TextRegionUtil textRegionUtil;

	private Font font;

	private int windowWidth;
	private int windowHeight;

	public TextRenderer() {
		renderState = RenderState.createRenderState(SVertex.factory());
		renderState.setHintMask(RenderState.BITHINT_GLOBAL_DEPTH_TEST_ENABLED);

		regionRenderer = RegionRenderer.create(renderState, RegionRenderer.defaultBlendEnable,
				RegionRenderer.defaultBlendDisable);

		textRegionUtil = new TextRegionUtil(Region.VARWEIGHT_RENDERING_BIT);

		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();
		regionRenderer.init(gl, Region.VARWEIGHT_RENDERING_BIT);

		try {
			font = FontFactory.get(FontFactory.JAVA).getDefault();
		} catch (IOException e) {
			logger.error("Error", e);
		}

		windowWidth = 640;
		windowHeight = 480;
	}

	public void loadFont(InputStream is) throws IOException {
		font = FontFactory.get(is, false);
	}
	public void loadFont(File file) throws IOException {
		font = FontFactory.get(file);
	}
	public void loadFont(String filepath) throws IOException {
		font = FontFactory.get(new File(filepath));
	}

	public void setWindowSize(int windowWidth, int windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}

	public void drawText(float x, float y, float z, String text, Color color, float size,
			float resolution) {
		PMVMatrix pmv = regionRenderer.getMatrix();
		pmv.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		pmv.glLoadIdentity();
		pmv.glTranslatef(x, y, z);

		renderState.setColorStatic(color.getR(), color.getG(), color.getB(), color.getA());

		float pixelSize = font.getPixelSize(size, resolution);

		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int[] countSamples = new int[]{4};
		regionRenderer.enable(gl, true);
		regionRenderer.reshapeOrtho(windowWidth, windowHeight, 0.1f, 100.0f);
		textRegionUtil.drawString3D(gl, regionRenderer, font, pixelSize, text, null, countSamples);
		regionRenderer.enable(gl, false);
	}
}
