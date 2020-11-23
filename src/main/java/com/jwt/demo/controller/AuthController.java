package com.jwt.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.demo.filter.JwtUtils;
import com.jwt.demo.model.ERole;
import com.jwt.demo.model.JwtResponse;
import com.jwt.demo.model.MessageResponse;
import com.jwt.demo.model.Role;
import com.jwt.demo.model.User;
import com.jwt.demo.repository.RoleRepository;
import com.jwt.demo.repository.UserRepository;
import com.jwt.demo.req.model.LoginRequest;
import com.jwt.demo.req.model.SignupRequest;
import com.jwt.demo.service.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	
	 // @Autowired CustomAuthenticationProvider customAuthenticationProvider;
	 

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
		String jwt = null;
		User loginUser = userRepository.findByMobile(loginRequest.getMobile());
		if(loginUser!=null) {
			if(loginUser.getOtp()==loginRequest.getOtp()) {
				jwt = jwtUtils.generateJwtToken(loginRequest.getMobile());
				if(jwt!=null) {
					Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getMobile(),loginRequest.getOtp()) ) ;
					  SecurityContextHolder.getContext().setAuthentication(authentication);
					  UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
					  List<String> roles = userDetails.getAuthorities().stream() .map(item ->
					  item.getAuthority()) .collect(Collectors.toList());
					  return ResponseEntity.ok(new JwtResponse(jwt,loginUser.getId(),loginUser.getMobile(),loginUser.getOtp()));
				} else {				
					return ResponseEntity.ok(new MessageResponse("No Token generated"));
				}					  		
			}else {		
				return ResponseEntity.ok(new MessageResponse("Invaled otp "));
			}
		}else {
			return ResponseEntity.ok(new MessageResponse("mobile nn not found"));
		}
	}
			
		
	

	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByMobile(signUpRequest.getMobile())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: mobile is already taken!"));
		}

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		int generateOtpNumber = generateOtpNumber();
		// Create new user's account
		User user = new User(signUpRequest.getMobile(), generateOtpNumber);

		if (strRoles == null) {
			
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.TRADER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully! and the OTP "+generateOtpNumber));
	}

	private int generateOtpNumber() {
		 int otp = 0;
         Random random = new Random();
         Integer generatedOTP = (Integer) 1000 + random.nextInt(9000);
         otp=new Integer(String.valueOf(generatedOTP));
         return otp;
	}
}