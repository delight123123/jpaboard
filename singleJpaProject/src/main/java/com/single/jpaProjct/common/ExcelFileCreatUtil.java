package com.single.jpaProjct.common;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.single.board.register.model.RegisterVO;

@Component
public class ExcelFileCreatUtil {
	
	private final static Logger logger=LoggerFactory.getLogger(ExcelFileCreatUtil.class);
	
	public SXSSFWorkbook makeExcelDown(List<RegisterVO> list) {
		logger.info("회원 목록 엑셀 파일로 만들기 list.size()={}",list.size());
		
		SXSSFWorkbook workbook=new SXSSFWorkbook();
		
		SXSSFSheet sheet=workbook.createSheet("회원목록");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		CellStyle cellStyle=workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
        //시트 열 너비 설정
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
       
        
        // 헤더 행 생
        Row headerRow = sheet.createRow(0);
        
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellStyle(cellStyle);
        headerCell.setCellValue("아이디");
        
        headerCell = headerRow.createCell(1);
        headerCell.setCellStyle(cellStyle);
        headerCell.setCellValue("이메일");
        
        headerCell = headerRow.createCell(2);
        headerCell.setCellStyle(cellStyle);
        headerCell.setCellValue("탈퇴일");
        
        // 과일표 내용 행 및 셀 생성
        Row bodyRow = null;
        Cell bodyCell = null;
        for(int i=0; i<list.size(); i++) {
            RegisterVO vo=list.get(i);
            
            // 행 생성
            bodyRow = sheet.createRow(i+1);
            
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(vo.getUserid());
            
            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(vo.getEmail1()+"@"+vo.getEmail2());
            
            String outdate="";
            if(vo.getOutDate()!=null && !vo.getOutDate().equals("")) {
            	outdate=sdf.format(vo.getOutDate());
            }
            
            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(outdate);
            
        }
        
        return workbook;
	}

}
