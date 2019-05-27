<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<meta http-equiv="refresh" content="0;URL='HomeAction'"/>
<title>index</title>
</head>
<body>
<!-- HOME画面にﾍｯﾀﾞｰを適用するため一度経由するjsp -->
<!-- ﾍｯﾀﾞｰにsqlを経由しないと項目がでない部分がある。※'#session.mCategoryDTOList -->
<!-- sqlを出すにはjsp⇒action⇒の流れがある※今授業で教えた方法。他にも方法があるが授業に則って適用。 -->
<!-- jspを経由してactionに行くためﾕｰｻﾞｰがhome.jspに入った時index.jspを経由し、 -->
<!-- actionを作動させsqlの項目をとってきたhome.jspを出します。 -->
</body>
</html>