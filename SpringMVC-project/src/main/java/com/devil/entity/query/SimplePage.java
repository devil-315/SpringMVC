package com.devil.entity.query;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * ClassName：SimplePage
 *
 * @author: Devil
 * @Date: 2024/9/16
 * @Description:
 * @version: 1.0
 */
@Getter
@ToString
public class SimplePage {
    //当前页
    private int pageNo;
    //总记录数
    private int countTotal;
    //每页显示数量
    private int pageSize;
    //总页数
    private int pageTotal;
    //当前页起始索引
    private int start;

    public SimplePage() {
    }

    public SimplePage(Integer pageNo, int countTotal, int pageSize) {
        if (pageNo == null){
            pageNo = 0;
        }
        this.pageNo = pageNo;
        this.countTotal = countTotal;
        this.pageSize = pageSize;
        action();
    }

    public SimplePage(int start) {
        this.start = start;
    }

    public void action(){
        if(this.pageSize <= 0){
            this.pageSize = 5;
        }

        //如果总记录数 大于 0，就计算总页数，否则默认总页数为1
        if(this.countTotal > 0){
            //计算总页数
            this.pageTotal = (int) Math.ceil((double) countTotal/pageSize);
        }else {
            pageTotal = 1;
        }

        //如果 当前页 <= 1，默认是第1页
        if(pageNo <= 1){
            pageNo = 1;
        }

        //如果当前页 大于 总页数，默认当前页是总页数（最后一页）
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }

        //计算起始索引
        this.start = (pageNo - 1) * pageSize;

    }

}
