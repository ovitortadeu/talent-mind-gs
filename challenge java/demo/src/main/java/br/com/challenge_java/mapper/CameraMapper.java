package br.com.challenge_java.mapper;

import br.com.challenge_java.dto.CameraDTO;
import br.com.challenge_java.model.Camera;
import br.com.challenge_java.model.Filial;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {FilialMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CameraMapper {

    @Mapping(source = "filial.id", target = "filialId")
    CameraDTO toCameraDTO(Camera camera);

    @Mapping(source = "filialId", target = "filial", qualifiedByName = "filialFromId")
    Camera toCamera(CameraDTO cameraDTO);

    @Named("filialFromId")
    default Filial filialFromId(Long id) {
        if (id == null) {
            return null;
        }
        Filial filial = new Filial();
        filial.setId(id);
        return filial;
    }
}