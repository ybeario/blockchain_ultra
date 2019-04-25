# block
区块链电子证据保全项目(个人开发中，未完待续)
参考网址： https://ybear-web.com/index
个人博客： https://blog.csdn.net/qq_24407053


页面截图及效果参考：

 https://blog.csdn.net/qq_24407053/article/details/85618314

开发环境：
1. JDK1.8
2. Maven
3. Spring Boot + SpringMVC + Mybatis
4. Spring Security 
5. web3j
6. EVM
7. docker

---

当前进度： 
1. 提供基于Spring Security 的安全网站
2. 基于web3j实现对以太坊的链上代码调用（Solidity相关代码放在resource下）
3. 封装Redis功能，能够对热点数据进行保存，可直接调用
4. 证据保全，校验，数据分析，证据结论上传全流程逻辑开发完成
5.前端页面开发尚不完善

运行方法：
1. 以maven工程导入
2. 安装docker,并在命令行中运行
```
docker run -d --name ethereum -p 8545:8545 -p 30303:30303 ethereum/client-go --rpc --rpcaddr "0.0.0.0" --rpcapi="db,eth,net,web3,personal" --rpccorsdomain "*" --dev
```
3. 运行springboot项目
4. 账户user,admin,researcher 密码：123456（目前没有集成数据库，是写死的，不能注册）
5. .../user进入功能页面，提供存证和查证功能
