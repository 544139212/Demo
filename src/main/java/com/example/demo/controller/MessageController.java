package com.example.demo.controller;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.MessageModelMapper;
import com.example.demo.mapper.UserModelMapper;
import com.example.demo.model.MessageModel;
import com.example.demo.model.UserModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Message;
import com.example.demo.vo.Pagination;
import com.example.demo.vo.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/message")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    SimpleDateFormat format = new SimpleDateFormat("MM-dd");

    @Autowired
    MessageModelMapper messageModelMapper;

	@Autowired
    UserModelMapper userModelMapper;

    /**
     * 添加消息信息<>当前登录用户</>
     * @param message
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Message message) {
        logger.debug("参数:{}", JsonUtils.deserializer(message));

        Result<Void> result = new Result<>();
        try {
            UserModel criteria = new UserModel();
            criteria.setId(message.getTargetUserId());
            List<UserModel> userModelList = userModelMapper.search(criteria);
            if (userModelList == null || userModelList.isEmpty()) {
                result.setCode(ResponseStatusEnum.USER_NOT_FOUND.getCode());
                result.setMsg(ResponseStatusEnum.USER_NOT_FOUND.getMsg());
                return result;
            }

            JPushClient jpushClient = new JPushClient("83cfc9359bd16bd2c5e00d8d", "a568c6a095d73e7536b62dd8", null, ClientConfig.getInstance());
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.android())
                    .setAudience(Audience.registrationId(userModelList.get(0).getJpushid()))
                    .setMessage(cn.jpush.api.push.model.Message.content(message.getMessage()))
                    .build();
            PushResult pushResult = jpushClient.sendPush(payload);
            if (pushResult.getResponseCode() == 200 && pushResult.statusCode == 0) {
                String str = pushResult.sendno + ";"
                        + pushResult.statusCode + ";"
                        + pushResult.msg_id + ";";
                logger.debug(str);

                MessageModel messageModel = new MessageModel();
                BeanUtils.copyProperties(message, messageModel);
                messageModel.setSourceUserId(Context.get().getUserId());
                messageModel.setCreateBy(Context.get().getUserId().toString());
                messageModel.setCreateDate(new Date());
                messageModel.setUpdateBy(Context.get().getUserId().toString());
                messageModel.setUpdateDate(new Date());
                messageModelMapper.insertSelective(messageModel);

                result.setCode(ResponseStatusEnum.SUCCESS.getCode());
                result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
            } else {
                String str = pushResult.getResponseCode() + ";"
                        + pushResult.error.getCode() + ";"
                        + pushResult.error.getMessage() + ";"
                        + pushResult.sendno + ";"
                        + pushResult.statusCode + ";"
                        + pushResult.msg_id + ";";
                logger.debug(str);

                result.setCode(ResponseStatusEnum.ERROR.getCode());
                result.setMsg(ResponseStatusEnum.ERROR.getMsg());
            }
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error(e.getMessage());
            result.setCode(ResponseStatusEnum.ERROR.getCode());
            result.setMsg(ResponseStatusEnum.ERROR.getMsg());

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error(e.getMessage());
            result.setCode(ResponseStatusEnum.ERROR.getCode());
            result.setMsg(ResponseStatusEnum.ERROR.getMsg());
        }
        return result;
    }

    /**
     * 获取分页消息<>当前登录用户</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/{targetUserId}/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Message>> list(@PathVariable Integer targetUserId, @PathVariable Integer pageNum) {
        Result<Pagination<Message>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        MessageModel criteria = new MessageModel();
        criteria.setSourceUserId(Context.get().getUserId());
        criteria.setTargetUserId(targetUserId);
        Page<MessageModel> page = (Page<MessageModel>)messageModelMapper.findMessages(criteria);//TODO:优化
        List<Message> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(messageModel -> {
                Message message = new Message();
                BeanUtils.copyProperties(messageModel, message);
                list.add(message);
            });
        }
        Pagination<Message> pagination = new Pagination<>();
        pagination.setPageNum(page.getPageNum());
        pagination.setPageSize(page.getPageSize());
        pagination.setPages(page.getPages());
        pagination.setTotal(page.getTotal());
        pagination.setList(list);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(pagination);
        return result;
    }

}
