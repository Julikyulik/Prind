package com.example.Prind.Controller;

import com.example.Prind.Domain.Prinad;
import com.example.Prind.Service.ServicePrinad;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/prinads")
@AllArgsConstructor
public class ControllerPrinad {
    private final ServicePrinad servicePrinad;

    @PostMapping()
    public ResponseEntity<?> savedCheck(@RequestBody Prinad prinad) throws IOException {
        servicePrinad.save(prinad);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/read")
    public List<Prinad> readChecks() throws IOException {
        return servicePrinad.readAll();
    }

    @GetMapping(value = "/{snum}")
    public ResponseEntity<?> readNum(@PathVariable(name = "snum") int snum) throws IOException {
        final Prinad prinad = servicePrinad.readSerNum(snum);
        return prinad != null
                ? new ResponseEntity<>(prinad, HttpStatus.OK)
                : new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{snum}")
    public ResponseEntity<?> updatePrinad(@PathVariable(name = "snum") int snum, @RequestBody Prinad prinad) throws IOException {
       int updStatus = servicePrinad.updatePrinad(prinad, snum);
        if (updStatus==0){
           return new ResponseEntity<>(HttpStatus.OK);
       }
       else {
           if(updStatus==1){
               return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
           } else {
               return new ResponseEntity<String>("Already exist", HttpStatus.FOUND);
           }
            //проверку на поиск-наличие элемнета
       }
    }

    @DeleteMapping(value = "/{snum}")
    public ResponseEntity<?> deletePrinad(@PathVariable(name = "snum") int snum) throws IOException {
        if (servicePrinad.deletePrinad(snum)){
            return new ResponseEntity<String>("Data deleted", HttpStatus.OK);
        }
        else {
           return new  ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/information")

    public String information()  throws ParserConfigurationException, IOException, SAXException {
        return String.format("Проект: 4.32. Сущность: пишущие принадлежности. Количество сущностей: %s. Выполнила: Панкова Юлия", servicePrinad.readAll().size());}

    /*@GetMapping(value ="/set-Header")
    public ResponseEntity<?> setHeader(){
        return ResponseEntity.ok().header("Julia", "20").body(HttpStatus.OK);
    }

    @GetMapping(value = "/change-Header")
    public ResponseEntity<?> changeHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setDate(5);
        return ResponseEntity
                .ok().headers(httpHeaders)
                .body(HttpStatus.OK);
    }*/
}
