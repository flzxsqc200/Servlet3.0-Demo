package com.demo.servlet.async;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Author LPJ
 * Date 2017/4/5
 */
@WebServlet(urlPatterns = "/async", asyncSupported = true)// 开始异步调用
public class AsyncServlet extends HttpServlet {
    private static final long serialVersionUID = -6795872120748178723L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        Date startDate = new Date();
        System.out.println("Servlet当前时间 --- " + startDate);

        // 从请求中获取AsyncContext
        AsyncContext asyncContext = req.startAsync();
        // 设置异步调用的超时时长
        asyncContext.setTimeout(30 * 1000);// 30秒
        // 开始异步调用
        asyncContext.start(new AsyncThread(asyncContext));

        Date endDate = new Date();
        System.out.println("Servlet结束时间 --- " + endDate);

        // startDate和endDate时间差不多相等。
        // 说明Servlet重新发起了一条线程去掉用耗时业务方法，避免了阻塞式调用
    }
}
