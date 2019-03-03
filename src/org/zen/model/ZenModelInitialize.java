package org.zen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.json.ProfileJson;
import org.zen.user.faker.FakerUtil;
import org.zen.user.punter.Punter;
import org.zen.user.punter.mgr.PunterMgr;
import org.zen.user.punter.mgr.PunterMgrException;

public class ZenModelInitialize {

	private static Logger log = Logger.getLogger(ZenModelInitialize.class);
	@Autowired
	private ZenModel zenModel;
	@Autowired
	private PunterMgr punterMgr;
	
	public ZenModelInitialize()
	{
	}
	
	public Punter initializeModel() throws Exception
	{
		punterMgr.deleteAllContacts();
		ProfileJson rootProfile = makeProfile("zen","zen@test.com","0123456789","88888888",null);
		punterMgr.registerPunter(rootProfile);
		Punter root = punterMgr.getByContact("zen");
		log.info("Got root : " + root);
		FakerUtil fu = new FakerUtil();
		fu.exclude("zen");
		recruitLevel(root,4,fu);
		return root;
	}
	
	private void recruitLevel(Punter parent,int levels, FakerUtil fu) throws PunterMgrException
	{
		if (levels==0)
			return;
		Random r = new Random();
		for (int i=0; i<ZenModel.FULLCHILDREN; i++)
		{
			String contact = fu.getExlusiveRandomName();
			String phone = "012" + r.nextInt(10000000);
			ProfileJson childProfile = makeProfile(contact,contact+"@test.com",phone,"88888888",parent.getContact());
			Punter child = punterMgr.registerPunter(childProfile);
			
			zenModel.payJoinFee(child);
			
			recruitLevel(child,levels-1, fu);
		}
	}
	
	private ProfileJson makeProfile(String contact,String email,String phone,String password,String sponsorContactId)
	{
		ProfileJson pj = new ProfileJson();
		pj.setContact(contact);
		pj.setEmail(email);
		pj.setPassword(password);
		pj.setPhone(phone);
		pj.setSponsorContactId(sponsorContactId);
		return pj;
	}

	public PunterMgr getPunterMgr() {
		return punterMgr;
	}

	public void setPunterMgr(PunterMgr punterMgr) {
		this.punterMgr = punterMgr;
	}
	
	public ZenModel getZenModel() {
		return zenModel;
	}

	public void setZenModel(ZenModel zenModel) {
		this.zenModel = zenModel;
	}

	public void printModel(Punter root) throws PunterMgrException
	{
		List<Integer> levelCounts = new ArrayList<Integer>();
		System.out.println("\nZen Model\n");
		printModelPunter(root,root,levelCounts);
		System.out.println("\nLevels:\n");
		int population = 0;
		for (int i=0; i<levelCounts.size(); i++)
		{
			System.out.println("Level - " + i + " : " + levelCounts.get(i));
			population += levelCounts.get(i);
		}
		System.out.println("\nPopulation : " + population);
	}
	
	private void printModelPunter(Punter punter,Punter upstream, List<Integer> levelCounts) throws PunterMgrException
	{
		int level = punterMgr.getLevel(punter, upstream);
		if (level+1 > levelCounts.size())
			levelCounts.add(1);
		else
		{
			int cnt = levelCounts.get(level);
			cnt++;
			levelCounts.set(level,cnt);
		}
		printPunterLine(punter,level);
		List<Punter> children = punterMgr.getChidren(punter);
		for (Punter child : children)
			printModelPunter(child,upstream,levelCounts);
	}
	
	private void printPunterLine(Punter p,int level)
	{
		String line = "";
		for (int i=0; i<level; i++)
			line += "-";
		System.out.println(line+p.toSummaryString() + " level : " + level);
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-service.xml");
		try
		{
			ZenModelInitialize zmi = (ZenModelInitialize) context.getBean("zenModelInitialize");
			Punter root = zmi.initializeModel();
			zmi.printModel(root);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		log.info("Done");
		System.exit(0);
	}
}
