package com.agaramtech.qualis.audittrail.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import com.agaramtech.qualis.global.CustomizedResultsetRowMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.agaramtech.qualis.global.Enumeration;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.jdbc.core.RowMapper;

@Entity
@Data
@Table(name = "sessiondetails")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SessionDetails extends CustomizedResultsetRowMapper<SessionDetails> 
implements Serializable, RowMapper<SessionDetails> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "nsessiondetailscode")
	private int nsessiondetailscode;
	
	@Column(name = "ssessionid", length = 100, nullable = false)
	private String ssessionid;
	
	@Column(name = "susername", length = 100, nullable = false)
	private String susername;
	
	@Column(name = "shostip", length = 100, nullable = false)
	private String shostip;
	
	@Column(name = "nusercode", nullable = false)
	private int nusercode;
	
	@Column(name = "dlogindate")
	private Date dlogindate;
	
	@Column(name = "dlogoutdate")
	private Date dlogoutdate;
	
	@Column(name = "nuserrolecode", nullable = false)
	private int nuserrolecode;
	
	@Column(name = "ntransactionstatus", nullable = false)
	private short ntransactionstatus;
	
	@Column(name = "nsitecode", nullable = false)
	@ColumnDefault("-1")
	private short nsitecode = (short)Enumeration.TransactionStatus.NA.gettransactionstatus();	
	
	@Column(name = "nstatus", nullable = false)
	@ColumnDefault("1")
	private short nstatus = (short)Enumeration.TransactionStatus.ACTIVE.gettransactionstatus();	
	
	@Column(name = "dmodifieddate", nullable = false)
	private Instant dmodifieddate;

	@Override
	public SessionDetails mapRow(ResultSet arg0, int arg1) throws SQLException {
		final SessionDetails objSessionDetail = new SessionDetails();
		objSessionDetail.setNsessiondetailscode(getInteger(arg0, "nsessiondetailscode", arg1));
		objSessionDetail.setSsessionid(getString(arg0, "ssessionid", arg1));
		objSessionDetail.setSusername(getString(arg0, "susername", arg1));
		objSessionDetail.setShostip(getString(arg0, "shostip", arg1));
		objSessionDetail.setNusercode(getInteger(arg0, "nusercode", arg1));
		objSessionDetail.setDlogindate(getDate(arg0, "dlogindate", arg1));
		objSessionDetail.setDlogindate(getDate(arg0, "dlogoutdate", arg1));
		objSessionDetail.setNusercode(getInteger(arg0, "nuserrolecode", arg1));
		objSessionDetail.setNtransactionstatus(getShort(arg0, "ntransactionstatus", arg1));
		objSessionDetail.setNsitecode(getShort(arg0, "nsitecode", arg1));
		objSessionDetail.setNstatus(getShort(arg0, "nstatus", arg1));
		objSessionDetail.setDmodifieddate(getInstant(arg0, "dmodifieddate", arg1));
		return objSessionDetail;
	}

}
