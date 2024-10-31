package SportsService.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 게시글 응답 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 서버에서 클라이언트로 게시글 정보를 전달할 때 사용됩니다.
 * 게시글의 상세 정보와 메타데이터(조회수, 좋아요 수, 댓글 수 등)를 포함합니다.
 *
 * @since 2024-10-26
 * @author minus43
 * @see SportsService.backend.entity.Board
 * @see SportsService.backend.controller.BoardController
 */
@Getter
@Setter
@ToString
@Builder
public class BoardResponseDto {

    /**
     * 게시글의 고유 식별자입니다.
     * 데이터베이스에서 자동 생성되는 기본키(Primary Key) 값입니다.
     */
    private Long boardNum;

    /**
     * 게시글의 제목입니다.
     * 게시글 목록이나 상세 페이지에서 표시되는 제목 텍스트입니다.
     */
    private String title;

    /**
     * 게시글의 내용입니다.
     * 게시글의 본문 텍스트를 포함하며, HTML 태그가 포함될 수 있습니다.
     */
    private String content;

    /**
     * 게시글 작성자의 닉네임입니다.
     * 작성자를 식별하는데 사용되며, 게시글 표시 시 작성자 정보로 사용됩니다.
     */
    private String writer;

    /**
     * 게시글의 최초 등록 일시입니다.
     * 게시글이 처음 작성된 시점을 나타냅니다.
     */
    private LocalDateTime regDate;

    /**
     * 게시글의 최종 수정 일시입니다.
     * 게시글이 마지막으로 수정된 시점을 나타내며, 수정되지 않은 경우 null일 수 있습니다.
     */
    private LocalDateTime modDate;

    /**
     * 게시글의 좋아요 수입니다.
     * 사용자들이 해당 게시글에 누른 총 좋아요 수를 나타냅니다.
     */
    private Long goodCount;

    /**
     * 게시글의 조회수입니다.
     * 게시글이 조회된 총 횟수를 나타냅니다.
     */
    private Long viewCount;

    /**
     * 게시글에 달린 댓글의 수입니다.
     * 해당 게시글에 작성된 총 댓글 수를 나타냅니다.
     */
    private Long replyCount;
}
