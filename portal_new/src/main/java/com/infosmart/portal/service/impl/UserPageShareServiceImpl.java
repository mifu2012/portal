package com.infosmart.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.infosmart.portal.pojo.User;
import com.infosmart.portal.pojo.UserPageShareInfo;
import com.infosmart.portal.service.UserPageShareService;

@Service
public class UserPageShareServiceImpl extends BaseServiceImpl implements
		UserPageShareService {
	/**
	 * 获得除自己外的所有用户
	 */
	@Override
	public List<User> getAllReceiveUsersBySendUser(Integer userId) {
		return this.myBatisDao.getList(
				"com.infosmart.mapper.User.getAllReceiveUsersBySendUser",
				userId);
	}

	/**
	 * 添加共享后，插入发送方共享信息
	 */
	@Override
	public void insertSendInfo(UserPageShareInfo userPageShareInfo)
			throws Exception {
		if (userPageShareInfo == null) {
			throw new Exception("添加共享后，插入发送方共享信息失败，参数为空");
		}
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertSendInfo",
				userPageShareInfo);

	}

	/**
	 * 添加共享后，插入接收方的共享信息
	 */
	@Override
	public void insertReceiveInfo(List<UserPageShareInfo> userPageShareInfoList) {
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertReceiveInfo",
				userPageShareInfoList);
	}

	/**
	 * 查看共享信息后设置为已读状态
	 */
	@Override
	public void updateIsReaded(Integer Id) {
		this.myBatisDao.update(
				"com.infosmart.mapper.UserPageShare.updateIsReaded", Id);
	}

	/**
	 * 获得发送的共享信息
	 */
	@Override
	public List<UserPageShareInfo> listPageSendInfosBySendUserId(
			UserPageShareInfo userPageShareInfo) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.UserPageShare.listPageSendInfosBySendUserId",
						userPageShareInfo);
	}

	/**
	 * 获得接受的共享信息
	 */
	@Override
	public List<UserPageShareInfo> listPageReceiveInfosByReceiveUserId(
			UserPageShareInfo userPageShareInfo) {
		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.UserPageShare.listPageReceiveInfosByReceiveUserId",
						userPageShareInfo);
	}

	/**
	 * 删除共享信息
	 */
	@Override
	public void deleteShareInfoById(Integer Id) throws Exception {
		if (Id == null) {
			throw new Exception("删除共享信息失败，ID为空");
		}
		this.myBatisDao.delete(
				"com.infosmart.mapper.UserPageShare.deleteShareInfoById", Id);
	}

	/**
	 * 获得未读记录数量
	 */
	@Override
	public Integer getMessageNum(String receiveUserId) {
		int num = 0;
		List<UserPageShareInfo> receiveList = this.myBatisDao.getList(
				"com.infosmart.mapper.UserPageShare.getUnReadedInfo",
				receiveUserId);
		if (receiveList != null && receiveList.size() > 0) {
			num = receiveList.size();
		}
		return num;
	}

	/**
	 * 获得所有相关的回复信息
	 */
	@Override
	public List<UserPageShareInfo> getAllReplayShareInfo(Integer userId,
			Integer originId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("originId", originId);

		return this.myBatisDao
				.getList(
						"com.infosmart.mapper.UserPageShare.getAllReplayShareInfo",
						map);

	}

	@Override
	public void insertReplayReceiveInfo(UserPageShareInfo userPageShareInfo) {
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertReplayReceiveInfo",
				userPageShareInfo);
	}

	@Override
	public void saveSharePage(UserPageShareInfo sendUserPageShaerInfo,
			List<UserPageShareInfo> userPageShareInfoList) throws Exception {
		if (sendUserPageShaerInfo == null) {
			this.logger.warn("保存共享页面出错信息失败：sendUserPageShaerInfo参数为空");
			throw new Exception("保存共享页面出错信息失败：sendUserPageShaerInfo参数为空");
		}
		if (userPageShareInfoList == null || userPageShareInfoList.isEmpty()) {
			this.logger.warn("保存共享页面出错信息失败：userPageShareInfoList参数为空");
			throw new Exception("保存共享页面出错信息失败：userPageShareInfoList参数为空");
		}
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertSendInfo",
				sendUserPageShaerInfo);
		Integer originId = sendUserPageShaerInfo.getId();
		if (userPageShareInfoList != null && userPageShareInfoList.size() > 0) {
			for (int i = 0; i < userPageShareInfoList.size(); i++) {
				userPageShareInfoList.get(i).setOriginId(originId);
			}
		}
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertReceiveInfo",
				userPageShareInfoList);

	}

	@Override
	public void saveReplayShare(UserPageShareInfo replayShareInfo)
			throws Exception {
		if (replayShareInfo == null) {
			this.logger.warn("保存回复共享页面出错信息失败：sendUserPageShaerInfo参数为空");
			throw new Exception("保存回复共享页面出错信息失败：sendUserPageShaerInfo参数为空");
		}
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertSendInfo",
				replayShareInfo);
		this.myBatisDao.save(
				"com.infosmart.mapper.UserPageShare.insertReplayReceiveInfo",
				replayShareInfo);

	}

}
