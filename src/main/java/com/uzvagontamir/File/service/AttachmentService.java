package com.uzvagontamir.File.service;

import com.uzvagontamir.File.model.Attachment;
import com.uzvagontamir.File.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;

    public Attachment save(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public Optional<Attachment> findById(Integer id) {
        return attachmentRepository.findById(id);
    }

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }
}
