package SportsService.backend.service;

import SportsService.backend.dto.request.BoardRequestDto;
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
 * 댓글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 댓글 저장, 수정, 삭제, 조회 기능을 제공합니다.
 *
 * 댓글 작성, 수정, 삭제 시 로그인한 사용자의 인증을 확인합니다.
 *
 * @since 2024-10-26
 * @author minus43
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 댓글을 저장하는 메서드입니다.
     * 요청으로부터 로그인한 사용자를 확인하고, 댓글 정보를 데이터베이스에 저장합니다.
     *
     * @param dto 댓글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 저장 성공 시 true, 실패 시 false
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
     * 댓글을 삭제하는 메서드입니다.
     * 로그인한 사용자가 댓글 작성자와 일치할 때만 삭제가 가능합니다.
     *
     * @param replyNum 삭제할 댓글의 고유 식별자
     * @param request HTTP 요청 객체
     * @return 삭제 성공 시 true, 실패 시 false
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
     * 댓글을 수정하는 메서드입니다.
     * 로그인한 사용자가 댓글 작성자와 일치할 때만 수정이 가능합니다.
     *
     * @param dto 수정할 댓글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 수정 성공 시 true, 실패 시 false
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
     * 특정 게시글에 대한 모든 댓글을 조회하는 메서드입니다.
     * 각 댓글 정보를 ReplyResponseDto로 변환하여 반환합니다.
     *
     * @param boardNum 조회할 게시글의 고유 식별자
     * @return 조회한 게시글의 모든 댓글 정보가 담긴 List, 실패 시 null
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

    public boolean likeUpdate(Long replyNum, ReplyRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (loginUser != null) {
                Reply reply = replyRepository.findById(replyNum).orElseThrow();
                reply.setGoodCount(dto.getGoodCount());
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
