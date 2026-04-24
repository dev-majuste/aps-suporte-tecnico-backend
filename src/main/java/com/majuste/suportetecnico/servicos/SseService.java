package com.majuste.suportetecnico.servicos;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(Long idUsuario) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        emitter.onCompletion(() ->  emitters.remove(idUsuario));
        emitter.onError((e) -> emitters.remove(idUsuario));
        emitter.onTimeout(() -> emitters.remove(idUsuario));

        emitters.put(idUsuario, emitter);
        return emitter;
    }

    public void notificar(Long idUsuario, String tipo, Object conteudo) {
        SseEmitter emitter = emitters.get(idUsuario);
        if (emitter != null) {
            try {
                Map<String, Object> env = new HashMap<>();
                env.put("tipo", tipo);
                env.put("conteudo", conteudo);

                emitter.send(SseEmitter.event().name("notificacao").data(env));
            } catch (IOException e) {
                emitters.remove(idUsuario);
            }
        }
    }
}
