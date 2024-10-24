package SportsService.backend.dto.response;


import SportsService.backend.entity.CommunityBoard;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @ToString
@EqualsAndHashCode
public class CommunityBoardListResponceDTO {

    private final long boardNum;
    //private final int userKey;

    private final String listTitle; //노출되는 글자수 제한
    private final String writer;
    //private final String listContent

    //private final String regDate;
    //private final long viewCount;

    public CommunityBoardListResponceDTO(CommunityBoard board) {
        this.boardNum = board.getBoardNum();

        this.listTitle = makeBoardTitle(board.getTitle());

        this.writer = board.getWriter();

        //this.regDate = makeRegDateString(board.getRegTime());

        //this.viewCount = board.getViewCount();
    }



    private String sliceString(String target, int length) {
        return (target.length() > length)
                ? target.substring(0, length) + "..."
                :target;
    }

    private String makeBoardTitle(String title) {
        return sliceString(title, 7);
    }


    public static String makeRegDateString(LocalDateTime regTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dtf.format(regTime);
    }
}
