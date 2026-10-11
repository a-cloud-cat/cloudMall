package org.example.cloudmall.common.result;

import com.github.pagehelper.Page;
import java.util.List;

/**
 * 统一分页返回，restPage 把 PageHelper 查出的 Page 转成对外结构
 */
public class CommonPage<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<>();
        if (list instanceof Page<T> page) {
            result.setPageNum(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setTotalPage(page.getPages());
            result.setTotal(page.getTotal());
        }
        result.setList(list);
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
