package org.zen.payment;

public class PaymentMethod {
	private String method;
	private String country;
	
	
	public PaymentMethod()
	{
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

	@Override
	public String toString() {
		return "PaymentMethod [method=" + method + ", country=" + country + "]";
	}

	
}
