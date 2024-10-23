package SportsService.backend.controller;


import SportsService.backend.dto.request.CommunityBoardWriteRequestDTO;
import SportsService.backend.dto.response.CommunityBoardListResponceDTO;
import SportsService.backend.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;

@Controller
@RequestMapping("/communityBoard")
@RequiredArgsConstructor
public class CommunitiyBoardController {

    private final CommunityBoardService communityBoardService;


    @GetMapping("/list")
    public String list(Model model) {
        List<CommunityBoardListResponceDTO> list =
                communityBoardService.getList();
        model.addAttribute("CommList", list);

        return "sportsService/list";
    }

    @GetMapping("/write")
    public String write() {
        return "sportsService/write";
    }

    @PostMapping("/write")
    public String write(CommunityBoardWriteRequestDTO dto) {

        System.out.println("dto test" + dto);
        communityBoardService.newWrite(dto);
        return "redirect:/CommunityBoard/list";
    }

    @GetMapping("/detail/{boardNumber}")
    public String detail(@PathVariable long boardNumber) {
        System.out.println("board number =" + boardNumber);

        communityBoardService.getDetail(boardNumber);

        return null;
    }
}
