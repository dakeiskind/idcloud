<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="langLocale" value="zh_CN"/>
<c:if test="${param.lang!=null}">
	<c:set var="langLocale" value="${param.lang}"/>
</c:if>
<fmt:setLocale value="${langLocale}"/>
<fmt:setBundle basename="i18n.message"/>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
