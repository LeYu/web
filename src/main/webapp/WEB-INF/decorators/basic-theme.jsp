<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html >
<head>
    <title><decorator:title default="独孤一笑" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="个人网站,IT,码农">
	<meta http-equiv="description" content="独孤一笑">
    <link type="text/css" rel="stylesheet" href="/themes/css/normalize.css">
    <link type="text/css" rel="stylesheet" href="/themes/basic-theme.css">
    <script>
    var _hmt = _hmt || [];
    (function() {
    var hm = document.createElement("script");
    hm.src = "//hm.baidu.com/hm.js?6172413690536d975c186ef13f865fc9";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
    })();
    </script>
    <%--<script type="text/javascript"--%>
    <%--src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js"--%>
    <%--data-appid="101206670"--%>
    <%--data-redirecturi="http://www.yule4j.cn/qqcallback.html" charset="utf-8"></script>--%>
    <%-- 
    <script src="${request.getContextPath()}/resource/require.js" ></script>
    <script src="${request.getContextPath()}/resource/require.config.js" ></script>
    --%>
    <decorator:head />
    </head>
<body>
   <div class="siteMesh-container">
	 <nav class="banner">
	    <div class="logo">  </div>
	    <div class="search">
	       <form action="http://www.baidu.com/baidu" target="_blank">
	    	 <input name=tn type=hidden value=baidu>
	        <div >
	          <input id="searchText" name=word  type="text" />
	        </div>

	   	     <input type="submit" id="bsearch" value="百度一下" />
	       </form>
	    </div>
		    <div class="login-div">
		    <a href="javascript:void(0);" class="login">登录</a>
		    
		    <span id="qqLoginBtn"></span>
		    </div>
	    </nav>
	    <div class="nav">
	     <ul>
		     <li data-role="link" data-url="">首页</li>
		     <li data-role="link" data-url="">生活</li>
		     <li>JAVA</li>
		     <li>JS</li>
		     <li>HTML/CSS</li>
		     <li>数据库</li>
		     <li>关于我</li>
	     </ul>
	     
	    </div>
	    <%--body begin --%>
	    <div class="siteMesh-body">
			<decorator:body />
			<div class="alert-login">
				<ul>
					<li>帐号:</li>
					<li> <input /> </li>
				</ul>
				<ul>
					<li>密码:</li>
					<li> <input /> </li>
				</ul>
			</div>
	    </div>
	    <footer class="siteMesh-foot">
	       <div class="beian">蜀ICP备15008378号</div>
	    </footer>
    </div>
</body>
<script charset="gbk" src="/resource/js/opensug.js"></script>
<script src="/resource/jquery/jquery-2.1.3.min.js"></script>
<script src="/resource/js/layer/layer.js"></script>
<script src="/js/public.js"></script>
    <script src="/js/index.js"></script>
    <script>
    	layer.config({extend: 'extend/layer.ext.js'});
    	$(function(){
			$(".login").on("click",function(){
				var index = layer.open({
								type:1,
								title:"登录",
								move: false,
								area: '410px',
								content: $('.alert-login')
					
							});
			});

        });
    
    </script>
</html>