package com.css.base.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


/**
 * <h1>ZIP压缩工具</h1></br>
 *
 * @author 中软信息系统工程有限公司 
 * @version 1.0
 * 
 */
public class ZipUtils {

	private static final String EXT = ".zip";
	private static final String BASE_DIR = "";
	private static final String SEPARATOR = File.separator;
	private static final int BUFFER = 1024;

	
	/**
	 * <h1>压缩文件至源目录</h1></br>
	 * 
	 * @param srcFile 源文件
	 * @throws Exception
	 */
	public static void compress(File[] srcFile) throws Exception {
		compress(srcFile, srcFile[0].getParent());
	}

	/**
	 * <h1>压缩多文件至指定目录</h1></br>
	 * 
	 * @param srcFile 源文件
	 * @param destPath 目标路径
	 * @throws Exception
	 */
	public static void compress(File[] srcFile, String destPath) throws Exception {
		Date date = new Date();
		//以日期作为压缩包文件名
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSSS");
		String fileName = fmt.format(date) + EXT; 
		File destFile = new File(destPath+SEPARATOR+fileName);
		// 对输出文件做CRC32校验
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
		ZipOutputStream zos = new ZipOutputStream(cos);
		for (int i = 0; i < srcFile.length; i++) {
			if (srcFile[i].isDirectory()) {
				compressDir(srcFile[i], zos, BASE_DIR);
			} else {
				compressFile(srcFile[i], zos, BASE_DIR);
			}
		}
		zos.flush();
		zos.close();
	}

	/**
	 * 压缩目录
	 * 
	 * @param dir
	 * @param zos
	 * @param basePath
	 * @throws Exception
	 */
	private static void compressDir(File dir, ZipOutputStream zos, String basePath) throws Exception {
		File[] files = dir.listFiles();
		// 构建空目录
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + SEPARATOR);
			zos.putNextEntry(entry);
			zos.closeEntry();
		}
		for (File file : files) {
			// 递归压缩
			if (file.isDirectory()) {
				compressDir(file, zos, basePath + dir.getName() + SEPARATOR);
			} else {
				compressFile(file, zos, basePath + dir.getName() + SEPARATOR);
			}
		}
	}

	/**
	 * 文件压缩
	 * 
	 * @param file 待压缩文件
	 * @param zos ZipOutputStream
	 * @param dir 压缩文件中的当前路径
	 * @throws Exception
	 */
	private static void compressFile(File file, ZipOutputStream zos, String dir) throws Exception {
		ZipEntry entry = new ZipEntry(dir + file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();
		zos.closeEntry();
	}
	
/*	*//**
	 * 多文件压缩
	 * 
	 * @param file 待压缩文件
	 * @param zos ZipOutputStream
	 * @param dir 压缩文件中的当前路径
	 * @throws Exception
	 *//*
	private static void compressFile(File[] file, ZipOutputStream zos, String dir) throws Exception {
		for (int i = 0; i < file.length; i++) {
			File f = file[i];
			ZipEntry entry = new ZipEntry(dir + f.getName());
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
		}
		zos.closeEntry();
	}*/
	
	
	/**
	 * <h1>解压缩文件至源目录</h1></br>
	 * 
	 * @param srcFile 源文件
	 * @throws Exception
	 */
	public static void decompress(File srcFile) throws Exception {
		String basePath = srcFile.getParent();
		decompress(srcFile, basePath);
	}

	/**
	 * <h1>解压缩文件至指定目录</h1></br>
	 * 
	 * @param srcFile 源文件
	 * @param destPath 目标路径
	 * @throws Exception
	 */
	public static void decompress(File srcFile, String destPath) throws Exception {
		File destFile = new File(destPath);
		CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());
		ZipInputStream zis = new ZipInputStream(cis);
		ZipEntry entry = null;
		while ((entry = zis.getNextEntry()) != null) {
			// 文件
			String dir = destFile.getPath() + SEPARATOR + entry.getName();
			File dirFile = new File(dir);
			// 文件检查
			checkParentFile(dirFile);
 			if (entry.isDirectory()) {
 				dirFile.mkdirs();
 			} else {
 				decompressFile(dirFile, zis);
 			}
 			zis.closeEntry();
 		}
		zis.close();
	}

	/**
	 * 当父目录不存在时创建目录
	 * 
	 * @param dirFile
	 */
	private static void checkParentFile(File dirFile) {
		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {
			// 递归寻找上级目录
			checkParentFile(parentFile);
			parentFile.mkdir();
		}
	}

	/**
	 * 文件解压缩
	 * 
	 * @param destFile 目标文件
	 * @param zis ZipInputStream
	 * @throws Exception
	 */
	private static void decompressFile(File destFile, ZipInputStream zis) throws Exception {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = zis.read(data, 0, BUFFER)) != -1) {
			bos.write(data, 0, count);
		}
		bos.close();
	}

}
