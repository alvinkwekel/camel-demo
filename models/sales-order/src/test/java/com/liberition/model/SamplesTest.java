package com.liberition.model;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SamplesTest {

    @Test
    public void testModelSamples() throws SAXException, IOException {

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaFile = new File("src/main/resources/com/liberition/model/salesorder/v1/sales-order-v1.xsd");
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();

        File samplesDirectory = new File("src/test/resources/com/liberition/model/salesorder/v1/");
        File[] sampleFiles = samplesDirectory.listFiles();
        Assert.assertEquals(sampleFiles.length, 1);

        for (File sampleFile : sampleFiles) {
            validateFile(validator, sampleFile);
        }
    }

    public void validateFile(Validator validator, File sampleFile) throws SAXException, IOException {

        InputStream targetStream = new FileInputStream(sampleFile);
        Source source = new StreamSource(targetStream);
        validator.validate(source);
    }
}
