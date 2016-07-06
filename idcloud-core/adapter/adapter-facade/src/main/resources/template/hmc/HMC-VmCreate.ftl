<#if vmType == "mpar">{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"mparName":"${name}",
"mparProfile":"${mparProfile}",
"maxVirtualSlots":"${maxVirtualSlots}",
"lparEnv":"${lparEnv}",
"minMem":${minMem},
"desiredMem":${desiredMem},
"maxMem":${maxMem},
"sharedProcPoolID":${sharedProcPoolID},
"procMode":"${procMode}",
"sharingMode":"${sharingMode}",
"uncapWeight":${uncapWeight},
"minProcs":${minProcs},
"desiredProcs":${desiredProcs},
"maxProcs":${maxProcs},
"minProcUnits":${minProcUnits},
"desiredProcUnits":${desiredProcUnits},
"maxProcUnits":${maxProcUnits},
"host":"${hostName}",
"osName":"${osName}",
"mksysbResource":"${image}",
"adminPass":"${adminPass}",
"veths":
[<#list nics as nic>
{"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"mparName":"${name}",
"host":"${hostName}",
"mparProfile":"${mparProfile}",
"portVlanID":"${nic.portVlanID}",
"microparIP":"${nic.privateIp}",
"microparGateway":"${nic.gateway}",
"virSwitch":"${nic.virSwitch}"},
</#list>],
"vscsiDisks":
[<#list disks as disk>
{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"mparName":"${name}",
"host":"${hostName}",
"mparProfile":"${mparProfile}",
"viosLparName":"${disk.vios.viosLparName}",
"viosIp":"${disk.vios.viosIp}",
"viosUser":"${disk.vios.viosUser}",
"viosPassword":"${disk.vios.viosPassword}",
"luName":<#if disk.name??>"${disk.name}",<#else>"",</#if>
"luSize":<#if disk.size??>"${disk.size}",<#else>"",</#if>
"clusterName":<#if disk.clusterName??>"${disk.clusterName}",<#else>"",</#if>
"sspName":<#if disk.sspName??>"${disk.sspName}",<#else>"",</#if>
"type":"${disk.type}",
"fc":<#if disk.fc??>"${disk.fc}",<#else>"",</#if>
"viosLparProfile":"${disk.vios.viosLparProfile}",
    <#if disk.mountLocal??>"mountLocal":"${disk.mountLocal}",</#if>
    <#if disk.fileSystem??> "fileSystem":"${disk.fileSystem}",</#if>
    <#if disk.lvmParam??>"lvmParam":"${disk.lvmParam}"</#if>
},
</#list>]
}<#elseif vmType == "lpar">{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"mparName":"${name}",
"mparProfile":"${mparProfile}",
"maxVirtualSlots":"${maxVirtualSlots}",
"lparEnv":"${lparEnv}",
"minMem":${minMem},
"desiredMem":${desiredMem},
"maxMem":${maxMem},
"minProcs":${minProcs},
"desiredProcs":${desiredProcs},
"maxProcs":${maxProcs},
"hostName":"${hostName}",
"osName":"${osName}",
"mksysbResource":"${image}",
"adminPass":"${adminPass}",
"veths":
[<#list nics as nic>
    {"hmcIp":"${providerUrl}",
    "hmcUserName":"${authUser}",
    "hmcPassword":"${authPass}",
    "mparName":"${name}",
    "hostName":"${hostName}",
    "mparProfile":"${mparProfile}",
    "ip":"${nic.privateIp}",
    "gateway":"${nic.gateway}",
    "unitPhysLoc":"${nic.unitPhysLoc}"
    }
    </#list>],
"disks":
[<#list disks as disk>
    {
    "size":"${disk.size}",
    "mountLocal":"${disk.mountLocal}",
    "name":"${disk.name}"
    },
    </#list>],
"fcs":
[<#list fcs as fc>
    {
    "hostItemIndex":"${fc.hostItemIndex}"
        <#if fc.hostItemAddr?exists>
        ,"hostItemAddr":"${fc.hostItemAddr}"
        </#if>
    },
    </#list>]
}<#else></#if>

