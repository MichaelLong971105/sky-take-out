package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

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

    /**
     * @Description: 统计指定时间区间内的用户数据
     * @Param: [begin, end]
     * @return: com.sky.vo.UserReportVO
     */
    UserReportVO getUserStatics(LocalDate begin, LocalDate end);

    /**
     * @Description: 统计指定时间区间内的订单数据
     * @Param: [begin, end]
     * @return: com.sky.vo.OrderReportVO
     */
    OrderReportVO getOrderStatics(LocalDate begin, LocalDate end);

    /**
     * @Description: 统计指定时间区间内的菜品销量数据
     * @Param: [begin, end]
     * @return: com.sky.vo.SalesTop10ReportVO
     */
    SalesTop10ReportVO getSalesTop10Statics(LocalDate begin, LocalDate end);
}
