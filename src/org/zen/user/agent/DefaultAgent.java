package org.zen.user.agent;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultAgent{

	public static Agent get()
	{
		Agent a = new Agent();
		a.setContact("Zen Agent");
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		a.setPassword(encoder.encode("8888888"));
		a.setEmail("zen@test.com");
		return a;
	}
}
