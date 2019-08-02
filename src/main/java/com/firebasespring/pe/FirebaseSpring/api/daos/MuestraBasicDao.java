package com.firebasespring.pe.FirebaseSpring.api.daos;

import com.firebasespring.pe.FirebaseSpring.api.Utils.MuestraBasicCodec;
import com.firebasespring.pe.FirebaseSpring.api.Utils.ObjectIdStringCodec;
import com.firebasespring.pe.FirebaseSpring.api.models.Gallery;
import com.firebasespring.pe.FirebaseSpring.api.models.MuestraBasic;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.ClassModelBuilder;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.codecs.pojo.PropertyModelBuilder;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.bson.codecs.configuration.CodecRegistries.fromCodecs;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


@Component
public class MuestraBasicDao extends  AbstractTasacionesDao {

    public static String MUESTRA_COLLECTION = "muestras";

    private MongoCollection<MuestraBasic> muestrasCollection;


    @Autowired
    public MuestraBasicDao(MongoClient mongoClient, @Value("${spring.mongodb.database}") String databaseName) {
        super(mongoClient, databaseName);

        ClassModelBuilder<MuestraBasic> classModelBuilder = ClassModel.builder(MuestraBasic.class);

        PropertyModelBuilder<ObjectId> idPropertyModelBuilder = (PropertyModelBuilder<ObjectId>) classModelBuilder.getProperty("id");

        idPropertyModelBuilder.codec(new ObjectIdStringCodec());


        MuestraBasicCodec muestraBasicCodec = new MuestraBasicCodec();
       /* CodecRegistry codecRegistry=fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                                    fromCodecs(muestraCodec));*/

        CodecRegistry stringIdCodeRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(
                        PojoCodecProvider.builder()
                                .register(classModelBuilder.build())
                                .automatic(true)
                                .build()

                ));


        muestrasCollection = db.getCollection(MUESTRA_COLLECTION, MuestraBasic.class).withCodecRegistry(stringIdCodeRegistry);



       /* CodecRegistry pojoCodecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));*/


        // muestrasCollection =db.getCollection(MUESTRA_COLLECTION, MuestraBasic.class).withCodecRegistry(codecRegistry) ;

    }

    /*======================    M E T O D O S     ======================*/

    public MuestraBasic getMuestra(String muestraId) {

        Document filter = new Document("_id", muestraId);
        return muestrasCollection.find(filter).limit(1).iterator().tryNext();
    }

    public List<MuestraBasic> getMuestras() {
        List<MuestraBasic> muestras = new ArrayList<>();

        muestrasCollection.find().iterator().forEachRemaining(muestras::add);

        return muestras;
    }

    public long getMuestrasCount() {
        return this.muestrasCollection.countDocuments();
    }

    /*==============================================================*/
    /*====================       C R U D        ====================*/

    public MuestraBasic addMuestraBasic(MuestraBasic muestra) {
        MuestraBasic muestraWithId = muestra.withNewId();
        //MuestraBasic muestraWithId=muestra;

        /*if(!Optional.ofNullable(muestraWithId.getId()).isPresent()){
            throw new IncorrectDaoOperation("Comment id cannot be null");
        }*/

        try {
            // retorna el objecto Muestra con el nuevo Id creado por el servidor

            System.out.println("Antes de agregar a la bd:" + muestraWithId.toString() + " \n " + muestraWithId.getId());

            muestrasCollection.insertOne(muestraWithId);

            System.out.println("Después de agregar a la bd:" + muestraWithId.toString() + " \n " + muestraWithId.getId());

            return muestraWithId;


        } catch (MongoException e) {
            System.out.println("Un error ocurrio mientras se insertaba la muestra");
            return null;
        }

    }

    public boolean updateMuestraBasic(MuestraBasic muestra) {

        // MuestraCodec muestraCodec=new MuestraCodec();
        muestrasCollection.replaceOne(Filters.eq("_id", muestra.getId()), muestra);
        return true;
    }

    public boolean deleteMuestraBasic(ObjectId muestraId) {
        if (!Optional.ofNullable(muestraId).isPresent()) {
            throw new IllegalArgumentException("El id no puede ser null");
        }

        DeleteResult dr = null;
        try {
            dr = muestrasCollection.deleteOne(Filters.eq("_id", muestraId));
        } catch (MongoException e) {
            System.out.println("Un error ocurrio mientras se eliminaba la muestra");
            return false;
        }

        return dr.getDeletedCount() > 0; // si el numero de registros que se elimino es "1"
    }


    /**
     *
     * db.muestras.updateOne({"_id":ObjectId("5d40708c2a40e87c32371560")},
     *                         {$push:{
     *                           "gallery":{
     *                             url:"https://firebasestorage.googleapis.com/v0/b/testauthtoken-cb10a.appspot.com/o/muestras%2F37w5dhck?alt=media&token=e8ea51ac-ce44-4d27-a1a7-9ee70d5899d4",
     *                             file_id:"37w5dhck"
     *                             }
     *                           }
     *                         }
     *                     )
     *
     * */
    public boolean addImageToGallery(ObjectId ID, Gallery imagen) {
        UpdateResult ur = null;

        try {
            // retorna el objecto Muestra con el nuevo Id creado por el servidor

            ur = muestrasCollection.updateOne(
                    Filters.eq("_id", ID),
                    Updates.push("gallery", imagen)
            );

        } catch (MongoException e) {
            System.out.println("Un error ocurrio mientras se agregaba la imagen en la Galeria");
            return false;
        }
        return ur.getModifiedCount() > 0;
    }


    /**
     *
     * db.muestras.updateOne({"_id":ObjectId("5d406f6f2a40e87c3237155f")},
     *                         {$pull:{
     *                           "gallery":{
     *                             url:"OTOP3456126-II.JPG",
     *                             file_id:"http://www.126-II.pe"
     *                             }
     *                           }
     *                         }
     *                     )
     * */

    public boolean removeImageToGallery(ObjectId ID, Gallery imagen) {
        UpdateResult ur = null;

        try {
            // retorna el objecto Muestra con el nuevo Id creado por el servidor

            ur = muestrasCollection.updateOne(
                    Filters.eq("_id", ID),
                    Updates.pull("gallery", imagen)
            );

        } catch (MongoException e) {
            System.out.println("Un error ocurrio mientras se removia la imagen en la Galeria");
            return false;
        }
        return ur.getModifiedCount() > 0;
    }
}
