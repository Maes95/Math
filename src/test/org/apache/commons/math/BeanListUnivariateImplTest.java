/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.commons.math;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.math.beans.*;

/**
 * Test cases for the {@link BeanListUnivariateImpl} class.
 *
 * @author <a href="mailto:tobrien@apache.org">Tim O'Brien</a>
 * @version $Revision: 1.1 $ $Date: 2003/05/29 19:41:31 $
 */

public final class BeanListUnivariateImplTest extends TestCase {
    
    private List patientList = null;
    private double tolerance = Double.MIN_VALUE;
    
    public BeanListUnivariateImplTest(String name) {
        super(name);
    }
    
    public void setUp() {  
        patientList = new ArrayList();

        // Create and add patient bean 1
        VitalStats vs1 = new VitalStats( new Double(120.0), 
                                         new Double(96.4) );
        Patient p1 = new Patient( vs1, new Integer( 35 ) );
        patientList.add( p1 );

        // Create and add patient bean 2
        VitalStats vs2 = new VitalStats( new Double(70.0), 
                                         new Double(97.4) );
        Patient p2 = new Patient( vs2, new Integer( 23 ) );
        patientList.add( p2 );

        // Create and add patient bean 3
        VitalStats vs3 = new VitalStats( new Double(90.0), 
                                         new Double(98.6) );
        Patient p3 = new Patient( vs3, new Integer( 42 ) );
        patientList.add( p3 );
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(BeanListUnivariateImplTest.class);
        suite.setName("Freq Tests");
        return suite;
    }
    
    /** test stats */
    public void testStats() {
    	
        StoreUnivariate u = new BeanListUnivariateImpl( patientList ); 

        assertEquals("total count",3,u.getN(),tolerance);

        u.clear();
        assertEquals("total count",0,u.getN(),tolerance);    
    }     
    
    public void testPropStats() {

        StoreUnivariate heartU = new BeanListUnivariateImpl( patientList,
                                          "vitalStats.heartRate" );

        

        assertEquals( "Mean heart rate unexpected", 93.333, 
                      heartU.getMean(), 0.001 );
        assertEquals( "Max heart rate unexpected", 120.0, 
                      heartU.getMax(), 0.001 );

        StoreUnivariate ageU = new BeanListUnivariateImpl( patientList,
                                                           "age" );

        assertEquals( "Mean age unexpected", 33.333,
                      ageU.getMean(), 0.001 );
        assertEquals( "Max age unexpected", 42.0,
                      ageU.getMax(), 0.001 );

    }

    /*    public void testN0andN1Conditions() throws Exception {
    	List list = new ArrayList();
    	
    	StoreUnivariate u = new ListUnivariateImpl( list );
    	    	
		assertTrue("Mean of n = 0 set should be NaN", Double.isNaN( u.getMean() ) );
		assertTrue("Standard Deviation of n = 0 set should be NaN", Double.isNaN( u.getStandardDeviation() ) );
		assertTrue("Variance of n = 0 set should be NaN", Double.isNaN(u.getVariance() ) );

		list.add( new Double(one));

		assertTrue( "Mean of n = 1 set should be value of single item n1", u.getMean() == one);
		assertTrue( "StdDev of n = 1 set should be zero, instead it is: " + u.getStandardDeviation(), u.getStandardDeviation() == 0);
		assertTrue( "Variance of n = 1 set should be zero", u.getVariance() == 0);	
    }
    
    public void testSkewAndKurtosis() {
    	StoreUnivariate u = new StoreUnivariateImpl();
    	
    	double[] testArray = { 12.5, 12, 11.8, 14.2, 14.9, 14.5, 21, 8.2, 10.3, 11.3, 14.1,
  	  										 9.9, 12.2, 12, 12.1, 11, 19.8, 11, 10, 8.8, 9, 12.3 };
  	  	for( int i = 0; i < testArray.length; i++) {
  	  		u.addValue( testArray[i]);
  	  	}
  	  	
		assertEquals("mean", 12.40455, u.getMean(), 0.0001);
		assertEquals("variance", 10.00236, u.getVariance(), 0.0001);
		assertEquals("skewness", 1.437424, u.getSkewness(), 0.0001);
  	  	assertEquals("kurtosis", 2.37719, u.getKurtosis(), 0.0001);
    }

    public void testProductAndGeometricMean() throws Exception {
    	ListUnivariateImpl u = new ListUnivariateImpl(new ArrayList());
        u.setWindowSize(10);
    	    	
        u.addValue( 1.0 );
        u.addValue( 2.0 );
        u.addValue( 3.0 );
        u.addValue( 4.0 );

        assertEquals( "Product not expected", 24.0, u.getProduct(), Double.MIN_VALUE );
        assertEquals( "Geometric mean not expected", 2.213364, u.getGeometricMean(), 0.00001 );

        // Now test rolling - UnivariateImpl should discount the contribution
        // of a discarded element
        for( int i = 0; i < 10; i++ ) {
            u.addValue( i + 2 );
        }
        // Values should be (2,3,4,5,6,7,8,9,10,11)
        
        assertEquals( "Product not expected", 39916800.0, u.getProduct(), 0.00001 );
        assertEquals( "Geometric mean not expected", 5.755931, u.getGeometricMean(), 0.00001 );


        } */

}

