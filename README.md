## IDCLOUD

### Demo Service 本地运行：
* 先在本地运行zookeeper3.4.6以上版本：zookeeper-3.4.6\bin\zkServer.cmd，默认监听127.0.0.1:2181
* 启动demo服务，运行idcloud-user-provider中的ProviderMain
* 启动消费者，运行idcloud-user-consumer中的ConsumerMain
* 可以看到服务提供者和消费者的console都有 hello world 日志输出，说明远程调用成功

### Dubbo 版本：
~~为了升级spring（3.2.16.RELEASE）版本和java（1.8）版本，切换到dubbox，但是maven中央库里没有dubbox的依赖，我们又没有maven私服，所以要自己clone源码然后instal到自己的本地repository中：~~
* ~~clone源码到本地：git clone --branch dubbox-2.8.4 http://118.126.138.5:8080/xcloud/dubbo.git~~
* ~~install到本地repository，进入dubbo根目录运行：mvn clean install -Dmaven.test.skip~~
* **我们搭了nexus私服了，以后直接去私服上拿这个包就行了，不用这么麻烦了**

### maven私服镜像
我们已经搭了内部的maven私服，大家在自己的maven配置文件settings.xml中注释掉原来的中央仓库镜像，加入我们自己的镜像：
**（如果不用我们的私服，将无法下载dubbo最新依赖，我们可能会基于dubbo开源版本做一些定制然后发布到私服仓库）**

    <mirror>
        <id>mirrorId</id>
        <mirrorOf>central</mirrorOf>
        <name>Human Readable Name for this Mirror.</name>
        <url>http://118.126.138.5:8081/nexus/content/groups/public/</url>
    </mirror>

然后在profile中加入以下内容，所有请求都走我们自己的私服：

    <profile>
      <id>nexus</id>
      <repositories>
        <repository>
          <id>nexus</id>
          <name>Nexus</name>
          <url>http://118.126.138.5:8081/nexus/content/groups/public/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>


我本地的完整配置示例如下：

    <?xml version="1.0" encoding="UTF-8"?>
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <!-- localRepository
       | The path to the local repository maven will use to store artifacts.
       |
       | Default: ~/.m2/repository
      -->
      <localRepository>E:/maven/repo</localRepository>
      <pluginGroups>
      </pluginGroups>

      <proxies>
      </proxies>

      <servers>
      </servers>

      <mirrors>
        <!-- <mirror>
          <id>mirrorId</id>
          <mirrorOf>central</mirrorOf>
          <name>Human Readable Name for this Mirror.</name>
          <url>http://central.maven.org/maven2/</url>
        </mirror> -->
        <mirror>
          <id>mirrorId</id>
          <mirrorOf>central</mirrorOf>
          <name>Human Readable Name for this Mirror.</name>
          <url>http://118.126.138.5:8081/nexus/content/groups/public/</url>
        </mirror>
      </mirrors>

      <profiles>
        <profile>
          <id>nexus</id>
          <repositories>
            <repository>
              <id>nexus</id>
              <name>Nexus</name>
              <url>http://118.126.138.5:8081/nexus/content/groups/public/</url>
              <releases>
                <enabled>true</enabled>
              </releases>
              <snapshots>
                <enabled>true</enabled>
              </snapshots>
            </repository>
          </repositories>
        </profile>
      </profiles>
    </settings>
