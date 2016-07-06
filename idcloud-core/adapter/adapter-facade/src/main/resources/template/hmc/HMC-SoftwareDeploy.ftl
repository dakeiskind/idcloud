{
"targetSrvIp":"${targetSrvIp}",
"targetUsrName":"${targetUsrName}",
"targetUsrPasswd":"${targetUsrPasswd}",
"osType":"${osType}",
"osVersion":"${osVersion}",
"softwares":
[<#list softwares as software>
{
"name":"${software.type}",
"version":"${software.version}",
"mediaPath":"${software.mediaPath}",
"scriptPath":"${software.scriptPath}",
"type":"${software.type}",
    <#if software.softWareConfig??> "softWareConfig":{<#list software.softWareConfig?keys as softwareKey>
    "${softwareKey}":"${software.softWareConfig[softwareKey]}",
    </#list>}<#else></#if>
},
</#list>]
}
