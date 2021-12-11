# mybatis plus
简单的使用教程
## 整合步骤
1. 创建maven项目
2. 导入必要依赖
3. 修改配置文件
4. mybatis-plus 使用前置配置
5. 使用mybatis-plus

### 创建maven项目
略
### 导入必要依赖
```xml
<!--web-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--test-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<!--mybatis-->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.0</version>
</dependency>
<!--mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.16</version>
</dependency>
```
### 修改配置文件
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/mp?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&createDatabaseIfNotExist=true
    username: root
    password: l(=8gp_04h*&
mybatis-plus:
  #  mapper-locations: classpath:mapper/*.xml
  mapper-locations: classpath*:/mapper/*.xml #其他包的也能扫描到
  type-aliases-package: com.po
  # 以下配置均有默认值,可以不设置
  global-config:
    db-config:
      logic-delete-field: deleted #配置逻辑删除，也可以在字段上加注解 @TableLogic(value = "0",delval = "1")
      logic-delete-value: 1 # 删除1
      logic-not-delete-value: 0
      #主键类型 AUTO:"数据库ID自增" INPUT:"用户输入ID",ID_WORKER:"全局唯一ID (数字类型唯一ID)", UUID:"全局唯一ID UUID";
      id-type: assign_id
      #字段策略 IGNORED:"忽略判断"  NOT_NULL:"非 NULL 判断")  NOT_EMPTY:"非空判断"
      field-strategy: NOT_EMPTY
      #数据库类型
      db-type: MYSQL
  configuration:
    # 是否开启自动驼峰命名规则映射:从数据库列名到Java属性驼峰命名的类似映射
    map-underscore-to-camel-case: true
    # 如果查询结果中包含空值的列，则 MyBatis 在映射的时候，不会映射这个字段
    call-setters-on-nulls: true
    # 这个配置会将执行的sql打印出来，在开发或测试的时候可以用
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```
### mybatis-plus 使用前置配置
1. 配置分页
```java
@Configuration
public class MybatisPlusConfig {
    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
```
2. 配置填充属性
```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final String createTimeField = "createTime";
    private final String createUserIdField = "createUserId";
    private final String updateTimeField = "updateTime";
    private final String updateUserIdField = "updateUserId";

    /**
     * 执行insert时填充数据
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(createTimeField, metaObject);
        Object createUserId = getFieldValByName(createUserIdField, metaObject);
        Object updateTime = getFieldValByName(updateTimeField, metaObject);
        Object updateUserId = getFieldValByName(updateUserIdField, metaObject);

        if(createTime == null) {
            //字段为空，可以进行填充
            setFieldValByName(createTimeField, LocalDateTime.now(), metaObject);
        }
        if (createUserId == null) {
            //字段为空，可以进行填充
            setFieldValByName(createUserIdField, 1L, metaObject);
        }
        if(updateTime == null) {
            //字段为空，可以进行填充
            setFieldValByName(updateTimeField, LocalDateTime.now(), metaObject);
        }
        if (updateUserId == null) {
            //字段为空，可以进行填充
            setFieldValByName(updateUserIdField, 1L, metaObject);
        }
    }
    /**
     * 执行update时填充数据
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(updateTimeField, metaObject);
        Object updateUserId = getFieldValByName(updateUserIdField, metaObject);
        //字段为空，可以进行填充
        setFieldValByName(updateTimeField, LocalDateTime.now(), metaObject);
        //字段为空，可以进行填充
        setFieldValByName(updateUserIdField, 1L, metaObject);
    }
}
```
3. 创建一个BasePO，内部拥有一张表的基本属性（创建时间，更新时间，更新人id，创建人id，是否被删除），用于其它实体类进行继承。
```java
@Data
public class BasePO implements Serializable {
    private static final long serialVersionUID = 8647446252613184267L;
    /**
     * id
     * 防止前端接受精度丢失
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using= ToStringSerializer.class)
    protected Long id;
    /**
     * 是否被删除
     */
    protected Boolean deleted;
    /**
     * 创建数据时的操作用户id
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createUserId;
    /**
     * 更新数据时的操作用户id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateUserId;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;
}
```
4. 创建实体，继承BasePO，使用注解标明对应的表名
```java
@Data
@TableName("user")
public class UserPO extends BasePO{

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    @Override
    public String toString() {
        return "UserPO{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", createUserId=" + createUserId +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
```
5. 编写service
```java
public interface UserService extends IService<UserPO> { }
```
6. 编写UserServiceImpl
```java
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService { }
```
7. 编写UserMapper，需要继承BaseMapper，例如：
```java
public interface UserMapper extends BaseMapper<UserPO> { }
```
8. 启动类加上mapper扫描
```java
@MapperScan("com.mapper")
```
### 使用mybatis-plus
基本使用，详情见测试类 MPTestApplication.java