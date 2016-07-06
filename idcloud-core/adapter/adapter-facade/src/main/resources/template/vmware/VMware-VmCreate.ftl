{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"name":"${name}",
"cpu":"${cpu}",
"memory":"${memory}",
"image":"${image}",
"adminName":"${adminName}",
"adminPass":"${adminPass}",
"hostName":"${hostName}",
"osCategory":"${osCategory}",
<#if (osName??) & osName!= "">"osName":"${osName}",<#else></#if>
"osCategoryDetail":"${osCategoryDetail}",
"sysDiskLocation":"${sysDiskLocation}",
"vncPort":"${vncPort}",
"dataDisks":
[<#list disks as disk>
{"name":"${disk.name}",
"size":"${disk.size}",
"location":"${disk.location}"},
</#list>],
"nics":
[<#list nics as nic>
{
"privateIp":"${nic.privateIp}",
"dns":"${nic.dns}",
"netmask":"${nic.netmask}",
"gateway":"${nic.gateway}",
"port":"${nic.port}",
"primary":"${nic.primary?c}"},
</#list>]
}

