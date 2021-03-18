package com.hust.hui.alarm.grpc;

import com.hust.hui.alarm.core.AlarmWrapper;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.List;

public class AlarmserviceImpl extends AlarmserviceGrpc.AlarmserviceImplBase {
    @Override
    public void sendMsg(AlarmserviceOuterClass.AlarmDefaultRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsg(request.getKey(), request.getContent());
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    @Override
    public void sendNormalMsg(AlarmserviceOuterClass.AlarmNormalRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsg(request.getKey(), request.getTitle(), request.getContent());
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    @Override
    public void sendTemplateMsg(AlarmserviceOuterClass.AlarmTemplateRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsg(request.getKey(), request.getTitle(), request.getContent(), request.getTemplate());
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    @Override
    public void sendMsgToUser(AlarmserviceOuterClass.AlarmToUserRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsgToUser(request.getKey(), request.getContent(), Arrays.asList(request.getAlarmUser().split(",")));
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    @Override
    public void sendNormalMsgToUser(AlarmserviceOuterClass.AlarmNormalToUserRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsgToUser(request.getKey(), request.getTitle(), request.getContent(), Arrays.asList(request.getAlarmUser().split(",")));
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    @Override
    public void sendMTemplatesgToUser(AlarmserviceOuterClass.AlarmTemplateToUserRequest request, StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        try {
            AlarmWrapper.getInstance().sendMsgToUser(request.getKey(), request.getTitle(), request.getContent(), request.getTemplate(), Arrays.asList(request.getAlarmUser().split(",")));
            responseOK(responseObserver);
        } catch (Exception e) {
            responseError(responseObserver, e);
        }
    }

    private void responseOK(StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver) {
        AlarmserviceOuterClass.AlarmResponse.Builder builder = AlarmserviceOuterClass.AlarmResponse.newBuilder();
        responseObserver.onNext(builder.setCode(200).setMsg("操作成功").build());
        responseObserver.onCompleted();
    }

    private void responseError(StreamObserver<AlarmserviceOuterClass.AlarmResponse> responseObserver, Exception e) {
        AlarmserviceOuterClass.AlarmResponse.Builder builder = AlarmserviceOuterClass.AlarmResponse.newBuilder();
        responseObserver.onNext(builder.setCode(500).setMsg("操作失败 : e : " + e.getMessage()).build());
        responseObserver.onCompleted();
    }
}
