package cn.yl.web.jfinal;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 2016/5/27.
 */
public class RequestHandler extends Handler {
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String requestType = request.getHeader("X-Requested-With");
        request.setAttribute("isAjaxRequest", requestType!=null);
    }
}
