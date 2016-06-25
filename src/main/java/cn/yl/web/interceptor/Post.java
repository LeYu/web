package cn.yl.web.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by john on 2016/6/7.
 */
public class Post implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String m =  inv.getController().getRequest().getMethod();
        System.out.println("method:"+m);
        inv.invoke();
    }
}
