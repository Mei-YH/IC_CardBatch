package com.icsys.batch.encryptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.icsys.batch.run.util.HexBinary;
import com.primeton.btp.icmp.koalcommon.engine.EMEnginePool;
import com.primeton.btp.icmp.koalcommon.engine.EMException;
import com.primeton.btp.icmp.koalcommon.engine.Engine;
import com.primeton.btp.icmp.koalcommon.engine.EngineFactory;
import com.primeton.btp.icmp.koalcommon.engine.InitParams;


/**
 * 根据苏州银行戈尔加密API进行修改(原有模拟加密版本)
 * 
 * @author sparrow
 * 
 */
public class EncryptorImpl{
	
	/**
	 * 使用工作主密钥计算MAC用参数
	 */
	// MAC计算接口使用 0x02——>WK
//	byte keyType = HexBinary.decode(EncryptorServerParam.getPropertyValue("encryptor.keytype"))[0];
//	// 密钥索引 00
//	byte keyIndex =  HexBinary.decode(EncryptorServerParam.getPropertyValue("encryptor.keyindex"))[0];
//	// WK——> [0-3] 02为MAC用
//	byte keyID =  HexBinary.decode(EncryptorServerParam.getPropertyValue("encryptor.keyid"))[0];
	
	
	// LOG instance
	private static Logger LOG = Logger.getLogger(EncryptorImpl.class);
	// 加密机engine
	private static Engine engine = null;
	private static EMEnginePool enginePool = null;

	public EncryptorImpl() {
		// 初始化连接参数
		try {
			getEngine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 数据加密
	 * 1.业务--->DP 使用传输密钥TK 例 制卡数据第2磁道信息加密给数据准备
	 * 2.脚本中使用的明文数据加密，使用MDK-ENC 1 （pin加密例外) 
	 */
	/*public EncodeDataOutput encodeData(EncodeDataInput data)
			throws EncryptorException {
		EncodeDataOutput output = new EncodeDataOutput();
		try {
			// 经过LMK保护的密钥
			byte[] encodeKey = null;
			byte initFun = 0x00;
			// 获取加密使用的密钥状态 
			// 使用MDK-enc
			if (data.getEncodeKeyType().equals("2")) {
				encodeKey = this.getSymmKeyByIndex(2, 0, 1);
				initFun = 0x02;
			} else if (data.getEncodeKeyType().equals("1")) {
				// 使用TK
				encodeKey = this.getSymmKeyByIndex(0, 0, 0);
				initFun = 0x01;
			} else if (data.getEncodeKeyType().equals("3")) {
				//TODO
				encodeKey = null;
			} else if (data.getEncodeKeyType().equals("4")) {
				//TODO
				encodeKey = null;
			} else {
				//TODO
				encodeKey = null;
			}
			byte[] result = null;
			// LMK解密后不需要分散
			if (initFun == (byte)0x01) {
				// 加密
				//TODO 原数据处理方式
				result = engine.symmetricKeyEnc((byte)0x00, initFun, encodeKey, null, data.getData().getBytes());
			} else {
				
			}
			output.setThdRetCod("000000");
			output.setData(new String(result));
			
		} catch (Exception e) {
			output.setData("");
			output.setThdRetCod("999999");
		}
		return output;
	}
*/
	/**
	 * 卡余额更新
	 */
	/*public CardBalanceOutput flashCardBalance(CardBalanceInput balance)
			throws EncryptorException {
		CardBalanceOutput output = new CardBalanceOutput();
		try {
			LOG.debug("更新卡内余额开始");
			String pan = getPAN(balance.getPan(),balance.getSeqNO());
			UpdateBalanceCommand command = new UpdateBalanceCommand(balance.getBalance());
			String macBlock = MAC.genHexSringMAB(balance.getIcTrnCnt(), balance.getArqc(), command);

			// 根据索引获取工作主密钥 2-MDK-MAC 2
			//byte[] mdkACKey = getSymmKeyByIndex(2);
			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			
			LOG.debug("pan："+pan);
			LOG.debug("macBlock："+macBlock);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			// 生成MAC
			//byte[] mac = engine.PBOC2MAC(HexBinary.decode("0000000000000000"), HexBinary.decode(pan), HexBinary.decode(balance.getIcTrnCnt()), encType, mdkACKey, dataLen, HexBinary.decode(macBlock));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(balance.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));
			// 拼接TLV命令
			Script script = new Script(command);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");
			
		} catch (Exception e) {
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		return output;
	}
*/
	/**
	 * 卡应用加锁
	 */
/*	public CardAppOutput lockCardApp(CardAppInput app)
			throws EncryptorException {
		CardAppOutput output = new CardAppOutput();
		try {
			LOG.debug("卡应用加锁开始");
			// scipt list
			List<Command> commands = new ArrayList<Command>() ;
			String pan = getPAN(app.getPan(),app.getSeqNO());
			AppBlockCommand command = new AppBlockCommand();
			// 生成MAC Block
			String macBlock = MAC.genHexSringMAB(app.getIcTrnCnt(), app.getArqc(), command);
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			LOG.debug("pan："+pan);
			LOG.debug("macBlock："+macBlock);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(app.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));
			commands.add(command);
			
			// 拼接TLV命令
			Script script = new Script(commands);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");			

			LOG.debug("卡应用加锁正常结束");
		} catch (Exception e) {
			// 出错打印debug信息 返回999999
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		return output;
	}
	*/
	/**
	 * 卡应用清零加锁
	 */
	/*public CardAppOutput cleanAndLock(CardAppInput app)
			throws EncryptorException {
		CardAppOutput output = new CardAppOutput();
		try {
			LOG.debug("卡清零应用加锁开始");
			// scipt list
			List<Command> commands = new ArrayList<Command>() ;
			String pan = getPAN(app.getPan(),app.getSeqNO());
			AppBlockCommand command = new AppBlockCommand();
			// 生成MAC Block
			String macBlock = MAC.genHexSringMAB(app.getIcTrnCnt(), app.getArqc(), command);

			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			LOG.debug("pan："+pan);
			LOG.debug("macBlock："+macBlock);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(app.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));
			
			
			// 生成清零脚本
			UpdateBalanceCommand command2 = new UpdateBalanceCommand("0");
			// 生成MAC Block2
			String macBlock2 = MAC.genHexSringMAB(app.getIcTrnCnt(), app.getArqc(), command2);
			LOG.debug("macBlock2："+macBlock2);
			dataLen = (byte) HexBinary.decode(macBlock2).length;
			
			byte[] mac2 = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(app.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock2));
			String macString2 = HexBinary.encode(mac2);
			LOG.debug("mac2："+macString2);
			command2.setMAC(HexBinary.decode(macString2+"00"));

			commands.add(command2);
			commands.add(command);
			
			// 拼接TLV命令
			Script script = new Script(commands);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");			

			LOG.debug("卡应用清零加锁正常结束");
		} catch (Exception e) {
			// 出错打印debug信息 返回999999
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		return output;
	}
*/
	/**
	 * 个人信息修改
	 */
/*	public UserInfoOutput modifyUserInfo(UserInfoInput info)
			throws EncryptorException {
		UserInfoOutput output = new UserInfoOutput();
		try {
			// 创建命令对象，第一个参数应该为终端发的READ RECORD的0101 70 TLV域信息
			UpdatePersonalInfoCommand command = new UpdatePersonalInfoCommand(HexBinary.decode(info.getAQDT())
					,info.getCusNam(), info.getCerTyp(), info.getCerNo());
			// 生成MAC Block
			String macBlock = MAC.genHexSringMAB(info.getIcTrnCnt(), info.getArqc(), command);
			// 获取分散用的pan
			String pan = getPAN(info.getPan(),info.getSeqNO());
			
			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			LOG.debug("pan："+pan);
			LOG.debug("macBlock："+macBlock);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(info.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));

			// 拼接TLV命令
			Script script = new Script(command);
			LOG.debug(script.toHexString());
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");			
		} catch (Exception e) {
			output.setThdRetCod("999999");
			output.setScpt("");
			e.printStackTrace();
		}
		return output;
	}

	@Deprecated
	public EncodeByWKOutput encodeByWK(EncodeByWKInput data)
			throws EncryptorException {
		return null;
	}
*/
	

	/*
	 * 更新余额上限
	 * 生成put data写卡脚本两个
	 */
	/*public UpdateBalanceCapOutput updateBalanceCap(UpdateBalanceCapInput input)
			throws EncryptorException {
		UpdateBalanceCapOutput output = new UpdateBalanceCapOutput();

		try {
			// scipt list
			List<Command> commands = new ArrayList<Command>() ;
			LOG.debug("更新卡内参数开始");
			// 获取pan
			String pan = getPAN(input.getPan(),input.getSeqNO());
			// 更新最大限额命令
			ResetBalanceCapCommand resetBalanceCommand = new ResetBalanceCapCommand(input.getTranCap());
			// 生成MAC Block 更新最大限额
			String macBlock1 = MAC.genHexSringMAB(input.getIcTrnCnt(), input.getArqc(), resetBalanceCommand);

			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock1).length;
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			LOG.debug("pan："+pan);
			LOG.debug("macBlock1："+macBlock1);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(input.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock1));
			
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			resetBalanceCommand.setMAC(HexBinary.decode(macString+"00"));
			commands.add(resetBalanceCommand);
			
			// 命令2
			UpdateTransLimitCommand resetTranCapCommand = new UpdateTransLimitCommand(input.getTranCapSingle());
			// 生成MAC Block 单笔交易限额
			String macBlock2 = MAC.genHexSringMAB(input.getIcTrnCnt(), input.getArqc(), resetTranCapCommand);
			LOG.debug("macBlock2："+macBlock2);

			dataLen = (byte) HexBinary.decode(macBlock2).length;
			byte[] mac2 = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(input.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock2));
			String macString2 = HexBinary.encode(mac2);
			LOG.debug("mac2："+macString2);
			resetTranCapCommand.setMAC(HexBinary.decode(macString2+"00"));
			commands.add(resetTranCapCommand);
			// 拼接TLV命令
			Script script = new Script(commands);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");			

			LOG.debug("更新卡内参数修改正常结束");
		} catch (Exception e) {
			// 出错打印debug信息 返回999999
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		return output;
	}
*/
	/**
	 * 更新卡PIN
	 * WK 02 索引 00 id MDK-ENC 1 
	 */
/*	public UpdatePINOutput updatePIN(UpdatePINInput data)
			throws EncryptorException {
		UpdatePINOutput output = new UpdatePINOutput();
		try {			
			LOG.debug("offlinepin :" + data.getOfflinePIN());
			// 获取pan
			String pan = getPAN(data.getPan(),data.getSeqNO());
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			// WK——> [0-3] 01为ENC用
			byte[] keyID =  {(byte)1};
			
			// 获取UDK-A
			byte[] udka = engine.GetUDK_A((byte)2, (byte)0, (byte)1, pan);
			// 生成PIN数据块
			byte[] pinBlock = data.getPinBlock(udka);
			
			// 工作方式 0x00加密
			byte[] calType = {(byte)0x00};

			// 对明文进行加密
			byte[] offlinePIN = engine.PBOC2EncIndex(HexBinary.decode(pan), HexBinary.decode(data.getIcTrnCnt())
					, encType, keyType, keyIndex, keyID, calType, (byte)pinBlock.length, pinBlock);

			LOG.debug("UDK-A ENC:" + HexBinary.encode(udka));
			LOG.debug("offlinepin 加密前的数据(PINBLOCK):" + HexBinary.encode(pinBlock));
			LOG.debug("offlinepin 加密后的数据:" + HexBinary.encode(offlinePIN));

//			byte[] testUnEnc = engine.PBOC2EncIndex(HexBinary.decode(pan), HexBinary.decode(data.getIcTrnCnt())
//					, encType, keyType, keyIndex, keyID, new byte[]{(byte)0x01}, (byte)offlinePIN.length, offlinePIN);
//			
//			LOG.debug("offlinepin 解密后的数据:" + HexBinary.encode(testUnEnc));

			// 生成脚本
			ChangePINCommand command = null;
			if (data.getTrnMod().equals("0")) {
				command = new ChangePINCommand(HexBinary.encode(offlinePIN),(byte)1);
			} else {
				command = new ChangePINCommand(HexBinary.encode(offlinePIN));
			}
			String macBlock = MAC.genHexSringMAB(data.getIcTrnCnt(), data.getArqc(), command);
			LOG.debug("macBlock："+macBlock);
			
			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(data.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));
			// 拼接TLV命令
			Script script = new Script(command);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");
			
		} catch (Exception e) {
			// 出错打印debug信息 返回999999
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		
		return output;
	}
*/


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
			/*byte[] returnARPC = */engine.indexVerifyARQCBuildARPC(cardType, HexBinary.decode(dealsPattern)[0], HexBinary.decode(arqcInput.getAcctNo()), 
					HexBinary.decode(arqcInput.getIcTrnCnt()), HexBinary.decode(arqcInput.getArqc()), encType, keyType, keyIndex, keyId,  
					HexBinary.decode(arqcInput.getAqdt()), HexBinary.decode(arqcInput.getArc()));
//			LOG.debug("return ARPC:" + HexBinary.encode(returnARPC));

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

	/*
	 * 密码校验
	 */
/*	public PwdPlaint verifyPassword(PwdCipher password)
			throws EncryptorException {
		PwdPlaint pwdPlaint = new PwdPlaint();
//		try {
//			// 1 重组加密机所需接口报文
//			PasswordInput pwdInput = recombin.verifyPassword(password);
//			// 2 加密机返回接口报文
//			PasswordOutput pwdOut = simublateEncryptor.verifyPassword(pwdInput);
//			// 3 重组应用所需接口报文
//			pwdPlaint.setThdRetCod(pwdOut.getThdRetCod());
//			pwdPlaint.setData(pwdOut.getData());
//		} catch (Exception e) {
//			pwdPlaint.setThdRetCod("999999");
//			pwdPlaint.setData("");
//		}
		return pwdPlaint;
	}
	/**
	 * 卡片应用解锁
	 * 
	 */
	/*public CardAppOutput unlockCardApp(CardAppInput app)
			throws EncryptorException {
		CardAppOutput output = new CardAppOutput();
		try {
			LOG.debug("卡应用解锁开始");
			String pan = getPAN(app.getPan(),app.getSeqNO());
			AppUnlockCommand command = new AppUnlockCommand();
			// 生成MAC Block
			String macBlock = MAC.genHexSringMAB(app.getIcTrnCnt(), app.getArqc(), command);
			// 0x01：128位分组加密
			byte[] encType = {0x01};
			// 计算MAC Block长度
			byte dataLen = (byte) HexBinary.decode(macBlock).length;
			LOG.debug("pan："+pan);
			LOG.debug("macBlock："+macBlock);
			LOG.debug("keyType："+ EncryptorServerParam.getPropertyValue("encryptor.keytype"));
			LOG.debug("keyIndex："+ EncryptorServerParam.getPropertyValue("encryptor.keyindex"));
			LOG.debug("keyID："+ EncryptorServerParam.getPropertyValue("encryptor.keyid"));
			byte[] mac = engine.PBOC2MACIndex(HexBinary.decode("0000000000000000")
					,HexBinary.decode(pan)
					,HexBinary.decode(app.getIcTrnCnt())
					,encType,keyType
					,keyIndex
					,keyID
					,dataLen
					,HexBinary.decode(macBlock));
			
			String macString = HexBinary.encode(mac);
			LOG.debug("mac："+macString);
			command.setMAC(HexBinary.decode(macString+"00"));
			// 拼接TLV命令
			Script script = new Script(command);
			output.setScpt(script.toHexString());
			output.setThdRetCod("000000");			

			LOG.debug("卡应用解锁正常结束");
		} catch (Exception e) {
			// 出错打印debug信息 返回999999
			e.printStackTrace();
			output.setThdRetCod("999999");
			output.setScpt("");
		}
		return output;
	}
	/**
	 * 链接加密机
	 * 
	 * @throws Exception
	 */
	private static void getEngine() throws Exception {
		int nType = Engine.EngineType_SJY49;
		try {
			if (nType == 0)
				throw new EMException("加密机实例获取失败");
			if(null == enginePool){
				String[] server = EncryptorServerParam.getPropertyValue("encryptor_ip").split("\\|");
				String[] port = EncryptorServerParam.getPropertyValue("encryptor_port").split("\\|");
				if(server.length != port.length){
					throw new EMException("加密机配置文件错误");
				}
				List<InitParams> params = new ArrayList<InitParams>();
				for(int i=0;i<server.length;i++){
					InitParams param = new InitParams();
					param.setEMType(nType);
					// 加密机地址
					param.setEMIp(server[i]);
					param.setEMPort(Integer.valueOf(port[i]));
					params.add(param);
				}
				enginePool = EMEnginePool.getInstance(params);
			}

			engine = enginePool.getOneEngine().getEMEngine();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获取应用主密钥
	 * @param keyType 0-TK;1-KEK;2-应用主密钥
	 * @param keyIndex 密钥索引 一般为0
	 * @param arrayIndex
	 * 	MDK-AC  0
	 *  MDK-ENC 1
	 *	MDK-MAC 2
	 * @param 返回数组位置 0 
	 * @return
	 * @throws Exception
	 */
	public byte[] getSymmKeyByIndex(int keyType, int keyIndex, int arrayIndex) throws Exception {
		List<byte[]> tempList = engine.getSymmKeyByIndex(keyIndex, keyType);
		// 数组第0组为 MDK-AC
		return tempList.get(arrayIndex);
	}
	
	
	private String getPAN(String acctNO, String appNO) {
		String subAppNO = appNO.substring(0, 2);
		String pan = acctNO.substring(acctNO.length() - 14, acctNO.length())
				+ subAppNO;
		return pan;
	}
}
