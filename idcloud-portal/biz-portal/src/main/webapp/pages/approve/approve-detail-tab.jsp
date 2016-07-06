<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
	
	<div style="width: 100%; height: 800px; margin-top: 50px;display: none;" id="tabApproveWin">
            <div id="tabwindow" style="display: none">
            	<div class="title">&nbsp;</div>
                <div style="overflow: hidden;" id="winContent">
                <div id="tab2" style="width: 100%; height: 100%;">
                        <ul style="margin-left: 6px;">
							<li style="margin-left: 6px;"><i class='icons-blue icon-tasks'></i>审核明细</li>
		                	<li><i class='icons-blue icon-list'></i>审核历史</li>
                        </ul>
                        
	<div id="approveMgtWindow">
		<div id="approveMgtForm" style="overflow: hidden; position: relative;">
			<input type="hidden" id="processInstanceId" /> <input type="hidden"
				id="approvorAction" /> <input type="hidden" id="processType" />
			<input type="hidden" id="detailSid" />
			<input type="hidden" id="orderId" />
			<input type="hidden" id="processObjectId" />
	
			<table id="apporveTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
				<!-- 
				<tr>
					<td align="right" valign="top" width="130px"><font
						class="title1"></font>信息:</td>
					<td align="left"><font id="content"></font></td>
				</tr>
				-->
				<tr><td colspan="2">
					<table border="0" width="100%" cellspacing="5" cellpadding="0">
						<tr>
							<td align="right">订单编号:</td>
							<td align="left"><span id="unapprove-orderNumber"></span></td>
							<!-- 
							<td align="right">所属租户:</td>
							<td align="left"><span id="unapprove-ownerTenant"></span></td>
							<td align="right">商务合同号:</td>
							<td align="left"><span id="unapprove-contractId"></span></td>
							<td align="right">项目立项号:</td>
							<td align="left"><span id="unapprove-projectId"></span></td>
							 -->
							<td align="right">申请人:</td>
							<td align="left"><span id="unapprove-owner"></span></td>
						</tr>
						<tr>
							<td align="right">订单状态:</td>
							<td align="left"><span id="unapprove-orderStatus"></span></td>
							<td align="right">申请时间:</td>
							<td align="left"><span id="unapprove-openTime"></span></td>
						</tr>
						<tr>
							<td align="right">所属项目:</td>
							<td align="left"><span id="unapprove-mgtObjName"></span></td>
							<td align="right"></td>
							<td align="left"></td>
						</tr>
					</table>
				</td></tr>
				<tr><td colspan="2">
					<div id="unapprove-orderDetailsGrid"></div>
				</td></tr>
				<tr><td colspan="2">
						<div id="unapprove-instance-tab">
							<ul style="margin-left: 6px;">
								<li style="margin-left: 6px;"><i class='icons-blue icon-list'></i>规格明细列表</li>
								<li id="bizResourceTbl"><i class='icons-blue icon-list'></i>资源选择</li>
							</ul>
							<div id="unapprove-spec-tabcontent">
								<div id="unapprove-serviceSpecGrid" style="margin-top:1px"></div>
							</div>
							<div id="unapprove-resource-tabcontent" style="margin-top:5px">
								
								<table border="0" width="100%" cellspacing="4" cellpadding="0">
									<!-- 
									<tr>
										<td colspan="5" align="right" valign="middle">
											<input type="button" id="applyAll" value="应用资源选择到所有实例" /> 
										</td>
									</tr>
									 -->
									 <tr class="resAllocateType" style="display:none">
										<td width="10"></td>
										<td align="right" width="90">
											分配方式:
										</td>
										<td width="210">
											<div id='allocateType-create' flag="1" style='float: left;margin-left:3px;margin-top:3px;'>创建</div>
											<div id='allocateType-nanotube' flag="2" style='float: left;margin-left:3px;margin-top:3px;'>纳管</div>
										</td>
										<td align="right" width="90">
										</td>
										<td>
										</td>
									</tr>
									<tr class="resType">
										<td width="10"></td>
										<td align="right" width="90">
											开通资源类型:
										</td>
										<td width="210">
											<div id='openResTypeVm'  style='float: left;margin-top:3px;'>虚拟机</div>
											<div id='openResTypeHost' style='float: left;margin-top:3px;'>物理机</div>
										</td>
										<td align="right" width="90">
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr class="resPartition" style="display:none" >
										<td width="10"></td>
										<td align="right" width="90">
											分区类型:
										</td>
										<td width="210">
											<div id="partitionType" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
										</td>
										<td>
										</td>
									</tr>
									<tr class="resCompute" style="display:none" >
										<td width="10"></td>
										<td align="right" width="90">
											资源环境:
										</td>
										<td width="210" align="right">
											<input id="resMgtObjSid" type="hidden">
											<input id="resVe" type="hidden">
											<input id="rescomuteid" type="hidden" >
											<input id="rescomuteType" type="hidden" >
											<div id="rescomuteLabel">
                            					<div id="rescomuteTree"></div>
                            				</div>
										</td>
										<td align="right" width="90">
										</td>
										<td>
										</td>
									</tr>
									<tr class="resSwitch" style="display:none" >
										<td width="10"></td>
										<td align="right" width="90">
											虚拟机交换机:
										</td>
										<td width="210">
											<div id="virtualSwitch" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
											CPU资源池:
										</td>
										<td>
											<div id="cpuResPool" style="float: left;margin-left: 3px;"></div>
										</td>
									</tr>
									<tr class="resHba" style="display:none" >
										<td width="10"></td>
										<td align="right" width="90">
											系统盘:
										</td>
										<td width="210">
											<div id="rootHbaCard" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
											 网卡端口：
										</td>
										<td>
											<div id="networkHbaCard" style="float: left;margin-left: 3px;"></div>
										</td>
									</tr>
									<tr class="resWan" id="wanTr1" style="display:none" >
										<td width="10"></td>
										<td align="right" width="90">
											业务外网:
										</td>
										<td>
											<div id="vLanIDO" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
											可选IP地址:
										</td>
										<td>
											<div id="wanIps" style="float: left;margin-left: 3px;"></div>
										</td>
									</tr>
									<tr class="resLan" id="lanTr1" style="display:none">
										<td width="10"></td>
										<td align="right" width="60">
											业务内网:
										</td>
										<td>
											<div id="vLanIDI" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
											可选IP地址:
										</td>
										<td>
											<div id="lanIps" style="float: left;margin-left: 3px;"></div>
										</td>
									</tr>
									<tr class="resStHba" id="stHbaTr" style="display:none">
										<td width="10"></td>
										<td align="right" width="60">
											存储HBA卡:
										</td>
										<td>
											<div id="stHbaDiv" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
										</td>
										<td>
										</td>
									</tr>
									<tr class="resAixSelect" style="display:none">
										<td width="10"></td>
										<td align="right" width="60">
											未纳管的分区:
										</td>
										<td>
											<div id="aixPar" style="float: left;margin-left: 3px;"></div>
										</td>
										<td align="right" width="90">
										</td>
										<td width="295">
										</td>
									</tr>
								</table>
							</div>
						</div>
				</td></tr>
			</table>
			<div>
				<!--
				<div id="bizResourceTbl" style="display:none;" width="100%">
					<div style="float: left;width: 33%;margin-left: 5px;">
						<div style="float: left;line-height: 25px;">计算资源集: </div>
						<div id="rescomuteid" style="float: left;margin-left: 3px;"></div>
					</div>
					<div id="wanTr1" style="display: none;float: left;width: 33%;margin-left: 5px;">
						<div style="float: left;line-height: 25px;">业务外网:</div> 
						<div id="vLanIDO" style="float: left;margin-left: 3px;"></div>
					</div>
					<div id="lanTr1" style="display: none;float: left;width: 30%;margin-left: 5px;">
						<div style="float: left;line-height: 25px;">业务内网:</div> 
						<div id="vLanIDI" style="float: left;margin-left: 3px;"></div>
					</div>
				</div>
				-->
				<table  border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" style="width: 20%;">审核状态:</td>
						<td align="left">
							<div id="approveStatus"></div>
						</td>
						<td>
							 <div id="approveIsNeedPrincipal" style="display:none;color:#767676" >
                    			是否需要云平台负责人审核
                			</div>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top"><font style="color: red">*</font>审批注释:</td>
						<td align="left" colspan="2"><textarea id="approveNote"></textarea></td>
					</tr>
					<tr>
						<td id="checkQuotaMsg" align="left" colspan="3" style="color: red"></td>
					</tr>
				</table>
			</div>
			<div style=" width: 99.5%; height: 25px; text-align: right">
				<input data-bind='click: vmResCheckSubmit' type="button" id="vmResCheck" value="资源检查" /> 
				<input data-bind='click: approveMgtSubmit' type="button"
					id="approveMgtSave" value="确定" /> <input style="margin-right: 6px;"
					id="approveMgtCancel" class="approveMgtCancel" type="button" value="取消" />
			</div>
		</div>
		<!-- 虚拟机变更审核界面 -->
		<div id="instance-approveForm" style="overflow: hidden; position: relative; display:none">
			<input type="hidden" id="instance-processInstanceId" />
			<input type="hidden" id="instance-approvorAction" />
			<input type="hidden" id="instance-processType" />
			<input type="hidden" id="instance-instanceSid" />
			<table id="instance-apporveTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr><td>
					<table border="0" width="100%" cellspacing="5" cellpadding="0">
						<tr>
							<td align="right" width="10%">实例名称:</td>
							<td align="left" width="40%"><span id="instance-instanceName"></span></td>
							<td align="right" width="10%">所有者:</td>
							<td align="left" width="20%"><span id="instance-owner"></span></td>
						</tr>
						<tr>
							<td align="right">所属项目:</td>
							<td align="left"><span id="instance-mgtObjName"></span></td>
							<td align="right"><span id="instance-mgtObjSid" style="display: none;"></span></td>
							<td align="left">&nbsp;</td>
						</tr>
					</table>
				</td></tr>
				<tr><td>
				    <!-- 规格变更grid -->
					<div id="instance-specGrid"></div>
				</td></tr>
				<tr><td>
					<div id="unapprove-spec-tab">
						<ul style="margin-left: 6px;">
							<li style="margin-left: 6px;"><i class='icons-blue icon-list'></i>磁盘变更规格项</li>
<!-- 							<li id="netWorkTab"><i class='icons-blue icon-list'></i>网络变更规格项</li> -->
						</ul>
						<div id="unapprove-diskspec-tabcontent">
							<!-- 磁盘规格变更grid -->
							<div id="instance-diskSpecGrid" style="margin-top:1px"></div>
						</div>
<!-- 					<div id="unapprove-netspec-tabcontent">
							<div id="instance-netSpecGrid" style="margin-top:1px">
								<table class="tableCss">
									<tr style="background-color: #EEEEEE;height:25px;">
										<th style="width: 10%;font-weight: normal;">类型</th>
										<th style="width: 30%;font-weight: normal;">网络</th>
										<th style="width: 30%;font-weight: normal;">IP地址</th>
										<th style="width: 10%;font-weight: normal;">操作</th>
									</tr>
									<tbody id="approve-net-info">
									
									</tbody>
								</table>
							</div>
						</div> -->	
					</div>
				</td></tr>
			</table>
			<div>
				<table  border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr>
						<td align="right" style="width: 24.8%;">审核状态:</td>
						<td align="left">
							<div id="instance-approveStatus"></div>
						</td>
						<td>
							 <div id="instance-isNeedPrincipal" style="display:none;color:#767676" >
                    			是否需要云平台负责人审核
                			</div>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top"><font style="color: red">*</font>审批注释:</td>
						<td align="left" colspan="2"><textarea id="instance-approveNote"></textarea></td>
					</tr>
				</table>
			</div>
			<div style="bottom: 5px; width: 99.5%; height: 25px; text-align: right">
				<input data-bind='click: instanceChangeCheckSubmit' type="button" id="instance-changeCheck" value="资源检查" />
				<input data-bind='click: instanceApproveMgtSubmit' type="button" id="instance-approveMgtSave" value="确定" />
				<input style="margin-right: 6px;" id="instance-approveMgtCancel" class="approveMgtCancel" type="button" value="取消" />
			</div>
		</div>
		
		
		<!-- 虚拟机退订审核界面 -->
		<div id="cancel-approveForm" style="overflow: hidden; position: relative; display:none">
			<input type="hidden" id="cancel-processInstanceId" />
			<input type="hidden" id="cancel-approvorAction" />
			<input type="hidden" id="cancel-processType" />
			<input type="hidden" id="cancel-instanceSid" />
			<table id="cancel-apporveTargetInfo" border="0" width="100%" cellspacing="5" cellpadding="0">
				<tr><td>
					<table border="0" width="100%" cellspacing="5" cellpadding="0">
						<tr>
							<td align="right" width="10%">实例名称:</td>
							<td align="left" width="40%"><span id="cancel-instanceName"></span></td>
							<td align="right" width="10%">所有者:</td>
							<td align="left" width="20%"><span id="cancel-owner"></span></td>
						</tr>
						<tr>
							<td align="right">所属项目:</td>
							<td align="left"><span id="cancel-mgtObjName"></span></td>
							<td align="right"><span id="cancel-mgtObjSid" style="display: none;"></span></td>
							<td align="left">&nbsp;</td>
						</tr>
					</table>
				</td></tr>
				<tr><td>
				    <!-- 规格变更grid -->
					<div id="cancel-specGrid"></div>
				</td></tr>
			</table>
			<div>
				<table  border="0" width="100%" cellspacing="5" cellpadding="0">
					<tr class="cancelTypeTr" style="display: none;">
						<td align="right" style="width: 24.8%;">退订方式:</td>
						<td align="left">
							<div id="cancel-type-create" style="float: left;margin-left:3px;margin-top:3px;">删除虚拟机</div>
							<div id="cancel-type-nanotube" style="float: left;margin-left:13px;margin-top:3px;">取消纳管</div>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td align="right" style="width: 24.8%;">审核状态:</td>
						<td align="left">
							<div id="cancel-approveStatus"></div>
						</td>
						<td>
							<div id="cancel-isNeedPrincipal" style="display:none;color:#767676" >
                    			是否需要云平台负责人审核
                			</div>
						</td>
					</tr>
					<tr>
						<td align="right" valign="top"><font style="color: red">*</font>审批注释:</td>
						<td align="left" colspan="2"><textarea id="cancel-approveNote"></textarea></td>
					</tr>
				</table>
			</div>
			<div style="bottom: 5px; width: 99.5%; height: 25px; text-align: right">
				<input type="button" id="cancel-changeCheck" value="资源检查" />
				<input data-bind='click: cancelApproveMgtSubmit' type="button" id="cancel-approveMgtSave" value="确定" />
				<input style="margin-right: 6px;" id="cancel-approveMgtCancel" class="approveMgtCancel" type="button" value="取消" />
			</div>
		</div>

	</div>

	 	<div id="unapproveHistoryWindow">
			<div id="unapproveHistoryForm" style="overflow: hidden;">
			<div style="border: 0px solid gray; width: 100%; height: 320px;position:relative;">
				<img id="processPic"
					width="100%" height="320px"
					src="" data-bind='event: {load: showUnapprovedHistory}'/>
				给执行的节点加框
				<div id="processTrace"
					style="position: absolute; border: 2px solid red;"></div>
			</div>
			<div id="unapproveHistoryGrid"></div>
			</div>
			<div style="position: absolute; bottom: 5px; width: 99.5%; height: 25px; text-align: right">
				<input style="margin-right: 6px;" id="approveMgtCancel2" class="approveMgtCancel" type="button" value="取消" />
			</div>
		</div>
    </div>
    </div>
    </div>
</div>
