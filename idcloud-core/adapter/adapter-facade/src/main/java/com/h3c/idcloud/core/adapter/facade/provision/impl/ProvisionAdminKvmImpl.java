package com.h3c.idcloud.core.adapter.facade.provision.impl;

import com.h3c.idcloud.core.adapter.facade.provision.ProvisionAdmin;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmAddUserToTenant;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmDeleteTenantResources;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmKeypairDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmKeypairGetInfo;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmKeypairListGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmRemoveUserFromTenant;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmRoleList;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmTenantCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmTenantDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmTenantInfoGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmTenantListGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserInfoGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserListGet;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserModify;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserRoleAdd;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserRoleDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.kvm.KvmUserRoleList;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackKeypairCreate;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackKeypairDelete;
import com.h3c.idcloud.core.adapter.facade.provision.action.openstack.OpenStackTenantCreate;
import com.h3c.idcloud.core.adapter.facade.provision.exception.AdapterUnavailableException;
import com.h3c.idcloud.core.adapter.facade.provision.exception.CommonAdapterException;
import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Roles;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.Tenant;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.User;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant.Tenants;
import com.h3c.idcloud.core.adapter.facade.provision.model.admin.user.Users;
import com.h3c.idcloud.core.adapter.facade.provision.model.keypair.KeyPairResult;
import com.h3c.idcloud.core.adapter.pojo.admin.RoleList;
import com.h3c.idcloud.core.adapter.pojo.admin.TenantGet;
import com.h3c.idcloud.core.adapter.pojo.admin.TenantQuery;
import com.h3c.idcloud.core.adapter.pojo.admin.UserCreate;
import com.h3c.idcloud.core.adapter.pojo.admin.UserDelete;
import com.h3c.idcloud.core.adapter.pojo.admin.UserGet;
import com.h3c.idcloud.core.adapter.pojo.admin.UserQuery;
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
import com.h3c.idcloud.core.adapter.pojo.user.UserInfoGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserListGet;
import com.h3c.idcloud.core.adapter.pojo.user.UserModify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionAdminKvmImpl implements ProvisionAdmin {

    @Autowired
    private KvmTenantCreate kvmTenantCreate;

    @Autowired
    private KvmTenantDelete kvmTenantDelete;

    @Autowired
    private KvmUserCreate kvmUserCreate;

    @Autowired
    private KvmUserDelete kvmUserDelete;

    @Autowired
    private KvmUserRoleAdd kvmUserRoleAdd;

    @Autowired
    private KvmUserRoleDelete kvmUserRoleDelete;

    @Autowired
    private KvmRoleList kvmRoleList;

    @Autowired
    private KvmUserRoleList kvmUserRoleList;

    @Autowired
    private KvmTenantListGet kvmTenantListGet;

    @Autowired
    private KvmTenantInfoGet kvmTenantInfoGet;

    @Autowired
    private KvmUserInfoGet kvmUserInfoGet;

    @Autowired
    private KvmUserListGet kvmUserListGet;

    @Autowired
    private KvmUserModify kvmUserModify;

    @Autowired
    private KvmAddUserToTenant kvmAddUserToTenant;

    @Autowired
    private KvmRemoveUserFromTenant kvmRemoveUserFromTenant;

    @Autowired
    private KvmDeleteTenantResources kvmDeleteTenantResources;

//    @Autowired
//    private KvmKeypairCreate kvmKeypairCreate;

//    @Autowired
//    private KvmKeypairDelete kvmKeypairDelete;

    @Autowired
    private KvmKeypairGetInfo kvmKeypairGetInfo;

    @Autowired
    private KvmKeypairListGet kvmKeypairListGet;


    //jclouds
    @Autowired
    private OpenStackTenantCreate openStackTenantCreate;
    @Autowired
    private OpenStackKeypairCreate openStackKeypairCreate;
    @Autowired
    private OpenStackKeypairDelete openStackKeypairDelete;

    public Tenant getTenant(TenantGet tenantGet) throws CommonAdapterException {

        // KvmTenantGet kvmTenantGet = new KvmTenantGet();
        // Tenant tenant = (Tenant) kvmTenantGet.invoke(tenantGet);
        //
        // return tenant;
        return null;
    }

    public List<Tenant> queryTenant(TenantQuery tenantQuery) throws CommonAdapterException {
        //
        // KvmTenantQuery kvmTenantQuery = new KvmTenantQuery();
        // ResultTenants tenants = (ResultTenants) kvmTenantQuery
        // .invoke(tenantQuery);
        //
        // return tenants.getListdata();
        return null;
    }

    public User createUser(UserCreate userCreate) throws CommonAdapterException, AdapterUnavailableException {

        User user = (User) kvmUserCreate.invoke(userCreate);

        return user;
    }

    public User getUser(UserGet userGet) throws CommonAdapterException {

        // KvmUserGet kvmUserGet = new KvmUserGet();
        // User user = (User) kvmUserGet.invoke(userGet);
        //
        // return user;

        return null;
    }

    public List<User> queryUser(UserQuery userQuery) throws CommonAdapterException {

        // KvmUserQuery kvmUserQuery = new KvmUserQuery();
        // ResultUsers users = (ResultUsers) kvmUserQuery
        // .invoke(userQuery);
        //
        // return users.getListdata();

        return null;
    }

    public boolean deleteUser(UserDelete userDelete) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = kvmUserDelete.invoke(userDelete);

        return result.isSuccess();
    }

    public boolean addRoleToUser(UserRoleAdd userRoleAdd) throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = kvmUserRoleAdd.invoke(userRoleAdd);

        return result.isSuccess();
    }

    public boolean deleteRoleToUser(UserRoleDelete userRoleDelete) throws CommonAdapterException,
            AdapterUnavailableException {

        CommonAdapterResult result = kvmUserRoleDelete.invoke(userRoleDelete);

        return result.isSuccess();
    }

    public Roles listRoles(RoleList roleList) throws CommonAdapterException, AdapterUnavailableException {

        Roles roles = (Roles) kvmRoleList.invoke(roleList);
        return roles;
    }

    public Roles listUserRoles(UserRoleList userRoleList) throws CommonAdapterException, AdapterUnavailableException {

        Roles roles = (Roles) kvmUserRoleList.invoke(userRoleList);
        return roles;
    }

    @Override
    public Tenants createTenant(
            TenantCreate tenantCreate)
            throws CommonAdapterException, AdapterUnavailableException {
//        Tenants tenants = (Tenants) kvmTenantCreate.invoke(tenantCreate);
        Tenants tenants = (Tenants) openStackTenantCreate.invoke(tenantCreate);
        return tenants;
    }

    @Override
    public Tenants getTenant(TenantInfoGet tenantInfoGet)
            throws CommonAdapterException, AdapterUnavailableException {

        return (Tenants) kvmTenantInfoGet.invoke(tenantInfoGet);
    }

    @Override
    public Tenants queryTenant(TenantListGet tenantListGet)
            throws CommonAdapterException, AdapterUnavailableException {


        return (Tenants) kvmTenantListGet.invoke(tenantListGet);
    }

    @Override
    public boolean deleteTenant(
            TenantDelete tenantDelete)
            throws CommonAdapterException, AdapterUnavailableException {

        CommonAdapterResult result = kvmTenantDelete.invoke(tenantDelete);

        return result.isSuccess();
    }

    @Override
    public Users createUser(com.h3c.idcloud.core.adapter.pojo.user.UserCreate userCreate)
            throws CommonAdapterException, AdapterUnavailableException {

        return (Users) kvmUserCreate.invoke(userCreate);
    }

    @Override
    public Users getUser(UserInfoGet userInfoGet) throws CommonAdapterException, AdapterUnavailableException {

        return (Users) kvmUserInfoGet.invoke(userInfoGet);
    }

    @Override
    public Users queryUser(UserListGet userListGet)
            throws CommonAdapterException, AdapterUnavailableException {

        return (Users) kvmUserListGet.invoke(userListGet);
    }

    @Override
    public boolean deleteUser(
            com.h3c.idcloud.core.adapter.pojo.user.UserDelete userDelete)
            throws CommonAdapterException, AdapterUnavailableException {

        return kvmUserDelete.invoke(userDelete).isSuccess();
    }

    @Override
    public boolean changeUserPwd(UserModify userModify)
            throws CommonAdapterException, AdapterUnavailableException {
        return kvmUserModify.invoke(userModify).isSuccess();
    }

    @Override
    public boolean addUserToTenant(AddUserToTenant addUserToTenant)
            throws CommonAdapterException, AdapterUnavailableException {
        return kvmAddUserToTenant.invoke(addUserToTenant).isSuccess();
    }

    @Override
    public boolean removeUserFromTenant(
            RemoveUserFromTenant removeUserFromTenant)
            throws CommonAdapterException, AdapterUnavailableException {
        return kvmRemoveUserFromTenant.invoke(removeUserFromTenant).isSuccess();
    }

    @Override
    public boolean deleteTenantResources(
            TenantResourcesDelete tenantResourcesDelete)
            throws CommonAdapterException, AdapterUnavailableException {
        return kvmDeleteTenantResources.invoke(tenantResourcesDelete).isSuccess();
    }

    @Override
    public KeyPairResult createKeyPair(KeypairCreate keypairCreate)
            throws CommonAdapterException, AdapterUnavailableException {
//        return (KeyPairResult) kvmKeypairCreate.invoke(keypairCreate);
        return (KeyPairResult) openStackKeypairCreate.invoke(keypairCreate);
    }

    @Override
    public KeyPairResult getKeyPairInfo(KeypairGet keypairGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return (KeyPairResult) kvmKeypairGetInfo.invoke(keypairGet);
    }

    @Override
    public KeyPairResult getKeyPairList(KeypairListGet keypairListGet)
            throws CommonAdapterException, AdapterUnavailableException {
        return (KeyPairResult) kvmKeypairListGet.invoke(keypairListGet);
    }

    @Override
    public boolean deletekeyPair(KeypairDelete keypairDelete)
            throws CommonAdapterException, AdapterUnavailableException {
//        return kvmKeypairDelete.invoke(keypairDelete).isSuccess();
        return openStackKeypairDelete.invoke(keypairDelete).isSuccess();
    }
}
