package com.github.clinker.commons.util.model;

/**
 * 操作响应。通常用于无业务返回值、又需要返回数据的操作，例如，Web Controller里的删除、更新等方法。
 */
public class OperationResponse {

	/**
	 * 成功
	 */
	public static final OperationResponse SUCCESS = new OperationResponse(true);

	/**
	 * 失败
	 */
	public static final OperationResponse FAIL = new OperationResponse(false);

	public static OperationResponse valueOf(final boolean success) {
		return success ? SUCCESS : FAIL;
	}

	/**
	 * 是否成功
	 */
	private boolean success;

	/**
	 * 默认为失败.
	 */
	public OperationResponse() {
		// 空操作
	}

	/**
	 * 声明是否成功.
	 *
	 * @param success 是否成功
	 */
	public OperationResponse(final boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "OperationResponse [success=" + success + "]";
	}
}
