package com.jpa.test;

import com.jpa.dao.CustomerDao;
import com.jpa.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/9/18 16:46
 */
@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定容器的配置信息
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据条件，查询单个对象
     */
    @Test
    public void testSpec() {
        // 匿名内部类
        /*
        * 自定义查询条件
        * 1. 实现 Specification接口（提供泛型，查询的对象类型）
        * 2. 实现toPredicate方法（构造查询条件）
        * 3. 需要借助方法参数中的两个参数（
        *       root：需要查询的对象属性
        *       criteriaBuilder：构造查询条件的，内部封装了很多查询条件（模糊，精准匹配）
        * ）
        * 案例：根据用户名称查询，查询客户名称为 传智播客的客户
        *   查询条件：
        *       1. 查询方式（criteriaBuilder 对象）
        *       2. 比较属性的名称（root对象）
        */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获取比较的属性
                Path<Object> custName = root.get("custName");
                // 构造查询条件
                /*
                    第一个参数：需要比较的属性对象
                    第二个参数：当前需要比较的取值
                 */
                Predicate predicate = criteriaBuilder.equal(custName, "传智播客1");// 进行精准的匹配(比较的属性，比较的属性的取值)
                return predicate;
            }
        };
        Customer one = customerDao.findOne(spec);
        System.out.println("one = " + one);
    }

    /**
     * 多条件查询
     */
    @Test
    public void testSpec1() {
        // 匿名内部类
        /*
         * 自定义查询条件
         * 1. 实现 Specification接口（提供泛型，查询的对象类型）
         * 2. 实现toPredicate方法（构造查询条件）
         * 3. 需要借助方法参数中的两个参数（
         *       root：需要查询的对象属性
         *       criteriaBuilder：构造查询条件的，内部封装了很多查询条件（模糊，精准匹配）
         * ）
         *   查询条件：
         *       1. 查询方式（criteriaBuilder 对象）
         *       2. 比较属性的名称（root对象）
         */
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获取比较的属性
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");

                // 构造查询条件
                /*
                    第一个参数：需要比较的属性对象
                    第二个参数：当前需要比较的取值
                 */
                Predicate p1 = criteriaBuilder.equal(custName, "传智播客1");// 进行精准的匹配(比较的属性，比较的属性的取值)
                Predicate p2 = criteriaBuilder.equal(custIndustry, "测试修改");
                // 将多个查询条件 组合到一起
                /*
                    多个条件有 与关系，或关系
                 */
                Predicate and = criteriaBuilder.and(p1, p2);

                return and;
            }
        };
        Customer one = customerDao.findOne(spec);
        System.out.println("one = " + one);
    }

    /**
     * equals： 直接得到Path对象，可以直接进行比较
     * gt,lt,ge,le,like:得到path对象，根据path指定比较的参数类型，再去进行比较
     * 指定参数类型：path.as(类型的字节码对象)
     */
    @Test
    public void testSpec3() {
        Specification<Customer> specification = new Specification<Customer>(){
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> custName = root.get("custName");
                Predicate like = criteriaBuilder.like(custName.as(String.class), "%传智播客%");

                return like;
            }
        };
        // List<Customer> all = customerDao.findAll(specification);

        // 创建排序对象
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> all = customerDao.findAll(specification, sort);
        all.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testSpec4() {
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        Pageable pageable = new PageRequest(0, 2);
        Page<Customer> all = customerDao.findAll(specification, pageable);

        all.getContent().forEach(System.out::println);
    }
}
