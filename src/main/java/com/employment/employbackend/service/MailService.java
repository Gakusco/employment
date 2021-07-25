package com.employment.employbackend.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmailo(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("portaldeempleomuni@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

	public void sendMailWithAttachment(String to, String subject, String body, String[] fileToAttach)
			throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("portaldeempleomuni@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body);

		for (int i = 0; i < fileToAttach.length; i++) {
			FileSystemResource file = new FileSystemResource(
					new File(UploadFileService.UPLOAD_FOLDER + fileToAttach[i]));
			helper.addAttachment(("curriculum").concat("-" + i).concat(".pdf"), file);
		}

		mailSender.send(message);
	}
}
