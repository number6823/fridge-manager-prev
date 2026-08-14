package com.lineacademy.fridgemanagerprev.controller;

import com.lineacademy.fridgemanagerprev.domain.notice.Notice;
import com.lineacademy.fridgemanagerprev.dto.common.PaginationResponse;
import com.lineacademy.fridgemanagerprev.dto.notice.response.NoticeResponse;
import com.lineacademy.fridgemanagerprev.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getNoticeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            // Page라고 하는 객체 안에는 여러가지 정보를 한꺼번에 받아옴
            // totalElements, totalPages 등
            Page<Notice> noticePage = noticeService.getNoticeList(page, size);

            List<NoticeResponse> list = noticePage.getContent().stream()
                    .map(NoticeResponse::from)
                    .toList();

            long total = noticePage.getTotalElements();

            PaginationResponse<NoticeResponse> paginationData = PaginationResponse.of(
                    page,
                    size,
                    total,
                    list
            );

            return ResponseEntity.ok(Map.of(
                    "message", "공지사항 목록을 불러오는데 성공했습니다.",
                    "data", paginationData
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "공지사항 목록 조회 중 서버 에러가 발생되었습니다."
            ));
        }
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<Map<String, Object>> getNoticeById(
            @PathVariable Long noticeId // 동적라우팅으로 주소에서 가져온 값을 집어넣는 어노테이션
    ) {
        try {
            Notice notice = noticeService.getNoticeById(noticeId);
            return ResponseEntity.ok(Map.of(
                    "message", "공지사항 조회 성공",
                    "data", NoticeResponse.from(notice)
            ));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("NOT_FOUND_NOTICE")) {
                return ResponseEntity.status(404).body(Map.of(
                        "message","해당 공지사항을 찾을 수 없습니다."
                ));
            }
            return ResponseEntity.status(500).body(Map.of(
                    "message","서버 에러가 발생되었습니다."
            ));
        }
    }
}