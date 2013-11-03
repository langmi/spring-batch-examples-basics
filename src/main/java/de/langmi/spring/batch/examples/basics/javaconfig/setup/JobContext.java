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
package de.langmi.spring.batch.examples.basics.javaconfig.setup;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
@Configuration
@EnableBatchProcessing
public class JobContext {

    @Bean
    public JobOperator jobOperator(final JobLauncher jobLauncher, final JobExplorer jobExplorer,
            final JobRepository jobRepository, final JobRegistry jobRegistry) throws Exception {
        return new SimpleJobOperator() {
            {
                setJobLauncher(jobLauncher);
                setJobExplorer(jobExplorer);
                setJobRepository(jobRepository);
                setJobRegistry(jobRegistry);
            }
        };
    }

    @Bean
    public JobExplorerFactoryBean jobExplorer(final DataSource dataSource) throws Exception {
        return new JobExplorerFactoryBean() {
            {
                setDataSource(dataSource);
            }
        };
    }

    @Bean
    public MapJobRegistry jobRegistry() throws Exception {
        return new MapJobRegistry();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegisterBeanPostProcess(final JobRegistry jobRegistry) throws Exception {
        return new JobRegistryBeanPostProcessor() {
            {
                setJobRegistry(jobRegistry);
            }
        };
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepo = new JobRepositoryFactoryBean();
        jobRepo.setDataSource(dataSource);
        jobRepo.setTransactionManager(transactionManager);
        return jobRepo.getJobRepository();
    }
}
