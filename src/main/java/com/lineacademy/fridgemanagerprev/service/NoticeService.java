package com.lineacademy.fridgemanagerprev.service;

import com.lineacademy.fridgemanagerprev.domain.notice.Notice;
import com.lineacademy.fridgemanagerprev.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public Page<Notice> getNoticeList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return noticeRepository.findAllByOrderByIdDesc(pageable);
    }
}
