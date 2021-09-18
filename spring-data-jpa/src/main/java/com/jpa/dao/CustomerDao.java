package com.jpa.dao;

import com.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 接口描述：
 * JpaRepository<操作的实体类型, 实体主键类型>
 *     + 封装了基本的CRUD操作
 * JpaSpecificationExecutor<操作的实体类型>
 *     + 封装了复杂查询（分页）
 * @Author e-Feilong.Chen
 * @Date 2021/9/18 12:35
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    /**
     * 案例：根据客户名称查询客户
     * jpql:from Customer where custNmae = ?
     * @param custName
     * @return
     */
    @Query(value = "from Customer where custName=?1")
    Customer findJpql(String custName);


    /**
     * 案例：根据客户名称和id查询客户
     * jpql:from Customer where custNmae = ? and custId = ?
     * @param custName
     * @return
     */
    @Query(value = "from Customer where custName = ?1 and custId = ?2")
    Customer findCustNameAndId(String custName, Long id);

    /**
     * 更新操作
     * 根据id更新客户的名称
     *
     * @Query: 进行查询
     * @Modifying 当前执行的是一个更新操作
     *
     */
    @Query(value = "update Customer set custName=?1 where custId=?2")
    @Modifying
    void updateCustomer(String custName, Long id);

    /**
     * 使用 sql的形式查询
     * 查询全部的客户
     * sql： select * from cst_customer
     */
    @Query(value = "select * from cst_customer", nativeQuery = true)
    List<Object[]> findSql();

    @Query(value = "select * from cst_customer where cust_name like ?1", nativeQuery = true)
    List<Object[]> findSqlLike(String custName);

    /**
     * 方法的约定：
     * findBy
     */
    Customer findByCustName(String custName);
}
