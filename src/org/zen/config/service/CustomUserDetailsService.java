package org.zen.config.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zen.services.Services;
import org.zen.user.BaseUser;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private static final Logger log = Logger.getLogger(CustomUserDetailsService.class);
	@Autowired
	private Services services;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername username : " + username);
		
		BaseUser baseUser = null;
		try
		{
			baseUser = services.getBaseUser(username);
		}
		catch (Exception e)
		{
			log.error("Error finding User: " + username + " not found");
			throw new UsernameNotFoundException("Error finding User: " + username);
		}
		
		log.info("User : " + baseUser.getContact() + " found with role :" + baseUser.getRole() + " enabled : " + baseUser.isEnabled());
		
		Collection<GrantedAuthority> authorities = getAuthorities(baseUser);
		
		User user = new User(baseUser.getContact(), baseUser.getPassword(), baseUser.isEnabled(), true, true, true, authorities);
		
		log.info("Using User : " + user.getUsername() + " with authorities :" + authorities);
		return user;
	
	}
	
	private Collection<GrantedAuthority> getAuthorities(BaseUser baseUser) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		authList.add(new SimpleGrantedAuthority(baseUser.getRole()));

		return authList;
	}
	
}
