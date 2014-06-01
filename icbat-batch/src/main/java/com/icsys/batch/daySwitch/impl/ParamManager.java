package com.icsys.batch.daySwitch.impl;

import java.util.List;


/**
 * 系统参数管理接口
 * 
 * @author kitty
 *
 */
public interface ParamManager{
	

    /**
     * 根据索引查询参数
     */
    public IcSysPara getParameter(String index)throws Exception;
    
    
   /**
    * 更新系统参数
    */
    public void setParameterValue(String index,String value)throws Exception;
    
    /**
     * 增添系统参数
     */
    public void addParameter(IcSysPara param)throws Exception;
    
    /**
     * 检索全部系统参数
     */
    public List<IcSysPara> searchAllParameter();
    
    /**
     * 根据索引查询参数
     */
    public IcSysPara getParameterByLock(String index) throws Exception;

//    /**
//     * 清空所有缓存
//     */
//    public void flushAll();
    
    //---------------------------------
    
    /**
     * 对应系统参数表  ic_sys_para
     * @author zhaosongpeng
     *
     */
    public class IcSysPara {
    	
        /**
         * 参数索引 (6)位
         * PARAMETER_INDEX
         * 主键    
         */
        private String parameterIndex;  
        
        /**
         * 参数名称     (64)位
         * PARAMETER_NAME
         */
        private String parameterName;
        
          /**
           * 参数值       (254)位
           * PARAMETER_VALUE
           */
        private String parameterValue;
        
        /**
         * 参数使用说明 (64)位
         * PARAMETER_DESCRIPTION
         */
        private String parameterDescription;
        
        /**
         * 参数控制标志 (8)位
         * PARAMETER_CONTROL
         */
        private String parameterControl;

    	public String getParameterIndex() {
    		return parameterIndex;
    	}

    	public void setParameterIndex(String parameterIndex) {
    		this.parameterIndex = parameterIndex;
    	}

    	public String getParameterName() {
    		return parameterName;
    	}

    	public void setParameterName(String parameterName) {
    		this.parameterName = parameterName;
    	}

    	public String getParameterValue() {
    		return parameterValue;
    	}

    	public void setParameterValue(String parameterValue) {
    		this.parameterValue = parameterValue;
    	}

    	public String getParameterDescription() {
    		return parameterDescription;
    	}

    	public void setParameterDescription(String parameterDescription) {
    		this.parameterDescription = parameterDescription;
    	}

    	public String getParameterControl() {
    		return parameterControl;
    	}

    	public void setParameterControl(String parameterControl) {
    		this.parameterControl = parameterControl;
    	}

    	@Override
    	public String toString() {
    		return "IcSysPara [parameterControl=" + parameterControl + ", parameterName=" + parameterName
    				+ ", parameterIndex=" + parameterIndex + ", parameterValue=" + parameterValue
    				+ ", parameterDescription=" + parameterDescription + "]";
    	}

    }
}
