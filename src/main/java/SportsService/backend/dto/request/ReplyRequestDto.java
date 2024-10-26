package SportsService.backend.dto.request;

import lombok.*;

/**
 * 댓글 요청 데이터를 담는 DTO 클래스입니다.
 * 클라이언트로부터 전달된 댓글 생성 및 수정 정보를 전달하기 위해 사용됩니다.
 *
 * @since 2024-10-26
 * @author minus43
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
     */
    private Long replyNum;

    /**
     * 댓글이 속한 게시글의 고유 식별자입니다.
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
}
