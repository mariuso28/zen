package org.zen.rest.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.multipart.MultipartFile;
import org.zen.json.AccountJson;
import org.zen.json.ActionJson;
import org.zen.json.ChangePasswordJson;
import org.zen.json.ModelJson;
import org.zen.json.PaymentInfoJson;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterJson;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.json.PunterProfileJson;
import org.zen.json.UpgradeJson;
import org.zen.json.XactionJson;
import org.zen.payment.PaymentInfo;
import org.zen.payment.Xtransaction;
import org.zen.rating.RatingMgr;
import org.zen.services.Services;
import org.zen.user.account.Account;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;
import org.zen.user.punter.mgr.PunterMgrException;
import org.zen.user.punter.mgr.PunterMgrValidationException;
import org.zen.user.punter.upgrade.PaymentStatus;
import org.zen.user.punter.upgrade.UpgradeStatus;

public class RestServices {
private static final Logger log = Logger.getLogger(RestServices.class);
	
	@Autowired
	private Services services;
	@Autowired
	private PunterMgr punterMgr;
	private RatingMgr ratingMgr;
	
	public RestServices()
	{
		ratingMgr = new RatingMgr();
	}
	

	public String resetPassword(String username) {
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(username);
			if (punter == null)
				throw new RestServicesException("Zen Member : " + username + " not found - please check");
			return punterMgr.resetPassword(punter);
		}
		catch (Exception e)
		{
			log.error("resetPassword",e);
			throw new RestServicesException("Zen Member : " + username + " not found - please check");
		}
	}

	
	public void approvePayment(String contact,String paymentId) {
		
		Punter sponsor = getPunter(contact);
		try {
			long idL = Long.parseLong(paymentId);
			Xtransaction xt = services.getHome().getPaymentDao().getXtransactionById(idL);
			Punter punter = getPunter(xt.getPayerContact());
			UpgradeStatus us = punter.getUpgradeStatus();
			us.setChanged((new GregorianCalendar()).getTime());
			us.setPaymentStatus(PaymentStatus.PAYMENTSUCCESS);
			punter.setRating(us.getNewRating());
			xt.setPaymentStatus(PaymentStatus.PAYMENTSUCCESS);
			services.updatePayment(xt,sponsor, punter);
			
			log.info("Payment approved for : " + contact);
			services.getMailNotifier().notifyUpgradeSuccessful(punter);
			
			// SET NEW UPGRADESTATUS for upstream
			punterMgr.tryUpgrade(sponsor);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not approve payment for id - contact support.");
		}
	}

	public void rejectPayment(String contact,String paymentId) {
		Punter sponsor = getPunter(contact);
		try {
			long idL = Long.parseLong(paymentId);
			Xtransaction xt = services.getHome().getPaymentDao().getXtransactionById(idL);
			Punter punter = getPunter(xt.getPayerContact());
			UpgradeStatus us = punter.getUpgradeStatus();
			us.setChanged((new GregorianCalendar()).getTime());
			us.setPaymentStatus(PaymentStatus.PAYMENTFAIL);
			punter.setRating(us.getNewRating());
			xt.setPaymentStatus(PaymentStatus.PAYMENTFAIL);
			services.updatePayment(xt,sponsor, punter);
			log.info("Payment rejected for : " + contact);
			services.getMailNotifier().notifyUpgradeFailed(punter,sponsor);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not approve payment for id - contact support.");
		}
	}
	
	public void submitTransactionDetails(String contact, MultipartFile uploadfile, String transactionDate,
			String transactionDetails) {
		
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Date td = null;
			try
			{
				td = sdf.parse(transactionDate);
			}
			catch (ParseException e)
			{
				String msg = "Invalid transaction date : " + transactionDate + " should be mm-dd-yyyy format.";
				log.info(msg);
				throw new RestServicesException(msg);
			}
			Punter punter = getPunter(contact);
			PaymentInfo pi = new PaymentInfo();
			pi.setTransactionDate(td);
			if (uploadfile.isEmpty())
			{
				log.info("Upload file is empty using transaction details");
				if (transactionDetails.isEmpty())
				{
					String msg = "Invalid transaction - details or upload file must be submitted.";
					log.info(msg);
					throw new RestServicesException(msg);
				}
				pi.setTransactionDetails(transactionDetails);
			}
			else
			{
				pi.setTransactionDetails("");
				try {
					pi.setUploadFileBytes(uploadfile.getBytes());
				} catch (IOException e) {
					log.error(e.getMessage(),e);
					String msg = "Invalid upload file - please try another.";
					log.info(msg);
					throw new RestServicesException(msg);
				}
			}
			Xtransaction xt = new Xtransaction();
			try {
				UpgradeStatus us = punter.getUpgradeStatus();
				xt.setPayerId(punter.getId());
				Punter sponsor = punterMgr.getPunterDao().getByContact(us.getSponsorContact());
				xt.setPayeeId(sponsor.getId());
				xt.setDate((new GregorianCalendar().getTime()));
				xt.setAmount(ratingMgr.getUpgradeFeeForRating(us.getNewRating()));
				if (us.getNewRating()==1)
					xt.setDescription("Zen Activate Member At Rank 1");
				else
					xt.setDescription("Zen Upgrade Member To Rank " + us.getNewRating());
				us.setPaymentStatus(PaymentStatus.PAYMENTMADE);
				xt.setPaymentStatus(us.getPaymentStatus());
				xt.setPaymentInfo(pi);
				
				services.getHome().getPaymentDao().storeXtransaction(xt);						// THESE NEED TO BE IN A TRANSACTION
				services.getHome().getPunterDao().updateUpgradeStatus(punter);
				
			} catch (Exception e) {
				log.info(e.getMessage());
				throw new RestServicesException("Could not submit transaction details - contact support.");
			}
	}
	
	public List<XactionJson> getXtransactionsForMember(String memberType, String contact, String paymentStatus,
			long offset, long limit)
	{
		Punter punter = getPunter(contact);
		List<XactionJson> xjs = new ArrayList<XactionJson>();
		PaymentStatus ps = PaymentStatus.valueOf(paymentStatus);
		try {
			List<Xtransaction> xts = services.getHome().getPaymentDao().getXtransactionsForMember(memberType, punter.getId(), ps, offset, limit);
			for (Xtransaction  xt : xts)
				xjs.add(createXactionJson(xt,memberType));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not get transactions for member - contact support.");
		}
		return xjs;
	}
	
	public XactionJson getXtransactionById(String paymentId, String memberType) {
		try {
			long idL = Long.parseLong(paymentId);
			Xtransaction xt = services.getHome().getPaymentDao().getXtransactionById(idL);
			XactionJson xj = createXactionJson(xt,memberType);
			PaymentInfoJson pi = createPaymentInfoJson(xt.getPaymentInfo());
			xj.setPaymentInfo(pi);
			return xj;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not get transaction for id - contact support.");
		}
	}
	
	private PaymentInfoJson createPaymentInfoJson(PaymentInfo paymentInfo) {
		PaymentInfoJson pi = new PaymentInfoJson();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		pi.setTransactionDate(sdf.format(paymentInfo.getTransactionDate()));
		pi.setTransactionDetails(paymentInfo.getTransactionDetails());
		if (paymentInfo.getUploadFileBytes() == null)
			return pi;
		byte[] img =  Base64.encode(paymentInfo.getUploadFileBytes());
		try {
			pi.setUploadFileImage(new String(img,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
		return pi;
	}

	private XactionJson createXactionJson(Xtransaction xt, String memberType) {
		XactionJson xj = new XactionJson();
		xj.setId(xt.getId());
		xj.setAmount(xt.getAmount());
		if (memberType.equals("payer"))
		{
			xj.setContact(xt.getPayeeContact());
			xj.setFullName(xt.getPayeeFullName());
			xj.setPhone(xt.getPayeePhone());
		}
		else
		{
			xj.setContact(xt.getPayerContact());
			xj.setFullName(xt.getPayerFullName());
			xj.setPhone(xt.getPayerPhone());
		}
		xj.setStatus(xt.getPaymentStatus().getDisplayStatus());
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm");
		xj.setDate(sdf.format(xt.getDate()));
		xj.setDescription(xt.getDescription());
		return xj;
	}

	public UpgradeJson getUpgradeRequest(String contact) {
		Punter punter = getPunter(contact);
		UpgradeStatus us = punter.getUpgradeStatus();
		if (!us.getPaymentStatus().equals(PaymentStatus.PAYMENTDUE) && !us.getPaymentStatus().equals(PaymentStatus.PAYMENTFAIL))
			throw new RestServicesException("Member not eligible for upgrade at this time.");
			
		try {
			UpgradeJson uj = getUpgradeRequest(punter);
			return uj;	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not get upgrade request - contact support.");
		}
	}

	private UpgradeJson getUpgradeRequest(Punter punter) {
		UpgradeStatus us = punter.getUpgradeStatus();
		UpgradeJson uj = new UpgradeJson();
		uj.setCurrentRank(punter.getRating());
		uj.setUpgradeRank(us.getNewRating());
		Punter sponsor = punterMgr.getPunterDao().getByContact(punter.getSponsorContact());
		uj.setUpline(createPunterProfileJson(sponsor));
		uj.setFee(ratingMgr.getUpgradeFeeForRating(us.getNewRating()));
		return uj;
	}
	
	public void registerPunter(PunterProfileJson profile) throws RestServicesException
	{
		try {
			Punter punter = punterMgr.registerPunter(profile);
			services.getMailNotifier().notifyFirstUpgradeRequired(punter);
		} catch (PunterMgrException e) {
			log.error(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}		
	}
	
	public String getRandomUsername() {
		try {
			return punterMgr.getRandomUsername();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}		
	}
	
	
	public ModelJson getModel(String rootContact) throws Exception
	{
		ModelJson mj = new ModelJson();
		List<PunterJson> punters = getPunters(rootContact,mj);
		mj.setPunters(punters);
		double systemOwnedRevenue = services.getHome().getPunterDao().getRevenue('S');
		mj.setSystemOwnedRevenue(systemOwnedRevenue);
		double punterOwnedRevenue = services.getHome().getPunterDao().getRevenue('P');
		mj.setPunterOwnedRevenue(punterOwnedRevenue);
		return mj;
	}
	
	private List<PunterJson> getPunters(String rootContact, ModelJson mj) throws PunterMgrException
	{
		Punter root = punterMgr.getByContact(rootContact);
		PunterJson parent = addPunter(root,mj);	
//		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		List<PunterJson> result =  new ArrayList<PunterJson>();
		result.add(parent);
		return result;
	}

	private PunterJson addPunter(Punter punter, ModelJson mj) throws PunterMgrException {
		PunterJson pj = createPunterJson(punter);
		if (punter.isSystemOwned())
			mj.setPopulationInside(mj.getPopulationInside()+1);
		else
			mj.setPopulationOutside(mj.getPopulationOutside()+1);
		
		mj.setPopulation(mj.getPopulation()+1);
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter p : children)
		{
			pj.getChildren().add(addPunter(p,mj));
		}
		return pj;
	}
	
	private PunterJson createPunterJson(Punter punter)
	{
		PunterJson pj = new PunterJson();
		int imageId = 12;
		if (!punter.getEmail().equals("zen@test.com"))
			imageId = punter.getRating();
		pj.setImageUrl("../../../img/" + imageId + ".jpeg");
		String text = punter.getContact();
		NumberFormat formatter = new DecimalFormat("#0.00");
		String bal = " RM" + formatter.format(punter.getAccount().getBalance());
		String lev = " #" + punter.getRating() + " ";
		String line = null;
		
		if (!punter.isSystemOwned())
			line = "<font color='#045023'>"+ text + bal + lev + "</font></a>";
		else
			line = "<font color='Red'>"+ text + bal + lev + "</font></a>";
		
		log.info("JJYYY");
		String method = "return getPunterDetails('" + punter.getContact() + "')";
		log.info("Method : " + method);
		String href = "<a href=\"#\" onclick=\"" + method +"\">" + line;
		log.info("href : " + href);
		pj.setText(href);
//		pj.setAccount(createAccountJson(punter.getAccount()));
		return pj;
	}

	@SuppressWarnings("unused")
	private AccountJson createAccountJson(Account account) {
		AccountJson aj = new AccountJson();
		aj.setBalance(account.getBalance());
		return aj;
	}
	
	public void changePassword(String contact, ChangePasswordJson changePassword) throws RestServicesException{
		Punter punter = getPunter(contact);
		try {
			punterMgr.changePassword(punter,changePassword);
		} catch (PunterMgrException e) {
			log.info(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}
	}
	
	public void register(String contact, PunterProfileJson profile) throws RestServicesException{
		try
		{
			profile.setSponsorContact(contact);
			punterMgr.registerPunter(profile);
		}
		catch (PunterMgrValidationException e)
		{
			throw new RestServicesException(e.getMessage());
		}
		catch (PunterMgrException e)
		{
			throw new RestServicesException(e.getMessage());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new RestServicesException(e.getMessage());
		}
	}

	public void updatePunterProfile(String contact,PunterProfileJson profile) throws RestServicesException{
		Punter punter = getPunter(contact);
	
		try {
			punterMgr.validateProfileValues(profile);
		} catch (PunterMgrException e) {
			log.info(e.getMessage());
			throw new RestServicesException(e.getMessage());
		}
		
		punter.copyProfileValues(profile);
		try
		{
			services.getHome().getPunterDao().update(punter);
		}
		catch (Exception e)
		{
			log.error("updatePunterProfile",e);
			throw new RestServicesException("Zen Member : " + contact + " could not be updated - contact support");
		}
	}

	public void addPunterPaymentMethod(String contact, String id, String accountNumber) throws RestServicesException{
		Punter punter = getPunter(contact);
		
		try
		{
			int index = Integer.parseInt(id);
			PaymentMethodJson pm = services.getHome().getPaymentDao().getPaymentMethodById(index);
			PunterPaymentMethodJson ppm = new PunterPaymentMethodJson();
			ppm.setAccountNum(accountNumber);
			ppm.setPunterId(punter.getId());
			ppm.setActivated(true);
			ppm.setCountry(pm.getCountry());
			ppm.setMethod(pm.getMethod());
			services.getHome().getPaymentDao().storePunterPaymentMethod(ppm);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not add payment method - contact support");
		}
	}
		
	public void deletePunterPaymentMethod(String id) {
		try
		{
			int index = Integer.parseInt(id);
			services.getHome().getPaymentDao().deletePunterPaymentMethodById(index);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new RestServicesException("Could not delete payment method - contact support");
		}
	}
	
	public PunterProfileJson getPunterProfile(String contact) throws RestServicesException{
	
		Punter punter = getPunter(contact);
		PunterProfileJson ppj = createPunterProfileJson(punter);
		List<Xtransaction> xts = services.getHome().getPaymentDao().getXtransactionsForMember("payee",punter.getId(), PaymentStatus.PAYMENTMADE, 0, Integer.MAX_VALUE); 
		ActionJson aj = new ActionJson();
		if (!xts.isEmpty())
			aj.setPaymentsPending(Integer.toString(xts.size()));
		if (punter.getUpgradeStatus().getPaymentStatus().equals(PaymentStatus.PAYMENTDUE))
			aj.setUpgradable("&#10004");
		ppj.setActions(aj);
		return ppj;
	}

	private Punter getPunter(String contact) throws RestServicesException{
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			if (punter == null)
				throw new RestServicesException("Zen Member : " + contact + " not found - contact support");
		}
		catch (Exception e)
		{
			log.error("getPunter",e);
			throw new RestServicesException("Zen Member : " + contact + " not found - contact support");
		}
		return punter;
	}
	
	public List<PunterProfileJson> getDownstreamPunters(String contact) {
		List<PunterProfileJson> dsp = new ArrayList<PunterProfileJson>();
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			List<Punter> punters = services.getHome().getPunterDao().getSponsoredChildren(punter);
			for (Punter p : punters)
			{
				dsp.add(createPunterProfileJson(p));
			}
			return dsp;
		}
		catch (Exception e)
		{
			log.error("getDownstreamPunters",e);
			throw new RestServicesException("Zen downstream members for : " + contact + " not found - contact support");
		}
	}
	
	private PunterProfileJson createPunterProfileJson(Punter punter) {
		PunterProfileJson pj = new PunterProfileJson();	
		pj.setId(punter.getId());
		pj.setContact(punter.getContact());
		pj.setEmail(punter.getEmail());
		pj.setPhone(punter.getPhone());
		pj.setSystemOwned(punter.isSystemOwned());
		pj.setFullName(punter.getFullName());
		pj.setGender(punter.getGender());
		pj.setPassportIc(punter.getPassportIc());
		pj.setAddress(punter.getAddress());
		pj.setState(punter.getState());
		pj.setPostcode(punter.getPostcode());
		pj.setCountry(punter.getCountry());
		pj.setUpstreamContact(punter.getParentContact());
		pj.setSponsorContact(pj.getSponsorContact());
		pj.setRating(punter.getRating());
		pj.setActivated(punter.getActivated());
		pj.setPaymentMethods(punter.getPaymentMethods());
		return pj;
	}

	public List<PunterPaymentMethodJson> getPunterPaymentMethods(String contact) {
		Punter punter = getPunter(contact);
		return punter.getPaymentMethods();
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
	

}
