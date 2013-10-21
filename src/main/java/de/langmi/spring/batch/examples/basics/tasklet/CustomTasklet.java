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
package de.langmi.spring.batch.examples.basics.tasklet;

import org.springframework.batch.repeat.RepeatStatus;

/**
 * Example for a tasklet which works without implementing the Tasklet interface.
 *
 * @author Michael R. Pralow <me@michael-pralow.de>
 */
public class CustomTasklet {
    
    public RepeatStatus customMethod() throws Exception {
        // why not using println? because it makes testing harder, *nix and
        // windows think different about new line as in \n vs \r\n
        System.out.print("Hello World!");
        
        // we want the step to stop here, the other status 
        // RepeatStatus.CONTINUABLE would start an endless loop here
        return RepeatStatus.FINISHED;
    }
}
