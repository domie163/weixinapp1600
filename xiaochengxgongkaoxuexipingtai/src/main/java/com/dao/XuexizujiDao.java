package com.dao;

import com.entity.XuexizujiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.XuexizujiView;

/**
 * 学习足迹 Dao 接口
 *
 * @author 
 */
public interface XuexizujiDao extends BaseMapper<XuexizujiEntity> {

   List<XuexizujiView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
