package com.example.demo.controller;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.example.demo.mapper.MessageModelMapper;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.dto.MessageListRespWsDTO;
import com.example.demo.model.MessageModel;
import com.example.demo.dto.MessageWsDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    SimpleDateFormat format = new SimpleDateFormat("MM-dd");

    @Autowired
    MessageModelMapper messageModelMapper;

	@Autowired
    UserModelMapper userModelMapper;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResultDTO send(@RequestParam(value = "type", required = true) String type,
                         @RequestParam(value = "oPhone", required = true) String oPhone,
                         @RequestParam(value = "tPhone", required = true) String tPhone,
                         @RequestParam(value = "message", required = true) String message) {
        UserModel criteria = new UserModel();
        criteria.setPhone(tPhone);

        List<UserModel> userModelList = userModelMapper.search(criteria);
        if (userModelList == null || userModelList.isEmpty()) {
            return new ResultDTO(404, "对方未注册", null);
        }
        if ("01".equals(type)) {
            if (oPhone.equals(tPhone)) {
                return new ResultDTO(404, "不能添加自己", null);
            }
            List<MessageModel> messageModels = messageModelMapper.findMessages(oPhone, tPhone);
            if (messageModels != null && !messageModels.isEmpty()) {
                return new ResultDTO(999, "您已添加对方，不能重复添加", null);
            }
        }
        try {
            JPushClient jpushClient = new JPushClient("83cfc9359bd16bd2c5e00d8d", "a568c6a095d73e7536b62dd8", null, ClientConfig.getInstance());
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.registrationId(userModelList.get(0).getJpushid()))
                    .setMessage(Message.content(message))
                    .build();
            PushResult result = jpushClient.sendPush(payload);
            if (result.getResponseCode() == 200 && result.statusCode == 0) {
                String str = result.sendno + ";"
                        + result.statusCode + ";"
                        + result.msg_id + ";";

                MessageModel messageModel = new MessageModel();
                messageModel.setOphone(oPhone);
                messageModel.setTphone(tPhone);
                messageModel.setMessage(message);
                messageModel.setDate(new Date());
                messageModelMapper.insertSelective(messageModel);

                return new ResultDTO(200, str, null);
            } else {
                String str = result.getResponseCode() + ";"
                        + result.error.getCode() + ";"
                        + result.error.getMessage() + ";"
                        + result.sendno + ";"
                        + result.statusCode + ";"
                        + result.msg_id + ";";
                return new ResultDTO(200, str, null);
            }
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            e.printStackTrace();
            return new ResultDTO(500, e.getMessage(), null);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            e.printStackTrace();
            return new ResultDTO(500, e.getMessage(), null);
        }
	}

	@RequestMapping(value = "/getMessages", method = RequestMethod.GET)
	public MessageListRespWsDTO getMessages(String oPhone, String tPhone) {
        List<MessageModel> messageModels = messageModelMapper.findMessages(oPhone, tPhone);

        List<MessageWsDTO> list = new ArrayList<>();
        if (messageModels != null && !messageModels.isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            for (MessageModel messageModel : messageModels) {
                list.add(modelMapper.map(messageModel, MessageWsDTO.class));
            }
        }

        return new MessageListRespWsDTO(200, "success", list);
	}

    @RequestMapping(value = "/getRecentContacts", method = RequestMethod.GET)
    public MessageListRespWsDTO getMessages(String oPhone) {
        List<MessageModel> messageModels = messageModelMapper.findRecentContacts(oPhone);
        List<MessageWsDTO> list = new ArrayList<>();
        if (messageModels != null && !messageModels.isEmpty()) {
            ModelMapper modelMapper = new ModelMapper();
            for (MessageModel messageModel : messageModels) {
                MessageWsDTO messageWsDTO = modelMapper.map(messageModel, MessageWsDTO.class);
                messageWsDTO.setDate(format.format(messageModel.getDate()));
                list.add(messageWsDTO);
            }
        }

        return new MessageListRespWsDTO(200, "success", list);
    }


}
