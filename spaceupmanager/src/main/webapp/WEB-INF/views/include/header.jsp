<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/resources/css/header.css">


<!-- 폰트 적용 -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
</head>

<%
	if(com.sist.web.util.CookieUtil.getCookie(request, (String) request.getAttribute("AUTH_COOKIE_NAME")) != null)
	{ //로그인 했을 경우
%>
<body>
	<header class="header main" style="top: 0px;">
		<div class="host-headerBox">
			<div class="header-left">

				<div class="headerLeftLogoBox">
					<i> <a href="/admin/adminMain" class="headerLeftLogoLink"> <img src="/resources/images/admin/admin_logo_trans_crop.png" alt="로고 이미지">
					</a>
					</i>
				</div>
			</div>

			<nav id="navmenu" class="navmenu">
				<ul>
					<li><a href="/admin/adminMain">게스트 계정 관리</a></li>
					<li><a href="/admin/hostList">호스트 계정 관리</a></li>
					<li><a href="/admin/spaceList">공간 관리</a></li>
					<li><a href="/admin/reportList">신고관리</a></li>
				</ul>
				<i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
			</nav>

			<script>
              // 모든 a 태그를 선택
	            const navLinks = document.querySelectorAll('#navmenu a');
	
	            // 페이지 로드 시 현재 URL과 href가 일치하는 메뉴에 active 클래스 추가
	            const currentPath = window.location.pathname;
	            navLinks.forEach(link => {
	              if (link.getAttribute('href') === currentPath) {
	                link.classList.add('active');
	              }
	            });
	
	            // 각 a 태그에 클릭 이벤트 추가
	            navLinks.forEach(link => {
	              link.addEventListener('click', function (e) {
	                // 기존에 active 클래스가 있는 링크에서 제거
	                navLinks.forEach(nav => nav.classList.remove('active'));
	
	                // 클릭된 링크에 active 클래스 추가
	                this.classList.add('active');
	              });
	            });
          </script>
			<div class="header-center"></div>

			<div class="header-right">
				<a href="/admin/logout"><i class="guestHomeBtn">로그아웃</i></a>
			</div>
	</header>

	<%
   } else { // 로그인 안 했을 경우
%>

<body>
	<header class="header main" style="top: 0px;">
		<div class="host-headerBox">
			<div class="header-left">

				<div class="headerLeftLogoBox">
					<i> <a href="/admin/adminMain" class="headerLeftLogoLink"> <img src="/resources/images/admin/admin_logo_trans_crop.png" alt="로고 이미지">
					</a>
					</i>
				</div>
			</div>

			<nav id="navmenu" class="navmenu">
				<ul>
					<li><a href="/admin/adminMain">게스트 계정 관리</a></li>
					<li><a href="/admin/hostList">호스트 계정 관리</a></li>
					<li><a href="/admin/spaceList">공간 관리</a></li>
					<li><a href="/admin/reportList">신고관리</a></li>
				</ul>
				<i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
			</nav>

			<script>
              // 모든 a 태그를 선택
	            const navLinks = document.querySelectorAll('#navmenu a');
	
	            // 페이지 로드 시 현재 URL과 href가 일치하는 메뉴에 active 클래스 추가
	            const currentPath = window.location.pathname;
	            navLinks.forEach(link => {
	              if (link.getAttribute('href') === currentPath) {
	                link.classList.add('active');
	              }
	            });
	
	            // 각 a 태그에 클릭 이벤트 추가
	            navLinks.forEach(link => {
	              link.addEventListener('click', function (e) {
	                // 기존에 active 클래스가 있는 링크에서 제거
	                navLinks.forEach(nav => nav.classList.remove('active'));
	
	                // 클릭된 링크에 active 클래스 추가
	                this.classList.add('active');
	              });
	            });
          </script>
			<div class="header-center"></div>

			<div class="header-right">
				&emsp; <a href="http://spaceupmanager.sist.co.kr:8088"><i class="guestHomeBtn">로그인</i></a>
			</div>
	</header>

	<%
   }
%>
	<script type="text/javascript">
	      $(document).ready(function () {
	    	    // 이미지 클릭 시 드롭다운 토글
	    	    $(".headerRightUserImg").on("click", function (e) {
	    	        e.stopPropagation(); // 이벤트 버블링 방지
	    	        $(".headerUserMenu").toggle(); // 드롭다운 메뉴 토글
	    	    });
	
	    	    // 페이지 다른 곳 클릭 시 드롭다운 닫기
	    	    $(document).on("click", function () {
	    	        $(".headerUserMenu").hide(); // 드롭다운 메뉴 숨기기
	    	    });
	    	});

      </script>

</body>