package it.uniroma2.dicii.isw2.jcs.paramTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import org.apache.jcs.JCS;

/**
 * Simple methods to be run by active test suites that test removal.
 *
 */
public class RemovalTestUtil {
	
	private JCS jcs;
	String key;
	String data;
	
	public void configure() throws Exception {
		jcs = JCS.getInstance( "testCache1" );
		key = ":key";
		data = "data";
	}
	
	public RemovalTestUtil(String test) throws Exception {
		System.out.println( test );
		configure();
	}

    /**
     * Constructor for the TestSimpleLoad object
     *
     * @param testName
     *            Description of the Parameter
     */

    /**
     * Adds elements in the range specified and then removes them using the
     * categorical or substring removal method.
     *
     * @param start
     * @param end
     *
     * @exception Exception
     *                Description of the Exception
     */
    public void runTestPutThenRemoveCategorical( int start, int end )
        throws Exception
    {

        for ( int i = start; i <= end; i++ )
        {
            jcs.put( i + key, data + i );
        }

        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + key );
            if ( res == null )
            {
                assertNotNull( "[" + i + key + "] should not be null", res );  // test
            }
        }
        System.out.println( "Confirmed that " + ( end - start ) + " items could be found" );

        for ( int i = start; i <= end; i++ )
        {
            jcs.remove( i + ":" );
            assertNull( jcs.get( i + key ) );	// test
        }
        System.out.println( "Confirmed that " + ( end - start ) + " items were removed" );

        System.out.println( jcs.getStats() );

    }

    /**
     * Put items in the cache in this key range. Can be used to verify that
     * concurrent operations are not effected by things like hierchical removal.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @throws Exception
     */
    public void runPutInRange( int start, int end ) throws Exception {

        for ( int i = start; i <= end; i++ )
        {
            jcs.put( i + key, data + i );
        }

        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + key );
            if ( res == null )
            {
                assertNotNull( "[" + i + key + "] should not be null", res );		// test
            }
        }

    }

    /**
     * Just get from start to end.
     *
     * @param start
     *            int
     * @param end
     *            int
     * @param check
     *            boolean -- check to see if the items are in the cache.
     * @throws Exception
     */
    public void runGetInRange( int start, int end, boolean check) throws Exception {

        // don't care if they are found
        for ( int i = end; i >= start; i-- )
        {
            String res = (String) jcs.get( i + key );
            if ( check && res == null )
            {
                assertNotNull( "[" + i + key + "] should not be null", res );		// test
            }

        }

    }

}
