package br.com.challenge_java.service;

import br.com.challenge_java.dto.VeiculoCreateDTO;
import br.com.challenge_java.dto.VeiculoDTO;
import br.com.challenge_java.exception.BusinessException;
import br.com.challenge_java.exception.ResourceNotFoundException;
import br.com.challenge_java.mapper.VeiculoMapper;
import br.com.challenge_java.model.Usuario;
import br.com.challenge_java.model.Veiculo;
import br.com.challenge_java.repository.UsuarioRepository;
import br.com.challenge_java.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VeiculoMapper veiculoMapper;


    @Transactional
    public VeiculoDTO criarVeiculo(VeiculoCreateDTO veiculoCreateDTO) {
        Usuario usuario = usuarioRepository.findById(veiculoCreateDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + veiculoCreateDTO.getUsuarioId()));
        if (veiculoRepository.findByPlacaNova(veiculoCreateDTO.getPlacaNova()).isPresent()) {
            throw new BusinessException("Já existe um veículo cadastrado com a placa nova: " + veiculoCreateDTO.getPlacaNova());
        }
        Veiculo veiculo = veiculoMapper.toVeiculo(veiculoCreateDTO);
        veiculo.setUsuario(usuario);
        Veiculo veiculoSalvo = veiculoRepository.save(veiculo);
        return veiculoMapper.toVeiculoDTO(veiculoSalvo);
    }

    @Transactional(readOnly = true)
    public VeiculoDTO buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .map(veiculoMapper::toVeiculoDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<VeiculoDTO> listarTodosPaginado(Pageable pageable) {
        return veiculoRepository.findAll(pageable)
                .map(veiculoMapper::toVeiculoDTO);
    }

    @Cacheable(value = "veiculosPorUsuario", key = "#usuarioId")
    @Transactional(readOnly = true)
    public List<VeiculoDTO> listarPorUsuarioId(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId);
        }
        return veiculoRepository.findByUsuarioId(usuarioId).stream()
                .map(veiculoMapper::toVeiculoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VeiculoDTO atualizarVeiculo(Long id, VeiculoCreateDTO veiculoCreateDTO) {
        Veiculo veiculoExistente = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID: " + id));
        Usuario usuario = usuarioRepository.findById(veiculoCreateDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + veiculoCreateDTO.getUsuarioId()));
        if (!veiculoExistente.getPlacaNova().equals(veiculoCreateDTO.getPlacaNova()) &&
            veiculoRepository.findByPlacaNova(veiculoCreateDTO.getPlacaNova()).isPresent()) {
            throw new BusinessException("Já existe outro veículo cadastrado com a placa nova: " + veiculoCreateDTO.getPlacaNova());
        }
        veiculoExistente.setUsuario(usuario);
        veiculoExistente.setPlacaAntiga(veiculoCreateDTO.getPlacaAntiga());
        veiculoExistente.setPlacaNova(veiculoCreateDTO.getPlacaNova());
        veiculoExistente.setTipoVeiculo(veiculoCreateDTO.getTipoVeiculo());
        Veiculo veiculoAtualizado = veiculoRepository.save(veiculoExistente);
        return veiculoMapper.toVeiculoDTO(veiculoAtualizado);
    }

    
    @Transactional(readOnly = true)
    public List<Veiculo> buscarVeiculosPorPerfil() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return veiculoRepository.findAll();
        } else {
            return veiculoRepository.findByUsuarioUsername(userDetails.getUsername());
        }
    }

    @Transactional(readOnly = true)
    public Veiculo findEntityById(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com ID: " + id));
    }
    
    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo atualizar(Long id, Veiculo dadosDoFormulario) {
        Veiculo veiculoDoBanco = findEntityById(id);
        veiculoDoBanco.setPlacaNova(dadosDoFormulario.getPlacaNova());
        veiculoDoBanco.setPlacaAntiga(dadosDoFormulario.getPlacaAntiga());
        veiculoDoBanco.setTipoVeiculo(dadosDoFormulario.getTipoVeiculo());
        
        if (dadosDoFormulario.getUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(dadosDoFormulario.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado para atualização do veículo."));
            veiculoDoBanco.setUsuario(usuario);
        }

        return veiculoRepository.save(veiculoDoBanco);
    }

    @Transactional
    public void deletarVeiculo(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veículo não encontrado com ID: " + id);
        }
        veiculoRepository.deleteById(id);
    }
}