package ra.academy.configs;

import org.springframework.web.servlet.HandlerInterceptor;
import ra.academy.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null){
            response.sendRedirect("/login");
            return false;
        }
        if(user != null && user.isRole()){
            return true;
        }
        response.sendRedirect("/403");
        return false;
    }

}
