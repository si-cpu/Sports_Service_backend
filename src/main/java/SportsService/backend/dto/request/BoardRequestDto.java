package SportsService.backend.dto.request;

import lombok.*;

/**
 * 게시글 요청 데이터를 담는 DTO(Data Transfer Object) 클래스입니다.
 * 게시글 생성, 수정 시 클라이언트로부터 전달되는 데이터를 담아 서버로 전달합니다.
 *
 * @since 2024-10-26
 * @author minus43
 * @see SportsService.backend.entity.Board
 */
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    /**
     * 게시글의 고유 식별자입니다.
     * 게시글 수정 시에만 사용되며, 새 게시글 생성 시에는 null 값이 허용됩니다.
     */
    private String boardNum;

    /**
     * 게시글의 제목입니다.
     * 클라이언트에서 입력된 게시글의 제목을 저장합니다.
     * null이 아닌 문자열이어야 합니다.
     */
    private String title;

    /**
     * 게시글의 내용입니다.
     * 클라이언트에서 입력된 게시글의 본문 내용을 저장합니다.
     * null이 아닌 문자열이어야 합니다.
     */
    private String content;

    /**
     * 게시글의 좋아요 수입니다.
     * 게시글이 받은 총 좋아요 수를 나타냅니다.
     * 기본값은 0이며, 음수값은 허용되지 않습니다.
     */
    private Long goodCount;
}
