package com.doumob.email;


/**
 * Created by Killer on 2015/1/29.
 */
public enum MailTemplate {

	/**
     * 
     */
	Salary_notice() {
		protected String title = "医院流量解决方案——Care医疗健康广告平台";
		protected String mailBody = "";
		protected String footer = "<p class='p2'>注意:该邮件是系统自动发送，若您发现有任何问题，请及时联系公司行政人员，切勿以任何形式进行讨论，传播。"
				+ "若系统出现其它问题，请联系作者。</p></body></html>";

		public String getTitle() {
			return this.title;
		}

		public String getMailBody() {
			return this.mailBody;
		}

		public String getFooter() {
			return this.footer;
		}
	};

	public String getTitle() {
		return "";
	}

	public String getMailBody() {
		return "";
	}

	public String getFooter() {
		return "";
	}
}
