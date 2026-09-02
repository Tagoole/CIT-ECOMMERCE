package com.ecommerce.demo.features.notifications.controller;

import com.ecommerce.demo.features.notifications.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(@Valid @RequestBody MessageRequest messageRequest){
        MessageResponse created = messageFacade.create(messageRequest);
        ApiResponse<MessageResponse> body = new ApiResponse<>(
                "SUCCESS",
                "Message Created successfully",
                created
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MessageResponse>> findById(@PathVariable Long id){
        MessageResponse message = messageFacade.findById(id);
        ApiResponse<MessageResponse> body = new ApiResponse<>(
                "SUCCESS",
                "Message fetched Successfully",
                message
        );

        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MessageResponse>>> findAll(){
        List<MessageResponse> messages = messageFacade.findAll();
        ApiResponse<List<MessageResponse>> body = new ApiResponse<>(
                "SUCCESS",
                "Messages fetched Successfully",
                messages
        );

        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MessageResponse>> updateMessage(@Valid @RequestBody MessageRequest messageRequest, @PathVariable Long id){
        MessageResponse message = messageFacade.update(id,messageRequest);
        ApiResponse<MessageResponse> body  = new ApiResponse<>(
                "SUCCESS",
                "Message Updated Successfully",
                message
        );

        return ResponseEntity.ok(body);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id){
        messageFacade.deleteById(id);
        ApiResponse<Void> body = new ApiResponse<>(
          "SUCESS",
          "Message Deleted Successfully",
          null
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(body);

    }

}
