package SportsService.backend.service;


import SportsService.backend.dto.request.CommunityBoardWriteRequestDTO;
import SportsService.backend.dto.response.CommunityBoardDetailResponseDTO;
import SportsService.backend.dto.response.CommunityBoardListResponceDTO;
import SportsService.backend.entity.CommunityBoard;
import SportsService.backend.repository.CommunityBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityBoardService {

    private final CommunityBoardRepository repository;

    public List<CommunityBoardListResponceDTO> getList() {
        List<CommunityBoard> communityBoardList = repository.findAll();

        return communityBoardList.stream()
                .map(CommunityBoardListResponceDTO::new)
                .collect(Collectors.toList());
    }


    public void newWrite(CommunityBoardWriteRequestDTO dto) {
        repository.save(new CommunityBoard(dto));
    }

    public CommunityBoardDetailResponseDTO getDetail(long boardNumber) {

        repository.updateViewCount(boardNumber);

        CommunityBoard communityBoard = repository.findById(boardNumber).orElseThrow();

        return new CommunityBoardDetailResponseDTO(communityBoard);
    }

    public void delete(long boardNum) {
        repository.deleteById(boardNum);
    }


}
