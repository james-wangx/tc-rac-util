package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.codicefun.tc.rac.exception.TestException;
import com.codicefun.tc.rac.util.ItemUtil;
import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentUser;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class TestItemUtilHandler extends AbstractHandler {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	private static final TCComponentItem selectedItem;
	static {
		TCComponent component = (TCComponent) application.getTargetComponent();
		if (!(component instanceof TCComponentItem)) {
			throw new TestException("Found target component not item");
		}
		selectedItem = (TCComponentItem) component;
	}

	public static void testCreate() {
		try {
			TCComponentUser user = session.getUser();
			TCComponentFolder folder = user.getNewStuffFolder();
			TCComponentItem item = ItemUtil	.create(folder, "Item", null, "TestCreateItem", null, null)
											.orElseThrow(() -> new TestException("Create item fialed"));
			System.out.println("Created item id: " + item.getStringProperty("item_id"));
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public static void testSaveAs() {
		try {
			TCComponentUser user = session.getUser();
			TCComponentFolder folder = user.getNewStuffFolder();
			TCComponentItem item = ItemUtil	.saveAs(folder, selectedItem, null, null)
											.orElseThrow(() -> new TestException("Save as item failed"));
			System.out.println("Save as item id: " + item.getStringProperty("item_id"));
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public static void testGetLatestRevision() {
		try {
			TCComponentItemRevision revision = ItemUtil	.getLatestItemRevision(selectedItem)
														.orElseThrow(() -> new TestException(
																"Test getLatestItemRevision failed"));
			String revisionId = revision.getStringProperty("item_revision_id");
			System.out.println("Latest revision id: " + revisionId);
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public static void testGetLatestReleasedRevision() {
		try {
			TCComponentItemRevision revision = ItemUtil	.getLatestReleasedItemRevision(selectedItem)
														.orElseThrow(() -> new TestException(
																"Test getLatestReleasedItemRevision failed."));
			String revisionId = revision.getStringProperty("item_revision_id");
			System.out.println("Latest released revision id: " + revisionId);
		} catch (TCException e) {
			e.printStackTrace();
		}
	}

	public static void testGetAllRevisions() {
		TCComponentItemRevision[] revisions = ItemUtil	.getAllRevisions(selectedItem)
														.orElseThrow(
																() -> new TestException("Test getAllRevisions failed"));
		System.out.println("All revisions count: " + revisions.length);
	}

	public static void testGetNoBaseLineReleasedRevision() {
		TCComponentItemRevision[] revisions = ItemUtil	.getNoBaseLineReleasedRevision(selectedItem)
														.orElseThrow(() -> new TestException(
																"Test getNoBaseLineReleasedRevision failed"));
		System.out.println("All no base line released revisions count: " + revisions.length);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Start TestItemUtilHandler");

		testCreate();
		testSaveAs();
		testGetLatestRevision();
		testGetLatestReleasedRevision();
		testGetAllRevisions();
		testGetNoBaseLineReleasedRevision();

		return null;
	}

}
