package cn.yl.web.jfinal;


import cn.yl.web.home.action.HomeAction;
import cn.yl.web.job.DoubleChromoSphere;
import cn.yl.web.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.render.ViewType;
import org.quartz.JobExecutionException;


public class WebConfig extends JFinalConfig {

	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		System.err.println(getPropertyToBoolean("devMode", false));
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.JSP);
		me.setError404View("/404.html"); // 过滤action不存在的情况，web.xml过滤静态资源不存在的情况
		me.setError500View("/500.html");
//		me.setUploadedFileSaveDirectory(getProperty("upload_base_url"));
		me.setBaseUploadPath(getProperty("upload_file_base_path"));
		// 配置微信 API 相关常量
//		ApiConfig.setDevMode(me.getDevMode());
//		ApiConfig.setUrl(getProperty("url"));
//		ApiConfig.setToken(getProperty("token"));
//		ApiConfig.setAppId(getProperty("appId"));
//		ApiConfig.setAppSecret(getProperty("appSecret"));		
	}
	
	public void configRoute(Routes me) {
//		me.add(new WebRoute());		//web端映射
		me.add("/home",HomeAction.class);
//		me.add("/",HomeAction.class);
	}
	
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"),getProperty("user"),getProperty("password").trim(),getProperty("jdbcdriver"));
		druidPlugin.setFilters("stat,wall");
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setDialect(new PostgreSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		arp.setShowSql(true);
		me.add(druidPlugin);
		_MappingKit.mapping(arp);
		me.add(arp);

//		QuartzPlugin  plugin = new QuartzPlugin();
//		me.add(plugin);

//		RedisPlugin redisPlugin = new RedisPlugin("public","127.0.0.1",6379);
//		me.add(redisPlugin);
	}
	
	public void configInterceptor(Interceptors me) {
//        me.add(new TxByRegex("/.*[save|delete|update].*"));
    }
	
	public void configHandler(Handlers me) {
//		me.add(new RequestHandler());
//		DruidStatViewHandler dvh =  new DruidStatViewHandler("/mydruid");
//		me.add(dvh);
//		HashSet<String> set = new HashSet<String>();
//		set.add("/page");
//		FileFilterHander file = new FileFilterHander(set);
//		me.add(file);
//		me.add(new TestHander());
	}
	
	@Override
	public void afterJFinalStart() {



	}

	@Override
	public void beforeJFinalStop() {
		
	}

	
}
