package it.uniroma2.dicii.isw2.jcs.paramTests;

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
import org.apache.jcs.access.exception.CacheException;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Random;

/**
 * Simple test for the JCS class.
 *
 * @version $Id: JCSUniTest.java 536904 2007-05-10 16:03:42Z tv $
 */
public class JCSUniTest {
	
    private Random random;
    private JCS jcs;
    private String key_value;
    private LinkedList list;
    int start;
    int end;

    /**
     * @param args
     */
    @Before
    public void configure() throws Exception {
        random = new Random();
        jcs = JCS.getInstance("testCache1");
        key_value = "some:key";
        list = buildList();
        start = 0;
        end = 100000; //100; 10000
    }

    /**
     * @throws CacheException 
     */
    @Test
    public void testJCS() throws CacheException {
        jcs.put(key_value, list);

        assertEquals( list, jcs.get(key_value));  //test
    }

    private LinkedList buildList()
    {
        LinkedList list = new LinkedList();

        for ( int i = start; i < end; i++ )
        {
            list.add( buildMap() );
        }

        return list;
    }

    private HashMap buildMap()
    {
        HashMap map = new HashMap();

        byte[] keyBytes = new byte[32];
        byte[] valBytes = new byte[128];

        for ( int i = 0; i < 10; i++ )
        {
            random.nextBytes( keyBytes );
            random.nextBytes( valBytes );

            map.put( new String( keyBytes ), new String( valBytes ) );
        }

        return map;
    }

}
