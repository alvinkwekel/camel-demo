package com.liberition.shopconnector;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TransformationTest {

    public void validate(File assertion, String schemaClassPath, LSResourceResolver resourceResolver) throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setResourceResolver(resourceResolver);
        Source schemaFile = new StreamSource(getClass().getClassLoader()
            .getResourceAsStream(schemaClassPath));
        try {
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            InputStream targetStream = new FileInputStream(assertion);
            Source source = new StreamSource(targetStream);
            validator.validate(source);
        } catch (SAXException e) {
            throw new IllegalArgumentException("Assertion file " + assertion.getName() + " doesn't conform to schema ", e);
        }
    }

    public void validate(File assertion, String schemaClassPath) throws SAXException, IOException {
        validate(assertion, schemaClassPath, null);
    }
}
