package org.zen.json;

import java.util.UUID;

public class PunterPaymentMethodJson extends PaymentMethodJson {

	private UUID punterId;
	private String method;
	private String country;
	private String accountNum;
	private boolean activated;
	
	public PunterPaymentMethodJson()
	{
	}

	public PunterPaymentMethodJson(PaymentMethodJson pm)
	{
		setMethod(pm.getMethod());
		setCountry(pm.getCountry());
	}
	
	public UUID getPunterId() {
		return punterId;
	}

	public void setPunterId(UUID punterId) {
		this.punterId = punterId;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
	
	
}
