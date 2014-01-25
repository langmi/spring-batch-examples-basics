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
import static org.junit.Assert.*;
import javax.sql.DataSource;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Tests for the JobDataSource.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class JobDataSourceTest {

    @Test
    public void simpleTest() throws Exception {
        DataSource ds = new JobDataSource().dataSource();
        assertNotNull(ds);
    }

    @Test
    public void simpleTestWithConnection() throws Exception {
        testDataSourceWithConnection(new JobDataSource().dataSource());
    }

    @Test
    public void testWithSpringContext() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JobDataSource.class);
        assertNotNull(context);
        testDataSourceWithConnection(context.getBean(DataSource.class));
    }

    private void testDataSourceWithConnection(DataSource ds) throws Exception {
        assertNotNull(ds);
        try (Connection con = ds.getConnection()) {
            ResultSet result = con.createStatement().executeQuery("SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS");
            assertNotNull(result);
        }
    }
}
