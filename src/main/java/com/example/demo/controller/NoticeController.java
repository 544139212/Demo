package com.example.demo.controller;

import com.example.demo.context.Context;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.mapper.NoticeModelMapper;
import com.example.demo.model.NoticeModel;
import com.example.demo.util.JsonUtils;
import com.example.demo.vo.Notice;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/notice")
public class NoticeController {
    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    NoticeModelMapper noticeModelMapper;

    /**
     * 添加通知信息<>管理</>
     * @param notice
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result<Void> add(@RequestBody @Valid Notice notice) {
        logger.debug("参数:{}", JsonUtils.deserializer(notice));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        NoticeModel criteria = new NoticeModel();
        criteria.setTitle(notice.getTitle());
        List<NoticeModel> list = noticeModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty()) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        NoticeModel noticeModel = new NoticeModel();
        BeanUtils.copyProperties(notice, noticeModel);
        noticeModel.setCreateBy(Context.get().getUserId().toString());
        noticeModel.setCreateDate(new Date());
        noticeModel.setUpdateBy(Context.get().getUserId().toString());
        noticeModel.setUpdateDate(new Date());
        noticeModelMapper.insertSelective(noticeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 更新通知信息<>管理</>
     * @param notice
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Result<Void> update(@RequestBody @Valid Notice notice) {
        logger.debug("参数:{}", JsonUtils.deserializer(notice));

        Result<Void> result = new Result<>();
        //TODO:鉴权
        if (notice.getId() == null) {
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(ResponseStatusEnum.INVALID.getMsg());
            return result;
        }
        NoticeModel criteria = new NoticeModel();
        criteria.setTitle(notice.getTitle());
        List<NoticeModel> list = noticeModelMapper.search(criteria);//TODO:优化
        if (list != null && !list.isEmpty() && !list.get(0).getId().equals(notice.getId())) {
            result.setCode(ResponseStatusEnum.EXIST.getCode());
            result.setMsg(ResponseStatusEnum.EXIST.getMsg());
            return result;
        }
        NoticeModel noticeModel = noticeModelMapper.selectByPrimaryKey(notice.getId());
        if (noticeModel == null) {
            result.setCode(ResponseStatusEnum.NOT_FOUND.getCode());
            result.setMsg(ResponseStatusEnum.NOT_FOUND.getMsg());
            return result;
        }
        BeanUtils.copyProperties(notice, noticeModel);
        noticeModel.setUpdateBy(Context.get().getUserId().toString());
        noticeModel.setUpdateDate(new Date());
        noticeModelMapper.updateByPrimaryKeySelective(noticeModel);
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        return result;
    }

    /**
     * 获取通知信息<>用户/管理</>
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result<Notice> get(@PathVariable Integer id) {
        Result<Notice> result = new Result<>();
        NoticeModel criteria = new NoticeModel();
        criteria.setId(id);
        List<NoticeModel> list = noticeModelMapper.search(criteria);//TODO:优化
        Notice notice = null;
        if (list != null && !list.isEmpty()) {
            notice = new Notice();
            BeanUtils.copyProperties(list.get(0), notice);
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(notice);
        return result;
    }

    /**
     * 获取全部通知<>用户/管理</>
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<Notice>> list() {
        Result<List<Notice>> result = new Result<>();
        NoticeModel criteria = new NoticeModel();
        List<NoticeModel> list = noticeModelMapper.search(criteria);//TODO:优化
        List<Notice> list1 = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            list.stream().forEach(noticeModel -> {
                Notice notice = new Notice();
                BeanUtils.copyProperties(noticeModel, notice);
                list1.add(notice);
            });
        }
        result.setCode(ResponseStatusEnum.SUCCESS.getCode());
        result.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        result.setData(list1);
        return result;
    }

    /**
     * 获取分页通知<>管理</>
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}", method = RequestMethod.GET)
    public Result<Pagination<Notice>> list(@PathVariable Integer pageNum) {
        Result<Pagination<Notice>> result = new Result<>();
        PageHelper.startPage(pageNum, 20);
        NoticeModel criteria = new NoticeModel();
        Page<NoticeModel> page = (Page<NoticeModel>)noticeModelMapper.search(criteria);//TODO:优化
        List<Notice> list = new ArrayList<>();
        if (page.getResult() != null && !page.getResult().isEmpty()) {
            page.getResult().stream().forEach(noticeModel -> {
                Notice notice = new Notice();
                BeanUtils.copyProperties(noticeModel, notice);
                list.add(notice);
            });
        }
        Pagination<Notice> pagination = new Pagination<>();
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
