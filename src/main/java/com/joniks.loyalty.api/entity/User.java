package com.joniks.loyalty.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.joniks.loyalty.api.utility.JSONConverter;
import com.joniks.loyalty.api.utility.SessionUtility;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String username;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@NotNull
	private String password;

	@Column(name = "role_id")
	private int roleId;

	@Column(name = "created_by")
	private int createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "modified_by")
	private int modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	private boolean deleted;

	private boolean uploaded;

	private transient String modifiedByUser;
	private transient String createdByUser;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setInfo(User user) {

		this.email = user.getEmail();

		this.firstName = user.getFirstName();

		this.lastName = user.getLastName();

		this.password = user.getPassword();

		this.roleId = user.getRoleId();

		this.username = user.getUsername();
	}

	public int getModifiedBy() {
		if (SessionUtility.getUser(this.modifiedBy) != null) {
			User user = SessionUtility.getUser(this.modifiedBy);
			this.modifiedByUser = user.getFirstName() + " " + user.getLastName();
		}
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedByUser() {
		return modifiedByUser;
	}

	public void setModifiedByUser(String modifiedByUser) {
		this.modifiedByUser = modifiedByUser;
	}

	public String getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
	}

	public int getCreatedBy() {
		if (SessionUtility.getUser(this.createdBy) != null) {
			User user = SessionUtility.getUser(this.createdBy);
			this.createdByUser = user.getFirstName() + " " + user.getLastName();
		}
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void update(User user) {
		if (!StringUtils.isEmpty(user.getFirstName()) && !user.getFirstName().equals(this.firstName))
			this.firstName = user.getFirstName();
		if (!StringUtils.isEmpty(user.getLastName()) && !user.getLastName().equals(this.lastName))
			this.lastName = user.getLastName();
		if (!StringUtils.isEmpty(user.getEmail()) && !user.getEmail().equals(this.email))
			this.email = user.getEmail();
		if (!StringUtils.isEmpty(user.getPassword()))
			this.password = UUID.nameUUIDFromBytes((user.getPassword()).getBytes()).toString();
		if (user.getRoleId() != this.roleId)
			this.roleId = user.getRoleId();
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean getUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	@Override
	public String toString() {

		return "[User: " + JSONConverter.convertObjectToJson(this) + "]";
	}
}