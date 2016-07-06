package com.h3c.idcloud.core.rest.war.filter;

import com.h3c.idcloud.infra.security.AuthService;
import com.h3c.idcloud.infrastructure.common.constants.AuthConstants;
import com.h3c.idcloud.infrastructure.common.util.StringUtil;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * Created by qct on 2016/2/3.
 */
@WebFilter(filterName = "jwt-filter", urlPatterns = {"/rest/*"})
public class JWTFilter extends GenericFilterBean {

    private static Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Autowired
    AuthService authService;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String path = ((HttpServletRequest) req).getRequestURI();
        if (excludeFromFilter(path)) {
            chain.doFilter(req, res);
        } else {
            try {
                String token = getToken((HttpServletRequest) req);
                //TODO 1.从缓存或者数据库中查詢token是否存在
                if (StringUtil.isNullOrEmpty(token) || "undefined".equals(token)) {
                    ((HttpServletResponse) res).setStatus(320);
                    return;
                }

                //TODO 2.如果存在，拿出token信息验证過期時間等
                final Claims claims;
                try {
                    claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY)
                            .parseClaimsJws(token).getBody();
                } catch (ExpiredJwtException e) {
                    ((HttpServletResponse) res).setStatus(320);
                    return;
                }

                if (claims.getExpiration().before(new Date())) {
                    ((HttpServletResponse) res).setStatus(320);
                    return;
                }

                if (!AuthConstants.SUBJECT.equals(claims.getSubject())) {
                    ((HttpServletResponse) res).setStatus(320);
                    throw new ServletException("Unauthorized: invalid token");
                }

                String account = claims.getIssuer();
                //TODO other validation

                req.setAttribute("claims", claims);
                // Do something with decoded information like UserId

                // Get custom fields from decoded Payload
                logger.info(claims.getIssuer());

            } catch (SignatureException e) {
                throw new ServletException("Unauthorized: Token validation failed", e);
            }
            chain.doFilter(req, res);
        }
    }

    private boolean excludeFromFilter(String path) {
        // TODO
        if (path.startsWith("/idcloud-rest/rest/user/login")
                || path.startsWith("/idcloud-rest/rest/services/serviceTree")
                || path.startsWith("/idcloud-rest/rest/user/current/{userSid}")
                || path.startsWith("/idcloud-rest/rest/user/registerProtalUser")
                || path.startsWith("/idcloud-rest/rest/user/validateUserAccount")
                || path.startsWith("/idcloud-rest/rest/user/validateUserEmail")
                || path.startsWith("/idcloud-rest/rest/user/validateUserMobile")
                || path.startsWith("/idcloud-rest/rest/user/findLostPwd/")
                || path.startsWith("/idcloud-rest/rest/mgtObj/find")
                || path.startsWith("/idcloud-rest/rest/configs")
                || path.startsWith("/idcloud-rest/rest/system")
                || path.startsWith("/idcloud-rest/rest/user/activate")
                || path.startsWith("/idcloud-rest/rest/keypairs/download/")
                || path.startsWith("/idcloud-rest/rest/user/validateExits")
                || path.startsWith("/idcloud-rest/rest/getAddress/getProvince")
                || path.startsWith("/idcloud-rest/rest/getAddress/getCityByProvince/")
                || path.startsWith("/idcloud-rest/rest/getAddress/getAreaByCity/")) {
            return true;
        } else {
            return false;
        }
    }

    private String getToken(HttpServletRequest httpRequest) throws ServletException {
        String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            throw new ServletException("Unauthorized: No Authorization header was found");
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            throw new ServletException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }


}
