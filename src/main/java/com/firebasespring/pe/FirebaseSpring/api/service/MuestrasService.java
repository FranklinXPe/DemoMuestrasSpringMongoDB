package com.firebasespring.pe.FirebaseSpring.api.service;

import com.firebasespring.pe.FirebaseSpring.api.daos.IncorrectDaoOperation;
import com.firebasespring.pe.FirebaseSpring.api.daos.MuestraBasicDao;
import com.firebasespring.pe.FirebaseSpring.api.models.Gallery;
import com.firebasespring.pe.FirebaseSpring.api.models.MuestraBasic;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Configuration
public class MuestrasService {



    @Autowired
    private MuestraBasicDao muestraBasicDao;

    public  MuestrasService(){super();}

    public MuestraBasic getMuestra(String id){
        MuestraBasic muestra= muestraBasicDao.getMuestra(id);
       // Muestra muestra= muestraDao.getMuestra(id);

        if(muestra.getId()==null ){
            return null;
        }

        return muestra;
    }

    public Map<String,?> getMuestras(){
        List<MuestraBasic> muestras=muestraBasicDao.getMuestras();
        Map<String, Object> result=new HashMap<>();

        result.put("muestras_list",muestras);
        result.put("muestras_count",muestraBasicDao.getMuestrasCount());

        return  result;
    }

    public long getMuestrasCount(){
        return muestraBasicDao.getMuestrasCount();
    }

    /*-------- AGREGAR -------*/
    public MuestraBasic createMuestra(MuestraBasic  muestra,Map<String,String> errors){
        try{

             return muestraBasicDao.addMuestraBasic(muestra);

        }catch (IncorrectDaoOperation ex){
            errors.put("msg",ex.getMessage());
            return null;
        }

    }

    /*-------- ACTUALIZAR -------*/
    public MuestraBasic updateMuestra(MuestraBasic  muestra,Map<String,String> errors){
        try{
            return muestraBasicDao.updateMuestraBasic(muestra)?muestra:null;

        }catch (IncorrectDaoOperation ex){
            errors.put("msg",ex.getMessage());
            return null;
        }

    }

    public boolean agregarImagenEnGaleria(String ID, Gallery imagen, Map<String,String> results){
        if(!muestraBasicDao.addImageToGallery(new ObjectId(ID),imagen)){
            results.put("error", MessageFormat.format("No se puede agregar la imagen {0}",imagen.toString()));
            return false;
        }
        results.put("ok", MessageFormat.format("Se agrego la imagen {0} satisfactoriamente",imagen.toString()));
        return true;
    }

    public boolean eliminarImagenEnGaleria(String ID, Gallery imagen, Map<String,String> results){
        if(!muestraBasicDao.removeImageToGallery (new ObjectId(ID),imagen)){
            results.put("error", MessageFormat.format("No se puede remover la imagen {0}",imagen.toString()));
            return false;
        }
        results.put("ok", MessageFormat.format("Se removio la imagen {0} satisfactoriamente",imagen.toString()));
        return true;
    }

    /*-------- ELIMINAR -------*/
    public boolean deleteMuestra(ObjectId muestraId, Map<String,Object> results){

        if(!muestraBasicDao.deleteMuestraBasic(muestraId)){
            results.put("error", MessageFormat.format("No se puede eliminar la muestra {0}",muestraId));
            return false;
        }
        results.put("ok", MessageFormat.format("Se eliminó la muestra {0} satisfactoriamente",muestraId));
        return true;

    }
}
