package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: sky-take-out-backend
 * @description: 销量排行前十
 * @author: MichaelLong
 * @create: 2024-04-20 16:01
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTop10 {
    //菜品名称
    private String name;
    //销售数量
    private Integer number;
}
