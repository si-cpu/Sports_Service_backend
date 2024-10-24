package SportsService.backend.controller;


import SportsService.backend.dto.request.CommunityBoardWriteRequestDTO;
import SportsService.backend.dto.response.CommunityBoardDetailResponseDTO;
import SportsService.backend.dto.response.CommunityBoardListResponceDTO;
import SportsService.backend.service.CommunityBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communityBoard")
@RequiredArgsConstructor
public class CommunitiyBoardController {

    private final CommunityBoardService communityBoardService;


    @GetMapping("/list")
    public String list(Model model) {
        List<CommunityBoardListResponceDTO> list =
                communityBoardService.getList();
        model.addAttribute("CommList", list);

        return "SportsService/list";
    }

    @GetMapping("/write")
    public String write() {
        return "SportsService/write";
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

        CommunityBoardDetailResponseDTO dto = communityBoardService.getDetail(boardNumber);

        return "SportsService/detail";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long boardNum) {

        communityBoardService.delete(boardNum);

        return "redirect:CommunityBoard/list";
    }

}
