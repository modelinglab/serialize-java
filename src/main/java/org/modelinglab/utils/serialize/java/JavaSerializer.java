/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.modelinglab.utils.serialize.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import org.modelinglab.utils.serialize.core.Serializer;
import org.modelinglab.utils.serialize.core.SerializerException;
import org.modelinglab.utils.serialize.core.SerializerIOException;

/**
 *
 */
public class JavaSerializer implements Serializable, Serializer {

    @Override
    public <E> E load(InputStream is, Class<E> expectedClass) throws SerializerException, ClassCastException {
        try {
            ObjectInput ois = new ObjectInputStream(is);
            return (E) ois.readObject();
        } catch (IOException ex) {
            throw new SerializerIOException(ex);
        } catch (ClassNotFoundException ex) {
            throw new SerializerException(ex);
        }
    }

    @Override
    public <E> E load(URL resourceUri, Class<E> expectedClass) throws SerializerException, ClassCastException {
        try (InputStream is = resourceUri.openStream()) {
            return load(is, expectedClass);
        } catch (IOException ex) {
            throw new SerializerIOException(ex);
        }
    }

    @Override
    public <E> E load(File resourceFile, Class<E> expectedClass) throws SerializerException, ClassCastException {
        try {
            return load(new FileInputStream(resourceFile), expectedClass);
        } catch (IOException ex) {
            throw new SerializerIOException(ex);
        }
    }

    @Override
    public void save(Object object, OutputStream output) throws SerializerException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(object);
        } catch (IOException ex) {
            throw new SerializerIOException(ex);
        }
    }

    @Override
    public void save(Object object, File output) throws SerializerException {
        try (OutputStream os = new FileOutputStream(output)) {
            save(object, os);
        } catch (IOException ex) {
            throw new SerializerIOException(ex);
        }
    }
}
