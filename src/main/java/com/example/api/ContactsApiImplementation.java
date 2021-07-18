package com.example.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.model.ContactResponseDataContract;
import com.example.service.SalesforceConnectionService;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.Contact;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;

@Service
public class ContactsApiImplementation implements ContactsApiDelegate {

	@Autowired
	SalesforceConnectionService connService;

	/**
	 * GET /contacts : Contacts_GET
	 *
	 * @param email (required)
	 * @return (status code 200)
	 * @see ContactsApi#contactsGET
	 */
	public ResponseEntity<ContactResponseDataContract> contactsGET(String email) {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					String exampleString = "null";
					ApiUtil.setExampleResponse(request, "application/json", exampleString);
					break;
				}
			}
		});

		// Set the response
		/*ContactResponseDataContract contact = new ContactResponseDataContract();
		contact.email(email);
		contact.firstName("HELLO WORLD");

		connService.getConnection();*/

		///////////////

		List<ContactResponseDataContract> results = new ArrayList<>();
		ContactResponseDataContract aContact = new ContactResponseDataContract();
		
		String soqlQuery = "SELECT FirstName, LastName, Email FROM Contact";
		try {
			QueryResult qr = connService.getConnection().query(soqlQuery);
			boolean done = false;

			if (qr.getSize() > 0) {

				SObject[] records = qr.getRecords();
				Contact record = (Contact) records[0];
				
				//aContact.address(record.getMailingAddress());
				aContact.email(record.getEmail());
				aContact.firstName(record.getFirstName());
				aContact.lastName(record.getLastName());
				results.add(aContact);

			} else {
				System.out.println("No records found.");
			}
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		} finally {
			//
		}

		///////////////

		// Create the response entity
		ResponseEntity<ContactResponseDataContract> result = new ResponseEntity<ContactResponseDataContract>(aContact,
				HttpStatus.OK);
		return result;

	}

}
