package com.github.clinker.commons.util.normalizer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 对金额进行四舍五入。
 */
public class BigDecimalNormalizer implements Normalizer<BigDecimal> {

	/**
	 * 对数字四舍五入。
	 *
	 * @param num 数字
	 * @return 四舍五入后的数字
	 */
	@Override
	public BigDecimal normalize(final BigDecimal num) {
		return num == null ? null : num.setScale(2, RoundingMode.HALF_UP);
	}

}
