package indi.qiaolin.net.ftp.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import sun.net.ftp.FtpClient;

/**
 *  Commons-net  ftp文件工具类 
 * @author qiaolin
 * @date 2018年3月22日
 *
 **/

@Slf4j
public class FTPTest {
	// FTP 客户端
	private FTPClient ftpClient;
	
	@Before
	public void init() throws Exception {
		// 创建客户端对象
		ftpClient = new FTPClient();
		// 设置链接的IP、端口
		ftpClient.connect("192.168.1.214", 21);
		// 登陆
		ftpClient.login("Administrator", "Liqiaolin");
		
		log.debug("ftpClient connented -> {}", ftpClient.isConnected());
	}
	
	/**
	 *  测试FTP上传文件
	 * @throws Exception 
	 */
	@Test
	public void testUpload() throws Exception {
		
	}
	
	/**
	 *  测试FTP下载文件
	 * @throws Exception 
	 */
	@Test
	public void testDownload() throws Exception {
		String fileName = "/5aa91a30-84bc-3536-a606-d25945440f92.bmp";
		
		FTPFile[] listFiles = ftpClient.listFiles(fileName);
		for (FTPFile ftpFile : listFiles) {
			log.debug("name {} , type {} , link{} ", ftpFile.getName(),ftpFile.getType(),ftpFile.getLink());
		}
		
		log.debug(" < ------------------- > ");
		
		FTPFile[] listFiles2 = ftpClient.listFiles();
		for (FTPFile ftpFile : listFiles2) {
			log.debug("name {} , type {} , link{} ", ftpFile.getName(),ftpFile.getType(),ftpFile.getLink());
		}
		
		 /*OutputStream os = new FileOutputStream("e:aa.bmp");
		 int len = -1;
		 byte[] b = new byte[1024];
		 while((len = fileStream.read(b)) > -1) {
			 os.write(b, 0, len);
		}
		 fileStream.close();
		 os.close();*/
	}
	
	/**
	 *  测试FTP删除文件
	 */
	@Test
	public void testDelete() {
		
	}
	
	
	@Test
	public void testRename() throws Exception  {
		String from = "/5aa91a30-84bc-3536-a606-d25945440f92.bmp";
		String to = "/android.bmp";
		try {
			boolean rename = ftpClient.rename(from, to);
			log.debug("{} rename {} is {}",from , to ,rename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void destroy() throws Exception {
		if(ftpClient.isConnected()) {
			ftpClient.logout();
			ftpClient.disconnect();
		}
	}
	
}
