# Yummy数据库设计



## ER图

见Yummy数据库ER图



## 十张表设计



### 1、会员 customer

```java
String username;//len<20,PK
String password;//len<20
String email;
String phone;
String name;//real name
int level;//default=1
boolean isValid;//default=true
double money;
String emailCode;//邮箱激活码
String emailValid;//是否激活，default=false
double consumption;//累计消费额
```

### 2、会员送餐地址customer_address

```java
int id;//PK
String username;//FK
String address;//系统提供选项
```

### 3、餐厅restaurant

```java
int rid;//len=7,PK
String password;
String name;
String address;//系统提供选项
RestaurantType type;//枚举类
double money;
String phone;
String email;
String emailCode;
boolean emailValid;//default=false
```

### 4、经理Manager

```java
String uid;
String password;
```

### 5、餐厅餐品信息product_info

```java
int rid;//FK
long pid;//PK
LocalDate startTime;
LocalDate endTime;
String productName;
int quantity;
double price;
String type;
String description;//单品说明以及套餐说明
boolean isSetMeal = false;
```

### 6、餐厅优惠信息discount_info

```java
int id;//PK
int rid;//FK
LocalDate startTime;
LocalDate endTime;
double upper;//=满？
double discount;//=减？
```

### 7、订单简略信息order_list

```java
long oid;//PK
boolean isPaid;
String username;//FK，customer
int rid;//FK，restaurant
LocalDateTime time;//下单时间+30min
double totalPrice;
double restaurant_profit;//default=totalPrice*0.7
double profit;//default=totalPrice*0.3
boolean isCancelled;//default=false
boolean isProcessed;//default=false商家默认未处理，确认接单后改为true；如果退订被修改，会修改成未处理
boolean isMade;//default=false商家默认未制作
boolean isValid;//default=true，15min未付款则改为false
String address;//送餐地址
```

### 8、订单详情order_detail【一个订单有多个餐品】

```java
int id;//PK
long oid;//FK
long pid;//FK
int quantity;
```

### 9、审批信息approval

```java
int id;//PK
int rid;//FK
String name;
String phone;
String address;//系统提供选项
RestaurantType type;//枚举类
boolean isProcessed;//defalut=false
```


### 10、套餐详情set_meal_detail

```java
int id;//PK
int smid;//FK
int pid;//FK
int quantity;
```

