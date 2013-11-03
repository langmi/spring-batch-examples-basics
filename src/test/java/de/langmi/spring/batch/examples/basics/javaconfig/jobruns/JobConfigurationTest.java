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
package de.langmi.spring.batch.examples.basics.javaconfig.jobruns;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class JobConfigurationTest {

    /**
     * Stream for catching System.out.
     */
    private final ByteArrayOutputStream newSysOut = new ByteArrayOutputStream();
    private PrintStream oldSysOut;

    /**
     * 
     * @throws Exception 
     */
    @Test
    public void testJobConfiguration() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("de.langmi.spring.batch.examples.basics.javaconfig.*");
        assertNotNull(context);
        Job job = context.getBean(Job.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        JobExecution execution = jobLauncher
                .run(job, new JobParametersBuilder().addLong("run.id", (long) (Math.random() * Long.MAX_VALUE))
                        .toJobParameters());
        assertEquals(BatchStatus.COMPLETED, execution.getStatus());
        assertEquals(1, execution.getStepExecutions().size());
        context.close();
        // assert sysoutput
        assertEquals("Hello World!", newSysOut.toString());
    }

    @Before
    public void setup() {
        // catch and set new system out
        oldSysOut = System.out;
        System.setOut(new PrintStream(newSysOut));
    }

    @After
    public void tearDown() {
        // reset JVM standard
        System.setOut(oldSysOut);
    }

}
