package com.firebasespring.pe.FirebaseSpring.api.Utils;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

public class ObjectIdStringCodec implements Codec<ObjectId> {
    @Override
    public ObjectId decode(BsonReader reader, DecoderContext decoderContext) {
        return reader.readObjectId();
    }

    @Override
    public void encode(BsonWriter writer, ObjectId value, EncoderContext encoderContext) {
        writer.writeObjectId(value);
    }

    @Override
    public Class<ObjectId> getEncoderClass() {
        return ObjectId.class;
    }
}
