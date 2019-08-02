package com.firebasespring.pe.FirebaseSpring.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.util.List;

public class MuestraBasic {


    //@BsonIgnore
    private ObjectId id;
    private String sid;
    private String tipo;
    private String nombre;
    private List<Gallery> gallery;

    public MuestraBasic() {
    }



    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public void setGallery(List<Gallery> gallery) {
        this.gallery = gallery;
    }

    @Override
    public String toString() {
        return "MuestraBasic{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", gallery=" + gallery +
                '}';
    }





    /*==================== OTROS METODOS ====================*/
   // @JsonIgnore
    /*public  boolean isEmpty(){
        return this.nombre==null || "".equals(this.getNombre());
    }*/

    public MuestraBasic withNewId(){
        setId(new ObjectId());
        this.sid=getId().toHexString();
        return this;
    }

}
