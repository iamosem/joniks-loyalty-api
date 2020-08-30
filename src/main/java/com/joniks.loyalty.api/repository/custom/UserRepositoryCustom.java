package com.joniks.loyalty.api.repository.custom;

import java.util.List;

import com.joniks.loyalty.api.entity.User;

public interface UserRepositoryCustom {

	public List<User> listUsers(User searchFilter);

	public Long countUsers(User searchFilter);
}
