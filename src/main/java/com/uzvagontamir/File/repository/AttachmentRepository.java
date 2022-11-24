package com.uzvagontamir.File.repository;

import com.uzvagontamir.File.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
