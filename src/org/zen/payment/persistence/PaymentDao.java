package org.zen.payment.persistence;

import java.util.List;

import org.zen.payment.PaymentMethod;
import org.zen.payment.PunterPaymentMethod;

public interface PaymentDao {
	
	public List<PaymentMethod> getAvailablePaymentMethods();
	public void storePaymentMethod(PaymentMethod pm);
	public void storePunterPaymentMethod(PunterPaymentMethod ppm);
	

}
