本项目扩展了Nacos对非MySql的外部数据源支持。受本人时间和能力限制，项目将主要扩展对PostgreSQL的支持；同时欢迎fork，star和pr，共同扩展Nacos支持外部数据源的范围。

# 注意：
1、本项目仅基于我日常的工作和学习场景进行测试，无法保证100%覆盖所有功能点。你可以随意使用和修改本项目的代码，但如果需要部署到生产环境，切记部署前先对功能进行充分验证以免造成不必要的损失。本人不对使用本项目代码造成的任何损失负任何责任。

2、本项目的版本号基于对应版本的Nacos生成，并添加一位补丁版本号用于修复开发过程发现的bug。完整的版本号格式类似3.1.1.0

# 构建和使用插件
以下我将以docker compose部署场景为例说明如何构建和使用本项目的扩展，物理部署等其它场景请参考本说明自行调整。

## 环境要求
JDK：17或以上，建议使用open JDK 17对应版本的GraalVM JDK

Maven：3.9.9或以上，建议使用Maven 3.9.9

## 克隆项目
```shell
git clone https://gitee.com/HimuraKenShin/nacos-datasource-plugin.git
git checkout -b <分支名> <版本号tag>
```

## 构建项目
进行项目代码所在目录，使用以下命令完成插件构建：
```shell
mvn clean package
```

## 部署Nacos和插件
使用应数据源插件模块的src/main/resources/META-INF/<数据源>-schema.sql脚本在数据库中创建运行nacos所需的数据表

参考以下内容编写docker compose部署配置
```yaml
services:
  #  nacos：配置及服务注册中心
  nacos:
    image: nacos/nacos-server:v3.1.1
    container_name: nacos-server
    env_file:
      - ./nacos/env/nacos-standalone.env
    volumes:
      - ./nacos/conf:/home/nacos/conf
      - ./nacos/data:/home/nacos/data
      - ./nacos/logs:/home/nacos/logs
      - ./nacos/plugins:/home/nacos/plugins
    ports:
      - "8080:8080"
      - "8848:8848"
      - "9848:9848"
    restart: unless-stopped
```
在部署目录下创建nacos目录，并依次在nacos目录下创建env、data、logs和plugins4个子目录；再次上一步构建生成的nacos-datasource-plugin-base-3.1.1.0.jar、对应数据库JDBC驱动和扩展插件的jar复制到plugins。

复制项目根目录下的resources/conf至部署目录的nacos子目录下

在env子目录下创建nacos-standalone.env，内容如下：
```text
PREFER_HOST_MODE=hostname
MODE=standalone
SPRING_DATASOURCE_PLATFORM=postgresql
DB_DRIVER=org.postgresql.Driver
DB_URL=jdbc:postgresql://postgres:5432/postgres
DB_USER=nacos
DB_PASSWORD=123456
NACOS_AUTH_IDENTITY_KEY=serverIdentity
NACOS_AUTH_IDENTITY_VALUE=security
NACOS_AUTH_TOKEN=VGhpc0lzTXlDdXN0b21TZWNyZXRLZXkwMTIzNDU2Nzg=
```
根据实际情况修改上述文件中并保存，主要修改内容如下：
```text
SPRING_DATASOURCE_PLATFORM：数据库标识，详见DsConstants类中的常量定义
DB_DRIVER：数据库驱动类
DB_URL：jdbc连接
DB_USER：nacos用于连接数据库的用户名
DB_PASSWORD：数据库密码
NACOS_AUTH_IDENTITY_KEY：启用身份认证后用于完成身份认证的键，建议设置为任意足够复杂的字符串
NACOS_AUTH_IDENTITY_VALUE：启用身份认证后用于完成身份认证的值，建议设置为任意足够复杂的字符串
NACOS_AUTH_TOKEN：启用身份认证后用于完成身份认证的令牌，建议使用足够长且复杂的随机字符串
```
在部署目录下执行以下命令部署并启动容器：
```shell
docker compose up nacos -d
```

# 如何扩展其它数据库支持
如果你使用的是SqlServer等其它数据库，可参考nacos-datasource-plugin-postgresql模块的目录和文件结构自行开发新的支持插件。同时也欢迎将你的成果pr到本项目。

当你使用新的插件前，注意需要同步修改nacos-standalone.env中对应的配置项。

## 特别提醒
受Nacos分页查询的机制限制，某些SQL的LIMIT和OFFSET的值必须写死在代码中，不能通过参数传入；详细原因可自行查阅Nacos源码中的ExternalStoragePaginationHelperImpl.doFetchPage方法。

如果你不确定正在定制的sql是否可以动态传入分页参数，建议参考Nacos官方对应的MySQL实现编写代码。