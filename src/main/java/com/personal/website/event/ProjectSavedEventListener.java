package com.personal.website.event;

import com.personal.website.entity.UserEntity;
import com.personal.website.repository.UserRepository;
import com.personal.website.service.SendEmail;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectSavedEventListener implements ApplicationListener<ProjectSavedEvent>
{

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Async
    @Override
    public void onApplicationEvent(ProjectSavedEvent event)
    {
        Thread.sleep(10000);

        List<UserEntity> userEntityList = userRepository.findAll();
        if(userEntityList.isEmpty())
            throw new Exception("");

        sendEmail.sendProjectAddedEmail(userEntityList,event.getProjectDetailsEntity());

    }
}
