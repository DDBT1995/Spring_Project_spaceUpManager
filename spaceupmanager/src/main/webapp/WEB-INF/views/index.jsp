<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/resources/css/admin/index.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">

    <link
    rel="stylesheet"
    href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
    
    <script>
		$(document).ready(function(){
			$("#adminId").focus();
			
			$("#adminId").on("keypress", function(e){
				if(e.which == 13){
					login();
				}
			});
			
			$("#adminPwd").on("keypress", function(e){
				if(e.which == 13){
					login();
				}
			});
		});
		
		function login(){
			if($.trim($("#adminId").val()).length <= 0){
				alert("아이디를 입력하세요.");
				$("#adminId").val("");
				$("#adminId").focus();
				return;
			}
			
			if($.trim($("#adminPwd").val()).length <= 0){
				alert("비밀번호를 입력하세요.");
				$("#adminPwd").val("");
				$("#adminPwd").focus();
				return;
			}
			
			$.ajax({
				type:"POST",
				url:"/admin/loginProc",
				data:{
					adminId: $("#adminId").val(),
					adminPwd: $("#adminPwd").val()
				},
				datatype:"JSON",
				beforeSend:function(xhr){
					xhr.setRequestHeader("AJAX","true");
				},
				success:function(response){
					icia.common.log(response);
					
					if(response.code == 1){
						location.href="/admin/adminMain"
					}
					else{
						alert("실패");
					}
				},
				complete:function(data){
					icia.common.log(data);
				},
				error:function(xhr, status, error){
					icia.common.error(error);
				}
			});
		}
	</script>
    
</head>
<body>
    <div class="login-container">
        <h5><span class="highlight">관리자</span> 로그인</h5>
        <a href="/index">
    		<img src="/resources/images/admin/admin_logo_trans_crop.png" alt="spaceUp 로고">
		</a>
        <form>
            <div class="form-group">
                <label for="email">이메일</label>
                <input type="text" id="adminId" name="adminId" value="" placeholder="이메일" required>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="adminPwd" name="adminPwd" value="" placeholder="비밀번호" required>
            </div>

            <button type="button" id="btnLogin" class="btnLogin" onclick="login()">로그인</button>
        </form>
    </div>

</body>
</html>
