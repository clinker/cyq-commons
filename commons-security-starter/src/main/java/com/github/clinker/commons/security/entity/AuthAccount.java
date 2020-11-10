package com.github.clinker.commons.security.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;

/**
 * 账号。
 */
@Data
public class AuthAccount {

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 所属服务ID，即租户
	 */
	private String serviceId;

	/**
	 * 登录名
	 */
	private String username;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 是否禁用
	 */
	private Boolean disabled;

	/**
	 * 头像路径，本地UNIX格式或URI
	 */
	private String avatar;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	private LocalDateTime creationTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime modifiedTime;

	/**
	 * 是否已删除
	 */
	@TableLogic
	private Boolean deleted;

}