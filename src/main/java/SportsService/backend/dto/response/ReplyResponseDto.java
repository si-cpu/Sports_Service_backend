package SportsService.backend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 댓글 정보를 클라이언트에게 전달하기 위한 응답 DTO(Data Transfer Object) 클래스입니다.
 * Reply 엔티티의 정보를 클라이언트에 적합한 형태로 변환하여 전달합니다.
 * 댓글의 상세 정보와 메타데이터(작성 시간, 수정 시간, 좋아요 수 등)를 포함합니다.
 *
 * @since 2024-10-26
 * @author minus43
 * @see SportsService.backend.entity.Reply
 * @see SportsService.backend.controller.ReplyController
 */
@Getter
@Setter
@ToString
@Builder
public class ReplyResponseDto {

    /**
     * 댓글의 고유 식별자입니다.
     * Reply 엔티티의 replyNum 값과 매핑됩니다.
     */
    private Long replyNum;

    /**
     * 댓글이 속한 게시글의 고유 식별자입니다.
     * 어떤 게시글의 댓글인지를 나타내며, Board 엔티티와의 관계를 표현합니다.
     */
    private Long boardNum;

    /**
     * 댓글의 내용입니다.
     * 사용자가 작성한 실제 댓글 텍스트를 포함합니다.
     */
    private String content;

    /**
     * 댓글 작성자의 닉네임입니다.
     * 댓글을 작성한 User의 식별 정보로 사용됩니다.
     */
    private String writer;

    /**
     * 댓글의 최초 작성 일시입니다.
     * ISO 8601 형식의 날짜/시간 값으로 제공됩니다.
     */
    private LocalDateTime regDate;

    /**
     * 댓글의 최종 수정 일시입니다.
     * 댓글이 수정된 경우에만 값이 존재하며,
     * ISO 8601 형식의 날짜/시간 값으로 제공됩니다.
     */
    private LocalDateTime modDate;

    /**
     * 댓글의 좋아요 수입니다.
     * 해당 댓글에 대한 총 좋아요 수를 나타냅니다.
     */
    private Long goodCount;
}
