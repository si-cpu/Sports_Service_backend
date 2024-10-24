package SportsService.backend.dto.request;

import SportsService.backend.entity.Board;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private String boardNum;
    private String nickName;
    private String title;
    private String content;
}
