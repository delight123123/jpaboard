package com.single.jpaProjct.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.single.jpaProjct.board.domain.UpfileListVO;

@Component
public class FileUploadUtil {
	private final static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	
	@Value("${file.upload-dir}")
	private String path;

	public List<UpfileListVO> fileupload(MultipartFile[] uploadfiles, HttpServletRequest request, HttpSession session) {
		logger.info("파일 업로드 시작");

		List<UpfileListVO> list = new ArrayList<UpfileListVO>();

		for (int i = 0; i < uploadfiles.length; i++) {
			MultipartFile upfile = uploadfiles[i];
			logger.info("index=" + i + "번째 파일업로드 진행중");

			if (!upfile.isEmpty()) {
				// 변경전 (원래) 파일명
				String originFileName = upfile.getOriginalFilename();
				// 변경된 파일명
				String fileName = getUniqueFileName(originFileName);
				// 파일 크기
				long fileSize = upfile.getSize();

				// 업로드 처리
				// 업로드할 경로 구하기
				String upPath = getFilePath(request, session);

				File file = new File(upPath, fileName);

				UpfileListVO vo = new UpfileListVO();
				vo.setFileName(fileName);
				vo.setFilesize(fileSize);
				vo.setOriginalFileName(originFileName);

				try {
					upfile.transferTo(file);
					list.add(vo);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public List<UpfileListVO> fileupload(HttpServletRequest request, HttpSession session) {

		logger.info("파일 업로드 시작");
		
		
		// 파일 업로드 처리
		MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;

		Map<String, MultipartFile> fileMap = multiReq.getFileMap();
		
		List<UpfileListVO> list = new ArrayList<UpfileListVO>();

		Iterator<String> iter=fileMap.keySet().iterator();
		
		while(iter.hasNext()) {
			String key=iter.next();
			MultipartFile upfile=fileMap.get(key);
			
			//업로드된 경우
			if(!upfile.isEmpty()) {
				// 변경전 (원래) 파일명
				String originFileName = upfile.getOriginalFilename();
				// 변경된 파일명
				String fileName = getUniqueFileName(originFileName);
				// 파일 크기
				long fileSize = upfile.getSize();

				// 업로드 처리
				// 업로드할 경로 구하기
				String upPath = getFilePath(request, session);				

				File file = new File(upPath, fileName);

				UpfileListVO vo = new UpfileListVO();
				vo.setFileName(fileName);
				vo.setFilesize(fileSize);
				vo.setOriginalFileName(originFileName);

				try {
					upfile.transferTo(file);
					list.add(vo);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}		
				
			}
		}
		
		return list;
		
	}

	
	
	
	public String getFilePath(HttpServletRequest request, HttpSession session) {
		// 업로드할 경로 구하기
		//String path = "";
		String userid = (String) session.getAttribute("userid");
		FileuploadProperties fileProper = new FileuploadProperties();
		//path = fileProper.getUploadDir();
		//path = request.getSession().getServletContext().getRealPath(path) + "/" + userid;
		path = path + "/" + userid;
		path=request.getSession().getServletContext().getRealPath(path);
		File folder=new File(path);
		if(!folder.exists()) {
			folder.mkdirs();
		}

		// config.getServletContext().getRealPath(upDir);
		logger.info("업로드 경로  path={}", path);

		return path;
	}

	public String getUniqueFileName(String originFileName) {
		// 파일명에 현재시간(년월일시분초밀리초)을 붙여서 파일명 변경
		// abc.txt => abc20191224120350123.txt
		int idx = originFileName.lastIndexOf(".");
		String fName = originFileName.substring(0, idx); // abc
		String ext = originFileName.substring(idx); // .txt

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = sdf.format(d);

		String fileName = fName + time + ext;
		logger.info("변경된 fileName={}", fileName);

		return fileName;
	}
	
	
	public boolean fileDel(List<UpfileListVO> list, HttpSession session,HttpServletRequest request) {
		boolean res=false;
		
		String path=getFilePath(request, session);
		
		for(UpfileListVO vo:list) {
			String fileName=vo.getFileName();
			
			File delfile=new File(path, fileName);
			if(delfile.exists()||delfile.canRead()) {
				res=delfile.delete();
			}//if
		}//for
		
		
		return res;
	}
	
	
}
