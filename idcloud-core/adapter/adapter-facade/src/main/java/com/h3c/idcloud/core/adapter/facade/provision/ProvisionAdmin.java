package com.h3c.idcloud.core.adapter.facade.provision;


import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Roles;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.pojo.admin.RoleList;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleAdd;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.UserRoleList;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairCreate;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairDelete;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairGet;
import com.h3c.idcloud.core.adapter.pojo.keypairs.KeypairListGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.AddUserToTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.RemoveUserFromTenant;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantCreate;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantDelete;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantInfoGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantListGet;
import com.h3c.idcloud.core.adapter.pojo.tenant.TenantResourcesDelete;
import com.h3c.idcloud.core.adapter.pojo.user.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.user.UserDelete;
import com.h3c.idcloud.core.adapter.pojo.user.UserInfoGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserListGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserModify;

import org.springframework.stereotype.Service;

/**
 * define operations of admin
 *
 * @author qct
 */
@Service
public interface ProvisionAdmin {

    Tenants createTenant(TenantCreate tenantCreate) throws CommonAdapterException, AdapterUnavailableException;

    Tenants getTenant(TenantInfoGet tenantInfoGet) throws CommonAdapterException, AdapterUnavailableException;

    Tenants queryTenant(TenantListGet tenantListGet) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteTenant(TenantDelete tenantDelete) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteTenantResources(TenantResourcesDelete tenantResourcesDelete)
            throws CommonAdapterException, AdapterUnavailableException;

    Users createUser(UserCreate userCreate) throws CommonAdapterException, AdapterUnavailableException;

    Users getUser(UserInfoGet userInfoGet) throws CommonAdapterException, AdapterUnavailableException;

    Users queryUser(UserListGet userListGet) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteUser(UserDelete userDelete) throws CommonAdapterException, AdapterUnavailableException;

    boolean addRoleToUser(UserRoleAdd userRoleAdd) throws CommonAdapterException, AdapterUnavailableException;

    boolean deleteRoleToUser(UserRoleDelete userRoleDelete) throws CommonAdapterException, AdapterUnavailableException;

    Roles listRoles(RoleList roleList) throws CommonAdapterException, AdapterUnavailableException;

    Roles listUserRoles(UserRoleList userRoleList) throws CommonAdapterException, AdapterUnavailableException;

    boolean changeUserPwd(UserModify userModify) throws CommonAdapterException, AdapterUnavailableException;

    boolean addUserToTenant(AddUserToTenant addUserToTenant) throws CommonAdapterException, AdapterUnavailableException;

    boolean removeUserFromTenant(RemoveUserFromTenant removeUserFromTenant)
            throws CommonAdapterException, AdapterUnavailableException;

    KeyPairResult createKeyPair(KeypairCreate keypairCreate) throws CommonAdapterException, AdapterUnavailableException;

    KeyPairResult getKeyPairInfo(KeypairGet keypairGet) throws CommonAdapterException, AdapterUnavailableException;

    KeyPairResult getKeyPairList(KeypairListGet keypairListGet)
            throws CommonAdapterException, AdapterUnavailableException;

    boolean deletekeyPair(KeypairDelete keypairDelete) throws CommonAdapterException, AdapterUnavailableException;

}
