package org.zen.user.punter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.zen.json.PunterPaymentMethodJson;
import org.zen.json.PunterProfileJson;
import org.zen.user.BaseUser;
import org.zen.user.account.Account;
import org.zen.user.punter.upgrade.PaymentStatus;
import org.zen.user.punter.upgrade.UpgradeStatus;

public class Punter extends BaseUser {

	private int rating;
	private List<Punter> children = new ArrayList<Punter>();
	private UUID parentId;
	private String parentContact;
	private Punter parent;
	private UUID sponsorId;
	private String sponsorContact;
	private Punter sponsor;
	private List<Punter> sponsoredChildren = new ArrayList<Punter>();
	private Account account = new Account();
	private boolean systemOwned;
	private Date activated;
	private UpgradeStatus upgradeStatus;
	private List<PunterPaymentMethodJson> paymentMethods = new ArrayList<PunterPaymentMethodJson>();
	private int level;
	private Date created;
	private Date updated;
	
	public Punter()
	{
		super();
		setRole(BaseUser.ROLE_PUNTER);
		setEnabled(false);
	}
	
	public void copyProfileValues(PunterProfileJson profile)
	{
		if (profile.getContact()!=null)					// null if comes from update
			setContact(profile.getContact());
		setEmail(profile.getEmail());
		setPhone(profile.getPhone());
		setFullName(profile.getFullName());
		setGender(profile.getGender());
		setPassportIc(profile.getPassportIc());
		setAddress(profile.getAddress());
		setState(profile.getState());
		setPostcode(profile.getPostcode());
		setCountry(profile.getCountry());
	}
	
	public String toSummaryString()
	{
		String msg = "Punter contact=" + getContact() + " rating=" + rating; 
		if (parent == null)
			return msg;
		msg+= " sponsor=" + sponsor.getContact() + " parent=" + parent.getContact();
		return msg;
	}
	
	public List<Punter> getChildren() {
		return children;
	}

	public void setChildren(List<Punter> children) {
		this.children = children;
	}

	public Punter getParent() {
		return parent;
	}

	public void setParent(Punter parent) {
		this.parent = parent;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Punter getSponsor() {
		return sponsor;
	}

	public void setSponsor(Punter sponsor) {
		this.sponsor = sponsor;
	}

	public List<Punter> getSponsoredChildren() {
		return sponsoredChildren;
	}

	public void setSponsoredChildren(List<Punter> sponsoredChildren) {
		this.sponsoredChildren = sponsoredChildren;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public UUID getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(UUID sponsorId) {
		this.sponsorId = sponsorId;
	}

	public boolean isSystemOwned() {
		return systemOwned;
	}

	public void setSystemOwned(boolean systemOwned) {
		this.systemOwned = systemOwned;
	}
	
	public String getParentContact() {
		return parentContact;
	}

	public void setParentContact(String parentContact) {
		this.parentContact = parentContact;
	}

	public String getSponsorContact() {
		return sponsorContact;
	}

	public void setSponsorContact(String sponsorContact) {
		this.sponsorContact = sponsorContact;
	}

	public Date getActivated() {
		return activated;
	}

	public void setActivated(Date activated) {
		this.activated = activated;
	}

	public UpgradeStatus getUpgradeStatus() {
		return upgradeStatus;
	}

	public void setUpgradeStatus(UpgradeStatus upgradeStatus) {
		this.upgradeStatus = upgradeStatus;
	}

	public List<PunterPaymentMethodJson> getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(List<PunterPaymentMethodJson> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}

	@Override
	public String toString() {
		return "Punter [rating=" + rating + ", getId()=" + getId()
				+ ", getContact()=" + getContact() + ", getEmail()=" + getEmail() + ", getPhone()=" + getPhone()
				+ ", isEnabled()=" + isEnabled() + "]";
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isUpgradeScheduled() {
		return getUpgradeStatus().getPaymentStatus().equals(PaymentStatus.PAYMENTDUE);
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
}
