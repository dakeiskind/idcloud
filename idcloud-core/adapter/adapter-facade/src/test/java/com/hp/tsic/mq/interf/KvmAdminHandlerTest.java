package com.hp.tsic.mq.interf;

import com.h3c.idcloud.core.adapter.facade.AdminHandler;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Role;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Roles;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.pojo.admin.RoleList;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleAdd;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleList;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantDelete;
import com.h3c.idcloud.core.adapter.pojo.user.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.user.UserDelete;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class KvmAdminHandlerTest {

    @Autowired
    private AdminHandler adminHandler;

    public void testTenantCreate() {

        TenantCreate tenantCreate = new TenantCreate();

        tenantCreate.setProviderType("Kvm");

        tenantCreate.setName("zhuyue1");
        tenantCreate.setDescription("zhuyue1");
        tenantCreate.setEnable(true);

        Tenants tenant = null;

        try {
            tenant = adminHandler.createTenant(tenantCreate);

            System.out.println(tenant.getTenantCreateResult().getId());

        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void testTenantDelete() {

        TenantDelete tenantDelete = new TenantDelete();

        tenantDelete.setProviderType("Kvm");


        boolean result = false;

        try {
            result = adminHandler.deleteTenant(tenantDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);

    }

    public void testUserCreate() {

        UserCreate userCreate = new UserCreate();

        userCreate.setProviderType("Kvm");

        userCreate.setName("zhuyue11");
        userCreate.setPassword("123456");
        userCreate.setTenantId("f0af7aff77f04c6f8b461a1cbb33bf6e");
        Users user = null;
        try {
            user = adminHandler.createUser(userCreate);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(user.getUserCreateResult().getId());

        //	Assert.assertSame("zhuyue", user.getUserCreateResult().getId()());

    }

    public void testUserDelete() {

        UserDelete userDelete = new UserDelete();

        userDelete.setProviderType("Kvm");

        userDelete.setUserId("42eaa76fa76048e097a33b88b1114eac");

        boolean result = false;

        try {
            result = adminHandler.deleteUser(userDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);

    }

    public void testUserRoleList() {

        UserRoleList userRoleList = new UserRoleList();

        userRoleList.setProviderType("Kvm");
        userRoleList.setUserUuid("dd232322b35e43f89239c08d69008c2c");
        userRoleList.setTenantUuid("f0af7aff77f04c6f8b461a1cbb33bf6e");

        Roles roles = null;
        try {
            roles = adminHandler.listUserRoles(userRoleList);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertNotNull(roles);

    }

    public void testRoleList() {

        RoleList roleList = new RoleList();

        roleList.setProviderType("Kvm");

        Roles roles = null;
        try {
            roles = adminHandler.listRoles(roleList);

            for (Role role : roles.getListdata()) {
                System.out.println(role.getName());
                System.out.println(role.getUuid());
                System.out.println(role.getDescription());
                System.out.println("##################");
            }
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertNotNull(roles);

    }

    public void testAddRole() {

        UserRoleAdd userRoleAdd = new UserRoleAdd();

        userRoleAdd.setProviderType("Kvm");
        userRoleAdd.setUserUuid("dd232322b35e43f89239c08d69008c2c");
        userRoleAdd.setTenantUuid("f0af7aff77f04c6f8b461a1cbb33bf6e");
        userRoleAdd.setRoleUuid("7b0de7b3e7174deabed075ea37a8121b");

        boolean result = false;

        try {
            result = adminHandler.addRoleToUser(userRoleAdd);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);

    }

    @Test
    public void testDeleteRole() {

        UserRoleDelete userRoleDelete = new UserRoleDelete();

        userRoleDelete.setProviderType("Kvm");
        userRoleDelete.setUserUuid("dd232322b35e43f89239c08d69008c2c");
        userRoleDelete.setTenantUuid("f0af7aff77f04c6f8b461a1cbb33bf6e");
        userRoleDelete.setRoleUuid("7b0de7b3e7174deabed075ea37a8121b");

        boolean result = false;

        try {
            result = adminHandler.deleteRoleToUser(userRoleDelete);
        } catch (CommonAdapterException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        } catch (AdapterUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Assert.assertTrue(result);

    }
}
