package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.model.ContactResponseDataContract;

@Service
public class ContactsApiImplementation implements ContactsApiDelegate {

    /**
     * GET /contacts : Contacts_GET
     *
     * @param email  (required)
     * @return  (status code 200)
     * @see ContactsApi#contactsGET
     */
     public ResponseEntity<ContactResponseDataContract> contactsGET(String email) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "null";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        
        // Set the response
        ContactResponseDataContract contact = new ContactResponseDataContract();
        contact.email(email);
        contact.firstName("HELLO WORLD");
        
        // Create the response entity
        ResponseEntity<ContactResponseDataContract> result = new ResponseEntity<ContactResponseDataContract>(contact, HttpStatus.OK);
        return result;

    }
	
}
