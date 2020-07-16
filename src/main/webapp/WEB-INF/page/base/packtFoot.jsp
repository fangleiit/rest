<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="packet-footer">
	<a id="ruleDescription" class="mui-pull-left">活动规则 》</a> <a id="record"
		class="mui-pull-right">中奖记录 》</a>
</div>
<script type="text/javascript">
	//规则说明
	$("#ruleDescription").click(function(){
		window.location.href = "${base}/rules/ruleDescription.do";
	});
	//中奖纪录
	$("#record").click(function(){
		window.location.href = "${base}/home/record.do";
	});
</script>