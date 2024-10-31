package SportsService.backend.dto.request;

import lombok.*;

/**
 * 댓글 요청 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 클라이언트로부터 전달된 댓글 생성, 수정 정보를 서버로 전달하기 위해 사용됩니다.
 *
 * @since 2024-10-26
 * @author minus43
 * @see SportsService.backend.entity.Reply
 * @see SportsService.backend.controller.ReplyController
 */
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDto {

    /**
     * 댓글의 고유 식별자입니다.
     * 댓글 수정 시에만 사용되며, 새 댓글 생성 시에는 null 값이 허용됩니다.
     */
    private Long replyNum;

    /**
     * 댓글이 속한 게시글의 고유 식별자입니다.
     * 댓글이 어떤 게시글에 속하는지를 나타내며, null이 허용되지 않습니다.
     */
    private Long boardNum;

    /**
     * 댓글의 내용입니다.
     * 클라이언트에서 입력된 댓글의 본문 내용을 저장합니다.
     * null이 아닌 문자열이어야 하며, 빈 문자열은 허용되지 않습니다.
     */
    private String content;

    /**
     * 댓글 작성자의 닉네임입니다.
     * 댓글을 작성한 사용자를 식별하는데 사용됩니다.
     * null이 아닌 문자열이어야 합니다.
     */
    private String writer;

    /**
     * 댓글의 좋아요 수입니다.
     * 댓글이 받은 총 좋아요 수를 나타냅니다.
     * 기본값은 0이며, 음수값은 허용되지 않습니다.
     */
    private Long goodCount;
}
