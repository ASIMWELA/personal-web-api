package com.personal.website.service;

import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.SubscriberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendEmail
{
    private JavaMailSender javaMailSender;

    @Value("${app.emailOrigin}")
    private String emailSender;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    //send email asynchronously to the subscriberEntity
    @Async
    public void sendSuccessEmail(SubscriberEntity subscriberEntity) throws MailException, InterruptedException
    {
        Thread.sleep(10000);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(subscriberEntity.getEmail());
        mail.setFrom(emailSender);
        mail.setSubject("Successful subscription");
        mail.setText("Thanks for subscribing to my services " + subscriberEntity.getFirstName() +". More services awaits you. Go and log in now");
        javaMailSender.send(mail);
    }

    @Async
    public void sendProjectAddedEmail(List<SubscriberEntity> subscriberEntity, ProjectDetailsEntity projectDetailsEntity) throws MailException, InterruptedException
    {
        Thread.sleep(10000);

        //construct project details
        String projectDetails = "\nProject Details\nName :"+projectDetailsEntity.getName()+"\nDetails: "+projectDetailsEntity.getDescription()+
                                "\nLocation link: "+projectDetailsEntity.getLocationLink();

        subscriberEntity.forEach(subscriber->{
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(subscriber.getEmail());
            mail.setFrom(emailSender);
            mail.setSubject("New Project Added");
            mail.setText("Hie "+ subscriber.getFirstName()+" "+emailSender+" has added a new project\n"+projectDetails);
            javaMailSender.send(mail);
        });

    }
}
