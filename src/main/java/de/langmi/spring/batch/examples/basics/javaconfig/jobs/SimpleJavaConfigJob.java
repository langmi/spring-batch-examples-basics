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
package de.langmi.spring.batch.examples.basics.javaconfig.jobs;

import de.langmi.spring.batch.examples.basics.tasklet.SimpleTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Example for a simple Job with JavaConfig.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
@Configuration
public class SimpleJavaConfigJob {

    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job simpleJob() throws Exception {
        SimpleJobBuilder builder = jobs.get("test").start(step1());
        return builder.build();
    }

    @Bean
    protected Step step1() throws Exception {
        return steps.get("step1").tasklet(new SimpleTasklet()).build();
    }

}
