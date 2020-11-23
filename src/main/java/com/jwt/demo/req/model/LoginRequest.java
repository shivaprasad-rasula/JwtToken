package com.jwt.demo.req.model;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String mobile;

	@NotBlank
	private int otp;

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



	

	
}