package com.example.api.dto.base;

import com.example.api.constant.APIConstants;
import org.slf4j.MDC;

/** This class represent the body that will be return to client. */
public record BaseResponseApi<T>(BaseResponseStatus status, T data, BaseResponseMeta metaData) {

  private static final String SUCCESS_CODE = "ES-0000";
  private static final String SUCCESS_MSG = "Success";

  private BaseResponseApi(BaseResponseStatus status, BaseResponseMeta metaData) {
    this(status, null, metaData);
  }

  public static <T> BaseResponseApi<T> success(T data) {
    return success(new BaseResponseStatus(SUCCESS_CODE, SUCCESS_MSG), data, null);
  }

  public static <T> BaseResponseApi<T> success(T data, String signature) {
    return success(new BaseResponseStatus(SUCCESS_CODE, SUCCESS_MSG), data, signature);
  }

  public static <T> BaseResponseApi<T> success(BaseResponseStatus status, T data) {
    return success(status, data, null);
  }

  public static <T> BaseResponseApi<T> success(
      BaseResponseStatus status, T data, String signature) {
    String requestId = MDC.get(APIConstants.REQUEST_ID_KEY);
    BaseResponseMeta metaData = BaseResponseMeta.fromRequestIdAndSignature(requestId, signature);

    return new BaseResponseApi<>(status, data, metaData);
  }

  public static <T> BaseResponseApi<T> error(String errorCode, String errorMessage) {
    return error(errorCode, errorMessage, null, null);
  }

  public static <T> BaseResponseApi<T> error(String errorCode, String errorMessage, T data) {
    return error(errorCode, errorMessage, data, null);
  }

  public static <T> BaseResponseApi<T> error(
      String errorCode, String errorMessage, T data, String signature) {
    BaseResponseStatus status = new BaseResponseStatus(errorCode, errorMessage);
    String requestId = MDC.get(APIConstants.REQUEST_ID_KEY);
    BaseResponseMeta metaData = BaseResponseMeta.fromRequestIdAndSignature(requestId, signature);

    return new BaseResponseApi<>(status, data, metaData);
  }
}
