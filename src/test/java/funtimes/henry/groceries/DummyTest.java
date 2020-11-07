package funtimes.henry.groceries;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DummyTest {

    @Test
    public void testSayHello() {
        Dummy dummy = new Dummy();
        assertEquals("hello", dummy.sayHello());
    }
}
