package SportsService.backend.service;

import SportsService.backend.dto.request.BoardRequestDto;
import SportsService.backend.dto.response.BoardResponseDto;
import SportsService.backend.entity.Board;
import SportsService.backend.repository.BoardRepository;
import SportsService.backend.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static SportsService.backend.utils.LoginUtils.*;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 * 게시글 저장, 수정, 삭제, 조회 기능을 제공합니다.
 *
 * 게시글 작성, 수정, 삭제 시 로그인한 사용자의 인증을 확인합니다.
 *
 * @since 2024-10-26
 * @author minus43
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 게시글을 저장하는 메서드입니다.
     * 요청으로부터 로그인한 사용자를 확인하고, 게시글 정보를 데이터베이스에 저장합니다.
     *
     * @param dto 게시글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 저장 성공 시 true, 실패 시 false
     */
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

    /**
     * 게시글을 삭제하는 메서드입니다.
     * 로그인한 사용자가 게시글 작성자와 일치할 때만 삭제가 가능합니다.
     *
     * @param boardNum 삭제할 게시글의 고유 식별자
     * @param request HTTP 요청 객체
     * @return 삭제 성공 시 true, 실패 시 false
     */
    public boolean delete(Long boardNum, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (boardRepository.findById(boardNum).orElseThrow().getUser().getNickName().equals(loginUser)) {
                boardRepository.deleteById(boardNum);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 게시글을 수정하는 메서드입니다.
     * 로그인한 사용자가 게시글 작성자와 일치할 때만 수정이 가능합니다.
     *
     * @param dto 수정할 게시글 정보를 담은 요청 DTO
     * @param request HTTP 요청 객체
     * @return 수정 성공 시 true, 실패 시 false
     */
    public boolean modify(BoardRequestDto dto, HttpServletRequest request) {
        try {
            String loginUser = isLogin(request);
            if (boardRepository.findById(Long.parseLong(dto.getBoardNum())).orElseThrow().getUser().getNickName().equals(loginUser)) {
                Board board = boardRepository.findById(Long.parseLong(dto.getBoardNum())).orElseThrow();
                board.setTitle(dto.getTitle());
                board.setContent(dto.getContent());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 모든 게시글을 조회하는 메서드입니다.
     * 각 게시글 정보를 BoardResponseDto로 변환하여 반환합니다.
     *
     * @return 조회한 모든 게시글의 정보가 담긴 List, 실패 시 null
     */
    public List<BoardResponseDto> findAll() {
        try {
            List<Board> boards = boardRepository.findAll();
            List<BoardResponseDto> boardsDto = new ArrayList<>();
            for (Board board : boards) {
                boardsDto.add(BoardResponseDto.builder()
                        .boardNum(board.getBoardNum())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .writer(board.getUser().getNickName())
                        .regDate(board.getRegDate())
                        .modDate(board.getModDate())
                        .goodCount(board.getGoodCount())
                        .viewCount(board.getViewCount())
                        .replyCount((long) board.getReplies().size())
                        .build()
                );
            }
            return boardsDto;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 게시글의 조회수를 1 증가시키는 메서드입니다.
     *
     * @param boardNum 조회수를 증가시킬 게시글의 고유 식별자
     * @return 조회수 증가 성공 시 true, 실패 시 false
     */
    public boolean viewUpdate(Long boardNum) {
        try {
            Board board = boardRepository.findById(boardNum).orElseThrow();
            board.setViewCount(board.getViewCount()+1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 게시글의 좋아요 수를 1 증가시키는 메서드입니다.
     * 사용자가 게시글에 좋아요를 눌렀을 때 호출됩니다.
     *
     * @param boardNum 좋아요 수를 증가시킬 게시글의 고유 식별자
     * @return 좋아요 수 증가 성공 시 true, 실패 시 false
     */
    public boolean makeLike(Long boardNum) {
        try {
            Board board = boardRepository.findById(boardNum).orElseThrow();
            board.setGoodCount(board.getGoodCount()+1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 게시글의 좋아요 수를 1 감소시키는 메서드입니다.
     * 사용자가 게시글의 좋아요를 취소했을 때 호출됩니다.
     *
     * @param boardNum 좋아요 수를 감소시킬 게시글의 고유 식별자
     * @return 좋아요 수 감소 성공 시 true, 실패 시 false
     */
    public boolean removeLike(Long boardNum) {
        try {
            Board board = boardRepository.findById(boardNum).orElseThrow();
            board.setGoodCount(board.getGoodCount()-1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
