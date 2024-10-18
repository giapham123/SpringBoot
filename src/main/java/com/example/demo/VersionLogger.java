package com.example.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This file will show the version SPRING SECURITY
@Component
public class VersionLogger implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(VersionLogger.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Spring Security Version: {}", SpringSecurityCoreVersion.getVersion());
    }
}