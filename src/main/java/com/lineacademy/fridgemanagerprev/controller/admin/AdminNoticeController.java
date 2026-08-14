package com.lineacademy.fridgemanagerprev.controller.admin;

import com.lineacademy.fridgemanagerprev.domain.notice.Notice;
import com.lineacademy.fridgemanagerprev.dto.notice.request.NoticeRequest;
import com.lineacademy.fridgemanagerprev.dto.notice.response.NoticeResponse;
import com.lineacademy.fridgemanagerprev.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class AdminNoticeController {
    private final NoticeService noticeService;

    // Mapping 정보에 주소를 안 적으면 , Controller에 기재된
    // 주소로 들어왔을 때 이 메서드가 실행됨
    @PreAuthorize("hasRole('ADMIN')") // 매개변수 자리에 String을 써주고 있음
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotice(
            @Valid @RequestBody NoticeRequest request
            ) {
        try {
            Notice notice = noticeService.createNotice(request);
            return ResponseEntity.ok(Map.of(
                    "message", "공지사항이 정상적으로 등록되었습니다.",
                    "data", NoticeResponse.from(notice)
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message","서버 에러가 발생되었습니다."
            ));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{noticeId)")
    public ResponseEntity<Map<String, Object>> deleteNotice(
            @PathVariable Long noticeId
    ) {
        try {
            noticeService.deleteNotice(noticeId);
            return ResponseEntity.ok(Map.of(
                    "message","공지사항이 성공적으로 삭제 되었습니다."
            ));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("NOT_FOUND_NOTICE")) {
                return ResponseEntity.status(404).body(Map.of(
                        "message", "서버 에러가 발생하였습니다."
                ));
            }
        }
    }
}
