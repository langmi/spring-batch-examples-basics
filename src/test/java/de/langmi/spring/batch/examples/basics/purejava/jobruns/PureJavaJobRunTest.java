/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.langmi.spring.batch.examples.basics.purejava.jobruns;

import de.langmi.spring.batch.examples.basics.purejava.PureJavaJobFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class PureJavaJobRunTest {

    /**
     * Stream for catching System.out.
     */
    private final ByteArrayOutputStream newSysOut = new ByteArrayOutputStream();
    private PrintStream oldSysOut;
    private JobLauncher jobLauncher;
    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;

    @Before
    public void setup() throws Exception {
        // catch and set new system out
        oldSysOut = System.out;
        System.setOut(new PrintStream(newSysOut));

        setupBatchInfrastructure();
    }

    private void setupBatchInfrastructure() throws Exception {
        // in-memory variants for important bean
        this.transactionManager = new ResourcelessTransactionManager();
        this.jobRepository = new MapJobRepositoryFactoryBean(transactionManager).getJobRepository();

        // setup job launcher
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        // with a SyncTaskExecutor the job is launched within the actual thread
        simpleJobLauncher.setTaskExecutor(new SyncTaskExecutor());
        simpleJobLauncher.setJobRepository(jobRepository);

        this.jobLauncher = simpleJobLauncher;
    }

    /**
     * Running the simple Job with a simple setup.
     *
     * @throws Exception
     */
    @Test
    public void testJobConfiguration() throws Exception {
        // creating the job
        Job job = PureJavaJobFactory.createJob(jobRepository, transactionManager);

        // running the job
        JobExecution execution = this.jobLauncher.run(job, new JobParameters());

        // assertions
        assertEquals(BatchStatus.COMPLETED, execution.getStatus());
        assertEquals(1, execution.getStepExecutions().size());
        assertEquals("Hello World!", newSysOut.toString());
    }

    @After
    public void tearDown() {
        // reset JVM standard
        System.setOut(oldSysOut);
    }

}
