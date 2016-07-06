{
"direction":"${direction}",
<#if portRangMin??>
"port_range_min":"${portRangMin}",<#else></#if>
"ethertype":"${ethertype}",
<#if portRangMin??>
"port_range_max":"${portRangeMax}",<#else></#if>
"protocol":"${protocol}",
"remote_ip_prefix":"${remoteIpPrefix}"
}
