package work.onss.aop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import work.onss.config.WechatConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private WechatConfig wechatConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            String authorization = request.getHeader("authorization");
            if (StringUtils.hasLength(authorization)) {
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(wechatConfig.getSign())).build();
                jwtVerifier.verify(authorization);
            }
        }
        return true;
    }
}
