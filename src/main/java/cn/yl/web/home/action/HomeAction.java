package cn.yl.web.home.action;

import cn.yl.web.interceptor.Post;
import cn.yl.web.jfinal.WebController;
import cn.yl.web.job.DoubleChromoSphere;
import cn.yl.web.model.Ball;
import cn.yl.web.model.Test;
import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2016/5/27.
 */
public class HomeAction extends WebController {
    public void index(){
        List<String> list = Lists.newArrayList();
        renderJsp("/index.jsp");
    }
    public void test(){
        new DoubleChromoSphere().execute();
        setAttr("count", Ball.dao.count());
        renderJson();
    }
}
