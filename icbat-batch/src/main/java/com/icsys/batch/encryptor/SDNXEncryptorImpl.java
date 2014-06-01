package com.icsys.batch.encryptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.run.util.HexBinary;
import com.primeton.btp.icmp.koalcommon.engine.EMEngineInfo;
import com.primeton.btp.icmp.koalcommon.engine.EMEnginePool;
import com.primeton.btp.icmp.koalcommon.engine.Engine;
import com.primeton.btp.icmp.koalcommon.engine.InitParams;

public class SDNXEncryptorImpl {
	private static Logger LOG = Logger.getLogger(SDNXEncryptorImpl.class);
	// 加密机engine
//	private static Engine engine = null;
	
	private static SDNXEncryptorImpl instantcManager;

	private static EMEnginePool emEnginePool;
	
	public static SDNXEncryptorImpl getInstance() throws Exception {
		if (instantcManager == null) {
			instantcManager = new SDNXEncryptorImpl();
		}
		if(emEnginePool == null ||emEnginePool.getPoolNum()==0|| getTsmParaList().size()!= emEnginePool.getPoolNum()){
			iniEmEnginePool();
			return instantcManager;
		}
		return instantcManager;
	}
	
	private static synchronized void iniEmEnginePool() throws Exception {
		emEnginePool = EMEnginePool.getInstance(getTsmParaList());
		emEnginePool.releaseAllAvailEngine();
	}
	
	public Engine getCanUseEngine() throws Exception {
		return getCanUseEngineInfo().getEMEngine();
	}
	
	public EMEngineInfo getCanUseEngineInfo() throws Exception {
		if(emEnginePool==null){
			iniEmEnginePool();
		}
		EMEngineInfo info = null;
		for(int i=0;i<3;i++){
			try{
				info = emEnginePool.getOneEngine();
				if(null == info){
					throw new Exception("EMEngineInfo is null");
				}
				break;
			}catch (Exception e) {
				// TODO: handle exception
				iniEmEnginePool();
				i++;
			}
		}
		return info;
	}

	
	private static List<InitParams> getTsmParaList(){
		List<InitParams> paraList = new ArrayList<InitParams>();
		TbTsmDao dao = new TbTsmDao();
		List<TbTsm> tbtsmList = dao.getAllTbTsm();
		for (TbTsm tbtsm : tbtsmList) {
			InitParams initParams = new InitParams();
			initParams.setEMIp(tbtsm.getHsmIp());
			initParams.setEMPort(tbtsm.getHsmPort());
			initParams.setEMType(tbtsm.getHsmType());
			paraList.add(initParams);
		}
		return paraList;
	}
	
	public void releaseEMEngineInfo(EMEngineInfo engineInfo) throws Exception{
		emEnginePool.releaseAvailEngine(engineInfo);
	}
	

	/**
	 * 根据模式选择，验证ARQC并生成ARPC
	 */
	public ARPCOutput verifyARQC(ARQCInput arqcInput) throws Exception {
		ARPCOutput arpcOutput = new ARPCOutput();
		try {
//			ARPCOutput arpcReturn = new ARPCOutput();
			// 0x00：Visa VSD、UKIS卡（目前不支持）0x01: Europay、MasterCard、 M/Chip卡
			// 0x02:PBOC卡
			byte cardType = 0x02;
			// 0x00:64位分组加密（目前不支持）0x01:128位分组加密
			byte encType = 0x01;
			// 0x01:验证ARQC\AAC\TC 0x02:验证ARQC\AAC\TC产生ARPC 0x03:产生ARPC
			String dealsPattern = arqcInput.getTrnMod();
			
			// 根据账号(卡号)，序列号获取子密钥分散用分散因子(PAN)
//			String div1 = this.getPAN(arqcInput.getAcctNo(), arqcInput.getAppNO());
			// 根据索引获取工作主密钥 0  MDK-AC  0
//			byte[] mdkACKey = getSymmKeyByIndex(2, 0, 0);
			// 计算arqcData长度
//			byte dataLen = (byte) HexBinary.decode(arqcInput.getAqdt()).length;

			// keytype
			byte keyType = 0x02;
			byte keyIndex = HexBinary.decode(arqcInput.getAppNO())[0];
			byte keyId = 0x00;
			
        	LOG.debug("pan:"+arqcInput.getAcctNo());
            LOG.debug("keyIndex:"+keyIndex);
            
			LOG.debug("dealsPattern:" + dealsPattern);
			LOG.debug("div1:" + arqcInput.getAcctNo());
			LOG.debug("icTrnCnt:" + arqcInput.getIcTrnCnt());
			LOG.debug("arqc:" + arqcInput.getArqc());
			LOG.debug("data:" + arqcInput.getAqdt());
			LOG.debug("Arc:" + arqcInput.getArc());
			// 调用接口验证ARQC
//			byte[] returnARPC = engine.VerifyARQC(HexBinary.decode(cardType),
//					HexBinary.decode(dealsPattern)[0], HexBinary.decode(div1),
//					HexBinary.decode(arqcInput.getIcTrnCnt()), HexBinary.decode(arqcInput
//							.getArqc()), HexBinary.decode(encType), mdkACKey,
//					dataLen, HexBinary.decode(arqcInput.getAqdt()), HexBinary.decode(arqcInput.getArc()));
			/*byte[] returnARPC = */
			EMEngineInfo engineInfo = getCanUseEngineInfo();
			engineInfo.getEMEngine().indexVerifyARQCBuildARPC(cardType, HexBinary.decode(dealsPattern)[0], HexBinary.decode(arqcInput.getAcctNo()), 
					HexBinary.decode(arqcInput.getIcTrnCnt()), HexBinary.decode(arqcInput.getArqc()), encType, keyType, keyIndex, keyId,  
					HexBinary.decode(arqcInput.getAqdt()), HexBinary.decode(arqcInput.getArc()));
//			LOG.debug("return ARPC:" + HexBinary.encode(returnARPC));
			releaseEMEngineInfo(engineInfo);
			// 设定返回值
			arpcOutput.setThdRetCod("000000");
//			arpcOutput.setARPC(HexBinary.encode(returnARPC));
//			return arpcReturn;
		} catch (Exception e) {
			arpcOutput.setThdRetCod("999999");
//			arpcOutput.setARPC("");
			e.printStackTrace();
			LOG.error("校验TC失败，"  + e.getMessage());
		}
		return arpcOutput;
	}

}
