package com.huitong.learn;

import com.huitong.learn.entity.Person;
import com.huitong.learn.listener.JobCompletionNotificationListener;
import com.huitong.learn.listener.SkipListener;
import com.huitong.learn.processor.batch.PersonItemProcessor;
import com.huitong.learn.processor.batch.EnrichDataProcessor;
import com.huitong.learn.processor.batch.PublishDataProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public JobLauncher jobLauncher;
    @Autowired
    public DataSource dataSource;
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void run() {
        String dateStr = new Date().toString();
        JobParameters jobParameter = new JobParametersBuilder().addString("date", dateStr).toJobParameters();
        try {
            jobLauncher.run(importUserJob(), jobParameter);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv")).delimited()
                .names(new String[]{"firstName", "lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                    setTargetType(Person.class);
                }}).build();
    }

    @Bean
    public PersonItemProcessor toUpperCaseProcessor() {
        return new PersonItemProcessor();
    }

    @Bean
    public EnrichDataProcessor enrichDataProcessor() {return new EnrichDataProcessor();
    }

    @Bean
    public PublishDataProcessor publishDataProcessor() {return new PublishDataProcessor();}

    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        return new JdbcBatchItemWriterBuilder<Person>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("Insert into person(first_name, last_name, address) values (:firstName, :lastName, :address)").dataSource(dataSource)
                .build();
    }
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener(jdbcTemplate)).start(step_fetch_enrich_Person()).build();
    }

    @Bean
    public Step step_fetch_enrich_Person() {
        CompositeItemProcessor processor = new CompositeItemProcessor();
        List<ItemProcessor> processList = new ArrayList();
        processList.add(toUpperCaseProcessor());
        processList.add(enrichDataProcessor());
        processList.add(publishDataProcessor());
        processor.setDelegates(processList);

        return stepBuilderFactory.get("toUpperCaseProcessor").<Person, Person>chunk(10)
                .reader(reader())
                .processor(processor)
                .writer(writer())
                .faultTolerant()
                .skipLimit(10)
                .skip(Exception.class)
                .listener(new SkipListener())
                .retryLimit(3)
                .retry(RuntimeException.class)
                .taskExecutor(taskExecutor())
                .throttleLimit(20)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
        poolExecutor.setMaxPoolSize(10);
        poolExecutor.afterPropertiesSet();
        return  poolExecutor;
    }
}
