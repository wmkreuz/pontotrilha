package br.com.pontotrilha.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pontotrilha.data.vo.v1.security.AccountCredentialsVO;
import br.com.pontotrilha.data.vo.v1.security.TokenVO;
import br.com.pontotrilha.exceptions.RequiredObjectIsNullException;
import br.com.pontotrilha.exceptions.UserAlreadyExistsException;
import br.com.pontotrilha.model.User;
import br.com.pontotrilha.model.UserPermission;
import br.com.pontotrilha.repositories.UserPermissionRepository;
import br.com.pontotrilha.repositories.UserRepository;
import br.com.pontotrilha.security.jwt.JwtTokenProvider;
import br.com.pontotrilha.util.EncryptPassword;

@Service
public class AuthServices {

	private Logger logger = Logger.getLogger(AuthServices.class.getName());

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

	@SuppressWarnings("rawtypes")
	public ResponseEntity signup(User user) {
		if (user == null)
			throw new RequiredObjectIsNullException();

		var userRet = repository.findByUsername(user.getUserName());

		var userSave = new User();

		if (userRet != null) {
			throw new UserAlreadyExistsException("Username " + userRet.getUserName() + " already exists!");
		} else {

			if (user.getUserName() != null && user.getPassword() != null) {
				logger.info("Creating one user!");

				var password = user.getPassword();

				EncryptPassword encryptPassword = new EncryptPassword();

				user.setPassword(encryptPassword.encrypt(password));
				user.setEnabled(true);
				user.setAccountNonExpired(true);
				user.setAccountNonLocked(true);
				user.setCredentialsNonExpired(true);
				repository.save(user);

				userSave = repository.findByUsername(user.getUserName());

				UserPermission userPermission = new UserPermission();

				userPermission.setIdUser(userSave.getId());
				userPermission.setIdPermission(3L);

				userPermissionRepository.save(userPermission);
			}

		}
		return ResponseEntity.ok(userSave);

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