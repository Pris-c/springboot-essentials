package com.prisc.springboot.controller;

import com.prisc.springboot.domain.Anime;
import com.prisc.springboot.request.AnimePostRequestBody;
import com.prisc.springboot.request.AnimePutRequestBody;
import com.prisc.springboot.service.AnimeService;
import com.prisc.springboot.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//@AllArgsConstructor     //Cria um construtor com todos os atributos da classe

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor //Cria um construtor com os atributos do tipo ~final~
public class AnimeController {

    private final AnimeService animeService;
    // Removido DateUtil para simplificar testes
    // private final DateUtil dateUtil;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable){
        //log.info(dateUtil.formatLocalDateTimeToDataBaseStile(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll(){
        //log.info(dateUtil.formatLocalDateTimeToDataBaseStile(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAllNotPageble());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
        //log.info(dateUtil.formatLocalDateTimeToDataBaseStile(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime){
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody ){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
