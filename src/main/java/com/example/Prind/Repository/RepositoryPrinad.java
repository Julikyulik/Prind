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


    public int update(Prinad prinad, int snum) throws IOException {
        int status = 1;//статус по умолч. пишущая принадлежность отсутствует
        boolean isFound = false;
        boolean alreadyExist = false;
        List<Prinad> prinads = findALL();//счит данных из файла

        for (int i = 0; i < prinads.size(); i++){
            if (prinads.get(i).getSerialNum() == prinad.getSerialNum()){
              alreadyExist = true; //обновить сер. номер не возможно, т.к имеется пиш.принадлежность с таким сер.номером
            }
        }
        for (int i = 0; i < prinads.size(); i++){
            if (prinads.get(i).getSerialNum() == snum){
                prinads.set(i, prinad);
                isFound = true; // найдена пишущая принадлежность с сер.номером
            }
        }
        if (isFound != true){
            status = 1;//не найден
        }
        else {
            if (alreadyExist){
                status = 2;// не можем заменить сер.номер, т.к такой сер.номер сущ. в пиш.прин;
            }
            else {
                status = 0;//можем заменить сер.номер и ост.параметры
                addPrinad(prinads);
            }
        }
        return status;
    }
    public Prinad getBySerialNumber(int snum) throws IOException {
        final List<Prinad> prinads = findALL();
        for (int i = 0; i < prinads.size(); i++){
            if (prinads.get(i).getSerialNum() == snum){
                return prinads.get(i);
            }
        }
        return null;
    }
    public boolean deleteByPrinad(int snum) throws IOException {
        final List<Prinad> prinadsBefore= findALL();
        final List<Prinad> prinads = findALL().stream()
                .filter(c -> c.getSerialNum() != snum)
                .collect(Collectors.toList());
        if (prinadsBefore != null && prinads != null){
            if (prinadsBefore.size() > prinads.size()){
                addPrinad(prinads);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    public void savePrinad(Prinad prinad) throws IOException {
        List<Prinad> prinads = findALL();
        prinads.add(prinad);
        addPrinad(prinads);
    }
}
