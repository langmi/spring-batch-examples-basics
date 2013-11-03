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

import java.sql.Connection;
import java.sql.ResultSet;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;

/**
 * Tests for the JobTransactionManager.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class JobTransactionManagerTest {

    @Test
    public void simpleTest() throws Exception {
        JobTransactionManager tmFactory = new JobTransactionManager();
        tmFactory.setDataSource(new JobDataSource().dataSource());
        DataSourceTransactionManager tm = tmFactory.createTransactionManager();
        assertNotNull(tm);
    }

    @Test
    public void simpleTestWithTransaction() throws Exception {
        JobTransactionManager tmFactory = new JobTransactionManager();
        tmFactory.setDataSource(new JobDataSource().dataSource());
        DataSourceTransactionManager tm = tmFactory.createTransactionManager();
        assertNotNull(tm);

        TransactionStatus status = tm.getTransaction(null);
        DataSource ds = tm.getDataSource();
        assertNotNull(ds);
        testDataSourceWithConnection(ds);
        tm.commit(status);
    }

    @Test
    public void simpleTestWithTransactionWithRollback() throws Exception {
        JobTransactionManager tmFactory = new JobTransactionManager();
        tmFactory.setDataSource(new JobDataSource().dataSource());
        DataSourceTransactionManager tm = tmFactory.createTransactionManager();
        assertNotNull(tm);

        TransactionStatus status = tm.getTransaction(null);
        DataSource ds = tm.getDataSource();
        assertNotNull(ds);
        testDataSourceWithConnection(ds);
        tm.rollback(status);
    }

    @Test
    public void testWithSpringContext() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JobDataSource.class, JobTransactionManager.class);
        assertNotNull(context);
        DataSourceTransactionManager tm = context.getBean(DataSourceTransactionManager.class);
        assertNotNull(tm);
        
        TransactionStatus status = tm.getTransaction(null);
        DataSource ds = tm.getDataSource();
        assertNotNull(ds);
        testDataSourceWithConnection(ds);
        tm.commit(status);
    }

    private void testDataSourceWithConnection(DataSource ds) throws Exception {
        assertNotNull(ds);
        Connection con = null;
        try {
            con = ds.getConnection();
            ResultSet result = con.createStatement().executeQuery("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS");
            assertNotNull(result);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
