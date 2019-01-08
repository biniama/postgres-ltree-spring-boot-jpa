package com.bonial.tmapipoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FactsController {

    private final FactsRepository factsRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public FactsController(FactsRepository factsRepository) {
        this.factsRepository = factsRepository;

        objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @GetMapping("/")
    String hello() {
        return "Implementation of Postgres LTree in Spring Boot app!";
    }

    @PostMapping(value = "/content-boost")
    ResponseEntity<?> createFacts(@RequestBody List<Facts> facts) throws URISyntaxException {

        facts
            .forEach(fact -> {
                try {
                    String rulesAsString = objectMapper.writeValueAsString(fact.getRules());
                    factsRepository.upsert(fact.getPath(), fact.getBoost(), rulesAsString);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        );

        return ResponseEntity.created(new URI("/content-boost/"))
                .body("{'result' : 'new Facts inserted'}");
    }

    @GetMapping(value = "/content-boost/{path}")
    ResponseEntity<?> retrieveContentBoost(@PathVariable("path") String path) {

        List<Facts> allByPath = factsRepository.findAllByPath(path);

        return ResponseEntity.ok().body(allByPath);
    }

    @GetMapping("/content-boost/count/{path}")
    ResponseEntity<?> getCountOfElementsByPath(@PathVariable("path") String path) {

        return ResponseEntity.ok().body(factsRepository.getCountOfElementsByPath(path));
    }

    @DeleteMapping("/content-boost/{path}")
    ResponseEntity<?> deleteAllByPath(@PathVariable("path") String path) {

        factsRepository.deleteAllByPath(path);
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/nlevel")
    ResponseEntity<?> nlevel() {

        List<FactsRepository.TreeNLevel> nLevel = factsRepository.nLevel();

        return ResponseEntity.ok().body(nLevel);
    }



    @GetMapping("/neworder")
    ResponseEntity<?> save() throws JsonProcessingException {

        List<Rule> rules = new ArrayList<>();
        rules.add(Rule.builder().placement("ad_placement__shelf_sort_managed").visible(true).boost(50).build());
        rules.add(Rule.builder().placement("ad_placement__shelf_unsort_managed").visible(false).boost(-20).build());

        Facts facts = Facts.builder()
                .path("order_z")
                //.visible(true)
                .boost(10)
                .rules(rules)
                .build();

        String rulesAsString = objectMapper.writeValueAsString(rules);

        factsRepository.upsert(facts.getPath(), /*facts.getVisible(),*/ facts.getBoost(), rulesAsString);

        return ResponseEntity.ok().body("inserted");
    }

    @GetMapping("/path")
    ResponseEntity<?> findAllByPath() {

        List<Facts> allByPath = factsRepository.findAllByPath("order_z");

        return ResponseEntity.ok().body(allByPath);
    }

    @GetMapping("/deleteAllByPath")
    ResponseEntity<?> deleteAllByPath() {

        factsRepository.deleteAllByPath("A.C");
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/moveTreeOneLevelUp")
    ResponseEntity<?> moveTreeOneLevelUp() {

        factsRepository.moveTreeOneLevelUp("A.C");
        return ResponseEntity.ok().body("moveTreeOneLevelUp");
    }

    @GetMapping("/moveRootTreeDown")
    ResponseEntity<?> moveRootTreeDown() {

        factsRepository.moveRootTreeDown("A.B.G", "C");
        return ResponseEntity.ok().body("moveRootTreeDown");
    }

    @GetMapping("/moveNonRootTreeDown")
    ResponseEntity<?> moveNonRootTreeDown() {

        factsRepository.moveNonRootTreeDown("A.B.G", "A.C");
        return ResponseEntity.ok().body("moveNonRootTreeDown");
    }

    @GetMapping("/copyTree")
    ResponseEntity<?> copyTree() {

        factsRepository.copyTree("A.B.G", "A.C");
        return ResponseEntity.ok().body("copyTree");
    }

    /*
    @GetMapping("/letter")
    ResponseEntity<?> letter() {

        List<TreeRepository.Letter> letter = treeRepository.letter();

        return ResponseEntity.ok().body(letter);
    }

    @GetMapping("/pathh")
    ResponseEntity<?> path() {

        List<TreeRepository.Path> path = treeRepository.path();

        return ResponseEntity.ok().body(path);
    }

    @GetMapping("/level")
    ResponseEntity<?> level() {

        List<TreeRepository.Level> level = treeRepository.level();

        return ResponseEntity.ok().body(level);
    }*/
}