# flyway
使用 flyway可以进行数据库版本管理
使用插件更加灵活，可以直接使用maven命令进行修复版本

##遗留问题
1. 使用maven插件的方式，启动项目不会自动执行脚本，但是修复时很方便
2. spring boot方式，项目启动会自动执行脚本，但是不能灵活修复执行失败的脚本。

## 方式一：flyway-maven-plugin 插件

1. 在resources下创建db/migration目录
2. 引入mysql驱动，导入flyway插件，并配置数据库信息
```xml
<dependencies>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.16</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-maven-plugin</artifactId>
            <version>6.4.4</version>
            <!--配置参考：https://www.cnblogs.com/qq739178184/p/5122099.html-->
            <configuration>
                <user>${MYSQL_USERNAME}</user>
                <password>${MYSQL_PASSWORD}</password>
                <driver>com.mysql.jdbc.Driver</driver>
                <!--createDatabaseIfNotExist=true, 数据库不存在创建数据库-->
                <url>jdbc:mysql://localhost:3306/flyway_demo?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=UTC&amp;createDatabaseIfNotExist=true</url>
                <table>flyway_schema_history</table>
                <locations>classpath:db/migration/</locations>
            </configuration>
        </plugin>
    </plugins>
</build>
```
### 执行maven 命令
如果有需要，需要先执行 `mvn clean install`
#### baseline
`mvn flyway:baseline`

对已经存在数据库Schema结构的数据库一种解决方案。实现在非空数据库新建MetaData表，并把Migrations应用到该数据库；也可以应用到已有表结构的数据库中也可以实现添加Metadata表
#### clean
`mvn flyway:clean`

清除掉对应数据库 Schema 中所有的对象，包括表结构，视图，存储过程等，clean 操作在 dev 和 test 阶段很好用
#### info
`mvn flyway:info`

用于打印所有的 Migrations 的详细和状态信息，也是通过 MetaData 和 Migrations 完成的，可以快速定位当前的数据库版本；
执行后，会打印出配置的Migrations的版本和执行状态。
#### migrate
`mvn flyway:migrate`

migrate 是指把数据 Schema 迁移到最新版本，在 Migrate 时会检查 MetaData 元数据表，如果不存在就创建 MetaData 表，MetaData 用于记录数据库历史变更等信息；
Migrate会扫描指定文件系统或者 classpath 下的 Migrations。会与 MetaData 中的记录进行对比，进行版本升级；
#### repair
`mvn flyway:repair`

repair操作能够修复metaData表（修复checksum值），该操作在metadata出现错误时很有用。
#### undo
`mvn flyway:undo`

Flyway Pro Edition or Flyway Enterprise Edition upgrade required: undo is not supported by Flyway Community Edition.
需要升级Flyway专业版或Flyway企业版:Flyway社区版不支持撤销。
#### validate
`mvn flyway:validate`

验证以及apply的Migrations是否有变更，默认开启的；原理是对比MetaData表与本地Migrations的checkNum值，如果值相同则验证通过，否则失败。

## 方式二：spring boot整合
1. 导入相关依赖
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```
2. 修改配置文件
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/flyway_demo_bak?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&createDatabaseIfNotExist=true
    username: ${MYSQL_USERNAME}
    password: ${MYSQL_PASSWORD}
  ## flyway数据迁移
  flyway:
    #是否开启
    enabled: true
    locations: classpath:db/migration #迁移脚本的位置，默认db/migration
    baseline-on-migrate: true
    out-of-order: false
    table: flyway_schema_history
```


## 使用说明

> 注意
>
> 我们在定义脚本的时候，除了 V 字开头的脚本之外，还有一种 R 字开头的脚本。
> + V 字开头的脚本只会执行一次（V后面加版本号）。
> + R 字开头的脚本，只要脚本内容发生了变化，启动时候就会执行（R后面直接__,不接版本号）。
> + 所有的脚本，一旦执行了，就会在 flyway_schema_history 表中有记录，如果你不小心搞错了，可以手动从 flyway_schema_history 表中删除记录，然后修改 SQL 脚本后再重新启动（生产环境不建议）。

> 注意
>
>Flyway将每一个数据库脚本称之为：migrations，flyway支持三种类型的migration：
>+ Versioned migrations：最常用的migration，可以简单的理解为数据库升级脚本
>+ Undo migrations：数据库版本回退脚本，需要Pro版本，忽略，而且使用过程存在较大风险，undo操作目前只能通过plugin或者command-line来执行
>+ Repeatable migrations：可重复执行的migration，例如create or replace脚本，当脚本checksums改变时会重新执行

## 配置属性
```yaml
spring:
  flyway:
    baseline-description: #执行基线时标记已有Schema的描述。
    baseline-on-migrate: false # 在没有元数据表的情况下，针对非空Schema执行迁移时是否自动调用基线。（默认值：false。）
    baseline-version: 1 #执行基线时用来标记已有Schema的版本。 （默认值：1。）
    check-location: false #检查迁移脚本所在的位置是否存在。 （默认值： false 。 ）
    clean-on-validation-error: false #在验证错误时，是否自动执行清理。 （默认值： false 。 ）
    enabled: true #开启Flyway。 （默认值： true 。 ）
    encoding: UTF-8 #设置SQL迁移文件的编码。 （默认值： UTF-8 。 ）
    ignore-failed-future-migration: false #在读元数据表时，是否忽略失败的后续迁移。 （默认值： false 。 ）
    init-sqls: #获取连接后立即执行初始化的SQL语句。
    locations: classpath:db/migration #迁移脚本的位置。 （默认值： db/migration 。 ）
    out-of-order: false #是否允许乱序（out of order）迁移。 （默认值： false 。 ）
    placeholder-prefix: #设置每个占位符的前缀。 （默认值： ${ 。 ）
    placeholder-replacement: #是否要替换占位符。 （默认值： true 。 ）
    flyway.placeholder-suffix: #设置占位符的后缀。 （默认值： } 。 ）
    placeholders: [placeholder name] #设置占位符的值。
    schemas: #Flyway管理的Schema列表，区分大小写。默认连接对应的默认Schema。
    sql-migration-prefix: V #SQL迁移的文件名前缀。 （默认值： V 。 ）
    sql-migration-separator: __ #SQL迁移的文件名分隔符。 （默认值： __ 。 ）
    sql-migration-suffix: .sql #SQL迁移的文件名后缀。 （默认值： .sql 。 ）
    table: #Flyway使用的Schema元数据表名称。 （默认值： flyway_schema_history 。 ）
    target: #Flyway要迁移到的目标版本号。 （默认最新版本。 ）
    url: #待迁移的数据库的JDBC URL。如果没有设置，就使用配置的主数据源。
    user: #待迁移数据库的登录用户。
    password: #待迁移数据库的登录密码
    validate-on-migrate: true #在运行迁移时是否要自动验证。 （默认值： true 。 ）
```
