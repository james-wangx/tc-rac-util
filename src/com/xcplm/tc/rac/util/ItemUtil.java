package com.xcplm.tc.rac.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentFolder;
import com.teamcenter.rac.kernel.TCComponentItem;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCComponentItemType;
import com.teamcenter.rac.kernel.TCComponentReleaseStatusType;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;

public class ItemUtil {

	private static final AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static final TCSession session = (TCSession) application.getSession();

	public static Optional<TCComponentItem> create(TCComponentFolder folder, String itemType, String itemId,
			String itemName, String itemDesc, String revId) {
		try {
			TCComponentItemType type = (TCComponentItemType) session.getTypeComponent("Item");
			TCComponentItem item = type.create(itemId, revId, itemType, itemName, itemDesc, null);
			if (item == null) {
				return Optional.empty();
			}
			if (folder != null) {
				folder.add("contents", item);
			}
			return Optional.of(item);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentItem> saveAs(TCComponentFolder folder, TCComponentItem item, String newItemId,
			String newRevId) {
		try {
			TCComponentItemRevision itemRevision = item.getLatestItemRevision();
			if (itemRevision == null) {
				return Optional.empty();
			}
			TCComponentItem newItem = itemRevision.saveAsItem(newItemId, newRevId);
			if (newItem == null) {
				return Optional.empty();
			}
			if (folder != null) {
				folder.add("contents", newItem);
			}
			return Optional.of(newItem);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentItemRevision> getLatestItemRevision(TCComponentItem item) {
		try {
			return Optional.ofNullable(item.getLatestItemRevision());
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentItemRevision> getLatestReleasedItemRevision(TCComponentItem item) {
		try {
			TCComponentItemRevision[] releasedItemRevisions = item.getReleasedItemRevisions();
			if (releasedItemRevisions == null || releasedItemRevisions.length == 0) {
				return Optional.empty();
			}

			String latestRevisionId = null;
			TCComponentItemRevision latestReleasedRev = null;
			for (TCComponentItemRevision releasedItemRevision : releasedItemRevisions) {
				String currentRevisionId = releasedItemRevision.getStringProperty("item_revision_id");
				if (currentRevisionId == null) {
					continue;
				}
				if (latestRevisionId == null || latestRevisionId.compareTo(currentRevisionId) < 0) {
					latestRevisionId = currentRevisionId;
					latestReleasedRev = releasedItemRevision;
				}
			}

			return Optional.ofNullable(latestReleasedRev);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentItemRevision[]> getAllRevisions(TCComponentItem item) {
		try {
			TCComponent[] components = item.getReferenceListProperty("revision_list");
			TCComponentItemRevision[] revisions = Arrays.stream(components)
														.filter(TCComponentItemRevision.class::isInstance)
														.map(TCComponentItemRevision.class::cast)
														.toArray(TCComponentItemRevision[]::new);
			return Optional.ofNullable(revisions);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static Optional<TCComponentItemRevision[]> getNoBaseLineReleasedRevision(TCComponentItem item) {
		try {
			List<TCComponentItemRevision> revisionList = new ArrayList<>();
			TCComponentItemRevision[] releasedRevisions = item.getReleasedItemRevisions();
			if (releasedRevisions == null || releasedRevisions.length == 0) {
				return Optional.empty();
			}
			for (TCComponentItemRevision releasedRevision : releasedRevisions) {
				if (releasedRevision.isBased()) {
					continue;
				}
				revisionList.add(releasedRevision);
			}
			return Optional.of(revisionList.toArray(new TCComponentItemRevision[0]));
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static void test() throws TCException {
		TCComponentReleaseStatusType rsType = (TCComponentReleaseStatusType) session.getTypeComponent("ReleaseStatus");
		rsType.create(null);
	}

}
