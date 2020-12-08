package jp.ac.o_hara.site.blog;
import java.io.Serializable;
import java.sql.Date;

import jp.ac.o_hara.site.calendar.CalendarBean;
import jp.ac.o_hara.site.calendar.Schedulable;
public class BlogBean implements Serializable, Schedulable {
	private String userId = null;
	private Date date = null;
	private String title = null;
	private String article = null;
	public BlogBean() {
	}
	public BlogBean(String userId, Date date, String title, String article) {
		this.setUserId(userId);
		this.setDate(date);
		this.setTitle(title);
		this.setArticle(article);
	}
	public void setUserId(String id) {this.userId = id;}
	public String getUserId() {return this.userId;}
	public void setDate(Date date) {this.date = date;	}
	public Date getDate() {return this.date;}
	public void setTitle(String title) {this.title = title;}
	public String getTitle() {return this.title;}
	public void setArticle(String article) {this.article = article;}
	public String getArticle() {return this.article;}
	
	@Override
	public String preRenderSchedule() {
		return "<table class='table table-bordered table-condensed'><tr><th>日時</th><th>ユーザー</th></tr>";
	}

	@Override
	public String renderSchedule(CalendarBean bean) {
		return "<tr><td>"+bean.getDate().toString()+"</td><td>"+bean.getUserID()+"</td></tr>";
	}

	@Override
	public String postRenderSchedule() {
		return "</table>";
	}

}
