package com.github.clinker.commons.verifycode;

import java.time.Duration;

import lombok.Data;

/**
 * 频率属性。
 */
@Data
public class VerifyCodeFrequencyProperties {

	/**
	 * 最大次数
	 */
	private int max;

	/**
	 * 时间单位
	 */
	private Duration unit;

}
