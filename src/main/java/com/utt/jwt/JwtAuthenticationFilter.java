package com.utt.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utt.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// Lấy jwt từ request
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				// lấy name user từ chuỗi jwt
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				// Lấy thông tin người dùng từ name
				UserDetails userDetails = userService.loadUserById(userId);
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			}

		} catch (Exception e) {
			e.getStackTrace();
		}
        filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if(org.springframework.util.StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7,headerAuth.length());
		}
		return null;
	}
}
