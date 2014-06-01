package com.icsys.batch.encryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 从配置文件读取加密机参数
 * @author sparrow
 *
 */
public class EncryptorServerParam {
	
	private static Map<String,String> properties = new HashMap<String,String>();
	static {
		// 获取系统变量
//		System.out.println("encrytor server configuration:" + System.getProperty("BATCH_HOME"));
		// server use
		parsePropertyFile( new File(System.getProperty("BATCH_HOME"), "conf/encryptorserver.properties"));

		// local test user
		//parsePropertyFile( new File("d:/", "encryptorserver.properties"));
		
		setDefaultValue();
		
	}
	
	private static void parsePropertyFile(File propertiesFile) {
        Properties properites = new Properties();
		try {
			//读取配置文件
			properites.load(new FileInputStream(propertiesFile));
			// 循环遍历属性
			Enumeration enumer = properites.propertyNames();

			while (enumer.hasMoreElements()) {
				String key = (String) enumer.nextElement();
				String value = properites.getProperty(key);
				properties.put(key, value);
			}
//			System.out.println("read encrytor server configuration successed:" + System.getProperty("BATCH_HOME"));
		}
		catch (IOException e) {
			System.out.println("Load conf/encryptorserver.properties failure!" + System.getProperty("BATCH_HOME"));
		}
    }
	
	/**
	 *设定默认值（当读取配置文件失败时） 
	 */
	private static void setDefaultValue() {
		// 服务器地址设定默认值
		if (properties.get("encryptor_ip") == null || properties.get("encryptor_ip").equals("")) {
			System.out.println("encryptor_ip is null and set default value :32.74.128.170");
			properties.put("encryptor_ip", "32.74.128.170");
		}
		// 服务器端口设定默认值
		if (properties.get("encryptor_port") == null || properties.get("encryptor_port").equals("")) {
			System.out.println("encryptor_port is null and set default value :5000");
			properties.put("encryptor_port", "5000");
		}		
	}

	/**
	 * 返回指定的环境变量
	 * @return
	 */
	public static String getPropertyValue(String key){
		return (String)properties.get(key);
	}
}
