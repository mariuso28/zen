package org.zen.payment;

import java.util.UUID;

public class PunterPaymentMethod extends PaymentMethod {

	private UUID punterId;
	private String accountNum;
	
	public PunterPaymentMethod()
	{
	}

	public PunterPaymentMethod(PaymentMethod pm)
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
	
	
	
}
