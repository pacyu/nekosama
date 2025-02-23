package com.pacyu.blog.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class SmtpService {

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "mail/text/emailtext.txt";
    private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "html/email-simple";
    private static final String EMAIL_WITHATTACHMENT_TEMPLATE_NAME = "html/email-withattachment";
    private static final String EMAIL_INLINEIMAGE_TEMPLATE_NAME = "html/email-inlineimage";
    private static final String EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES = "classpath:mail/editablehtml/email-editable.html";

    private static final String BACKGROUND_IMAGE = "mail/editablehtml/images/background.png";
    private static final String LOGO_BACKGROUND_IMAGE = "mail/editablehtml/images/logo-background.png";
    private static final String THYMELEAF_BANNER_IMAGE = "mail/editablehtml/images/thymeleaf-banner.png";
    private static final String THYMELEAF_LOGO_IMAGE = "mail/editablehtml/images/thymeleaf-logo.png";

    private static final String PNG_MIME = "image/png";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private TemplateEngine textTemplateEngine;

    @Autowired
    private TemplateEngine stringTemplateEngine;



    /*
     * Send plain TEXT mail
     */
    public void sendTextMail(
        final String senderName, final String senderEmail, final String title, final String content,
        final String recipientName, final String recipientEmail, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("recipientName", "@" + recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("content", content);
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        if (!Objects.equals(title, ""))
            message.setSubject("您有一条来自 nekosama.club 的文章「" + title + "」下的回复");
        else
            message.setSubject("来自 " + recipientName + " 的问题");
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);

        // Create the plain TEXT body using Thymeleaf
        final String textContent = this.textTemplateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);
        message.setText(textContent);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail (simple)
     */
    public void sendSimpleMail(
        final String senderName, final String senderEmail, final String title, final String content,
        final String recipientName, final String recipientEmail, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("recipientName", "@" + recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("content", content);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        if (title != "")
            message.setSubject("您有一条来自 nekosama.club 的文章「" + title + "」下的回复");
        else
            message.setSubject("来自 " + recipientName + " 的问题");
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Send email
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with attachment.
     */
    public void sendMailWithAttachment(
        final String senderName, final String senderEmail, final String title, final String content,
        final String recipientName, final String recipientEmail, final String attachmentFileName,
        final byte[] attachmentBytes, final String attachmentContentType, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("recipientName", "@" + recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("content", content);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        if (title != "")
            message.setSubject("您有一条来自 nekosama.club 的文章「" + title + "」下的回复");
        else
            message.setSubject("来自 " + recipientName + " 的问题");
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_WITHATTACHMENT_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the attachment
        final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
        message.addAttachment(
            attachmentFileName, attachmentSource, attachmentContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with inline image
     */
    public void sendMailWithInline(
        final String senderName, final String senderEmail, final String title, final String content,
        final String recipientName, final String recipientEmail, final String imageResourceName,
        final byte[] imageBytes, final String imageContentType, final Locale locale)
        throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("recipientName", "@" + recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("content", content);
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
            = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        if (title != "")
            message.setSubject("您有一条来自 nekosama.club 的文章「" + title + "」下的回复");
        else
            message.setSubject("来自 " + recipientName + " 的问题");
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = htmlTemplateEngine.process(EMAIL_INLINEIMAGE_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }


    /*
     * Send HTML mail with inline image
     */
    public String getEditableMailTemplate() throws IOException {
        final Resource templateResource = this.applicationContext.getResource(EMAIL_EDITABLE_TEMPLATE_CLASSPATH_RES);
        final InputStream inputStream = templateResource.getInputStream();
        return IOUtils.toString(inputStream, SmtpConfig.EMAIL_TEMPLATE_ENCODING);
    }


    /*
     * Send HTML mail with inline image
     */
    public void sendEditableMail(
        final String senderName, final String senderEmail, final String title, final String content,
        final String recipientName, final String recipientEmail, final String htmlContent,
        final Locale locale)
        throws MessagingException {

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        if (title != "")
            message.setSubject("您有一条来自 nekosama.club 的文章「" + title + "」下的回复");
        else
            message.setSubject("来自 " + recipientName + " 的问题");
        message.setFrom(senderEmail);
        message.setTo(recipientEmail);

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("senderName", senderName);
        ctx.setVariable("senderEmail", senderEmail);
        ctx.setVariable("recipientName", "@" + recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("content", content);

        // Create the HTML body using Thymeleaf
        final String output = stringTemplateEngine.process(htmlContent, ctx);
        message.setText(output, true /* isHtml */);

        // Add the inline images, referenced from the HTML code as "cid:image-name"
        message.addInline("background", new ClassPathResource(BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("logo-background", new ClassPathResource(LOGO_BACKGROUND_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-banner", new ClassPathResource(THYMELEAF_BANNER_IMAGE), PNG_MIME);
        message.addInline("thymeleaf-logo", new ClassPathResource(THYMELEAF_LOGO_IMAGE), PNG_MIME);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

}