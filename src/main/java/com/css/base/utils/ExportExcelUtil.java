package com.css.base.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import jxl.report.ReportEnginer;

public class ExportExcelUtil {

	@SuppressWarnings("deprecation")
	public static void excelAndSvg(Map<String, Object> context,
			String templatePath, String filePrefix) {
		// 创建文件夹目录
		String path = CurrentUtil.getRequest().getRealPath("/");
		String sourceFilePath = path + File.separator + "exportExcel"; // 源文件路径

		ReportEnginer enginer = new ReportEnginer();
		InputStream templateIS = ExportExcelUtil.class
				.getResourceAsStream(templatePath);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = filePrefix + sdf.format(new Date()) + ".xls";
			String destFile = sourceFilePath + File.separator + fileName;
			File file = new File(sourceFilePath + File.separator);
			if (!file.exists()) {
				file.mkdirs();
			}
			OutputStream destFileOS = new FileOutputStream(destFile);

			enginer.excute(templateIS, context, destFileOS);
			destFileOS.flush();
			destFileOS.close();
			downloadFile(destFile, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description 方法：下载文件
	 * @param filepath
	 *            文件所在路径
	 * @param filename
	 *            文件名(filename + '.' + postfix)
	 * @throws IOException
	 */
	public static void downloadFile(String filepath, String filename)
			throws IOException {
		HttpServletResponse response = CurrentUtil.getResponse();
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = new String(filename.getBytes(), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + name);
		String filedir = filepath;
		BufferedInputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filedir));
			out = response.getOutputStream();
			byte[] buff = new byte[1024];
			int i = 0;
			while ((i = in.read(buff)) > 0) {
				out.write(buff, 0, i);
				out.flush();
			}
		} catch (Exception e) {
			System.out.println("客户端下载过程中异常中断下载操作！！");
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	/**
	 * @description 方法：下载文件
	 * @param filepath
	 *            文件所在路径
	 * @param filename
	 *            文件名(filename + '.' + postfix)
	 * @throws IOException
	 */
	public static void downloadFile(ByteArrayOutputStream excelOS, String filename)
			throws IOException {
		HttpServletResponse response = CurrentUtil.getResponse();
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = new String(filename.getBytes(), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + name);
		BufferedInputStream in = null;
		OutputStream out = null;
		try {
			byte[] bts=excelOS.toByteArray();
			ByteArrayInputStream excelIS=new ByteArrayInputStream(bts);
			in = new BufferedInputStream(excelIS);
			out = response.getOutputStream();
			byte[] buff = new byte[1024];
			int i = 0;
			while ((i = in.read(buff)) > 0) {
				out.write(buff, 0, i);
				out.flush();
			}
		} catch (Exception e) {
			System.out.println("客户端下载过程中异常中断下载操作！！");
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	public static void excelMemory(Map<String, Object> context,
			String templatePath, String filePrefix) {
		ReportEnginer enginer = new ReportEnginer();
		InputStream templateIS = ExportExcelUtil.class
		.getResourceAsStream(templatePath);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String fileName = filePrefix + sdf.format(new Date()) + ".xls";
			ByteArrayOutputStream excelOS=new ByteArrayOutputStream();
			
			enginer.excute(templateIS, context, excelOS);
			downloadFile(excelOS, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}