package com.h3c.idcloud.core.adapter.facade.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

@Service
public class FreeMarkerHelper {

    private static Logger log = LoggerFactory.getLogger(FreeMarkerHelper.class);
    @Autowired
    private ApplicationContext context;

    public String getParam(String virtualType, Object from) {
        Configuration conf = (Configuration) context.getBean("freemarkerConfig");
        // conf.setClassForTemplateLoading(this.getClass(), "/template");
        ClassTemplateLoader vmwareTpl = new ClassTemplateLoader(this.getClass(), "/template/vmware");
        ClassTemplateLoader kvmTpl = new ClassTemplateLoader(this.getClass(), "/template/kvm");
        ClassTemplateLoader hmcTpl = new ClassTemplateLoader(this.getClass(), "/template/hmc");
        TemplateLoader[] loaders = new TemplateLoader[]{vmwareTpl, kvmTpl, hmcTpl};
        MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
        conf.setTemplateLoader(mtl);
        try {
            String templateName = virtualType + "-" + from.getClass().getSimpleName() + ".ftl";
            Template template = conf.getTemplate(templateName);
            log.info("get freemarker template :" + template.getName());
            Writer out = new StringWriter();
            template.process(from, out);
            log.info(out.toString());
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (TemplateException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public JSONObject convert(String virtualType, Object from) {
//
//        Configuration conf = (Configuration) context.getBean("freemarkerConfig");
//
//        // conf.setClassForTemplateLoading(this.getClass(), "/template");
//
//        ClassTemplateLoader vmwareTpl = new ClassTemplateLoader(this.getClass(), "/template/vmware");
//
//        ClassTemplateLoader kvmTpl = new ClassTemplateLoader(this.getClass(), "/template/kvm");
//        ClassTemplateLoader hmcTpl = new ClassTemplateLoader(this.getClass(), "/template/hmc");
//
//        TemplateLoader[] loaders = new TemplateLoader[]{vmwareTpl, kvmTpl, hmcTpl};
//
//        MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
//
//        conf.setTemplateLoader(mtl);
//
//        try {
//
//            String templateName = virtualType + "-" + from.getClass().getSimpleName() + ".ftl";
//
//            Template template = conf.getTemplate(templateName);
//
//            log.info("get freemarker template :" + template.getName());
//
//            Writer out = new StringWriter();
//
//            template.process(from, out);
//
//            log.info(out.toString());
//
//            JSONObject json = JSONObject.fromObject(out.toString());
//
////			json.put("authUser", ((Base) from).getAuthUser());
//
//            log.info(json.toString());
//
//            return json;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } catch (TemplateException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
}
