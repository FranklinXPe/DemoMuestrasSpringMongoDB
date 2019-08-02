package com.firebasespring.pe.FirebaseSpring.api.controllers;

import com.firebasespring.pe.FirebaseSpring.api.exception.HTTP400Exception;
import com.firebasespring.pe.FirebaseSpring.api.models.Gallery;
import com.firebasespring.pe.FirebaseSpring.api.models.MuestraBasic;
import com.firebasespring.pe.FirebaseSpring.api.service.MuestrasService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/muestras")
public class MuestraController {

    @Autowired
    private MuestrasService muestrasService;

    public MuestraController(){
        super();
    }

    /*==== OBTIENE TODAS LAS MUESTRAS ====*/
    @GetMapping(value = "/")
    public ResponseEntity<Map> getMuestras(){
        return buildOkResponse(muestrasService.getMuestras());
    }

    /*==== OBTIENE UNA MUESTRA EN BASE AL ID ====*/
    @GetMapping(value = "/{muestraId}")
    ResponseEntity getMuestra(@PathVariable(value = "muestraId") String muestraId){
        HashMap<String,Object> result=new HashMap<>();
        MuestraBasic muestra=muestrasService.getMuestra(muestraId);
        if(muestra ==null){
            result.put("error","Not FOUND");
            return ResponseEntity.badRequest().body(result);
        }

        result.put("muestra",muestra);
        return ResponseEntity.ok(result);
    }

    /*==== AGREGAR MUESTRA ====*/

    @RequestMapping(value = "",method = RequestMethod.POST,consumes = { "application/json"},produces = { "application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMuestra(@RequestBody MuestraBasic muestra, HttpServletRequest request, HttpServletResponse response){


        Map<String,String> results=new HashMap<>();

        MuestraBasic  newMuestra=muestrasService.createMuestra(muestra,results);
        if(newMuestra==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(results);

        return ResponseEntity.ok(newMuestra);
    }

    /*==== MODIFICAR MUESTRA ====*/
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT,consumes = {"application/json"},produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMuestra(@PathVariable("id") String id, @RequestBody MuestraBasic muestra, HttpServletRequest request, HttpServletResponse response){
        ObjectId idMongo=new ObjectId(id);

        Map<String,String> results=new HashMap<>();
       if(! idMongo.equals( muestra.getId()))
           throw new HTTP400Exception("El ID no coincide!");
       else
           this.muestrasService.updateMuestra(muestra,results);

    }

    /*==== AGREGAR IMAGEN A LA GALERIA ====*/
    @RequestMapping(value = "/{id}/galeria/add",method = RequestMethod.PUT,consumes = {"application/json"},produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addImagenGallery(@PathVariable("id") String id, @RequestBody Gallery imagen, HttpServletRequest request, HttpServletResponse response){

        Map<String,String> results=new HashMap<>();

        if(imagen ==null)
            throw new HTTP400Exception("No existe la imagen!");
        else
            this.muestrasService.agregarImagenEnGaleria(id,imagen,results);
    }

    /*==== QUITAR IMAGEN DE LA GALERIA ====*/
    @RequestMapping(value = "/{id}/galeria/delete",method = RequestMethod.PUT,consumes = {"application/json"},produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImagenGallery(@PathVariable("id") String id, @RequestBody Gallery imagen, HttpServletRequest request, HttpServletResponse response){

        Map<String,String> results=new HashMap<>();

        if(imagen ==null)
            throw new HTTP400Exception("No existe la imagen!");
        else
            this.muestrasService.eliminarImagenEnGaleria(id,imagen,results);

    }



    /*==== ELIMINAR MUESTRA ====*/
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE,consumes = {"application/json"},produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteMuestra(@PathVariable("id") String sid, HttpServletRequest request, HttpServletResponse response){

        System.out.println(sid);

        Map<String,Object> results=new HashMap<>();

        if(sid  ==null || sid.equals(""))
        {
            results.put("error","Código de la muestra no encontrado");
            return ResponseEntity.badRequest().body(results);
        }
        else {
            ObjectId idMuestraDelete=new ObjectId(sid);

            this.muestrasService.deleteMuestra(idMuestraDelete, results);
            return ResponseEntity.ok(results);
        }

    }

    /*============= OTROS METODOS ==============*/
    private ResponseEntity<Map> buildOkResponse(Map<String,?> muestraResults){

        Map<String,Object> results=new HashMap<>();
        results.put("muestras",muestraResults.get("muestras_list"));
        if(muestraResults.containsKey("muestras_count")){
            results.put("total_results",muestraResults.get("muestras_count"));
        }

        return ResponseEntity.ok(results);

    }


}
