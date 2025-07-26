package com.codicefun.tc.rac.util;

import java.io.File;
import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentDatasetType;
import com.teamcenter.rac.kernel.TCComponentTcFile;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class DatasetUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	public static Optional<TCComponentDataset> create(String type, String name, String desc) {
		try {
			TCComponentDatasetType datasetType = (TCComponentDatasetType) session.getTypeComponent("Dataset");
			if (datasetType == null) {
				return Optional.empty();
			}
			return Optional.ofNullable(datasetType.create(name, desc, type));
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static boolean addRef(TCComponentDataset dataset, String filePath, String refName) {
		try {
			dataset.setFiles(new String[] { filePath }, new String[] { refName });
			return true;
		} catch (TCException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean downloadFiles(TCComponentDataset dataset, String targetPath) {
		try {
			TCComponentTcFile[] tcFiles = dataset.getTcFiles();
			for (TCComponentTcFile tcFile : tcFiles) {
				File sourceFile = tcFile.getFmsFile();
				String fileName = tcFile.getStringProperty("original_file_name");
				File file = new File(targetPath + File.separator + fileName);
				if (file.exists()) {
					file.delete();
				}
				if (!FileUtil.copy(sourceFile, file)) {
					return false;
				}
				return true;
			}
		} catch (TCException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean download(TCComponentDataset dataset, String targetFilePath) {
		try {
			TCComponentTcFile[] tcFiles = dataset.getTcFiles();
			if (tcFiles.length == 0) {
				return false; // No files to download
			}
			TCComponentTcFile tcFile = tcFiles[0];
			File fmsFile = tcFile.getFmsFile();
			File targetFile = new File(targetFilePath);
			if (FileUtil.copy(fmsFile.toPath(), targetFile.toPath(), true)) {
				return true; // Successfully copied the file
			}
			return false; // Failed to copy the file
		} catch (TCException e) {
			e.printStackTrace();
		}

		return false;
	}

}
