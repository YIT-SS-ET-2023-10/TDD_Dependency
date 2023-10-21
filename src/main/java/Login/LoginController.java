package Login;

public class LoginController {
    public LoginService loginService;

    public String login(LoginForm loginForm){
        if(loginForm == null){
            return "NULL ERROR";

        } else {
            boolean logged;

            try{
                logged = loginService.login(loginForm);
            } catch(Exception e){
                return "ERROR";
            }

            if(logged){
                loginService.setCurrentUser(loginForm.getUserId());
                return "success";
            } else{
                return "fail";
            }
        }
    }
}
