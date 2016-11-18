package com.doumob.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
	public static void main(String[] args) throws Exception {
//		HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();
//		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//是否有音调
//		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//大小写
//		format.setVCharType(HanyuPinyinVCharType.WITH_V);
//		System.out.println(PinyinHelper.toHanyuPinyinStringArray('女', format)[0]);
		System.out.println(getPinyin("催迪"));
	}
	/**
	 *@DESC:将字符串转成拼音，其中除中文以外的不动<br>
	 *@2015年9月28日<br>
	 *@autor:zhangH<br>
	 *@param str 要转为拼音的字符串
	 *@return 
	 */
	public static String getPinyin(String str){
		StringBuffer sb=new StringBuffer();
		if(str!=null&&str.length()>0){
			for (int i = 0; i < str.length(); i++) {
				sb.append(format(str.charAt(i)));
			}
		}
		return sb.toString();
	}
	
	public static String format(char c){
		String[] py=null;
		HanyuPinyinOutputFormat format= new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//是否有音调
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);//大小写
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			py=PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if(py==null||py.length<1){
			return String.valueOf(c);
		}
		return py[0];
	}

}
