package com.biniam.tmapipoc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final TreeRepository treeRepository;

    @Autowired
    public HelloController(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    @GetMapping("/")
    String hello() {
        return "Implementation of Postgres LTree in Spring Boot app!";
    }

    @GetMapping("/path")
    ResponseEntity<?> findAllByPath() {

        List<Tree> allByPath = treeRepository.findAllByPath("A");

        return ResponseEntity.ok().body(allByPath);
    }

    @GetMapping("/countByPath")
    ResponseEntity<?> countByPath() {

        return ResponseEntity.ok().body(treeRepository.countByPath("A"));
    }

    @GetMapping("/deleteAllByPath")
    ResponseEntity<?> deleteAllByPath() {

        treeRepository.deleteAllByPath("A.C");
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/moveTreeOneLevelUp")
    ResponseEntity<?> moveTreeOneLevelUp() {

        treeRepository.moveTreeOneLevelUp("A.C");
        return ResponseEntity.ok().body("moveTreeOneLevelUp");
    }

    @GetMapping("/moveRootTreeDown")
    ResponseEntity<?> moveRootTreeDown() {

        treeRepository.moveRootTreeDown("A.B.G", "C");
        return ResponseEntity.ok().body("moveRootTreeDown");
    }

    @GetMapping("/moveNonRootTreeDown")
    ResponseEntity<?> moveNonRootTreeDown() {

        treeRepository.moveNonRootTreeDown("A.B.G", "A.C");
        return ResponseEntity.ok().body("moveNonRootTreeDown");
    }

    @GetMapping("/copyTree")
    ResponseEntity<?> copyTree() {

        treeRepository.copyTree("A.B.G", "A.C");
        return ResponseEntity.ok().body("copyTree");
    }

    @GetMapping("/nlevel")
    ResponseEntity<?> nlevel() {

        List<TreeRepository.TreeNLevel> nLevel = treeRepository.nlevel();

        return ResponseEntity.ok().body(nLevel);
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