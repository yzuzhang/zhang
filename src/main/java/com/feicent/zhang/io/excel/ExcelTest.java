package com.feicent.zhang.io.excel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Test;
import com.feicent.zhang.io.excel.ExcelUtil.ExcelExportData;

public class ExcelTest {
	
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
    public void testExcel() throws Exception {
		
        List<String[]> columNames = new ArrayList<String[]>();
        columNames.add(new String[] { "运单号", "代理人", "时间"});
        columNames.add(new String[] { "运单号", "代理人", "时间"});
        
        List<String[]> fieldNames = new ArrayList<String[]>();
        fieldNames.add(new String[] { "awbNumber", "agent", "datetime"});
        fieldNames.add(new String[] { "awbNumber", "agent", "datetime"});
        
        LinkedHashMap<String, List<?>> map = new LinkedHashMap<String, List<?>>();
        map.put("运单月报(1月)", getData1());
        map.put("运单月报(2月)", getData2());
        
        ExcelExportData setInfo = new ExcelExportData();
        setInfo.setDataMap(map);
        setInfo.setFieldNames(fieldNames);
        setInfo.setTitles(new String[] { "航空运单报表1","航空运单报表2"});
        setInfo.setColumnNames(columNames);
        
        // 将需要导出的数据输出到文件
        String path = "D:/ExcelUtil.xls";
        long start = System.currentTimeMillis();
        ExcelUtil.export2File(setInfo, path);
        System.out.println("数据成功导出到"+path+", 耗时"+(System.currentTimeMillis()-start)+"ms"); 
    }
	
	private List<AwbDto> getData1() {
        List<AwbDto> data = new ArrayList<AwbDto>();
        for (int i = 0; i < 1000; i++) {
            data.add(new AwbDto("111-" + FileUtil.leftPad(i + "", 8, '0'), "张三", sdf.format(new Date())));
        }
        return data;
    }
    
    private List<AwbDto> getData2() {
        List<AwbDto> data = new ArrayList<AwbDto>();
        for (int i = 0; i < 1000; i++) {
            data.add(new AwbDto("999-" + FileUtil.leftPad(i + "", 8, '0'), "李四", sdf.format(new Date())));
        }
        return data;
    }
    
    public List<AwbDto> getData(String prefix, String agent) {
        List<AwbDto> data = new ArrayList<AwbDto>();
        for (int i = 0; i < 1000; i++) {
            data.add(new AwbDto(prefix + FileUtil.leftPad(i + "", 8, '0'), agent, sdf.format(new Date())));
        }
        return data;
    }
    
}
