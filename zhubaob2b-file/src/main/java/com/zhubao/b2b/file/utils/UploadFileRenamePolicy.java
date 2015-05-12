package com.zhubao.b2b.file.utils;

import com.oreilly.servlet.multipart.FileRenamePolicy;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Random;

public class UploadFileRenamePolicy implements FileRenamePolicy {

    private UploadParam param;
    private Random random;

    public UploadFileRenamePolicy(UploadParam param, Random random) {
        this.param = param;
        this.random = random;
    }

    @Override
    public File rename(File file) {
        String parent = file.getParent();
        String name = file.getName();
        String ext = null;

        if (StringUtils.contains(name, '.')) {
            ext = StringUtils.substringAfterLast(name, ".");
        }

        StringBuffer newName = new StringBuffer();
        newName.append("up_");
        newName.append(System.currentTimeMillis());
        newName.append("_").append(Math.abs(random.nextInt()));
        if (ext != null) {
            newName.append(".").append(ext.toLowerCase());
        }

        return new File(parent, newName.toString());
    }
}
