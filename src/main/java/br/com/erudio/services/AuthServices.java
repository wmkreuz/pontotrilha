package br.com.erudio.services;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;

import br.com.erudio.config.SecurityConfig;
import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.model.Permission;
import br.com.erudio.model.User;
import br.com.erudio.model.UserPermission;
import br.com.erudio.repositories.UserPermissionRepository;
import br.com.erudio.repositories.UserRepository;
import br.com.erudio.security.jwt.JwtTokenProvider;
import br.com.erudio.util.EncryptPassword;

@Service
public class AuthServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private UserPermissionRepository userPermissionRepository;

	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByUsername(username);
			
			var tokenResponse = new TokenVO();
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

	public void signup(User user) {
        if (user == null) throw new RequiredObjectIsNullException();

		var userRet = repository.findByUsername(user.getUserName());

		if(userRet != null){
			System.out.println("User with username '" + userRet.getUserName() + "' already exists");
		} else {
			
			if (user.getUserName() != null && user.getPassword() != null) {
				logger.info("Creating one user!");

				var password = user.getPassword();

				System.out.println(password);

				EncryptPassword encryptPassword = new EncryptPassword();

				System.out.println(encryptPassword.encrypt(password));
        	
				user.setPassword(encryptPassword.encrypt(password));
				user.setEnabled(true);
    			user.setAccountNonExpired(true);
    			user.setAccountNonLocked(true);
    			user.setCredentialsNonExpired(true);
				repository.save(user);

				var userSave = repository.findByUsername(user.getUserName());

				UserPermission userPermission = new UserPermission();

				userPermission.setIdUser(userSave.getId());
				userPermission.setIdPermission(3L);
        	
				userPermissionRepository.save(userPermission);
			}
		
		}
		
    }
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		var user = repository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found!");
		}
		return ResponseEntity.ok(tokenResponse);
	}
	
}
