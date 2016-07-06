<#if migrateType == "HOT">{
"live_migrate":{
"block_migration": true,
"hostname":${targetHostName}
}
}
}<#elseif migrateType == "COLD">{
"migrate":null}
}</#if>