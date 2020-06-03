package com.fvsen.apigateway.filter;

import com.fvsen.apigateway.enums.RateLimitEnum;
import com.fvsen.apigateway.exception.RateLimitException;
import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 *  限流
 * @author Fvsen
 * @date 2020/4/26/026 21:52
 */
public class RateLimitFilter extends ZuulFilter {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()) {
//            RequestContext currentContext = RequestContext.getCurrentContext();
//            HttpServletRequest request = currentContext.getRequest();
//            currentContext.setSendZuulResponse(false);
//            currentContext.setResponseStatusCode(404);
            throw new RateLimitException(RateLimitEnum.RATE_LIMIT_ERROT.getMessage());
        }
        return null;
    }
}
