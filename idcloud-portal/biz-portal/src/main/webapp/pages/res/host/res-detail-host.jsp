<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
   <!-- 查看主机详情 -->
	   <div id="detailHostWindow">
            <div>主机详情</div>
            <div id="detailHostForm" style="overflow: hidden;">
            	<div class="customPanel" style="width:100%;height:125px;">
            	    <div class="title">基本信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机名称:</td>
		                        <td align="left" ><span id="detail-hostName"></span></td>
		                        <td align="right" width="100">主机全名:</td>
		                        <td align="left" ><span id="detail-fullName"></span></td>
		                        <td align="right" width="100">主机型号:</td>
		                        <td align="left"><span id="detail-hostType"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right">集群名称:</td>
		                        <td align="left"><span id="detail-clusterName"></span></td>
		                        <td align="right">机柜编码:</td>
		                        <td align="left"><span id="detail-rackNumber"></span></td>
		                        <td align="right">机笼Enclosure:</td>
		                        <td align="left"><span id="detail-cageEnclosure"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right" width="80">UUID:</td>
		                        <td align="left"><span id="detail-uuid"></span></td>
		                        <td align="right">制造商:</td>
		                        <td align="left"><span id="detail-vendor"></span></td>
		                        <td align="right">所属数据中心:</td>
		                        <td align="left"><span id="detail-dataCenter"></span></td>
		                    </tr>
		                  
		                </table>
	                </div>
            	</div>
            	
            	<div class="customPanel" style="width:100%;height:125px;margin-top:10px;">
            	    <div class="title">资源信息</div>
		            <div>
		            	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		                    <tr>
		                        <td align="right" width="100"><font style="color:red">*</font>主机IP:</td>
		                        <td align="left"><span id="detail-hostIp"></span></td>
		                        <td align="right" width="100"><font style="color:red">*</font>内存大小(MB):</td>
		                        <td align="left"><span id="detail-memorySize"></span></td>
		                        <td align="right" width="100"><font style="color:red">*</font>CPU个数:</td>
		                        <td align="left"><span id="detail-cpuNumber"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>CPU核数:</td>
		                        <td align="left"><span id="detail-cpuCores"></span></td>
		                        <td align="right">CPU主频(GHz):</td>
		                        <td align="left"><span id="detail-cpuGhz"></span></td>
		                        <td align="right">CPU类型:</td>
		                        <td align="left"><span id="detail-cpuType"></span></td>
		                    </tr>
		                    <tr>
		                        <td align="right"><font style="color:red">*</font>虚拟平台类型:</td>
		                        <td align="left">
		                           <span id="detail-virtualPlatformType"></span>
		                        </td>
		                        <td align="right"><font style="color:red">*</font>操作系统:</td>
		                        <td align="left" colspan="3">
		                           <span id="detail-hostOsType"></span>
		                        </td>
		                    </tr>
		                </table>
	                </div>
            	</div>
                <div style="width:100%;padding-top:10px;text-align:right">
		              <input id="detailhostCancel" type="button" value="取消" />
                </div>
            </div>
       </div>
         
  <script type="text/javascript">
	    var detailhost = new detailHostModel();
	    detailhost.initPopWindow();
  </script>