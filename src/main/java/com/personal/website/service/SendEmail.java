package com.personal.website.service;

import com.personal.website.entity.ProjectDetailsEntity;
import com.personal.website.entity.UserEntity;
import com.personal.website.model.ERole;
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
    public void sendSuccessEmail(UserEntity userEntity) throws MailException, InterruptedException
    {
        Thread.sleep(10000);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userEntity.getEmail());
        mail.setFrom(emailSender);
        mail.setSubject("Successful subscription");
        mail.setText("Thanks for subscribing to my services " + userEntity.getUserName() +". More services awaits you. Go and log in now");
        javaMailSender.send(mail);
    }

    @Async
    public void sendProjectAddedEmail(List<UserEntity> subscriberEntity, ProjectDetailsEntity projectDetailsEntity) throws MailException, InterruptedException
    {
        Thread.sleep(10000);

        //construct project details
        String projectDetails = "\nProject Details\nName :"+projectDetailsEntity.getName()+"\nDetails: "+projectDetailsEntity.getDescription()+
                                "\nLocation link: "+projectDetailsEntity.getLocationLink();

        //send only to subscribers since they only have a single role as user
        subscriberEntity.forEach(subscriber->{
                if(subscriber.getRoles().size()==1)
                {
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(subscriber.getEmail());
                    mail.setFrom(emailSender);
                    mail.setSubject("New Project Added");
                    mail.setText("Hie "+ subscriber.getUserName()+" "+emailSender+" has added a new project\n"+projectDetails);
                    javaMailSender.send(mail);
                }

        });

    }
}
