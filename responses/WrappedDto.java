// 
// Decompiled by Procyon v0.6.0
// 

package com.yojito.minima.responses;

import io.netty.handler.codec.http.HttpResponseStatus;
import java.util.Map;
import com.yojito.minima.gson.GsonDto;

public class WrappedDto<T extends GsonDto> extends GsonDto
{
    private final T response;
    private final Map<String, String> extraHeaders;
    private final HttpResponseStatus responseStatus;
    private final Map<String, String> cookies;
    private final String customTemplate;
    
    public WrappedDto(final T response, final Map<String, String> extraHeaders, final HttpResponseStatus responseStatus) {
        this.response = response;
        this.extraHeaders = extraHeaders;
        this.responseStatus = responseStatus;
        this.cookies = null;
        this.customTemplate = null;
    }
    
    public WrappedDto(final T response, final Map<String, String> cookies, final Map<String, String> extraHeaders, final HttpResponseStatus responseStatus) {
        this.response = response;
        this.extraHeaders = extraHeaders;
        this.responseStatus = responseStatus;
        this.cookies = cookies;
        this.customTemplate = null;
    }
    
    public WrappedDto(final T response, final Map<String, String> extraHeaders, final HttpResponseStatus responseStatus, final String customTemplate) {
        this.response = response;
        this.extraHeaders = extraHeaders;
        this.responseStatus = responseStatus;
        this.cookies = null;
        this.customTemplate = customTemplate;
    }
    
    public WrappedDto(final T response, final Map<String, String> cookies, final Map<String, String> extraHeaders, final HttpResponseStatus responseStatus, final String customTemplate) {
        this.response = response;
        this.extraHeaders = extraHeaders;
        this.responseStatus = responseStatus;
        this.cookies = cookies;
        this.customTemplate = customTemplate;
    }
    
    public T getResponse() {
        return this.response;
    }
    
    public Map<String, String> getExtraHeaders() {
        return this.extraHeaders;
    }
    
    public HttpResponseStatus getResponseStatus() {
        return this.responseStatus;
    }
    
    public Map<String, String> getCookies() {
        return this.cookies;
    }
    
    public String getCustomTemplate() {
        return this.customTemplate;
    }
}
