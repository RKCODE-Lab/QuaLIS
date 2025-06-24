package com.agaramtech.qualis.instrumentmanagement.service.instrumentcategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.agaramtech.qualis.global.AuditUtilityFunction;
import com.agaramtech.qualis.global.CommonFunction;
import com.agaramtech.qualis.global.DateTimeUtilityFunction;
import com.agaramtech.qualis.global.Enumeration;
import com.agaramtech.qualis.global.JdbcTemplateUtilityFunction;
import com.agaramtech.qualis.global.ProjectDAOSupport;
import com.agaramtech.qualis.global.StringUtilityFunction;
import com.agaramtech.qualis.global.UserInfo;
import com.agaramtech.qualis.global.ValidatorDel;
import com.agaramtech.qualis.instrumentmanagement.model.InstrumentCategory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class InstrumentCategoryDAOImpl implements InstrumentCategoryDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentCategoryDAOImpl.class);

	private final StringUtilityFunction stringUtilityFunction;
	private final CommonFunction commonFunction;
	private final JdbcTemplate jdbcTemplate;
	private ValidatorDel valiDatorDel;
	private final JdbcTemplateUtilityFunction jdbcUtilityFunction;
	private final ProjectDAOSupport projectDAOSupport;
	private final DateTimeUtilityFunction dateUtilityFunction;
	private final AuditUtilityFunction auditUtilityFunction;

	/**
	 * This method is used to retrieve list of all active Instrument Category for
	 * the specified site.
	 * 
	 * @param userInfo object is used for fetched the list of active records based
	 *                 on site
	 * @return response entity object holding response status and list of all active
	 *         Instrument Category
	 * @throws Exception that are thrown from this DAO layer
	 */
	@Override
	public ResponseEntity<Object> fetchInstrumentCategory(final UserInfo userInfo) throws Exception {
		final String sQuery = "select ic.ninstrumentcatcode,ic.sinstrumentcatname from instrumentcategory ic"
				+ " where ic.nstatus=" + Enumeration.TransactionStatus.ACTIVE.gettransactionstatus()
				+ " and ninstrumentcatcode>0" + " and ic.nsitecode = " + userInfo.getNmastersitecode();
		return new ResponseEntity<Object>(
				(List<InstrumentCategory>) jdbcTemplate.query(sQuery, new InstrumentCategory()), HttpStatus.OK);
	}

	/**
	 * This interface declaration is used to retrieve list of all active Instrument
	 * Category for the specified site through its DAO layer
	 * 
	 * @param userInfo object is used for fetched the list of active records based
	 *                 on site
	 * @return response entity object holding response status and list of all active
	 *         Instrument Category
	 * @throws Exception that are thrown in the DAO layer
	 */
	@Override
	public ResponseEntity<Object> getInstrumentCategory(final UserInfo userInfo) throws Exception {
		LOGGER.info("getInstrumentCategory");
		final String sQuery = "select ic.ninstrumentcatcode,ic.sinstrumentcatname,"
				+ " coalesce(t1.jsondata->'stransdisplaystatus'->>'" + userInfo.getSlanguagetypecode()
				+ "', t1.jsondata->'stransdisplaystatus'->>'en-US') as scategorybasedflow,"
				+ "coalesce(t2.jsondata->'stransdisplaystatus'->>'" + userInfo.getSlanguagetypecode()
				+ "',t2.jsondata->'stransdisplaystatus'->>'en-US') as scalibrationrequired," + " ic.ncategorybasedflow,"
				+ "ic.ncalibrationreq,ic.ndefaultstatus,coalesce(t3.jsondata->'stransdisplaystatus'->>'"
				+ userInfo.getSlanguagetypecode() + "',"
				+ "t2.jsondata->'stransdisplaystatus'->>'en-US') as sdisplaystatus,"
				+ "ic.sdescription from instrumentcategory ic,transactionstatus t1,transactionstatus t2,transactionstatus t3 "
				+ " where  ic.ndefaultstatus=t3.ntranscode " + " and t1.ntranscode=ic.ncategorybasedflow "
				+ "and t2.ntranscode=ic.ncalibrationreq  and ic.nstatus="
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and t1.nstatus="
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and t2.nstatus="
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and t3.nstatus="
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and ninstrumentcatcode>0"
				+ " and ic.nsitecode = " + userInfo.getNmastersitecode();
		return new ResponseEntity<Object>(jdbcTemplate.query(sQuery, new InstrumentCategory()), HttpStatus.OK);
	}

	/**
	 * This method is used to add a new entry to Instrument Category table. Need to
	 * check for duplicate entry of manufacturer name for the specified site before
	 * saving into database.
	 * 
	 * @param UserInfo           object is used for fetched the list of active
	 *                           records based on site
	 * @param InstrumentCategory [Instrument Category] object holding details to be
	 *                           added in Instrument Category table
	 * @return response entity object holding response status and data of added
	 *         Instrument Category object
	 * @throws Exception that are thrown from this DAO layer
	 */
	@Override
	public ResponseEntity<Object> createInstrumentCategory(final InstrumentCategory instrumentCategory,
			final UserInfo userInfo) throws Exception {
		final String sQuery = " lock  table lockinstrumentcategory "
				+ Enumeration.ReturnStatus.TABLOCK.getreturnstatus();
		jdbcTemplate.execute(sQuery);

		final InstrumentCategory instrumentCategoryListByName = getInstrumentCategoryByName(
				instrumentCategory.getSinstrumentcatname(), userInfo.getNmastersitecode());

		if (instrumentCategoryListByName == null) {
			if (instrumentCategory.getNdefaultstatus() == Enumeration.TransactionStatus.YES.gettransactionstatus()) {

				final InstrumentCategory defaultInstrumentCategory = getInstrumentCategoryByDefaultStatus(
						instrumentCategory.getNsitecode());

				if (defaultInstrumentCategory != null) {

					final InstrumentCategory instrumentCategoryBeforeSave = SerializationUtils
							.clone(defaultInstrumentCategory);

					final List<Object> defaultListBeforeSave = new ArrayList<>();
					defaultListBeforeSave.add(instrumentCategoryBeforeSave);

					defaultInstrumentCategory
							.setNdefaultstatus((short) Enumeration.TransactionStatus.NO.gettransactionstatus());

					final String updateQueryString = " update instrumentcategory set ndefaultstatus="
							+ Enumeration.TransactionStatus.NO.gettransactionstatus() + " where ninstrumentcatcode ="
							+ defaultInstrumentCategory.getNinstrumentcatcode() + " and nsitecode="
							+ userInfo.getNmastersitecode();
					jdbcTemplate.execute(updateQueryString);

					final List<Object> defaultListAfterSave = new ArrayList<>();
					defaultListAfterSave.add(defaultInstrumentCategory);

					auditUtilityFunction.fnInsertAuditAction(defaultListAfterSave, 2, defaultListBeforeSave,
							Arrays.asList("IDS_EDITINSTRUMENTCATEGORY"), userInfo);
				}
			}
			final String sequencenoquery = "select nsequenceno from SeqNoInstrumentManagement where stablename ='instrumentcategory' and nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus();
			int nsequenceno = jdbcTemplate.queryForObject(sequencenoquery, Integer.class);
			nsequenceno++;
			final String insertquery = "Insert into instrumentcategory (ninstrumentcatcode,ncalibrationreq,ncategorybasedflow,sinstrumentcatname,sdescription,ndefaultstatus,dmodifieddate,nsitecode,nstatus)"
					+ "values(" + nsequenceno + "," + instrumentCategory.getNcalibrationreq() + ","
					+ instrumentCategory.getNcategorybasedflow() + "," + " N'"
					+ stringUtilityFunction.replaceQuote(instrumentCategory.getSinstrumentcatname()) + "',N'"
					+ stringUtilityFunction.replaceQuote(instrumentCategory.getSdescription()) + "',"
					+ instrumentCategory.getNdefaultstatus() + ",'" + dateUtilityFunction.getCurrentDateTime(userInfo)
					+ "'," + userInfo.getNmastersitecode() + ","
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + ")";
			jdbcTemplate.execute(insertquery);

			final String updatequery = "update SeqNoInstrumentManagement set nsequenceno =" + nsequenceno
					+ " where stablename ='instrumentcategory'";
			jdbcTemplate.execute(updatequery);
			instrumentCategory.setNinstrumentcatcode(nsequenceno);

			final List<Object> savedManufacturerList = new ArrayList<>();
			savedManufacturerList.add(instrumentCategory);

			final String sequencenoquery1 = "select nsequenceno from SeqNoConfigurationMaster where stablename ='treetemplatemaster' and nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus();
			int nsequenceno1 = jdbcTemplate.queryForObject(sequencenoquery1, Integer.class);
			nsequenceno1++;
			final String insertquery1 = "Insert into treetemplatemaster (ntemplatecode,ncategorycode,sdescription,nrootcode,nformcode,dmodifieddate,nsitecode,nstatus)"
					+ "values(" + nsequenceno1 + "," + instrumentCategory.getNinstrumentcatcode() + ",N'root',"
					+ Enumeration.TransactionStatus.NA.gettransactionstatus() + "," + " " + userInfo.getNformcode()
					+ ",'" + dateUtilityFunction.getCurrentDateTime(userInfo) + "'," + userInfo.getNmastersitecode()
					+ "," + Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + ")";
			jdbcTemplate.execute(insertquery1);

			final String updatequery1 = "update SeqNoConfigurationMaster set nsequenceno =" + nsequenceno1
					+ " where stablename ='treetemplatemaster'";
			jdbcTemplate.execute(updatequery1);

			auditUtilityFunction.fnInsertAuditAction(savedManufacturerList, 1, null,
					Arrays.asList("IDS_ADDINSTRUMENTCATEGORY"), userInfo);

			return getInstrumentCategory(userInfo);
		} else {
			return new ResponseEntity<>(
					commonFunction.getMultilingualMessage(Enumeration.ReturnStatus.ALREADYEXISTS.getreturnstatus(),
							userInfo.getSlanguagefilename()),
					HttpStatus.CONFLICT);
		}
	}

	/**
	 * This method is used to fetch the active Instrument Category objects for the
	 * specified InstrumentCategory name and site.
	 * 
	 * @param sinstrumentcatname [String] Instrument Category for which the records
	 *                           are to be fetched
	 * @return list of active Instrument Category based on the specified Instrument
	 *         Category name and site
	 * @throws Exception that are thrown from this DAO layer
	 */

	private InstrumentCategory getInstrumentCategoryByName(final String sinstrumentcatname, final int nmastersitecode)
			throws Exception {
		final String strQuery = "select ninstrumentcatcode from instrumentcategory where sinstrumentcatname = N'"
				+ stringUtilityFunction.replaceQuote(sinstrumentcatname) + "' and nstatus = "
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and nsitecode=" + nmastersitecode;
		return (InstrumentCategory) jdbcUtilityFunction.queryForObject(strQuery, InstrumentCategory.class,
				jdbcTemplate);
	}

	/**
	 * This method is used to get a default Instrument Category object with respect
	 * to the site code
	 * 
	 * @param nmasterSiteCode [int] Site code
	 * @return a InstrumentCategory Object
	 * @throws Exception that are from DAO layer
	 */
	private InstrumentCategory getInstrumentCategoryByDefaultStatus(int nmasterSiteCode) throws Exception {
		final String strQuery = "select * from instrumentcategory u" + " where u.nstatus = "
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and u.ndefaultstatus="
				+ Enumeration.TransactionStatus.YES.gettransactionstatus() + " and u.nsitecode = " + nmasterSiteCode;
		return (InstrumentCategory) jdbcUtilityFunction.queryForObject(strQuery, InstrumentCategory.class,
				jdbcTemplate);
	}

	/**
	 * This method is used to update entry in Instrument Category table. Need to
	 * validate that the Instrument Category object to be updated is active before
	 * updating details in database. Need to check for duplicate entry of Instrument
	 * Category name for the specified site before saving into database.
	 * 
	 * @param UserInfo           object is used for fetched the list of active
	 *                           records based on site
	 * @param InstrumentCategory [Instrument Category] object holding details to be
	 *                           updated in Instrument Category table
	 * @return response entity object holding response status and data of updated
	 *         Instrument Category object
	 * @throws Exception that are thrown from this DAO layer
	 */
	@Override
	public ResponseEntity<Object> updateInstrumentCategory(final InstrumentCategory instrumentCategory,
			final UserInfo userInfo) throws Exception {
		final InstrumentCategory objinstrumentCategory = getActiveInstrumentCategoryById(
				instrumentCategory.getNinstrumentcatcode());
		final List<Object> listAfterUpdate = new ArrayList<>();
		final List<Object> listBeforeUpdate = new ArrayList<>();

		if (objinstrumentCategory == null) {
			return new ResponseEntity<>(
					commonFunction.getMultilingualMessage(Enumeration.ReturnStatus.ALREADYDELETED.getreturnstatus(),
							userInfo.getSlanguagefilename()),
					HttpStatus.EXPECTATION_FAILED);
		} else {
			final InstrumentCategory instrumentCategoryListByName = duplicateCheckingForInstrumentCategory(
					instrumentCategory.getSinstrumentcatname(), instrumentCategory.getNinstrumentcatcode(),
					userInfo.getNmastersitecode());

			if (instrumentCategoryListByName == null) {
				if (instrumentCategory.getNdefaultstatus() == Enumeration.TransactionStatus.YES
						.gettransactionstatus()) {
					final InstrumentCategory defaultInstrumentCategory = getInstrumentCategoryByDefaultStatus(
							instrumentCategory.getNsitecode());

					if (defaultInstrumentCategory != null && defaultInstrumentCategory
							.getNinstrumentcatcode() != instrumentCategory.getNinstrumentcatcode()) {

						final InstrumentCategory instrumentCategoryBeforeSave = SerializationUtils
								.clone(defaultInstrumentCategory);

						listBeforeUpdate.add(instrumentCategoryBeforeSave);

						defaultInstrumentCategory
								.setNdefaultstatus((short) Enumeration.TransactionStatus.NO.gettransactionstatus());

						final String updateQueryString = " update instrumentcategory set ndefaultstatus="
								+ Enumeration.TransactionStatus.NO.gettransactionstatus()
								+ " where ninstrumentcatcode =" + defaultInstrumentCategory.getNinstrumentcatcode();
						jdbcTemplate.execute(updateQueryString);

						listAfterUpdate.add(defaultInstrumentCategory);
						auditUtilityFunction.fnInsertAuditAction(listAfterUpdate, 2, listBeforeUpdate,
								Arrays.asList("IDS_EDITINSTRUMENTCATEGORY"), userInfo);
					}
				}
				final String updateQueryString = "update instrumentcategory set sinstrumentcatname=N'"
						+ stringUtilityFunction.replaceQuote(instrumentCategory.getSinstrumentcatname())
						+ "', sdescription =N'"
						+ stringUtilityFunction.replaceQuote(instrumentCategory.getSdescription())
						+ "', ndefaultstatus =" + instrumentCategory.getNdefaultstatus() + ", ncategorybasedflow ="
						+ instrumentCategory.getNcategorybasedflow() + ", ncalibrationreq ="
						+ instrumentCategory.getNcalibrationreq() + ",dmodifieddate='"
						+ dateUtilityFunction.getCurrentDateTime(userInfo) + "'" + " where ninstrumentcatcode ="
						+ instrumentCategory.getNinstrumentcatcode();

				jdbcTemplate.execute(updateQueryString);

				final List<Object> savedListAfterSave = new ArrayList<>();
				savedListAfterSave.add(instrumentCategory);

				final List<Object> savedListBeforeSave = new ArrayList<>();
				savedListBeforeSave.add(objinstrumentCategory);

				auditUtilityFunction.fnInsertAuditAction(savedListAfterSave, 2, savedListBeforeSave,
						Arrays.asList("IDS_EDITINSTRUMENTCATEGORY"), userInfo);

				return getInstrumentCategory(userInfo);
			} else {
				return new ResponseEntity<>(
						commonFunction.getMultilingualMessage(Enumeration.ReturnStatus.ALREADYEXISTS.getreturnstatus(),
								userInfo.getSlanguagefilename()),
						HttpStatus.CONFLICT);
			}
		}
	}

	/**
	 * This method is used to retrieve active Instrument Category object based on
	 * the specified ninstrumentcatcode.
	 * 
	 * @param UserInfo           object is used for fetched the list of active
	 *                           records based on site
	 * @param ninstrumentcatcode [int] primary key of Instrument Category object
	 * @return response entity object holding response status and data of Instrument
	 *         Category object
	 * @throws Exception that are thrown from this DAO layer
	 */
	@Override
	public InstrumentCategory getActiveInstrumentCategoryById(final int ninstrumentcatcode) throws Exception {
		final String strQuery = "select ic.* from instrumentcategory ic " + " where ic.nstatus = "
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and ic.ninstrumentcatcode = "
				+ ninstrumentcatcode;
		final InstrumentCategory objInstrumentCategory = (InstrumentCategory) jdbcUtilityFunction
				.queryForObject(strQuery, InstrumentCategory.class, jdbcTemplate);
		return objInstrumentCategory;
	}

	/**
	 * This method is used to check the duplicate entry in the table other than the
	 * current object.
	 * 
	 * @param ninstrumentcatcode [Int] from the object holding details to be updated
	 *                           in Instrument Category table
	 * @param sinstrumentcatname [String] is used for fetch the record.
	 * @return response entity object holding response status and data of updated
	 *         Instrument Category object
	 * @throws Exception that are thrown from this DAO layer
	 */
	private InstrumentCategory duplicateCheckingForInstrumentCategory(final String sinstrumentcatname,
			final int ninstrumentcatcode, final int nmasterSiteCode) throws Exception {
		final String strQuery = "select ninstrumentcatcode from instrumentcategory where sinstrumentcatname = N'"
				+ stringUtilityFunction.replaceQuote(sinstrumentcatname) + "' and nstatus = "
				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and ninstrumentcatcode <> "
				+ ninstrumentcatcode + " and nsitecode=" + nmasterSiteCode;
		return (InstrumentCategory) jdbcUtilityFunction.queryForObject(strQuery, InstrumentCategory.class,
				jdbcTemplate);
	}

	/**
	 * This method is used to delete entry in InstrumentCategory table. Need to
	 * validate that the specified InstrumentCategory object is active and is not
	 * associated with any of its child tables before updating its nstatus to -1.
	 * 
	 * @param UserInfo           object is used for fetched the list of active
	 *                           records based on site
	 * @param InstrumentCategory [InstrumentCategory] object holding detail to be
	 *                           deleted in InstrumentCategory table
	 * @return response entity object holding response status and data of deleted
	 *         InstrumentCategory object
	 * @throws Exception that are thrown in the DAO layer
	 */
	@Override
	public ResponseEntity<Object> deleteInstrumentCategory(final InstrumentCategory instrumentCategory,
			final UserInfo userInfo) throws Exception {
		final InstrumentCategory objInstrumentCategory = getActiveInstrumentCategoryById(
				instrumentCategory.getNinstrumentcatcode());
		if (objInstrumentCategory == null) {
			return new ResponseEntity<>(
					commonFunction.getMultilingualMessage(Enumeration.ReturnStatus.ALREADYDELETED.getreturnstatus(),
							userInfo.getSlanguagefilename()),
					HttpStatus.EXPECTATION_FAILED);
		} else {
			final String query = "select 'IDS_INSTRUMENT' as Msg from instrument where ninstrumentcatcode= "
					+ instrumentCategory.getNinstrumentcatcode() + " and nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " union all"
					+ " select 'IDS_TESTGROUP' as Msg from testgrouptest where  ninstrumentcatcode= "
					+ instrumentCategory.getNinstrumentcatcode() + " and nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " union all"
					+ " select 'IDS_TESTMASTER' as Msg from testinstrumentcategory where  ninstrumentcatcode= "
					+ instrumentCategory.getNinstrumentcatcode() + "  and nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " union all"
					+ " select 'IDS_STUDYPLANTEMPLATE' as Msg from treetemplatemaster tt,treeversiontemplate tv"
					+ " where  tt.ncategorycode= " + instrumentCategory.getNinstrumentcatcode() + " and tt.nformcode ="
					+ userInfo.getNformcode() + " and  tt.ntemplatecode=tv.ntemplatecode  and tt.nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and tv.nstatus="
					+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus();
			valiDatorDel = projectDAOSupport.getTransactionInfo(query, userInfo);
			boolean validRecord = false;
			if (valiDatorDel.getNreturnstatus() == Enumeration.Deletevalidator.SUCCESS.getReturnvalue()) {
				validRecord = true;
				valiDatorDel = projectDAOSupport
						.validateDeleteRecord(Integer.toString(instrumentCategory.getNinstrumentcatcode()), userInfo);
				if (valiDatorDel.getNreturnstatus() == Enumeration.Deletevalidator.SUCCESS.getReturnvalue()) {
					validRecord = true;
				} else {
					validRecord = false;
				}
			}

			if (validRecord) {
				final List<Object> savedinstrumentCategoryList = new ArrayList<>();
				final String updateQueryString = "update InstrumentCategory set dmodifieddate='"
						+ dateUtilityFunction.getCurrentDateTime(userInfo) + "', nstatus = "
						+ Enumeration.TransactionStatus.DELETED.gettransactionstatus() + " where ninstrumentcatcode ="
						+ instrumentCategory.getNinstrumentcatcode();

				jdbcTemplate.execute(updateQueryString);

				instrumentCategory.setNstatus((short) Enumeration.TransactionStatus.DELETED.gettransactionstatus());

				savedinstrumentCategoryList.add(instrumentCategory);

				auditUtilityFunction.fnInsertAuditAction(savedinstrumentCategoryList, 1, null,
						Arrays.asList("IDS_DELETEINSTRUMENTCATEGORY"), userInfo);
				return getInstrumentCategory(userInfo);
			} else {
				return new ResponseEntity<>(valiDatorDel.getSreturnmessage(), HttpStatus.EXPECTATION_FAILED);
			}
		}
	}

	/**
	 * This method is used to get the Interfacetype from Interfacetype table
	 * 
	 * @param userInfo object is used for fetched the list of active records based
	 *                 on site
	 * @return response entity object holding response status and data of deleted
	 *         InstrumentCategory object
	 * @throws Exception that are thrown in the DAO layer
	 */
//	public ResponseEntity<Object> getInterfacetype(UserInfo userInfo) throws Exception {
//		String strQuery = "select ninterfacetypecode,coalesce(jsondata->'sinterfacetypename'->>'"+userInfo.getSlanguagetypecode()+"',	jsondata->'sinterfacetypename'->>'en-US') as sinterfacetypename from interfacetype"
//				+ " where ninterfacetypecode>0 and nstatus="
//				+ Enumeration.TransactionStatus.ACTIVE.gettransactionstatus() + " and nsitecode="
//				+ userInfo.getNmastersitecode();
//		return new ResponseEntity<Object>(
//				(List<InstrumentCategory>) jdbcTemplate.query(strQuery, new InstrumentCategory()), HttpStatus.OK);
//	}

}
