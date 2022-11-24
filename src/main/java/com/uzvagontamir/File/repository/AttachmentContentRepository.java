package com.uzvagontamir.File.repository;

import com.uzvagontamir.File.model.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {

    Optional<AttachmentContent> findByAttachmentId(Integer integer);
}
