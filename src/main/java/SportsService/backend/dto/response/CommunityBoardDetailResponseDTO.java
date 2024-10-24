package SportsService.backend.dto.response;

import SportsService.backend.entity.CommunityBoard;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@EqualsAndHashCode
public class CommunityBoardDetailResponseDTO {

    private final long boardNum;
    private final String title;
    private final String content;
    private final String writer;
    private final String regTime;



    public CommunityBoardDetailResponseDTO(CommunityBoard communityBoard) {

        this.boardNum = communityBoard.getBoardNum();
        this.title = communityBoard.getTitle();
        this.content = communityBoard.getContent();
        this.writer = communityBoard.getWriter();

        this.regTime = CommunityBoardListResponceDTO.makeRegDateString(communityBoard.getRegTime());

    }
}
