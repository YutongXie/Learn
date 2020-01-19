package com.huitong.learn.listener;

import com.huitong.learn.entity.Person;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            jdbcTemplate.query("Select first_name, last_name, address from person", (rs, row) -> new Person(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3))
            ).forEach(person -> System.out.println("Found " + person.toString() + " in database"));
        }
    }
}
