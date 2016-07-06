{
"admin_pass":"${adminPass}",
"name":"${name}",
"nics":
[<#list nics as nic>
{"ip_address":"${nic.privateIp}",
"network_id":"${nic.netId}"},
</#list>],
"memory":"${memory}",
"image_id":"${image}",
"disk":"${sysDiskSize}",
"cpu":"${cpu}"
<#if (keyName??) & keyName!= "">,"key_name":"${keyName}"<#else></#if>
}

