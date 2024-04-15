package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MessagingTest extends TestCase
{
    private App webSocketServer = new App(3300);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MessagingTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MessagingTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    //test that a bad word returns output A, and good word returns output B
    public void testMessaging()
    {
        Messaging messaging = new Messaging(webSocketServer);
        
        assertEquals(messaging.sendMsg("test1"), "Unfriendly Language");
        assertEquals(messaging.sendMsg("Good"), "Successful");

        assertEquals("Sent", messaging.updateTextBox("message"));
    }
}


