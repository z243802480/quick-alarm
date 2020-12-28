package com.hust.hui.alarm.api;

import com.alibaba.fastjson.JSONObject;
import com.hust.hui.alarm.api.entity.Result;
import com.hust.hui.alarm.core.AlarmWrapper;
import com.hust.hui.alarm.core.loader.ConfLoaderFactory;
import com.hust.hui.alarm.core.loader.api.IConfLoader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class AlarmApiMainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route(HttpMethod.GET, "/alarm/*")
                .handler(StaticHandler.create("static"));

//        router.route(HttpMethod.GET, "/alarm")
//                .handler(StaticHandler.create("static"));

        router.route(HttpMethod.POST, "/alarm/api/send")
              .consumes("application/json")
              .handler(BodyHandler.create())
              .handler(context -> {

            JsonObject object = context.getBodyAsJson();
            System.out.println(object.toString());

            JsonArray users = object.getJsonArray("users");
            if (users == null) {
                AlarmWrapper.getInstance().sendMsg(object.getString("key"),
                        object.getString("title"),
                        object.getString("msg"));
            }else {
                AlarmWrapper.getInstance().sendMsgToUser(object.getString("key"),
                        object.getString("title"),
                        object.getString("msg"), users.getList());
            }
            context.json(new JsonObject(JSONObject.toJSONString(Result.ok())));
        });

        router.route(HttpMethod.POST, "/alarm/api/config")
                .consumes("application/json")
                .handler(BodyHandler.create())
                .handler(context -> {

                    JsonObject object = context.getBodyAsJson();

                    IConfLoader confLoader = ConfLoaderFactory.loader();
                    String path = confLoader.getRegisterInfo().getAlarmConfPath();
                    File file;
                    if (path.startsWith("/")) {
                        file = new File(path);
                    } else {
                        URL url = this.getClass().getClassLoader().getResource(path);
                        file = new File(url.getFile());
                    }

                    writeContentToFile(file, object.toString());

                    context.json(new JsonObject(JSONObject.toJSONString(Result.ok())));
                });

        router.route(HttpMethod.GET, "/alarm/api/config/get")
                .consumes("application/json")
                .handler(context -> {

                    IConfLoader confLoader = ConfLoaderFactory.loader();
                    String path = confLoader.getRegisterInfo().getAlarmConfPath();
                    File file;
                    if (path.startsWith("/")) {
                        file = new File(path);
                    } else {
                        URL url = this.getClass().getClassLoader().getResource(path);
                        file = new File(url.getFile());
                    }
                    try {
                        String content = FileUtils.readFileToString(file, "UTF-8");
                        JSONObject o = JSONObject.parseObject(content);
                        context.json(new JsonObject(JSONObject.toJSONString(Result.ok(o))));
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.json(new JsonObject(JSONObject.toJSONString(Result.error())));
                    }
                });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8888)
                .onSuccess(server ->
                        System.out.println(
                                "HTTP server started on port " + server.actualPort()
                        )
                );
    }

    /**
     * 写入文件内容
     * @param fileName
     */
    public static void writeContentToFile(File file, String content) {
        try {
            if(! file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

