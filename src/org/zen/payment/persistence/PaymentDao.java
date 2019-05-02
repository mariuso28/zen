package org.zen.payment.persistence;

import java.util.List;

import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterPaymentMethodJson;

public interface PaymentDao {
	
	public List<PaymentMethodJson> getAvailablePaymentMethods();
	public void storePaymentMethod(PaymentMethodJson pm);
	public void storePunterPaymentMethod(PunterPaymentMethodJson ppm);
	public PaymentMethodJson getPaymentMethodById(int id);
	public void deletePunterPaymentMethodById(long id);

}
