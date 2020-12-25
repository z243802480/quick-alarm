# 继承 vert.x 镜像
FROM vertx/vertx4

#                                                       (1)
ENV VERTICLE_NAME com.hust.hui.alarm.api.AlarmApiMainVerticle
ENV VERTICLE_FILE core-1.0.jar

# 设置 verticles 存放路径
ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

# 拷贝 verticle 至容器中                   (2)
COPY $VERTICLE_FILE $VERTICLE_HOME/

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec vertx run $VERTICLE_NAME -cp $VERTICLE_HOME/*"]