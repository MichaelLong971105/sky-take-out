package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @program: sky-take-out-backend
 * @description: 数据统计相关接口
 * @author: MichaelLong
 * @create: 2024-04-07 22:44
 **/

@RestController
@RequestMapping("/admin/report")
@Api(tags = "数据统计相关接口")
@Slf4j
public class reportController {

    @Autowired
    private ReportService reportService;

    /**
     * @Description: 营业额统计
     * @Param: [begin, end]
     * @return: com.sky.result.Result<com.sky.vo.TurnoverReportVO>
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("营业额数据统计{}至{}", begin, end);
        return Result.success(reportService.getTurnoverStatics(begin, end));
    }

}
