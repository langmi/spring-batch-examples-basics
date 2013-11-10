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

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Job definition with pure Java. For this example i go without any re-usable
 * code.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class PureJavaJobFactory {

    public static Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);

        Step step = stepBuilderFactory.get("step1").tasklet(new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                // why not using println? because it makes testing harder, *nix and
                // windows think different about new line as in \n vs \r\n
                System.out.print("Hello World!");

                // we want the step to stop here, the other status 
                // RepeatStatus.CONTINUABLE would start an endless loop
                return RepeatStatus.FINISHED;

            }
        }).build();

        return jobBuilderFactory.get("job1").start(step).build();
    }

}
