package com.sky.service.impl;

import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: ReportService接口实现
 * @author: MichaelLong
 * @create: 2024-04-07 22:51
 **/

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 统计指定时间区间内的营业额数据
     * @Param: [begin, end]
     * @return: com.sky.vo.TurnoverReportVO
     */
    @Override
    public TurnoverReportVO getTurnoverStatics(LocalDate begin, LocalDate end) {
        //存放从begin到end范围内每天的日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Double> amountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            //查询date对应的营业额(状态为已完成订单金额合计)
            Double amount = orderMapper.getAmount(date, 5);
            amount = amount == null ? 0.00 : amount;
            amountList.add(amount);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(amountList, ","))
                .build();
    }

    /**
     * @Description: 统计指定时间区间内的用户数据
     * @Param: [begin, end]
     * @return: com.sky.vo.UserReportVO
     */
    @Override
    public UserReportVO getUserStatics(LocalDate begin, LocalDate end) {
        //存放从begin到end范围内每天的日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            //计算指定日期的后一天对应的日期
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        //存放每天新增用户数量
        List<Integer> newUserList = new ArrayList<>();
        //存放每天总用户数量
        List<Integer> totalUserList = new ArrayList<>();

        for (LocalDate date : dateList) {
            //查询date对应的新增用户数
            LocalDateTime beginDateTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUserAmount = userMapper.getUserAmount(beginDateTime, endDateTime);
            newUserAmount = newUserAmount == null ? 0 : newUserAmount;
            newUserList.add(newUserAmount);

            Integer totalUserAmount = userMapper.getUserAmount(null, endDateTime);
            totalUserAmount = totalUserAmount == null ? 0 : totalUserAmount;
            totalUserList.add(totalUserAmount);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }
}
