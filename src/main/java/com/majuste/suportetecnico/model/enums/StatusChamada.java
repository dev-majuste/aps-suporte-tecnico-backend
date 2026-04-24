package com.majuste.suportetecnico.model.enums;

//Enum dos status que o chamado pode ter
public enum StatusChamada {
    EM_ABERTO, //Cliente criou o chamado
    EM_ANDAMENTO, //Aguardando atendimento/Suporte atender o chamado
    AGUARDANDO_CLIENTE, //Esperando resposta do cliente
    AGUARDANDO_SUPORTE, //Esperando resposta do suporte
    RESOLVIDO //Resolvido e finalizado
}
