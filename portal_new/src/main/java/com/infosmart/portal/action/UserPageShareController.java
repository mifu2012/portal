package com.infosmart.portal.action;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.UserPageShareInfo;
import com.infosmart.portal.service.UserPageShareService;
import com.infosmart.portal.util.Constants;
import com.infosmart.portal.util.StringUtils;

/**
 * 用户页面共享
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/userShare")
public class UserPageShareController extends BaseController {
	@Autowired
	UserPageShareService userPageShareService;

	/**
	 * 显示所有接收的共享
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/showReceiveInfo")
	public ModelAndView showReceiveInfo(HttpServletRequest request,
			HttpServletResponse response, UserPageShareInfo userPageShareInfo) {
		ModelAndView mv = new ModelAndView();
		User receiveUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		userPageShareInfo.setReceiveUserId(String.valueOf(receiveUser
				.getUserId()));
		List<UserPageShareInfo> receiveShareInfoList = userPageShareService
				.listPageReceiveInfosByReceiveUserId(userPageShareInfo);
		mv.addObject("receiveShareInfoList", receiveShareInfoList);
		mv.addObject("userPageShareInfo", userPageShareInfo);
		mv.setViewName("userPageShare/receiveShareInfo");
		return mv;
	}

	/**
	 * 显示所有发送的共享
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/showSendInfo")
	public ModelAndView showSendInfo(HttpServletRequest request,
			HttpServletResponse response, UserPageShareInfo userPageShareInfo) {
		ModelAndView mv = new ModelAndView();
		User sendUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		userPageShareInfo.setSendUserId(String.valueOf(sendUser.getUserId()));
		List<UserPageShareInfo> sendShareInfoList = userPageShareService
				.listPageSendInfosBySendUserId(userPageShareInfo);
		mv.addObject("sendShareInfoList", sendShareInfoList);
		mv.addObject("userPageShareInfo", userPageShareInfo);
		mv.setViewName("userPageShare/sendShareInfo");
		return mv;
	}

	/**
	 * 显示要发送共享的用户名单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/showShareUsers")
	public void addSharePage(HttpServletRequest request,
			HttpServletResponse response) {
		User sendUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		List<User> receiveUserList = userPageShareService
				.getAllReceiveUsersBySendUser(sendUser.getUserId());
		this.sendJsonMsgToClient(receiveUserList, response);
	}

	/**
	 * 保存发送
	 * 
	 * @param request
	 * @param response
	 * @param userPageShareInfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSharePage", method = RequestMethod.POST)
	public void saveSharePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User sendUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		String sendShareInfos = request.getParameter("sendUsersValue");
		String shareRemark = request.getParameter("shareRemark");
		if (StringUtils.notNullAndSpace(shareRemark)) {
			shareRemark = new String(shareRemark.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		String url = request.getParameter("url");
		if (!StringUtils.notNullAndSpace(url)) {
			this.logger.info("保存共享页面失败，URL为空");
			this.sendMsgToClient(isFailed, response);
			return;
		}
		url = url.replace("^", "&");
		String productId = this.getCrtProductIdOfReport(request);
		String queryDate = this.getCrtQueryDateOfReport(request);
		String queryMonth = this.getCrtQueryMonthOfReport(request);
		String sendStringId = "";
		String sendStringName = "";
		url = url + "&productId=" + productId + "&queryDate=" + queryDate
				+ "&queryMonth=" + queryMonth;
		if (StringUtils.notNullAndSpace(sendShareInfos)) {
			sendShareInfos = new String(sendShareInfos.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		List<UserPageShareInfo> userPageShareInfoList = new ArrayList<UserPageShareInfo>();
		UserPageShareInfo sendUserPageShaerInfo = new UserPageShareInfo();
		if (sendShareInfos != null && sendShareInfos.length() > 0) {
			String[] sendUsers = sendShareInfos.split(",");
			for (int i = 0; i < sendUsers.length; i++) {
				if (sendUsers[i] == null)
					continue;
				String[] sendUserIdAndName = sendUsers[i].split("-");
				if (i == 0) {
					sendStringId = sendUserIdAndName[0];
					sendStringName = sendUserIdAndName[1];
				} else {
					sendStringId = sendStringId + "," + sendUserIdAndName[0];
					sendStringName = sendStringName + ","
							+ sendUserIdAndName[1];
				}

				UserPageShareInfo userPageShareInfo = new UserPageShareInfo();
				userPageShareInfo.setSendUserId(String.valueOf(sendUser
						.getUserId()));
				userPageShareInfo.setSendUserName(sendUser.getUserName());
				userPageShareInfo.setReceiveUserId(sendUserIdAndName[0]);
				userPageShareInfo.setReceiveUserName(sendUserIdAndName[1]);
				userPageShareInfo.setRemark(shareRemark);
				userPageShareInfo.setUrl(url);
				userPageShareInfoList.add(userPageShareInfo);
			}
		}
		sendUserPageShaerInfo
				.setSendUserId(String.valueOf(sendUser.getUserId()));
		sendUserPageShaerInfo.setSendUserName(sendUser.getUserName());
		sendUserPageShaerInfo.setReceiveUserId(sendStringId);
		sendUserPageShaerInfo.setReceiveUserName(sendStringName);
		sendUserPageShaerInfo.setRemark(shareRemark);
		sendUserPageShaerInfo.setUrl(url);
		sendUserPageShaerInfo.setOriginId(-1);
		try {
			userPageShareService.saveSharePage(sendUserPageShaerInfo,
					userPageShareInfoList);
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}

	}

	/**
	 * 获得未读共享数目
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMessageNum")
	public void getMessageNum(HttpServletRequest request,
			HttpServletResponse response) {
		User receiveUser = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		String num = userPageShareService.getMessageNum(
				String.valueOf(receiveUser.getUserId())).toString();
		this.sendMsgToClient(num, response);

	}

	/**
	 * 更新阅读状态
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updateIsRead")
	public void updateIsRead(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			userPageShareService.updateIsReaded(Integer.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/deleteInfo")
	public void deleteInfo(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			userPageShareService.deleteShareInfoById(Integer.valueOf(id));
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}

	}

	@RequestMapping("/replayShareInfo")
	public ModelAndView replayShareInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		ModelAndView mv = new ModelAndView();
		String originId = request.getParameter("originId");
		String url = request.getParameter("replayUrl");
		url = url.replace("^", "&");
		String sendUserId = request.getParameter("sendUserId");
		String sendUserName = request.getParameter("sendUserName");
		if (StringUtils.notNullAndSpace(sendUserName)) {
			sendUserName = new String(sendUserName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		List<UserPageShareInfo> replayShareList = userPageShareService
				.getAllReplayShareInfo(user.getUserId(),
						Integer.valueOf(originId));
		mv.setViewName("userPageShare/replayShareInfo");
		mv.addObject("replayShareList", replayShareList);
		mv.addObject("sendUserId", sendUserId);
		mv.addObject("url", url);
		mv.addObject("originId", originId);
		mv.addObject("sendUserName", sendUserName);

		return mv;
	}

	/**
	 * 回复保存
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveReplayShare", method = RequestMethod.POST)
	public void saveReplayShare(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(
				Constants.SESSION_USER_INFO);
		String replayUserId = request.getParameter("replayUserId");
		String replayUserName = request.getParameter("replayUserName");
		if (StringUtils.notNullAndSpace(replayUserName)) {
			replayUserName = new String(replayUserName.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		String replayMessage = request.getParameter("replayMessage");
		if (StringUtils.notNullAndSpace(replayUserName)) {
			replayMessage = new String(replayMessage.getBytes("ISO-8859-1"),
					"UTF-8");
		}
		String url = request.getParameter("url");
		if (StringUtils.notNullAndSpace(url)) {
			url = url.replace("^", "&");
		}
		String originId = request.getParameter("originId");
		UserPageShareInfo replayShareInfo = new UserPageShareInfo();
		replayShareInfo.setSendUserId(String.valueOf(user.getUserId()));
		replayShareInfo.setSendUserName(user.getUserName());
		replayShareInfo.setReceiveUserId(replayUserId);
		replayShareInfo.setReceiveUserName(replayUserName);
		replayShareInfo.setUrl(url);
		replayShareInfo.setRemark(replayMessage);
		replayShareInfo.setOriginId(Integer.valueOf(originId));
		try {
			userPageShareService.saveReplayShare(replayShareInfo);
			this.sendMsgToClient(isSuccess, response);
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			this.sendMsgToClient(isFailed, response);
		}
	}

}
