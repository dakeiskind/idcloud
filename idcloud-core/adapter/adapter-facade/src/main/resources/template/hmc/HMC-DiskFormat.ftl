{
"serverIP":"${targetSerIp}",
"serverUser":"${targetUser}",
"serverPwd":"${targetPwd}",
"disks":
[<#list disks as disk>
{"deviceTagert":"${disk.deviceTagert}",
"mountLocal":"${disk.mountLocal}",
"fileSystem":"${disk.fileSystem}",
"lvmParam":"${disk.lvmParam}"
},
</#list>]
}
