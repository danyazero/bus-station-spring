package com.zero.springweb.repository;

import com.zero.springweb.entities.User;
import com.zero.springweb.model.UserInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> getFirstByPhone(String phoneIs);

  @Cacheable(value = "user", unless = "#result == null")
  Optional<UserInfo> findUserByIdEquals(int userId);
}