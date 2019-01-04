package com.bonial.tmapipoc;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TreeRepository extends PagingAndSortingRepository<Tree, Long> {

    //@Query(value = "SELECT * FROM Tree WHERE path <@ '" + ":pathToSearch" +"'", nativeQuery = true)
    @Query(value = "SELECT * FROM Tree WHERE path <@ 'A'", nativeQuery = true)
    List<Tree> findAllByPath(@Param("pathToSearch") String pathToSearch);

    @Query(value = "SELECT COUNT(*) FROM Tree WHERE path <@ 'A'", nativeQuery = true)
    Long countByPath(@Param("pathToSearch") String pathToSearch);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Tree WHERE path <@ 'A.C'", nativeQuery = true)
    void deleteAllByPath(@Param("pathToDelete") String pathToDelete);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Tree SET path = subpath(path, nlevel('A.C')-1) WHERE path <@ 'A.C'", nativeQuery = true)
    void moveTreeOneLevelUp(@Param("pathToMove") String pathToMove);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Tree SET path = 'A.B.G' || path WHERE path <@ 'C'", nativeQuery = true)
    void moveRootTreeDown(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Tree SET path = 'A.B.G' || subpath(path, 1) WHERE path <@ 'A.C'", nativeQuery = true)
    void moveNonRootTreeDown(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tree (letter, path) (SELECT letter, 'A.B.G' || subpath(path, 1) FROM tree WHERE 'A.C' @> path)", nativeQuery = true)
    void copyTree(@Param("destinationPath") String destinationPath, @Param("sourcePath") String sourcePath);

    @Query(value = "SELECT letter, CAST(path as TEXT), nlevel(path) FROM Tree", nativeQuery = true)
    List<TreeNLevel> nlevel();

    interface TreeNLevel {

        String getLetter();
        String getPath();
        Integer getNlevel();
    }
/*
    @Query(value = "SELECT letter FROM Tree", nativeQuery = true)
    List<Letter> letter();

    interface Letter {

        @Type(type = "character")
        String getLetter();
    }

    @Query(value = "SELECT CAST(path as TEXT) FROM Tree", nativeQuery = true)
        //@Query(value = "SELECT CAST(letter AS VARCHAR), CAST(path AS VARCHAR), CAST(nlevel(path) AS VARCHAR) FROM Tree", nativeQuery = true)
    List<Path> path();

    interface Path {

        //@Type(type = "ltree")
        String getPath();
    }

    @Query(value = "SELECT nlevel(path) FROM Tree", nativeQuery = true)
        //@Query(value = "SELECT CAST(letter AS VARCHAR), CAST(path AS VARCHAR), CAST(nlevel(path) AS VARCHAR) FROM Tree", nativeQuery = true)
    List<Level> level();

    interface Level {

        Integer getNlevel();
    }*/
}
