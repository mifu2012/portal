package com.infosmart.portal.service;

import java.util.List;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.UserPageShareInfo;

public interface UserPageShareService {

	/**
	 * 根据用户ID查询所有其他用户
	 * 
	 * @param userId
	 * @return
	 */
	List<User> getAllReceiveUsersBySendUser(Integer userId);

	/**
	 * 插入发送信息
	 * 
	 * @param userPageShareInfo
	 */
	void insertSendInfo(UserPageShareInfo userPageShareInfo) throws Exception;

	void insertReceiveInfo(List<UserPageShareInfo> userPageShareInfoList);

	/**
	 * 修改已读状态
	 * 
	 * @param Id
	 */
	void updateIsReaded(Integer Id);

	/**
	 * 获得该用户发送的共享记录
	 * 
	 * @param userPageShareInfo
	 * @return
	 */
	List<UserPageShareInfo> listPageSendInfosBySendUserId(
			UserPageShareInfo userPageShareInfo);

	/**
	 * 获得别人共享给你的共享信息
	 * 
	 * @param userPageShareInfo
	 * @return
	 */
	List<UserPageShareInfo> listPageReceiveInfosByReceiveUserId(
			UserPageShareInfo userPageShareInfo);

	/**
	 * 删除共享信息
	 * 
	 * @param Id
	 */
	void deleteShareInfoById(Integer Id) throws Exception;

	/**
	 * 获得未读的信息数
	 * 
	 * @param receiveUserId
	 * @return
	 */
	Integer getMessageNum(String receiveUserId);

	/**
	 * 根据originId和用户Id某个主题的相关聊天记录
	 * 
	 * @param userId
	 * @param originId
	 * @return
	 */
	List<UserPageShareInfo> getAllReplayShareInfo(Integer userId,
			Integer originId);

	/**
	 * 回复后插入接收方的数据信息
	 * 
	 * @param userPageShareInfo
	 */
	void insertReplayReceiveInfo(UserPageShareInfo userPageShareInfo);

	/**
	 * 保存共享页面信息
	 * 
	 * @param sendUserPageShaerInfo
	 * @param userPageShareInfoList
	 */
	void saveSharePage(UserPageShareInfo sendUserPageShaerInfo,
			List<UserPageShareInfo> userPageShareInfoList) throws Exception;

	/**
	 * 保存回复的页面共享信息
	 * 
	 * @param replayShareInfo
	 */
	void saveReplayShare(UserPageShareInfo replayShareInfo) throws Exception;
}
