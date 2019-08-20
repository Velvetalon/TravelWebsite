package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有旅游线路
     */
    public abstract List<Category> findAll();
}
