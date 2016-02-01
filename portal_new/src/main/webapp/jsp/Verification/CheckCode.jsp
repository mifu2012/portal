<%@ page contentType="image/jpeg; charset=UTF-8"%>  
<%@ page import="java.awt.Color"%>  
<%@ page import="java.util.Random"%>  
<%@ page import="java.awt.image.BufferedImage"%>  
<%@ page import="java.awt.Graphics"%>  
<%@ page import="java.awt.Font"%>  
<%@ page import="javax.imageio.ImageIO"%>  
  
<%--  
这是一个用于生成随机验证码图片的JSP文件  
这里contentType="image/jpeg"用来告诉容器：该JSP文件的输出格式为图片格式  
登录网站时，通常要求输入随机生成的验证码，这是为了防止有些软件会自动生成破解密码  
这些验证码一般都是通过图片显示出来的，并且图片上有很多不规则的线条或者图案来干扰，使得软件很难识别出图案上的验证码  
--%>  
  
<%!//获得一个在给定范围内的颜色  
	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}%>  
  
<%
    	//设置页面不缓存  
    	
		System.setProperty("java.awt.headless", "true");
    	response.setHeader("Pragma", "No-cache");
    	response.setHeader("Cache-Control", "no-cache");
    	response.setDateHeader("Expires", 0);

    	//在内存中创建图像  
    	int width = 60, height = 20;
    	BufferedImage image = new BufferedImage(width, height,
    			BufferedImage.TYPE_INT_RGB);
    	Graphics g = image.getGraphics(); //获取图形上下文  
    	Random random = new Random(); //生成随机类  
    	g.setColor(this.getRandColor(200, 250)); //设定背景色  
    	g.fillRect(0, 0, width, height);
    	g.setFont(new Font("Times New Roman", Font.PLAIN, 18)); //设定字体  

    	//随机产生50条干扰线，使图像中的验证码不易被其它程序探测到  
    	g.setColor(this.getRandColor(160, 200));
    	for (int i = 0; i < 50; i++) {
    		int x11 = random.nextInt(width);
    		int y11 = random.nextInt(height);
    		int x22 = random.nextInt(width);
    		int y22 = random.nextInt(height);
    		g.drawLine(x11, y11, x11 + x22, y11 + y22);
    	}

    	//取随机产生的验证码，这里取四位数字  
    	String sRand = "";
    	for (int i = 0; i < 4; i++) {
    		String rand = String.valueOf(random.nextInt(10));
    		sRand += rand;
    		//将验证码显示到图像中  
    		g.setColor(new Color(20 + random.nextInt(110), 20 + random
    				.nextInt(100), 20 + random.nextInt(100)));
    		//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成  
    		g.drawString(rand, 13 * i + 6, 16);
    	}

    	session.setAttribute("validateing", sRand); //将验证码存入SESSION  
    	g.dispose(); //图像生效 
    	response.reset();
    	out.clear();
    	out = pageContext.pushBody();
    	ImageIO.write(image, "JPEG", response.getOutputStream()); //输出图像到页面
    %> 