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
import org.zen.json.CountryJson;
import org.zen.json.ModelJson;
import org.zen.json.NotificationJson;
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
	
	public String x(String src)
	{
		return services.getTxm().xlate(src);
	}
	
	public void sendPaymentQuery(String contact, String paymentId) {
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			if (punter == null)
				throw new RestServicesException(x("Zen Member : ") + contact + x(" not found - please check."));
			long idL = Long.parseLong(paymentId);
			Xtransaction xt = services.getHome().getPaymentDao().getXtransactionById(idL);
			Punter payee = services.getHome().getPunterDao().getById(xt.getPayeeId());
			Punter zen = services.getHome().getPunterDao().getByContact(punterMgr.getZenContact().getContact());
			services.getMailNotifier().notifyPaymentQuery(zen,punter,payee,xt);
		}
		catch (Exception e)
		{
			log.error("sendPaymentQuery",e);
			throw new RestServicesException(x("error send payment query" + x(" - contact support.")));
		}
	}
	
	public List<NotificationJson> getNotifications(String contact) {
		Punter punter;
		try
		{
			punter = services.getHome().getPunterDao().getByContact(contact);
			if (punter == null)
				throw new RestServicesException(x("Zen Member : ") + contact + x(" not found - please check"));
			return punterMgr.getNoticationsForPunter(punter);
		}
		catch (Exception e)
		{
			log.error("getNotifications",e);
			throw new RestServicesException(x("error get notifications") + x(" - contact support."));
		}
	}

	public String resetPassword(String username) {
		Punter punter;
		try
		{
			punter = getPunter(username);
			return punterMgr.resetPassword(punter);
		}
		catch (Exception e)
		{
			log.error("resetPassword",e);
			throw new RestServicesException(x("Zen Member : ") + username + x(" not found - please check"));
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
			services.updatePaymentSuccess(xt,sponsor, punter);
			
			log.info("Payment approved for : " + contact);
			services.getMailNotifier().notifyUpgradeSuccessful(punter);
			
			// SET NEW UPGRADESTATUS for upstream
			punterMgr.tryUpgrade(sponsor,this);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException(x("Could not approve payment for id") + x(" - contact support."));
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
			xt.setPaymentStatus(PaymentStatus.PAYMENTFAIL);
			services.updatePaymentFail(xt,sponsor, punter);
			log.info("Payment rejected for : " + contact);
			services.getMailNotifier().notifyUpgradeFailed(punter,sponsor);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException(x("Could not approve payment for id") + x(" - contact support."));
		}
	}
	
	public long submitTransactionDetails(String contact, MultipartFile uploadfile, String transactionDate,
			String transactionDetails) {
		
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			Date td = null;
			try
			{
				td = sdf.parse(transactionDate);
			}
			catch (ParseException e)
			{
				String msg = x("Invalid transaction date : ") + transactionDate + x(" should be mm-dd-yyyy format.");
				log.info(msg);
				throw new RestServicesException(msg);
			}
			Punter punter = getPunter(contact);
			PaymentInfo pi = new PaymentInfo();
			pi.setTransactionDate(td);
			if (uploadfile==null || uploadfile.isEmpty())
			{
				log.info("Upload file is empty using transaction details");
				if (transactionDetails.isEmpty())
				{
					String msg = x("Invalid transaction - details or upload file must be submitted.");
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
					pi.setUploadFileName(uploadfile.getOriginalFilename());
					log.info("storing file : " + uploadfile.getOriginalFilename());
				} catch (IOException e) {
					log.error(e.getMessage(),e);
					String msg = x("Invalid upload file - please try another.");
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
				xt.setNewRating(us.getNewRating());
				
				services.storePaymentMade(punter,xt);
				
				return xt.getId();
				
			} catch (Exception e) {
				log.info(e.getMessage());
				throw new RestServicesException(x("Could not submit transaction details") + x(" - contact support."));
			}
	}
	
	public List<XactionJson> getXtransactionsForMember(String memberType, String contact, String paymentStatus,
			long offset, long limit)
	{
		Punter punter = getPunter(contact);
		List<XactionJson> xjs = new ArrayList<XactionJson>();
		PaymentStatus ps = PaymentStatus.valueOf(paymentStatus);
		try {
			if (limit==-1)
				limit = Long.MAX_VALUE;
			List<Xtransaction> xts = services.getHome().getPaymentDao().getXtransactionsForMember(memberType, punter.getId(), ps, offset, limit);
			for (Xtransaction  xt : xts)
				xjs.add(createXactionJson(xt,memberType));
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException(x("Could not get transactions for member") + x(" - contact support."));
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
			throw new RestServicesException(x("Could not get transaction for id") + x(" - contact support."));
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
		xj.setStatus(x(xt.getPaymentStatus().getDisplayStatus()));
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm");
		xj.setDate(sdf.format(xt.getDate()));
		String desc = xt.getDescription().trim();
		int pos = desc.indexOf("Zen ");
		if (pos>=0)
			desc = desc.substring(pos+4);
		pos = desc.lastIndexOf("Rank ")+5;
		if (pos<0)
			xj.setDescription(x(xt.getDescription()));
		else
			xj.setDescription("Zen " + x(desc.substring(0,pos))+desc.substring(pos));
		return xj;
	}

	public UpgradeJson getUpgradeRequest(String contact) {
		Punter punter = getPunter(contact);
		UpgradeStatus us = punter.getUpgradeStatus();
		if (!us.getPaymentStatus().equals(PaymentStatus.PAYMENTDUE) && !us.getPaymentStatus().equals(PaymentStatus.PAYMENTFAIL))
			throw new RestServicesException(x("Member not eligible for upgrade at this time."));
			
		try {
			UpgradeJson uj = getUpgradeRequest(punter);
			return uj;	
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new RestServicesException(x("Could not get upgrade request") + x(" - contact support."));
		}
	}

	private UpgradeJson getUpgradeRequest(Punter punter) {
		UpgradeStatus us = punter.getUpgradeStatus();
		UpgradeJson uj = new UpgradeJson();
		uj.setCurrentRank(punter.getRating());
		uj.setUpgradeRank(us.getNewRating());
		Punter sponsor = null;
		if (punter.getRating()==0)
			sponsor = punterMgr.getPunterDao().getByContact(punter.getSponsorContact());
		else
			sponsor = punterMgr.getUpgradeParentToPay(punter,us.getNewRating());
		
		uj.setUpline(createPunterProfileJson(sponsor,false));
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
	
	public List<String> searchModelByContact(String searchTerm) throws PunterMgrException{

		Punter contact = punterMgr.getByContact(searchTerm);
		if (contact == null)
			throw new RestServicesException(x("Zen Member : ") + searchTerm + x(" not in your hierarchy."));
		List<String> ids = new ArrayList<String>();
		ids.add(contact.getContact());
		while (true)
		{
			if (contact.getParentContact()==null)
				break;
			Punter p = services.getHome().getPunterDao().getByContact(contact.getParentContact());
			ids.add(0,p.getContact());
			contact = p;
		}
		return ids;
	}

	
	public List<PunterJson> getRoot(String rootContact) throws PunterMgrException
	{
		Punter root = punterMgr.getByContact(rootContact);
		List<PunterJson> result = new ArrayList<PunterJson>();
		result.add(createPunterJson(root));
		return result;
	}
		
	public List<PunterJson> getPunters(String rootContact) throws PunterMgrException
	{
		Punter root = punterMgr.getByContact(rootContact);
		List<PunterJson> result = new ArrayList<PunterJson>();
		List<Punter> children = services.getHome().getPunterDao().getChildren(root);
		for (Punter p : children)
			result.add(createPunterJson(p));
		return result;
	}
	
	@SuppressWarnings("unused")
	private PunterJson addPunter(Punter punter,Punter parent) throws PunterMgrException {
		PunterJson pj = createPunterJson(punter);
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter p : children)
		{
			pj.getChildren().add(addPunter(p,punter));
		}
		return pj;
	}
	
	private List<PunterJson> getPunters(String rootContact, ModelJson mj) throws PunterMgrException
	{
		Punter root = punterMgr.getByContact(rootContact);
		PunterJson parent = addPunter(root,mj,null);	
//		log.info("created " + services.getZenModel().getPopulation() + " punterJsons");
		List<PunterJson> result =  new ArrayList<PunterJson>();
		result.add(parent);
		return result;
	}

	private PunterJson addPunter(Punter punter, ModelJson mj,Punter parent) throws PunterMgrException {
		PunterJson pj = createPunterJson(punter);
		if (punter.isSystemOwned())
			mj.setPopulationInside(mj.getPopulationInside()+1);
		else
			mj.setPopulationOutside(mj.getPopulationOutside()+1);
		
		mj.setPopulation(mj.getPopulation()+1);
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter p : children)
		{
			pj.getChildren().add(addPunter(p,mj,punter));
		}
		return pj;
	}
	
	private PunterJson createPunterJson(Punter punter)
	{
		PunterJson pj = new PunterJson();
		pj.setParentId(punter.getParentContact());
		pj.setHasChildren(services.getHome().getPunterDao().getChildrenCnt(punter)>0);
		pj.setId(punter.getContact());
		int imageId = 12;
		if (!punter.getContact().equals(punterMgr.getZenContact().getContact()))
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
		
		String method = "return getPunterDetails('" + punter.getContact() + "')";
		String href = "<a href=\"#\" onclick=\"" + method +"\">" + line;
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
			throw new RestServicesException(x("Zen Member : ") + contact + x(" could not be updated") + x(" - contact support."));
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
			throw new RestServicesException(x("Could not add payment method") + x(" - contact support."));
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
			throw new RestServicesException(x("Could not delete payment method") + x(" - contact support."));
		}
	}
	
	public PunterProfileJson getPunterProfileForCaller(String caller, String contact) throws RestServicesException{
	
		boolean fullyPopulate = true;
		Punter punter = getPunter(contact);
		if (!caller.equals(contact))
		{
			Punter cp = getPunter(caller);
			if (!cp.isSystemOwned() && !punter.getSponsorContact().equals(caller))
				fullyPopulate = false;
		}
		
		PunterProfileJson ppj = createPunterProfileJson(punter,fullyPopulate);
		List<Xtransaction> xts = services.getHome().getPaymentDao().getXtransactionsForMember("payee",punter.getId(), PaymentStatus.PAYMENTMADE, 0, Integer.MAX_VALUE); 
		ActionJson aj = new ActionJson();
		if (!xts.isEmpty())
			aj.setPaymentsPending(Integer.toString(xts.size()));
		if (punter.isUpgradeScheduled())
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
				throw new RestServicesException(x("Zen Member : ") + contact + x(" not found") + x(" - contact support."));
		}
		catch (Exception e)
		{
			log.error("getPunter",e);
			throw new RestServicesException(x("Zen Member : ") + contact + x(" not found") + x(" - contact support."));
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
				dsp.add(createPunterProfileJson(p,punter.isSystemOwned()));
			}
			return dsp;
		}
		catch (Exception e)
		{
			log.error("getDownstreamPunters",e);
			throw new RestServicesException(x("Zen downstream members for : ") + contact + x(" not found") + x(" - contact support."));
		}
	}
	
	private PunterProfileJson createPunterProfileJson(Punter punter, boolean isSystemOwned) {
		PunterProfileJson pj = new PunterProfileJson();	
		pj.setId(punter.getId());
		pj.setContact(punter.getContact());
		pj.setSystemOwned(punter.isSystemOwned());
		pj.setFullName(punter.getFullName());
		pj.setUpstreamContact(punter.getParentContact());
		pj.setSponsorContact(punter.getSponsorContact());
		pj.setRating(punter.getRating());
		pj.setActivated(punter.getActivated());
		pj.setPaymentMethods(punter.getPaymentMethods());
		pj.setCanUpgrade(testUpgradable(punter));
		pj.setCanRecruit(testCanRecruit(punter));
		log.info("can recruit : " + pj.isCanRecruit());
		if (!isSystemOwned)
			return pj;
			
		pj.setGender(services.getTxm().xlate(punter.getGender()));
		pj.setEmail(punter.getEmail());
		pj.setPhone(punter.getPhone());
		pj.setPassportIc(punter.getPassportIc());
		pj.setAddress(punter.getAddress());
		pj.setState(punter.getState());
		pj.setPostcode(punter.getPostcode());
		pj.setCountryCode(punter.getCountry());
		CountryJson cj = services.getHome().getCountryDao().getCountryByCode(punter.getCountry());
		if (services.getTxm().getIsoCode().equals("km"))
			pj.setCountry(cj.getCountrykm());
		else
			pj.setCountry(cj.getCountry());
	
		return pj;
	}

	private boolean testCanRecruit(Punter punter) {
		return punterMgr.getAvailableParent(punter) != null;
	}

	private boolean testUpgradable(Punter punter) {
		UpgradeStatus us = punter.getUpgradeStatus();
		return us.getPaymentStatus().equals(PaymentStatus.PAYMENTDUE) || us.getPaymentStatus().equals(PaymentStatus.PAYMENTFAIL);
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
