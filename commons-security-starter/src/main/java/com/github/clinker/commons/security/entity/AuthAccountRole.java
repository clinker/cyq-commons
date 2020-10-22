package com.github.clinker.commons.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 账号与角色的关联。
 */
@Data
public class AuthAccountRole {

	/**
	 * ID
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;

	/**
	 * 账号ID
	 */
	private String accountId;

	/**
	 * 角色ID
	 */
	private String roleId;

}
