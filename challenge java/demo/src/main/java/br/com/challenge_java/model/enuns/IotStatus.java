package br.com.challenge_java.model.enuns;

public enum IotStatus {
    INDISPONIVEL(0),
    DISPONIVEL(1);

    private final int valor;

    IotStatus(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static IotStatus fromValor(Short valor) {
        if (valor == null) return null;
        for (IotStatus status : IotStatus.values()) {
            if (status.valor == valor.intValue()) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor inválido para IotStatus: " + valor);
    }
}