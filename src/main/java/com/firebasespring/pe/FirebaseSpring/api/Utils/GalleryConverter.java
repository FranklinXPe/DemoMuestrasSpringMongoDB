package com.firebasespring.pe.FirebaseSpring.api.Utils;

import com.firebasespring.pe.FirebaseSpring.api.models.Gallery;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class GalleryConverter {

    public Document convert(Gallery item){
        Document document=new Document();
        document.put("file_id",item.getFile_id());
        document.put("url",item.getUrl());

        return document;
    }

    public List<Document> convertToListDocument(List<Gallery> galeria){
        List<Document> documentosGaleria=new ArrayList<Document>();
        Document item=new Document();
        for (Gallery imagen: galeria) {
            documentosGaleria.add(convert(imagen));

        }

        return documentosGaleria;
    }

    public Gallery convert(Document document){
        Gallery item=new Gallery();
        item.setFile_id(document.getString("_id"));
        item.setUrl(document.getString("url"));

        return item;
    }

    public List<Gallery> convertToListGallery(List<Document> galeria){
        List<Gallery> documentosGaleria=new ArrayList<Gallery>();
        Gallery item=new Gallery();
        for (Document imagen: galeria) {
           documentosGaleria.add(convert(imagen))  ;

        }

        return documentosGaleria;
    }
}
