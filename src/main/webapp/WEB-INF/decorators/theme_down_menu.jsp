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
    <link type="text/css" rel="stylesheet" href="/themes/style_down.css">
    <decorator:head />
    </head>
<body>
   <div class="down-menu">

   </div>
   <div class="siteMesh-container">
	 <nav class="banner">
	    <div class="logo">  </div>
	    <div class="search">

		</div>
	 </nav>

	    <%--body begin --%>
	    <div class="siteMesh-body">
			<decorator:body />

	    </div>
	    <footer class="siteMesh-foot">
	       <%--<div class="beian">蜀ICP备15008378号</div>--%>
	    </footer>
    </div>
   <script src="/resource/jquery/jquery-2.1.3.min.js"></script>
   <script src="/resource/js/layer/layer.js"></script>
   <script src="/js/public.js"></script>
</body>


</html>