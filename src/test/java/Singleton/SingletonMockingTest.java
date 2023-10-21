package Singleton;

import Singleton.SingletonHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SingletonMockingTest {
    @Test
    public void testGetInstance(){
        SingletonClass singletone = SingletonClass.getInstance();
        assertNotNull(singletone);
        Assertions.assertEquals(singletone, SingletonClass.getInstance());
    }

    @Test
    public void testSingletonClassUsingDocallRealMethod() {
        SingletonClass singletonObject = mock(SingletonClass.class);

//        Mockito.doCallRealMethod().when(singletonObject).isValid();
        boolean valid = singletonObject.isValid();
        verify(singletonObject, atLeastOnce()).isValid();
        assertTrue(valid);

        Mockito.doCallRealMethod().when(singletonObject).getSomeString();
        Assertions.assertEquals("singleton object",singletonObject.getSomeString());
    }

    @Test
    public void testSingletonClassUsingHandlerClass(){
        SingletonClass singletonObject = mock(SingletonClass.class);
        doReturn("singleton class").when(singletonObject).getSomeString();
        when(singletonObject.isValid()).thenReturn(false);

        SingletonHandler handler = new SingletonHandler(singletonObject);

        assertTrue(handler.isTestable());
        assertEquals("singleton object",handler.getString());
    }

    @Test
    void testCallRealMethod(){
        SingletonClass singletonMock = mock(SingletonClass.class);

        doCallRealMethod().when(singletonMock).isValid();
        assertEquals(true, singletonMock.isValid());
    }
}
