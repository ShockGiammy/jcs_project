package it.uniroma2.dicii.isw2.jcs.paramTests;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Before;
import org.junit.Test;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Test which exercises the hierarchical removal when the cache is active.
 */
public class ConcurrentRemovalLoadTest {
	
	RemovalTestUtil testUtilRemove;
	RemovalTestUtil testUtilPut;
	RemovalTestUtil testUtilPut2;
    RemovalTestUtil testUtilPut3;
    RemovalTestUtil testUtilRemove2;
    RemovalTestUtil testUtilRemove3;
    RemovalTestUtil testUtilPut4;
    
	@Before
    public void configure() throws Exception {
		JCS.setConfigFilename( "/TestRemoval.ccf" );
        JCS.getInstance( "testCache1" );
        testUtilRemove = new RemovalTestUtil( "testRemoveCache1" );
		testUtilPut = new RemovalTestUtil( "testPutCache1" );
		testUtilPut2 = new RemovalTestUtil( "testPutCache2" );
		testUtilPut3 = new RemovalTestUtil( "testPutCache3" );
		testUtilRemove2 = new RemovalTestUtil( "testRemoveCache1" );
		testUtilRemove3 = new RemovalTestUtil( "testRemoveCache1" );
		testUtilPut4 = new RemovalTestUtil( "testPutCache2" );
    }

    /**
     * A unit test suite for JUnit. This verfies that we can remove
     * hierarchically while the region is active.
     * @return 
     *
     * @return The test suite
     * @throws Exception 
     */
	@Test
    public void TestSuite() throws Exception {

		testUtilRemove.runTestPutThenRemoveCategorical( 0, 200 );

		testUtilPut.runPutInRange( 300, 400 );
       
		testUtilPut2.runPutInRange( 401, 600 );

        // stomp on previous put
        testUtilPut3.runPutInRange( 401, 600 );
        
        testUtilRemove2.runTestPutThenRemoveCategorical( 601, 700 );

        testUtilRemove3.runTestPutThenRemoveCategorical( 701, 800 );

       	testUtilPut4.runGetInRange( 0, 1000, false );

    }
}
