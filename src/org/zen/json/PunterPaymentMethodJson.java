package org.zen.json;

import java.util.UUID;

public class PunterPaymentMethodJson {

	private UUID punterId;
	private String method;
	private String country;
	private String accountNum;
	
	public PunterPaymentMethodJson()
	{
	}

	public UUID getPunterId() {
		return punterId;
	}

	public void setPunterId(UUID punterId) {
		this.punterId = punterId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	
}
