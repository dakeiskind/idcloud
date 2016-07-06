package com.h3c.idcloud.infrastructure.common.constants;

/**
 * 共通常量类
 *
 * @author swq
 */
public interface WebConstants {

    /**
     * 保存session中的用户信息key
     */
    public static final String CURRENT_PORTAL_USER = "TS_CLOUD_CURRENT_PORTAL_USER";

    public static final String CURRENT_PLATFORM_USER = "TS_CLOUD_CURRENT_PLATFORM_USER";


    /**
     * 参数规格定义
     */
    public interface SpecificationProperty {

        String DATA = "data";
        String SPECIFICATIONS = "specifications";
        String SPECIFICATIONS_DESC = "specificationsDesc";
        String SERVICE_CODE = "serviceCode";
        String QUANTITY = "quantity";
        String BILLING_TYPE = "billingType";
        String DURATION = "duration";
        String ORDER_TYPE = "orderType";
    }

    /**
     * 规格项计费状态
     */
    public interface SpecBillStatus {

        int NO_BILLED = 0;
        int BILLED = 1;
    }

    /**
     * 功能菜单所属系统
     */
    public interface ModuleCategory {

        /**
         * 消费门户:1
         */
        Integer PROTAL = 1;

        /**
         * 后台门户:2
         */
        Integer DASHBOARD = 2;
    }

    /**
     * 功能菜单类型
     */
    public interface ModuleType {

        /**
         * 菜单:0
         */
        Integer MEMU = 0;

        /**
         * 功能:1
         */
        Integer FUNCTION = 1;
    }

    public interface GiftCardBatchStatus {

        //0 - 未生成
        Integer GIFT_CARD_NOT_GENERATED = 0;
        //1 - 已生成
        Integer GIFT_CARD_GENERATED = 1;
        //2 - 已作废
        Integer BATCH_INVALID = 2;
    }

    public interface GiftCardStatus {

        //0未激活
        Integer NOT_ACTIVATED = 0;
        Integer ACTIVATED = 1;
    }

    /**
     * 固定角色主键
     */
    public interface RoleSid {

        /**
         * 租户用户
         */
        Long T_USER = 104L;

        /**
         * 租户管理员
         */
        Long T_MANAGER = 103L;

        /**
         * 运营管理员
         */
        Long O_MANAGER = 102L;

        /**
         * 运维管理员
         */
        Long OM_MANAGER = 101L;
        Long OM_MANAGERKLB = 204L;

        /**
         * 超级管理员
         */
        Long AD_MANAGER = 100L;

        /**
         * 企业管理员
         */
        Long MGT_OBJ_MANAGER = 205L;
    }

    /**
     * 服务编码
     */
    public interface ServiceCode {

        /**
         * 云主机
         */
        String CS = "cs";

        /**
         * 对象存储
         */
        String OSS = "oss";

        /**
         * 块存储
         */
        String CBS = "cbs";

        /**
         * 弹性IP
         */
        String EIP = "eip";

        /**
         * 私有网络
         */
        String VPC = "vpc";

        /**
         * CND
         */
        String CND = "cdn";

        /**
         * 负责均衡
         */
        String LOADBALANCE = "loadbalance";
    }

    /**
     * 服务SID
     */
    public interface ServiceSid {

        /**
         * VM服务
         */
        Long SERVICE_VM = 100L;

        /**
         * 对象存储服务
         */
        Long SERVICE_OBJ_STORAGE = 104L;

        /**
         * 块存储服务
         */
        Long SERVICE_STORAGE = 105L;

        /**
         * 弹性IP服务
         */
        Long FLOATING_IP = 106L;

        /**
         * CDN自服务
         */
        Long SERVICE_CDN = 107L;

        /**
         * EXCHANGE服务
         */
        Long SERVICE_EX = 114L;

        /**
         * SHAREPOINT服务
         */
        Long SERVICE_SP = 115L;

        /**
         * 自动化部署服务
         */
        Long SERVICE_DEPLOYMENT = 120L;

        /**
         * 物理机服务
         */
        Long SERVICE_PM = 121L;

        /**
         * 项目变更经理
         */
        Long CHANGE_MANAGER_SERVICE = -100L;

        /**
         * 虚机变更项目
         */
        Long CHANGE_MGTOBJ_SERVICE = -200L;

    }

    /**
     * 服务类型
     */
    public interface ServiceType {

        /**
         * VM服务
         */
        String VM = "VM";

        /**
         * SHAREPOINT服务
         */
        String SHAREPOINT = "SHAREPOINT";

        /**
         * EXCHANGE服务
         */
        String EXCHANGE = "EXCHANGE";

        /**
         * STORAGE服务
         */
        String STORAGE = "STORAGE";

        /**
         * OBJECT STORAGE服务
         */
        String OBJECT_STORAGE = "OBJECT_STORAGE";

        /**
         * DEPLOYMENT服务
         */
        String DEPLOYMENT = "DEPLOYMENT";

        /**
         * 物理机服务
         */
        String PM = "PM";

        /**
         * 弹性IP服务
         */
        String FLOATING_IP = "FLOATING_IP";

        /**
         * 弹性IP服务
         */
        String CDN = "CDN";

        /**
         * 其他
         */
        String OTHER = "OTHER";
    }

    /**
     * 消息代码
     */
    public interface MsgCd {
        /* 提示消息 */
        /**
         * info.insert.success=创建成功。
         */
        String INFO_INSERT_SUCCESS = "info.insert.success";

        /**
         * info.register.success=注册成功。
         */
        String INFO_REGISTER_SUCCESS = "info.register.success";

        /**
         * info.update.success=更新成功。
         */
        String INFO_UPDATE_SUCCESS = "info.update.success";

        /**
         * info.publish.success=发布成功。
         */
        String INFO_PUBLISH_SUCCESS = "info.publish.success";

        /**
         * info.delete.success=删除成功。
         */
        String INFO_DELETE_SUCCESS = "info.delete.success";

        /**
         * info.relation.success=关联操作成功。
         */
        String INFO_RELATION_SUCCESS = "info.relation.success";

        /**
         * info.approve.success=审核成功。
         */
        String INFO_APPROVE_SUCCESS = "info.approve.success";

        /**
         * info.message.success=发送成功。
         */
        String INFO_MESSAGE_SUCCESS = "info.message.success";

        /**
         * info.inventory.success=盘点成功。
         */
        String INFO_INVENTORY_SUCCESS = "info.inventory.success";

        /**
         * info.in.success=入库成功。
         */
        String INFO_IN_SUCCESS = "info.in.success";

        /**
         * info.relate.success=关联成功。
         */
        String INFO_RELATE_SUCCESS = "info.relate.success";

        /**
         * info.out.success=出库成功。
         */
        String INFO_OUT_SUCCESS = "info.out.success";

        /**
         * info.copy.success=复制成功。
         */
        String INFO_COPY_SUCCESS = "info.copy.success";

        /**
         * vm.start.success=启动成功。
         */
        String VM_START_SUCCESS = "vm.start.success";

        /**
         * vm.stop.success=关机成功。
         */
        String VM_STOP_SUCCESS = "vm.stop.success";

        /**
         * vm.restart.success=重启成功。
         */
        String VM_RESTART_SUCCESS = "vm.restart.success";

        /**
         * vm.reconfig.success=调整成功。
         */
        String VM_RECONFIG_SUCCESS = "vm.start.reconfig";

        /**
         * vm.migrate.success=迁移成功。
         */
        String VM_MIGRATE_SUCCESS = "vm.migrate.success";

        /**
         * vm.destory.success=退订成功。
         */
        String VM_DESTORY_SUCCESS = "vm.destory.success";

        /**
         * vm.managed.success=纳管成功。
         */
        String VM_MANAGED_SUCCESS = "vm.managed.success";

        /**
         * vm.rename.success=虚拟机修改名称成功。
         */
        String VM_RENAME_SUCCESS = "vm.rename.success";

        /**
         * task.issued.success=任务下发成功，请到日志中心查看详情。<br>待任务完成后请手动进行刷新操作。
         */
        String TASK_ISSUED_SUCCESS = "task.issued.success";

        /**
         * image.disabled.success=停用成功。
         */
        String IMAGE_DISABLED_SUCCESS = "image.disabled.success";

        /**
         * info.ticket.success=分配工单成功
         */
        String INFO_TICKET_SUCCESS = "info.ticket.success";

        /**
         * info.vm.res.check=资源检查成功。
         */
        String INFO_VM_RES_CHECK = "info.vm.res.check";

        /**
         * info.ticket.execute=重新执行工单成功。
         */
        String INFO_TICKET_EXECUTE = "info.ticket.execute";

        /**
         * info.ticket.execute=操作成功。
         */
        String INFO_OPERATE_SUCCESS = "info.operate.success";

        String SYSTEM_MESSAGE_COUPON = "system.message.coupon";

        String INFO_MOBILE_GET_SUCCESS = "info.mobile.get.success";

        /**
         * error.edit.failure=编辑失败。
         */
        String ERROR_EDIT_FAILURE = "error.edit.failure";
        /**
         * info.edit.success=编辑成功。
         */
        String INFO_EDIT_SUCCESS = "info.edit.success";

         /* 错误消息 */
        /**
         * error.system.exception
         **/
        String ERROR_SYS_EXCEPTION = "error.system.exception";

		/* 警告消息 */
        /**
         * warning_service_repeat=对不起，该服务不能重复订购。
         */
        String WARNING_SERVICE_REPEAT = "warning_service_repeat";

        /**
         * warning.query.failure=对不起，数据为空。
         */
        String WARNING_QUERY_FAILURE = "warning.query.failure";

		/* 错误消息 */
        /**
         * error.insert.failure=创建失败。
         */
        String ERROR_INSERT_FAILURE = "error.insert.failure";

        /**
         * error.message.failure=发送失败。
         */
        String ERROR_MESSAGE_FAILURE = "error.message.failure";

        /**
         * error.query.failure=获取信息失败，数据已被更新或删除。
         */
        String ERROR_QUERY_FAILURE = "error.query.failure";

        /**
         * error.register.failure=注册失败，数据或已存在，请重试。
         */
        String ERROR_REGISTER_FAILURE = "error.register.failure";

        /**
         * error.update.failure=更新失败，数据已被更新或删除。
         */
        String ERROR_UPDATE_FAILURE = "error.update.failure";

        /**
         * error.publish.success=发布失败。
         */
        String ERROR_PUBLISH_FAILURE = "error.publish.failure";

        /**
         * error.delete.failure=删除失败，数据已被更新或删除。
         */
        String ERROR_DELETE_FAILURE = "error.delete.failure";

        /**
         * error.approve.failure=审核失败。
         */
        String ERROR_APPROVE_FAILURE = "error.approve.failure";

        /**
         * error.sendmail.failure=邮件发送失败。
         */
        String ERROR_SENDMAIL_FAILURE = "error.sendmail.failure";

        /**
         * error.inventory.failure=盘点失败。
         */
        String ERROR_INVENTORY_FAILURE = "error.inventory.failure";

        /**
         * error.in.failure=入库失败。
         */
        String ERROR_IN_FAILURE = "error.in.failure";

        /**
         * error.relate.failure=关联失败。
         */
        String ERROR_RELATE_FAILURE = "error.relate.failure";

        /**
         * error.out.failure=出库失败。
         */
        String ERROR_OUT_FAILURE = "error.out.failure";

        /**
         * error.copy.failure=复制失败。
         */
        String ERROR_COPY_FAILURE = "error.copy.failure";

        /**
         * error.relation.failure=关联操作失败，数据已被更新或删除。
         */
        String ERROR_RELATION_FAILURE = "error.relation.failure";

        /**
         * error.data.exist={0}已经存在，请重新填写。
         */
        String ERROR_DATA_EXIST = "error.data.exist";

        /**
         * error.data.relation={0}，不能进行删除。
         */
        String ERROR_DATA_RELATION = "error.data.relation";

        /**
         * error.data.relation.delete=存在关联关系，不能进行删除。
         */
        String ERROR_DATA_RELATION_DELETE = "error.data.relation.delete";

        /**
         * error.data.relation.update=不能进行修改。
         */
        String ERROR_DATA_RELATION_UPDATE = "error.data.relation.update";

        /**
         * error.data.relation={0}，数据已被更新或删除。
         */
        String ERROR_DATA_FAILURE = "error.data.failure";

        /**
         * error.file.oversize=您选择的文件过大，请重新选择。
         */
        String ERROR_FILE_OVERSIZE = "error.file.oversize";

        /**
         * error.plan.published=该名称预案已经发布。
         */
        String ERROR_PLAN_PUBLISHED = "error.plan.published";

        /**
         * error.plan.published=该预案已经停用。
         */
        String ERROR_PLAN_DISABLED = "error.plan.disabled";

        /**
         * error.assess.maxnum={0}不能超过十个。
         */
        String ERROR_ASSESS_MAXNUM = "error.assess.maxnum";

        /**
         * 订单取消失败。
         */
        String ERROR_CANCEL_ORDER = "error.cancel.order";

        /**
         * error.vm.start=启动失败。
         */
        String ERROR_VM_START = "error.vm.start";

        /**
         * error.vm.stop=关机失败。
         */
        String ERROR_VM_STOP = "error.vm.stop";

        /**
         * error.vm.restart=重启失败。
         */
        String ERROR_VM_RESTART = "error.vm.restart";

        /**
         * error.vm.reconfig=调整失败。
         */
        String ERROR_VM_RECONFIG = "error.vm.reconfig";

        /**
         * error.vm.migrate=迁移失败。
         */
        String ERROR_VM_MIGRATE = "error.vm.migrate";

        /**
         * error.vm.managed=纳管失败。
         */
        String ERROR_VM_MANAGED = "error.vm.managed";

        /**
         * error.vm.scan=扫描失败。
         */
        String ERROR_VM_SCAN = "error.vm.scan";

        /**
         * error.vm.destory=退订失败。
         */
        String ERROR_VM_DESTORY = "error.vm.destory";

        /**
         * error.vm.rename=虚拟机修改名称失败。
         */
        String ERROR_VM_RENAME = "error.vm.rename";

        /**
         * error.task.issued=任务发送失败。
         */
        String ERROR_TASK_ISSUED = "error.task.issued";
        /**
         * error.image.disabled=停用失败。
         */
        String ERROR_IMAGE_DISABLED = "error.image.disabled";

        /**
         * error.recover.busy={0}，请等待。
         */
        String ERROR_RECOVER_BUSY = "error.recover.busy";

		/* 警告消息 */
        /**
         * warning_ippool_repeat=对不起，该IP不能重复添加到资源池。
         */
        String WARNING_IPPOOL_REPEAT = "warning_ippool_repeat";

		/* 警告消息 */
        /**
         * warning_ip_repeat=对不起，该IP已经添加到资源。
         */
        String WARNING_IP_REPEAT = "warning_ip_repeat";

        /**
         * error.ticket.allocate=分配工单失败。
         */
        String ERROR_TICKET_ALLOCATE = "error.ticket.allocate";

        /**
         * error.vm.res.check=资源不足。
         */
        String ERROR_VM_RES_CHECK = "error.vm.res.check";

        /**
         * error.biz.user.check=没有用户关联到所选业务名称。
         */
        String ERROR_BIZ_USER_CHECK = "error.biz.user.check";

        /**
         * error.ticket.execute=重新执行工单失败。
         */
        String ERROR_TICKET_EXECUTE = "error.ticket.execute";

        /**
         * error.operate.failure=操作失败。
         */
        String ERROR_OPERATE_FAILURE = "error.operate.failure";

        /**
         * warning.mgtobjres.failure=该租户尚未关联资源。
         */
        String WARNING_MGTOBJRES_FAILURE = "warning.mgtobjres.failure";

        /**
         * error.operate.failure=获取虚拟化环境失败。
         */
        String ERROR_VE_FAILURE = "error.ve.failure";

        /**
         * INFO_GIFT_CARD_GEN_SUCCESS
         */
        String INFO_GIFT_CARD_GEN_SUCCESS = "info_gift_card_gen_success";
        String ERROR_PARAMETER_WRONG = "error_parameeter_wrong";

    }

    /**
     * 数据字典类型代码
     */
    public interface CodeCategroy {

        /**
         * 服务状态
         */
        String SERVICE_STATUS = "SERVICE_STATUS";

        /**
         * 订单状态
         */
        String ORDER_STATUS = "ORDER_STATUS";

        /**
         * 审核状态
         */
        String APPROVE_STATUS = "APPROVE_STATUS";

        /**
         * 审核流程类型
         */
        String PROCESS_TYPE = "PROCESS_TYPE";

        /**
         * 服务实例状态
         */
        String SERVICE_INASTANCE_STATUS = "SERVICE_INASTANCE_STATUS";

        /**
         * 是否可用
         */
        String AVAILABLITY = "AVAILABLITY";

        /**
         * 运行状态
         */
        String PERFORMANCE = "PERFORMANCE";

        /**
         * 租户类型
         */
        String TENANT_TYPE = "TENANT_TYPE";

        /**
         * 计费状态
         */
        String BILL_STATUS = "BILL_STATUS";

        /**
         * 租户管理员状态
         */
        String TENANT_ADMIN = "TENANT_ADMIN";

        /**
         * 租户用户状态
         */
        String TENANT_USER = "TENANT_USER";

        /**
         * 运营管理员状态
         */
        String OPERATION_ADMIN = "OPERATION_ADMIN";

        /**
         * 运维管理员状态
         */
        String MAINTENANCE_ADMIN = "MAINTENANCE_ADMIN";

        /**
         * 服务模板状态
         */
        String SERVICE_TEMPLATE_STATUS = "SERVICE_TEMPLATE_STATUS";

        /**
         * 资源池状态
         */
        String RESOURCE_POOL_STATUS = "RESOURCE_POOL_STATUS";

        /**
         * 资源实例状态
         */
        String RESOURCE_INSTANCE_STATUS = "RESOURCE_INSTANCE_STATUS";

        /**
         * 云主机资源实例状态
         */
        String RES_HOST_INSTANCE_STATUS = "RES_HOST_INSTANCE_STATUS";

        /**
         * 块存储资源实例状态
         */
        String RES_STORAGE_INSTANCE_STATUS = "RES_STORAGE_INSTANCE_STATUS";

        /**
         * IP资源实例状态
         */
        String RES_IP_INSTANCE_STATUS = "RES_IP_INSTANCE_STATUS";

        /**
         * IP资源状态
         */
        String IP_RESOURCE_STATUS = "IP_RESOURCE_STATUS";

        /**
         * IP池类型
         */
        String IP_POOL_TYPE = "IP_POOL_TYPE";

        /** */
        String OPERATION_TYPE = "OPERATION_TYPE";

        /** */
        String B_DETAIL = "B_DETAIL";

        /**
         * 资源状态
         */
        String RESOURCE_STATUS = "RESOURCE_STATUS";

        /**
         * 虚机平台类型
         */
        String VIRTUAL_PLATFORM_TYPE = "VIRTUAL_PLATFORM_TYPE";

        /**
         * 分配策略
         */
        String ALLOCATION_POLICY = "ALLOCATION_POLICY";

        /**
         * 性能保障等级
         */
        String PERF_LEVEL = "PERF_LEVEL";

        /**
         * VLAN类型
         */
        String VLAN_TYPE = "VLAN_TYPE";

        /**
         * 存储类型
         */
        String STORAGE_TYPE = "STORAGE_TYPE";

        /**
         * 主机类型
         */
        String HOST_TYPE = "HOST_TYPE";

        /**
         * 管理状态
         */
        String MANAGEMENT_STATUS = "MANAGEMENT_STATUS";

        /**
         * 使用状态
         */
        String USAGE_STATUS = "USAGE_STATUS";

        /**
         * CPU类型
         */
        String CPU_TYPE = "CPU_TYPE";

        /**
         * IP类型
         */
        String IP_TYPE = "IP_TYPE";

        /**
         * IP分类
         */
        String IP_CATEGORY = "IP_CATEGORY";

        /**
         * 租户状态
         */
        String TENANT_STATUS = "TENANT_STATUS";

        /**
         * 企业类型
         */
        String BUSINESS_TYPE = "BUSINESS_TYPE";

        /**
         * 用户类型
         */
        String USER_TYPE = "USER_TYPE";

        /**
         * 用户状态
         */
        String USER_STATUS = "USER_STATUS";

        /**
         * 操作系统类型
         */
        String OS_TYPE = "OS_TYPE";

        /**
         * 操作系统版本
         */
        String OS_VERSION = "OS_VERSION";

        /**
         * 计费计划类型
         */
        String BILLING_PLAN_TYPE = "BILLING_PLAN_TYPE";

        /**
         * 计费计划状态
         */
        String BILLING_PLAN_STATUS = "BILLING_PLAN_STATUS";

        /**
         * 计费类型(包年包月)
         */
        String BILLING_TYPE_YM = "BILLING_TYPE_YM";

        /**
         * 告警级别
         */
        String ALARM_LEVEL = "ALARM_LEVEL";

        /**
         * 告警类型
         */
        String ALARM_TYPE = "ALARM_TYPE";

        /**
         * 告警指标
         */
        String ALARM_KPI = "ALARM_KPI";

        /**
         * 判断方法
         */
        String CHECK_OPTR = "CHECK_OPTR";

        /**
         * 判断方法
         */
        String BILLING_CHARGE_TYPE = "BILLING_CHARGE_TYPE";

        /**
         * 部署类型
         */
        String DEPLOYMENT_TYPE = "DEPLOYMENT_TYPE";

        /**
         * 部署软件类型
         */
        String SOFTWARE_TYPE = "SOFTWARE_TYPE";

        /**
         * 部署软件版本
         */
        String SOFTWARE_VERSION = "SOFTWARE_VERSION";

        /**
         * 硬盘类型
         */
        String HARD_DISK_TYPE = "HARD_DISK_TYPE";

        /**
         * 存储类别
         */
        String STORAGE_CATEGORY = "STORAGE_CATEGORY";

        /**
         * 存储用途
         */
        String STORAGE_PURPOSE = "STORAGE_PURPOSE";

        /**
         * 告警状态
         */
        String ALARM_STATUS = "ALARM_STATUS";

        /**
         * 物理主机操作系统
         */
        String HOST_OS_TYPE = "HOST_OS_TYPE";

        /**
         * 工单问题分类
         */
        String QUESTION_TYPE = "QUESTION_TYPE";

        /**
         * 工单问题级别
         */
        String QUESTION_LEVEL = "QUESTION_LEVEL";

        /**
         * 工单状态
         */
        String TICKET_STATUS = "TICKET_STATUS";

        /**
         * 租户配额
         */
        String TENANT_QUOTA = "TENANT_QUOTA";
    }

    /**
     * 订单状态
     */
    public interface OrderType {

        /**
         * 新购
         */
        String NEW_BUY = "01";
    }

    /**
     * 订单审批状态代码
     */
    public interface OrderStatusCd {

        /**
         * 01：已创建
         */
        String DRAFT = "01";

        /**
         * 02：审核中
         */
        String APPROVING = "02";

        /**
         * 03：已审批
         */
        String APPROVED = "03";

        /**
         * 04：开通中
         */
        String OPENING = "04";

        /**
         * 05：已开通
         */
        String OPENED = "05";

        /**
         * 99：已取消
         */
        String CANCEL = "99";

        // ChengQi start
        /**
         * 98：已拒绝
         */
        String REJECTED = "98";
        // ChengQi end

        /**
         * 97:未支付
         */
        String NO_PAY = "97";

        /**
         * 97:以支付
         */
        String PAYED = "96";
    }

    /**
     * 资源池类型代码
     */
    public interface ResPoolType {

        /**
         * 主机资源池
         */
        String RES_POOL_VM = "RES-POOL-VM";

        /**
         * IP资源池
         */
        String RES_POOL_IP = "RES-POOL-IP";

        /**
         * VLAN资源池
         */
        String RES_POOL_VLAN = "RES-POOL-VLAN";

        /**
         * 存储资源池
         */
        String RES_POOL_STORAGE = "RES-POOL-STORAGE";

        /**
         * EXCHANGE资源池
         */
        String RES_POOL_EXCHANGE = "RES-POOL-EXCHANGE";

        /**
         * SHAREPOINT资源池
         */
        String RES_POOL_SHAREPOINT = "RES-POOL-SHAREPOINT";

        /**
         * 物理机资源池
         */
        String RES_POOL_PM = "RES-POOL-PM";

    }

    /**
     * 资源类型代码
     */
    public interface ResourceType {

        /**
         * 虚拟机资源
         */
        String RES_VM = "RES-VM";

        /**
         * 虚拟磁盘资源
         */
        String RES_VD = "RES-VD";

        /**
         * 主机资源
         */
        String RES_HOST = "RES-HOST";

        /**
         * IP资源
         */
        String RES_IP = "RES-IP";

        /**
         * VLAN资源
         */
        String RES_VLAN = "RES-VLAN";

        /**
         * 存储资源
         */
        String RES_STORAGE = "RES-STORAGE";

        /**
         * EX资源
         */
        String RES_EXCHANGE = "RES-EXCHANGE";

        /**
         * SP资源
         */
        String RES_SHAREPOINT = "RES-SHAREPOINT";

        /**
         * PM资源
         */
        String RES_PM = "RES-PM";

        /**
         * 网络资源
         */
        String RES_NETWORK = "RES-NETWORK";

        /**
         * CDN
         */
        String RES_CDN = "RES-CDN";

        /**
         * 软件资源
         */
        String RES_SOFTWARE = "RES-SOFTWARE";

        /**
         * 软件资源
         */
        String RES_FLOATING_IP = "RES-FLOATING-IP";
    }

    /**
     * 资源实例类型代码
     */
    public interface ResourceInstanceType {

        /**
         * 主机实例资源
         */
        String RES_INST_VM = "RES-INST-VM";

        /**
         * IP实例资源
         */
        String RES_INST_IP = "RES-INST-IP";

        /**
         * VLAN实例资源
         */
        String RES_INST_VLAN = "RES-INST-VLAN";

        /**
         * 存储实例资源
         */
        String RES_INST_STORAGE = "RES-INST-STORAGE";

        /**
         * EXCHANGE实例资源
         */
        String RES_INST_EXCHANGE = "RES-INST-EXCHANGE";

        /**
         * SHAREPOINT实例资源
         */
        String RES_INST_SHAREPOINT = "RES-INST-SHAREPOINT";

        /**
         * 自动化部署实例资源
         */
        String RES_INST_DEPLOYMENT = "RES-INST-DEPLOYMENT";

        /**
         * 物理机实例资源
         */
        String RES_INST_PM = "RES-INST-PM";

    }

    /**
     * 服务配置Email模板代码
     */
    public interface ServiceConfigEmail {

        /**
         * 开通成功通知用户Email
         */
        String SEND_TO_OWNER = "SEND_TO_OWNER";

        /**
         * 开通成功通知租户Email
         */
        String SEND_TO_TENANT = "SEND_TO_TENANT";

        /**
         * 开通成功通知运维管理员Email
         */
        String SEND_TO_OM_MANAGER = "SEND_TO_OM_MANAGER";

        /**
         * 退订云主机服务通知用户Email
         */
        String UNSUBSCRIBE_VM_SEND_TO_OWNER = "UNSUBSCRIBE_VM_SEND_TO_OWNER";

        /**
         * 退订云主机服务通知租户Email
         */
        String UNSUBSCRIBE_VM_SEND_TO_TENANT = "UNSUBSCRIBE_VM_SEND_TO_TENANT";
    }

    /**
     * 服务配置Activiti流程
     */
    public interface ServiceConfigActiviti {

        /**
         * 01 VM服务审批流程
         */
        String VM_SERVICE_APPROVE_PROCESS1 = "VM_SERVICE_APPROVE_PROCESS";

        /**
         * 02 VM服务自动开通流程
         */
        String VM_SERVICE_START_PROCESS1 = "VM_SERVICE_START_PROCESS";

        /**
         * 03 VM服务退订流程
         */
        String VM_CANCEL_PROCESS = "VM_CANCEL_PROCESS";

        /**
         * 04 EXCHANGE订单审批流程
         */
        String EXCHANGE_SERVICE_APPROVE_PROCESS = "EXCHANGE_SERVICE_APPROVE_PROCESS";

        /**
         * 05 EXCHANGE服务自动开通流程
         */
        String EXCHANGE_SERVICE_START_PROCESS = "EXCHANGE_SERVICE_START_PROCESS";

        /**
         * 06 EXCHANGE服务退订流程
         */
        String EXCHANGE_CANCEL_PROCESS = "EXCHANGE_CANCEL_PROCESS";

        /**
         * 07 SHAREPOINT订单审批流程
         */
        String SHAREPOINT_SERVICE_APPROVE_PROCESS = "SHAREPOINT_SERVICE_APPROVE_PROCESS";

        /**
         * 08 SHAREPOINT服务自动开通流程
         */
        String SHAREPOINT_SERVICE_START_PROCESS = "SHAREPOINT_SERVICE_START_PROCESS";

        /**
         * 09 SHAREPOINT服务退订流程
         */
        String SHAREPOINT_CANCEL_PROCESS = "SHAREPOINT_CANCEL_PROCESS";

        /**
         * 01 CLOUD服务审批流程
         */
        String CLOUD_SERVICE_APPROVE_PROCESS = "CLOUD_SERVICE_APPROVE_PROCESS";

        /**
         * 02 CLOUD服务自动开通流程
         */
        String CLOUD_SERVICE_START_PROCESS = "CLOUD_SERVICE_START_PROCESS";

        /**
         * 03 VM服务退订流程
         */
        String CLOUD_SERVICE_CANCEL_PROCESS = "CLOUD_SERVICE_CANCEL_PROCESS";

        /**
         * 11 闲置资源回收流程
         */
        String FREE_RESOURCE_REDUCE_PROCESS = "FREE_RESOURCE_REDUCE_PROCESS";

        /**
         * 12  实例变更流程
         */
        String INSTANCE_ADJUST_PROCESS = "INSTANCE_ADJUST_PROCESS";

        /**
         * 13 项目经理变更流程
         */
        String CHANGE_MANAGER_PROCESS = "CHANGE_MANAGER_PROCESS";

        /**
         * 14  虚机所属项目变更流程
         */
        String CHANGE_VM_MGT_OBJ_PROCESS = "CHANGE_VM_MGT_OBJ_PROCESS";
    }

    /**
     * 服务实例状态代码
     */
    public interface ServiceInstanceStatus {

        /**
         * 0 待开通
         */
        String PENDING = "00";

        /**
         * 1 开通中
         */
        String OPENING = "01";

        /**
         * 2 无效实例
         */
        String INVALID = "02";

        /**
         * 3 已开通
         */
        String OPENED = "03";

        /**
         * 4 初始化异常
         */
        String EXCEPTION = "04";

        /**
         * 5 已禁用
         */
        String DISABLED = "05";

        /**
         * 6 退订中
         */
        String CANCELING = "06";

        /**
         * 7 变更中
         */
        String CHANGEING = "07";

        /**
         * 8 已拒绝
         */
        String REFUSED = "08";


        /**
         * 9 已退订
         */
        String CANCELED = "99";
    }

    /**
     * 资源状态代码
     */
    public interface ResourceStatus {

        /**
         * 01 可用
         */
        String AVAILABLE = "01";

        /**
         * 02 预占中
         */
        String PREBOOK = "02";

        /**
         * 03 已占用
         */
        String OCCUPIED = "03";

        /**
         * 04 禁用
         */
        String DISABLED = "04";

    }

    /**
     * 资源实例状态
     */
    public interface ResourceInstanceStatus {

        /**
         * 01 预占中
         */
        String PREBOOK = "01";

        /**
         * 02 使用中
         */
        String USED = "02";

        /**
         * 03 已停用
         */
        String STOPED = "03";

        /**
         * 99 已释放
         */
        String RELEASED = "99";

    }

    /**
     * 主机资源实例状态
     */
    public interface ResHostInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 正常
         */
        String NORMAL = "02";

        /**
         * 03 已关机
         */
        String SHUTDOWN = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已销毁
         */
        String DESTROY = "99";

    }

    /**
     * 存储资源实例状态
     */
    public interface ResStorageInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * IP资源实例状态
     */
    public interface ResIpInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * 消费记录操作类型
     */
    public interface billingAccountCdrOpType {

        /**
         * 01 充值
         */
        String DEPOSIT = "01";

        /**
         * 02 扣款
         */
        String CUT_PAYMENT = "02";
    }

    /**
     * 消费记录状态
     */
    public interface billingAccountCdrStatus {

        /**
         * 01 交易成功
         */
        String SUCCESS = "01";

        /**
         * 02 交易失败
         */
        String FAILURE = "02";

    }

    /**
     * EXCHANGE资源实例状态
     */
    public interface ResExchangeInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * SHAREPOINT资源实例状态
     */
    public interface ResSharepointInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * 物理机资源实例状态
     */
    public interface ResPmInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * DEPLOYMENT资源实例状态
     */
    public interface ResDeploymentInstanceStatus {

        /**
         * 00 预占中
         */
        String PREBOOK = "00";

        /**
         * 01等待中
         */
        String WAITING = "01";

        /**
         * 02 可用
         */
        String AVAILABLE = "02";

        /**
         * 03 使用中
         */
        String USING = "03";

        /**
         * 04 已暂停
         */
        String PAUSE = "04";

        /**
         * 99 已删除
         */
        String DELETED = "99";

    }

    /**
     * 资源实例类型
     */
    public interface ResInstType {

        /**
         * 南向接口创建虚机URL
         */
        String RES_INST_VM = "RES-INST-VM";
    }

    /**
     * IP资源状态
     */
    public interface ResIpStatus {

        /**
         * 00 预占中
         */
        String PRE_OCCUPIED = "00";

        /**
         * 01 未使用
         */
        String UNOCCUPIED = "01";

        /**
         * 02 已使用
         */
        String OCCUPIED = "02";

    }

    /**
     * IP资源状态
     */
    public interface ResIpUsageStatus {

        /**
         * 02 使用中
         */
        String USEING = "02";

        /**
         * 01 未使用
         */
        String AVAILABLE = "01";

    }

    /**
     * IP池类型
     */
    public interface IpPoolType {

        /**
         * 01 内网
         */
        String INTRANET = "1";

        /**
         * 02 公网
         */
        String NETWORK = "2";

    }

    /**
     * activiti流程图名称
     */
    public interface ActivitiFlow {

        /**
         * 01 订单审批流程
         */
        String ORDER_APPROVE_PROCESS = "VApproveProcess";

        /**
         * 02 服务自动开通流程
         */
        String ORDER_SERVICE_START_PROCESS = "OrderServiceStartProcess";

        /**
         * 03 服务退订流程
         */
        String SERVICE_CANCEL_PROCESS = "ServiceCancelProcess";

    }

    /**
     * 服务实例规格类型
     */
    public interface InstanceSpecType {

        /**
         * 01 CPU核数
         */
        String CPU = "cpu";

        /**
         * 02 内存大小
         */
        String MEMORY = "memory";

        // /** 03 数据盘 */
        // String DATA_DISK = "DATA_DISK";

        /**
         * 04 是否需要外网IP
         */
        String NEED_WAN = "NEED_WAN";

        /**
         * 05 性能保障等级
         */
        String PERF_LEVEL = "PERF_LEVEL";

        /**
         * 06 VLAN类型
         */
        String VLAN_TYPE = "VLAN_TYPE";

        /**
         * 07 操作系统
         */
        String OS = "os";

        /**
         * 08 SHAREPOINT_VOLUME
         */
        String SHAREPOINT_VOLUME = "SHAREPOINT_VOLUME";

        /**
         * 09 EACH_MAIL_VOLUME
         */
        String EACH_MAIL_VOLUME = "EACH_MAIL_VOLUME";

        /**
         * 10 DOMAIN
         */
        String DOMAIN = "DOMAIN";

        /**
         * 11 USER_AMOUNT
         */
        String USER_AMOUNT = "USER_AMOUNT";

        /**
         * 12 STORAGE_TYPE
         */
        String STORAGE_TYPE = "STORAGE_TYPE";

        /**
         * 13 STORAGE_PURPOSE
         */
        String STORAGE_PURPOSE = "STORAGE_PURPOSE";

        /**
         * 14 DISK_SIZE
         */
        String DISK_SIZE = "DISK_SIZE";

        /**
         * 15 deploymentType
         */
        String DEPLOYMENT_TYPE = "deploymentType";

        /**
         * 16 softwareType
         */
        String SOFTWARE_TYPE = "softwareType";

        /**
         * 17 softwareVersion
         */
        String SOFTWARE_VERSION = "softwareVersion";

        /**
         * 18 runOsCategory
         */
        String RUN_OS_CATEGORY = "runOsCategory";

        /**
         * 19 runTargetHost
         */
        String RUN_TARGET_HOST = "runTargetHost";

        /**
         * 20 dbName
         */
        String DB_NAME = "dbName";

        /**
         * 21 dbPassword
         */
        String DB_PASSWORD = "dbPassword";

        /**
         * 22 dbMemoryLimit
         */
        String DB_MEMORY_LIMIT = "dbMemoryLimit";

        /**
         * 23 domainName
         */
        String DOMAIN_NAME = "domainName";

        /**
         * 24 虚拟化平台类型
         */
        String VIRTUAL_PLATFORM_TYPE = "VIRTUAL_PLATFORM_TYPE";

        /**
         * 数据盘
         */
        String DATA_DISK = "dataDisk";

        /**
         * 是否需要内网IP
         */
        String NEED_LAN = "needLan";

        /**
         * 系统盘
         */
        String SYSTEM_DISK = "systemDisk";

        /**
         * 网络
         */
        String NETS = "nets";

        /**
         * 对象存储容量
         */
        String OBJECT_DISK = "OBJECT_DISK";

        /**
         * 宽带规格
         */
        String TAPE_WIDTH = "TAPE_WIDTH";

        /**
         * IP个数
         */
        String IP_COUNT = "IP_COUNT";

        /**
         * 公钥、私钥
         **/
        String KEY_PAIR = "keyPair";

        /**
         * 预安装软件
         **/
        String INSTALL_SOFTWARE = "installSoftware";

        /**
         * 回收方式
         **/
        String RECOVERY_TYPE = "recoveryType";

        /**
         * 备注
         **/
        String REMARK = "remark";

        /**
         * 挂载点
         **/
        String MOUNT_POINT = "MOUNT_POINT";

        /**
         * 文件系统
         **/
        String FILE_SYSTEM = "FILE_SYSTEM";

        /**
         * 区域
         **/
        String REGION = "region";

        /**
         * 分区
         **/
        String ZONE = "zone";

        /**
         * 安全组
         **/
        String SECURITY_GROUP = "securityGroup";

    }

    /**
     * 虚拟平台类型
     */
    public interface VirtualPlatformType {

        /**
         * 01 Vmware
         */
        String VMWARE = "VMware";

        /**
         * 02 OPENSTACK
         */
        String OPENSTACK = "OpenStack";

        /**
         * 03 Hyper-V
         */
        String HYPERV = "Hyper-V";

        /**
         * 04 PowerVM
         */
        String PowerVM = "PowerVM";

        /**
         * 05 HP-UX
         */
        String HP_UX = "HP-UX";

        /**
         * 06 Hmc
         */
        String HMC = "HMC";

        /**
         * 07 IVM
         */
        String IVM = "IVM";

        /**
         * 08 Other
         */
        String OTHER = "Other";

    }

    /**
     * 存储卷类型
     */
    public interface VolumeType {

        /**
         * 01 VG
         */
        String VG = "KVM";

        /**
         * 02 Datastore
         */
        String DATASTORE = "VMware";

        /**
         * 03 Hyper-V
         */
        String HYPERV = "Hyper-V";

        /**
         * 04 PowerVM
         */
        String PowerVM = "PowerVM";

        /**
         * 05 HP-UX
         */
        String HP_UX = "HP-UX";

    }

    /**
     * 存储卷类型
     */
    public interface VolumeStatus {

        /**
         * 01 可用
         */
        String NORMAL = "01";

        /**
         * 02 不可用
         */
        String OUTLINE = "02";

    }

    /**
     * 是否需要外网ip
     */
    public interface NeedWan {

        /**
         * 0 不需要
         */
        String NO = "0";

        /**
         * 1 需要
         */
        String YES = "1";

    }

    /**
     * 审核状态
     */
    public interface ApproveStatus {

        /**
         * 01 同意
         */
        String AGREE = "01";

        /**
         * 02 不同意
         */
        String DISAGREE = "02";

    }

    /**
     * 流程审核类型
     */
    public interface ProcessType {

        /**
         * 01 VM订单开通
         */
        String VM_OPEN = "01";

        /**
         * 02 VM服务退订
         */
        String VM_CANCEL = "02";

        /**
         * 03 EXCHANGE开通
         */
        String EXCHANGE_OPEN = "03";

        /**
         * 04 EXCHANGE退订
         */
        String EXCHANGE_CANCEL = "04";

        /**
         * 05 SHAREPOINT开通
         */
        String SHAREPOINT_OPEN = "05";

        /**
         * 06 SHAREPOINT退订
         */
        String SHAREPOINT_CANCEL = "06";

        /**
         * 07 DEPLOYMENT开通
         */
        String DEPLOYMENT_OPEN = "07";

        /**
         * 08 DEPLOYMENT退订
         */
        String DEPLOYMENT_CANCEL = "08";

        /**
         * 09 块存储开通
         */
        String STORAGE_OPEN = "09";

        /**
         * 10 块存储退订
         */
        String STORAGE_CANCEL = "10";

        /**
         * 11 闲置资源回收
         */
        String FREE_RESOURCE_REDUCE = "11";

        /**
         * 12 实例变更
         */
        String INSTANCE_ADJUST = "12";

        /**
         * 13 对象存储开通
         */
        String OBJECT_STORAGE_OPEN = "13";

        /**
         * 14 对象存储退订
         */
        String OBJECT_STORAGE_CANCEL = "14";

        /**
         * 15 弹性IP服务开通
         */
        String FLOATING_IP_OPEN = "15";

        /**
         * 16 弹性IP服务退订
         */
        String FLOATING_IP_CANCEL = "16";

        /**
         * 17 CDN服务开通
         */
        String CDN_OPEN = "17";

        /**
         * 18 CDN服务开通
         */
        String CDN_CANCEL = "18";

        /**
         * 19 块存储扩容
         */
        String STORAGE_EXPAND = "19";

        /**
         * 20  项目经理变更
         */
        String MANAGER_CHANGE = "20";

        /**
         * 21  项目变更
         */
        String MGT_OBJ_CHANGE = "21";
    }

    /**
     * 流程审核类型
     */
    public interface ProcessApproveStatus {

        /**
         * 01 未审批
         */
        String APPROVING = "01";

        /**
         * 02 已审批
         */
        String APPROVED = "02";

    }

    /**
     * 主机管理状态
     */
    public interface HostManagementStatus {

        /**
         * 01 已纳入管理
         */
        String YES = "01";

        /**
         * 02 未纳入管理
         */
        String NO = "02";

    }

    /**
     * 用户类型
     */
    public interface USER_TYPE {

        /**
         * 01 后台用户
         */
        String BACKGROUND = "01";

        /**
         * 02 消费用户
         */
        String FOREGROUND = "02";

    }

    /**
     * 角色类型
     */
    public interface RORLE_TYPE {

        /**
         * 01 前台角色
         */
        String FOREGROUND = "01";

        /**
         * 02 后台角色
         */
        String BACKGROUND = "02";

    }

    /**
     * 用户状态
     */
    public interface UserStatus {

        /**
         * 1 有效
         */
        String AVAILABILITY = "1";

        /**
         * 0 禁用
         */
        String FORBIDDEN = "0";

        /**
         * 2 待审核
         */
        String NOTAPPROVE = "2";

        /**
         * 3 待激活
         */
        String NOTACTIVATE = "3";

    }

    /**
     * 租户状态
     */
    public interface TenantStatus {

        /**
         * 1 待审核
         */
        String NOTAPPROVE = "1";

        /**
         * 2 正常
         */
        String NORMAL = "2";

        /**
         * 3 禁止
         */
        String FORBIDDEN = "3";

    }

    /**
     * 租户类型
     */
    public interface TenantType {

        /**
         * 01 企业
         */
        String COMPANY = "01";

        /**
         * 02 个人
         */
        String PERSONAL = "02";

    }

    /**
     * 主机管理状态
     */
    public interface ApproveAction {

        /**
         * 01 租户审批
         */
        String TENANT_APPROVE = "TenantApprove";

        /**
         * 02 运维审批
         */
        String ORDER_APPROVE = "OrderApprove";

    }

    /**
     * 调用北向接口返回结果状态
     */
    public interface ResultStatus {

        /**
         * success 成功
         */
        String SUCCESS = "success";

        /**
         * failure 失败
         */
        String FAILURE = "failure";
    }

    /**
     * 流水号类别
     */
    public interface SidCategory {

        /**
         * 01 订单编号
         */
        String ORDER = "ORDER_ID";

        /**
         * 02 用户自定义镜像ID
         */
        String UD_IMAGE_ID = "UD_IMAGE_ID";

        /**
         * 03 工单编号
         */
        String TICKET = "TICKET_NO";

        /**
         * 04 虚拟机名称
         */
        String VM_NAME = "VM_NAME";

        /**
         * 03 磁盘名称
         */
        String VD_NAME = "VD_NAME";

        /**
         * VM_SNAPSHOT_ID 虚拟机快照编号
         */
        String VM_SNAPSHOT_ID = "VM_SNAPSHOT_ID";

        /**
         * VM_BACKUP_ID 虚拟机快照编号
         */
        String VM_BACKUP_ID = "VM_BACKUP_ID";

        /**
         * VD_SNAPSHOT_ID 虚拟机快照编号
         */
        String VD_SNAPSHOT_ID = "VD_SNAPSHOT_ID";

        /**
         * VM_BACKUP_ID 虚拟机快照编号
         */
        String VD_BACKUP_ID = "VD_BACKUP_ID";

    }

    /**
     * IP分类
     */
    public interface IpCategory {

        /**
         * 01 内网
         */
        String INTRANET_IP = "01";

        /**
         * 02 公网
         */
        String PUBLIC = "02";
    }

    /**
     * IP 类型
     */
    public interface IpType {

        /**
         * 01 IPv4
         */
        String IPV4 = "01";

        /**
         * 02 IPv6
         */
        String IPV6 = "02";
    }

    /**
     * IP 类型
     */
    public interface EtherType {

        /**
         * 01 IPv4
         */
        String IPV4 = "IPv4";

        /**
         * 02 IPv6
         */
        String IPV6 = "IPv6";
    }

    /**
     * 使用状态
     */
    public interface UsageStatus {

        /**
         * 01 已使用
         */
        String USED = "01";

        /**
         * 02 未使用
         */
        String UNUSED = "02";
    }

    /**
     * 计费计划类型
     */
    public interface BillingPlanType {

        /**
         * YM 包年包月
         */
        String YM = "YM";

        /**
         * Metering 按量付费
         */
        String METERING = "Metering";
    }

    /**
     * 计费类型
     */
    public interface BillingType {

        /**
         * YEAR 按年
         */
        String YEAR = "Year";

        /**
         * MONTH 按月
         */
        String MONTH = "Month";

        /**
         * DAY 按天
         */
        String DAY = "Day";
    }

    /**
     * 数据字典状态
     */
    public interface CodeEnable {

        /**
         * 1 启用
         */
        String ABLE = "1";

        /**
         * 0 不启用
         */
        String UNABLE = "0";
    }

    /**
     * 服务状态
     */
    public interface ServiceStatus {

        /**
         * 00 已新建
         */
        String CREATED = "00";

        /**
         * 01 已提交
         */
        String COMMITED = "01";

        /**
         * 02 已审批
         */
        String APPROVED = "02";

        /**
         * 03 已发布
         */
        String RELEASED = "03";

        /**
         * 04 已部署
         */
        String DEPLOYED = "04";

        /**
         * 05 已停用
         */
        String DISABLED = "05";

        /**
         * 99 已注销
         */
        String LOGOUT = "99";
    }

    /**
     * 服务状态
     */
    public interface ServiceTemplateStatus {

        /**
         * 00 已新建
         */
        String CREATED = "00";

        /**
         * 01 已发布
         */
        String RELEASED = "01";

        /**
         * 02 已停用
         */
        String DISABLED = "02";

    }

    /**
     * Email模板代码
     */
    public interface MailTemplateId {

        /**
         * 激活账号Email
         */
        String EMAIL_TO_ACTIVATE = "4";

        /**
         * 意见反馈Email
         */
        String EMAIL_TO_FEEDBACK = "5";

        /**
         * 重置密码Email
         */
        String EMAIL_TO_CHANGEPWD = "8";

        /**
         * 准备开通服务通知Email
         */
        String PRE_LAUNCH_SERVICE = "21";

        /**
         * 订单审批提醒
         */
        String ORDER_APPROVE_NOTIFY = "22";

        /**
         * 闲置资源审批提醒
         */
        String FREE_RES_APPROVE_NOTIFY = "23";

        /**
         * 报表通知
         */
        String EMAIL_TO_EXCEL = "24";

        /**
         * 注册完成通知
         */
        String EMAIL_TO_REGISTER = "25";

        /**
         * 审核完成通知
         */
        String EMAIL_TO_APPROVE = "26";

        /**
         * 资源闲置回收通知
         */
        String FREE_RES_RECOVER_NOTIFY = "27";

        /**
         * 服务开通成功通知
         */
        String SERVICE_OPEN_SUCCESS_EMAIL = "2";

        /**
         * 服务变更成功通知
         */
        String SERVICE_CHANGE_SUCCESS_EMAIL = "28";

        /**
         * 服务退订成功通知
         */
        String SERVICE_CENCAL_SUCCESS_EMAIL = "29";

        /**
         * 告警信息运维管理员通知邮件
         */
        String TICKET_NOTICE_ADMIN_SEND = "30";

        /**
         * 闲置报表通知
         */
        String FRES_EMAIL_TO_EXCEL = "31";

        /**
         * 回收报表通知
         */
        String RECOVERY_EMAIL_TO_EXCEL = "32";

        /**
         * 运维周报通知
         */
        String WEEKREPORT_EMAIL_TO_EXCEL = "33";

        /**
         * 待审核用户注册邮件通知
         */
        String PENDING_TO_EMAIL = "34";

        /**
         * 项目到期邮件通知
         */
        String MGTOBJ_EXPIREDATE_TO_EMAIL = "35";

        /**
         * 项目申请，运维管理员审核通知邮件
         */
        String MGTOBJ_APPLY_ADMIN_EMAIL = "36";
        /**
         * 项目审核完成，项目经理通知邮件
         */
        String APPROVE_PROJECT_EMAIL = "37";
//		/**项目到期，资源回收通知邮件*/
//		String RES_RECYCLING_NOTIFY_EMAIL = "38";
        /**服务申请单开通完成，相关人员通知邮件*/

        /**
         * 工单分配，相关人员通知邮件
         */
        String TICKET_ALLOCATION_NOTICE_EMAIL = "38";
        /**
         * 服务审核拒绝邮件通知
         */
        String ORDER_REFUSE_NOTIFY = "39";
        /**
         * 告警信息邮件通知
         */
        String ALARM_INFO_EMAIL = "40";
    }

    /**
     * 检查开通实例状态
     */
    public interface CheckInstanceStatus {

        /**
         * VMWare开启
         */
        String POWERED_ON = "poweredOn";

        /**
         * KVM 开启
         */
        String ACTIVE = "ACTIVE";

        /**
         * 关机
         */
        String POWERED_OFF = "poweredOff";

        /**
         * 暂停
         */
        String SUSPENDED = "suspended";

        /**
         * 正常
         */
        String NORMAL = "NORMAL";

        /**
         * 不正常
         */
        String NON_NORMAL = "NON_NORMAL";

        /**
         * 错误
         */
        String ERROR = "ERROR";
    }

    /**
     * 检查开通实例状态
     */
    public interface RepeatOrder {

        /**
         * 0 否
         */
        String NO = "0";

        /**
         * 1 是
         */
        String YES = "1";

    }

    /**
     * 存储目的
     */
    public interface StoragePurpose {

        /**
         * 01 系统盘
         */
        String SYSTEM_DISK = "01";

        /**
         * 02 数据盘
         */
        String DATA_DISK = "02";

        /**
         * 03 系统数据盘
         */
        String SYSTEM_DATA_DISK = "03";
    }

    /**
     * 分配方式
     */
    public interface AllocationMode {

        /**
         * 按内存
         */
        String MEMORY = "M";

        /**
         * 按CPU
         */
        String CPU = "C";

    }

    /**
     * 分配策略
     */
    public interface AllocationPolicy {

        /**
         * 均分
         */
        String STRIPING = "Striping";

        /**
         * 填满
         */
        String PACKING = "Packing";

        /**
         * 轻负载
         */
        String LOAD_AWARE = "Load-Aware";

        /**
         * 高可用
         */
        String HA_AWARE = "HA-Aware";

    }

    /**
     * 告警状态
     */
    public interface AlarmStatus {

        /**
         * 未处理
         */
        String UNTREATED = "01";

        /**
         * 已确认
         */
        String CONFIRMED = "02";

        /**
         * 已清除
         */
        String CLEARED = "03";

    }

    /**
     * 租户配额
     */
    public interface TenantQuota {

        /**
         * 虚拟内核
         */
        String CORES = "cores";

        /**
         * 内存MB
         */
        String RAMS = "rams";

        /**
         * 云主机
         */
        String INSTANCES = "instances";

        /**
         * 外置存储
         */
        String STORAGES = "storageQuota";

        /**
         * 虚机个数
         */
        String VM_NUM = "vmNum";

        /**
         * 元数据条目
         */
        String METADATA_ITEMS = "metadata_items";

        /**
         * 注入的文件
         */
        String INJECTED_FILES = "injected_files";

        /**
         * 注入的文件内容字节数
         */
        String INJECTED_FILE_CONTENT_BYTES = "injected_file_content_bytes";

        /**
         * 存储GB
         */
        String GIGABYTES = "gigabytes";

        /**
         * 块存储
         */
        String VOLUMES = "volumes";

        /**
         * 快照
         */
        String SNAPSHOTS = "snapshots";

        /**
         * 安全组
         */
        String SECURITY_GROUP = "security_group";

        /**
         * 安全组规则
         */
        String SECURITY_GROUP_RULE = "security_group_rule";

        /**
         * 浮动IP
         */
        String FLOATINGIP = "floatingip";

        /**
         * 网络
         */
        String NETWORK = "network";

        /**
         * 端口
         */
        String PORT = "port";

        /**
         * 路由
         */
        String ROUTER = "router";

        /**
         * 子网
         */
        String SUBNET = "subnet";

    }

    /**
     * 计费状态
     */
    public interface BillStatus {

        /**
         * 月结完成
         */
        String MONTH_COMPLETED = "01";

        /**
         * 已审核
         */
        String APPROVED = "02";

        /**
         * 已缴费
         */
        String PAYED = "03";

        /**
         * 作废
         */
        String CANCELLATION = "09";

    }

    /**
     * 资费计划状态
     */
    public interface BillPlanStatus {

        /**
         * 1 启用
         */
        String ABLE = "01";

        /**
         * 0 不启用
         */
        String UNABLE = "02";
    }

    /**
     * 计费计价类型
     */
    public interface BillingChargeType {

        /**
         * 01 增量收费
         */
        String INCREMENT_CHARGE = "01";

        /**
         * 02 固定收费
         */
        String FIX_CHARGE = "02";
    }

    /**
     * 工单状态
     */
    public interface TicketStatus {

        /**
         * 01 新工单
         */
        String CREATED = "01";

        /**
         * 02 已分配
         */
        String ALLOCATED = "02";

        /**
         * 03 处理中
         */
        String PROCESSING = "03";

        /**
         * 04 已解决
         */
        String RESOLVE = "04";

        /**
         * 05 已关闭
         */
        String CLOSE = "05";

        /**
         * 99 已取消
         */
        String CANCEL = "99";

    }

    /**
     * 工单操作
     */
    public interface TicketOperate {

//		/** 01 前台回复 */
//		String FRONT_REPLY = "01";
//
//		/** 02 前台关闭 */
//		String FRONT_CLOSE = "02";
//
//		/** 03 后台回复 */
//		String BACK_REPLY = "03";
//
//		/** 04 后台关闭 */
//		String BACK_CLOSE = "04";
//
//		/** 05 后台移除 */
//		String BACK_REMOVE = "05";

        /**
         * 01 自动处理处理
         */
        String MANUAL_HANDLER = "01";

        /**
         * 02 手动处理
         */
        String AUTO_HANDLER = "02";

    }

    /**
     * 单点登录用户类型操作
     */
    public interface SSOUserType {

        /**
         * tenantUser 租户用户
         */
        String TENANT_USER = "tenantUser";

        /**
         * tenantAdmin 租户管理员
         */
        String TENANT_ADMIN = "tenantAdmin";

        /**
         * plantformAdmin 后台管理员
         */
        String PLANTFORM_ADMIN = "platformAdmin";

    }

    /**
     * 镜像状态
     */
    public interface ImageStatus {

        /**
         * 00已新建
         */
        String NEWCREATE = "00";

        /**
         * 01 已发布
         */
        String AVAILABILITY = "01";

        /**
         * 02 已停用
         */
        String FORBIDDEN = "02";

    }

    /**
     * 镜像类型
     */
    public interface ImageType {

        /**
         * 01 公有
         */
        String PUBLIC = "01";

        /**
         * 02 私有
         */
        String PRIVATE = "02";
    }

    /**
     * 发现主机状态
     */
    public interface FIND_HOST_STATUS {

        /**
         * 获取信息中
         */
        String GETING_INFO = "01";

        /**
         * 未部署系统
         */
        String NO_DEPLOYMENT_SYSTEM = "02";

        /**
         * 系统部署中
         */
        String SYSTEM_DEPLOYMENTING = "03";

        /**
         * 已部署系统
         */
        String DEPLOYED_SYSTEM = "04";

        /**
         * 已加入平台
         */
        String ADDED_TO_PLATFORM = "05";

    }

    /**
     * 系统日志级别
     */
    public interface SYS_LOG_LEVEL {

        /**
         * 提示
         */
        String INFO = "01";

        /**
         * 警告
         */
        String WARNING = "02";

        /**
         * 错误
         */
        String ERROR = "03";

        /**
         * 严重错误
         */
        String FATAL = "04";
    }

    /**
     * 系统日志操作结果
     */
    public interface SYS_LOG_RESULT {

        /**
         * 失败
         */
        String FAIL = "01";

        /**
         * 成功
         */
        String SUCCESS = "02";
    }

    /**
     * 前后台门户区分名称
     */
    public interface PlatformName {

        /**
         * 前台消费门户
         */
        String PORTAL = "PORTAL";

        /**
         * 后台管理门户
         */
        String PLATFORM = "PLATFORM";
    }

    /**
     * 资费类型
     */
    public interface BILLING_TYPE {

        /**
         * 年
         */
        String YEAR = "Year";

        /**
         * 月
         */
        String MONTH = "Month";

        /**
         * 天
         */
        String DAY = "Day";
    }

    /**
     * 资费计划类型
     */
    public interface BILLING_PLAN_TYPE {

        /**
         * 包年包月
         */
        String YM = "YM";

        /**
         * 按量付费
         */
        String METERING = "Metering";
    }

    /**
     * 资费计划状态
     */
    public interface BILL_PLAN_STATUS {

        /**
         * 禁用
         */
        String UNABLE = "02";

        /**
         * 启用
         */
        String ABLE = "01";
    }

    /**
     * 收费类型
     */
    public interface BILLING_CHARGE_TYPE {

        /**
         * 增量收费
         */
        String INCREMENT_CHARGE = "01";

        /**
         * 固定收费
         */
        String FIX_CHARGE = "02";
    }

    /**
     * 计费状态
     */
    public interface BILL_STATUS {

        /**
         * 未缴费
         */
        String UNPAYED = "01";

        /**
         * 已缴费
         */
        String PAYED = "02";

        /**
         * 已作废
         */
        String CANCELLATION = "09";
    }

    /**
     * 服务实例状态代码
     */
    public interface SERVICE_INSTANCE_CD {

        /**
         * 0 已创建
         */
        String CREATED = "00";

        /**
         * 1 开通中
         */
        String OPENING = "01";

        /**
         * 2 无效实例
         */
        String INVALID = "02";

        /**
         * 3 已开通
         */
        String OPENED = "03";

        /**
         * 4 初始化异常
         */
        String EXCEPTION = "04";

        /**
         * 5 已禁用
         */
        String DISABLED = "05";

        /**
         * 6 退订中
         */
        String CANCELING = "06";

        /**
         * 9 已退订
         */
        String CANCELED = "99";
    }

    /**
     * 订单状态
     */
    public interface ORDER_STATUS {

        /**
         * 1 已创建
         */
        String CREATED = "01";

        /**
         * 2 审核中
         */
        String CHECKING = "02";

        /**
         * 3 已审核
         */
        String INVALID = "03";

        /**
         * 4 开通中
         */
        String OPENING = "04";

        /**
         * 5 已开通
         */
        String OPENED = "05";

        /**
         * 9 已退订
         */
        String UNSUBSCRIBED = "09";

        /**
         * 9 已取消
         */
        String CANCELED = "99";
    }

    /**
     * 订单状态
     */
    public interface RES_TOPOLOGY_TYPE {

        /**
         * 1 区域
         */
        String REGION = "R";

        /**
         * 2 数据中心
         */
        String DC = "DC";

        /**
         * 3 虚拟化环境
         */
        String VE = "VE";

        /**
         * 4 虚拟化资源集群
         */
        String VC = "VC";

        /**
         * 5 资源分区
         */
        String RZ = "RZ";

        /**
         * 6 x86虚拟化资源池
         */
        String PCVX = "PCVX";

        /**
         * 7 x86非虚拟化资源池
         */
        String PCX = "PCX";

        /**
         * 8 Power虚拟化资源池
         */
        String PCVP = "PCVP";

        /**
         * 9 Power非虚拟化资源池
         */
        String PCP = "PCP";

        /**
         * 10 存储资源池
         */
        String PS = "PS";

        /**
         * 11 网络资源池
         */
        String PN = "PN";

        /**
         * 12 DVS
         */
        String DVS = "PND";

        /**
         * 13 VLAN池
         */
        String PNV = "PNV";

        /**
         * 14 内部网络池
         */
        String PNI = "PNI";

        /**
         * 15 外部网络池
         */
        String PNE = "PNE";

        /**
         * 16 公网IP池
         */
        String PNP = "PNP";

        /**
         * 17 存储类别
         */
        String RSC = "RSC";

        /**
         * 18 计算资源池
         */
        String PC = "PC";
    }

    /**
     * 任务状态
     */
    public interface TaskStatus {

        /**
         * 执行中
         */
        String RUNNING = "01";

        /**
         * 成功
         */
        String SUCCESS = "02";

        /**
         * 失败
         */
        String FAIL = "09";

    }

    /**
     * 任务类型
     */
    public interface TaskType {

		/* 虚拟机 */
        /**
         * 创建虚拟机
         */
        String CREATE_VM = "createVm";
        /**
         * 操作虚拟机
         */
        String OPERATE_VM = "operateVm";
        /**
         * 调整虚拟机配置
         */
        String RECONFIG_VM = "reconfigVm";
        /**
         * 删除虚拟机
         */
        String DELETE_VM = "deleteVm";
        /**
         * 添加虚拟机网卡
         */
        String ADD_NET_VM = "addVmNetwork";
        /**
         * 删除虚拟机网卡
         */
        String DEL_NET_VM = "delVmNetwork";
        /**
         * 迁移虚拟机
         */
        String MIGRATE_VM = "migrateVm";
        /**
         * 同步虚拟机
         */
        String SCAN_VM = "resVmSync";

		/* 镜像 */
        /**
         * 同步虚拟机模板
         */
        String CREATE_IMAGE = "resVeImageSync";

		/* 磁盘 */
        /**
         * 创建虚拟磁盘
         */
        String CREATE_VD = "createVd";

        /**
         * 挂载虚拟磁盘
         */
        String ATTACH_VD = "acttachVd";

        /**
         * 卸载虚拟磁盘
         */
        String DETACH_VD = "detachVd";

        /**
         * 虚拟磁盘扩容
         */
        String EXPAND_VD = "expandVd";

        /**
         * 删除虚拟磁盘
         */
        String DELETE_VD = "deleteVd";


		/* 网络 */
        /**
         * 创建网络
         */
        String CREATE_NETWORK = "createNetwork";

        /**
         * 同步虚拟化环境
         */
        String SCAN_VCENTER = "resVeSync";

        /**
         * 同步集群
         */
        String SCAN_CLUSTER = "resVcSync";

        /**
         * 同步主机
         */
        String SCAN_HOST = "resHostSync";

        /**
         * 同步网络
         */
        String SCAN_NETWORK = "resNetworkSync";

		/* 浮动IP */
        /**
         * 创建浮动IP
         */
        String CREATE_FLOATINGIP = "createFloatingIp";

        /**
         * 挂载浮动IP
         */
        String ATTACH_FLOATINGIP = "attachFloatingIp";

        /**
         * 卸载浮动IP
         */
        String DETACH_FLOATINGIP = "detachFloatingIp";


        /**
         * 删除浮动IP
         */
        String DELETE_FLOATINGIP = "deleteFloatingIp";

		/* 虚拟机快照 */
        /**
         * 创建虚机快照
         **/
        String CREATE_VM_SNAPSHOT = "createVmSnapshot";

        /**
         * 删除虚机快照
         **/
        String DELETE_VM_SNAPSHOT = "deleteVmSnapshot";

        /**
         * 恢复虚机快照
         **/
        String REVERT_VM_SNAPSHOT = "revertVmSnapshot";

		/* 安全组 */
        /**
         * 创建安全组
         **/
        String CREATE_SECURITY_GROUP = "createSecurityGroup";

        /**
         * 删除安全组
         **/
        String DELETE_SECURITY_GROUP = "deleteSecurityGroup";

        /**
         * 创建块存储快照
         **/
        String CREATE_VD_SNAPSHOT = "createVdSnapshot";

        /**
         * 创建块存储备份
         **/
        String CREATE_VD_BACKUP = "createVdBackup";

        /**
         * 恢复块存储快照
         **/
        String REVERT_VD_SNAPSHOT = "revertVdSnapshot";

        /**
         * 恢复块存储备份
         **/
        String REVERT_VD_BACKUP = "revertVdBackup";

        /**
         * 删除块存储快照
         **/
        String DELETE_VD_SNAPSHOT = "deleteVdSnapshot";

        /**
         * 删除块存储备份
         **/
        String DELETE_VD_BACKUP = "deleteVdBackup";

        /**
         * 绑定安全组
         **/
        String BUND_SECURITY_GROUP = "bundSecurityGroup";

        /**
         * 绑定安全组失败
         **/
        String BUND_SECURITY_FAIL = "bundSecurityFail";

        /**
         * 解绑安全组失败
         **/
        String UNBUND_SECURITY_FAIL = "unbundSecurityFail";

        /**
         * 解绑安全组
         **/
        String UNBUND_SECURITY_GROUP = "unbundSecurityGroup";

		/* CDN */
        /**
         * 创建CDN
         **/
        String CREATE_CDN = "createCdn";

        /**
         * 删除CDN
         **/
        String DELETE_CDN = "deleteCdn";

        /**
         * 安装软件
         */
        String INSTALL_SOFTWARE = "installSoftware";

        /**
         * 存储
         */
        String DELETE_DATASTORAGE = "deleteDatastore";
        String EXTEND_DATASTORAGE = "extendDatastore";
        String CREATE_DATASTORAGE = "createDatastore";

    }

    /**
     * 虚拟机状态
     */
    public interface ResVmStatus {

        /**
         * occuping 预占中
         */
        String OCCUPING = "occuping";

        /**
         * creating 创建中
         */
        String CREATING = "creating";

        /**
         * normal 正常
         */
        String NORMAL = "normal";

        /**
         * booting 关机中
         */
        String BOOTING = "booting";

        /**
         * rebooting 重启中
         */
        String REBOOTING = "rebooting";

        /**
         * setting 配置中
         */
        String SETTING = "setting";

        /**
         * poweringOff 关机中
         */
        String POWERINGOFF = "poweringOff";

        /**
         * poweredOff 已关机
         */
        String POWEREDOFF = "poweredOff";

        /**
         * pasueing 暂停中
         */
        String PAUSEING = "pasueing";

        /**
         * paused 已关机
         */
        String PAUSED = "已暂停";

        /**
         * suspending 挂起中
         */
        String SUSPENDING = "suspending";

        /**
         * suspended 已挂起
         */
        String SUSPENDED = "suspended";

        /**
         * migrating 迁移中
         */
        String MIGRATING = "migrating";

        /**
         * failure 故障
         */
        String FAILURE = "failure";

        /**
         * deleting 销毁中
         */
        String DELETING = "deleting";

        /**
         * deleted 已销毁
         */
        String DELETED = "deleted";

        /**
         * recovering 恢复中
         */
        String RECOVERING = "recovering";

        /**
         * Firmware 打开固件
         */
        String FIRMWARE = "firmware";

        /**
         * Unavailable 不可用
         */
        String UNAVAILABLE = "unavailable";

    }

    /**
     * 主机状态
     */
    public interface ResHostStatus {

        /**
         * 01 正常
         */
        String NORMAL = "01";

        /**
         * 02 离线
         */
        String OUTLINE = "02";

        /**
         * 03 维护
         */
        String STANDBY = "03";

        /**
         * 04 故障
         */
        String UNKNOWN = "04";

    }

    /**
     * 存储状态
     */
    public interface ResStorageStatus {

        /**
         * 01 可用
         */
        String NORMAL = "01";

        /**
         * 02 故障
         */
        String UNKNOWN = "02";

        /**
         * 03 不可用
         */
        String OUTLINE = "03";


    }

    /**
     * 存储状态
     */
    public interface ResStorageType {

        /**
         * VMFS
         */
        String VMFS = "VMFS";

        /**
         * NFS
         */
        String NFS = "NFS";

        /**
         * CIFS
         */
        String CIFS = "CIFS";

        /**
         * VFAT
         */
        String VFAT = "VFAT";
    }

    /**
     * 存储类别
     */
    public interface StorageCategory {

        /**
         * 01 本地存储
         */
        String LOCAL = "01";

        /**
         * 02 共享存储
         */
        String SHARE = "02";

        /**
         * 03 CINDER块存储
         */
        String CINDER = "03";

    }

    /**
     * 虚拟化环境连接状态
     */
    public interface ResVeConnectStatus {

        /**
         * 01 正常
         */
        String SUCCESS = "01";

        /**
         * 02 离线
         */
        String FAILED = "02";
    }

    /**
     * 虚拟化环境更新状态
     */
    public interface ResVeUpdateStatus {

        /**
         * 01 更新中
         */
        String UPDATING = "01";

        /**
         * 02 更新成功
         */
        String UPDATE_SUCCESS = "02";

        /**
         * 03 更新失败
         */
        String UPDATE_FAIL = "09";
    }

    /**
     * 配额关联对象类型
     */
    public interface QuotaObjectType {

        /**
         * 0 业务
         */
        Long BIZ = 0L;

        /**
         * 1 组织
         */
        Long ORG = 1L;

        /**
         * 2 租户
         */
        Long TENANT = 2L;
    }

    /**
     * 问题状态
     */
    public interface IssueStatus {

        /**
         * 01 新问题
         */
        String NEW = "01";

        /**
         * 02 已回复
         */
        String REPLIED = "02";

    }

    /**
     * 问题回复记录类型
     */
    public interface IssueReplyType {

        /**
         * 01 管理员回复
         */
        String ADMIN = "01";

        /**
         * 02 用户回复
         */
        String USER = "02";
    }

    /**
     * 虚拟机操作
     */
    public interface VmOperation {

        /**
         * 01 开启
         */
        String START = "start";

        /**
         * 02 关闭
         */
        String STOP = "stop";

        /**
         * 03 重启
         */
        String REBOOT = "reboot";

        /**
         * 04 暂停
         */
        String PAUSE = "pause";

        /**
         * 05  取消暂停
         */
        String UNPAUSE = "unpause";

        /**
         * 06 挂起
         */
        String SUSPEND = "suspend";

        /**
         * 07 继续
         */
        String RESUME = "resume";

        /**
         * 08 退订
         */
        String DESTORY = "destory";

        /**
         * 09 迁移
         */
        String MIGRATE = "migrate";

        /**
         * 10 Power开通
         */
        String ACTIVATE = "activate";

        /**
         * 11 Power关机
         */
        String SHUTDOWN = "shutdown";
    }

    /**
     * 网卡操作
     */
    public interface NetOperate {

        /**
         * add 添加
         */
        String ADD = "add";

        /**
         * delete 删除
         */
        String DELLETE = "delete";

        /**
         * unchange 不变
         */
        String UNCHANGE = "unchange";
    }

    /**
     * 网卡操作
     */
    public interface VdOperate {

        /**
         * add 添加
         */
        String ADD = "add";

        /**
         * expand 扩容
         */
        String EXPAND = "expand";

        /**
         * delete 删除
         */
        String DELLETE = "delete";
    }

    /**
     * 磁盘状态
     */
    public interface ResVdStatus {

        /**
         * occuping 预占中
         */
        String OCCUPING = "occuping";

        /**
         * creating 创建中
         */
        String CREATING = "creating";


        /**
         * setting 配置中
         */
        String SETTING = "setting";

        /**
         * setting 配置中
         */
        String RECOVERING = "recovering";

        /**
         * normal 正常
         */
        String NORMAL = "normal";

        /**
         * failure 故障
         */
        String FAILURE = "failure";

        /**
         * deleting 删除中
         */
        String DELETING = "deleting";

        /**
         * deleted 删除
         */
        String DELETED = "deleted";

    }

    /**
     * 磁盘状态
     */
    public interface ResVmNetworkStatus {

        /**
         * occuping 预占中
         */
        String OCCUPING = "occuping";

        /**
         * creating 创建中
         */
        String CREATING = "creating";

        /**
         * setting 配置中
         */
        String SETTING = "setting";

        /**
         * normal 正常
         */
        String NORMAL = "normal";

        /**
         * failure 故障
         */
        String FAILURE = "failure";

        /**
         * deleting 删除中
         */
        String DELETING = "deleting";

        /**
         * deleted 删除
         */
        String DELETED = "deleted";

    }

    /**
     * 网络类型
     */
    public interface ResNetworkType {

        /**
         * 01 内部
         */
        String PRIVATE = "01";

        /**
         * 02 外部
         */
        String PUBLIC = "02";

        /**
         * 03 自定义
         */
        String CUSTOM = "03";
    }

    /**
     * 实例规格状态
     */
    public interface SpecStatus {

        /**
         * 0 处理中
         */
        String changing = "0";

        /**
         * 1 有效
         */
        String valid = "1";

        /**
         * 2 无效
         */
        String invalid = "2";
    }


    public interface IdcConstants {

        /**
         * 服务订单_合同号
         */
        String CONTRACTID = "IDC000000";
    }

    public interface IdcDispatchCode {

        /**
         * IDC接口反馈成功
         */
        String SUCCESS = "000000";

        /**
         * IDC接口反馈失败
         */
        String FAILURE = "000001";
    }

    public interface IdcServCode {

        /**
         * 勘查
         */
        String CHECK = "20001";

        /**
         * 开通
         */
        String OPEN = "20002";

        /**
         * 撤单
         */
        String CANCEL = "20005";

        /**
         * 退订
         */
        String UNSUBSCRIBE = "20010";

        /**
         * 同步
         */
        String SYNC = "20011";

        /**
         * 操作
         */
        String OPERATE = "20013";

        /**
         * 故障
         */
        String INCIDENT = "20014";
    }

    public interface IdcVmOpType {

        /**
         * 增加
         */
        Long ADD = 1L;

        /**
         * 删除
         */
        Long DELETE = 2L;

        /**
         * 变更
         */
        Long CHANGE = 3L;
    }

    /**
     * 问题级别
     */
    public interface QuestionLevel {

        String BEST_LOW = "01";

        String LOW = "02";

        String NORMAL = "03";

        String HIGH = "04";

        String BEST_HIGH = "05";
    }

    /**
     * 服务变更记录状态
     */
    public interface ServiceChangeStatus {

        Long NOT_CHANGE = 0L;

        Long CHANGED = 1L;

        Long FAIL_CHANGE = 2L;

        Long CHANGE_DISAGREE = 3L;
    }

    /**
     * 资源池配置Key
     */
    public interface ResPoolConfigKey {

        /**
         * 资源分配策略
         */
        String ALLOCATIONPOLICY = "allocation_policy";

        /**
         * 资源分配方式
         */
        String ALLOCATIONMODE = "allocation_mode";

        /**
         * 资源分配比率
         */
        String ALLOCATIONRATE = "allocation_rate";

        /**
         * 资源可分配阀值
         */
        String ALLOCATIONTHRESHOLD = "allocation_threshold";
    }

    /**
     * 资源池配置Key
     */
    public interface ResVsType {

        /**
         * 01 分布式交换机
         */
        String DISTRIBUTE_VS = "01";

        /**
         * 02 标准交换机
         */
        String STANDARD_VS = "02";

    }

    /**
     * 资源池配置Key
     */
    interface ResConfig {

        /**
         * 资源环境类型
         */
        String RES_ENV_TYPE = "res_env_type";
        /**
         * 资源环境ID
         */
        String RES_ENV_ID = "res_env_id";
        /**
         * Openstack区域名称
         */
        String REGION_NAME = "region_name";

    }


    /**
     * 天馈接口常数
     */
    public interface TKMonitorNode {

        String OPERATE_ADD = "0";

        String OPERATE_UPDATE = "1";

        String OPERATE_DEL = "2";

        String STATUS_UNMONITORED = "未监控";

        String STATUS_MONITORED = "已监控";

    }

    /**
     * 主网卡标识常数
     */
    public interface NetPrimary {

        /**
         * P 主网卡
         */
        String P = "P";

    }

    /**
     * 是否回调服务层
     */
    public interface NeedCallbackService {

        /**
         * true 是
         */
        boolean YES = true;

        /**
         * false 否
         */
        boolean NO = false;

    }

    /**
     * 工单处理类型
     */
    public interface TicketProcessType {

        /**
         * 01 VM订单开通
         */
        String VM_OPEN = "01";

        /**
         * 02 VM服务退订
         */
        String VM_CANCEL = "02";

        /**
         * 03 EXCHANGE开通
         */
        String EXCHANGE_OPEN = "03";

        /**
         * 04 EXCHANGE退订
         */
        String EXCHANGE_CANCEL = "04";

        /**
         * 05 SHAREPOINT开通
         */
        String SHAREPOINT_OPEN = "05";

        /**
         * 06 SHAREPOINT退订
         */
        String SHAREPOINT_CANCEL = "06";

        /**
         * 07 DEPLOYMENT开通
         */
        String DEPLOYMENT_OPEN = "07";

        /**
         * 08 DEPLOYMENT退订
         */
        String DEPLOYMENT_CANCEL = "08";

        /**
         * 07 存储开通
         */
        String STORAGE_OPEN = "09";

        /**
         * 08 存储退订
         */
        String STORAGE_CANCEL = "10";

        /**
         * 11 闲置资源回收
         */
        String FREE_RESOURCE_REDUCE = "11";

        /**
         * 12 实例变更
         */
        String INSTANCE_ADJUST = "12";

        /**
         * 13   磁盘缩容
         */
        String DISK_REDUCE = "13";

        /**
         * 14 对象存储开通
         */
        String OBJECT_STORAGE_OPEN = "14";

        /**
         * 15 弹性IP开通
         */
        String FLOATING_IP_OPEN = "15";

        /**
         * 16 对象存储退订
         */
        String OBJECT_STORAGE_CANCEL = "16";

        /**
         * 17 弹性IP退订
         */
        String FLOATING_IP_CANCEL = "17";

        /**
         * 18 弹性IP退订
         */
        String CDN_OPEN = "18";

        /**
         * 19 弹性IP退订
         */
        String CDN_CANCEL = "19";

        /**
         * 20 块存储扩容
         */
        String STORAGE_EXPAND = "20";

    }

    /**
     * 配置类型
     **/
    public interface ConfigType {

        /**
         * 首页图的显示
         */
        String INDEX_CHART = "indexshow_config";

        /**
         * 首页资源配置
         */
        String RES_CONFIG = "res_config";

        /**
         * 邮件地址配置
         */
        String EMAIL_ADDRESS = "email_config";

        /**
         * 其他
         */
        String OTHER = "other_config";

    }

    /**
     * 扫描虚拟化环境时，标识虚拟机变化
     **/
    public interface scanVmChangeType {

        /**
         * 虚拟机变化
         */
        String CHANGE = "change";

        /**
         * 虚拟机删除
         */
        String DELETE = "delete";

    }

    /**
     * IDC订单状态
     */
    public interface IdcOrderStatus {

        /**
         * 已撤销
         */
        Long CANCELED = 0L;

        /**
         * 待审核
         */
        Long NOT_APPROVE = 1L;

        /**
         * 审核通过
         */
        Long APPROVED_PASS = 2L;

        /**
         * 审核拒绝
         */
        Long APPROVED_REJECTED = 3L;

        /**
         * 待开通
         */
        Long WAIT_OPEN = 4L;

        /**
         * 处理中
         */
        Long PROCESSING = 5L;

        /**
         * 处理完成
         */
        Long COMPLETED = 6L;

        /**
         * 已反馈
         */
        Long FEEDBACKED = 7L;

    }

    /**
     * IDC订单明细状态
     */
    public interface IdcOrderDetailStatus {

        /**
         * 待处理
         */
        Long PENDING = 1L;

        /**
         * 处理中
         */
        Long PROCESSING = 2L;

        /**
         * 处理成功
         */
        Long SUCCESS = 3L;

        /**
         * 处理失败
         */
        Long FAILURE = 4L;

    }

    /**
     * IDC操作记录状态
     */
    public interface IdcServiceRecordStatus {

        /**
         * 待处理
         */
        Long PENDING = 0L;

        /**
         * 已处理
         */
        Long COMPLETED = 1L;
    }

    /**
     * IDC操作类型
     */
    public interface IdcServiceRecordOpType {

        /**
         * 勘察
         */
        Long CHECK = 1L;

        /**
         * 开通
         */
        Long OPEN = 2L;

        /**
         * 撤单
         */
        Long CANCEL = 3L;

        /**
         * 退订
         */
        Long UNSUBSCRIBE = 4L;
    }

    /**
     * IDC订单类型
     */
    public interface IdcOrderType {

        /**
         * 开通
         */
        Long OPEN = 1L;

        /**
         * 变更
         */
        Long CHANGE = 2L;

        /**
         * 退订
         */
        Long UNSUBSCRIBE = 3L;
    }

    /**
     * IDC-故障申报状态
     */
    public interface IdcIncidentStatus {

        /**
         * 未处理
         */
        String UNHANDLE = "01";

        /**
         * 处理中
         */
        String HANDLING = "02";

        /**
         * 已处理
         */
        String HANDLED = "03";

        /**
         * 已反馈
         */
        String FEEDBACKED = "04";

        /**
         * 已关闭
         */
        String CLOSED = "99";
    }

    /**
     * BIZ属性
     */
    public interface BIZ_TYPE {

        /**
         * 1 自有合作
         */
        Long COOPERATION = 1L;

        /**
         * 2内容引入
         */
        Long CONTENT = 2L;

        /**
         * 3 创新业务
         */
        Long INNOVATION = 3L;
    }

    /**
     * IDC-处理结果反馈状态
     */
    public interface IdcFeedbackStatus {

        /**
         * 解决
         */
        String SOLVED = "1";

        /**
         * 未解决
         */
        String UNRESOLVED = "2";

        /**
         * 撤销
         */
        String REVOKE = "3";
    }

    /**
     * 重启类型
     */
    public interface RebootType {

        /**
         * 软重启
         */
        String SOFT = "SOFT";

        /**
         * 硬重启
         */
        String HARD = "HARD";

    }

    /**
     * 管理对象类型
     */
    public interface MgtObjType {

        /**
         * 个人
         */
        Long PERSON = 1L;

        /**
         * 企业
         */
        Long ENTERPRISE = 2L;
    }

    /**
     * 管理对象分组ID
     */
    public interface MgtObjGroupId {

        /**
         * 个人
         */
        Long PERSON = 112L;

        /**
         * 企业
         */
        Long ENTERPRISE = 111L;
    }

    /**
     * 备份类型
     */
    public interface BACKUP_TYPE {

        /**
         * 01 快照
         */
        String SNAPSHOT = "01";

        /**
         * 02 备份
         */
        String BACKUP = "02";
    }

    /**
     * 快照状态
     **/
    public interface BACKUP_STATUS {

        /**
         * 01 创建中
         */
        String CREATING = "creating";

        /**
         * 02 创建完成
         */
        String CREATE_SUCCESS = "normal";

        /**
         * 03  删除中
         */
        String DELETING = "deleting";

    }

    /**
     * 网络状态
     */
    public interface NETWORK_STATUS {

        /**
         * 01 创建中
         */
        String CREATING = "01";

        /**
         * 02 创建成功
         */
        String CREATE_SUCCESS = "02";

        /**
         * 03 创建失败
         */
        String CREATE_FAILURE = "03";

        /**
         * 04 删除中
         */
        String REMOVING = "04";

        /**
         * 05 删除失败
         */
        String REMOVE_FAILURE = "05";

    }

    /**
     * 对象存储状态
     */
    public interface ResObjStorageInstStatus {

        /**
         * creating 创建中
         */
        String CREATING = "creating";

        /**
         * normal 创建成功
         */
        String NORMAL = "normal";

    }

    /**
     * 对象存储状态
     */
    public interface ResFloatingIpStatus {

        /**
         * creating 创建中
         */
        String CREATING = "creating";

        /**
         * normal 创建成功
         */
        String NORMAL = "normal";

        /**
         * deleting 创建成功
         */
        String DELETING = "deleting";

        /**
         * deleted 创建成功
         */
        String DELETED = "deleted";

        /**
         * error 异常
         */
        String ERROR = "error";
    }


    /**
     * 安全组状态
     **/
    public interface SECURITY_GROUP_STATUS {

        /**
         * 01 创建中
         */
        String CREATING = "creating";

        /**
         * 02 创建完成
         */
        String CREATE_SUCCESS = "normal";

        /**
         * 03  删除中
         */
        String DELETING = "deleting";

    }


    /**
     * CDN结果
     **/
    public interface CdnResult {

        /**
         * 0 成功
         */
        String SUCCESS = "0";

        /**
         * 1 已启用或已停用
         */
        String OPERATED = "1";

        /**
         * 2 失败
         */
        String FAILED = "2";
    }

    /**
     * CDN实例状态
     **/
    public interface CdnInstStatus {

        /**
         * normal 正常
         */
        String NORMAL = "normal";

        /**
         * deleted 已退订
         */
        String DELETED = "deleted";

    }


    public interface instanceChangeType {

        /**
         * 创建
         */
        String CREATE = "1";

        /**
         * 变更
         */
        String CHANGE = "2";

        /**
         * 退订
         */
        String UNSUBSCRIBE = "3";

        /**
         * 项目变更项目经理
         */
        String CHANGE_MANAGER = "4";

        /**
         * 虚机变更项目
         */
        String CHANGE_MGT_OBJ = "5";
    }

    public interface instanceChangeStatus {

        /**
         * 待变更
         */
        String UNCHANGE = "1";

        /**
         * 变更中
         */
        String CHANGEING = "2";

        /**
         * 变更完成
         */
        String CHANGED = "3";

        /**
         * 变更取消
         */
        String CANCELED = "0";
    }


    /**
     * 计费时，不满一月是否按照一月计算
     */
    public interface IS_BILLING_MONTH {

        /**
         * 0  是否
         */
        int NO = 0;

        /**
         * 1 创建完成
         */
        int YES = 1;

    }

    /**
     * 管理对象状态
     */
    public interface MGT_OBJ_STATUS {

        /**
         * 01  待审核
         */
        String UNAPPROVE = "01";

        /**
         * 02  正常
         */
        String NORMAL = "02";

        /**
         * 03  禁用
         */
        String DISABLE = "03";

        /**
         * 04  变更中
         */
        String SETTING = "04";

    }

    /**
     * 项目资源回收方式
     */
    public interface RECOVERY_REMARK_TYPE {

        /**
         * 1  到期提醒
         */
        String NOTICE = "1";

        /**
         * 2 到期回收
         */
        String RECOVER = "2";

    }

    /**
     * 配额类型
     */
    public interface QUOTA_TYPE {

        /**
         * Linux,Windows
         */
        String X86 = "Linux,Windows";

        /**
         * AIX
         */
        String AIX = "AIX";


    }

    public interface OS_TYPE {

        /**
         * Windows系列操作系统
         */
        String WINDOWS = "Windows";

        /**
         * Linux系列操作系统
         */
        String LINUX = "Linux";

        /**
         * AIX系列操作系统
         */
        String AIX = "AIX";

    }

    /**
     * 主机是否安装VIOS
     *
     * @author yxu
     */
    public interface VirtualIoServerCapable {

        /**
         * 1 是
         */
        String YES = "1";

        /**
         * 0 否
         */
        String NO = "0";

    }

    /**
     * 主机是否安装VIOS
     *
     * @author yxu
     */
    public interface HostItemTypeCode {

        /**
         * 1 本地盘
         */
        String LOCAL_DISK = "1";

        /**
         * 2 光纤卡
         */
        String OPTICAL_CARD = "2";

        /**
         * 3 网卡
         */
        String NETWORK_CARD = "3";

        /**
         * ethernet 网卡端口
         */
        String ETHERNET = "Ethernet";

        /**
         * Disk 挂盘信息
         */
        String DISK = "Disk";

        /**
         * FC FC信息
         */
        String FC = "FC";

    }

    /**
     * 配件资源分配状态标识
     *
     * @author yxu
     */
    public interface ResHostItemAllocFlag {

        /**
         * 0 未占用
         */
        String NOT_OCCUPIED = "1";

        /**
         * 1 已占用
         */
        String OCCUPIED = "2";

    }

    /**
     * 配件资源分配状态
     */
    public interface HostItemAllocStatus {

        /**
         * 0 未占用
         */
        int FREE = 0;

        /**
         * 1 已占用
         */
        int OCCUPY = 1;

    }

    public interface resourceTopologyType {

        /**
         * X86虚拟机化资源池
         */
        String PCVX = "PCVX";

        /**
         * X86非虚拟机资源池
         */
        String PCX = "PCX";

        /**
         * Power虚拟化资源池
         */
        String PCVP = "PCVP";

        /**
         * Power非虚拟机化资源池
         */
        String PCP = "PCP";
    }

    /**
     * 工单类型
     */
    public interface ticketType {

        /**
         * 日常维护工单
         */
        String DAILY_MAINTENANCE_TICKET = "01";

        /**
         * 云主机自动开通失败工单
         */
        String VM_AUTO_OPEN_FAILURE_TICKET = "02";

        /**
         * 物理机开通工单
         */
        String HOST_OPEN_TICKET = "03";

        /**
         * AIX系统外置盘开通工单
         */
        String AIX_DISK_OPEN_TICKET = "04";

        /**
         * AIX系统外置盘卸载工单
         */
        String AIX_DISK_REMOVE_TICKET = "05";

        /**
         * 软件安装工单
         */
        String SOFTWARE_INSTALL_TICKET = "06";

        /**
         * 云主机自动回收失败工单
         */
        String VM_AUTO_UNSUBSCRIBE_FAILURE_TICKET = "07";

        /**
         * 云主机自动变更失败工单
         */
        String VM_AUTO_CHANGE_FAILURE_TICKET = "08";

    }

    /**
     * 终审资源类型
     */
    public interface ResType {

        /**
         * 虚拟机
         */
        String VM = "1";

        /**
         * 主机
         */
        String HOST = "2";

    }

    /**
     * 虚拟机化环境
     */
    public interface VirtualEnv {

        String X86 = "VMware";


        String POWER = "HMC,IVM";

    }

    /**
     * 操作系统软件状态
     */
    public interface OsSoftwareStatus {

        /**
         * 待安装
         */
        String WAITING = "01";

        /**
         * 安装中
         */
        String PROCESSING = "02";

        /**
         * 已安装
         */
        String INSTALLED = "03";

        /**
         * 异常
         */
        String EXCEPTION = "99";

    }

    /**
     * Power分区类型
     */
    public interface PowerPartitionType {

        /**
         * Lpar 物理分区
         */
        String LPAR = "0";

        /**
         * Mpar 虚拟分区
         */
        String MPAR = "1";
    }

    /**
     * 主机是否有VIOS 01:"否"；"02":"是"
     */
    public interface IsViosFlag {

        /**
         * The NO.
         */
        String NO = "01";

        /**
         * The YES.
         */
        String YES = "02";
    }

    /**
     * 文件系统
     */
    public interface FileSystem {

        String JSF2 = "JSF2";

        String NTFS = "NTFS";

        String EXT4 = "ext4";
    }

    /**
     * 磁盘类型 MQ参数
     */
    public interface VmDiskType {

        /**
         * 系统盘
         */
        String SYS_DISK = "sysDisk";
        /**
         * 数据盘
         */
        String DATA_DISK = "dataDisk";
    }

    /**
     * 审核的分配方式
     */
    public interface AllocateType {

        /**
         * 创建
         */
        String CREATE = "1";
        /**
         * 纳管
         */
        String NANOTUBE = "2";
    }

    /**
     * 账户状态
     */
    public interface ACCOUNT_STATUS {

        /**
         * 01 激活
         */
        String ACTIVE = "01";

        /**
         * 02 未激活
         */
        String INACTIVE = "02";

        /**
         * 03 冻结
         */
        String FREEZE = "03";
    }

    /**
     * 账户类型
     *
     * @author chxiaoqi
     */
    public interface ACCOUNT_TYPE {

        /**
         * 01 个人
         */
        String PERSONAL = "01";

        /**
         * 02 企业
         */
        String ENTERPRISE = "02";

        /**
         * 03 管理员
         */
        String ADMINISTRATION = "03";
    }

    /**
     * 资源释放方式
     *
     * @author Chaohong.Mao
     */
    public interface ReleaseMode {

        /**
         * 随实例释放
         */
        String WITH_INSTANCE = "1";
        /**
         * 单独释放
         */
        String STAND_ALONE = "2";
    }

    /**
     * 充值支付状态
     */
    public interface RECHARGE_STATUS {

        /**
         * 0未支付
         */
        String NO_PAY = "0";

        /**
         * 1已支付
         */
        String PAYED = "1";
    }

    /**
     * 币种
     */
    public interface CURRENCY {

        /**
         * 01 RMB
         */
        String RMB = "01";

        /**
         * 02 USD
         */
        String USD = "02";

    }

    /**
     * 充值方式
     */
    public interface CHARGE_CHANNEL {

        /**
         * 01 支付宝
         */
        String ALIPAY = "01";

        /**
         * 02 礼品卡
         */
        String HYCARD = "02";

        /**
         * 03 银行转账
         */
        String BANK = "03";

        /**
         * 04 微信充值
         */
        String WEIXIN = "04";

        /**
         * 06 直充
         */
        String ZHICHONG = "05";
    }

    public interface ACTION_LEVEL {

        String LEVEL1 = "1";  //基础操作级
        String LEVEL2 = "2";  //业务操作级
    }

    public interface ACT_TARGET{
        String USER_MANAGEMENT = "01";
        //用户中心
        String USER_CENTER = "02";
        //订单管理
        String ORDER_MANAGEMENT = "03";
        //云主机
        String VM_MANAGEMENT = "04";
        //弹性公网IP
        String EIP_MANAGEMENT="05";
    }

    public interface CHARGE_UNIT_ZH{
        String RMB = "元";
        String DOLLOR ="美元";
    }

}
