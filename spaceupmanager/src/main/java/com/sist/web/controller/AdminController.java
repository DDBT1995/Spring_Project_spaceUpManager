package com.sist.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sist.common.util.StringUtil;
import com.sist.web.model.Admin;
import com.sist.web.model.Guest;
import com.sist.web.model.Host;
import com.sist.web.model.Paging;
import com.sist.web.model.QuestionReport;
import com.sist.web.model.Response;
import com.sist.web.model.ReviewReport;
import com.sist.web.model.Space;
import com.sist.web.service.AdminService;
import com.sist.web.service.GuestService;
import com.sist.web.service.HostService;
import com.sist.web.service.QuestionReportService;
import com.sist.web.service.ReviewReportService;
import com.sist.web.service.SpaceService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;

@Controller("adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private HostService hostService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ReviewReportService reviewReportService;

    @Autowired
    private QuestionReportService questionReportService;

    @Value("#{env['auth.cookie.name']}")
    private String AUTH_COOKIE_NAME;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private static final int LIST_COUNT = 10;
    private static final int PAGE_COUNT = 5;

    // 로그인
    @RequestMapping(value = "/admin/loginProc", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> loginProc(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	String adminId = HttpUtil.get(request, "adminId");
	String adminPwd = HttpUtil.get(request, "adminPwd");

	// 전달 받은 값 체크
	if (!StringUtil.isEmpty(adminId) && !StringUtil.isEmpty(adminPwd)) {
	    Admin admin = adminService.adminSelect(adminId);

	    // 해당 회원 가입 여부 체크
	    if (admin != null) {

		// 아이디와 비밀번호 일치 여부 체크
		if (StringUtil.equals(admin.getAdminPwd(), adminPwd)) {
		    // 회원 상태 체크
		    if (StringUtil.equals(admin.getStatus(), "Y")) {
			CookieUtil.addCookie(response, "/", -1, AUTH_COOKIE_NAME, CookieUtil.stringToHex(adminId));
			ajaxResponse.setResponse(1, "로그인 성공");
		    } else if (StringUtil.equals(admin.getStatus(), "N")) {
			ajaxResponse.setResponse(0, "탈퇴한 회원");
		    } else if (StringUtil.equals(admin.getStatus(), "S")) {
			ajaxResponse.setResponse(-1, "정지된 회원");
		    }
		} else {
		    ajaxResponse.setResponse(-2, "일치하지 않는 비밀번호");
		}
	    } else {
		ajaxResponse.setResponse(-3, "존재하지 않는 계정");
	    }
	} else {
	    ajaxResponse.setResponse(400, "전달받은 값이 비어있음");
	}
	return ajaxResponse;
    }
    // 로그아웃
    @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

	if (CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null) {
	    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
	}
	return "redirect:/index";
    }

    // 게스트 리스트
    @RequestMapping(value = "/admin/adminMain", method = RequestMethod.GET)
    public String guestList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

	// 검색
	String searchValue = HttpUtil.get(request, "searchValue", "");

	// 현재 페이지
	long curPage = HttpUtil.get(request, "curPage", (long) 1);

	// 총 게스트 수
	long totalCount = 0;

	// 게스트 리스트
	List<Guest> list = null;

	// 페이징 객체
	Paging paging = null;

	// 정산 조회객체
	Guest search = new Guest();

	if (!StringUtil.isEmpty(searchValue)) {
	    search.setSearchValue(searchValue);
	}

	totalCount = guestService.guestCount(search);

	if (totalCount > 0) {
	    paging = new Paging("/admin/adminMain", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");

	    search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());

	    list = guestService.guesttList(search);
	}

	model.addAttribute("list", list);
	model.addAttribute("curPage", curPage);
	model.addAttribute("searchValue", searchValue);
	model.addAttribute("paging", paging);

	return "/admin/adminMain";
    }

    // 게스트 상태 업데이트
    @RequestMapping(value = "/admin/updateGuestStatus", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> updateGuestStatus(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	String guestEmail = HttpUtil.get(request, "guestEmail");
	String status = HttpUtil.get(request, "status");

	if (!StringUtil.isEmpty(guestEmail) && !StringUtil.isEmpty(status)) {
	    Guest guest = new Guest();
	    guest.setGuestEmail(guestEmail);
	    guest.setStatus(status);

	    if (guestService.gueestStatusUpdate(guest) > 0) {
		ajaxResponse.setResponse(1, "업데이트 성공");
	    } else {
		ajaxResponse.setResponse(-1, "업데이트 실패");
	    }
	} else {
	    ajaxResponse.setResponse(400, "값이 비어있습니다.");
	}

	return ajaxResponse;
    }

    // 호스트 리스트
    @RequestMapping(value = "/admin/hostList", method = RequestMethod.GET)
    public String hostList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

	// 검색
	String searchValue = HttpUtil.get(request, "searchValue", "");

	// 현재 페이지
	long curPage = HttpUtil.get(request, "curPage", (long) 1);

	// 총 게스트 수
	long totalCount = 0;

	// 게스트 리스트
	List<Host> list = null;

	// 페이징 객체
	Paging paging = null;

	// 정산 조회객체
	Host search = new Host();

	if (!StringUtil.isEmpty(searchValue)) {
	    search.setSearchValue(searchValue);
	}

	totalCount = hostService.hostCount(search);

	if (totalCount > 0) {
	    paging = new Paging("/admin/hostList", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");

	    search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());

	    list = hostService.hosttList(search);
	}

	model.addAttribute("list", list);
	model.addAttribute("curPage", curPage);
	model.addAttribute("searchValue", searchValue);
	model.addAttribute("paging", paging);

	return "/admin/hostList";
    }

    // 호스트 상태 업데이트
    @RequestMapping(value = "/admin/updateHostStatus", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> updateHostStatus(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	String hostEmail = HttpUtil.get(request, "hostEmail");
	String status = HttpUtil.get(request, "status");

	if (!StringUtil.isEmpty(hostEmail) && !StringUtil.isEmpty(status)) {
	    Host host = new Host();
	    host.setHostEmail(hostEmail);
	    host.setStatus(status);

	    if (hostService.hostStatusUpdate(host) > 0) {
		ajaxResponse.setResponse(1, "업데이트 성공");
	    } else {
		ajaxResponse.setResponse(-1, "업데이트 실패");
	    }
	} else {
	    ajaxResponse.setResponse(400, "값이 비어있습니다.");
	}

	return ajaxResponse;
    }

    // 공간 리스트
    @RequestMapping(value = "/admin/spaceList", method = RequestMethod.GET)
    public String spaceList(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

	// 검색
	String searchValue = HttpUtil.get(request, "searchValue", "");

	// 현재 페이지
	long curPage = HttpUtil.get(request, "curPage", (long) 1);

	// 총 게스트 수
	long totalCount = 0;

	// 게스트 리스트
	List<Space> list = null;

	// 페이징 객체
	Paging paging = null;

	// 정산 조회객체
	Space search = new Space();

	if (!StringUtil.isEmpty(searchValue)) {
	    search.setSearchValue(searchValue);
	}

	totalCount = spaceService.spaceCount(search);

	if (totalCount > 0) {
	    paging = new Paging("/admin/spaceList", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");

	    search.setStartRow(paging.getStartRow());
	    search.setEndRow(paging.getEndRow());

	    list = spaceService.spacetList(search);
	}

	model.addAttribute("list", list);
	model.addAttribute("curPage", curPage);
	model.addAttribute("searchValue", searchValue);
	model.addAttribute("paging", paging);

	return "/admin/spaceList";
    }

    // 공간 상태 업데이트
    @RequestMapping(value = "/admin/updateSpaceStatus", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> updateSpaceStatus(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	long spaceId = HttpUtil.get(request, "spaceId", (long) -1);
	String status = HttpUtil.get(request, "status");

	if (spaceId != -1 && !StringUtil.isEmpty(status)) {
	    Space space = new Space();
	    space.setSpaceId(spaceId);
	    space.setStatus(status);

	    if (spaceService.spaceStatusUpdate(space) > 0) {
		ajaxResponse.setResponse(1, "업데이트 성공");
	    } else {
		ajaxResponse.setResponse(-1, "업데이트 실패");
	    }
	} else {
	    ajaxResponse.setResponse(400, "값이 비어있습니다.");
	}

	return ajaxResponse;
    }

    @RequestMapping(value = "/admin/reportList", method = RequestMethod.GET)
    public String reportList(ModelMap model, HttpServletRequest request) {

	// 신고 종류(리뷰 or QnA)
	int reportType = HttpUtil.get(request, "reportType", 1);

	// 현재 페이지
	long curPage = HttpUtil.get(request, "curPage", (long) 1);

	// 총 리스트 수
	long totalCount = 0;

	// 페이징 객체
	Paging paging = null;

	// 리스트 공통 변수
	List<?> list = null;

	// 조건에 따라 처리
	if (reportType == 1) {
	    // 리뷰 신고 리스트 처리
	    ReviewReport search = new ReviewReport();
	    totalCount = reviewReportService.reviewReportCount(search);

	    if (totalCount > 0) {
		paging = new Paging("/admin/reportList", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
		search.setStartRow(paging.getStartRow());
		search.setEndRow(paging.getEndRow());
		list = reviewReportService.reviewReportList(search);
	    }

	} else if (reportType == 2) {
	    // QnA 신고 리스트 처리
	    QuestionReport search = new QuestionReport();
	    totalCount = questionReportService.questionReportCount(search);

	    if (totalCount > 0) {
		paging = new Paging("/admin/reportList", totalCount, LIST_COUNT, PAGE_COUNT, curPage, "curPage");
		search.setStartRow(paging.getStartRow());
		search.setEndRow(paging.getEndRow());
		list = questionReportService.questionReportList(search);
	    }
	}

	// 공통 모델 속성 추가
	model.addAttribute("list", list);
	model.addAttribute("curPage", curPage);
	model.addAttribute("paging", paging);
	model.addAttribute("reportType", reportType);

	return "/admin/reportList";
    }

    // 리뷰 신고 처리 업데이트
    @RequestMapping(value = "/admin/reviewReportHandling", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> reviewReportHandling(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	long reviewReportId = HttpUtil.get(request, "reviewReportId", (long) -1);
	long reviewId = HttpUtil.get(request, "reviewId", (long) -1);
	String reviewWriter = HttpUtil.get(request, "reviewWriter");
	int selectedVal = HttpUtil.get(request, "selectedVal", -1);

	if (reviewId != -1 && selectedVal != -1 && !StringUtil.isEmpty(reviewWriter)) {

	    // 리뷰 삭제(리뷰 삭제)
	    if (selectedVal == 1) {
		if (reviewReportService.reviewStatusUpdateN(reviewId, reviewReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    }
	    // 회원 정지(작성자 정지)
	    else if (selectedVal == 2) {
		if (reviewReportService.reviewWriterStatusUpdateS(reviewWriter, reviewReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    }
	    // 반려(신고 처리 상태 값 Y로 업데이트)
	    else if (selectedVal == 3) {
		if (reviewReportService.reviewReportStatusUpdateY(reviewReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    } else {
		ajaxResponse.setResponse(400, "잘못된 값이 선택되었습니다.");
	    }

	} else {
	    ajaxResponse.setResponse(400, "값이 비어있습니다.");
	}

	return ajaxResponse;
    }
    
    // 문의 신고 처리 업데이트
    @RequestMapping(value = "/admin/questionReportHandling", method = RequestMethod.POST)
    @ResponseBody
    public Response<Object> questionReportHandling(HttpServletRequest request, HttpServletResponse response) {
	Response<Object> ajaxResponse = new Response<Object>();

	long questionReportId = HttpUtil.get(request, "questionReportId", (long) -1);
	long spaceQuestionId = HttpUtil.get(request, "spaceQuestionId", (long) -1);
	String questionWriter = HttpUtil.get(request, "questionWriter");
	int selectedVal = HttpUtil.get(request, "selectedVal", -1);

	if (spaceQuestionId != -1 && selectedVal != -1 && !StringUtil.isEmpty(questionWriter)) {

	    // 리뷰 삭제(리뷰 삭제)
	    if (selectedVal == 1) {
		if (questionReportService.questionStatusUpdateN(spaceQuestionId, questionReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    }
	    // 회원 정지(작성자 정지)
	    else if (selectedVal == 2) {
		if (questionReportService.questionWriterStatusUpdateS(questionWriter, questionReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    }
	    // 반려(신고 처리 상태 값 Y로 업데이트)
	    else if (selectedVal == 3) {
		if (questionReportService.questionReportStatusUpdateY(questionReportId) > 0) {
		    ajaxResponse.setResponse(1, "신고 처리 완료");
		} else {
		    ajaxResponse.setResponse(0, "신고 처리 실패");
		}
	    } else {
		ajaxResponse.setResponse(400, "잘못된 값이 선택되었습니다.");
	    }

	} else {
	    ajaxResponse.setResponse(400, "값이 비어있습니다.");
	}

	return ajaxResponse;
    }

}
