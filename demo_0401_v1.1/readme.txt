sql脚本在：rental\src\main\resources\static

修改数据库密码

运行访问：localhost/login


v1.1版本修改内容：
1. 存储日志时添加了日期
2. 用户登录、注册、注销时 进行日志记录
3. 用户注册时，取消了用户角色中间表的类和映射，并对业务逻辑层进行了优化。
