package com.doumob.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpSslServer;

import org.apache.log4j.Logger;

import com.doumob.util.Property;

/**
 * Created by Killer on 2015/1/29.
 */
public class JoddMailService {
	private Logger logger = Logger.getLogger(getClass());
	private static JoddMailService service;

	private SendMailSession mailSession;
	private String[] host={"smtp.exmail.qq.com"};//,"smtp.163.com","smtp.126.com","smtp.sina.com.cn","smtp.sohu.com"};
	private String[] address={"shangwu@caredsp.com"};//,"blkshangwu@163.com","blkshangwu@126.com","blkshangwu@sina.com","blkshangwu@sohu.com"};

	private JoddMailService(Integer index) {
		if(index>address.length-1){
			logger.warn("可用地址己全部用完。");
			System.exit(0);
		}
		logger.info("当前使用:"+address[index]);
		SmtpSslServer server = new SmtpSslServer(host[index]);
		server.authenticateWith(address[index],Property.getValue("auth_pass"));
		mailSession = server.createSession();
		mailSession.open();
		logger.info("己成功创建连接,当前使用服务："+host[index]);
	}
	
	public static JoddMailService getService(Integer index) {
		if (service == null) {
			service = new JoddMailService(index);
		}
		return service;
	}

	public void close() {
		mailSession.close();
		service = null;
		logger.info("连接己关闭------");
	}

	/**
	 * 发送重置密码通知
	 * 
	 * @param mailTo
	 */
	public boolean send(String mailTo, String content) throws Exception {
		mailTo=mailTo.replaceAll("\\s", "");
		try {
			if(valid(mailTo)){
				Email email = Email.create().from(new String(Property.getValue("nick").getBytes("iso-8859-1"),"UTF-8"),Property.getValue("auth_name"))
						.to(mailTo).subject(MailTemplate.Salary_notice.getTitle())// 主题
						.addHtml(content);
				mailSession.sendMail(email);
				return true;
			}else{
				logger.warn("邮箱格式不正确:"+mailTo);
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
	private final Pattern p =  Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");//复杂匹配  
	public boolean valid(String mailTo){
        Matcher m = p.matcher(mailTo);  
        return m.matches();  
	}
	
	public static void main(String[] args) {
		System.out.println(getService(0).valid("rcyy20080118@126.com"));
	}
}
