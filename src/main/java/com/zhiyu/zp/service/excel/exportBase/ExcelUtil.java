package com.zhiyu.zp.service.excel.exportBase;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel导出工具类
 * @author wdj
 *
 */

public class ExcelUtil {

	private static float heigth_pix = 40.7f;
	
	private static float width_pix = 150f;
	
	/**
     * 创建excel文档，
     * [@param] list 数据，Map数据列表
     * @param keys list中map的key数组集合，也就是键值集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("数据导出");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (width_pix * heigth_pix));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 12);
        f.setFontName("宋体");
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i <= list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i-1).get(keys[j]) == null?" ": list.get(i-1).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
    
    /**
     * 创建excel文档，
     * [@param] list 数据，Map数据列表
     * @param keys list中map的key数组集合，也就是键值集合
     * @param columnNames excel的列名
     * @throws IOException 
     * */
	public static Workbook createWorkBookCombineWithPicture(List<Map<String, Object>> list,String []keys,String columnNames[],String excludeColumnNames[],String flagFileRoot) throws IOException {
        // 创建excel工作簿
    	HSSFWorkbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        HSSFSheet sheet = wb.createSheet("数据导出");
        //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();   
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
        	if(i==0){
        		sheet.setColumnWidth( i,  (256*12+184));
        	}else{
        		sheet.setColumnWidth( i, (8*256+184));
        	}
        }

        // 创建第一行
//        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontName("宋体");
        f.setFontHeightInPoints((short) 12);
        f.setColor(IndexedColors.BLACK.getIndex());

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 12);
        f2.setFontName("宋体");
        f2.setColor(IndexedColors.BLACK.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
//        for(int i=0;i<columnNames.length;i++){
//            Cell cell = row.createCell(i);
//            cell.setCellValue(columnNames[i]);
//            cell.setCellStyle(cs);
//        }
        //设置每行每列的值
        for (short i = 0; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) (i));
            row1.setHeight((short)(15.75*20));
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
            	String key = keys[j];
            	boolean isExclude = false;
            	for(String s:excludeColumnNames){
            		if(s.equals(key)){
            			isExclude = true;
            			break;
            		}
            	}
            	if(!isExclude){
            		 Cell cell = row1.createCell(j);
                     cell.setCellValue(list.get(i).get(keys[j]) == null?" ": list.get(i).get(keys[j]).toString());
                     cell.setCellStyle(cs2);
            	}else{//图片
            		 //anchor主要用于设置图片的属性
            		 Cell cell = row1.createCell(j);
                     cell.setCellValue("	");
                     cell.setCellStyle(cs2);
            		BufferedImage bufferImg = null;   
            		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();   
            		if(list==null){
            			continue;
            		}
            		String picType = list.get(i).get(keys[j]).toString();
            		if(j==keys.length-1){
            			if("1".equals(list.get(i).get(keys[j]))){
            				picType = "2";
            			}else{
            				picType = "";
            			}
            		}
            		String filePath = flagFileRoot+picType+".bmp";
            		if(!new File(filePath).exists()){
            			continue;
            		}
                    bufferImg = ImageIO.read(new File(filePath)); 
                    ImageIO.write(bufferImg, "jpg", byteArrayOut);  
                    //行列是行数和列数
                    int startRow = i;//行
                    short startCol = (short)(j);//列
                    int dx1 = 0;//行偏移量
                    int dy1 = 0;//列偏移量
                    int dx2 = dx1+512;
                    HSSFClientAnchor anchor = new HSSFClientAnchor(dx1,dy1,dx2,255,startCol,startRow,startCol,startRow);   
                    anchor.setAnchorType(HSSFClientAnchor.MOVE_AND_RESIZE);
                    //插入图片  
                    HSSFPicture pict = patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
                    pict.resize(1);
            	}
               
            }
        }
        return wb;
    }


}
