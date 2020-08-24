package com.github.dabasan.mjgl.gl.util.asset;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * File visitor to enumerate default textures
 * 
 * @author Daba
 *
 */
class TextureFileVisitor implements FileVisitor<Path> {
	private List<Path> filepaths;

	public TextureFileVisitor() {
		filepaths = new ArrayList<>();
	}

	public List<Path> getFilepaths() {
		return filepaths;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		Path filepath = Paths.get(file.getParent().toString(), file.getFileName().toString());
		filepaths.add(filepath);

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
