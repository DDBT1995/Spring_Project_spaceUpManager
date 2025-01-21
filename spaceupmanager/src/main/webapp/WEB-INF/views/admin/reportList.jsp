<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.0/css/all.min.css" integrity="sha512-10/jx2EXwxxWqCLX/hHth/vu2KY3jCF70dCQB8TSgNjbCVAC/8vai53GfMDrO2Emgwccf2pJqxct9ehpzG+MTw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="/resources/css/admin/reportList.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function(){
	$("#btnSearch").on("click", function(){
		document.reportForm.searchValue.value = $("#searchValue").val();
		document.reportForm.curPage.value = "1";
		document.reportForm.action = "/admin/reportList";
		document.reportForm.submit();
	});
});


function fn_list(curPage)
{
   document.reportForm.curPage.value = curPage;
   document.reportForm.action = "/admin/reportList"
   document.reportForm.submit();
}

function changeReportType(reportType){
	document.reportForm.curPage.value = ""
	document.reportForm.reportType.value = reportType;
	document.reportForm.action = "/admin/reportList"
	document.reportForm.submit();
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
					<h2 class="space_management_title">신고 관리</h2>
				</div>
			</div>

			<div class="tab-container">
				<!-- 리뷰 신고 관리 버튼 -->
				<button class="${reportType == 1 ? 'tab-button active' : 'tab-button'}" id="reviewReportButton" style="border-top-left-radius: 10px;" onclick="changeReportType(1)">리뷰 신고 관리</button>

				<!-- 문의 신고 관리 버튼 -->
				<button class="${reportType == 2 ? 'tab-button active' : 'tab-button'}" id="questionReportButton" style="border-top-right-radius: 10px;" onclick="changeReportType(2)">문의 신고 관리</button>
			</div>




			<div class="box_review">
				<div class="box_inner_se">
					<div id="commentList" class="comment-list">

						<div class="review_start">
							<div class="manegementComment">
								<div class="manegementComment-details">
									<div class="manegementComment-content">
										<c:if test='${empty list}'>
											접수된 신고가 존재하지 않습니다.
										</c:if>
										<c:if test='${!empty list}'>
											<c:choose>
												<c:when test="${reportType==1}">
													<table class="reviewReportTable">
														<thead>
															<tr>
																<th>신고자</th>
																<th>신고자종류</th>
																<th>리뷰내용</th>
																<th>신고내용</th>
																<th>신고날짜</th>
																<th>처리여부</th>
																<th>처리</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="report" items="${list}" varStatus="status">
																<tr>
																	<td>${report.reporterId}</td>
																	<td>
																		<c:choose>
																			<c:when test="${report.reporterType == 'G'}">게스트</c:when>
																			<c:when test="${report.reporterType == 'H'}">호스트</c:when>
																			<c:otherwise>알 수 없음</c:otherwise>
																		</c:choose>
																	</td>
																	<td>${report.reviewContent}</td>
																	<td>${report.reportReason}</td>
																	<td>${report.reportDate}</td>
																	<td>
																		<c:choose>
																			<c:when test="${report.status == 'Y'}">
																				<span style="color: #3395E5; font-weight: bold;">처리완료</span>
																			</c:when>
																			<c:when test="${report.status == 'W'}">
																				<span style="color: #E53339; font-weight: bold;">처리대기중</span>
																			</c:when>
																			<c:otherwise>알 수 없음</c:otherwise>
																		</c:choose>
																	</td>
																	<td class="report-handling">
																		<select id="reportHandling" name="reportHandling" class="status" onchange="reportHandlingConfirm('${report.reviewReportId}', '${report.reviewWriter}', '${report.reviewId}', this)" data-prev-value="" style="display: ${report.status == 'W' ? 'block' : 'none'};">
																			<option value="">신고처리  ▼</option>
																			<option value="1">리뷰삭제  ▼</option>
																			<option value="2">회원정지  ▼</option>
																			<option value="3">신고반려  ▼</option>
																		</select>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<script type="text/javascript">
														function reportHandlingConfirm(reviewReportId, reviewWriter, reviewId, selectElement) {
															const $select = $(selectElement);
														    const newValue = $select.val(); // 새로 선택된 값
														    const previousValue = $select.data('prev-value') || ""; // 이전 값
														    
														    // Confirm 창 띄우기
														    if (confirm("해당 신고를 처리 하시겠습니까?")) {
														        // AJAX 요청 실행
														        reportHandling(reviewReportId, reviewWriter, reviewId, newValue);
														    } else {
														        // 사용자가 취소하면 기존 값으로 복원
														        $("#reportHandling").val(previousValue);
														    }
														}
				
														function reportHandling(reviewReportId, reviewWriter, reviewId, newValue) {
														    $.ajax({
														        type: 'POST',
														        url: '/admin/reviewReportHandling',
														        data: {
														        	reviewReportId: reviewReportId,
														        	reviewWriter: reviewWriter,
														        	reviewId: reviewId,
														        	selectedVal: newValue
														        },
														        dataType: "JSON",
														        beforeSend:function(xhr){
																	xhr.setRequestHeader("AJAX","true");
																},
														        success: function (response) {
														        	console.log(response);
														        	if(response.code == 1){
														        		alert('신고처리가 완료되었습니다.');
														                location.reload(); // 페이지 새로고침
																	}
																	else{
																		alert('신고 처리에 실패했습니다. 다시 시도해주세요.');
														                location.reload(); // 페이지 새로고침
														                
																	}
														        },
														        error: function () {
														            alert('서버에 오류가 발생했습니다.');
														            $select.val($select.data('prev-value')); // 값 복원
														        }
														    });
														}
													</script>
												</c:when>


												<c:when test="${reportType==2}">
													<table class="reviewReportTable">
														<thead>
															<tr>
																<th>신고자</th>
																<th>문의내용</th>
																<th>신고내용</th>
																<th>신고날짜</th>
																<th>처리여부</th>
																<th>처리</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="question" items="${list}" varStatus="status">
																<tr>
																	<td>${question.hostEmail}</td>
																	<td>${question.questionContent}</td>
																	<td>${question.reportReason}</td>
																	<td>${question.reportDate}</td>
																	<td>
																		<c:choose>
																			<c:when test="${question.status == 'Y'}">
																				<span style="color: #3395E5; font-weight: bold;">처리완료</span>
																			</c:when>
																			<c:when test="${question.status == 'W'}">
																				<span style="color: #E53339; font-weight: bold;">처리대기중</span>
																			</c:when>
																			<c:otherwise>알 수 없음</c:otherwise>
																		</c:choose>
																	</td>
																	<td class="report-handling">
																		<select id="reportHandling" name="reportHandling" class="status" onchange="reportHandlingConfirm('${question.questionReportId}', '${question.questionWriter}', '${question.spaceQuestionId}', this)" data-prev-value="" style="display: ${question.status == 'W' ? 'block' : 'none'};">
																			<option value="">신고처리  ▼</option>
																			<option value="1">문의삭제  ▼</option>
																			<option value="2">회원정지  ▼</option>
																			<option value="3">신고반려  ▼</option>
																		</select>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<script type="text/javascript">
												function reportHandlingConfirm(questionReportId, questionWriter, spaceQuestionId, selectElement) {
													const $select = $(selectElement);
												    const newValue = $select.val(); // 새로 선택된 값
												    const previousValue = $select.data('prev-value') || ""; // 이전 값
												    
												    // Confirm 창 띄우기
												    if (confirm("해당 신고를 처리 하시겠습니까?")) {
												        // AJAX 요청 실행
												        reportHandling(questionReportId, questionWriter, spaceQuestionId, newValue);
												    } else {
												        // 사용자가 취소하면 기존 값으로 복원
												        $("#reportHandling").val(previousValue);
												    }
												}
		
												function reportHandling(questionReportId, questionWriter, spaceQuestionId, newValue) {
												    $.ajax({
												        type: 'POST',
												        url: '/admin/questionReportHandling',
												        data: {
												        	questionReportId: questionReportId,
												        	questionWriter: questionWriter,
												        	spaceQuestionId: spaceQuestionId,
												        	selectedVal: newValue
												        },
												        dataType: "JSON",
												        beforeSend:function(xhr){
															xhr.setRequestHeader("AJAX","true");
														},
												        success: function (response) {
												        	console.log(response);
												        	if(response.code == 1){
												        		alert('신고처리가 완료되었습니다.');
												                location.reload(); // 페이지 새로고침
															}
															else{
																alert('신고 처리에 실패했습니다. 다시 시도해주세요.');
												                location.reload(); // 페이지 새로고침
												                
															}
												        },
												        error: function () {
												            alert('서버에 오류가 발생했습니다.');
												            $select.val($select.data('prev-value')); // 값 복원
												        }
												    });
												}
											</script>
												</c:when>
											</c:choose>
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

		<form name="reportForm" id="reportForm">
			<input type="hidden" name="curPage" value="${curPage}" /> <input type="hidden" name="reportType" value="${reportType}" />
		</form>

	</div>

</body>
</html>