package com.sand.api.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.sand.api.annotation.CheckToken;
import com.sand.biz.exception.LoginException;
import com.sand.biz.utils.JwtUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description
 * @author: YunLong
 * @create: 2019-11-21
 **/
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (checkToken.required()) {
                String token = request.getHeader("token");
                if (Strings.isEmpty(token)) {
                    throw new LoginException("no token");
                }

                if (!JwtUtils.checkJWT(token)) {
                    throw new LoginException("token invalid");
                }

                try {
                    String userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException ex) {
                    throw new LoginException("token invalid");
                }
                return true;
            }
        }
        return true;
    }
}
