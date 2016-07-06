package com.h3c.idcloud.infra.security.provider;

import com.h3c.idcloud.core.pojo.dto.user.User;
import com.h3c.idcloud.infra.security.AuthService;
import com.h3c.idcloud.infra.test.ProviderTestBase;
import com.h3c.idcloud.infrastructure.common.constants.AuthConstants;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by qct on 2016/2/4.
 */
public class AuthServiceImplTest extends ProviderTestBase {

    @Before
    public void setup() {
        // 使用父类方法暴露provider
        super.setProvider(AuthService.class);
    }

    @Test
    public void testCreateJWT() throws Exception {
        User user = new User();
        user.setAccount("qct");

        // 使用父类方法得到引用类
        AuthService authService = super.getReference(AuthService.class);

        String jwt = "";

        logger.info("secret key: " + new String(Base64.decodeBase64(AuthConstants.SECRET_KEY)));
        logger.info(jwt);

        assertEquals(user.getAccount(),
                Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(jwt).getBody().getIssuer());
    }
}