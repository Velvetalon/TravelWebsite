package cn.itcast.travel.service.impl;

import cn.itcast.travel.cache.CategoryCache;
import cn.itcast.travel.cache.impl.CategoryCacheImpl;
import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private CategoryCache categoryCache = new CategoryCacheImpl();

    public  List<Category> findAll(){
        //缓存有数据时直接返回缓存，否则查询数据库并设置缓存
        List<Category> result = categoryCache.getCategoryCache();
        if(result != null && !result.isEmpty()){
            return result;
        }

        result = categoryDao.findAll();
        categoryCache.setCategoryCache(result);
        return result;
    }
}
