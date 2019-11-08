##  资料  
[maven仓库](https://mvnrepository.com/artifact/com.auth0/java-jwt/3.8.3)   
[Java实现基于token认证](https://blog.csdn.net/Eye_Me/article/details/88720585)  
[UUID在线生成器](http://www.uuid.online)  
[@ManyToMany关系的理解和使用](https://wycode.cn/2018-04-19-many_to_many.html)  
[jpa只查指定的字段](https://blog.csdn.net/asd54090/article/details/91042658)

###  git 操作  

- 第一步  :**创建git仓库，提交代码**
```git
    git init
    git commit -m "first commit"
    git remote add origin https://github.com/{username}/{repositoryName}.git
    git push -u origin master
```  

### 在学习中遇到的问题  

-  在JwtUntil中token过期时间过短的问题
> 原代码
```java
private static final long EXPIRE_TIME = 3 * 30 * 24 * 60 * 60 * 1000;
```

> 修改后的代码
```java
private static final long EXPIRE_TIME = 3 * 30 * 24 * 60 * 60 * 1000l;
```
> 初步猜想  
> 在原代码中所有相乘的数为Int型，相乘后的结果也为Int型，导致最终的结果也为Int型，直接导致结果超出Int型的显示范围
> 在修改后的代码中为Int * long = long 结果为long类型，数据在显示范围类  

- **在实体中添加一个实体对象最为属性时需要使用@Transient注解，告诉JPA当前实体不需要映射到数据表，一旦不添加这个注解就会报错**
```java
@Transient
private CUser cUser;
```
