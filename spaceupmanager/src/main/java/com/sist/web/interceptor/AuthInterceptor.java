/**
 * <pre>
 * 프로젝트명 : HBoard
 * 패키지명   : com.icia.web.interceptor
 * 파일명     : AuthInterceptor.java
 * 작성일     : 2021. 1. 19.
 * 작성자     : daekk
 * </pre>
 */
package com.sist.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sist.common.util.StringUtil;
import com.sist.web.model.Admin;
import com.sist.web.model.Response;
import com.sist.web.service.AdminService;
import com.sist.web.util.CookieUtil;
import com.sist.web.util.HttpUtil;
import com.sist.web.util.JsonUtil;

/**
 * <pre>
 * 패키지명   : com.icia.web.interceptor
 * 파일명     : AuthInterceptor.java
 * 작성일     : 2021. 1. 19.
 * 작성자     : daekk
 * 설명       :
 * </pre>
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    private String AUTH_COOKIE_NAME;

    private String AJAX_HEADER_NAME;

    @Autowired
    private AdminService adminService;

    // 인증체크 안해도 되는 url 리스트
    private List<String> authExcludeUrlList;

    /**
     * 생성자
     */
    public AuthInterceptor() {
	this(null, null, null);
    }

    /**
     * 생성자
     * 
     * @param authExcludeUrlList 인증 체크에서 제외될 URL 리스트
     */
    public AuthInterceptor(String authCookieName, String ajaxHeaderName, List<String> authExcludeUrlList) {
	this.AUTH_COOKIE_NAME = authCookieName;
	this.AJAX_HEADER_NAME = ajaxHeaderName;
	this.authExcludeUrlList = authExcludeUrlList;

	if (logger.isDebugEnabled()) {
	    logger.debug("############################################################################");
	    logger.debug("# AuthInterceptor                                                          #");
	    logger.debug("############################################################################");
	    logger.debug("//////////////////////////////////////////////////");
	    logger.debug("// Auth Cookie Name                             //");
	    logger.debug("//////////////////////////////////////////////////");
	    logger.debug("// " + AUTH_COOKIE_NAME);
	    logger.debug("//////////////////////////////////////////////////");
	    logger.debug("//////////////////////////////////////////////////");
	    logger.debug("// Ajax Header Name                             //");
	    logger.debug("//////////////////////////////////////////////////");
	    logger.debug("// " + AJAX_HEADER_NAME);
	    logger.debug("//////////////////////////////////////////////////");

	}

	if (this.authExcludeUrlList != null && this.authExcludeUrlList.size() > 0) {
	    if (logger.isDebugEnabled()) {
		logger.debug("//////////////////////////////////////////////////");
		logger.debug("// Auth Exclude Url                             //");
		logger.debug("//////////////////////////////////////////////////");

		for (int i = 0; i < this.authExcludeUrlList.size(); i++) {
		    logger.debug("// " + StringUtil.nvl(this.authExcludeUrlList.get(i)));
		}

		logger.debug("//////////////////////////////////////////////////");
	    }
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("############################################################################");
	}
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	boolean bFlag = true;
	boolean ajaxFlag = false;

	// 쿠키명 입력
	request.setAttribute("AUTH_COOKIE_NAME", AUTH_COOKIE_NAME);

	String url = request.getRequestURI();

	if (!StringUtil.isEmpty(AJAX_HEADER_NAME)) {
	    ajaxFlag = HttpUtil.isAjax(request, AJAX_HEADER_NAME);
	} else {
	    ajaxFlag = HttpUtil.isAjax(request);
	}

	if (logger.isDebugEnabled()) {
	    request.setAttribute("_http_logger_start_time", String.valueOf(System.currentTimeMillis()));

	    logger.debug("############################################################################");
	    logger.debug("# Logging start [" + url + "]");
	    logger.debug("############################################################################");
	    logger.debug(HttpUtil.requestLogString(request));
	    logger.debug("############################################################################");
	}

	// 인증이 필요 없는 URL도 쿠키 체크
	if (isExcludeUrl(url)) {
	    // 인증 체크 로직 추가 (로그인 필요 없는 URL에 대한 쿠키 체크)
	    if (CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null) {
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

		if (!StringUtil.isEmpty(cookieUserId)) {

		    if (url.equals("/admin/loginForm") || url.equals("/admin/findEmailForm")
			    || url.equals("/admin/showEmailForm") || url.equals("/admin/findPwdForm")
			    || url.equals("/admin/changePwdForm") || url.equals("/admin/regForm")) {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script>" + "alert('로그인 된 상태입니다.');" // 알림 메시지
				+ "window.history.back();" // 이전 페이지로 돌아가기
				+ "</script>");
			return false; // 더 이상 처리하지 않도록 return false
		    }

		    Admin admin = adminService.adminSelect(cookieUserId);

		    if (admin != null) {
			if ("Y".equals(admin.getStatus())) {
			    logger.debug("# Valid Cookie for Exclude URL : [" + cookieUserId + "]");
			} else {
			    handleAuthError(response, ajaxFlag, "유효하지 않은 쿠키입니다.");
			    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			    bFlag = false;
			}
		    } else {
			handleAuthError(response, ajaxFlag, "유효하지 않은 쿠키입니다.");
			CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			bFlag = false;
		    }
		}
	    }
	} else {
	    // 인증 체크 필요 URL에 대한 처리
	    if (logger.isDebugEnabled()) {
		logger.debug("# [" + url + "] : [인증체크] ");
	    }

	    // 인증 체크
	    if (CookieUtil.getCookie(request, AUTH_COOKIE_NAME) != null) {
		String cookieUserId = CookieUtil.getHexValue(request, AUTH_COOKIE_NAME);

		if (!StringUtil.isEmpty(cookieUserId)) {
		    if (logger.isDebugEnabled()) {
			logger.debug("# [Cookie] : [" + cookieUserId + "]");
		    }

		    Admin admin = adminService.adminSelect(cookieUserId);

		    if (admin != null) {
			if ("Y".equals(admin.getStatus())) {
			    bFlag = true;
			} else if ("N".equals(admin.getStatus())) {
			    handleAuthError(response, ajaxFlag, "탈퇴한 계정입니다.");
			    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			    bFlag = false;
			} else if ("S".equals(admin.getStatus())) {
			    handleAuthError(response, ajaxFlag, "정지된 계정입니다.");
			    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			    bFlag = false;
			} else {
			    // 기타 상태 처리
			    handleAuthError(response, ajaxFlag, "유효하지 않은 계정입니다.");
			    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			    bFlag = false;
			}
		    } else {
			handleAuthError(response, ajaxFlag, "등록된 계정이 아닙니다.");
			CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
			bFlag = false;
		    }
		} else {
		    handleAuthError(response, ajaxFlag, "로그인 후 이용이 가능합니다. 로그인 페이지로 이동합니다.");
		    CookieUtil.deleteCookie(request, response, "/", AUTH_COOKIE_NAME);
		    bFlag = false;
		}
	    } else {
		handleAuthError(response, ajaxFlag, "로그인 후 이용이 가능합니다. 로그인 페이지로 이동합니다.");
		bFlag = false;
	    }
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("############################################################################");
	}

	return bFlag;
    }

    /**
     * <pre>
     * 메소드명   : handleAuthError
     * 설명       : 인증 실패 시 에러 처리 로직
     * </pre>
     * 
     * @param response 응답 객체
     * @param ajaxFlag Ajax 요청 여부
     * @param message  사용자에게 표시할 메시지
     */
    private void handleAuthError(HttpServletResponse response, boolean ajaxFlag, String message) throws Exception {
	if (ajaxFlag) {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(JsonUtil.toJson(new Response<Object>(HttpStatus.BAD_REQUEST.value(), message)));
	} else {
	    response.setContentType("text/html; charset=UTF-8");
	    response.getWriter().write("<script>" + "alert('" + message + "');"
		    + "window.location.href='/index';" + "</script>");
	}
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	    throws Exception {
	if (logger.isDebugEnabled()) {
	    long start_time = StringUtil.stringToLong((String) request.getAttribute("_http_logger_start_time"), 0);
	    long end_time = System.currentTimeMillis() - start_time;

	    logger.debug("############################################################################");
	    logger.debug("# Logging end                                                              #");
	    logger.debug("############################################################################");
	    logger.debug("# [request url]          : [" + request.getRequestURI() + "]");
	    logger.debug("# [elapse time (second)] : [" + String.format("%.3f", (end_time / 1000.0f)) + "]");
	    logger.debug("############################################################################");
	}
    }

    /**
     * <pre>
     * 메소드명   : isExcludeUrl
     * 작성일     : 2021. 1. 19.
     * 작성자     : daekk
     * 설명       : 인증하지 않아도 되는 URL 인지 체크 한다.
     *              (true-인증체크 안함, false: 인증체크 해야됨)
     * </pre>
     * 
     * @param url 호출 url
     * @return boolean
     */
    private boolean isExcludeUrl(String url) {

	if (authExcludeUrlList != null && authExcludeUrlList.size() > 0 && !StringUtil.isEmpty(url)) {
	    for (int i = 0; i < authExcludeUrlList.size(); i++) {
		String chkUrl = StringUtil.trim(StringUtil.nvl(authExcludeUrlList.get(i)));

		if (!StringUtil.isEmpty(chkUrl) && chkUrl.length() <= url.length()) {
		    if (url.startsWith(chkUrl)) {
			return true;
		    }
		}
	    }

	    return false;
	}

	return true;
    }
}