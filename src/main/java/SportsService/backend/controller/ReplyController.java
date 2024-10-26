package SportsService.backend.controller;

import SportsService.backend.dto.request.ReplyRequestDto;
import SportsService.backend.dto.response.ReplyResponseDto;
import SportsService.backend.entity.Reply;
import SportsService.backend.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 관련 요청을 처리하는 컨트롤러 클래스입니다.
 * 댓글 생성, 삭제, 수정, 조회 기능을 제공합니다.
 *
 * 클라이언트로부터 요청을 받아 서비스 계층에 전달하고, 처리 결과를 응답으로 반환합니다.
 *
 * @since 2024-10-26
 * @see ReplyService
 * @author minus43
 */
@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 댓글 저장 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 댓글 데이터를 받아 저장 로직을 수행하고, 성공 여부를 반환합니다.
     *
     * @param dto 저장할 댓글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 저장 성공 시 "success" 메시지를 반환, 실패 시 "failed" 메시지를 반환
     */
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ReplyRequestDto dto, HttpServletRequest request) {
        if (replyService.save(dto, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 댓글 삭제 요청을 처리하는 메서드입니다.
     * 주어진 댓글 번호를 이용하여 댓글을 삭제하고, 성공 여부를 반환합니다.
     *
     * @param replyNum 삭제할 댓글의 고유 식별자
     * @param request HTTP 요청 객체
     * @return 삭제 성공 시 "success" 메시지를 반환, 실패 시 "failed" 메시지를 반환
     */
    @DeleteMapping("/delete/{replyNum}")
    public ResponseEntity<String> delete(@PathVariable Long replyNum, HttpServletRequest request) {
        if (replyService.delete(replyNum, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 댓글 수정 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 댓글 데이터를 이용해 댓글을 수정하고, 성공 여부를 반환합니다.
     *
     * @param dto 수정할 댓글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 수정 성공 시 "success" 메시지를 반환, 실패 시 "failed" 메시지를 반환
     */
    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody ReplyRequestDto dto, HttpServletRequest request) {
        if (replyService.modify(dto, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    /**
     * 특정 게시글에 대한 모든 댓글 조회 요청을 처리하는 메서드입니다.
     * 주어진 게시글 번호에 해당하는 모든 댓글 데이터를 조회하여 반환합니다.
     *
     * @param boardNum 조회할 게시글의 고유 식별자
     * @return 조회한 댓글 목록이 담긴 ResponseEntity 객체, 실패 시 "failed" 메시지를 반환
     */
    @GetMapping("/find_all/{boardNum}")
    public ResponseEntity<?> findAll(@PathVariable Long boardNum) {
        List<ReplyResponseDto> replies = replyService.findAll(boardNum);
        if (replies != null) {
            return ResponseEntity.ok().body(replies);
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
