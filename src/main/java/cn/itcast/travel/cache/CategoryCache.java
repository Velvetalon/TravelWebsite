package cn.itcast.travel.cache;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryCache {
    /**
     * 查询/设置/清除缓存;
     * @return
     */
    public abstract List<Category> getCategoryCache();
    public abstract void setCategoryCache(List<Category> list);
    public abstract void removeCategoryCache();
}
