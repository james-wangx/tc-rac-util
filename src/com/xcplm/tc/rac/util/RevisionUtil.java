package com.xcplm.tc.rac.util;

import java.util.Optional;

import com.teamcenter.rac.kernel.DeepCopyInfo;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;

public class RevisionUtil {

	public static Optional<TCComponentItemRevision> revise(TCComponentItemRevision revision, String id, String name,
			String desc) {
		try {
			if (!revision	.getItem()
							.okToModify()) {
				return Optional.empty();
			}
			DeepCopyInfo info = new DeepCopyInfo(revision, DeepCopyInfo.COPY_AS_OBJECT_ACTION, null, null, true, true,
					true);
			TCComponentItemRevision newRevision = revision.saveAs(id, name, desc, true, new DeepCopyInfo[] { info });
			return Optional.ofNullable(newRevision);
		} catch (TCException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

}
