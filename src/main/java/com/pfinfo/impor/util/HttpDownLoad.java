package com.pfinfo.impor.util;

import static com.pfinfo.impor.util.ConstantConfig.SAVEPATH;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pfinfo.impor.exception.ImportExcelBaseException;

public class HttpDownLoad {

	public static String downLoadFormUrl(String urlStr) throws ImportExcelBaseException{
		return downLoadFormUrl(urlStr, SAVEPATH);
	}
	
	public static String downLoadFormUrl(String urlStr,String savepath) throws ImportExcelBaseException{
		return downLoadFormUrl(urlStr,savepath,3);
	}
	
	public static String downLoadFormUrl(String urlStr,String savepath,int time) throws ImportExcelBaseException{
		String suffix = StringUtil.getFileSuffix(urlStr);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		String fileName = LocalDateTime.now().format(formatter)+suffix;
		try{
			String localPath = downLoadFormUrl(urlStr, fileName, savepath, time);
			return localPath;
		}catch(IOException e){
			throw new ImportExcelBaseException(e);
		}
	}

	/**
	 * 
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @param time
	 * @return
	 * @throws IOException
	 */
	public static String downLoadFormUrl(String urlStr,String fileName,String savePath,int time) throws IOException{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//设置超时时间
		conn.setConnectTimeout(time * 1000);
		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; Windows NT; DigExt)");
		//得到输入流
		try(InputStream inputStream = conn.getInputStream()){
			//取得字节数组
			byte[] getData = readInputStream(inputStream);
			//创建文件夹
			File saveDir = new File(savePath);
			if(saveDir.exists()){
				saveDir.mkdir();
			}
			//构建文件路径
			String finalPath = saveDir+File.separator+fileName;
			//写到服务器
			File file = new File(saveDir+File.separator+fileName);
			try(FileOutputStream fos = new FileOutputStream(file)){
				fos.write(getData);
			}
			//返回服务器文件路径
			return finalPath;
		}
	}
	
	public static byte[] readInputStream(InputStream inputStream) throws IOException{
		byte[] buffer = new byte[1024];
		int len = 0;
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
			while((len=inputStream.read(buffer))!=-1){
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		}
	}
}
