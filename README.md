# 基于Netty的简易聊天室
> 该repo目前只是一个netty学习的demo，使用命令行进行控制，主要体验一下netty通信的内核思想，未来有机会将功能完善一下



## 功能

+ 实现用户登录（预留认证函数，实际是伪登录）
+ 实现用户互聊
+ 支持用户创建群聊并自主删除或显示群聊成员



## 优化

+ 用退避算法支持客户端连接，超过连接时间客户端取消连接
+ 支持心跳检测



## Run

```shell
run NewNettyClient
run NewNettyServer
```



## TODO List

+ 前端页面优化