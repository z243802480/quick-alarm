package com.hust.hui.alarm.core.execut.spi;

import com.hust.hui.alarm.core.DingdingPublisher;
import com.hust.hui.alarm.core.execut.api.IExecute;

import java.util.List;

/**
 * 钉钉报警
 * Created by @author yihui in 17:26 19/12/25.
 */
public class DingdingExecute implements IExecute {

    @Override
    public void sendMsg(List<String> users, String title, String msg) {
        for (String user : users) {
            DingdingPublisher.sendMessage(title, msg, user);
        }
    }

}
