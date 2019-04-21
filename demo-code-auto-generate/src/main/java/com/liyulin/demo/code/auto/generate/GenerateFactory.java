package com.liyulin.demo.code.auto.generate;

import com.liyulin.demo.code.auto.generate.config.ConfigFactory;
import com.liyulin.demo.code.auto.generate.db.BaseDaoSupport;
import com.liyulin.demo.code.auto.generate.db.TableParse;
import com.liyulin.demo.code.auto.generate.db.TableParseImpl;
import com.liyulin.demo.code.auto.generate.handler.EntityVoHandler;
import com.liyulin.demo.code.auto.generate.model.EntityVo;
import com.liyulin.demo.code.auto.generate.model.Table;
import com.liyulin.demo.code.auto.generate.utils.FreemarkUtils;
import com.liyulin.demo.code.auto.generate.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class GenerateFactory {

	public static String parentPath = null;


	private static void codeGenerate(String configFile) throws Exception {
		ConfigFactory.init(configFile);
		ConfigFactory.config.setTemplateDir("/template");
		String strTables = ConfigFactory.config.getTarget();
		if (strTables != null) {
			String[] tableNames = null;
			TableParse tableParse = new TableParseImpl();
			if ("all".equalsIgnoreCase(strTables)) {
				tableNames = tableParse.getAllTables().toArray(new String[] {});
			} else {
				tableNames = strTables.split(",");
			}
			FreemarkUtils.init(ConfigFactory.config.getTemplateDir());

			for (String tableName : tableNames) {
				Table table = tableParse.getTable(tableName);
				EntityVo vo = EntityVoHandler.createVo(table);
				Map<String, Object> root = new HashMap<String, Object>();
				root.put("vo", vo);
				root.put("generate", ConfigFactory.config);
				String packageName = ConfigFactory.config.getConfig().get("packageName");
				root.put("package",packageName);
				FreemarkUtils.WriterPage(root,"EntityTemplate.ftl",ConfigFactory.config.getConfig().get("serviceProjectPath"),
						packageName,"true");
			}
		}
	}

	/**
	 * 功能：代码生成入口
	 * @param clazz
	 */
	@SuppressWarnings("unused")
	public static void generateRun(Class clazz) {
		String resourcePath = clazz.getClassLoader().getResource("").getPath();
		File file = new File(resourcePath);
		parentPath = file.getParentFile().getParentFile().getParentFile().getParent();
		System.out.println("parentPath=" + parentPath);
		String confile = GenerateFactory.class.getResource("/config/config.properties").getFile();
		/*InputStream is = this.getClass().getClassLoader().
				.getResourceAsStream("/config/config.properties");*/
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(confile))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties conf = new Properties();
		try {
			conf.load(bf);
			String strValue = (String) conf.get("config.properties");
			System.out.println("config=" + strValue);
			if (StringUtils.isNotBlank(strValue)) {
				String[] properties = strValue.split(",");
				if (properties.length > 0) {
					for (String p : properties) {
						GenerateFactory.codeGenerate(confile.substring(0,confile.lastIndexOf("/"))+"/"+p);
					}
				}
			}
			System.out.println("finish...");
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (bf != null) {
					bf.close();
				}
			} catch (IOException e) {
				log.error("", e);
			} finally {
				/*if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("", e);
					}
				}*/
			}
		}
		BaseDaoSupport.close();
	}

}