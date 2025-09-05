package com.xcplm.tc.rac.util;

import java.util.Optional;

import com.teamcenter.rac.aif.AbstractAIFUIApplication;
import com.teamcenter.rac.aifrcp.AIFUtility;
import com.teamcenter.rac.kernel.TCComponent;
import com.teamcenter.rac.kernel.TCComponentBOMViewRevision;
import com.teamcenter.rac.kernel.TCComponentBOMViewType;
import com.teamcenter.rac.kernel.TCComponentItemRevision;
import com.teamcenter.rac.kernel.TCException;
import com.teamcenter.rac.kernel.TCSession;
import com.teamcenter.services.internal.rac.structuremanagement.StructureService;
import com.teamcenter.services.internal.rac.structuremanagement._2011_06.Structure.CreateOrSaveAsPSBOMViewRevisionInput;
import com.teamcenter.services.internal.rac.structuremanagement._2011_06.Structure.CreateOrSaveAsPSBOMViewRevisionResponse;
import com.teamcenter.services.internal.rac.structuremanagement._2011_06.Structure.GetAllAvailableViewTypesInput;
import com.teamcenter.services.internal.rac.structuremanagement._2011_06.Structure.GetAvailableViewTypesResponse;
import com.xcplm.tc.rac.exception.RacUtilException;

public class StructureUtil {

	private static AbstractAIFUIApplication application = AIFUtility.getCurrentApplication();
	private static TCSession session = (TCSession) application.getSession();
	private static final StructureService structureService = StructureService.getService(session);

	/**
	 * Create PSBOMViewRevision
	 * 
	 * @param itemRevision item revision
	 * @param viewTypeName view type name
	 * @param isPrevise    is precise
	 * @return Optional of TCComponentBOMViewRevision
	 */
	public static Optional<TCComponentBOMViewRevision> createPSBVR(TCComponentItemRevision itemRevision,
			String viewTypeName, boolean isPrevise) {
		try {
			CreateOrSaveAsPSBOMViewRevisionInput[] inputs = new CreateOrSaveAsPSBOMViewRevisionInput[1];
			inputs[0] = new CreateOrSaveAsPSBOMViewRevisionInput();
			inputs[0].itemObject = itemRevision.getItem();
			inputs[0].itemRevObj = itemRevision;
			if (!StringUtil.isNullOrEmpty(viewTypeName)) {
				inputs[0].viewTypeTag = getPSVT(itemRevision, viewTypeName)
						.orElseThrow(() -> new RacUtilException("Not found PSViewType by name: " + viewTypeName));
			}
			inputs[0].isPrecise = isPrevise;

			CreateOrSaveAsPSBOMViewRevisionResponse response = structureService.createOrSavePSBOMViewRevision(inputs);
			if (ServiceUtil.catchPartialErrors(response.serviceData) || response.psBVROutputs == null
					|| response.psBVROutputs.length == 0) {
				return Optional.empty();
			}

			return Optional.ofNullable(response.psBVROutputs[0].bvrTag);
		} catch (TCException e) {
			System.out.println("Create PSBVR failed: " + e.getMessage());
			return Optional.empty();
		}
	}

	/**
	 * Create a TCComponentBOMViewRevision for the given ItemRevision.
	 *
	 * @param itemRevision The ItemRevision to create the TCComponentBOMViewRevision
	 *                     for.
	 * @return An Optional containing the created TCComponentBOMViewRevision, or an
	 *         empty Optional if creation failed.
	 */
	public static Optional<TCComponentBOMViewRevision> createPSBVR(TCComponentItemRevision itemRevision) {
		return createPSBVR(itemRevision, null, false);
	}

	/**
	 * Get PSViewType by name
	 * 
	 * @param itemRevision item revision
	 * @param viewTypeName view type name
	 * @return Optional of TCComponentBOMViewType
	 */
	public static Optional<TCComponentBOMViewType> getPSVT(TCComponentItemRevision itemRevision, String viewTypeName) {
		GetAllAvailableViewTypesInput[] inputs = new GetAllAvailableViewTypesInput[1];
		try {
			inputs[0] = new GetAllAvailableViewTypesInput();
			inputs[0].itemObject = itemRevision.getItem();
			inputs[0].itemRevisionObj = itemRevision;

			GetAvailableViewTypesResponse response = structureService.getAvailableViewTypes(inputs);
			if (ServiceUtil.catchPartialErrors(response.serviceData) || response.viewTypesOutputs == null
					|| response.viewTypesOutputs.length == 0) {
				return Optional.empty();
			}

			TCComponent[] psViewTypeTags = response.viewTypesOutputs[0].viewTags;
			for (TCComponent psViewTypeTag : psViewTypeTags) {
				String tempName = psViewTypeTag.getProperty("name");
				if (tempName.equals(viewTypeName)) {
					return Optional.of((TCComponentBOMViewType) psViewTypeTag);
				}
			}
		} catch (TCException e) {
			System.out.println("Get PSViewType failed: " + e.getMessage());

		}

		return Optional.empty();
	}

}
