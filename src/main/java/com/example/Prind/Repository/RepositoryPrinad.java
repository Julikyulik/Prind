package com.example.Prind.Repository;

import com.example.Prind.Domain.Prinad;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryPrinad {
    public List<Prinad> findALL() throws IOException {
        File file = new File("src/main/resources/prinad.yaml");
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        List<Prinad> prinads = new ArrayList<>(Arrays.asList(om.readValue(file, Prinad[].class)));
        return prinads;
    }
    public void addPrinad(List<Prinad> prinads) throws IOException {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        om.writeValue(new File("src/main/resources/prinad.yaml"), prinads);
    }
    public void update(Prinad prinad, int snum) throws IOException {
        List<Prinad> prinads = findALL();
        for (int i = 0; i < prinads.size(); i++){
            if (prinads.get(i).getSerialNum() == snum){
                prinads.set(i, prinad);
            }
        }
        addPrinad(prinads);
    }
    public Prinad getBySerialNumber(int snum) throws IOException, IllegalAccessError {
        final List<Prinad> prinads = findALL();
        for (int i = 0; i < prinads.size(); i++){
            if (prinads.get(i).getSerialNum() == snum){
                return prinads.get(i);
            }
        }
        throw new IllegalAccessError("Not found choose another number!");
    }
    public void deleteByPrinad(int snum) throws IOException {
        final List<Prinad> prinads = findALL().stream()
                .filter(c -> c.getSerialNum() != snum)
                .collect(Collectors.toList());
        addPrinad(prinads);
    }
    public void savePrinad(Prinad prinad) throws IOException {
        List<Prinad> prinads = findALL();
        prinads.add(prinad);
        addPrinad(prinads);
    }
}
