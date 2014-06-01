package com.icsys.batch.task.profitdata.proxy.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 此类初始化生成要生成文件名的连个map
 * @author SDNX
 *
 */
public class MapInit {  
	public static Map<String,String> getAreaMap(){
		Map<String,String> adressMap = new HashMap<String,String>();
		adressMap.put("JN","01");
		adressMap.put("QD","02");
		adressMap.put("ZB","03");
		adressMap.put("ZZ","04");
		adressMap.put("DY","05");
		adressMap.put("YT","06");
		adressMap.put("WF","07");
		adressMap.put("JL","08");
		adressMap.put("TA","09");
		adressMap.put("WH","10");
		adressMap.put("RZ","11");
		adressMap.put("LW","12");
		adressMap.put("BZ","13");
		adressMap.put("DZ","14");
		adressMap.put("LC","15");
		adressMap.put("LY","16");
		adressMap.put("HZ","17");                                           
		return adressMap;                                                                       
	} 
}