package SportsService.backend.service;

import SportsService.backend.dto.request.ReplyRequestDto;
import SportsService.backend.dto.response.ReplyResponseDto;
import SportsService.backend.entity.Reply;
import SportsService.backend.repository.BoardRepository;
import SportsService.backend.repository.ReplyRepository;
import SportsService.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static SportsService.backend.utils.LoginUtils.isLogin;

/**
 * 댓글 관련 기능을 처리하는 서비스 클래스입니다.
 * 댓글 작성, 수정, 삭제, 조회 및 좋아요 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>댓글 작성</li>
 *   <li>댓글 수정</li>
 *   <li>댓글 삭제</li>
 *   <li>게시글별 댓글 목록 조회</li>
 *   <li>댓글 좋아요 처리</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 댓글을 저장합니다.
     * 로그인한 사용자만 댓글을 작성할 수 있습니다.
     *
     * @param dto 댓글 정보를 담은 DTO (내용, 게시글 번호)
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 저장 성공 시 true, 실패 시 false
     * @throws RuntimeException 게시글이나 사용자를 찾을 수 없는 경우
     */
    public boolean save(ReplyRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (loginUser != null) {
                Reply reply = Reply.builder()
                        .board(boardRepository.findById(dto.getBoardNum()).orElseThrow())
                        .content(dto.getContent())
                        .user(userRepository.findByNickName(loginUser).orElseThrow())
                        .build();
                replyRepository.save(reply);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 댓글을 삭제합니다.
     * 댓글 작성자만 삭제할 수 있습니다.
     *
     * @param replyNum 삭제할 댓글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 삭제 성공 시 true, 실패 시 false
     * @throws RuntimeException 댓글을 찾을 수 없는 경우
     */
    public boolean delete(Long replyNum, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (replyRepository.findById(replyNum).orElseThrow().getUser().getNickName().equals(loginUser)) {
                replyRepository.deleteById(replyNum);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 댓글을 수정합니다.
     * 댓글 작성자만 수정할 수 있습니다.
     *
     * @param dto 수정할 댓글 정보를 담은 DTO (댓글 번호, 내용, 작성자)
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 수정 성공 시 true, 실패 시 false
     * @throws RuntimeException 댓글을 찾을 수 없는 경우
     */
    public boolean modify(ReplyRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (!Objects.equals(dto.getWriter(), loginUser)) {
                return false;
            }
            Reply reply = replyRepository.findById(dto.getReplyNum()).orElse(null);
            if (reply != null) {
                reply.setContent(dto.getContent());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 특정 게시글의 모든 댓글을 조회합니다.
     *
     * @param boardNum 댓글을 조회할 게시글의 고유 식별자
     * @return 댓글 목록을 담은 DTO 리스트, 실패 시 null
     */
    public List<ReplyResponseDto> findAll(Long boardNum) {
        try {
            List<Reply> replies = replyRepository.findAll();
            List<ReplyResponseDto> repliesDto = new ArrayList<>();
            for (Reply reply : replies) {
                if (reply.getBoard().getBoardNum().equals(boardNum)) {
                    repliesDto.add(ReplyResponseDto.builder()
                            .replyNum(reply.getReplyNum())
                            .boardNum(reply.getBoard().getBoardNum())
                            .content(reply.getContent())
                            .writer(reply.getUser().getNickName())
                            .regDate(reply.getRegDate())
                            .modDate(reply.getModDate())
                            .goodCount(reply.getGoodCount())
                            .build()
                    );
                }
            }
            return repliesDto;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 댓글의 좋아요 수를 증가시킵니다.
     *
     * @param replyNum 좋아요를 증가시킬 댓글의 고유 식별자
     * @return 증가 성공 시 true, 실패 시 false
     * @throws RuntimeException 댓글을 찾을 수 없는 경우
     */
    public boolean makeLike(Long replyNum) {
        try {
            Reply reply = replyRepository.findById(replyNum).orElseThrow();
            reply.setGoodCount(reply.getGoodCount() + 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 댓글의 좋아요 수를 감소시킵니다.
     *
     * @param replyNum 좋아요를 감소시킬 댓글의 고유 식별자
     * @return 감소 성공 시 true, 실패 시 false
     * @throws RuntimeException 댓글을 찾을 수 없는 경우
     */
    public boolean removeLike(Long replyNum) {
        try {
            Reply reply = replyRepository.findById(replyNum).orElseThrow();
            reply.setGoodCount(reply.getGoodCount() - 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
