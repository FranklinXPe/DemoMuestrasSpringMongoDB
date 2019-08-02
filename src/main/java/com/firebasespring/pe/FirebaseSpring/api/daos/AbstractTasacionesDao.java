package com.firebasespring.pe.FirebaseSpring.api.daos;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public abstract class AbstractTasacionesDao {

    @Value("${spring.mongodb.uri}")
    private String  connectionString;

    protected final String MUESTRAS_DATABASE;

    protected MongoDatabase db;
    protected MongoClient mongoClient;


    public AbstractTasacionesDao(MongoClient mongoClient, String databaseName) {

        this.mongoClient = mongoClient;
        MUESTRAS_DATABASE=databaseName;

        this.db=this.mongoClient.getDatabase(MUESTRAS_DATABASE);
    }

    public ObjectId generateObjectId(){
        return new ObjectId();
    }

    public Map<String, Object> getConfiguration(){

        ConnectionString connString =new ConnectionString(connectionString);
        Bson command=new Document("connectionStatus",1);
        Document connectionStatus=this.mongoClient.getDatabase(MUESTRAS_DATABASE).runCommand(command);


        List authUserRoles= ((Document) connectionStatus.get("authInfo")).get("authenticationUserRoles",List.class);

        Map<String,Object> configuration=new HashMap<>();
        if(!authUserRoles.isEmpty()){
            configuration.put("role",((Document) authUserRoles.get(0)).getString("role"));
            configuration.put("pool_size",connString.getMaxConnectionPoolSize());
            configuration.put("wtimeout",this.mongoClient.getDatabase("demomuestras").getWriteConcern().getWTimeout(TimeUnit.MILLISECONDS));
        }
        return configuration;

    }

}
