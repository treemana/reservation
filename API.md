# 1. 图书馆预约系统-API

<!-- TOC -->

- [1. 图书馆预约系统-API](#1-图书馆预约系统-api)
  - [1.1. tips](#11-tips)
  - [1.2. 登陆相关](#12-登陆相关)
    - [1.2.1. 登陆](#121-登陆)
    - [1.2.2. 获取验证码](#122-获取验证码)
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
      - [1.4.2.4. 批量预留柜子ById](#1424-批量预留柜子byid)
      - [1.4.2.5. 批量预留柜子ByNumber](#1425-批量预留柜子bynumber)
      - [1.4.2.6. 按照systemId范围预留柜子](#1426-按照systemid范围预留柜子)
      - [1.4.2.7. 批量增加柜子](#1427-批量增加柜子)
      - [1.4.2.8. 批量删除柜子by id](#1428-批量删除柜子by-id)
      - [1.4.2.9. 按照systemId范围删除柜子](#1429-按照systemid范围删除柜子)
      - [1.4.2.10. 批量删除柜子by number](#14210-批量删除柜子by-number)
      - [1.4.2.11. 增加区域](#14211-增加区域)
      - [1.4.2.12. 删除区域](#14212-删除区域)
    - [1.4.3. 学生管理](#143-学生管理)
      - [1.4.3.1. 添加黑名单](#1431-添加黑名单)
      - [1.4.3.2. 删除黑名单](#1432-删除黑名单)
      - [1.4.3.3. 显示黑名单](#1433-显示黑名单)
      - [1.4.3.4. 上传文件导入学生信息](#1434-上传文件导入学生信息)
      - [1.4.3.5. 删除所有学生](#1435-删除所有学生)

<!-- /TOC -->

## 1.1. tips

- 用户类别 :
  - 0 ：学生用户
  - 1 ：教师用户

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
  - location : 1_1 代表一楼区域1 1_2代表区域2

---

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
        "type" : "1"
    }

}
```

---

### 1.2.2. 获取验证码

- GET /code

- return :

```json
{
  "data" : "base64"
}
```

---

## 1.3. 学生系统

### 1.3.1. 预约书包柜

- POST /box-order
  - location 为 null时,随机预约书包柜
- payload :

```json
{
    "location" : "1_1",
    "studentId" : "2016210111",
    "verifyCode" : "12ab"
}
```

- return :

```json
{
    "code" : 0,
    "data" : true
}
```

---

### 1.3.2. 当前预约状态

- GET /status?studentId=2016210111&verifyCode=2nghs2

- cookie : 验证码回传的Jsession

- return :

```json
{
    "code" : 0,
    "data" : 100
}
```

---

### 1.3.3. 我的信息

- GET /info/{studentId}

- return :

```json
{
    "code" : 0,
    "data" : {
        "location" : "1_1",
        "number" : "123"
    }
}
```

---

### 1.3.4. 获取开放时间

- GET /time

- return :

```json
{
    "code" : 0,
    "data" : {
        "startTime" : "2018-10-10 18:00:00",
        "nowTime" : "2018-10-10 15:00:00"
    }
}
```

---

### 1.3.5. 获取书包柜数量

- GET /num/{floor}  

- return :

```json
{
    "code" : 0,
    "data" :
        [
            {
                "location" : "1_2",
                "num" : 100
            },
            {
                "location" : "1_3",
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

---

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

---

#### 1.4.1.3. 获取预约区域

- GET /open-area?floor=1

- return :

```json
{
    "code": 0,
    "data":
    [
        {
            "configKey" : "1_4",
            "configValue" : "1",
            "systemId" : "1"
        },
        {
            "configKey" : "1_4",
            "configValue" : "1",
            "systemId" : "2"
        },
        {
            "configKey": "1_3",
            "configValue": "1",
            "systemId": "3"
        },
        {
            "configKey": "1_2",
            "configValue": "0",
            "systemId": "4"
        }
    ]
}
```

---

#### 1.4.1.4. 修改预约区域

- PUT /open-area
- payload :
  - status : 0代表关闭，1代表开放

```json
{
    "locationList":["1_1","1_2"],
    "status" : 1

}
```

- return :

```json
{
    "code" : 0
}
```

---

#### 1.4.1.5. 区域预约状态

- GET /area-status/?studentId=2016224283&floor=1
  - 1 表示可以预约 | 0 表示不可预约
  - data 中的长度代表这一楼层中区域的数量,分别对应1_1,1_2...

- return:

```json
{
    "code" : 0,
    "data" : [
        {
           "location" : "1_1",
           "status" : 0
        },
        {
           "location" : "1_2",
           "status" : 1
        },
        {
           "location" : "1_3",
           "status" : 0
        }
    ]
}
```

---

### 1.4.2. 书包柜管理

#### 1.4.2.1. 查询

- GET /detail?location=1_1&status=0&number=10-11&systemIdLeft=1&systemIdRight=10&studentId=2016224283&page=1

- 默认page=1, status=0 查询开放柜子 || status=1 查询被占用柜子 || status=2 查询预留柜子
- number代表书包柜的编号（与location一块查询 例如location=1_1&number=3-20）
- location 2_代表2楼所有区域
- SystemIdLeft 代表systemId的左边界
- SystemIdRight 右边界
- return :
  - perPage 每页显示条数

```json
{
    "code" : 0,
    "data" : {
        "ships" :
        [
            {
                "location" : "1_1",
                "number" : 123,
                "systemId" : 145,
                "status" : 0,
                "studentId" : "123"
            },
            {
                "location" : "1_1",
                "number" : 123,
                "systemId" : 140,
                "status" : 0,
                "studentId" : "123"
            }
        ],
        "pages" : {
            "totalPage" : 15,
            "nowPage" : 10,
            "perPage" : 10,
            "totalSize" : 85
        }
    }
}
```

---

#### 1.4.2.2. 清空/删除关系

- DELETE /ship
- payload :
  - data 不为空时表示删除数组内柜子的关系, 为空时表示清空所有关系,数组成员为systemId

```json
[123, 234, 345]
```

- return :

```json
{
    "code" : 0
}
```

---

#### 1.4.2.3. 修改单个关系

- PUT /ship
- payload :
  - systemId : 书包柜systemId
  - stuId : 为 null 则释放关系, 为学号则建立关系

```json
{
    "systemId" : 189,
    "studentId" : "1234555"
}
```

- return :

```json
{
    "code" : 0
}
```

---

#### 1.4.2.4. 批量预留柜子ById

- POST /reserved-id
- payload ：
  - array : 代表预留柜子的id的数组

```json
{
    "array" : [1,2,3]
}
```

- return :

```json
{
    "code" : 0,
    "data": true
}
```

---

#### 1.4.2.5. 批量预留柜子ByNumber

- POST /reserved-number
- payload :
  - start : 代表预留柜子开始的number
  - end : 代表预留柜子结束时的number

```json
{
    "floor" : 1,
    "area" : 2,
    "start" : 1,
    "end" : 3
}
```

- return :

```json
{
    "code" : 0,
    "data": true
}
```

#### 1.4.2.6. 按照systemId范围预留柜子

- POST /bookcase-systemId

```json
{
    "systemIdLeft" : 1,
    "systemIdRight" : 10
}
```

- return :

```json
{
   "code" : 0,
   "data": true
}
```

#### 1.4.2.7. 批量增加柜子

- POST /bookcase
- payload :

```json
{
    "floor" : 1,
    "area" : 1,
    "total" : 100
}
```

- return :

```json
{
    "code" : 0
}
```

#### 1.4.2.8. 批量删除柜子by id

- DELETE /bookcase-id
- payload ：
  - array : 代表删除柜子的id的数组

```json
{
    "array" : [1,2,3]
}
```

- return :

```json
{
    "code" : 0,
    "data": true
}
```

#### 1.4.2.9. 按照systemId范围删除柜子

- DELETE /bookcase-systemId

```json
{
    "systemIdLeft" : 1,
    "systemIdLeftRight" : 10
}
```

- return :

```json
{
   "code" : 0,
   "data": true
}
```

#### 1.4.2.10. 批量删除柜子by number

- DELETE /bookcase-number
- payload :
  - start : 代表删除柜子开始的num
  - end : 代表删除柜子结束时的num

```json
{
    "floor" : 1,
    "area" : 2,
    "start" : 1,
    "end" : 3
}
```

- return :

```json
{
    "code" : 0,
    "data": true
}
```

#### 1.4.2.11. 增加区域

- POST /location
- payload :
  - floor : 楼层
  - status : 0 不可预约 | 1 可预约

```json
{
    "floor":1,
    "status":0
}
```

- return :

```json
{
    "code" : 0,
    "data" : true
}

```

#### 1.4.2.12. 删除区域

- DELETE /location/{location}

- return :

```json
{
    "code" : 0,
    "data" : true
}
```

### 1.4.3. 学生管理

#### 1.4.3.1. 添加黑名单

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

---

#### 1.4.3.2. 删除黑名单

- DELETE /list/{studentId}

- return :

```json
{
    "code" : 0
}
```

---

#### 1.4.3.3. 显示黑名单

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

---

#### 1.4.3.4. 上传文件导入学生信息

- POST /student
- payload :
  - Content-Type = multipart/form-data
  - file = 2015.xlsx
- return :

```json
{
    "code" : 0,
    "data" : true
}
```

---

#### 1.4.3.5. 删除所有学生

- DELETE /student
- return :

```json
{
    "code" : 0,
    "data" : true
}
```

---