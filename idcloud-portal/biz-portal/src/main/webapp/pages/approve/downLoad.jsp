<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="java.net.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    String filePath = request.getParameter("filePath");
    String portlaHost = request.getParameter("portlaHost");
    String rootPath = portlaHost +filePath;//获取请求路径
    String[] urlname = rootPath.split("/");
	int len = urlname.length-1;
    String uname = urlname[len];//获取文件名
    //建立远程连接
   	URL url = new URL(rootPath);
    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
    uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
    uc.connect();
    InputStream iputstream = uc.getInputStream();
    OutputStream oputstream = response.getOutputStream();
    byte[] buffer = new byte[500];
    int byteRead = -1;
    File fileLoad=new File(rootPath);  
    response.reset();  
    response.setContentType("text/plain;application/pdf;application/vnd.ms-excel;application/vnd.ms-powerpoint;application/wordperfect5.1");  
    response.setHeader("content-disposition","attachment; filename="+java.net.URLEncoder.encode(uname, "UTF-8"));
    long fileLength=fileLoad.length();  
    String length1=String.valueOf(fileLength);  
  //  FileInputStream in=new FileInputStream(fileLoad);  
    while((byteRead=(iputstream.read(buffer)))!= -1){
        oputstream.write(buffer, 0, byteRead);
    }   
    oputstream.flush();
  //  in.close();
    iputstream.close();
    oputstream.close();
%>
</body>
</html>