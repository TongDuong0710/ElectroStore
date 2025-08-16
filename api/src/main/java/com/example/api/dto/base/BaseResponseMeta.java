package com.example.api.dto.base;

public record BaseResponseMeta(String requestId, String signature, Long timestamp) {

  public static BaseResponseMeta fromRequestIdAndSignature(String requestId, String signature) {
    return new BaseResponseMeta(
        requestId, signature != null ? signature : "", System.currentTimeMillis());
  }
}
