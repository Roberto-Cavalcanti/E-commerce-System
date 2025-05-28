package com.project.ecommerce.models.enums;

import lombok.Getter;

import java.math.BigDecimal;
@Getter
public enum States {
    AC("ACRE", BigDecimal.valueOf(20)),
    AL("Alagoas", BigDecimal.valueOf(15)),
    AP("Amapá", BigDecimal.valueOf(22)),
    AM("Amazonas", BigDecimal.valueOf(32)),
    BA("Bahia", BigDecimal.valueOf(15)),
    CE("Ceará", BigDecimal.valueOf(13)),
    DF("Distrito Federal*", BigDecimal.valueOf(25)),
    ES("Espírito Santo", BigDecimal.valueOf(11)),
    GO("Goiás", BigDecimal.valueOf(9)),
    MA("Maranhão", BigDecimal.valueOf(12)),
    MT("Mato Grosso", BigDecimal.valueOf(14)),
    MS("Mato Grosso do Sul", BigDecimal.valueOf(15)),
    MG("Minas Gerais", BigDecimal.valueOf(11)),
    PA("Pará", BigDecimal.valueOf(12)),
    PB("Paraíba", BigDecimal.valueOf(16)),
    PR("Paraná", BigDecimal.valueOf(17)),
    PE("Pernambuco", BigDecimal.valueOf(12)),
    PI("Piauí", BigDecimal.valueOf(13)),
    RJ("Rio de Janeiro", BigDecimal.valueOf(27)),
    RN("Rio Grande do Norte", BigDecimal.valueOf(10)),
    RS("Rio Grande do Sul", BigDecimal.valueOf(16)),
    RO("Rondônia", BigDecimal.valueOf(24)),
    RR("Roraima", BigDecimal.valueOf(23)),
    SC("Santa Catarina", BigDecimal.valueOf(20)),
    SP("São Paulo", BigDecimal.valueOf(24)),
    SE("Sergipe", BigDecimal.valueOf(30)),
    TO("Tocantins", BigDecimal.valueOf(17));

    private final String state;
    private final BigDecimal value;

    States(String state, BigDecimal value) {
        this.state = state;
        this.value = value;
    }
}
