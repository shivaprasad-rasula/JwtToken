package com.jwt.demo.model;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String mobile;
	private int otp;
	private List<String> roles;

	
	public JwtResponse(String token, Long id, String mobile, int otp, List<String> roles) {
		super();
		this.token = token;
		this.id = id;
		this.mobile = mobile;
		this.otp = otp;
		this.roles = roles;
	}
	
	public JwtResponse(String token, Long id, String mobile, int otp) {
		super();
		this.token = token;
		this.id = id;
		this.mobile = mobile;
		this.otp = otp;
	}

	



	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getRoles() {
		return roles;
	}
}
