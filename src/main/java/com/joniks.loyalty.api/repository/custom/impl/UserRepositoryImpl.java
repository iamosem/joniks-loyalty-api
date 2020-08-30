package com.joniks.loyalty.api.repository.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.joniks.loyalty.api.entity.User;
import com.joniks.loyalty.api.repository.custom.UserRepositoryCustom;
import com.joniks.loyalty.api.utility.StringUtility;

@Repository("userRepositoryCustom")
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<User> listUsers(User searchFilter) {
		StringBuilder sqlQuery = new StringBuilder("SELECT  * FROM user user ");
		sqlQuery.append(" WHERE user.deleted=0 ");
		if (!StringUtility.isStringNullOrEmpty(searchFilter.getUsername())) {
			sqlQuery.append(" AND user.username LIKE :username ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getFirstName())) {
			sqlQuery.append(" AND user.firstName LIKE :firstName ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getLastName())) {
			sqlQuery.append(" AND user.lastName LIKE :lastName ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getEmail())) {
			sqlQuery.append(" AND user.email LIKE :email ");
		}

		if (searchFilter.getRoleId() != 0) {
			sqlQuery.append(" AND user.role = :role ");
		}

		sqlQuery.append(" ORDER by user.created_date DESC, user.username ASC ");
		Query query = entityManager.createNativeQuery(sqlQuery.toString(), User.class);

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getUsername())) {
			query.setParameter("username", "%" + searchFilter.getUsername() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getFirstName())) {
			query.setParameter("firstName", "%" + searchFilter.getFirstName() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getLastName())) {
			query.setParameter("lastName", "%" + searchFilter.getLastName() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getEmail())) {
			query.setParameter("email", "%" + searchFilter.getEmail() + "%");
		}

		if (searchFilter.getRoleId() != 0) {
			query.setParameter("role", searchFilter.getRoleId());
		}

		return query.getResultList();
	}

	@Override
	public Long countUsers(User searchFilter) {
		StringBuilder sqlQuery = new StringBuilder("SELECT  count(*) AS userCount FROM user user ");
		sqlQuery.append(" WHERE user.deleted=0 ");
		if (!StringUtility.isStringNullOrEmpty(searchFilter.getUsername())) {
			sqlQuery.append(" WHERE user.username LIKE :username ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getFirstName())) {
			sqlQuery.append(" AND user.firstName LIKE :firstName ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getLastName())) {
			sqlQuery.append(" AND user.lastName LIKE :lastName ");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getEmail())) {
			sqlQuery.append(" AND user.email LIKE :email ");
		}

		if (searchFilter.getRoleId() != 0) {
			sqlQuery.append(" AND user.role = :role ");
		}

		Query query = entityManager.createNativeQuery(sqlQuery.toString());

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getUsername())) {
			query.setParameter("username", "%" + searchFilter.getUsername() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getFirstName())) {
			query.setParameter("firstName", "%" + searchFilter.getFirstName() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getLastName())) {
			query.setParameter("lastName", "%" + searchFilter.getLastName() + "%");
		}

		if (!StringUtility.isStringNullOrEmpty(searchFilter.getEmail())) {
			query.setParameter("email", "%" + searchFilter.getEmail() + "%");
		}

		if (searchFilter.getRoleId() != 0) {
			query.setParameter("role", searchFilter.getRoleId());
		}

		query.unwrap(SQLQuery.class).addScalar("userCount", LongType.INSTANCE);

		return (Long) query.getSingleResult();
	}
}
