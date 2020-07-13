package org.zen.translate.chinese;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.zen.persistence.home.Home;

public class TranslateChineseOverrides {
	private static Logger log = Logger.getLogger(TranslateChineseOverrides.class);
	static String[][] reps = { 
			{ "下线", "代理商" },
			{ "下线名单", "代理商名单" },
			{ "Zen赞助", "禅宗赞助" },
			{ "上线信息", "赞助商信息" },
			{ "新下线信息", "新代理信息" },
			{ "Zen用户名", "禅宗会员" },
			{ "Zen会员：", "禅宗激活会员等级" },
			{ "Zen激活会员等级", "禅宗激活会员等级" },
			{ "Zen上线：", "禅宗赞助商：" },
			{ "下线信息", "代理商信息" },
			{ "Zen会员的新密码：", "禅宗会员的新密码：" },
			{ "恭喜！", "矛盾！" },
			{ "Zen激活会员等级1", "禅宗激活会员等级1" }
			};
	
	public static void main(String... args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"zen-persistence.xml");
		Home home = (Home) context.getBean("home");
		
		log.info("Doing chinese translation overrides");
		try
		{
			for (String[] rep : reps)
			{
				home.getTranslationDao().override("ch",rep[1], rep[0]);
			}
		}
		catch (Exception e)
		{
			log.info("Broke",e);
		}
		System.out.println("DONE");
	}
}
