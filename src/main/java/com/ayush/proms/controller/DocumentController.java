package com.ayush.proms.controller;

import com.ayush.proms.pojos.DocumentPOJO;
import com.ayush.proms.service.DocumentService;
import com.ayush.proms.utils.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/document")
@CrossOrigin(origins = "http://localhost:3000")
public class DocumentController extends BaseController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity uploadDocument(@ModelAttribute @RequestBody DocumentPOJO documentPOJO){
        Long data = 0L;
        try {
            data = documentService.upload(documentPOJO);
        } catch (IOException e) {
            data=0L;
            e.printStackTrace();
        }
        if (data>=1){
            return new ResponseEntity(successResponse(customMessageSource.get("crud.save",customMessageSource.get("document")),data), HttpStatus.OK);
        }else {
            return new ResponseEntity(errorResponse(customMessageSource.get("crud.failed",customMessageSource.get("document")),data),HttpStatus.OK);
        }
    }

    @GetMapping("/view-or-download/view/{documentId}")
    public ResponseEntity getDocument(@PathVariable("documentId") Long documentId, HttpServletResponse httpServletResponse) throws IOException {
        String action="view";
        String jsonData = documentService.getDocument(documentId, action, httpServletResponse);
        return ResponseEntity.ok(successResponse(customMessageSource.get("document.fetch"),jsonData));
    }


}
