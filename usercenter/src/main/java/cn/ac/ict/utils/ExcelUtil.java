package cn.ac.ict.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    /**
     * 公共方法，功能如下：1、兼容65535行单sheet页数据限制 2、兼容数字类型
     * 3、导出数据带格式属性（边框，自动调整列宽，字体，带序号）
     * 4、自定义导出，提供beanToMap方法，传入导出所需字段名称，对应字段属性，反射调用对应get方法获取属性值
     *
     * @param fileName 导出的文件名
     * @param heads    Excel表头
     * @param cols     对应的
     * @param list     需要被导出的数据
     * @return
     */
    public static Boolean exportExcel(HttpServletResponse response, String fileName, String[] heads, String[] cols, List<Map<String, Object>> list) {
        // 创建一个excel对象
        HSSFWorkbook hssfworkbook = new HSSFWorkbook();
        //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
        //获取列头样式对象
        HSSFCellStyle columnTopStyle = getColumnTopStyle(hssfworkbook);
        //单元格样式对象
        HSSFCellStyle style = getStyle(hssfworkbook);
        for (int i = 0; i <= (list.size() / 65535); i++) {
            //单个sheet页超过65535行再新建一个sheet页
            HSSFSheet hssfsheet = hssfworkbook.createSheet(); // 工作表
            // sheet页名称
            hssfworkbook.setSheetName(i, fileName.replace(".xls", "") + "(第" + (i + 1) + "页)");
            //设置起始行数
            int beginRows = 65535 * i;
            //设置截止行数
            int endRows = (list.size() > 65535 * (i + 1)) ? 65535 * (i + 1) - 1 : list.size() - 1;
            //新建表头
            HSSFRow rowHead = hssfsheet.createRow(0);
            //输出excel 表头
            if (heads != null && heads.length > 0) {
                for (int h = 0; h < heads.length; h++) {
                    HSSFCell hssfCell = rowHead.createCell(h, CellType.STRING);
                    hssfCell.setCellValue(heads[h]);
                    hssfCell.setCellStyle(columnTopStyle);
                }
            }
            // 要设置数值型列表；是否是数值型：boolean isNum = false;输出excel数据。
            for (int curRow = beginRows; curRow <= endRows; curRow++) {
                // 获取数据
                Map hm = list.get(curRow);
                // 创建excel行 表头1行 导致数据行数 延后一行
                HSSFRow hssfrow = hssfsheet.createRow(curRow % 65535 + 1);
                // 读取数据值
                for (int k = 0; k < cols.length; k++) {
                    HSSFCell hssfcell = hssfrow.createCell(k);
                    hssfcell.setCellValue(hm.get(cols[k]) == null ? "" : hm.get(cols[k]).toString());
                    hssfcell.setCellStyle(style);
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < heads.length; colNum++) {
                int columnWidth = hssfsheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < hssfsheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (hssfsheet.getRow(rowNum) == null) {
                        currentRow = hssfsheet.createRow(rowNum);
                    } else {
                        currentRow = hssfsheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellTypeEnum() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    hssfsheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    hssfsheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        }
        // excel生成完毕，写到输出流
        String fileKey = fileName + ".xls";
        try {
            //清空response
            response.reset();
            //设置response的Header
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileKey, "utf-8"));
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/json;charset=gb2312");
            //将excel写入到输出流中
            hssfworkbook.write(os);
            os.flush();
            os.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置 表头 单元格样式
     */
    public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBold(true);
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor((short) 0);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor((short) 0);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor((short) 0);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor((short) 0);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 列数据信息单元格样式
     */
    public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 10);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor((short) 0);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor((short) 0);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor((short) 0);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor((short) 0);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //数据格式
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("##0"));
        return style;
    }
}
