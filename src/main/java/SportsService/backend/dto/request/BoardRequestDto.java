package SportsService.backend.dto.request;

import lombok.*;

/**
 * 게시글 요청 데이터를 담는 DTO 클래스입니다.
 * 게시글 생성 또는 수정 시 클라이언트로부터 전달되는 데이터를 담습니다.
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
public class BoardRequestDto {

    /**
     * 게시글의 고유 식별자입니다.
     */
    private String boardNum;

    /**
     * 게시글의 제목입니다.
     */
    private String title;

    /**
     * 게시글의 내용입니다.
     */
    private String content;

    /**
     * 게시글의 좋아요 수입니다.
     */
    private Long goodCount;
}
