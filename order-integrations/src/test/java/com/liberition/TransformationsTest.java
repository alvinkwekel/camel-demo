package com.liberition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberition.model.sales_order.v1.SalesOrder;
import com.liberition.model.transportbooking.v1.TransportBooking;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

public class TransformationsTest {

    @Test
    public void testTransformation() throws IOException, JAXBException, JSONException {

        // Convert input XML file to SalesOrder object
        File input = new File("src/test/resources/com/liberition/order/sales-order-v1-sample-1.xml");
        SalesOrder salesOrder = (SalesOrder) JAXBContext.newInstance(SalesOrder.class).createUnmarshaller().unmarshal(input);

        // Transform SalesOrder to TransportBooking
        TransportBooking transportBooking = Transformations.salesOrderToTransportBooking(salesOrder);

        // Convert TransportBooking object to JSON string
        String outputJson = new ObjectMapper().writeValueAsString(transportBooking);

        // Read assertion JSON file
        File assertionFile = new File("src/test/resources/com/liberition/order/transport-booking-sample-1.json");
        /*assertion.createNewFile();
        FileUtils.writeStringToFile(assertion, outputJson, Charset.defaultCharset());*/
        String assertionJson = FileUtils.readFileToString(assertionFile, "UTF-8");

        // Compare assertion with output
        JSONAssert.assertEquals(assertionJson, outputJson, true);
    }

    @Test
    public void testDateTransformation() {

        assertEquals("2018-12-09",
            Transformations.transformDate(ZonedDateTime.parse("2018-12-07T10:30:00Z")));
    }
}

