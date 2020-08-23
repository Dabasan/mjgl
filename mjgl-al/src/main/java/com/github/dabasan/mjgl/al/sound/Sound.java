package com.github.dabasan.mjgl.al.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.ejml_3dtools.Vector;
import com.jogamp.common.nio.Buffers;
import com.jogamp.openal.AL;
import com.jogamp.openal.ALConstants;
import com.jogamp.openal.ALFactory;
import com.jogamp.openal.util.ALut;

/**
 * Sound
 * 
 * @author Daba
 *
 */
public class Sound {
	private int buffer;
	private int source;

	public Sound(InputStream is) {
		this.constructorBase(is);
	}
	public Sound(File file) throws IOException {
		try (var fis = new FileInputStream(file)) {
			this.constructorBase(fis);
		}
	}
	public Sound(String filepath) throws IOException {
		try (var fis = new FileInputStream(filepath)) {
			this.constructorBase(fis);
		}
	}
	private void constructorBase(InputStream is) {
		int[] format = new int[1];
		ByteBuffer[] data = new ByteBuffer[1];
		int[] size = new int[1];
		int[] freq = new int[1];
		int[] loop = new int[1];
		ALut.alutLoadWAVFile(is, format, data, size, freq, loop);

		AL al = ALFactory.getAL();

		IntBuffer buffers = Buffers.newDirectIntBuffer(1);
		IntBuffer sources = Buffers.newDirectIntBuffer(1);
		al.alGenBuffers(buffers.capacity(), buffers);
		al.alGenSources(sources.capacity(), sources);
		buffer = buffers.get(0);
		source = sources.get(0);

		al.alBufferData(buffer, format[0], data[0], size[0], freq[0]);

		al.alSourcei(source, ALConstants.AL_BUFFER, buffer);
		al.alSourcef(source, ALConstants.AL_PITCH, 1.0f);
		al.alSourcef(source, ALConstants.AL_GAIN, 1.0f);
		al.alSource3f(source, ALConstants.AL_POSITION, 0.0f, 0.0f, 0.0f);
		al.alSource3f(source, ALConstants.AL_VELOCITY, 0.0f, 0.0f, 0.0f);
		al.alSourcef(source, ALConstants.AL_REFERENCE_DISTANCE, 10.0f);
		al.alSourcef(source, ALConstants.AL_MAX_DISTANCE, 50.0f);
		al.alSourcei(source, ALConstants.AL_LOOPING, ALConstants.AL_FALSE);
	}

	public void delete() {
		IntBuffer buffers = Buffers.newDirectIntBuffer(new int[]{buffer});
		IntBuffer sources = Buffers.newDirectIntBuffer(new int[]{source});

		AL al = ALFactory.getAL();
		al.alDeleteBuffers(buffers.capacity(), buffers);
		al.alDeleteSources(sources.capacity(), sources);
	}

	public void setSourcePosition(Vector sourcePosition) {
		AL al = ALFactory.getAL();
		al.alSource3f(source, ALConstants.AL_POSITION, sourcePosition.getXFloat(),
				sourcePosition.getYFloat(), sourcePosition.getZFloat());
	}
	public void setSourceVelocity(Vector sourceVelocity) {
		AL al = ALFactory.getAL();
		al.alSource3f(source, ALConstants.AL_VELOCITY, sourceVelocity.getXFloat(),
				sourceVelocity.getYFloat(), sourceVelocity.getZFloat());
	}
	public void setReferenceDistance(float referenceDistance) {
		AL al = ALFactory.getAL();
		al.alSourcef(source, ALConstants.AL_REFERENCE_DISTANCE, referenceDistance);
	}
	public void setMaxDistance(float maxDistance) {
		AL al = ALFactory.getAL();
		al.alSourcef(source, ALConstants.AL_MAX_DISTANCE, maxDistance);
	}
	public void enableLooping(boolean enable) {
		AL al = ALFactory.getAL();
		if (enable) {
			al.alSourcei(source, ALConstants.AL_LOOPING, ALConstants.AL_TRUE);
		} else {
			al.alSourcei(source, ALConstants.AL_LOOPING, ALConstants.AL_FALSE);
		}
	}

	public void play() {
		AL al = ALFactory.getAL();
		al.alSourcePlay(source);
	}
	public void stop() {
		AL al = ALFactory.getAL();
		al.alSourceStop(source);
	}
	public void pause() {
		AL al = ALFactory.getAL();
		al.alSourcePause(source);
	}

	public boolean isPlaying() {
		AL al = ALFactory.getAL();

		IntBuffer stateBuffer = Buffers.newDirectIntBuffer(1);
		al.alGetSourceiv(source, ALConstants.AL_SOURCE_STATE, stateBuffer);

		if (stateBuffer.get(0) == ALConstants.AL_PLAYING) {
			return true;
		} else {
			return false;
		}
	}
}
