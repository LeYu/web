package cn.yl.web.jfinal;

import com.jfinal.core.Controller;

public abstract class WebController extends Controller {
	
	protected static String JSP_BATHPATH = "/WEB-INF/jsp/";
	protected static String JSP_EXT = ".jsp";

//
	public void index(){
		System.out.println("index:"+getPara());

		render("/404.html");

	}

	@Override
	public void renderJsp(String view) {
		if(!view.startsWith("/")){
			view = JSP_BATHPATH+view+JSP_EXT;
		}
		super.renderJsp(view);
	}
	
	public void renderData(){
		
	}

}
