package com.github.clinker.commons.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 权限。
 */
@Data
public class AuthPermission {

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
	 * 名称
	 */
	private String name;

	/**
	 * ANT风格URL
	 */
	private String url;

	/**
	 * HTTP方法，逗号分隔，不区分大小写。*表示全部
	 */
	private String method;

	/**
	 * 不检查认证和授权
	 */
	private Boolean ignored;

	/**
	 * 排序，升序
	 */
	private Integer sort;

	/**
	 * 描述
	 */
	private String description;

}
