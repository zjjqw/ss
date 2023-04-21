package top.metass.pro.module.infra.controller.admin.test.vo;

import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 字典类型 Excel VO
 *
 * @author 三生宇宙
 */
@Data
public class TestDemoExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("名字")
    private String name;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("类型")
    private Integer type;

    @ExcelProperty("分类")
    private Integer category;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
