package br.ufscar.dc.dsw.service.spec;

import javax.mail.internet.InternetAddress;

public interface IEmail {
	
	void send(InternetAddress from, InternetAddress to, String subject, String body);
}
