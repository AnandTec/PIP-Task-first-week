package com.example.employee.batch;

import com.example.employee.dao.entity.Employee;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
@Slf4j
/**
 * Doing configuration for the Spring batch
 */
public class EmployeeBatchConfig {


    /**
     *Preparing a job, for job we need some parameter
     * listener,repository and steps
     */
    @Bean
    public Job getEmployeeJob(JobRepository jobRepository,
                              JobExecutionListener listener, Step step){
       return new JobBuilder("employeeJob",jobRepository)
//               .incrementer(new RunIdIncrementer())
               .listener(listener)
               .start(step)
               .build();
    }

    /**
     * Implementing listener for the Job
     */
    @Bean
    public JobExecutionListener listener(){

        return new JobExecutionListener() {
            @Override
            public void beforeJob(@NonNull JobExecution jobExecution) {
                log.info("Job Executing started......");
            }
            @Override
            public void afterJob(@NonNull JobExecution jobExecution) {
                if(jobExecution.getStatus()== BatchStatus.COMPLETED)
                    log.info("Employee Job Completed.......");
            }
        };
    }

    /**
     * Preparing Step for the job
     * @param jobRepository
     * @param transactionManager
     * @param reader
     * @param processor
     * @param writer
     * @return
     */
    @Bean
    public Step steps(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ItemReader<Employee> reader, ItemProcessor<Employee,Employee> processor,
                      ItemWriter<Employee> writer){

        return new StepBuilder("employeeStep", jobRepository)
                .<Employee,Employee>chunk(5,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /**
     * Preparing Item Reader for th stops
     * @param dataSource
     * @return
     */
    @Bean
    public ItemReader<Employee> itemReader(@Autowired DataSource dataSource){

        return new JdbcCursorItemReaderBuilder<Employee>()
                .name("employeeReader")
                .sql("select user_name,password,salary from employee")
                .dataSource(dataSource)
//                .beanRowMapper(Employee.class)
                .rowMapper(new BeanPropertyRowMapper<>(Employee.class))
                .saveState(false)
                .build();
    }

    /**
     * Preparing processor for the step
     * @return
     */
    @Bean
    public ItemProcessor<Employee,Employee> itemProcessor(){

        return new ItemProcessor<Employee, Employee>() {
            @Override
            public Employee process(@NonNull Employee item) throws Exception {
                log.info("Processing are doing");
                return item;
            }
        };
    }

    /**
     * Preparing Item Writer for the step
     * @return
     */
    @Bean
    public ItemWriter<Employee> itemWriter(){
        return new FlatFileItemWriterBuilder<Employee>()
                .name("employeeWriter")
                .resource(new FileSystemResource("src\\main\\resources\\employee.csv"))
//                .resource((WritableResource) new ClassPathResource("employee.csv"))
                .delimited()
                .delimiter(",")
                .names("userName","password","salary")
                .build();

    }

}
