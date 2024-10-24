package SportsService.backend.controller;

import SportsService.backend.dto.request.BoardRequestDto;
import SportsService.backend.entity.Board;
import SportsService.backend.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody BoardRequestDto dto, HttpServletRequest request) {
        if (boardService.save(dto, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody BoardRequestDto dto, HttpServletRequest request) {
        if (boardService.delete(dto, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    @PutMapping("/modify")
    public ResponseEntity<String> modify(@RequestBody BoardRequestDto dto, HttpServletRequest request) {
        if (boardService.modify(dto, request)) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("failed");
    }

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll() {
        List<Board> boards = boardService.findAll();
        if (boards != null && !boards.isEmpty()) {
            return ResponseEntity.ok(boards);
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
