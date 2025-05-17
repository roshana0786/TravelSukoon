package com.sukoon.expiendia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendResetPasswordEmail(String to, String token) {
        String subject = "Forgot your password? No problem!";
        String link = "http://localhost:3000/set-password?token=" + token;

        String content = """
                <div style="font-family:Arial,sans-serif; max-width:600px; margin:auto; padding:20px; border:1px solid #ddd; border-radius:8px;">
                    <div style="text-align:center;">
                        <img src="https://sukoonexpedia.com/images/Logo.png" alt="sukoonexpedia.com" style="height:50px; margin-bottom:20px;" />
                                                                                             
                                                                                                                                   
                    </div>
                    <h2 style="color:#333;">Forgot your password? No problem!</h2>
                    <p>Dear Customer,</p>
                    <p>We have received your request to set password.<br>
                    To set password, please click the link below:</p>

                    <div style="text-align:center; margin:20px 0;">
                        <a href="%s" style="background-color:#1e3a8a; color:#fff; padding:12px 24px; text-decoration:none; border-radius:5px; display:inline-block;">Set Your Akbartravels Password</a>
                    </div>

                    <p>This link is valid for <strong>two hours</strong> from your request initiation for password recovery.</p>
                    <p><strong>If you didn't request a password set you can delete this email.</strong></p>

                    <hr style="margin:30px 0;">
                    <p style="color:#f00;"><strong>Note</strong> - Please do not reply to this email. It has been sent from an email account that is not monitored.<br>
                    Please add this email address in your contact list to ensure that you receive communication for your booking.</p>

                    <div style="display:flex; justify-content:space-between; align-items:center; margin-top:30px;">
                        <div>
                            <a href="#"><img src="https://www.akbartravels.com/images/google-play.png" alt="Get it on Google Play" height="40"></a>
                            <a href="#"><img src="https://www.akbartravels.com/images/app-store.png" alt="Download on the App Store" height="40"></a>
                        </div>
                        <div style="text-align:right;">
                            <p style="margin:0;"><strong>📞</strong> +91 022 40 666 444</p>
                            <p style="margin:0;"><strong>✉️</strong> <a href="mailto:mysupport@sookunexpendia.com">mysupport@akbartravels.com</a></p>
                        </div>
                    </div>

                    <p style="text-align:center; color:#888; font-size:12px; margin-top:30px;">Copyright © 2019 
                        <a href="https://www.akbartravels.com" style="color:#555;">www.sookunexpendia.com</a>. All Rights Reserved.
                    </p>
                </div>
                """.formatted(link);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset password email", e);
        }
    }

}




/*
package com.sukoon.expiendia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendResetPasswordEmail(String to, String token) {
        String subject = "Forgot your password? No problem!";
        String link = "http://localhost:3000/set-password?token=" + token;

        String content = """
                <div style="font-family:Arial,sans-serif; max-width:600px; margin:auto; padding:20px; border:1px solid #ddd; border-radius:8px;">
                    <div style="text-align:center;">
                        <img src="https://sukoonexpedia.com/images/Logo.png" alt="sukoonexpedia.com" style="height:50px; margin-bottom:20px;" />


                    </div>
                    <h2 style="color:#333;">Forgot your password? No problem!</h2>
                    <p>Dear Customer,</p>
                    <p>We have received your request to set password.<br>
                    To set password, please click the link below:</p>

                    <div style="text-align:center; margin:20px 0;">
                        <a href="%s" style="background-color:#1e3a8a; color:#fff; padding:12px 24px; text-decoration:none; border-radius:5px; display:inline-block;">Set Your Akbartravels Password</a>
                    </div>

                    <p>This link is valid for <strong>two hours</strong> from your request initiation for password recovery.</p>
                    <p><strong>If you didn't request a password set you can delete this email.</strong></p>

                    <hr style="margin:30px 0;">
                    <p style="color:#f00;"><strong>Note</strong> - Please do not reply to this email. It has been sent from an email account that is not monitored.<br>
                    Please add this email address in your contact list to ensure that you receive communication for your booking.</p>

                    <div style="display:flex; justify-content:space-between; align-items:center; margin-top:30px;">
                        <div>
                            <a href="#"><img src="https://www.akbartravels.com/images/google-play.png" alt="Get it on Google Play" height="40"></a>
                            <a href="#"><img src="https://www.akbartravels.com/images/app-store.png" alt="Download on the App Store" height="40"></a>
                        </div>
                        <div style="text-align:right;">
                            <p style="margin:0;"><strong>📞</strong> +91 022 40 666 444</p>
                            <p style="margin:0;"><strong>✉️</strong> <a href="mailto:mysupport@sookunexpendia.com">mysupport@akbartravels.com</a></p>
                        </div>
                    </div>

                    <p style="text-align:center; color:#888; font-size:12px; margin-top:30px;">Copyright © 2019
                        <a href="https://www.akbartravels.com" style="color:#555;">www.sookunexpendia.com</a>. All Rights Reserved.
                    </p>
                </div>
                """.formatted(link);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset password email", e);
        }
    }
}
*/









   /* public void sendRegistrationEmail(String to, String token) {
        String subject = "Complete your registration";
        String link = "http://localhost:3000/set-password?token=" + token;

        String content = "<p>Hello,</p>"
                + "<p>You’ve started registration. Please complete it by setting your password:</p>"
                + "<a href=\"" + link + "\">Set Password</a>";

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
*/

/*
package com.sukoon.expiendia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.frontend.url}")  //  Inject the base URL from application.properties
    private String baseUrl;

    public void sendRegistrationEmail(String to, String token) {
        String subject = "Complete your registration";
        String link = baseUrl + "/set-password?token=" + token;  //  Use the configured URL

        //  Create a Thymeleaf context and set the 'link' variable.
        Context context = new Context();
        context.setVariable("link", link);

        //  Process the template with the context.
        String emailContent = templateEngine.process("registration-email.html", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");  //  Set encoding
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true); // true = HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Failed to send registration email", e);  //  Use custom exception
        }
    }

    //  Custom Exception for Email Sending
    public static class EmailException extends RuntimeException {
        public EmailException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
*/
