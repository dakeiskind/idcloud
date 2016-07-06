{
"providerUrl":"${providerUrl}",
"authUser":"${authUser}",
"authPass":"${authPass}",
"name":"${name}",
"type":"disk",
"size":"${size}",
"dataStore":"${location}",
"vmname":"${vmName}",
<#if virtualDiskType??>"virtualDiskType":${virtualDiskType}<#else></#if>
}
