<#if vmType == "lpar">
{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"parName":"${vmName}",
"frameHost":"${hostName}",
    <#if ip?? && ""!=ip>"hostIp":"${ip}",</#if>
    <#if osName?? && ""!=osName>"osName":"${osName}",<#else>"osName":"unknown",</#if>
    <#if disks??>"volumes":
    [<#list disks as disk>
    {
    "wwn":"${volume.wwn}"
    },
    </#list>]
    </#if>
}
<#elseif vmType == "mpar">
{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"mparName":"${vmName}",
"hostName":"${hostName}",
    <#if osName?? && ""!=osName>"osName":"${osName}",<#else>"osName":"",</#if>
    <#if cluster?? && ""!=cluster>"cluster":"${cluster}",</#if>
"mparId":"${id}",
"lvUuid":"${sysDiskUuid}",
"ssp":"${ssp}",
"viosList":
[<#list visoList as vios>
{"viosIp":"${vios.viosIp}",
"viosUser":"${vios.viosUser}",
"viosPwd":"${vios.viosPassword}",
"viosProfile":"${vios.viosLparProfile}",
"viosName":"${vios.viosLparName}"},
</#list>],
    <#if ip?? && ""!=ip>"hostIp":"${ip}",</#if>
    <#if disks??>"volumes":
    [<#list disks as disk>
    {
    "wwn":"${volume.wwn}"
    },
    </#list>]
    </#if>
}
<#else></#if>