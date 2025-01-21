<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css" integrity="sha512-10/jx2EXwxxWqCLX/hHth/vu2KY3jCF70dCQB8TSgNjbCVAC/8vai53GfMDrO2Emgwccf2pJqxct9ehpzG+MTw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="/resources/css/admin/hostList.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function(){
	$("#btnSearch").on("click", function(){
		document.hostForm.searchValue.value = $("#searchValue").val();
		document.hostForm.curPage.value = "1";
		document.hostForm.action = "/admin/hostList";
		document.hostForm.submit();
	});
});


function fn_list(curPage)
{
   document.hostForm.curPage.value = curPage;
   document.hostForm.action = "/admin/hostList"
   document.hostForm.submit();
}
</script>
</head>

<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<div class="profile-page">
		<!-- Info Section -->
		<div class="info-section">
			<div class="tabs">
				<div class="tab active">
					<h2 class="space_management_title">호스트 계정 관리</h2>
				</div>
			</div>

			<!-- 정산번호 검색-->
			<div class="box_search">
				<div class="box_inner">
					<div class="one_search">
						<div class="flex_wrap">
							<div class="flex_box">
								<div>
									<h3>사용자 아이디 검색</h3>
								</div>
								<div class="flex">
									<div class="input">
										<input type="text" id="searchValue" placeholder="사용자의 아이디를 입력하세요." value="${searchValue}">
									</div>
								</div>
								<div class="flex">
									<a class="btn btn_default" id="btnSearch"> <i class="fa-solid fa-magnifying-glass"></i> <i class="sp_icon ico_btn_search"></i>검색
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>



			<div class="box_review">
				<div class="box_inner_se">
					<div id="commentList" class="comment-list">

						<div class="review_start">
							<div class="manegementComment">
								<div class="manegementComment-details">
									<div class="manegementComment-content">
										<c:if test='${empty list}'>
											회원(구매자)가 존재하지 않습니다.
										</c:if>
										<c:if test='${!empty list}'>
											<table class="hostTable">
												<thead>
													<tr>
														<th>아이디</th>
														<th>닉네임</th>
														<th>전화번호</th>
														<th>상태</th>
														<th>가입날짜</th>
														<th>수정날짜</th>
														<th>탈퇴날짜</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="host" items="${list}" varStatus="status">
														<tr>
															<td>${host.hostEmail}</td>
															<td>${host.hostNickname}</td>
															<td>${host.hostTel}</td>
															<td class="status-cell">
																<select id="hostStatus" name="hostStatus" class="status" onchange="changeStatus(this, '${host.hostEmail}')" data-prev-value="${host.status}"  ${host.status == 'N' ? 'disabled' : ''}>
																	<option value="Y" ${host.status == 'Y' ? 'selected' : ''}>정상</option>
																	<option value="W" ${host.status == 'W' ? 'selected' : ''}>승인대기</option>
																	<option value="N" ${host.status == 'N' ? 'selected' : ''}>탈퇴</option>
																	<option value="S" ${host.status == 'S' ? 'selected' : ''}>정지</option>
																</select>
															</td>
															<td>${host.regDate}</td>
															<td>${host.modiDate}</td>
															<td>${host.delDate}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<script type="text/javascript">
												function changeStatus(selectElement, hostEmail) {
												    const $select = $(selectElement);
												    const newValue = $select.val(); // 새로 선택된 값
												    const previousValue = $select.data('prev-value') || ""; // 이전 값
		
												    // Confirm 창 띄우기
												    if (confirm("유저의 상태를 변경하시겠습니까?")) {
												        // AJAX 요청 실행
												        updateHostStatus(hostEmail, newValue, $select);
												    } else {
												        // 사용자가 취소하면 기존 값으로 복원
												        $select.val(previousValue);
												    }
												}
		
												function updateHostStatus(hostEmail, newStatus, $select) {
												    $.ajax({
												        type: 'POST',
												        url: '/admin/updateHostStatus',
												        data: {
												            hostEmail: hostEmail,
												            status: newStatus
												        },
												        dataType: "JSON",
												        beforeSend:function(xhr){
															xhr.setRequestHeader("AJAX","true");
														},
												        success: function (response) {
												        	
												        	if(response.code == 1){
												        		alert('유저 상태가 변경되었습니다.');
												                // 현재 값을 이전 값으로 설정
												                $select.data('prev-value', newStatus);
															}
															else{
																alert('상태 변경에 실패했습니다. 다시 시도해주세요.');
												                $select.val($select.data('prev-value')); 
															}
												        },
												        error: function () {
												            alert('서버에 오류가 발생했습니다.');
												            $select.val($select.data('prev-value')); // 값 복원
												        }
												    });
												}
											</script>
										</c:if>
									</div>
								</div>
							</div>
						</div>

					</div>
					<!--commentList 끝-->

				</div>

			</div>

			<div class="paging">
				<c:if test="${!empty paging}">
					<!-- 이전 버튼 -->
					<c:if test="${paging.prevBlockPage gt 0}">
						<a class="bestSlidePrevBtn" href="javascript:void(0)" onclick="fn_list(${paging.prevBlockPage})"></a>
					</c:if>

					<!-- 숫자 버튼 -->
					<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
						<c:choose>
							<c:when test="${i ne curPage}">
								<a class="btnPage" href="javascript:void(0)" onclick="fn_list(${i})">${i}</a>
							</c:when>
							<c:otherwise>
								<a class="btnPage active" href="javascript:void(0)" onclick="fn_list(${i})" style="cursor: default;">${i}</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<!-- 다음 버튼 -->
					<c:if test="${paging.nextBlockPage gt 0}">
						<a class="bestSlideNextBtn" href="javascript:void(0)" onclick="fn_list(${paging.nextBlockPage})"></a>
					</c:if>
				</c:if>
			</div>
		</div>

		<form name="hostForm" id="hostForm">
			<input type="hidden" name="curPage" value="${curPage}" /> <input type="hidden" name="searchValue" value="${searchValue}" />
		</form>

	</div>

</body>
</html>