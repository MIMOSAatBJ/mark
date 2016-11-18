package com.doumob.execute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.doumob.email.JoddMailService;
import com.doumob.reader.ExcelReader;

public class Executor {
	private static Logger logger = Logger.getLogger(Executor.class);

	/**
	 * @2015-6-29<br>
	 * @autor:zhangH<br>
	 * @desc:执行解析，并入库<br>
	 * @param path
	 *            文件路径
	 * @throws Exception
	 */
	public static void execute(String path) throws Exception {
		logger.info("邮件系统启动……");
		Long smillis = System.currentTimeMillis();
		ExcelReader reader=new ExcelReader();
		Workbook wb=reader.readWorkBook(path);
		Sheet sheet = reader.readSheet(wb, 1);
		Integer current=0;
		JoddMailService service=JoddMailService.getService(current);
		String s= getContent();
		logger.info("开始解析并发送邮件……");
		List<String> errorList=new ArrayList<String>();
		//String[] mt={"1972913439@qq.com","zhangchenhui@doumob.com","hkw1991@foxmail.com","89444485@qq.com"};
		for (int i = 1250; i < 2000; i++) {
			String to = sheet.getRow(i).getCell(0).getStringCellValue();
			try {
				boolean b= service.send(to,s);
				if (!b) {
					errorList.add("发送失败，下标"+i+",邮箱:"+to);
				}
			} catch (Exception e) {
//				service.close();
//				logger.debug("邮件发送失败，当前联接己废弃--");
//				current++;
//				service=JoddMailService.getService(current);
//				i--;
				continue;
			}
			logger.debug("当前第"+i+"个");
			Thread.sleep(10000);
		}
		service.close();
		if(errorList.size()>0){
			logger.info("邮件发送失败"+errorList.size()+"个。具体原因请查看错误日志。");
			File dir = new File("");
			File log = new File(dir.getAbsolutePath() +"/错误日志.txt");
			FileOutputStream fos=new FileOutputStream(log);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			for (String to: errorList) {
				bw.write(to);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}
		Thread.sleep(2000);
		Long emillis = System.currentTimeMillis();
		logger.info("系统运行时间：" + (emillis - smillis) / 1000 + "秒。");

	}

	public static void main(String[] args) throws Exception {
		File file = new File("");
		File dir = new File(file.getAbsolutePath() + "\\");
		String path = file.getAbsolutePath() + "\\";
		boolean find=false;
		if (dir.isDirectory()) {
			File[] fs = dir.listFiles();
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].isFile() &&( fs[i].getName().endsWith(".xls")||fs[i].getName().endsWith(".xlsx"))) {
					path = path + fs[i].getName();
					find=true;
					break;
				}
			}
		}
		if(find){
			logger.info("找到文件:"+path);
			execute(path);
		}else{
			logger.info("未找到可用文件，请检查当前文件夹:"+path);
		}
		// String path="C:/Users/killer/Desktop/kaoqin/8月.xls";
		Thread.sleep(3000);
		System.exit(0);
	}
	
	public static String getContent(){
		InputStream is= Executor.class.getResourceAsStream("/mail.html");
		InputStreamReader reader=new InputStreamReader(is);
		BufferedReader br=new BufferedReader(reader);
		StringBuffer sb=new StringBuffer();
		try {
			while (br.ready()) {
				sb.append(br.readLine());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
