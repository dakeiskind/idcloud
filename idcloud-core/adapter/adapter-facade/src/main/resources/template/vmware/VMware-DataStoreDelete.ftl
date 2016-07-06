{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"clusterName":"${clusterName}",
<#if hostName??>"hostName":"${hostName}",<#else></#if>
"dsName":"${datastoreName}",
"volumes":
[<#list volumes as volume>
{"wwn":"${volume.wwn}"
},
</#list>]
}