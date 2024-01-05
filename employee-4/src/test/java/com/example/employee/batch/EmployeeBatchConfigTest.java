package com.example.employee.batch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.example.employee.dao.entity.Employee;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobKeyGenerator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.job.flow.FlowStep;
import org.springframework.batch.core.listener.CompositeJobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.JdbcExecutionContextDao;
import org.springframework.batch.core.repository.dao.JdbcJobExecutionDao;
import org.springframework.batch.core.repository.dao.JdbcJobInstanceDao;
import org.springframework.batch.core.repository.dao.JdbcStepExecutionDao;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.core.step.item.ChunkOrientedTasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;

//@ContextConfiguration(classes = {EmployeeBatchConfig.class})
//@ExtendWith(SpringExtension.class)

class EmployeeBatchConfigTest {
    @Autowired
    private EmployeeBatchConfig employeeBatchConfig;

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#getEmployeeJob(JobRepository, JobExecutionListener, Step)}
     */
    @Test
    void testGetEmployeeJob() {

        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        JdbcJobInstanceDao jobInstanceDao = new JdbcJobInstanceDao();
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(jobInstanceDao, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        CompositeJobExecutionListener listener = new CompositeJobExecutionListener();
        Job actualEmployeeJob = employeeBatchConfig.getEmployeeJob(jobRepository, listener, new FlowStep());
        assertTrue(actualEmployeeJob.getJobParametersValidator() instanceof DefaultJobParametersValidator);
        assertEquals("employeeJob", actualEmployeeJob.getName());
        assertEquals(1, ((SimpleJob) actualEmployeeJob).getStepNames().size());
        assertTrue(actualEmployeeJob.isRestartable());
    }

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#getEmployeeJob(JobRepository, JobExecutionListener, Step)}
     */
    @Test
    void testGetEmployeeJob2() {

        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(null, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        CompositeJobExecutionListener listener = new CompositeJobExecutionListener();
        Job actualEmployeeJob = employeeBatchConfig.getEmployeeJob(jobRepository, listener, new FlowStep());
        assertTrue(actualEmployeeJob.getJobParametersValidator() instanceof DefaultJobParametersValidator);
        assertEquals("employeeJob", actualEmployeeJob.getName());
        assertEquals(1, ((SimpleJob) actualEmployeeJob).getStepNames().size());
        assertTrue(actualEmployeeJob.isRestartable());
    }

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#getEmployeeJob(JobRepository, JobExecutionListener, Step)}
     */
    @Test
    void testGetEmployeeJob3() {

        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();

        JdbcJobInstanceDao jobInstanceDao = new JdbcJobInstanceDao();
        jobInstanceDao.setJobKeyGenerator(mock(JobKeyGenerator.class));
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(jobInstanceDao, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        CompositeJobExecutionListener listener = new CompositeJobExecutionListener();
        Job actualEmployeeJob = employeeBatchConfig.getEmployeeJob(jobRepository, listener, new FlowStep());
        assertTrue(actualEmployeeJob.getJobParametersValidator() instanceof DefaultJobParametersValidator);
        assertEquals("employeeJob", actualEmployeeJob.getName());
        assertEquals(1, ((SimpleJob) actualEmployeeJob).getStepNames().size());
        assertTrue(actualEmployeeJob.isRestartable());
    }

    /**
     * Method under test: {@link EmployeeBatchConfig#listener()}
     */
    @Test
    void testListener() {
        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        employeeBatchConfig.listener();
        assertEquals("employeeWriter", ((FlatFileItemWriter<Employee>) employeeBatchConfig.itemWriter()).getName());
    }

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#steps(JobRepository, PlatformTransactionManager, ItemReader, ItemProcessor, ItemWriter)}
     */
    @Test
    void testSteps() {
        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        JdbcJobInstanceDao jobInstanceDao = new JdbcJobInstanceDao();
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(jobInstanceDao, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        Step actualStepsResult = employeeBatchConfig.steps(jobRepository, new ResourcelessTransactionManager(),
                mock(ItemReader.class), mock(ItemProcessor.class), mock(ItemWriter.class));
        assertTrue(((TaskletStep) actualStepsResult).getTasklet() instanceof ChunkOrientedTasklet);
        assertEquals("employeeStep", actualStepsResult.getName());
        assertFalse(actualStepsResult.isAllowStartIfComplete());
        assertEquals(Integer.MAX_VALUE, actualStepsResult.getStartLimit());
    }

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#steps(JobRepository, PlatformTransactionManager, ItemReader, ItemProcessor, ItemWriter)}
     */
    @Test
    void testSteps2() {
        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(null, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        Step actualStepsResult = employeeBatchConfig.steps(jobRepository, new ResourcelessTransactionManager(),
                mock(ItemReader.class), mock(ItemProcessor.class), mock(ItemWriter.class));
        assertTrue(((TaskletStep) actualStepsResult).getTasklet() instanceof ChunkOrientedTasklet);
        assertEquals("employeeStep", actualStepsResult.getName());
        assertFalse(actualStepsResult.isAllowStartIfComplete());
        assertEquals(Integer.MAX_VALUE, actualStepsResult.getStartLimit());
    }

    /**
     * Method under test:
     * {@link EmployeeBatchConfig#steps(JobRepository, PlatformTransactionManager, ItemReader, ItemProcessor, ItemWriter)}
     */
    @Test
    void testSteps3() {
        EmployeeBatchConfig employeeBatchConfig = new EmployeeBatchConfig();
        JdbcJobInstanceDao jobInstanceDao = new JdbcJobInstanceDao();
        JdbcJobExecutionDao jobExecutionDao = new JdbcJobExecutionDao();
        JdbcStepExecutionDao stepExecutionDao = new JdbcStepExecutionDao();
        SimpleJobRepository jobRepository = new SimpleJobRepository(jobInstanceDao, jobExecutionDao, stepExecutionDao,
                new JdbcExecutionContextDao());

        ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();
        Step actualStepsResult = employeeBatchConfig.steps(jobRepository, transactionManager, new RepositoryItemReader<>(),
                mock(ItemProcessor.class), mock(ItemWriter.class));
        assertTrue(((TaskletStep) actualStepsResult).getTasklet() instanceof ChunkOrientedTasklet);
        assertEquals("employeeStep", actualStepsResult.getName());
        assertFalse(actualStepsResult.isAllowStartIfComplete());
        assertEquals(Integer.MAX_VALUE, actualStepsResult.getStartLimit());
    }

    /**
     * Method under test: {@link EmployeeBatchConfig#itemProcessor()}
     */
    @Test
    void testItemProcessor() throws Exception {
        ItemProcessor<Employee, Employee> actualItemProcessorResult = (new EmployeeBatchConfig()).itemProcessor();
        Employee employee = new Employee();
        employee.setPassword("iloveyou");
        employee.setSalary(10.0d);
        employee.setUserName("janedoe");
        assertSame(employee, actualItemProcessorResult.process(employee));
    }

    /**
     * Method under test: {@link EmployeeBatchConfig#itemWriter()}
     */
    @Test
//    @Disabled("TODO: Complete this test")
    void testItemWriter() {
        employeeBatchConfig = new EmployeeBatchConfig();

        employeeBatchConfig.itemWriter();
    }
}
