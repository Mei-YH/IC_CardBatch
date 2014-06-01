package com.icsys.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class PlatformFTP {

	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

	// path should not the path from root index
	// or some FTP server would go to root as '/'.
	public void connectServer(URL url) throws SocketException, IOException {
		String server = url.getHost();
		int port = url.getPort();

		String userInfo = url.getUserInfo();
		System.out.println(userInfo);
		int index = userInfo!=null ? userInfo.indexOf(':') : 0;
		String user = index > 0 ? userInfo.substring(0, index) : userInfo;
		String password = index > 0 ? userInfo.substring(index+1) : "";
		String path = url.getPath();
		connectServer(server, port, user, password, path);	
		}

	public void connectServer(String server, int port, String user, String password, String path)
	        throws SocketException, IOException {
		ftpClient = new FTPClient();
		//打印日志
		ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		ftpClient.connect(server, port);
		ftpClient.setControlEncoding("GBK");
		System.out.println("Connected to " + server + ".");
		System.out.println(ftpClient.getReplyCode());
		boolean isLogin=ftpClient.login(user, password);
		System.out.println("连接服务器是否成功:"+isLogin);
		// Path is the sub-path of the FTP path
		if (path.length() != 0) {
			System.out.println("changeWorkingDir:"+ftpClient.changeWorkingDirectory(path));//linux下path是全路径
//			System.out.println("changeWorkingDir:"+ftpClient.changeWorkingDirectory(""));//linux下path是全路径
		}
	}

	// FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE
	// Set transform type
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	public void closeServer() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	// =======================================================================
	// == About directory =====
	// The following method using relative path better.
	// =======================================================================

	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	public boolean createDirectory(String pathname) throws IOException {
		return ftpClient.makeDirectory(pathname);
	}

	public boolean mkdirs(String pathname) throws IOException {
		if (ftpClient.makeDirectory(pathname)) {
			return true;
		}
		else {
			int index = pathname.lastIndexOf('/');
			if (index > 0) {
				if (mkdirs(pathname.substring(0, index))) {
					return ftpClient.makeDirectory(pathname);
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		
	}
	
	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	// delete all subDirectory and files.
	public boolean removeDirectory(String path, boolean isAll) throws IOException {

		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		//
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				System.out.println("* [sD]Delete subPath [" + path + "/" + name + "]");
				removeDirectory(path + "/" + name, true);
			}
			else if (ftpFile.isFile()) {
				System.out.println("* [sF]Delete file [" + path + "/" + name + "]");
				deleteFile(path + "/" + name);
			}
			else if (ftpFile.isSymbolicLink()) {

			}
			else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory(path);
	}

	// Check the path is exist; exist return true, else false.
	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		System.out.println("ftpFileArr:"+ftpFileArr);
		System.out.println("path:"+path);
		System.out.println("fileCount:"+ftpFileArr.length);
		for (FTPFile ftpFile : ftpFileArr) {
			System.out.println("ftpFile.isDirectory():"+ftpFile.isDirectory());
			System.out.println("ftpFile.getName():"+ftpFile.getName());
			System.out.println("path:"+path);
			if (ftpFile.isDirectory() || path.contains(ftpFile.getName())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// =======================================================================
	// == About file =====
	// Download and Upload file using
	// ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) better!
	// =======================================================================

	// #1. list & delete operation
	// Not contains directory
	public List<String> getFileList(String path) throws IOException {
		// listFiles return contains directory and file, it's FTPFile instance
		// listNames() contains directory, so using following to filer
		// directory.
		// String[] fileNameArr = ftpClient.listNames(path);
		System.out.println("要查找的路径为:"+path);
		
		FTPFile[] ftpFiles = ftpClient.listFiles(path);
        System.out.println("ftpFiles:"+ftpFiles.length);
		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	// #2. upload to ftp server
	// InputStream <------> byte[] simple and See API

	public boolean uploadFile(String fileName, String newName) throws IOException {
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(fileName);
			System.out.println("new name:"+newName);
			System.out.println("fileName:"+fileName);
			flag = ftpClient.storeFile(newName, iStream);
		}
		catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		}
		finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	public boolean uploadFile(String fileName) throws IOException {
		return uploadFile(fileName, fileName);
	}

	public boolean uploadFile(InputStream iStream, String newName) throws IOException {
		boolean flag = false;
		try {
			// can execute [OutputStream storeFileStream(String remote)]
			// Above method return's value is the local file stream.
			flag = ftpClient.storeFile(newName, iStream);
		}
		catch (IOException e) {
			flag = false;
			return flag;
		}
		finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	// #3. Down load

	public boolean download(String remoteFileName, String localFileName) throws IOException {
		boolean flag = false;
		System.out.println("远程文件："+remoteFileName);
		System.out.println("本地文件："+localFileName);
		File outfile = new File(localFileName);
		if(!outfile.exists()) {
			String parent=outfile.getParent();
			File dir=new File(parent);
			dir.mkdirs();
		}
		OutputStream oStream = null;
		try {
			System.out.println("文件开始下载：");
			oStream = new FileOutputStream(outfile);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
			System.out.println(flag);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
			flag = false;
			return flag;
		}
		finally {
			oStream.close();
		}
		return flag;
	}

	public InputStream downFile(String sourceFileName) throws IOException {
		return ftpClient.retrieveFileStream(sourceFileName);
	}

}