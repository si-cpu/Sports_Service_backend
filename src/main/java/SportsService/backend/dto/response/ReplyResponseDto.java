package SportsService.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 댓글 응답 데이터를 담는 DTO 클래스입니다.
 * 댓글의 번호, 게시글 번호, 내용, 작성자, 등록 및 수정 날짜, 좋아요 수 등의 정보를 포함합니다.
 *
 * @since 2024-10-26
 * @author minus43
 */
@Getter
@Setter
@ToString
@Builder
public class ReplyResponseDto {

    /**
     * 댓글의 고유 식별자입니다.
     */
    private Long replyNum;

    /**
     * 댓글이 속한 게시글의 식별자입니다.
     */
    private Long boardNum;

    /**
     * 댓글의 내용입니다.
     */
    private String content;

    /**
     * 댓글 작성자의 닉네임입니다.
     */
    private String writer;

    /**
     * 댓글의 등록 날짜 및 시간입니다.
     */
    private LocalDateTime regDate;

    /**
     * 댓글의 마지막 수정 날짜 및 시간입니다.
     */
    private LocalDateTime modDate;

    /**
     * 댓글의 좋아요 수입니다.
     */
    private Long goodCount;
}
