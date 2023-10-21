package Login;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    LoginDAO loginDao;

    @Spy
    @InjectMocks
    LoginService spiedLoginService;

    @Mock    LoginService loginService;

    @InjectMocks
    LoginController loginController;

    @Test
    public void assertThatNoMethodHasBeenCalled(){
        MockitoAnnotations.openMocks(this);
        loginController.login(null);
        verify(loginService,never()).setCurrentUser(anyString());
    }

    @Test
    public void assertTwoMethosHavaBeenCalled(){
        MockitoAnnotations.openMocks(this);
        Login.LoginForm loginForm = new Login.LoginForm();
        loginForm.userId  = "testuserid";
        Mockito.when(loginService.login(loginForm)).thenReturn(true);

        assertEquals("success", loginController.login(loginForm));
        verify(loginService).login(loginForm);
        verify(loginService).setCurrentUser("testuserid");
    }

    @Test
    public void assertOnlyOneMethodBeenCalled(){
        MockitoAnnotations.openMocks(this);
        Login.LoginForm loginForm = new Login.LoginForm();
        Mockito.when(loginService.login(loginForm)).thenReturn(false);

        assertEquals("fail", loginController.login(loginForm));
        verify(loginService).login(any(Login.LoginForm.class));
        verify(loginService,never()).setCurrentUser(anyString());
    }

    @Test
    public void mockExceptionThrown(){
        MockitoAnnotations.openMocks(this);
        Login.LoginForm loginForm = new Login.LoginForm();
//        Mockito.when(loginService.login(loginForm)).thenThrow(IllegalArgumentException.class);
        doThrow(IllegalArgumentException.class).when(loginService).login(loginForm);
        assertEquals("ERROR", loginController.login(loginForm));
        verify(loginService, atLeastOnce()).login(loginForm);
        verify(loginService,never()).setCurrentUser(any());
    }


    @Test
    public void mockObjectToPassAround(){
        MockitoAnnotations.openMocks(this);
        Login.LoginForm loginForm = Mockito.when(Mockito.mock(Login.LoginForm.class).getUserId()).thenReturn("userId").getMock();
        Mockito.when(loginService.login(loginForm)).thenReturn(true);

        assertEquals("success", loginController.login(loginForm));
        verify(loginService).login(loginForm);
        verify(loginService).setCurrentUser("userId");

    }

    @Test
    public void argumentMatching(){
        MockitoAnnotations.openMocks(this);
        Login.LoginForm loginForm = new Login.LoginForm();
        loginForm.userId = "testuser";
//        Mockito.when(loginService.login(Mockito.any(Login.LoginForm.class))).thenReturn(true);
        Mockito.when(loginService.login(loginForm)).thenReturn(true);

        assertEquals("success", loginController.login(loginForm));
        verify(loginService).login(loginForm);
        verify(loginService).setCurrentUser(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.startsWith("test");
            }
        }));
    }

    @Test
    public void partialMocking(){
        MockitoAnnotations.openMocks(this);
        loginController.loginService = spiedLoginService;
        Login.LoginForm loginForm = new Login.LoginForm();
        loginForm.userId = "testuser";
        Mockito.when(loginDao.login(loginForm)).thenReturn(1);

        assertEquals("success", loginController.login(loginForm));
        verify(spiedLoginService).setCurrentUser(contains("test"));

    }

}
