package com.example.demo.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        // Log ngoại lệ
        logger.error("Ngoại lệ không bắt được xảy ra trong phương thức async: " + method.getName(), throwable);
        System.out.println("Ngoại lệ không bắt được xảy ra trong phương thức async: " + method.getName() + throwable);

        // Xử lý bổ sung (nếu cần) như gửi email hoặc ghi vào cơ sở dữ liệu
    }
}