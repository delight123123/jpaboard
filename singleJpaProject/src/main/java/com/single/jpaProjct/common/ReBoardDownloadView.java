package com.single.jpaProjct.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class ReBoardDownloadView extends AbstractView{

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		File file=(File)model.get("file");
		if(file==null || !file.canRead() || !file.exists()) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print("<script type='text/javascript'>");
			out.print("alert('파일이 존재하지 않거나 손상되었습니다');");
			out.print("history.back();");
			out.print("</script>");
			return;
		}
		
		String fileName=file.getName();
		String fName=new String(fileName.getBytes("euc-kr"),"8859_1");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment;filename="+fName);
		
		OutputStream os=response.getOutputStream();
		FileInputStream fis=null;
		fis=new FileInputStream(file);
		
		FileCopyUtils.copy(fis, os);
		
		fis.close();
		
	}

}
