package com.xcplm.tc.rac.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtil {

	public static boolean copy(File source, File target) {
		try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos = new FileOutputStream(target)) {
			byte[] buffer = new byte[1024];
			int read;
			while ((read = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, read);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Copies a file from source to target.
	 *
	 * @param source    the source file path
	 * @param target    the target file path
	 * @param overwrite whether to overwrite the target file if it already exists
	 * @return true if copy is successful, false otherwise
	 */
	public static boolean copy(Path source, Path target, boolean overwrite) {
		if (source == null || target == null) {
			return false;
		}

		if (!Files.exists(source) || !Files.isRegularFile(source)) {
			return false; // Source doesn't exist or is not a file
		}

		try {
			// Ensure target directory exists
			Path parent = target.getParent();
			if (parent != null && !Files.exists(parent)) {
				Files.createDirectories(parent);
			}
			CopyOption[] options = overwrite ? new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }
					: new CopyOption[0];
			Files.copy(source, target, options);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Recursively deletes the specified directory and all its contents.
	 * 
	 * @param dir the directory to delete
	 * @return true if deletion was successful, false otherwise
	 */
	public static boolean deleteDirectory(File dir) {
		if (dir == null || !dir.exists()) {
			return false; // Directory does not exist
		}

		if (!dir.isDirectory()) {
			return false; // Not a directory
		}

		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				// If it's a directory, delete recursively; otherwise, delete the file
				if (file.isDirectory()) {
					if (!deleteDirectory(file)) {
						return false;
					}
				} else {
					if (!file.delete()) {
						return false;
					}
				}
			}
		}

		// Delete the now-empty directory
		return dir.delete();
	}

}
