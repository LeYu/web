package cn.yl.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTest<M extends BaseTest<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setOpendate(java.util.Date opendate) {
		set("opendate", opendate);
	}

	public java.util.Date getOpendate() {
		return get("opendate");
	}

}
