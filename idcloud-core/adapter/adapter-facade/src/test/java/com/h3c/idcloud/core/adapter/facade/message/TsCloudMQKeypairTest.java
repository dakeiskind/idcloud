package com.h3c.idcloud.core.adapter.facade.message;

import com.h3c.idcloud.core.adapter.core.MQException;
import com.h3c.idcloud.core.adapter.core.MQHelper;
import com.h3c.idcloud.core.adapter.facade.common.Constants;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;

import org.jclouds.ssh.SshKeys;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Map;

/**
 * Created by qct on 2016/3/15.
 */
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class TsCloudMQKeypairTest extends AbstractJUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(TsCloudMQKeypairTest.class);
    @Test
    public void testCreateKeypair() throws MQException, NoSuchAlgorithmException, NoSuchProviderException {
        KeypairCreate keypairCreate = new KeypairCreate();
        keypairCreate.setVirtEnvType("OpenStack");
        keypairCreate.setVirtEnvUuid("debug");
        keypairCreate.setProviderType(Constants.Provider.OPEN_STACK);

        keypairCreate.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        keypairCreate.setAuthTenant("admin");
        keypairCreate.setAuthUser("admin");
        keypairCreate.setAuthPass("1");

        keypairCreate.setProviderUrl("http://192.168.9.191:5000/v2.0/");
//        keypairDelete.setTenantName("qct-project");
//        keypairDelete.setTenantUserName("qct");
        keypairCreate.setTenantName("admin");
        keypairCreate.setTenantUserName("admin");
        keypairCreate.setTenantUserPass("1");

        keypairCreate.setRegion("RegionOne");

        keypairCreate.setName("qct-keypair");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        Map<String, String> generate = SshKeys.generate(keyGen, random);
        keypairCreate.setPublicKey(generate.get("public"));
        logger.debug("public: {}", generate.get("public"));
        logger.debug("private: {}", generate.get("private"));

        Object o = MQHelper.rpc(keypairCreate);
        System.out.println(o);
    }

    @Test
    public void testDeleteKeypair() throws MQException {
        KeypairDelete keypairDelete = new KeypairDelete();
        keypairDelete.setVirtEnvType("OpenStack");
        keypairDelete.setVirtEnvUuid("debug");
        keypairDelete.setProviderType(Constants.Provider.OPEN_STACK);

        keypairDelete.setAuthUrl("http://192.168.9.191:35357/v2.0/");
        keypairDelete.setAuthTenant("admin");
        keypairDelete.setAuthUser("admin");
        keypairDelete.setAuthPass("1");

        keypairDelete.setProviderUrl("http://192.168.9.191:5000/v2.0/");
//        keypairDelete.setTenantName("qct-project");
//        keypairDelete.setTenantUserName("qct");
        keypairDelete.setTenantName("admin");
        keypairDelete.setTenantUserName("admin");
        keypairDelete.setTenantUserPass("1");

        keypairDelete.setRegion("RegionOne");

        keypairDelete.setKeyName("qct-keypair");

        Object o = MQHelper.rpc(keypairDelete);
        System.out.println(o);
    }
}
