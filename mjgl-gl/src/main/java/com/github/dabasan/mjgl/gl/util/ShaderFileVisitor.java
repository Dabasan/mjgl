package com.github.dabasan.mjgl.gl.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * File visitor to enumerate default shaders
 * 
 * @author Daba
 *
 */
class ShaderFileVisitor implements FileVisitor<Path> {
	private List<Path> dirs;

	public ShaderFileVisitor() {
		dirs = new ArrayList<>();
	}

	public List<Path> getDirs() {
		return dirs;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		boolean dirAlreadyVisited = false;
		for (var dir : dirs) {
			if (Files.isSameFile(dir, file.getParent())) {
				dirAlreadyVisited = true;
				break;
			}
		}

		if (dirAlreadyVisited == false) {
			dirs.add(file.getParent());
		}

		return FileVisitResult.CONTINUE;
	}
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.TERMINATE;
	}
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
}
