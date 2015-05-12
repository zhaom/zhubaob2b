package com.zhubao.b2b.file.servlet;

import com.oreilly.servlet.MultipartRequest;
import com.zhubao.b2b.file.utils.UploadFileRenamePolicy;
import com.zhubao.b2b.file.utils.UploadParam;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;

public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = -5857548330058926387L;

    private String uploadRoot;
    private String saveToPattern;
    private String fileURLBase;
    private String fileURLPattern;
    private int maxFileSizeInMB;
    private String encoding;
    private int maxSize;

    @Override
    public void init() throws ServletException {
        uploadRoot = getInitParameter("uploadRoot");
        saveToPattern = getInitParameter("saveToPattern");
        fileURLBase = getInitParameter("fileURLBase");
        fileURLPattern = getInitParameter("fileURLPattern");
        maxFileSizeInMB = Integer.parseInt(getInitParameter("maxFileSizeInMB"));
        encoding = getInitParameter("encoding");
        maxSize = maxFileSizeInMB * 1024 * 1024;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UploadParam param = new UploadParam();
        param.setRequest(request);
        param.setFileUrlBase(fileURLBase);

        Calendar now = Calendar.getInstance();
        String saveTo = getSaveTo(now);
        param.setSaveTo(saveTo);

        String absoluteSavePath = uploadRoot + saveTo;

        Random random = new Random(System.currentTimeMillis());

        try {
            File saveToDir = new File(absoluteSavePath);
            if (!saveToDir.exists()) {
                saveToDir.mkdirs();
            }

            MultipartRequest multi = new MultipartRequest(request, absoluteSavePath, maxSize, encoding, new UploadFileRenamePolicy(param, random));

            @SuppressWarnings("unchecked")
            Enumeration<String> e = multi.getFileNames();
            if (e.hasMoreElements()) {
                String element = e.nextElement();
                param.setSrcFilename(multi.getOriginalFileName(element));
                param.setDestFilename(multi.getFilesystemName(element));
                param.setFileSize(multi.getFile(element).length());
                param.setFileUrl(getFileURL(now, param.getDestFilename()));
                param.setFileExt(getFileExt(param.getSrcFilename()));
            }

            param.setSucc(true);
            param.setErrorCode(UploadParam.SUCC_SAVE);
            param.setMessage("upload success!");
        } catch (Exception e) {
            param.setErrorCode(UploadParam.ERROR_SAVE);
            param.setSucc(false);
            param.setMessage("upload failure!");
        }

        response.setCharacterEncoding(encoding);
        PrintWriter out = response.getWriter();
        try {
            out.print(param.getReplyJSONMsg());
            out.flush();
        } catch (Exception e) {
            try {
                out.close();
            } catch (Exception e1) {
            }
        }
    }

    private String getSaveTo(Calendar now) {
        String result = saveToPattern;
        result = StringUtils.replace(result, "{year}", String.valueOf(now.get(Calendar.YEAR)));
        result = StringUtils.replace(result, "{month}", String.valueOf(now.get(Calendar.MONTH) + 1));
        result = StringUtils.replace(result, "{day}", String.valueOf(now.get(Calendar.DATE)));
        return result;
    }

    private String getFileURL(Calendar now, String filename) {
        String result = fileURLBase + fileURLPattern;
        result = StringUtils.replace(result, "{year}", String.valueOf(now.get(Calendar.YEAR)));
        result = StringUtils.replace(result, "{month}", String.valueOf(now.get(Calendar.MONTH) + 1));
        result = StringUtils.replace(result, "{day}", String.valueOf(now.get(Calendar.DATE)));
        result = StringUtils.replace(result, "{filename}", filename);
        return result;
    }

    private String getFileExt(String filename) {
        return StringUtils.substringAfterLast(filename, ".");
    }
}
