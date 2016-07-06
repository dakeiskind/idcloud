package com.h3c.idcloud.core.adapter.facade;


import com.h3c.idcloud.core.adapter.facade.common.ProviderFactory;
import com.h3c.idcloud.core.adapter.facade.provision.ProvisionAdmin;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * admin handler template
 *
 * @author qct
 */
@Service
public class AdminHandler implements ProvisionAdmin {

    /**
     * provider factory
     */
    @Autowired
    private ProviderFactory provider;

    @Override
    public boolean addRoleToUser(UserRoleAdd userRoleAdd)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userRoleAdd.getProviderType()).addRoleToUser(userRoleAdd);

    }

    @Override
    public boolean deleteRoleToUser(UserRoleDelete userRoleDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userRoleDelete.getProviderType())
                .deleteRoleToUser(userRoleDelete);
    }

    @Override
    public Roles listRoles(RoleList roleList)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(roleList.getProviderType()).listRoles(roleList);
    }

    @Override
    public Roles listUserRoles(UserRoleList userRoleList)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userRoleList.getProviderType())
                .listUserRoles(userRoleList);
    }

    @Override
    public Tenants createTenant(TenantCreate tenantCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(tenantCreate.getProviderType())
                .createTenant(tenantCreate);
    }

    @Override
    public Tenants getTenant(TenantInfoGet tenantInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(tenantInfoGet.getProviderType())
                .getTenant(tenantInfoGet);
    }

    @Override
    public Tenants queryTenant(TenantListGet tenantListGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(tenantListGet.getProviderType())
                .queryTenant(tenantListGet);
    }

    @Override
    public boolean deleteTenant(TenantDelete tenantDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(tenantDelete.getProviderType())
                .deleteTenant(tenantDelete);
    }

    @Override
    public Users createUser(UserCreate userCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userCreate.getProviderType()).createUser(userCreate);
    }

    @Override
    public Users getUser(UserInfoGet userInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userInfoGet.getProviderType()).getUser(userInfoGet);
    }

    @Override
    public Users queryUser(UserListGet userListGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userListGet.getProviderType()).queryUser(userListGet);
    }

    @Override
    public boolean deleteUser(UserDelete userDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userDelete.getProviderType()).deleteUser(userDelete);
    }

    @Override
    public boolean changeUserPwd(UserModify userModify)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(userModify.getProviderType()).changeUserPwd(userModify);
    }

    @Override
    public boolean addUserToTenant(AddUserToTenant addUserToTenant)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(addUserToTenant.getProviderType())
                .addUserToTenant(addUserToTenant);
    }

    @Override
    public boolean removeUserFromTenant(
            RemoveUserFromTenant removeUserFromTenant)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(removeUserFromTenant.getProviderType())
                .removeUserFromTenant(removeUserFromTenant);
    }

    @Override
    public boolean deleteTenantResources(
            TenantResourcesDelete tenantResourcesDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(tenantResourcesDelete.getProviderType())
                .deleteTenantResources(tenantResourcesDelete);
    }

    @Override
    public KeyPairResult createKeyPair(KeypairCreate keypairCreate)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(keypairCreate.getProviderType())
                .createKeyPair(keypairCreate);
    }

    @Override
    public KeyPairResult getKeyPairInfo(KeypairGet keypairGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(keypairGet.getProviderType()).getKeyPairInfo(keypairGet);
    }

    @Override
    public KeyPairResult getKeyPairList(KeypairListGet keypairListGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(keypairListGet.getProviderType())
                .getKeyPairList(keypairListGet);
    }

    @Override
    public boolean deletekeyPair(KeypairDelete keypairDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return provider.getProvisionAdmin(keypairDelete.getProviderType())
                .deletekeyPair(keypairDelete);
    }
}