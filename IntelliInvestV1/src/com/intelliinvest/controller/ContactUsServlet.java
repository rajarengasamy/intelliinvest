package com.intelliinvest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.intelliinvest.data.model.Contact;
import com.intelliinvest.util.IntelliInvestStore;
import com.intelliinvest.util.MailUtil;

@Controller
public class ContactUsServlet {

	protected final Log logger = LogFactory.getLog(getClass());

	@RequestMapping(value = { "contact-us" }, method = RequestMethod.GET)
	public ModelAndView contactUs() {
		logger.info("Calling Contact Us");
		ModelAndView model = new ModelAndView("contact-us");
		model.addObject("title", "Contact Us");
		return model;
	}
	
	@RequestMapping(value = { "sendemail" }, method = { RequestMethod.GET,	RequestMethod.POST })
	@ResponseBody()
	public String sendemail(@ModelAttribute Contact contact) {
		String messageFull = "Hi Intelli Invest Support,<br>" + "Mail from : "
				+ contact.getEmail() + "<br>" + "Phone     : " + contact.getPhone() + "<br>" + "Company     : " + contact.getCompany() + "<br>" 
				+ "Message     : " + contact.getMessage() + "<br>";
		Boolean status = MailUtil.sendMail(IntelliInvestStore.properties
				.getProperty("smtp.host"), IntelliInvestStore.properties
				.getProperty("mail.from"), IntelliInvestStore.properties
				.getProperty("mail.password"),
				new String[] { IntelliInvestStore.properties
						.getProperty("mail.from") }, contact.getSubject(), messageFull);
		if (status) {
			return "Succesfully sent to IntelliInvest Support. We will communicate with you related to your query with in 48 hrs.";
		} else {
			return "Error sending information to Support, please try contacting admin through mail";
		}
	}
	
}