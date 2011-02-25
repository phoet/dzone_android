package de.nofail.dzone;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;

public class TestRunner extends InstrumentationTestRunner {

	@Override
	public TestSuite getAllTests() {
		TestSuite suite = new InstrumentationTestSuite(this);
		suite.addTestSuite(ItemDataTest.class);
		return suite;
	}

	@Override
	public ClassLoader getLoader() {
		return TestRunner.class.getClassLoader();
	}
}
