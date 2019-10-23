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
package net.lr.osgibp.it;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.notNullValue;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerSuite.class)
public class TaskResourceTest extends BaseITest {

    @Inject
    public ClientBuilder clientBuilder;
    
    private WebTarget target;

    @Configuration
    public Option[] config() {
        return super.config();
    }
    
    @Before
    public void before() {
        target = clientBuilder.build().target("http://localhost:8181/tasks");
    }

    private Response getAll() {
        return target.request().get();
    }
    
    @Test
    public void testGetAll() {
        Response response = await().atMost(20, TimeUnit.SECONDS).until(this::getAll, notNullValue());
        log.info(response.getEntity().toString());
    }


}
