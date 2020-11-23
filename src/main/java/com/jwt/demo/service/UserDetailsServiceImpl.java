package com.jwt.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.demo.model.User;
import com.jwt.demo.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		System.out.println("*****************"+mobile);
		User user = userRepository.findByMobile(mobile);
	System.out.println(user.getMobile()+"*****user******"+user.getOtp());
		return UserDetailsImpl.build(user);
	}
}