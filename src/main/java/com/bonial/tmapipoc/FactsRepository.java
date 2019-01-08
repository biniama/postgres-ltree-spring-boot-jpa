package com.bonial.tmapipoc;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FactsRepository extends PagingAndSortingRepository<Facts, Long> {

    @Query(value = "SELECT * FROM Facts WHERE path <@ CAST(:pathToSearch AS ltree)", nativeQuery = true)
    List<Facts> findAllByPath(@Param("pathToSearch") String pathToSearch);

    @Query(value = "SELECT COUNT(*) FROM Facts WHERE path <@ CAST(:pathToSearch AS ltree)", nativeQuery = true)
    Long getCountOfElementsByPath(@Param("pathToSearch") String pathToSearch);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Facts (path, boost, rules) " +
            "VALUES (CAST(:path AS ltree), :boost, CAST(:rules as jsonb)) " +
            "ON CONFLICT (path) DO UPDATE " +
            "  SET boost = :boost, " +
            "      rules = CAST(:rules as jsonb)", nativeQuery = true)
    void upsert(@Param("path") String path, @Param("boost") Integer boost, @Param("rules") String rules);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Facts WHERE path <@ CAST(:pathToDelete AS ltree)", nativeQuery = true)
    void deleteAllByPath(@Param("pathToDelete") String pathToDelete);

    @Query(value = "SELECT CAST(path as TEXT), boost, nlevel(path) FROM Facts", nativeQuery = true)
    List<TreeNLevel> nLevel();


    @Modifying
    @Transactional
    @Query(value = "UPDATE Facts SET path = subpath(path, nlevel('A.C')-1) WHERE path <@ CAST(:pathToMove AS ltree)", nativeQuery = true)
    void moveTreeOneLevelUp(@Param("pathToMove") String pathToMove);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Facts SET path = CAST(:destinationPath AS ltree) || path WHERE path <@ CAST(:sourcePath AS ltree)", nativeQuery = true)
    void moveRootTreeDown(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Facts SET path = CAST(:destinationPath AS ltree) || subpath(path, 1) WHERE path <@ CAST(:sourcePath AS ltree)", nativeQuery = true)
    void moveNonRootTreeDown(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Facts (letter, path) (SELECT letter, CAST(:destinationPath AS ltree) || subpath(path, 1) FROM Facts WHERE CAST(:sourcePath AS ltree) @> path)", nativeQuery = true)
    void copyTree(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    interface TreeNLevel {

        String getLetter();
        String getPath();
        Integer getNlevel();
    }

    /*
    @Query(value = "SELECT letter FROM Fact", nativeQuery = true)
    List<Letter> letter();

    interface Letter {

        @Type(type = "character")
        String getLetter();
    }

    @Query(value = "SELECT CAST(path as TEXT) FROM Fact", nativeQuery = true)
        //@Query(value = "SELECT CAST(letter AS VARCHAR), CAST(path AS VARCHAR), CAST(nlevel(path) AS VARCHAR) FROM Fact", nativeQuery = true)
    List<Path> path();

    interface Path {

        //@Type(type = "ltree")
        String getPath();
    }

    @Query(value = "SELECT nlevel(path) FROM Fact", nativeQuery = true)
        //@Query(value = "SELECT CAST(letter AS VARCHAR), CAST(path AS VARCHAR), CAST(nlevel(path) AS VARCHAR) FROM Fact", nativeQuery = true)
    List<Level> level();

    interface Level {

        Integer getNlevel();
    }*/
}
