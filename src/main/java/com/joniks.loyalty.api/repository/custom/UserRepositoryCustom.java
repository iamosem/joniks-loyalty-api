package com.joniks.lotalty.api.repository.custom;

import java.util.List;

import com.joniks.lotalty.api.entity.User;

public interface UserRepositoryCustom {

	public List<User> listUsers(User searchFilter);

	public Long countUsers(User searchFilter);
}
