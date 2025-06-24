package com.agaramtech.qualis.login.service;

import java.util.Map;

/**
 * This interface holds the method declaration of login operation
 * @author ATE142
 * @version 9.0.0.1
 * @since 23-06-2020
 */

import org.springframework.http.ResponseEntity;

import com.agaramtech.qualis.configuration.model.Language;
import com.agaramtech.qualis.credential.model.UsersSite;
import com.agaramtech.qualis.global.UserInfo;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
	
	public ResponseEntity<Object> getLoginInfo() throws Exception;
	public ResponseEntity<Object> getLoginValidation(final String sloginid, final Language language, final int nlogintypecode) throws Exception;
	public ResponseEntity<Object> collectLoginData(Map<String, Object> objinputmap, HttpServletRequest request)throws Exception;
	public ResponseEntity<Object> collectAdsLoginData(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> createNewPassword(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> changePassword(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> getUserScreenRightsMenu(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> getChangeRole(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> validateEsignCredential(final UserInfo userInfo, final String password)throws Exception;
	public ResponseEntity<Object> getUserRoleScreenControl(Map<String, Object> objinputmap) throws Exception;
	public ResponseEntity<Object> getPassWordPolicy(final int nuserrolecode) throws Exception;
	public ResponseEntity<Object> idleTimeAuditAction(final UserInfo userInfo, final String password,final boolean flag, final int nFlag) throws Exception;
	public ResponseEntity<Object> insertAuditAction(final UserInfo userInfo, final String sauditaction,	final String scomments, final int nFlag) throws Exception;
	public ResponseEntity<Object> changeOwner(Map<String, Object> inputMap) throws Exception;
	public ResponseEntity<Object> validateEsignADSCredential(final UserInfo userInfo, final String password)throws Exception;
	public ResponseEntity<Object> changeSite(final UsersSite usersSite) throws Exception;
	public ResponseEntity<Object> getLoginTypeValidation(final String sloginid, final Language language,final int nusermultisitecode, final int nusermultirolecode, final int nuserrolecode) throws Exception;
	public ResponseEntity<Object> getAboutInfo(final UserInfo userInfo) throws Exception;
	public ResponseEntity<Object> validateADSPassword(Map<String, Object> objinputmap) throws Exception;
	 //Gowtham R - ALPD-5190 -- Vacuum Analysis
	public ResponseEntity<Object> getJavaTime() throws Exception;

}
