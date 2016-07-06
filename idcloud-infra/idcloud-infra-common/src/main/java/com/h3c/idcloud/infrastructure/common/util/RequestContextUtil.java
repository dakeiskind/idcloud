package com.h3c.idcloud.infrastructure.common.util;


import com.google.common.base.CaseFormat;
import com.h3c.idcloud.infrastructure.common.cache.JedisUtil;
import com.h3c.idcloud.infrastructure.common.constants.AuthConstants;
import com.h3c.idcloud.infrastructure.common.pojo.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * RequestContext工具类
 *
 * @author Chaohong.Mao
 */
public class RequestContextUtil {

    private static final String SQL = "SELECT * FROM sys_m_user WHERE account = ?";

    /** ResCommonInst 中的属性 */
    private static final String FIELD_MGT_OBJ_SID = "mgtObjSid";
    private static final String FIELD_USER_ACCOUNT = "userAccount";
    private static final String FIELD_USER_PASS = "userPass";
    private static final String FIELD_ZONE_ID = "zoneId";
    private static final String FIELD_RES_SPEC_PARAM_ = "resSpecParam";

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param req              HttpServletRequest
     * @param resBaseInfoClazz 资源层共通参数类
     * @param withJsonParam    是否设置JSON配置项<br/>
     *                         true -> 从request的body中取得参数，直接设置到对象的字段上，但是有前提是REST接口上面，
     *                              没有使用自动映射参数
     * @exception IllegalStateException 如果 {@code withJsonParam} 为true，发生异常由于 {@link HttpServletRequest#getInputStream} 或者 {@link HttpServletRequest#getReader()}
     *                                     在这个Request上已经被调用过
     * @return the t
     */
    public static <T> T buildResBaseInfo(HttpServletRequest req, Class<T> resBaseInfoClazz, boolean withJsonParam) {
        AuthUser authUser = getAuthUserInfo(req);
        T result = transUserInfo2ResBase(authUser, resBaseInfoClazz);
        // 将request的body（JSON）设置到对象上
        if (withJsonParam) {
            String body = null;
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                while ((body = reader.readLine()) != null) {
                    sb.append(body);
                }
                // resSpecParam
                Field resSpecParam = ReflectionUtils.findField(resBaseInfoClazz, FIELD_RES_SPEC_PARAM_);
                ReflectionUtils.makeAccessible(resSpecParam);
                ReflectionUtils.setField(resSpecParam, result, sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param req              HttpRequest
     * @param resBaseInfoClazz 资源层共通参数类
     * @return the t
     */
    public static <T> T buildResBaseInfo(HttpServletRequest req, Class<T> resBaseInfoClazz) {
        return buildResBaseInfo(req, resBaseInfoClazz, false);
    }

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param authUser         授权用户
     * @param resBaseInfoClazz 资源层参数类
     * @return the t
     */
    public static <T> T transUserInfo2ResBase(AuthUser authUser, Class<T> resBaseInfoClazz) {
        T result;
        try {
            result = resBaseInfoClazz.newInstance();
            // mgt_obj_sid
            Field mgtObjSid = ReflectionUtils.findField(resBaseInfoClazz, FIELD_MGT_OBJ_SID);
            ReflectionUtils.makeAccessible(mgtObjSid);
            ReflectionUtils.setField(mgtObjSid, result, authUser.getMgtObjSid());
            // userAccount
            Field userAccount = ReflectionUtils.findField(resBaseInfoClazz, FIELD_USER_ACCOUNT);
            ReflectionUtils.makeAccessible(userAccount);
            ReflectionUtils.setField(userAccount, result, authUser.getAccount());
            // userPass
            Field userPass = ReflectionUtils.findField(resBaseInfoClazz, FIELD_USER_PASS);
            ReflectionUtils.makeAccessible(userPass);
            ReflectionUtils.setField(userPass, result, authUser.getPassword());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * 获得授权用户
     *
     * @param req the req
     * @return the auth user info
     */
    public static AuthUser getAuthUserInfo(HttpServletRequest req) {
        Claims claims = (Claims) req.getAttribute(AuthConstants.CLAIMS_KEY);
        AuthUser authUser = null;
        if (claims == null) {
            String token = getToken(req);
            if (token == null) {
                return null;
            }
            claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(token).getBody();
            if (claims == null) {
                return null;
            }
        }
        String userAccount = claims.getIssuer();
        // Redis中
        Map<String, String> userInfo = JedisUtil.instance().hgetall(AuthConstants.CACHE_KEY_USER_PREFIX + userAccount);

        if (userInfo != null && userInfo.size() > 0) {
            authUser = JsonUtil.fromJson(JsonUtil.toJson(userInfo), AuthUser.class);
        } else {
            // From DB
            Map userInfoMap = DBUtil.queryMap(SQL, userAccount);
            Map<String, String> userMap = new HashMap<>();
            for (Object o : userInfoMap.keySet()) {
                String key = o.toString();
                String camelKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
                userMap.put(camelKey, StringUtil.nullToEmpty(userInfoMap.get(key)));
            }
            authUser = JsonUtil.fromJson(JsonUtil.toJson(userMap), AuthUser.class);
            if(JedisUtil.instance().isConnected()){
                JedisUtil.instance()
                        .hmset(AuthConstants.CACHE_KEY_USER_PREFIX + userAccount,
                                userMap,
                                AuthConstants.PERIED_TIME
                        );
            }

        }
        //设置ip
        authUser.setCurrentRequestIp(WebUtil.getIpAddr(req));
        return authUser;
    }

    /**
     * 获得 token.
     *
     * @param httpRequest the http request
     * @return the token
     */
    private static String getToken(HttpServletRequest httpRequest) {
        String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            return null;
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            return null;
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }

//    public static void main(String args[]) {
//        String uuid = UUID.randomUUID().toString();
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        long nowMillis = System.currentTimeMillis();
//        Date now = new Date(nowMillis);
//
//        //We will sign our JWT with user secret
//        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(AuthConstants.SECRET_KEY);
//        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//        //Let's set the JWT Claims
//        JwtBuilder builder = Jwts.builder()
//                                 .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
//                                 .signWith(signatureAlgorithm, signingKey)
//                                 .setId(uuid)
//                                 .setIssuedAt(now)
//                                 .setSubject(AuthConstants.SUBJECT)
//                                 .setIssuer("57845@qq.com");
//
//        //if it has been specified, let's add the expiration
//        if (AuthConstants.TTL_MILLIS >= 0) {
//            long expMillis = nowMillis + AuthConstants.TTL_MILLIS;
//            Date exp = new Date(expMillis);
//            builder.setExpiration(exp);
//        }
//        //Builds the JWT and serializes it to a compact, URL-safe string
//        String compact = builder.compact();
//        System.out.println(compact);
//
//        Claims claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(compact).getBody();
//        System.out.println(claims.getIssuer());
//        System.out.println(claims.getSubject());
//    }

}
