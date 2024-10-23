package SportsService.backend.controller;

import SportsService.backend.dto.request.SignUpRequestDto;
import SportsService.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign_up")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpRequestDto dto){
        System.out.println("dto.toString() = " + dto.toString());
        memberService.register(dto);
        return ResponseEntity.ok().body("성공");
    }

}
