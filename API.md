# 图书馆预约系统-API

## 1.1. tips

- 用户类别 :
  - S ：学生用户
  - T ：教师用户

- code :
  - 0 : 一切正常
  - 1 : 添加 message 字段展示错误

- url : host + uri
  - example :
    - host : <http://lib.nefu.edu.cn/api>
    - uri : /login
    - url : <http://lib.nefu.edu.cn/api/login>

- 编号 :
  - studentId : 学生学号
  - index : 数据库主键id
  - number ： 书包柜编号
  - location : null 全部 | 1 二楼北 | 2 二楼南 | 3 三楼北 | 4 三楼南

## 1.2. 登陆

- POST  /login
- payload :
  - 匹配数据库查看是管理员账号登录(账号+密码)还是学生账号登录(学号+姓名)

```json
{
    "studentId" : "2016210157",
    "studentName" : "张三"
}
```

- return :

```json
{
    "code" : 0,
    "data" : {
        "token" : "dsfuihwi87yr8734riuew9",
        "type" : "S"
    }

}
```

## 1.3. 学生系统

### 1.3.1. 预约书包柜

- POST /box-order
- payload :

```json
{
    "location" : 1,
    "studentId" : "2016210157"
}
```

- return :

```json
{
    "code" : 0,
    "data" : "排队成功，请耐心等待!"
}
```

### 1.3.2. 当前预约状态

- GET /status/{studentId}

- return :

```json
{
    "code" : 0,
    "data" : 100
}
```

### 1.3.3. 我的信息

- GET /info/{studentId}

- rteurn :

```json
{
    "code" : 0,
    "data" : {
        "location" : 1,
        "number" : "123"
    }
}
```

### 1.3.4. 获取开放时间

- GET /time

- return :

```json
{
    "code" : 1,
    "data" : {
        "startTime" : "2018-10-10 18:00:00",
        "nowTime" : "2018-10-10 15:00:00"
    }
}
```

### 1.3.5. 获取书包柜数量

- GET /num

- return :

```json
{
    "code" : 0,
    "data" :
        [
            {
                "location" : 1,
                "num" : 100
            },
            {
                "location" : 2,
                "num" : 100
            }
        ]
}
```

## 1.4. 教师系统

### 1.4.1. 预约管理

#### 1.4.1.1. 获取预约时间

- GET /open-time

- return :

```json
{
    "code" : 0,
    "data" : {
        "startTime" : "2018-10-10 12:00:00",
        "endTime" : "2018-10-10 13:00:00"
    }
}
```

#### 1.4.1.2. 修改预约时间

- PUT /open-time
- payload :
  - startTime 开始时间
  - endTime 结束时间

```json
{
    "startTime" : "2018-10-10 12:00:00",
    "endTime" : "2018-10-10 13:00:00"
}
```

- return :

```json
{
    "code" : 0,
    "data" : "修改成功"
}
```

#### 1.4.1.3. 获取预约区域

- GET /open-area

- return :

```json
{
    "code" : 0,
    "data" : [1, 2]
}
```

#### 1.4.1.4. 修改预约区域

- PUT /open-area
- payload :

```json
[1, 2]
```

- return :

```json
{
    "code" : 0
}
```

### 1.4.2. 书包柜管理

#### 1.4.2.1. 查询

- GET /detail?location=2N&status=0&id=10-11&studentId=2016111

- return :
  - perPage 每页显示条数

```json
{
    "code" : 0,
    "data" : {
        "ships" :
        [
            {
                "location" : 1,
                "id" : "123",
                "status" : 0,
                "studentId" : "123"
            },
            {
                "location" : 2,
                "id" : "123",
                "status" : 0,
                "studentId" : "123"
            }
        ]
        "pages" : {
            "totalPage" : 15,
            "nowPage" : 10,
            "perPage" : 10
        }
    }
}
```

#### 1.4.2.2. 清空/删除关系

- DELETE /ship
- payload :
  - data 不为空时表示删除数组内柜子的关系, 为空时表示清空所有关系

```json
{
    "data" : ["123", "234", "345"]
}
```

- return :

```json
{
    "code" : 0
}
```

#### 1.4.2.3. 修改单个关系

- PUT /ship
- payload :
  - number : 书包柜编号
  - stuid : 为 null 则释放关系, 为学号则建立关系

```json
{
    "number" : "123",
    "studentId" : "1234555"
}
```

- return :

```json
{
    "code" : 0
}
```

#### 1.4.2.4. 批量预留柜子

- POST /preorder
- payload :

```json
["123", "234", "345"]
```

- return :

```json
{
    "code" : 0
}
```

### 1.4.3 学生管理

#### 1.4.3.1. 开放年级

- POST /open-grades
- payload :

```json
{
    "startGrade" : "2015",
    "endGrade" : "2016"
}
```

- return :

```json
{
    "code" : 0
}
```

#### 1.4.3.2. 添加黑名单

- POST /list
- payload :

```json
{
    "studentId" : "12434555434"
}
```

- return :

```json
{
    "code" : 0
}
```

#### 1.4.3.3. 删除黑名单

- DELETE /list/{studentId}

- return :

```json
{
    "code" : 0
}
```

#### 1.4.3.4. 显示黑名单

- GET /list

- return :

```json
{
    "code" : 0,
    "data" : [
        {
            "name" : "张三",
            "studentId" : "1234556676"
        },
        {
            "name" : "张三",
            "studentId" : "1234556676"
        }
    ]
}
```