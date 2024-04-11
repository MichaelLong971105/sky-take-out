package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

/**
 * @program: sky-take-out-backend
 * @description: 数据统计Service
 * @author: MichaelLong
 * @create: 2024-04-07 22:48
 **/
public interface ReportService {

    /**
     * @Description: 统计指定时间区间内的营业额数据
     * @Param: [begin, end]
     * @return: com.sky.vo.TurnoverReportVO
     */
    TurnoverReportVO getTurnoverStatics(LocalDate begin, LocalDate end);

}
