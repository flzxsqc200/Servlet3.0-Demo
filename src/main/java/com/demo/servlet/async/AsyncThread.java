package com.demo.servlet.async;

import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import java.util.Date;

/**
 * Author LPJ
 * Date 2017/4/5
 */
public class AsyncThread implements Runnable {

    // 异步上下文
    private AsyncContext asyncContext = null;

    public AsyncThread(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    public void run() {
        try {
            // 开始时间
            Date startDate = new Date();
            // 暂停5秒
            Thread.sleep(5 * 1000);
            // 开始时间
            Date endDate = new Date();

            // 从异步上下文获取请求对象
            ServletRequest request = asyncContext.getRequest();
            request.setAttribute("name", "lpj");
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            asyncContext.dispatch("/asyncThread.jsp");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
