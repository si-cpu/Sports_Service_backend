package SportsService.backend.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class CommunityBoardWriteRequestDTO {

    private String title;
    private String content;
    private String writer;


}
