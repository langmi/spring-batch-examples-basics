/* Licensed under the Apache License, Version 2.0 (the "License");
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
package de.langmi.spring.batch.examples.basics.taskletstep.configstyles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JobConfigurationTest.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
@ContextConfiguration(locations = {
    "classpath*:spring/batch/job/tasklet/config-styles/simple-taskletstep-job-config-style-2.xml",
    "classpath*:spring/batch/setup/**/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleTaskletStepJobStyleTwoConfigurationTest {

    /**
     * Stream for catching System.out.
     */
    private final ByteArrayOutputStream newSysOut = new ByteArrayOutputStream();
    private PrintStream oldSysOut;
    /**
     * JobLauncherTestUtils Bean.
     */
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    /**
     * Launch Test.
     */
    @Test
    public void launchJob() throws Exception {
        // launch the job, the utils provide the job with a unique parameter
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
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
