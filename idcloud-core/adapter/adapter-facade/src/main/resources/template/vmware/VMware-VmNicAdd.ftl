{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"name":"${vmName}",
"osCategory":"${osType}",
"osCategoryDetail":"${osTypeDetail}",
"nics":
[<#list nics as nic>
{"publicIp":"${nic.publicIp}",
"privateIp":"${nic.privateIp}",
"dns":"${nic.dns}",
"netmask":"${nic.netmask}",
"gateway":"${nic.gateway}",
"port":"${nic.port}"},
</#list>]

}