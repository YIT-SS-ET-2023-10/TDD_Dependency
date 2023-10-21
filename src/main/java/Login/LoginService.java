package Login;

import Login.LoginDAO;

public class LoginService {
    private LoginDAO loginDao;
    private String currentUser;

    public boolean login(LoginForm loginForm){
        assert null!= loginForm;

        int loginResults = loginDao.login(loginForm);

        return (loginResults==1) ? true:false;
    }

    public void setCurrentUser(String userId){
        if(userId != null){
            currentUser = userId;
        }
    }
}
