# 1. 图书馆预约系统-API

<!-- TOC -->

- [1. 图书馆预约系统-API](#1-图书馆预约系统-api)
  - [1.1. tips](#11-tips)
  - [1.2. 登陆相关](#12-登陆相关)
    - [1.2.1. 登陆](#121-登陆)
    - [1.2.2. 获取验证码](#122-获取验证码)
    - [1.2.3. 验证验证码](#123-验证验证码)
  - [1.3. 学生系统](#13-学生系统)
    - [1.3.1. 预约书包柜](#131-预约书包柜)
    - [1.3.2. 当前预约状态](#132-当前预约状态)
    - [1.3.3. 我的信息](#133-我的信息)
    - [1.3.4. 获取开放时间](#134-获取开放时间)
    - [1.3.5. 获取书包柜数量](#135-获取书包柜数量)
  - [1.4. 教师系统](#14-教师系统)
    - [1.4.1. 预约管理](#141-预约管理)
      - [1.4.1.1. 获取预约时间](#1411-获取预约时间)
      - [1.4.1.2. 修改预约时间](#1412-修改预约时间)
      - [1.4.1.3. 获取预约区域](#1413-获取预约区域)
      - [1.4.1.4. 修改预约区域](#1414-修改预约区域)
      - [1.4.1.5. 区域预约状态](#1415-区域预约状态)
    - [1.4.2. 书包柜管理](#142-书包柜管理)
      - [1.4.2.1. 查询](#1421-查询)
      - [1.4.2.2. 清空/删除关系](#1422-清空删除关系)
      - [1.4.2.3. 修改单个关系](#1423-修改单个关系)
      - [1.4.2.4. 批量预留柜子](#1424-批量预留柜子)
    - [1.4.3. 学生管理](#143-学生管理)
      - [1.4.3.1. 修改开放年级](#1431-修改开放年级)
      - [1.4.3.2. 添加黑名单](#1432-添加黑名单)
      - [1.4.3.3. 删除黑名单](#1433-删除黑名单)
      - [1.4.3.4. 显示黑名单](#1434-显示黑名单)
      - [1.4.3.5. 获取开放年级](#1435-获取开放年级)

<!-- /TOC -->

## 1.1. tips

- 用户类别 :
  - S ：学生用户
  - T ：教师用户

- code :
  - 0 : 一切正常
  - 1 : 添加 message 字段展示错误
  - 2 : 登陆失败

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

## 1.2. 登陆相关

### 1.2.1. 登陆

- POST  /login
- payload :
  - 匹配数据库查看是管理员账号登录(账号+密码)还是学生账号登录(学号+姓名)

```json
{
    "studentId" : "2016210111",
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

### 1.2.2. 获取验证码

- GET /code

- return :

```json
{
  "data" : "base64"
}
```

### 1.2.3. 验证验证码

- GET /vrifycode/{vrifyCode}
- vrifyCode : 验证码数值

- 成功return：
```json
{
    "code" : 0,
    "data" : "请求成功"
}
```
- 失败return：
```json
{
    "code" : 1,
    "data" : "验证码错误"
}
```

## 1.3. 学生系统

### 1.3.1. 预约书包柜

- POST /box-order
- payload :

```json
{
    "location" : 1,
    "studentId" : "2016210111",
    "vrifyCode" : "12ab"
}
```

- return :

```json
{
    "code" : 0,
    "data" : true
}
```

### 1.3.2. 当前预约状态

- GET /status?studentId=2016210111&vrifyCode=2nghs2

- cookie : 验证码回传的Jsession

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
    "data" : true
}
```

#### 1.4.1.3. 获取预约区域

- GET /open-area

- return :

```json
{
    "code": 0,
    "data":
    [
        {
            "configKey" : "area_two_n",
            "configValue" : "1",
            "systemId" : "1"
        },
        {
            "configKey" : "area_two_s",
            "configValue" : "1",
            "systemId" : "2"
        },
        {
            "configKey": "area_three_n",
            "configValue": "1",
            "systemId": "3"
        },
        {
            "configKey": "area_three_s",
            "configValue": "0",
            "systemId": "4"
        }
    ]
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
#### 1.4.1.5. 区域预约状态

- GET /area-status/{studentId}

  - 0表示可以预约，1表示不可预约

  - data中的数据分别为二楼北、二楼南、三楼北、三楼南

- return:

```json
{
    "code" : 0,
    "data" : [0,1,0,0]
}
```

### 1.4.2. 书包柜管理

#### 1.4.2.1. 查询

- GET /detail?location=2&status=0&id=10-11&studentId=2016224283&page=1
-默认page=1, status=0 查询开放柜子 || status=1 查询被占用柜子 || status=2 查询预留柜子

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
        ],
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
[123, 234, 345]
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
[123, 234, 345]
```

- return :

```json
{
    "code" : 0
}
```

### 1.4.3. 学生管理

#### 1.4.3.1. 修改开放年级

- PUT /open-grades
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
    "code" : 0,
    "data" : true
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
#### 1.4.3.5. 获取开放年级

- GET /open-grades

- return:

```json
{
     "code":  0,
     "data": {
          "startGrade" : "2015",
          "endGrade" : "2016"
     }
}
```