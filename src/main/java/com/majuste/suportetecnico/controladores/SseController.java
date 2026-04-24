package com.majuste.suportetecnico.controladores;

import com.majuste.suportetecnico.servicos.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/fluxo")
@CrossOrigin("*")
public class SseController {

    @Autowired
    private SseService sseService;

    @GetMapping(value = "/subscribe/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long id) {return sseService.subscribe(id);}
}
