package com.prisc.springboot.controller;

import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
//@AllArgsConstructor     //Cria um construtor com todos os atributos da classe
@RequiredArgsConstructor //Cria um construtor com os atributos do tipo ~final~
public class AnimeController {

    private final DateUtil dateUtil;
    @GetMapping("/list")
    public List<Anime> list(){
        log.info(dateUtil.formatLocalDateTimeToDataBaseStile(LocalDateTime.now()));
        return List.of(new Anime("DBZT"), new Anime("Bersek"));
    }
}
