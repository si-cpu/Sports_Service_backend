package SportsService.backend.service;

import SportsService.backend.entity.LikeReply;
import SportsService.backend.entity.Reply;
import SportsService.backend.entity.User;
import SportsService.backend.repository.BoardRepository;
import SportsService.backend.repository.LikeReplyRepository;
import SportsService.backend.repository.ReplyRepository;
import SportsService.backend.repository.UserRepository;
import SportsService.backend.utils.LoginUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 댓글 좋아요 기능을 처리하는 서비스 클래스입니다.
 * 사용자의 댓글 좋아요 추가, 삭제 및 상태 확인 기능을 제공합니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *   <li>댓글 좋아요 추가</li>
 *   <li>댓글 좋아요 삭제</li>
 *   <li>특정 게시글의 댓글 좋아요 상태 확인</li>
 * </ul>
 *
 * @author minus43
 * @since 2024-10-26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LikeReplyService {
    private final LikeReplyRepository likeReplyRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    /**
     * 사용자가 댓글에 좋아요를 추가합니다.
     * 로그인한 사용자만 좋아요를 추가할 수 있습니다.
     *
     * @param replyNum 좋아요를 추가할 댓글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 좋아요 추가 성공 시 true, 실패 시 false
     * @throws RuntimeException 사용자나 댓글을 찾을 수 없는 경우
     */
    public boolean makeLike(Long replyNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Reply reply = replyRepository.findById(replyNum).orElseThrow();
            LikeReply likeReply = LikeReply.builder()
                    .user(user)
                    .reply(reply)
                    .build();
            likeReplyRepository.save(likeReply);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 사용자가 댓글의 좋아요를 삭제합니다.
     * 이미 좋아요한 댓글에 대해서만 삭제가 가능합니다.
     *
     * @param replyNum 좋아요를 삭제할 댓글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 좋아요 삭제 성공 시 true, 실패 시 false
     * @throws RuntimeException 사용자, 댓글 또는 좋아요 정보를 찾을 수 없는 경우
     */
    public boolean removeLike(Long replyNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Reply reply = replyRepository.findById(replyNum).orElseThrow();
            LikeReply likeReply = likeReplyRepository.findByUserAndReply(user, reply).orElseThrow();
            likeReplyRepository.delete(likeReply);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 특정 게시글의 댓글들에 대한 현재 사용자의 좋아요 상태를 확인합니다.
     * 로그인한 사용자가 좋아요한 댓글들의 번호 목록을 반환합니다.
     *
     * @param boardNum 확인할 게시글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 사용자가 좋아요한 댓글 번호 목록, 실패 시 null
     * @throws RuntimeException 사용자나 게시글을 찾을 수 없는 경우
     */
    public List<Long> isLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            List<Reply> replies = replyRepository.findByBoard(boardRepository.findById(boardNum).orElseThrow());
            List<Long> replyNums = new ArrayList<>();
            for (Reply reply : replies) {
                if(likeReplyRepository.findByUserAndReply(user, reply).isPresent()){
                    replyNums.add(reply.getReplyNum());
                }
            }
            return replyNums;
        } catch (Exception e) {
            return null;
        }
    }
}
