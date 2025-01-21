<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css" integrity="sha512-10/jx2EXwxxWqCLX/hHth/vu2KY3jCF70dCQB8TSgNjbCVAC/8vai53GfMDrO2Emgwccf2pJqxct9ehpzG+MTw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="/resources/css/admin/spaceList.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function(){
	$("#btnSearch").on("click", function(){
		document.spaceForm.searchValue.value = $("#searchValue").val();
		document.spaceForm.curPage.value = "1";
		document.spaceForm.action = "/admin/spaceList";
		document.spaceForm.submit();
	});
});


function fn_list(curPage)
{
   document.spaceForm.curPage.value = curPage;
   document.spaceForm.action = "/admin/spaceList"
   document.spaceForm.submit();
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
					<h2 class="space_management_title">공간 관리</h2>
				</div>
			</div>

			<!-- 정산번호 검색-->
			<div class="box_search">
				<div class="box_inner">
					<div class="one_search">
						<div class="flex_wrap">
							<div class="flex_box">
								<div>
									<h3>공간 번호 검색</h3>
								</div>
								<div class="flex">
									<div class="input">
										<input type="text" id="searchValue" placeholder="공간 번호를 검색하세요." value="${searchValue}">
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
											등록된 공간이 존재하지 않습니다.
										</c:if>
										<c:if test='${!empty list}'>
											<table class="spaceTable">
												<thead>
													<tr>
														<th>공간번호</th>
														<th>공간이름</th>
														<th>공간유형</th>
														<th>호스트</th>
														<th>상태</th>
														<th>가입날짜</th>
														<th>수정날짜</th>
														<th>탈퇴날짜</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="space" items="${list}" varStatus="status">
														<tr>
															<td>${space.spaceId}</td>
															<td>${space.spaceName}</td>
															<td>${space.spaceType}</td>
															<td>${space.hostEmail}</td>
															<td class="status-cell">
																<select id="spaceStatus" name="spaceStatus" class="status" onchange="changeStatus(this, '${space.spaceId}')" data-prev-value="${space.status}">
																	<option value="Y" ${space.status == 'Y' ? 'selected' : ''}>정상</option>
																	<option value="N" ${space.status == 'N' ? 'selected' : ''}>삭제</option>
																	<option value="D" ${space.status == 'D' ? 'selected' : ''}>비활성화</option>
																</select>
															</td>
															<td>${space.regDate}</td>
															<td>${space.modiDate}</td>
															<td>${space.delDate}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<script type="text/javascript">
												function changeStatus(selectElement, spaceId) {
												    const $select = $(selectElement);
												    const newValue = $select.val(); // 새로 선택된 값
												    const previousValue = $select.data('prev-value') || ""; // 이전 값
		
												    // Confirm 창 띄우기
												    if (confirm("공간의 상태를 변경하시겠습니까?")) {
												        // AJAX 요청 실행
												        updateSpaceStatus(spaceId, newValue, $select);
												    } else {
												        // 사용자가 취소하면 기존 값으로 복원
												        $select.val(previousValue);
												    }
												}
		
												function updateSpaceStatus(spaceId, newStatus, $select) {
												    $.ajax({
												        type: 'POST',
												        url: '/admin/updateSpaceStatus',
												        data: {
												            spaceId: spaceId,
												            status: newStatus
												        },
												        dataType: "JSON",
												        beforeSend:function(xhr){
															xhr.setRequestHeader("AJAX","true");
														},
												        success: function (response) {
												        	
												        	if(response.code == 1){
												        		alert('공간의 상태가 변경되었습니다.');
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

		<form name="spaceForm" id="spaceForm">
			<input type="hidden" name="curPage" value="${curPage}" /> <input type="hidden" name="searchValue" value="${searchValue}" />
		</form>

	</div>

</body>
</html>