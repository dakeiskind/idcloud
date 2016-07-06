{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"hostName":"${hostName}",
"mparName":"${vmName}",
"parProfile":"${parProfile}",
"parId":"${parId}",
"parType":"${parType}",
"minMem":${minMem?c},
"mem":${memory},
"maxMem":${maxMem?c},
"minProcs":"${minProcs}",
"cpu":${cpu},
"maxProcs":${maxProcs},
<#if hostIp??>"hostIp":"${hostIp}",</#if>
<#if password??>"password":"${password}",</#if>
<#if userName??>"userName":"${userName}",</#if>
<#if minProcUnits??>"minProcUnits":${minProcUnits},<#else>"minProcUnits":0.0,</#if>
<#if desiredProcUnits??>"desiredProcUnits":${desiredProcUnits},<#else>"desiredProcUnits":0.0,</#if>
<#if maxProcUnits??>"maxProcUnits":${maxProcUnits},<#else>"maxProcUnits":0.0,</#if>
"volumes":
[<#list disks as disk>
{<#if disk.operate == "add">
    "operation":"${disk.operate}",
    "size":"${disk.size}",
    "mountLocal":"${disk.mountLocal}",
    "fileSystem":"${disk.fileSystem}",
    "lvmParam":"${disk.lvmParam}"
    <#elseif disk.operate == "delete">
    "operation":"${disk.operate}",
    "volumes":
    [<#list disk.volumes as volume>
    {"wwn":"${volume.wwn}"},
    </#list>]
    </#if>
},
</#list>]
}

