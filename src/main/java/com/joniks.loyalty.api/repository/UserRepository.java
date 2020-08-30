package com.joniks.lotalty.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.joniks.lotalty.api.entity.User;
import com.joniks.lotalty.api.repository.custom.UserRepositoryCustom;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
	public User findOneByUsernameAndPasswordAndDeleted(String username, String password, boolean deleted);

	public User findOneByUsernameAndDeleted(String username, boolean deleted);

	public User findOneByIdAndDeleted(Integer userId, boolean deleted);

	@Transactional
	@Modifying
	@Query("UPDATE User us SET us.uploaded = false, us.deleted=true WHERE us.id=?1")
	public void deleteById(int id);
}
