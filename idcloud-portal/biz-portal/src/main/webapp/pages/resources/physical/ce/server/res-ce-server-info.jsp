<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="x86ServerWindows" style="display: none;">
     <div>详情</div>
     <div id="x86ServerForm" >
<!--      <div id="x86ServerForm" style="overflow: hidden;"> -->
             <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;配置信息</div>
                    <div>
                        <table border="0" width="100%" cellspacing="1" cellpadding="1">
                            <tr>
                                <td align="right" style="width:15%">主机名称:</td>
                                <td align="left" style="width:15%"id="x86hostName"></td>
                                <td align="right" style="width:15%">主机IP:</td>
                                <td align="left" style="width:15%"id="x86hostIp"/></td>
                                <td align="right" style="width:15%">操作系统：</td>
                                <td align="left"style="width:15%"id="x86equipHostOsType"></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">CPU个数:</td>
                                <td align="left" style="width:15%"id="x86cpuNumber"/></td>
                                <td align="right" style="width:15%">CPU核数:</td>
                                <td align="left" style="width:15%"id="x86cpuCores"/></td>
                                <td align="right" style="width:15%">CPU类型：</td>
                                <td align="left" style="width:15%"id="x86cpuType"/></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" style="width:15%"id="x86memorySize"/></td>
                                <td align="right" style="width:15%">主机用户名:</td>
                                <td align="left" style="width:15%"id="x86managementUser"/></td>
                                <td align="right" style="width:15%">主机密码：</td>
                                <td align="left" style="width:15%"id="x86managementPwd"/></td>
                            </tr>
                     </table>
                </div>
                 <div class="customPanel" style="width:99%;height:180px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="1" cellspacing="1">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" style="width:15%"id="x86locationNumber"></td>
                                    <td align="right"  style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="x86name"></td>
                                    <td align="right">设备类型：</td>
                                    <td align="left" style="width:15%"id="x86equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">设备分类：</td>
                                    <td align="left" style="width:15%"id="x86equipCategoryName"></td>
                                    <td align="right"style="width:15%">序列号：</td>
                                    <td align="left" style="width:15%"id="x86serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="x86brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right" style="width:15%">型号：</td>
                                    <td align="left" colspan ="5" style="width:15%"id="x86model"></td>
                                </tr>
                                  <tr>
                                    <td align="right">所属机房：</td>
                                    <td align="left" style="width:15%"id="x86roomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left" style="width:15%"id="x86cabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left" style="width:15%"id="x86rackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">规格：</td>
                                    <td align="left" colspan="7"id="x86spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">描述：</td>
                                    <td align="left" colspan="7"id="x86description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;远程管理信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">远程管理IP1:</td>
                                    <td align="left" style="width:15%"id="x86remoteMgtIp1"></td>
                                    <td align="right"  style="width:15%">远程管理IP2:</td>
                                    <td align="left" style="width:15%"id="x86remoteMgtIp2"></td>
                                    <td align="right"  style="width:15%">关联IP地址：</td>
                                    <td align="left" style="width:15%"id="x86relevanceIp"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">远程管理用户：</td>
                                    <td align="left" style="width:15%"id="x86remoteMgtUser"></td>
                                    <td align="right"style="width:15%">远程管理密码：</td>
                                    <td align="left" style="width:15%"id="x86remoteMgtPwd"></td>
                                </tr>
                        </table>
                    </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:110px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">维保厂商：</td>
                                    <td align="left" style="width:15%"id="x86maintCompany"></td>
                                    <td align="right"style="width:15%">维保电话：</td>
                                    <td align="left" style="width:15%"id="x86maintTel"></td>
                                    <td align="right" style="width:15%">购置日期：</td>
                                    <td align="left" style="width:15%"id="x86purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">保修起始日期：</td>
                                    <td align="left" style="width:15%"id="x86startEndDate"></td>
                                    <td align="right"style="width:15%">保修期限：</td>
                                    <td align="left" style="width:15%"id="x86warrantyPeriod"></td>
                                    <td align="right"style="width:15%">设备提供商：</td>
                                    <td align="left" style="width:15%"id="x86equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:5px;text-align:right;">
                  <br/>
                  <input type="button" id="serverx86-close-button" value="关闭" />
            </div>
     </div>
</div>
</div>

<div id="ibmServerWindows"style="display: none;">
     <div>详情</div>
     <div id="ibmServerForm" >
             <div class="customPanel" style="width:99%;height:120px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;配置信息</div>
                    <div>
                        <table border="0" width="100%" cellspacing="5" cellpadding="0">
                            <tr>
                                <td align="right" style="width:15%">主机名称:</td>
                                <td align="left"style="width:15%"id="ibm-hostName"></td>
                                <td align="right" style="width:15%">主机IP:</td>
                                <td align="left" style="width:15%"id="ibm-hostIp"/></td>
                                <td align="right" style="width:15%">操作系统：</td>
                                <td align="left"style="width:15%"id="ibm-equipHostOsType"></td>
                            </tr>
                            <tr>
                                <td align="right" style="width:15%">CPU个数:</td>
                                <td align="left"style="width:15%"id="ibm-cpuNumber"/></td>
                               <!--  <td align="right">CPU核数:</td>
                                <td align="left" id="cpuCores"/></td> -->
                                <td align="right">CPU类型：</td>
                                <td align="left" style="width:15%"id="ibm-cpuType"/></td>
                                <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" style="width:15%"id="ibm-memorySize"/></td>
                            </tr>
                            <tr>
                                <!-- <td align="right" style="width:15%">内存大小(MB):</td>
                                <td align="left" id="memorySize"/></td> -->
                                <td align="right">主机用户名:</td>
                                <td align="left" style="width:15%"id="ibm-managementUser"/></td>
                                <td align="right" style="width:15%">主机密码：</td>
                                <td align="left" width="85px"id="ibm-managementPwd"/></td>
                                <td align="right" style="width:15%"> 是否虚拟化：</td>
                                <td align="left" width="85px"id="ibm-isVios"/></td>
                            </tr>
                             <tr>
                                <td align="right" style="width:15%">VIOS用户名：</td>
                                <td align="left" width="85px"id="ibm-viosUser"/></td>
                                <td align="right" style="width:15%">VIOS密码：</td>
                                <td align="left"style="width:15%" id="ibm-viosPwd"/></td>
                            </tr>
                     </table>
                </div>
                <div class="customPanel" style="width:99%;height:190px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;基本信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="5">
                                <tr>
                                    <td align="right" style="width:15%">位置编号：</td>
                                    <td align="left" id="ibm-locationNumber"></td>
                                    <td align="right"  style="width:15%">设备编号：</td>
                                    <td align="left" style="width:15%"id="ibm-name"></td>
                                    <td align="right" style="width:15%">设备类型：</td>
                                    <td align="left" style="width:15%"id="ibm-equipType"></td>
                                </tr>
                                <tr>
                                    <td align="right">设备分类：</td>
                                    <td align="left" style="width:15%"id="ibm-equipCategoryName"></td>
                                    <td align="right">序列号：</td>
                                    <td align="left" style="width:15%"id="ibm-serialNumber"></td>
                                    <td align="right">品牌：</td>
                                    <td align="left" style="width:15%"id="ibm-brand" ></td>
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">型号：</td>
                                    <td align="left" style="width:15%"id="ibm-model" colspan="5"></td>
                                    <!-- <td align="left"style="width:15%">所属机架：</td>
                                    <td align="right" style="width:15%"id="equipCabinetName" ></td> -->
                                </tr>
                                <tr>
                                    <td align="right"style="width:15%">所属机房：</td>
                                    <td align="left" style="width:15%"id="ibm-roomName" ></td>
                                    <td align="right">所属机柜：</td>
                                    <td align="left" style="width:15%"id="ibm-cabinetName" ></td>
                                    <td align="right">所属机架：</td>
                                    <td align="left" style="width:15%"id="ibm-rackName" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">规格：</td>
                                    <td align="left" colspan="7"id="ibm-spec" ></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="1">描述：</td>
                                    <td align="left" colspan="7"id="ibm-description" ></td>
                                </tr>
                        </table>
                    </div>
             </div>
            <div class="customPanel" style="width:99%;height:90px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;远程管理信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">远程管理IP1:</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtIp1"></td>
                                    <td align="right"  style="width:15%">远程管理IP2:</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtIp2"></td>
                                    <td align="right"  style="width:15%">关联IP地址：</td>
                                    <td align="left" style="width:15%"id="ibm-relevanceIp"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">远程管理用户：</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtUser"></td>
                                    <td align="right"style="width:15%">远程管理密码：</td>
                                    <td align="left" style="width:15%"id="ibm-remoteMgtPwd"></td>
                                </tr>
                        </table>
                    </div>
            </div>
            
            <div class="customPanel" style="width:99%;height:90px;margin-left:0.5%;margin-top:8px;">
                    <div class="title">&nbsp;&nbsp;维保信息</div>
                    <div>
                          <table border="0" width="100%" cellpadding="0" cellspacing="10">
                                <tr>
                                    <td align="right" style="width:15%">维保厂商：</td>
                                    <td align="left" style="width:15%"id="ibm-maintCompany"></td>
                                    <td align="right" style="width:15%">维保电话：</td>
                                    <td align="left" style="width:15%"id="ibm-maintTel"></td>
                                    <td align="right" style="width:15%">购置日期：</td>
                                    <td align="left" style="width:15%"id="ibm-purchaseDate"></td>
                                </tr>
                                <tr>    
                                    <td align="right"style="width:15%">保修起始日期：</td>
                                    <td align="left" style="width:15%"id="ibm-startEndDate"></td>
                                    <td align="right"style="width:15%">保修期限：</td>
                                    <td align="left" style="width:15%"id="ibm-warrantyPeriod"></td>
                                    <td align="right"style="width:15%">设备提供商：</td>
                                    <td align="left" style="width:15%"id="ibm-equipSupplier"></td>
                                </tr>
                          </table>
                    </div>
            </div>
            <div style="width:99%;height:40px;margin-left:0.5%;margin-top:5px;text-align:right;">
                  <br/>
                  <input type="button" id="serveribm-close-button" value="关闭" />
            </div>
     </div>
</div>
</div>