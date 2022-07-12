package com.example.Prind.Repository;

import com.example.Prind.Domain.Prinad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryPrinadTest {
    RepositoryPrinad repositoryPrinad;

    @BeforeEach
    void init() {
        repositoryPrinad = new RepositoryPrinad();
    }

    @Test
    void findALL() throws IOException {
        repositoryPrinad.findALL().forEach(System.out::println);
    }

    @Test
    void addPrinad() throws IOException {
        repositoryPrinad.savePrinad(new Prinad(4,"red",4,5));
    }

    @Test
    void update() throws IOException {
        repositoryPrinad.update(new Prinad(1,"blue",4,5), 1);
    }

    @Test
    void getBySerialNumber() throws IOException {
        System.out.println(repositoryPrinad.getBySerialNumber(1));
    }

    @Test
    void deleteByPrinad() throws IOException {
        repositoryPrinad.deleteByPrinad(3);
    }

    @Test
    void savePrinad() {
    }
}