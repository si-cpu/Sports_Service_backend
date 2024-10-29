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

@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

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
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Board board = boardRepository.findById(boardNum).orElseThrow();
            LikeBoard likeBoard = likeBoardRepository.findByUserAndBoard(user, board).orElseThrow();
            likeBoardRepository.delete(likeBoard);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin = LoginUtils.isLogin(request);
            User user = userRepository.findByNickName(isLogin).orElseThrow();
            Board board = boardRepository.findById(boardNum).orElseThrow();
            //like 존재시 true, 없으면 false 반환
            return (likeBoardRepository.findByUserAndBoard(user,board).isPresent());
        } catch (Exception e) {
            return false;
        }
    }
}
