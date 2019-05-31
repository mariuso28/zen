package org.zen.user.punter.mgr;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zen.json.ChangePasswordJson;
import org.zen.json.NotificationJson;
import org.zen.json.PaymentMethodJson;
import org.zen.json.PunterPaymentMethodJson;
import org.zen.json.PunterProfileJson;
import org.zen.model.ZenModel;
import org.zen.model.ZenModelOriginal;
import org.zen.payment.Xtransaction;
import org.zen.payment.persistence.PaymentDao;
import org.zen.persistence.PersistenceRuntimeException;
import org.zen.rating.RatingMgr;
import org.zen.rest.services.RestServices;
import org.zen.services.Services;
import org.zen.user.account.Account;
import org.zen.user.faker.FakeContact;
import org.zen.user.faker.FakeContactGen;
import org.zen.user.faker.FakerUtil;
import org.zen.user.persistence.BaseUserDao;
import org.zen.user.punter.Punter;
import org.zen.user.punter.persistence.PunterDao;
import org.zen.user.punter.upgrade.PaymentStatus;
import org.zen.user.punter.upgrade.UpgradeStatus;
import org.zen.util.EmailValidator;
import org.zen.util.PhoneValidator;

public class PunterMgr {

	private static final Logger log = Logger.getLogger(PunterMgr.class);
	@Autowired
	private Services services;
	private RatingMgr ratingMgr;
	@Autowired
	private PunterDao punterDao;
	private FakeContactGen fakeContactGen;
	private FakeContact zenContact;
	
	public PunterMgr()
	{
	}
	
	public void init()
	{
		ratingMgr = new RatingMgr();
		fakeContactGen = new FakeContactGen(services);
		String zenRootContact = services.getProp().getProperty("zenRootContact", "mony;Mony;Seyha"); 
		String[] toks = zenRootContact.split(";");
		String zenSupportEmail = services.getProp().getProperty("zenSupportEmail","zen@test.com");
		setZenContact(new FakeContact(toks[0],toks[1],toks[2],zenSupportEmail));		
	}
	
	private String x(String src)
	{
		return services.getTxm().xlate(src);
	}
	
	public List<NotificationJson> getNoticationsForPunter(Punter punter) {
		List<NotificationJson> njs = new ArrayList<NotificationJson>();
		if (punter.isUpgradeScheduled())
			njs.add(getUpgradeNotication());
		else
		if (punter.getUpgradeStatus().getPaymentStatus().equals(PaymentStatus.PAYMENTMADE))
			njs.add(getPaymentMade());
		njs.addAll(getPaymentsOutstanding(punter));
		njs.addAll(getPaymentsDue(punter));
		if (njs.isEmpty())
			njs.add(getNoNotications());
		return njs;
	}
	
	private List<NotificationJson> getPaymentsDue(Punter punter) {
		List<NotificationJson> njs = new ArrayList<NotificationJson>();
		List<Punter> children = services.getHome().getPunterDao().getSponsoredChildren(punter);
		for (Punter child : children)
		{
			NotificationJson nj = new NotificationJson();
			UpgradeStatus us = child.getUpgradeStatus();
			if (us.getPaymentStatus().equals(PaymentStatus.PAYMENTDUE))
			{
				nj.setMessage(x("Waiting for payment of $") + ratingMgr.getUpgradeFeeForRating(us.getNewRating()) + x(" to be made from ") + child.getContact()
															 + x(" to upgrade to rank ") + us.getNewRating());
				nj.setHref("/zen/zx4/web/anon/goGeneologyContact?contact="+child.getContact());
				nj.setPriority(3);
				njs.add(nj);
			}
		}
		return njs;
	}
	
	private List<NotificationJson> getPaymentsOutstanding(Punter punter) {
		List<NotificationJson> njs = new ArrayList<NotificationJson>();
		List<Xtransaction> xts = services.getHome().getPaymentDao().getXtransactionsForMember("payee",punter.getId(), PaymentStatus.PAYMENTMADE, 0, Integer.MAX_VALUE);
		for (Xtransaction xt : xts)
		{
			NotificationJson nj = new NotificationJson();
			nj.setMessage(x("Payment of $") + xt.getAmount() + x(" submitted from ") + xt.getPayerContact() + x(" to ") + x(xt.getDescription()));
			nj.setHref("/zen/zx4/web/anon/goPaymentReceived");
			nj.setPriority(1);
			njs.add(nj);
		}
		return njs;
	}
	
	private NotificationJson getPaymentMade() {
		NotificationJson nj = new NotificationJson();
		nj.setMessage(x("Your payment made - remember to check email for status update."));
		nj.setHref("#");
		nj.setPriority(2);
		return nj;
	}

	private NotificationJson getNoNotications()
	{
		NotificationJson nj = new NotificationJson();
		nj.setMessage(x("No Posts Currently Flagged."));
		nj.setHref("#");
		nj.setPriority(5);
		return nj;
	}
	
	private NotificationJson getUpgradeNotication()
	{
		NotificationJson nj = new NotificationJson();
		nj.setMessage(x("You are eligible for upgrade!! Upgrade now!!"));
		nj.setHref("/zen/zx4/web/anon/goUpgrade");
		nj.setPriority(1);
		return nj;
	}

	public String resetPassword(Punter punter) throws PunterMgrException
	{
		String pw="";
		boolean ok=false;
		while (!ok)
		{
			pw = createRandomPassword();
			try
			{
				validatePassword(pw);
				ok=true;
			}
			catch (Exception e)
			{
			}
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		punter.setPassword(encoder.encode(pw));
		try
		{
			punterDao.updatePassword(punter);
			services.getMailNotifier().notifyPasswordReset(punter,pw);
			String msg = x("A reset password has been sent to your email : ") + punter.getEmail() + x(". Please logon and change at your convenience.");
			log.info(msg);
			return msg;
		}
		catch (Exception e)
		{
			throw new PunterMgrException(x("Could not reset password") +  x(" - contact support."));
		}
	}
	
	private String createRandomPassword()
	{
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
		        .withinRange('0', 'z')
		        .filteredBy(LETTERS, DIGITS)
		        .build();
		String random = generator.generate(8);
		return random;
	}
	
	public void changePassword(Punter punter, ChangePasswordJson changePassword) throws PunterMgrException{

		PasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean match = encoder.matches(changePassword.getOldPassword(),punter.getPassword());
		if (!match)
			throw new PunterMgrException(x("Old password not correct, fix or logout to reset."));
		validatePassword(changePassword.getPassword());
		punter.setPassword(encoder.encode(changePassword.getPassword()));
		try
		{
			punterDao.updatePassword(punter);
		}
		catch (Exception e)
		{
			throw new PunterMgrException(x("Could not change password") +  x(" - contact support."));
		}
	}

	public List<Punter> getSystemPunters() throws PunterMgrException
	{
		try
		{
			return punterDao.getSystemPunters();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not get system punters") +  x(" - contact support."));
		}
	}
	
	public int getLevel(Punter punter,Punter upstream) throws PunterMgrException
	{
		int level=0;
		if (punter.getId().equals(upstream.getId()))
			return level;
		Punter parent = punter;
		while (true)
		{
			level++;
			parent = punterDao.getById(parent.getParentId());
			if (parent == null)
				throw new PunterMgrException(x("Could not get level") + x(" - contact support."));
			if (parent.getId().equals(upstream.getId()))
				return level;
		}
	}
	
	public void setPunterEnabled(Punter punter,boolean enabled) throws PunterMgrException {
		punter.setEnabled(enabled);
		try
		{
			punterDao.setPunterEnabled(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("could not set punter enabled")+  x(" - contact support."));
		}
	}
	
	public FakeContact getFakeContact(boolean systemGen)
	{
		BaseUserDao bud = services.getHome().getBaseUserDao();
		FakeContact fc;
		while (true)
		{
			if (systemGen)
			{
				fc = fakeContactGen.getSytemFakeContact();
				fc.setEmail(zenContact.getEmail());
			}
			else
			{
				fc = fakeContactGen.getFakeContact();
			}
			if (zenContact.getContact().equals(fc.getContact()))
				continue;
			if (!punterDao.getByFullName(fc.getFullName()).isEmpty())
				continue;
			List<String> contacts = bud.getNearestContactId(fc.getContact());
			if (!contacts.isEmpty())
				fc.setContact(contacts.get(0));
			break;
		}
		return fc;
	}
	
	public String getRandomUsername() {
		FakerUtil fu = new FakerUtil();
		BaseUserDao bud = services.getHome().getBaseUserDao();
		while (true)
		{
			String un = fu.getRandomName();
			int pos = un.indexOf(' ');
			if (pos>0)
				un = un.substring(0,pos);
			pos = un.indexOf('\'');
			if (pos>0)
				un = un.substring(0,pos);
			un = fu.sanitize(un);
			if (un==null)
				continue;
			if (un.length()>6 || un.length()<3)
				continue;
			un = un.toLowerCase();
			List<String> contacts = bud.getNearestContactId(un);
			if (contacts.isEmpty())
				return un;
			return contacts.get(0);
		}
	}

	public Punter registerPunter(PunterProfileJson profile) throws PunterMgrException{
		BaseUserDao bud = services.getHome().getBaseUserDao();
		try
		{
			List<String> contacts = bud.getNearestContactId(profile.getContact());
			if (!contacts.isEmpty())
			{
				String msg = x("Member name : ") + profile.getContact() + x(" already taken please choose another. Suggested alternatives : ");
				for (String contact : contacts)
					msg += contact + ",";
				throw new PunterMgrException(msg.substring(0,msg.length()-1)+".");
			}
			validateContact(profile.getContact());
		}
		catch (PersistenceRuntimeException e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not register member with contact name")+  x(" - contact support."));
		}
		validatePassword(profile.getPassword());
		validateProfileValues(profile);
		Punter sponsor = validateSponsor(profile.getSponsorContact());
		Punter parent = null;
		if (sponsor!=null)
			parent = getParent(sponsor);
		Punter punter = createPunter(profile,sponsor,parent);
		
		try
		{
			punterDao.store(punter);
			return punterDao.getByContact(punter.getContact());
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not register member with contact name") +  x(" - contact support."));
		}
	}
	
	@SuppressWarnings("unused")
	private void storeDefaultPaymentMethods(Punter punter) {
		PaymentDao pd = services.getHome().getPaymentDao();
		List<PaymentMethodJson> pms = pd.getAvailablePaymentMethods();
		for (PaymentMethodJson pm : pms)
		{
			PunterPaymentMethodJson ppm = new PunterPaymentMethodJson(pm);
			ppm.setPunterId(punter.getId());
			String accountNum = createRandomAccNum(punter.getId().toString());
			ppm.setAccountNum(accountNum);
			ppm.setActivated(true);
			pd.storePunterPaymentMethod(ppm);
		}
	}

	private String createRandomAccNum(String source) {
		String acc = "";
		for (int i=0; i<source.length(); i++)
			if (Character.isDigit(source.charAt(i)))
				acc += source.charAt(i);
		if (acc.length()>16)
			return acc.substring(0,16);
		return acc;
	}

	public void validateProfileValues(PunterProfileJson profile) throws PunterMgrValidationException
	{
		validateField(x("Full Name"),profile.getFullName());
		PhoneValidator pv = new PhoneValidator();
		if (!pv.validate(profile.getPhone()))
			throw new PunterMgrValidationException(x("Invalid phone number : ") + profile.getPhone() + x(" - please fix."));
		EmailValidator ev = new EmailValidator();
		if (!ev.validate(profile.getEmail()))
			throw new PunterMgrValidationException(x("Invalid email : ") + profile.getEmail() + x(" - please fix."));
	}
	
	private void validateField(String fieldName, String field) throws PunterMgrValidationException{
		if (field == null || field.isEmpty())
			throw new PunterMgrValidationException(x("Please supply a valid ") + fieldName);
	}

	private Punter createPunter(PunterProfileJson profile, Punter sponsor, Punter parent) {
		Punter punter = new Punter();
		punter.copyProfileValues(profile);
		punter.setSponsor(sponsor);
		punter.setParent(parent);
		punter.setAccount(new Account());
		punter.setEnabled(true);
		UpgradeStatus us = new UpgradeStatus();
		us.setChanged((new GregorianCalendar()).getTime());
		if (sponsor==null)			// zen
		{
			punter.setRating(-1);
			punter.setLevel(0);
			us.setPaymentStatus(PaymentStatus.NONE);
			us.setSponsorContact("");
		}
		else
		{
			punter.setRating(0);
			punter.setLevel(parent.getLevel()+1);
			us.setPaymentStatus(PaymentStatus.PAYMENTDUE);
			us.setSponsorContact(sponsor.getContact());
			us.setNewRating(1);
		}
		punter.setSystemOwned(punter.getLevel()<=ZenModelOriginal.SYSTEMLEVELS);
		
		punter.setUpgradeStatus(us);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		punter.setPassword(encoder.encode(profile.getPassword()));			
		return punter;
	}

	public void tryUpgrade(Punter punter, RestServices restServices) throws PunterMgrException
	{
		int newRating = canUpgrade2(punter);
		if (newRating<=0)
			return;
		
		scheduleUpgrade(punter,newRating,restServices);
		while (true)
		{
			Punter parent = getByUUID(punter.getParentId());
			newRating = canUpgrade2(parent);
			if (newRating<=0)
				return;
			scheduleUpgrade(parent,newRating,restServices);
			punter = parent;
		}
	}
	
	private void scheduleUpgrade(Punter punter, int newRating,RestServices restServices) throws PunterMgrException {
		UpgradeStatus us = punter.getUpgradeStatus();
		us.setPaymentStatus(PaymentStatus.PAYMENTDUE);
		us.setNewRating(newRating);
		Punter parentToPay = getByUUID(punter.getParentId());
		if (parentToPay.getRating()!=-1)					// jump a generation
		{
			parentToPay = getByUUID(parentToPay.getParentId());
		}
		// find the parent above rating
		while (parentToPay.getRating() != -1 && parentToPay.getRating()<punter.getRating())
		{
			parentToPay = getByUUID(parentToPay.getParentId());
		}
		us.setSponsorContact(parentToPay.getContact());
		punterDao.updateUpgradeStatus(punter);
		if (punter.isSystemOwned())						// upgrade automatically
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String ds = sdf.format((new GregorianCalendar()).getTime());
			long paymentId = restServices.submitTransactionDetails(punter.getContact(),null,ds,
					x("Pay from ") + punter.getContact() + x(" to ") + punter.getSponsorContact());
			restServices.approvePayment(punter.getSponsorContact(),Long.toString(paymentId));
		}
		else
		{
			double fee = ratingMgr.getUpgradeFeeForRating(newRating);
			services.getMailNotifier().notifyUpgradeRequired(punter,fee);
		}
	}

	private int canUpgrade2(Punter punter) throws PunterMgrException
	{
		if (punter.getRating()<0)
			return -1;
		List<Punter> children = getChidren(punter);
		if (children.size()<ZenModel.FULLCHILDREN)
			return -1;
		for (Punter c : children)
		{
			if (c.getRating()<punter.getRating())
				return -1;
		}
		int newRating = Integer.MAX_VALUE;
		for (Punter c : children)
		{
			if (c.getRating()<newRating)
				newRating = c.getRating();
		}
		return newRating+1;
	}
	
	private Punter getParent(Punter root)
	{
		List<Punter> children = punterDao.getChildren(root);
		if (children.size()<ZenModelOriginal.FULLCHILDREN)
			return root;
		Punter parent = null;
		int max = ZenModelOriginal.FULLCHILDREN;
		for (Punter child : children)
		{
			List<Punter> children1 = punterDao.getChildren(child);
			if (children1.size()<max)
			{
				parent = child;
				max = children1.size();
			}
		}
		if (parent != null)
			return parent;
		Random r = new Random();
		parent = children.get(r.nextInt(ZenModelOriginal.FULLCHILDREN));
		return getParent(parent);
	}

	private Punter validateSponsor(String sponsorContact) throws PunterMgrException {
		if (sponsorContact==null)				// root;
			return null;
		try
		{
			Punter sponsor = punterDao.getByContact(sponsorContact);
			if (sponsor==null)
			{
				String msg = x("Zen sponsor : ") + sponsorContact + x(" does not exist. Please check.");
				log.error(msg);
				throw new PunterMgrException(msg);
			}
			return sponsor;
		}
		catch (PersistenceRuntimeException e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not validate sponsor") +  x(" - contact support."));
		}
	}

	private void validateContact(String contact) throws PunterMgrValidationException{
		EmailValidator ev = new EmailValidator();
		if (contact==null || !ev.validate(contact+"@test.com"))
		{
			log.info(contact);
			throw new PunterMgrValidationException(x("Please complete contact from alpha and digit and period characters only."));
		}
	}

	private void validatePassword(String password) throws PunterMgrValidationException{
		if (password != null && password.length()>=8)
		{
			for (int i=0; i<password.length(); i++)
				if (Character.isDigit(password.charAt(i)))
					return;
		}
		throw new PunterMgrValidationException(x("Password must be at least 8 characters and contain at least 1 digit."));
	}

	public void deleteAllPunters(boolean systemOwned) throws PunterMgrException
	{
		try
		{
			punterDao.deleteAllPunters(systemOwned);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not delete all contacts") +  x(" - contact support."));
		}
	}
	
	public void deleteAllPunters() throws PunterMgrException
	{
		try
		{
			punterDao.deleteAllPunters();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not delete all contacts") + x(" - contact support."));
		}
	}
	
	public Punter getUpgradeParentToPay(final Punter punter,int newRating)
	{ 
		Punter parent = punterDao.getByContact(punter.getParentContact());
		if (parent.getRating()==-1)
			return parent;
		parent = punterDao.getByContact(parent.getParentContact());				// skip a generation
		while (parent.getRating()!=-1)
		{
			if (parent.getRating() >= newRating)
				break;
			parent = punterDao.getByContact(parent.getParentContact());
		}
		return parent;
	}
	
	public void deleteByContact(final Punter punter) throws PunterMgrException
	{
		try
		{
			punterDao.deletePunter(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not delete punter by contact") +  x(" - contact support."));
		}
	}
	
	public Punter getByContact(final String contact) throws PunterMgrException
	{
		try
		{
			Punter punter = punterDao.getByContact(contact);
			populatePunter(punter);
			return punter;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not get punter by contact") +  x(" - contact support."));
		}
	}
	
	public List<Punter> getChidren(final Punter punter) throws PunterMgrException
	{
		try
		{
			List<Punter> children = punterDao.getChildren(punter);
			return children;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not get children")+  x(" - contact support."));
		}
	}
	
	public int getChidrenCnt(final Punter punter) throws PunterMgrException
	{
		try
		{
			return punterDao.getChildrenCnt(punter);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not get children") +  x(" - contact support."));
		}
	}
	
	public Punter getByUUID(final UUID id) throws PunterMgrException
	{
		try
		{
			Punter punter = punterDao.getById(id);
			populatePunter(punter);
			return punter;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(),e);
			throw new PunterMgrException(x("Could not get punter by id") +  x(" - contact support."));
		}
	}

	private void populatePunter(Punter punter) throws Exception{
		if (punter.getParentId()==null)
			return;
		
		punter.setSponsor(punterDao.getById(punter.getSponsorId()));
		punter.setParent(punterDao.getById(punter.getParentId()));
	}

	public RatingMgr getRatingMgr() {
		return ratingMgr;
	}

	public void setRatingMgr(RatingMgr ratingMgr) {
		this.ratingMgr = ratingMgr;
	}

	public PunterDao getPunterDao() {
		return punterDao;
	}

	public void setPunterDao(PunterDao punterDao) {
		this.punterDao = punterDao;
	}

	public FakeContact getZenContact() {
		return zenContact;
	}

	public void setZenContact(FakeContact zenContact) {
		this.zenContact = zenContact;
	}

	
}
