package com.h3c.idcloud.infrastructure.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 共通文件操作Util
 */
public final class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	/*@Autowired
	private static AttachmentService attachmentService = SpringContextHolder.getBean("attachmentServiceImpl");*/

    private static String filenameTemp;

    /**
     * 构造方法
     */
    private FileUtil() {

    }

    /**
     * 从系统Classpath中读取文件
     *
     * @param filePath 文件相对路径
     * @return StringBuffer
     */
    public static StringBuffer readFileByClasspath(String filePath) {

        StringBuffer sb = new StringBuffer();
        try {
            // 返回读取指定资源的输入流
            InputStream is = FileUtil.class.getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String s = "";
            try {
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
            } catch (IOException e) {
                log.error("文件读取失败!\n" + ExceptionUtils.getFullStackTrace(e));
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return sb;
    }

    /**
     * 根据文件地址读取文件
     *
     * @param filePath 文件位置
     * @return StringBuffer
     */
    public static StringBuffer readFileByFilePath(String filePath) {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            log.info("读取文件:" + filePath);
            log.info("文件内容为:" + sb.toString());
        } catch (FileNotFoundException e) {
            log.error("文件未找到!\n" + ExceptionUtils.getFullStackTrace(e));
        } catch (IOException e) {
            log.error("文件读取失败!\n" + ExceptionUtils.getFullStackTrace(e));
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb;
    }

    /**
     * 文件下载
     *
     * @param rsp      HttpServletResponse对象
     * @param filePath 文件在服务器上的路径
     * @param fileName 用户下载时,保存的文件名
     */
    public static InputStream fileDownLoad(HttpServletResponse rsp, String filePath,
                                           String fileName) {
        InputStream fis = null;
        OutputStream fos = null;

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
            // 请不要删除，以后有用
            // file.createTempFile(prefix, suffix, directory)
        }
        try {
            // 读取临时文件下载
            fis = new BufferedInputStream(new FileInputStream(filePath));
            // 每次读取的缓冲数组大小
            int readBuffer = 2048;
            // 缓冲数组
            byte[] buffer = new byte[readBuffer];
            int byteRead = 0;
            // 计算最后一次缓冲区的大小
            int lastBufferSize = fis.available() % readBuffer;
            int leftByte = -1;
            // 如果文件的大小,小于缓冲区的值
            if (fis.available() < readBuffer) {
                leftByte = fis.available();
            }
            // 清空response
            rsp.reset();
            // 设置response的Header
            rsp.addHeader("Content-Disposition", "attachment;filename="
                                                 + new String(fileName.getBytes("gb2312"),
                                                              "ISO-8859-1"));
            // rep.addHeader("Content-Length", "" + savedPath.length());
            rsp.setContentType("application/octet-stream;charset=GBK");

            fos = new BufferedOutputStream(rsp.getOutputStream());

            // 输出流写出到终端
            while ((byteRead = fis.read(buffer)) != -1) {
                // log.debug(fis.available());
                // 最后一次缓冲
                if (fis.available() == 0) {
                    // 文件大小是缓冲 整倍数
                    if (lastBufferSize == 0) {
                        fos.write(buffer, 0, buffer.length);
                    } else if (leftByte == -1) {
                        fos.write(buffer, 0, lastBufferSize);
                    }
                    // 文件大小小于缓冲
                    else {
                        fos.write(buffer, 0, leftByte);
                    }
                } else {
                    fos.write(buffer, 0, buffer.length);
                }
            }
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 在cxf中文件下载
     *
     * @param filePath 下载文件的地址
     * @param fileName 下载文件名称
     */
    public static ResponseBuilder fileDownLoadInCxf(String filePath, String fileName) {

        File file = new File(filePath);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=" + fileName + "");

        return response;
    }

    /**
     * 文件下载
     *
     * @param rsp     HttpServletResponse对象
     * @param iStream 文件在服务器上的路径
     */
    public static void ImageOutput(HttpServletResponse rsp, InputStream iStream) {
        InputStream fis = null;
        OutputStream fos = null;

        try {
            // 读取临时文件下载
            fis = new BufferedInputStream(iStream);
            // 每次读取的缓冲数组大小
            int readBuffer = 2048;
            // 缓冲数组
            byte[] buffer = new byte[readBuffer];
            int byteRead = 0;
            // 计算最后一次缓冲区的大小
            int lastBufferSize = fis.available() % readBuffer;
            int leftByte = -1;
            // 如果文件的大小,小于缓冲区的值
            if (fis.available() < readBuffer) {
                leftByte = fis.available();
            }
            // 清空response
            rsp.reset();
            // 设置response的Header
            //rsp.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
            // rep.addHeader("Content-Length", "" + savedPath.length());
            rsp.setContentType("application/octet-stream;charset=GBK");

            fos = new BufferedOutputStream(rsp.getOutputStream());

            // 输出流写出到终端
            while ((byteRead = fis.read(buffer)) != -1) {
                // log.debug(fis.available());
                // 最后一次缓冲
                if (fis.available() == 0) {
                    // 文件大小是缓冲 整倍数
                    if (lastBufferSize == 0) {
                        fos.write(buffer, 0, buffer.length);
                    } else if (leftByte == -1) {
                        fos.write(buffer, 0, lastBufferSize);
                    }
                    // 文件大小小于缓冲
                    else {
                        fos.write(buffer, 0, leftByte);
                    }
                } else {
                    fos.write(buffer, 0, buffer.length);
                }
            }
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Integer> getImageSizeByBufferedImage(InputStream inputStream)
            throws IOException {
        Map<String, Integer> imageInfo = new HashMap<String, Integer>();
        BufferedImage sourceImg = javax.imageio.ImageIO.read(inputStream);
        imageInfo.put("imageWidth", sourceImg.getWidth());
        imageInfo.put("imageHeight", sourceImg.getHeight());
        return imageInfo;
    }

    /***
     * 得到file文件的详细信息并存入pojo，同时上传至指定文件夹
     *
     * @param attachments  附件集合
     *
     * @param uploadPath   上传路径
     *
     * @throws IOException
     *
     * @return list 附件对象id集合

    public static List<String> uploadAttachmentFiles(List<Attachment> attachments) throws IOException {
    List<String>  list = new ArrayList<String>();
    if(attachments != null && attachments.size() > 0 ){
    for(Attachment attachment : attachments){

    Attachments attach = new Attachments();
    // 附件原始名称
    String name = attachment.getContentDisposition().getParameter("filename");
    String oriName = new String(name.getBytes("iso-8859-1"),"UTF-8");
    attach.setOriginalName(oriName);
    // 附件后缀名
    String extName = StringUtils.substringAfterLast(oriName, ".");
    attach.setExtName(extName);
    // 附件名称
    String attachmentName = UUID.randomUUID().toString();
    attach.setAttachmentName(attachmentName);
    // 上传路径
    String dataPath = DateFormatUtils.format(new Date(), "yyyy-MM/dd");
    String attachmentUrl =  dataPath + "/" + attachmentName + ("".equals(extName) ? "" : "." + extName);
    attach.setAttachmentUrl(attachmentUrl);
    // 文件大小
    DataHandler dh = attachment.getDataHandler();
    InputStream ins = dh.getInputStream();
    Long attachmentSize = (long) ins.available();
    attach.setAttachmentSize(attachmentSize);
    // 上传时间
    attach.setUploadDate(new Date());
    WebUtil.prepareInsertParams(attach);

    // 获取系统配置文件中的上传目录
    String uploadPath = com.hptsic.cloud.common.utils.PropertiesUtil.getProperty("upload.path");

    String finalPath = uploadPath + attachmentUrl;

    File saveFile = new File(finalPath);
    // 判断文件夹是否存在，不存在则创建
    if (!saveFile.getParentFile().exists()) {
    saveFile.getParentFile().mkdirs();
    }

    writeToFile(ins,finalPath);

    // 插入数据库
    attachmentService.insertSelective(attach);

    list.add(attach.getAttachmentSid());
    }

    }

    return list;
    }
     */
	/*public static List<String> uploadOnlyAttachmentFile(List<Attachment> attachments) throws IOException {
		List<String>  list = new ArrayList<String>(); 
		if(attachments != null && attachments.size() > 0 ){
				Attachments attach = new Attachments();
				// 附件原始名称
				String name = attachments.get(0).getContentDisposition().getParameter("filename");
				String oriName = new String(name.getBytes("iso-8859-1"),"UTF-8");
				attach.setOriginalName(oriName);
				// 附件后缀名
				String extName = StringUtils.substringAfterLast(oriName, ".");
				attach.setExtName(extName);
				// 附件名称
				String attachmentName = UUID.randomUUID().toString();
				attach.setAttachmentName(attachmentName);
				// 上传路径
				String dataPath = DateFormatUtils.format(new Date(), "yyyy-MM/dd");
				String attachmentUrl =  dataPath + "/" + attachmentName + ("".equals(extName) ? "" : "." + extName);
				attach.setAttachmentUrl(attachmentUrl);
				// 文件大小
		        DataHandler dh = attachments.get(0).getDataHandler(); 
		        InputStream ins = dh.getInputStream();
		        Long attachmentSize = (long) ins.available();
		        attach.setAttachmentSize(attachmentSize);
		        // 上传时间
		        attach.setUploadDate(new Date());
		        WebUtil.prepareInsertParams(attach);
		        
		        // 获取系统配置文件中的上传目录
		        String uploadPath = com.hptsic.cloud.common.utils.PropertiesUtil.getProperty("upload.path");
		        
		        String finalPath = uploadPath + attachmentUrl;
		        
		        File saveFile = new File(finalPath);
				// 判断文件夹是否存在，不存在则创建
				if (!saveFile.getParentFile().exists()) {
					saveFile.getParentFile().mkdirs();
				}
				
				writeToFile(ins,finalPath);
				
				// 插入数据库
				attachmentService.insertSelective(attach);
				
				list.add(attach.getAttachmentSid());
			
			
		}
		
		return list;
	}*/

    /**
     * 导出文件
     */
    @SuppressWarnings("unused")
    private static void writeToFile(InputStream ins, String path) {
        try {
            OutputStream out = new FileOutputStream(new File(path));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = ins.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/*public static boolean creatTxtFile(String name,Attachments attach) throws IOException {
		boolean flag = false;
		WebUtil.prepareInsertParams(attach);
		int result = attachmentService.insertSelective(attach);
		if(1==result){
			String path = com.hptsic.cloud.common.utils.PropertiesUtil.getProperty("upload.path");
			String uploadPath = path+attach.getAttachmentUrl();
			File filename = new File(uploadPath);
			if (!filename.getParentFile().exists()) {
				filename.getParentFile().mkdirs();
			}
			 OutputStream out = new FileOutputStream(new File(uploadPath));  
			 int read = 0; 
			 byte[] bytes = new byte[1024];
			 out.write(bytes, 0, read);
			 out.flush();  
		     out.close(); 
		     flag = true;
		}
		return flag;
	}*/

    public static boolean writeTxtFile(String content, File fileName) throws Exception {
        RandomAccessFile mm = null;
        boolean flag = false;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mm != null) {
                mm.close();
            }
        }
        return flag;
    }

    /**
     * 删除一个文件
     *
     * @param filePath 文件路径
     */
    public boolean deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }
}

