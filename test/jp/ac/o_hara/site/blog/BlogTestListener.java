package jp.ac.o_hara.site.blog;
import java.sql.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class BlogTestListener implements ServletContextListener {
	BlogDAO dao = null;
	public BlogTestListener() {
		dao = BlogDAO.getInstanse();
	}
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (System.getenv("DATABASE_URL") != null) {
		} else {
			if (dao.execSQL("CREATE TABLE IF NOT EXISTS blogtbl"
					+ " (id IDENTITY , userID VARCHAR(64), date DATE , title VARCHAR(64), article VARCHAR(256))")) {
				System.out.println("TestBlogDB is READY.");
			} else {
				System.out.println("TestUserDB is NOT READY.");
			}
		}
		dao.create(new BlogBean("hoge", Date.valueOf("2020-12-08"), "ほげ", "ほげほげ"));
		dao.create(new BlogBean("piyo", Date.valueOf("2020-12-07"), "ぴよ", "ぴよぴよ"));
		//4つテスト用に作る
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		dao = null;
	}
}
