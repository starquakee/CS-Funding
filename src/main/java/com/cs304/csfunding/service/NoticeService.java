package com.cs304.csfunding.service;

import com.cs304.csfunding.api.NoticeDTO;
import com.cs304.csfunding.entity.Notice;
import com.cs304.csfunding.mapper.NoticeMapper;
import com.cs304.csfunding.util.HttpContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NoticeService {
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {this.noticeMapper = noticeMapper;}

    public String testInsert(NoticeDTO noticeDTO, int to) {
        Notice notice = new Notice();
        notice.setContent(noticeDTO.getContent());
        notice.setTime(Long.toString(System.currentTimeMillis()));
        notice.setNoticefrom(HttpContextUtil.getRequestUuid());
        notice.setNoticeto(to);
        noticeMapper.addNotice(notice);
        return "";
    }

    public List<Notice> testQueryAll(){
        return noticeMapper.getAllNotice();
    }

    public Notice testQueryByID(int uuid) { return noticeMapper.queryNoticeByID(uuid); }
    public List<Notice> testQueryByNoticeTo(int uuid) { return noticeMapper.queryNoticeByNoticeTo(uuid); }

    public void testDeleteByID(int uuid) { noticeMapper.deleteNotice(uuid); }
}
