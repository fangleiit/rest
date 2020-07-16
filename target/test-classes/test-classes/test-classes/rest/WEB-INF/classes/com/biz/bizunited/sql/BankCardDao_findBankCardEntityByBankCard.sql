select * from BANK_CARD where 1 = 1
<#if bankCard.mobile ?exists>
	and mobile = :bankCard.mobile
</#if>
<#if bankCard.name ?exists>
	and name = :bankCard.name
</#if>
<#if bankCard.status ?exists>
	and name = :bankCard.status
</#if>
<#if bankCard.id ?exists>
	and id = :bankCard.id
</#if>