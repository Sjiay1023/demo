package com.ssm.demo.dao;


import com.ssm.demo.domain.ProductRobbingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductRobbingRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductRobbingRecord record);

    int insertSelective(ProductRobbingRecord record);

    ProductRobbingRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductRobbingRecord record);

    int updateByPrimaryKey(ProductRobbingRecord record);
}