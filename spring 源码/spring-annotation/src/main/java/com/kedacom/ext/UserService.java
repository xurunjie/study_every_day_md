package com.kedacom.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent applicationEvent) {
        System.out.println("UserService 监听事件; " + applicationEvent);
    }
}
