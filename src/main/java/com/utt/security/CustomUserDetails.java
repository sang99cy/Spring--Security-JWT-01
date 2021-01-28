package com.utt.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;

import com.utt.model.User;



public class CustomUserDetails implements UserDetails {
	public CustomUserDetails(User user) {
		this.user=user;
	}

	User user=new User();
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Mặc định mình sẽ để tất cả là ROLE_USER. Để demo cho đơn giản.
		return java.util.Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
   
}
