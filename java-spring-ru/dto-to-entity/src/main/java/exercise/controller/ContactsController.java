package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping(path = "")
    public ResponseEntity<ContactDTO> create(@RequestBody ContactCreateDTO contactCreateDTO) {
        var contact = toEntity(contactCreateDTO);
        contactRepository.save(contact);
        var dto = toDTO(contact);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    private Contact toEntity(ContactCreateDTO contactCreateDTO) {
        var contact = new Contact();
        contact.setPhone(contactCreateDTO.getPhone());
        contact.setLastName(contactCreateDTO.getLastName());
        contact.setFirstName(contactCreateDTO.getFirstName());
        return contact;
    }

    private ContactDTO toDTO(Contact contact) {
        var dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setPhone(contact.getPhone());
        dto.setLastName(contact.getLastName());
        dto.setFirstName(contact.getFirstName());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());

        return dto;
    }
}
