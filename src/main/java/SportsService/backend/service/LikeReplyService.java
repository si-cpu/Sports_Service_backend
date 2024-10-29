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

@Service
@Transactional
@RequiredArgsConstructor
public class LikeReplyService {
    private final LikeReplyRepository likeReplyRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    public boolean makeLike(Long replyNum, HttpServletRequest request) {
        try {
            String isLogin= LoginUtils.isLogin(request);
            User user =userRepository.findByNickName(isLogin).orElseThrow();
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

    public boolean removeLike(Long replyNum, HttpServletRequest request) {
        try {
            String isLogin= LoginUtils.isLogin(request);
            User user =userRepository.findByNickName(isLogin).orElseThrow();
            Reply reply = replyRepository.findById(replyNum).orElseThrow();
            LikeReply likeReply = likeReplyRepository.findByUserAndReply(user, reply).orElseThrow();
            likeReplyRepository.delete(likeReply);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Long> isLike(Long boardNum, HttpServletRequest request) {
        try {
            String isLogin= LoginUtils.isLogin(request);
            User user =userRepository.findByNickName(isLogin).orElseThrow();
            List<Reply> replies=replyRepository.findByBoard(boardRepository.findById(boardNum).orElseThrow());
            List<Long> replyNums=new ArrayList<>();
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
