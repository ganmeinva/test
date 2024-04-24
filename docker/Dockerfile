# 使用官方的 Java 11 镜像作为基础镜像
FROM openjdk:8

# 在容器中创建一个目录用于存放 jar 包
RUN mkdir /app

# 将宿主机上的 /root/xxoo 目录挂载到容器内的 /app 目录下
VOLUME /app

# 将宿主机上的 jar 包复制到容器内的 /app 目录下
COPY /root/xxoo/*.jar /app/

# 指定容器启动时执行的命令
CMD ["java", "-jar", "/app/one-0.0.1-SNAPSHOT.jar"]
