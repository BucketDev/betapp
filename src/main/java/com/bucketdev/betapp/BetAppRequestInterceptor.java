package com.bucketdev.betapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BetAppRequestInterceptor implements WebRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        String idToken = webRequest.getHeader("BetApp-auth");
        logger.info("idToken: {}", idToken);
        if (!isValidToken(idToken))
            throw new Exception();
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {

    }

    private boolean isValidToken(String idToken) {
        if (null != idToken && !idToken.isEmpty()) {
            try {
                FirebaseAuth.getInstance().verifyIdToken(idToken);
            } catch (FirebaseAuthException fe) {
                logger.warn("token validation failed!");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
