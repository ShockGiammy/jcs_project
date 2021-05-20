package it.uniroma2.dicii.isw2.jcs.paramTests;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.junit.Before;
import org.junit.Test;

import junit.extensions.ActiveTestSuite;

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
	
	private ActiveTestSuite suite;
    
	@Before
    public void configure() throws CacheException {
		JCS.setConfigFilename( "/TestRemoval.ccf" );
        JCS.getInstance( "testCache1" );
        suite = new ActiveTestSuite();
        
    }

    /**
     * A unit test suite for JUnit. This verfies that we can remove
     * hierarchically while the region is active.
     * @return 
     *
     * @return The test suite
     */
	@Test
    public void TestSuite() {

        suite.addTest( new RemovalTestUtil( "testRemoveCache1" )
        {
            public void runTest()
                throws Exception
            {
                runTestPutThenRemoveCategorical( 0, 200 );
            }
        } );

        suite.addTest( new RemovalTestUtil( "testPutCache1" )
        {
            public void runTest()
                throws Exception
            {
                runPutInRange( 300, 400 );
            }
        } );

        suite.addTest( new RemovalTestUtil( "testPutCache2" )
        {
            public void runTest()
                throws Exception
            {
                runPutInRange( 401, 600 );
            }
        } );

        // stomp on previous put
        suite.addTest( new RemovalTestUtil( "testPutCache3" )
        {
            public void runTest()
                throws Exception
            {
                runPutInRange( 401, 600 );
            }
        } );

        suite.addTest( new RemovalTestUtil( "testRemoveCache1" )
        {
            public void runTest()
                throws Exception
            {
                runTestPutThenRemoveCategorical( 601, 700 );
            }
        } );

        suite.addTest( new RemovalTestUtil( "testRemoveCache1" )
        {
            public void runTest()
                throws Exception
            {
                runTestPutThenRemoveCategorical( 701, 800 );
            }
        } );

        suite.addTest( new RemovalTestUtil( "testPutCache2" )
        {
            // verify that there are no errors with concurrent gets.
            public void runTest()
                throws Exception
            {
                runGetInRange( 0, 1000, false );
            }
        } );
    }
}
