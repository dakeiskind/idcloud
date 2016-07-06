{
"hmcIp":"${providerUrl}",
"hmcUserName":"${authUser}",
"hmcPassword":"${authPass}",
"visoList":
[<#list viosList as vios>
{"viosIp":"${vios.viosIp}",
"viosUser":"${vios.viosUser}",
"viosPwd":"${vios.viosPwd}",
"viosHostName":"${vios.viosHostName}",
"viosId":"${vios.viosId}",
"viosName":"${vios.viosName}"},
</#list>],
"hostNames":
[<#list hosts as host>
"${host}",
</#list>]
}
