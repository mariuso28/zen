package org.zen.user.punter.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.zen.user.punter.PunterHead;

public class PunterSupport {
	private static Logger log = Logger.getLogger(PunterSupport.class);
	private JdbcTemplate template;
	/*
	select id,contact,downlinecnt,r from
	(select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt < 3 
		and parentid in
		(select id from baseuser where rating>0 and childrencnt = 3 
		and parentid in
		(select id from baseuser where rating>0 and childrencnt = 3 
		and parentid in
		(select id from baseuser where rating>0 and childrencnt = 3 and 
		parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f'
		)
		)
		) 
	) as A 
	order by downlinecnt,r limit 1
	*/
	
	private String t0 = "select id,contact,downlinecnt,random() as r from baseuser where childrencnt < 3 and id=?";
	private String t1 = "select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt < 3 " 
			+ "and parentid = ? order by downlinecnt,r limit 1";
	private String t2Prefix = "select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt < 3 ";
	private String t2Interim = " and parentid in (select id from baseuser where rating>0 and childrencnt = 3";
	private String t2Cond = " and parentid in "
			+ "(select id from baseuser where rating>0 and childrencnt = 3 and parentid = ?";
	private String t2Suffix = " order by downlinecnt,r limit 1";
	
	private List<String> sqls = new ArrayList<String>();
	private List<String> psqls = new ArrayList<String>();
	
	public PunterSupport(JdbcTemplate template)
	{
		setTemplate(template);
	}
	
	public PunterHead getAvailableParent(UUID parentId)
	{
		int level = 1;
		String sql;
		String psql;
		while (true)
		{
			if (sqls.size()<level)
			{
				sql = getPunterAtLevelSql(parentId,level,false);	
				log.info(sql);
				sqls.add(level-1,sql);
				psql = getPunterAtLevelSql(parentId,level,true);	
				log.info(psql);
				psqls.add(level-1,psql);
			}
			else
			{
				sql = sqls.get(level-1);
				psql = psqls.get(level-1);
			}
			log.info(sql);
			log.info(parentId);
			
			List<PunterHead> phs = getTemplate().query(sql,new Object[] { parentId },BeanPropertyRowMapper.newInstance(PunterHead.class));
			if (!phs.isEmpty())
				return phs.get(0);
			if (level>1)
			{
				phs = getTemplate().query(psql,new Object[] { parentId },BeanPropertyRowMapper.newInstance(PunterHead.class));
				if (phs.isEmpty())
					return null;
			}
			level++;
		}
	}
		
	private String getPunterAtLevelSql(@SuppressWarnings("unused") UUID parentId,int level,boolean fail)
	{
		String sql;
		if (level==1)
			sql = t0;
		else
		if (level==2)
			sql = t1;
		else
		{
			sql = t2Prefix;
			for (int i=2; i<level; i++)
				sql += t2Interim;
			sql += t2Cond;
			for (int i=1; i<level; i++)
				sql += ")";
			sql += t2Suffix;		
		}
		if (fail)
			return sql.replace("< 3","= 3");
		return sql;
	}
	
	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}


	public String getT1() {
		return t1;
	}


	public void setT1(String t1) {
		this.t1 = t1;
	}


	public String getT2Prefix() {
		return t2Prefix;
	}


	public void setT2Prefix(String t2Prefix) {
		this.t2Prefix = t2Prefix;
	}


	public String getT2Interim() {
		return t2Interim;
	}


	public void setT2Interim(String t2Interim) {
		this.t2Interim = t2Interim;
	}


	public String getT2Cond() {
		return t2Cond;
	}


	public void setT2Cond(String t2Cond) {
		this.t2Cond = t2Cond;
	}


	public String getT2Suffix() {
		return t2Suffix;
	}


	public void setT2Suffix(String t2Suffix) {
		this.t2Suffix = t2Suffix;
	}
			
	public static void main(String... argv) {
		
		PunterSupport ps = new PunterSupport(null);
		String sql = ps.getPunterAtLevelSql(UUID.fromString("24e6da31-89bc-43c1-a44d-59d0cdae7f4f"),3,false);
		log.info(sql);
	}
			
			
}
