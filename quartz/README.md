# Quartz 中文文档
https://www.w3cschool.cn/quartz_doc/quartz_doc-h4ux2cq6.html
## 相关注解
### Job状态与并发
@DisallowConcurrentExecution: 将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义（这里指特定的job类）的多个实例

@PersistJobDataAfterExecution：将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据

## 使用方式
### 1. 使用枚举定义
### 2. 自定义启动器