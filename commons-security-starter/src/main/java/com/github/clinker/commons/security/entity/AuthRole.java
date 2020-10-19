package com.github.clinker.commons.security.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

/**
 * 角色。
 */
@Data
public class AuthRole {

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
	 * 标识
	 */
	private String identifier;

	/**
	 * 是否是超级用户
	 */
	private Boolean superRole;

	/**
	 * 排序，升序
	 */
	private Integer sort;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	private LocalDateTime creationTime;

}
