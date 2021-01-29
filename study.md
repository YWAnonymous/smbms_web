
### jdbc连接地址：
```markdown
url=jdbc:mysql://localhost:3306/smbms?useUnicode=true&characterEncoding=utf8&useSSL=true
```

### 重定向和请求转发的区别
```markdown

重定向：resp.sendRedirect("jsp/frame.jsp");
    请求域的数据丢失
    地址栏的路径发生变化


请求转发：request.getRequestDispatcher("/login.jsp").forward(request,response);
    请求域的数据不丢失
    地址栏的路径不发生变化
```

### static代码块