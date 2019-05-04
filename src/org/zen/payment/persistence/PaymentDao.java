package org.zen.payment.persistence;

import java.util.List;

import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.payment.Xtransaction;
import org.zen.user.punter.upgrade.PaymentStatus;

public interface PaymentDao {
	
	public List<PaymentMethodJson> getAvailablePaymentMethods();
	public void storePaymentMethod(PaymentMethodJson pm);
	public void storePunterPaymentMethod(PunterPaymentMethodJson ppm);
	public PaymentMethodJson getPaymentMethodById(int id);
	public void deletePunterPaymentMethodById(long id);
	public void storeXtransaction(Xtransaction xt);
	public List<Xtransaction> getXtransactionsForMember(String memberType, String contact, PaymentStatus paymentStatus,
			long offset, long limit);

}
