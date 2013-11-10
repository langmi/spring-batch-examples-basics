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
package de.langmi.spring.batch.examples.basics.purejava;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Tests for the simple pure java job.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class PureJavaJobTest {

    JobRepository jobRepository;
    PlatformTransactionManager transactionManager;

    /**
     * Some simple tests for the simple job.
     * 
     * @throws Exception
     */
    @Test
    public void testJob() throws Exception {
        Job job = PureJavaJobFactory.createJob(jobRepository, transactionManager);
        // assertions on job level
        assertNotNull(job);
        assertEquals("job1", job.getName());
    }
    
    /**
     * Test if the job is constructed as i wanted it to be.
     * 
     * @throws Exception 
     */
    @Test
    public void testJobStepConfig() throws Exception {
        Job job = PureJavaJobFactory.createJob(jobRepository, transactionManager);
        // assertions on job level
        assertNotNull(job);
        assertEquals("job1", job.getName());
        assertTrue(job instanceof SimpleJob);
        // assertions on step level
        assertNotNull(((SimpleJob)job).getStepNames());
        assertEquals(1, ((SimpleJob)job).getStepNames().size());
        Step step = ((SimpleJob)job).getStep("step1");
        assertNotNull(step);
        assertTrue(step instanceof TaskletStep);        
    }
    
    @Before
    public void setUp() throws Exception {
        this.transactionManager = new ResourcelessTransactionManager();
        this.jobRepository = new MapJobRepositoryFactoryBean(transactionManager).getJobRepository();
    }
}
