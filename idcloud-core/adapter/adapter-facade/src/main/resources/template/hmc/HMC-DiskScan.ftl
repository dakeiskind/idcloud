{
"hmcIp":"${providerUrl}",
"visoList":
[<#list visoList as vios>
{"viosIp":"${vios.viosIp}",
"viosUser":"${vios.viosUser}",
"viosPwd":"${vios.viosPwd}",
"viosHostName":"${vios.viosHostName}",
"viosId":"${vios.viosId}",
"viosName":"${vios.viosName}"},
</#list>]
}
