package com.h3c.idcloud.core.service.res.api;

/**
 * 接口 Res vm vnc port service.
 */
public interface ResVmVncPortService {
    /**
     * 根据主键查询同VE下虚拟机的vnc端口占用
     *
     * @param allocateResHostSid the allocate res host sid
     *
     * @return the boolean
     */
    boolean selectByParamByCountVnc(String allocateResHostSid);

    /**
     * 在创建虚拟机时，创建VNC端口
     *
     * @param resTopologySid the res topology sid
     *
     * @return the vnc port
     */
    String getVNCPort(String resTopologySid);

    /**
     * 在虚拟机已经存在的时，添加VNC端口（如：纳管后）
     *
     * @param resVmSid    the res vm sid
     * @param providerUrl the provider url
     * @param authUser    the auth user
     * @param authPass    the auth pass
     * @param isOpen      the is open
     *
     * @return the boolean
     */
    boolean vmResetVNCPort(String resVmSid, String providerUrl, String authUser, String authPass, boolean isOpen);

    /**
     * 创建novnc的tokens文件
     *
     * @param resVmSid the res vm sid
     * @param port     the port
     *
     * @return the boolean
     */
    boolean creatVncTokens(String resVmSid, String port);

    /**
     * 是否开启vnc服务
     *
     * @param resTopologySid the res topology sid
     *
     * @return the boolean
     */
    boolean findVmVncSwitch(String resTopologySid);

    /**
     * 修改novnc的tokens文件
     *
     * @param path       the path
     * @param oldStr     the old str
     * @param replaceStr the replace str
     */
    void modifyVncTokens(String path, String oldStr, String replaceStr);

    /**
     * 删除novnc的tokens文件
     *
     * @param resVmSid the res vm sid
     *
     * @return the boolean
     */
    boolean deleteVncTokens(String resVmSid);

    /**
     * 开启novnc的webconsole控制台
     *
     * @param resVmSid the res vm sid
     * @param host     the host
     *
     * @return the no vnc web console
     */
    String getNoVncWebConsole(String resVmSid, String host);

}