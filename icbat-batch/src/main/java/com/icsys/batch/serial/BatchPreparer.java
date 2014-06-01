package com.icsys.batch.serial;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import com.icsys.platform.serial.NonceGenerator;
import com.icsys.platform.serial.SerialGenerator;
import com.icsys.platform.serial.SerialManager;
import com.icsys.platform.serial.SerialManagerMBean;
import com.icsys.platform.util.cache.CacheManager;
import com.icsys.platform.util.cache.CacheManagerMBean;

/**
 *
 * @author LiuYongli
 */
public class BatchPreparer {

    private static final String PARAM_MBEANSERVER = "MBeanServer";

    private MBeanServer mbeanServer;

	private Map<String, Object> parameters = new HashMap<String, Object>();
    
    private String moduleName = "task-preparer";
    
    public void setMBeanServer(MBeanServer mbeanServer) {
        this.mbeanServer = mbeanServer;
    }

	public void setParameter(String name, Object value) {
		this.parameters.put(name, value);
	}

	public Object getParameter(String name) {
		return parameters.get(name);
	}

    private ObjectName getObjectName(String domain, String module) throws MalformedObjectNameException {
        String type = this.getClass().getSimpleName();
        return new ObjectName(domain+":Module="+module+",Type="+type);
    }

    public void init(){


        try {
            //获取MBeanServer
            if (this.mbeanServer == null){
                String mbeanServName = (String) this.getParameter(PARAM_MBEANSERVER);
                if (mbeanServName == null){
                    throw new RuntimeException("Parameter 'MBeanServer' not specified");
                }
                InitialContext context = new InitialContext();

                this.mbeanServer = (MBeanServer) context.lookup(mbeanServName);
            }

            String domain = mbeanServer.getDefaultDomain();

            try {
                SerialManager serialManager = new SerialManager();
                mbeanServer.registerMBean(serialManager, new ObjectName(SerialManagerMBean.OBJECT_NAME));
            }
            catch (Exception ex){
                throw new RuntimeException(ex.getMessage(), ex);
            }
            
            try {
                CacheManager cacheManager = new CacheManager();
                mbeanServer.registerMBean(cacheManager, new ObjectName(CacheManagerMBean.MBEAN_NAME));
            }
            catch (Exception ex){
                throw new RuntimeException(ex.getMessage(), ex);
            }

            //初始化流水号生成器
            SerialGenerator.initialize(NonceGenerator.class);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage() ,ex);
        }
    }

    public void start(){

    }

    public void stop(){

    }

    public void shutdown(){
        
    }

}
