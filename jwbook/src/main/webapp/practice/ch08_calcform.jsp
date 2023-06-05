<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
</head>
<script>
	function here() {
		var first = document.getElementById("first").value;
		var second = document.getElementById("second").value;
		
		if (!(first && second)) {
			alert("공란이 있습니다.");
			return false;
		}
		else return true;
	}
</script>
<body>
	<h2>계산기 - 컨트롤러</h2>
	<hr>
	<div id = "sendInfo">
	<form method="post" action="/jwbook/ch08/calcController?action=result" onsubmit="return here();">
		<input id="first" name="first"/>
		<select name="op">
			<option>+</option>
			<option>-</option>
			<option>*</option>
			<option>/</option>
		</select>
		<input id="second" name="second"/>
		<button type="submit">실행</button>
	</form>
	</div>
	<div id="result-group">
		계산 결과
		<table>
		<c:forEach var="list" items="${calc}" varStatus="status">
			<tr>
				<td>${list.first}</td>
				<td>${list.op}</td>
				<td>${list.second}</td>
				<td>= ${list.result}</td>
			</tr>
		</c:forEach>
		</table>
	</div>
</body>
</html>