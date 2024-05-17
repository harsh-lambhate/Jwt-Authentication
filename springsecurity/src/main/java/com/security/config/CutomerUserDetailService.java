package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.security.entity.User;
import com.security.repo.UserRepository;

public class CutomerUserDetailService implements UserDetailsService {

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(user.getUserName())
				.password(passwordEncoder.encode(user.getPassword()))
				.roles(user.getRole())
				.build();

		return userDetails;
	}

}
