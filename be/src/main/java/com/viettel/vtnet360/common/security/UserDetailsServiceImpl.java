package com.viettel.vtnet360.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetailsServiceImpl() {
    }

    /** FOR LATE */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	// TODO: Implement Query user name and password for spring security or modifier spring security . . . PLS Do it soon as possible
    	throw new UsernameNotFoundException("Please Integration With VSA for Refresh Token");
    }
}