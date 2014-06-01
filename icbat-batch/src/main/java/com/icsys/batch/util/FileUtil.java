package com.icsys.batch.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.log4j.Logger;

public class FileUtil {
	private static Logger LOG = Logger.getLogger(FileUtil.class);

	public static boolean buildDirectory(File file) {
		return file.exists() || file.mkdirs();
	}

	public static void copyInputStream(InputStream in, OutputStream out,
			int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		int len = in.read(buffer);
		while (len >= 0) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		// in.close();
		out.close();
	}

	/**
	 * 解压tar包
	 * 
	 * @param theFile 需要解压的tar文件
	 * @param targetDir tar文件解压后存放目录（绝对路径）
	 * @return
	 * @throws IOException
	 */
	public static File unpackTarArchive(File sourceFile, File targetDir)
			throws IOException {
		if (!sourceFile.exists()) {
			throw new IOException(sourceFile.getAbsolutePath()
					+ " does not exist");
		}
		if (!buildDirectory(targetDir)) {
			throw new IOException("Could not create directory: " + targetDir);
		}
		TarArchiveInputStream tis = new TarArchiveInputStream(
				new FileInputStream(sourceFile));
		TarArchiveEntry entry = null;
		while ((entry = tis.getNextTarEntry()) != null) {
			File file = new File(targetDir, File.separator + entry.getName());
			if (!buildDirectory(file.getParentFile())) {
				throw new IOException("Could not create directory: "
						+ file.getParentFile());
			}
			if (!entry.isDirectory()) {
				copyInputStream(tis, new BufferedOutputStream(
						new FileOutputStream(file)), (int) entry.getSize());
			} else {
				if (!buildDirectory(file)) {
					throw new IOException("Could not create directory: " + file);
				}
			}
		}
		tis.close();
		return sourceFile;
	}

	/**
	 * 解压gz包
	 * 
	 * @param theFile
	 * @param targetDir
	 * @throws IOException
	 */
	public static void unpackGzArchive(File theFile, File targetDir)
			throws IOException {
		FileInputStream fin = new FileInputStream(theFile);
		BufferedInputStream in = new BufferedInputStream(fin);
		FileOutputStream out = new FileOutputStream(targetDir);
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
		final byte[] buffer = new byte[1024];
		int n = 0;
		while (-1 != (n = gzIn.read(buffer))) {
			out.write(buffer, 0, n);
		}
		out.close();
		gzIn.close();
	}

	/**
	 * 解压ZIP包
	 * 
	 * @param theFile
	 * @param targetDir
	 * @return the file
	 * @throws IOException
	 */
	public static File unpackArchive(File theFile, File targetDir)
			throws IOException {
		if (!theFile.exists()) {
			throw new IOException(theFile.getAbsolutePath() + " does not exist");
		}
		if (!buildDirectory(targetDir)) {
			throw new IOException("Could not create directory: " + targetDir);
		}
		ZipFile zipFile;
		zipFile = new ZipFile(theFile);
		for (Enumeration entries = zipFile.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			File file = new File(targetDir, File.separator + entry.getName());
			// Take the sledgehammer approach to creating directories
			// to work around ZIP's that incorrectly miss directories
			if (!buildDirectory(file.getParentFile())) {
				throw new IOException("Could not create directory: "
						+ file.getParentFile());
			}
			if (!entry.isDirectory()) {
				copyInputStream(zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(file)),
						(int) entry.getSize());
			} else {
				if (!buildDirectory(file)) {
					throw new IOException("Could not create directory: " + file);
				}
			}
		}
		zipFile.close();
		return theFile;
	}

	/**
	 * Zip up a directory
	 * 
	 * @param directory
	 * @param zipName
	 * @throws IOException
	 */
	public static void zipDir(String directory, String zipName)
			throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName));
		String path = "";
		zipDir(directory, zos, path);
		zos.close();
	}

	private static void zipDir(String directory, ZipOutputStream zos,
			String path) throws IOException {
		File zipDir = new File(directory);
		// get a listing of the directory content
		String[] dirList = zipDir.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		// loop through dirList, and zip the files
		for (int i = 0; i < dirList.length; i++) {
			System.out.println(dirList[i]);
			File f = new File(zipDir, dirList[i]);
			if (f.isDirectory()) {
				String filePath = f.getPath();
				zipDir(filePath, zos, path + f.getName() + "/");
				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			try {
				ZipEntry anEntry = new ZipEntry(path + f.getName());
				anEntry.setSize(fis.available());
				zos.putNextEntry(anEntry);
				bytesIn = fis.read(readBuffer);
				while (bytesIn != -1) {
					zos.write(readBuffer, 0, bytesIn);
					bytesIn = fis.read(readBuffer);
				}
				zos.closeEntry();
			} finally {
				fis.close();
			}
		}
	}

	public static void tarDir(String directory, String tarName)
			throws IOException {
		TarArchiveOutputStream tarOut = new TarArchiveOutputStream(
				new FileOutputStream(tarName));
		String path = "";
		tarDir(directory, tarOut, path);
		tarOut.close();
	}

	private static void tarDir(String directory, TarArchiveOutputStream tos,
			String path) throws IOException {
		File tarDir = new File(directory);
		String[] dirList = tarDir.list();
		byte[] readBuffer = new byte[2156];
		int bytesIn = 0;
		for (int i = 0; i < dirList.length; i++) {
			File f = new File(tarDir, dirList[i]);
			System.out.println(dirList[i]);
			if (f.isDirectory()) {
				String filePath = f.getPath();
				tarDir(filePath, tos, path + f.getName() + "/");
				continue;
			}
			FileInputStream fis = new FileInputStream(f);
			try {
				TarArchiveEntry entry = new TarArchiveEntry(path + f.getName());
				entry.setSize(fis.available());
				tos.putArchiveEntry(entry);
				bytesIn = fis.read(readBuffer);
				while (bytesIn != -1) {
					tos.write(readBuffer, 0, bytesIn);
					bytesIn = fis.read(readBuffer);
				}
				tos.closeArchiveEntry();
			} finally {
				fis.close();
			}
		}
	}

	/**
	 * 打*tar.Z包(路径皆为绝对路径)
	 * 
	 * @param directory
	 *            要打成tar.z包的文件的目录(一个或多个文件)
	 * @param tarZFileName
	 *            要打成tar.z包的tar文件的路径（包含文件名）
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void tarZDir(String directory, String tarZFileName)
			throws IOException, InterruptedException {
		tarDir(directory, tarZFileName);
		String cmd = "compress " + tarZFileName;
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();
	}

	/**
	 * @param tarZFilePath
	 *            要解压的*tar.Z包的路径(包含文件名)
	 * @param targetDir
	 *            解压后要存放文件的路径
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void unTarZDir(String tarZFilePath, File targetDir)
			throws IOException, InterruptedException {
		String cmd = "uncompress " + tarZFilePath;
		Process process = Runtime.getRuntime().exec(cmd);
		process.waitFor();
		unpackTarArchive(new File(tarZFilePath.replace(".Z", "")), targetDir);
	}

	/**
	 * 文件夹模糊过滤器
	 * 
	 * @param path
	 *            文件夹绝对路径
	 * @param fileName
	 *            要过滤的文件名字
	 * @param type
	 *            0-过滤类型 保留文件名含有fileName的文件 1-过滤掉文件名包含为fileName的文件
	 */
	public static void fileFilter(String path, String fileName, int type) {
		File oldFile = new File(path);
		// 保留文件命中含有fileName的文件
		if (oldFile.isDirectory()) {
			File[] files = oldFile.listFiles();
			for (File file : files) {
				System.out.println(file.getName());
				if (file.isDirectory()) {
					fileFilter(file.getAbsolutePath(), fileName, type);
				} else {
					if (!file.getName().contains(fileName) && type == 0) {
						file.delete();// 保留文件名含有fileName的文件
					} else if (file.getName().contains(fileName) && type == 1) {
						file.delete();// 1-过滤掉文件名包含fileName的文件
					}
				}
			}
		}
		System.out.println("文件夹过滤成功!");
	}

	/**
	 * 解压一个目录下的所有的*.Z包
	 * 
	 * @param path
	 *            目录的绝对路径
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void unZ(String pathName) throws IOException,
			InterruptedException {
		File fileDir = new File(pathName);

		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".Z")) {
					String cmd = "uncompress " + pathName + "/"
							+ file.getName();
					Process process = Runtime.getRuntime().exec(cmd);
					LOG.info("==========cmd==========:" + cmd);
					process.waitFor();
				}
			}
		}

	}

	/**
	 * 文件的复制 lyq
	 * 
	 * @param rootFile源文件
	 * @param newFile复制后的新文件
	 * @throws IOException
	 */
	public static void copyFile(File rootFile, File newFile) throws IOException {
		File newFileParent = newFile.getParentFile();
		if (!newFileParent.exists()) {
			newFileParent.mkdirs();
		}
		FileInputStream input = new FileInputStream(rootFile);
		FileChannel channelInput = input.getChannel();
		FileOutputStream output = new FileOutputStream(newFile);
		FileChannel channelOut = output.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		while (channelInput.read(byteBuffer) != -1) {
			byteBuffer.flip();
			channelOut.write(byteBuffer);
			byteBuffer.clear();
		}
		input.close();
		channelInput.close();
		output.close();
		channelOut.close();
		LOG.info("=====文件复制成功=====");
	}

	/**
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void delAll(String path) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			LOG.info("===========文件夹" + path + "不存在================");
			return;
		}// 文件夹不存在不存在
		boolean rslt = true;// 保存中间结果
		if (!(rslt = f.delete())) {// 先尝试直接删除
			// 若文件夹非空。枚举、递归删除里面内容
			File subs[] = f.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory())
					delAll(subs[i].getAbsolutePath());// 递归删除子文件夹内容
				rslt = subs[i].delete();// 删除子文件夹本身
			}
			rslt = f.delete();// 删除此文件夹本身
		}
		if (!rslt) {
			return;
		}
		LOG.info("===========删除文件夹" + path + "成功==============");
	}

	/**
	 * 已独占的方式打开一个文件,并为这个文件生成一个正在执行的标识文件
	 * 
	 * @param source
	 *            绝对路径 包含文件名
	 */
	public void lockAndMakeExcuteFile(String sourceFilePath) {
		FileOutputStream fos=null;
		try {
			File sourceFile=new File(sourceFilePath);
			RandomAccessFile raf = new RandomAccessFile(sourceFile, "rw");
			FileChannel fc = raf.getChannel();
			FileLock lock = fc.tryLock();
			 fos = new FileOutputStream(sourceFile.getAbsolutePath()+".executing");
			byte[] info = "正在执行标识文件".getBytes();
			fos.write(info);
			fos.flush();
			lock.release();
			raf.close();
			LOG.info("========正在执行的标识文件生成========");
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		FileUtil fileUtil = new FileUtil();
		fileUtil.lockAndMakeExcuteFile("F:\\对账");
		new Thread() {
			public void run() {
				while(true) {
				try {
					Thread.sleep(100);
					FileReader fr = new FileReader(new File("F:\\对账\\ICCheck_20110821_1"));
		              int c;
		              while ( (c = fr.read()) != -1) {
		                System.out.println( (char) c);
		              }
		              fr.close();
		              break; 
				} catch (Exception e) {
					e.getStackTrace();
				}
			
			}
				}
		}.start();
	}
}
