package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.codicefun.tc.rac.exception.TestException;
import com.codicefun.tc.rac.util.DatasetUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentDataset;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;

public class TestDatasetUtilHandler extends AbstractHandler {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();

	private static final String filePath = "C:\\Users\\Administrator\\Documents\\Test.docx";
	private static final String targetPath = "C:\\Users\\Administrator\\Downloads";

	private static final TCComponentItemRevision selectedRevision;
	static {
		TCComponent component = (TCComponent) application.getTargetComponent();
		if (!(component instanceof TCComponentItemRevision)) {
			throw new TestException("Found target component not item revision");
		}
		selectedRevision = (TCComponentItemRevision) component;
	}

	private static TCComponentDataset selectedDataset;

	public static void testCreate() {
		try {
			TCComponentDataset dataset = DatasetUtil.create("MSWordX", "TestDatasetUtil", null)
													.orElseThrow(() -> new TestException("Test create failed."));
			selectedRevision.add("IMAN_specification", dataset);
			selectedDataset = dataset;
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public static void testAddRef() {
		if (!DatasetUtil.addRef(selectedDataset, filePath, "word")) {
			throw new TestException("Test addRef failed.");
		}
	}

	public static void testDownloadFiles() {
		if (!DatasetUtil.downloadFiles(selectedDataset, targetPath)) {
			throw new TestException("Test downloadFiles failed.");
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Start TestDatasetUtilHandler");

		testCreate();
		testAddRef();
		testDownloadFiles();

		return null;
	}

}
