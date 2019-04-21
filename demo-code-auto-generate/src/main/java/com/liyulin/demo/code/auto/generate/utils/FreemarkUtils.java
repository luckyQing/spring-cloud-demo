package com.liyulin.demo.code.auto.generate.utils;

import com.liyulin.demo.code.auto.generate.model.EntityVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class FreemarkUtils {
	private static final String CONTENT_ENCODING = "UTF-8";
	private static final Logger log = LoggerFactory.getLogger(FreemarkUtils.class);
	private static Configuration configuration;
	private static Template template;

	public static void init(String templateDir) {
		try {
			configuration = new Configuration(Configuration.VERSION_2_3_22);
			configuration.setClassForTemplateLoading(FreemarkUtils.class, templateDir);
		} catch (Exception e) {
			log.error("", e);
		}
	}


	public static void WriterPage(Map<String, Object> data, String templateName,
			String fileDirPath, String targetFile, String coverwrite) {
		FileOutputStream fos = null;
		try {
			String str = "/src/main/java/";
			targetFile = targetFile.replace(".","/");
			String dirName = fileDirPath+str+targetFile;
			File dir = new File(dirName);

			File file = new File(dirName,((EntityVo)data.get("vo")).getName()+".java");
			if (file.exists()) {
				if (!StringUtils.isNotBlank(coverwrite) || !coverwrite.equals("true")) {
					//					System.out.println(targetFile + " is exist, skip");
					return;
				}
			}
			if (!dir.exists()) {
				dir.mkdirs();
			}
			fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(fos, CONTENT_ENCODING));

			template = configuration.getTemplate(templateName);
			template.process(data, writer);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}
}