package SportsService.backend.service;

import SportsService.backend.entity.Board;
import SportsService.backend.entity.LikeBoard;
import SportsService.backend.entity.User;
import SportsService.backend.repository.BoardRepository;
import SportsService.backend.repository.LikeBoardRepository;
import SportsService.backend.repository.UserRepository;
import SportsService.backend.utils.LoginUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 좋아요 기능을 처리하는 서비스 클래스입니다.
 * 사용자의 게시글 좋아요 추가, 삭제 및 상태 확인 기능을 제공합니다.
 *
 * @author minus43
 * @since 2024-10-26
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    /**
     * 사용자가 게시글에 좋아요를 추가합니다.
     * 이미 좋아요가 존재하는 경우 false를 반환합니다.
     *
     * @param boardNum 좋아요를 추가할 게시글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 좋아요 추가 성공 시 true, 실패 시 false
     */
    public boolean makeLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Board board = boardRepository.findById(boardNum).orElseThrow();
            LikeBoard likeBoard = LikeBoard.builder()
                    .board(board)
                    .user(user)
                    .build();
            likeBoardRepository.save(likeBoard);
            boardService.makeLike(boardNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 사용자가 게시글의 좋아요를 삭제합니다.
     * 좋아요가 존재하지 않는 경우 false를 반환합니다.
     *
     * @param boardNum 좋아요를 삭제할 게시글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 좋아요 삭제 성공 시 true, 실패 시 false
     */
    public boolean removeLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Board board = boardRepository.findById(boardNum).orElseThrow();
            LikeBoard likeBoard = likeBoardRepository.findByUserAndBoard(user, board).orElseThrow();
            likeBoardRepository.delete(likeBoard);
            boardService.removeLike(boardNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 현재 사용자가 특정 게시글에 좋아요를 눌렀는지 확인합니다.
     *
     * @param boardNum 확인할 게시글의 고유 식별자
     * @param request 현재 로그인한 사용자 정보를 포함한 HTTP 요청
     * @return 좋아요가 존재하면 true, 없으면 false
     */
    public boolean isLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Board board = boardRepository.findById(boardNum).orElseThrow();
            return (likeBoardRepository.findByUserAndBoard(user, board).isPresent());
        } catch (Exception e) {
            return false;
        }
    }
}
