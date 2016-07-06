{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"name":"${vmName}",
"nics":
[<#list nics as nic>
{"publicIp":"${nic.publicIp}",
"privateIp":"${nic.privateIp}"
},
</#list>]
}