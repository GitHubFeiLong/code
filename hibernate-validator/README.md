README

根据Hibernate的官网文档[validator](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#preface)
进行学习并记录笔记



## 常用注解使用

| 注解名   | 使用示例                          | 作用                  |
| -------- | --------------------------------- | --------------------- |
| @NotNull | @NotNull<br />String manufacturer | 永远不能为空          |
| @Size    | @Size(min = 2, max = 14)          | 必须在2到14个字符之间 |
| @Min     | @Min(2)                           | 值必须至少是2         |

```java
public class Clzz {
    // 永远不能为空
    @NotNull
    private String manufacturer;
	
    // 永远不能为空,必须在2到14个字符之间
    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    // 必须至少是2
    @Min(2)
    private int seatCount;
}
```

