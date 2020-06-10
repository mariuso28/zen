package org.zen.json;

public class PaymentMethodJson {
	private int id;
	private String method;
	private String country;
	
	
	public PaymentMethodJson()
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
