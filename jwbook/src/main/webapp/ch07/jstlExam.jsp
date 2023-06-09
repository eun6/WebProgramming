<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>종합 예제</title>
</head>
<body>
	<h2>JSTL 종합 예제</h2>
	<!--  set, out -->
	<h3>set, out</h3>
	<c:set var="product1" value="<h2>애플 아이폰 </h2>" />
	<c:set var="product2" value="삼성 갤럭시 노트" />
	<c:set var="intArray" value="${[1, 2, 3, 4, 5]}" />
	<c:set var="charArray" value="${['h', 't', 'i', 'l']}" />
	<p>
		product1(jstl):
		<c:out value="${product1}" default = "Not registered" escapeXml = "true"/>
	</p>
	<p>product1(el): ${product1}</p>
	<p>intArray[2]: ${intArray[2]}</p>
	<p>charArray[2]: ${charArray[2]}</p>
	
	<hr>
	
	<!-- forEach -->
	<h3>forEach: 배열 출력</h3>
	<ul>
		<c:forEach var="num" varStatus="i" items="${intArray}">
			<li>${i.index} : ${num}</li>
		</c:forEach>
		<br>
		<c:forEach var="word" varStatus="i" items="${charArray}" begin="1" end="2">
			<li>${i.index} : ${word} - 카운트 값 : ${i.count}</li>
		</c:forEach>
	</ul>
	
	<!-- if -->
	<h3>if</h3>
	<c:set var="checkout" value="true"/>
		<c:if test="${checkout}">
			<p>주문 제품: ${product2}</p>
		</c:if>
		<c:if test="${!checkout}">
			<p>주문 제품 아님.</p>
		</c:if>
		<c:if test="${!empty product2}">
			<p>${product2} 이미 추가됨.</p>
		</c:if>
		
		<!-- test코드 | true로 출력 됨(참인 경우에만 내용이 출력 됨). -->
		<c:if test="${result != checkout}" var="result">
			${result}
		</c:if>
	<!-- choose, when, otherwise -->
	<h3>choose, when, otherwise</h3>
		<c:choose>
			<c:when test="${checkout}">
				<p>주문 제품: ${product2}</p>
			</c:when>
			<c:otherwise>
				<p>주문 제품이 아님!</p>
			</c:otherwise>
		</c:choose>
		
		<!-- forTokens -->
		<h3>forTokens</h3>
		<c:forTokens var="city" items="Seoul | Tokyo | New york | Toronto" delims="|"
			varStatus="i">
			<c:if test="${i.first}">도시 목록 : </c:if> ${city}
			<c:if test="${!i.last}">, </c:if>		
		</c:forTokens>
		
		<%-- varStatus 속성
		* status.first : 현재가 첫번째 루프이면 참입니다.
		* status.last : 현재가 마지막 루프이면 참입니다.
		--%>
</body>
</html>