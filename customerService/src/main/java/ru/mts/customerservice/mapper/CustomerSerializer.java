package ru.mts.customerservice.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.mts.customerservice.entity.Customer;

import java.io.IOException;

public class CustomerSerializer extends StdSerializer<Customer> {
    protected CustomerSerializer() {
        super(Customer.class);
    }

    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", customer.getId_customer());
        jsonGenerator.writeStringField("phoneNumber", customer.getPhoneNumber());
        jsonGenerator.writeNumberField("bankAccountId", customer.getBank_account_id());
        jsonGenerator.writeEndObject();
    }
}
