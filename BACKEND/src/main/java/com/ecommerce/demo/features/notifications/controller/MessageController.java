package com.ecommerce.demo.features.notifications.controller;

import com.ecommerce.demo.features.notifications.dto.MessageApiResponse;
import com.ecommerce.demo.features.notifications.dto.MessageRequest;
import com.ecommerce.demo.features.notifications.dto.MessageResponse;
import com.ecommerce.demo.features.notifications.facade.MessageFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageFacade messageFacade;
    public MessageController(MessageFacade messageFacade){
        this.messageFacade= messageFacade;
    }

    @PostMapping()
    public ResponseEntity<MessageApiResponse<MessageResponse>> createMessage(@Valid @RequestBody MessageRequest messageRequest){
        MessageResponse created = messageFacade.create(messageRequest);
        MessageApiResponse<MessageResponse> body = new MessageApiResponse<>(
                "SUCCESS",
                "Message Created successfully",
                created
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MessageApiResponse<MessageResponse>> findById(@PathVariable Long id){
        MessageResponse message = messageFacade.findById(id);
        MessageApiResponse<MessageResponse> body = new MessageApiResponse<>(
                "SUCCESS",
                "Message fetched Successfully",
                message
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<MessageApiResponse<List<MessageResponse>>> findAll(){
        List<MessageResponse> messages = messageFacade.findAll();
        MessageApiResponse<List<MessageResponse>> body = new MessageApiResponse<>(
                "SUCCESS",
                "Messages fetched Successfully",
                messages
        );

        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageApiResponse<MessageResponse>> updateMessage(@Valid @RequestBody MessageRequest messageRequest, @PathVariable Long id){
        MessageResponse message = messageFacade.update(id,messageRequest);
        MessageApiResponse<MessageResponse> body  = new MessageApiResponse<>(
                "SUCCESS",
                "Message Updated Successfully",
                message
        );
        
        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageApiResponse<Void>> deleteById(@PathVariable Long id){
        messageFacade.deleteById(id);
        MessageApiResponse<Void> body = new MessageApiResponse<>(
          "SUCESS",
          "Message Deleted Successfully",
          null
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);

    }

}
