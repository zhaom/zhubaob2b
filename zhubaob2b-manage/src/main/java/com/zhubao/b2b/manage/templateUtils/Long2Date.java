package com.zhubao.b2b.manage.templateUtils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-2-15
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public class Long2Date implements TemplateMethodModel {
    @Override
    public Object exec(List list) throws TemplateModelException {
        SimpleDateFormat mydate = new SimpleDateFormat((String) list.get(0));
        long dt = Long.parseLong((String)list.get(1));
        if(dt > 10000000)
            return mydate.format(new Date(dt));
        else
            return "";
    }
}
