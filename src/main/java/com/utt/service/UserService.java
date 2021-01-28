package com.utt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utt.model.User;
import com.utt.repositories.UserRepository;
import com.utt.security.CustomUserDetails;
@Service
public class UserService implements UserDetailsService {
	
	 @Autowired
	 private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Kiểm tra xem user có tồn tại trong database không?
		User user=userRepository.findByUsername(username);
		if(user==null) {
			 throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetails(user);
	}
	// JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }

}
