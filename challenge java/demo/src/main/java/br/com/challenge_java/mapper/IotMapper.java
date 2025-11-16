package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.IotDTO;
import br.com.challenge_java.model.Iot;
import br.com.challenge_java.model.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IotMapper {

    @Mapping(source = "veiculo.id", target = "veiculoId")
    IotDTO toIotDTO(Iot iot);

    @Mapping(source = "veiculoId", target = "veiculo", qualifiedByName = "veiculoFromId")
    Iot toIot(IotDTO iotDTO);

    @Named("veiculoFromId")
    default Veiculo veiculoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Veiculo veiculo = new Veiculo();
        veiculo.setId(id);
        return veiculo;
    }
}