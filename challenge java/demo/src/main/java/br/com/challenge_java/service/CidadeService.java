package br.com.challenge_java.service;

import br.com.challenge_java.dto.CidadeDTO;
import br.com.challenge_java.mapper.CidadeMapper;
import br.com.challenge_java.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CidadeService {
    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;

    @Transactional(readOnly = true)
    public List<CidadeDTO> listarTodas() {
        return cidadeRepository.findAll().stream()
                .map(cidadeMapper::toCidadeDTO)
                .collect(Collectors.toList());
    }
}