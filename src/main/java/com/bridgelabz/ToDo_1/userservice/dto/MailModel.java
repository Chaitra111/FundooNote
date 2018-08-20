package com.bridgelabz.ToDo_1.userservice.dto;

import org.springframework.stereotype.Service;
/**
 * @author Chaitra Ankolekar
 * Purpose :Mail pojo class
 */
@Service
public class MailModel 
{
	private String toMailAddress;
	private String subject;
	private String body;
	private String mailSign;

	/**
	 * @return String
	 */
	public String getToMailAddress() {
		return toMailAddress;
	}

	/**
	 * @param toMailAddress
	 * @return String
	 */
	public String setToMailAddress(String toMailAddress) {
		return this.toMailAddress = toMailAddress;
	}

	/**
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 * @return String
	 */
	public String setSubject(String subject) {
		return this.subject = subject;
	}

	/**
	 * @return String
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 * @return String
	 */
	public String setBody(String body) {
		return this.body = body;
	}

	/**
	 * @return String
	 */
	public String getMailSign() {
		return mailSign;
	}

	/**
	 * @param mailSign
	 */
	public void setMailSign(String mailSign) {
		this.mailSign = mailSign;
	}
}