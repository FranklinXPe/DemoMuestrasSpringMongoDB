package com.firebasespring.pe.FirebaseSpring.api.Utils;

import com.firebasespring.pe.FirebaseSpring.api.models.Gallery;
import com.firebasespring.pe.FirebaseSpring.api.models.MuestraBasic;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.types.ObjectId;

import java.util.List;

public class MuestraBasicCodec implements CollectibleCodec<MuestraBasic> {

    private final Codec<Document> documentCodec;
    private final GalleryConverter converter;

    public MuestraBasicCodec(){
        super();
        this.documentCodec=new DocumentCodec();
        this.converter=new GalleryConverter();
    }



    @Override
    public MuestraBasic generateIdIfAbsentFromDocument(MuestraBasic muestra) {
        return !documentHasId(muestra)? muestra.withNewId(): muestra;
    }

    @Override
    public boolean documentHasId(MuestraBasic muestra) {
        return null != muestra.getId();

    }

    @Override
    public BsonValue getDocumentId(MuestraBasic muestra) {
        if(documentHasId(muestra)){
            throw new IllegalStateException("Este documento no tiene un _id");
        }
        return new BsonObjectId(muestra.getId());
    }

    @Override
    public MuestraBasic decode(BsonReader reader, DecoderContext decoderContext) {
        Document muestraDoc=documentCodec.decode(reader,decoderContext);



        MuestraBasic muestra=new MuestraBasic();

        muestra.setId(muestraDoc.getObjectId("_id"));
        muestra.setTipo(muestraDoc.getString("tipo"));
        muestra.setNombre(muestraDoc.getString("nombre"));
        muestra.setGallery(converter.convertToListGallery((List<Document>) muestraDoc.get("gallery")));

        return muestra;

    }

    @Override
    public void encode(BsonWriter writer, MuestraBasic muestra, EncoderContext encoderContext) {

        Document muestraDoc=new Document();

        ObjectId muestraId= muestra.getId();
        String tipo=muestra.getTipo();
        String nombre=muestra.getNombre();
        List<Gallery> imagenes=muestra.getGallery();



        if(null != muestraId){
            muestraDoc.put("_id",muestraId);
        }
        if(null != tipo){
            muestraDoc.put("tipo",tipo);
        }
        if(null != nombre){
            muestraDoc.put("nombre",nombre);
        }
        if(null != imagenes){
            muestraDoc.put("gallery", converter.convertToListDocument(imagenes) );
        }

        documentCodec.encode(writer,muestraDoc,encoderContext);
    }

    @Override
    public Class<MuestraBasic> getEncoderClass() {
        return MuestraBasic.class;
    }
}
