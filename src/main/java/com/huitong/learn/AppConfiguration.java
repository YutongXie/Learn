package com.huitong.learn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Value("${app.ticketRecord.executor.corePoolSize}")
    private int ticketRecordCorePoolSize;

    @Bean
    public int ticketRecordCorePoolSize() {
        return ticketRecordCorePoolSize;
    }
}
