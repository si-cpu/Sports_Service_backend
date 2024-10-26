package SportsService.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 게시글 응답 데이터를 담는 DTO 클래스입니다.
 * 게시글의 번호, 제목, 내용, 작성자, 등록 및 수정 날짜, 좋아요 수, 조회수, 댓글 수 등의 정보를 포함합니다.
 *
 * @since 2024-10-26
 * @author minus43
 */
@Getter
@Setter
@ToString
@Builder
public class BoardResponseDto {

    /**
     * 게시글의 고유 식별자입니다.
     */
    private Long boardNum;

    /**
     * 게시글의 제목입니다.
     */
    private String title;

    /**
     * 게시글의 내용입니다.
     */
    private String content;

    /**
     * 게시글 작성자의 닉네임입니다.
     */
    private String writer;

    /**
     * 게시글의 등록 날짜 및 시간입니다.
     */
    private LocalDateTime regDate;

    /**
     * 게시글의 마지막 수정 날짜 및 시간입니다.
     */
    private LocalDateTime modDate;

    /**
     * 게시글의 좋아요 수입니다.
     */
    private Long goodCount;

    /**
     * 게시글의 조회수입니다.
     */
    private Long viewCount;

    /**
     * 게시글에 달린 댓글 수입니다.
     */
    private Long replyCount;
}
