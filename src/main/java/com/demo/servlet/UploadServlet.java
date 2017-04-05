package com.demo.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Author LPJ
 * Date 2017/4/5
 */
@WebServlet(urlPatterns = "/upload.html")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 首先请求中的输入流，通过输出流输出到临时文件tempFileName

        // 从request中获取流信息
        InputStream fileStream = req.getInputStream();
        String tempFileName = "D:/tempFileName";
        File tempFile = new File(tempFileName);

        // 文件输出流指向这个临时文件
        FileOutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buff = new byte[1024];// 分配1024个字节大小的内存给buff byte数组
        int n;
        while ((n = fileStream.read(buff)) != -1) {
            outputStream.write(buff, 0, n); // 读取1024个字节放到buff byte数组中
        }

        // 关闭输出流、输入流
        outputStream.close();
        fileStream.close();

        //	RandomAccessFile 进行操作临时文件tempFileName
        //	RandomAccessFile的使用在开源项目java-core-learning中有介绍使用
        // 读取临时文件
        RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r");
        // 读取文件第二行，为了读取文件名
        int second = 1;
        String secondLine = null;
        while (second <= 2) {
            secondLine = randomAccessFile.readLine();
            second++;
        }
        // 获取文件名的位置
        int fileNamePosition = secondLine.lastIndexOf("filename");
        // 获取文件名称
        String fileName = secondLine.substring(fileNamePosition + 10,// "filename='" 十个字符
                secondLine.length() - 1);

        // 重定向文件指针到文件头
        randomAccessFile.seek(0);
        long startPosition = 0;
        int i = 1;
        // 获取文件内容起始位置
        while ((n = randomAccessFile.readByte()) != -1
                && i <= 4) { // 临时文件前面4行为表单属性
            if (n == '\n') {
                startPosition = randomAccessFile.getFilePointer();
                i++;
            }
        }

        // 重定向到临时文件末尾
        randomAccessFile.seek(randomAccessFile.length());
        long endPosition = randomAccessFile.getFilePointer();
        int j = 1;
        while (endPosition > 0
                && j <= 2) { // 临时文件后面2行为表单属性
            endPosition--;
            randomAccessFile.seek(endPosition);
            if (randomAccessFile.readByte() == '\n') {
                j++;
            }
        }

        // 保存文件到上传文件路径
        // 设置保存上传文件路径目录
        String realPath = getServletContext().getRealPath("/") + "upload";
        File uploadDir = new File(realPath);
        // 如果路径不存在，创建路径
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File uploadFile = new File(uploadDir, fileName);
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(uploadFile, "rw");

        // 从临时文件中读取文件内容（从起始位置开始获取）
        randomAccessFile.seek(startPosition);
        while (startPosition < endPosition - 1) {
            randomAccessFile2.write(randomAccessFile.readByte());
            startPosition = randomAccessFile.getFilePointer();
        }

        // 关输出输入流，删除临时文件
        randomAccessFile.close();
        randomAccessFile2.close();
        tempFile.delete();

        // 输出信息
        resp.setContentType("text/html;");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(" 绝对路径：" + uploadFile.getAbsolutePath());
        out.println("<br> 可读：" + uploadFile.canRead());
        out.println("<br> 可写：" + uploadFile.canWrite());
        out.println("<br> 文件名：" + uploadFile.getName());
        out.println("<br> 上级目录：" + uploadFile.getParent());
        out.println("<br> 相对地址：" + uploadFile.getPath());
        out.println("<br> 长度：" + uploadFile.length());
        out.println("<br> 最近修改时间：" + uploadFile.lastModified());
    }
}
