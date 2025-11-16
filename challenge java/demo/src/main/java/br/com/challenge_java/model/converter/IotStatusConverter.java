package br.com.challenge_java.model.converter;

import br.com.challenge_java.model.enuns.IotStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IotStatusConverter implements AttributeConverter<IotStatus, Short> {

    @Override
    public Short convertToDatabaseColumn(IotStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return (short) attribute.getValor();
    }

    @Override
    public IotStatus convertToEntityAttribute(Short dbData) {
        if (dbData == null) {
            return null;
        }
        return IotStatus.fromValor(dbData);
    }
}