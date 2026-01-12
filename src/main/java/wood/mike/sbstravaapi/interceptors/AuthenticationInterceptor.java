package wood.mike.sbstravaapi.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import wood.mike.sbstravaapi.config.Constants;

import static wood.mike.sbstravaapi.config.Constants.REDIRECT_AFTER_LOGIN;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("/login");
            return false;
        }
        else if (requestURI.equals("/login") || requestURI.equals("/oauth/callback")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(Constants.ATHLETE_ID) == null) {
            log.info("No session exists for user, redirecting to /login");
            String fullUrl = request.getRequestURI();

            if (request.getQueryString() != null) {
                fullUrl += "?" + request.getQueryString();
            }

            request.getSession().setAttribute(REDIRECT_AFTER_LOGIN, fullUrl);
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
