package com.utt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.utt.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// Kiểm tra xem user có tồn tại trong database không?

    @Query("SELECT u FROM User u WHERE u.username = :username")
	  User findByUsername(@RequestParam("username") String username);
}
