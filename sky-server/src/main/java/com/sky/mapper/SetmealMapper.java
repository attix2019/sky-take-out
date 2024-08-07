package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.Autofill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    Integer countByDishIds(List<Long> ids);

    @Autofill(operationType = OperationType.INSERT)
    void insertSetmeal(Setmeal setmeal);

    Page<SetmealVO> findSetmeals(SetmealPageQueryDTO setmealPageQueryDTO);

    @Autofill(operationType = OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    @Select("select * from setmeal where id = #{id}")
    Setmeal getSetmealById(long id);

    Integer countActiveSetmealByIds(List<Long> idList);

    void deleteSetmeals(List<Long> idList);

    @Select("select * from setmeal where category_id = #{categoryId} and status = 1")
    List<Setmeal> findActiveSetmealsByCategoryId(Integer categoryId);

    @Select("select ifnull(count(*),0) from setmeal where status = #{status} ")
    Integer getSetmealNumberByStatus(int status);
}
