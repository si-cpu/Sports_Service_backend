package SportsService.backend.service;

import SportsService.backend.dto.request.BoardRequestDto;
import SportsService.backend.entity.Board;
import SportsService.backend.repository.BoardRepository;
import SportsService.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public String isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // 세션 확인
        if (session != null && session.getAttribute("member") != null) {
            return session.getAttribute("member").toString();
        } else {
            return null;
        }
    }

    public boolean save(BoardRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            Board board = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .user(userRepository.findByNickName(loginUser).orElseThrow())
                    .build();
            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(BoardRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            System.out.println("loginUser = " + loginUser);
            if(boardRepository.findById(Long.parseLong(dto.getBoardNum())).orElseThrow().getUser().getNickName().equals(loginUser)) {
                System.out.println("dto.getBoardNum() = " + dto.getBoardNum());
                boardRepository.deleteById(Long.parseLong(dto.getBoardNum()));
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    public boolean modify(BoardRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if(boardRepository.findById(Long.parseLong(dto.getBoardNum())).orElseThrow().getUser().getNickName().equals(loginUser)) {
                Board board=boardRepository.findById(Long.parseLong(dto.getBoardNum())).orElseThrow();
                board.setTitle(dto.getTitle());
                board.setContent(dto.getContent());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Board> findAll() {
        try {
            return boardRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }
}
