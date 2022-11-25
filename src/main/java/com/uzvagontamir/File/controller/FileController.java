package com.uzvagontamir.File.controller;

import com.uzvagontamir.File.model.Attachment;
import com.uzvagontamir.File.model.AttachmentContent;
import com.uzvagontamir.File.service.AttachmentContentService;
import com.uzvagontamir.File.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Optional;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;


@Controller
public class FileController {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    AttachmentContentService contentService;

    @GetMapping("/uploadFile")
    public String homepage(Model model) {
        model.addAttribute("files", attachmentService.findAll());
        return "main";
    }

    @PostMapping("/upload")
    public String uploadFileToDB(MultipartHttpServletRequest request,@RequestParam(value = "name", required = false) String name, RedirectAttributes attributes) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (!file.isEmpty()) {

            //File haqida malumot olish
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();

            Attachment attachment = new Attachment();
            attachment.setName(name);
            attachment.setFileOriginalName(originalFilename);
            attachment.setContentType(contentType);
            attachment.setSize(size);
            Attachment savedAttachment = attachmentService.save(attachment);

            //Fileni ichidagi Contentni saqlaydi
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            contentService.save(attachmentContent);

            attributes.addFlashAttribute("message", "File " + savedAttachment.getName() + " nomi bilan saqlandi ");

            return "redirect:/uploadFile";
        }
        attributes.addFlashAttribute("message", "File tanlang");
        return "redirect:/uploadFile";
    }


    @GetMapping("/getFile/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentService.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = contentService.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();

                //fileni nomini berish uchun
                response.setHeader("Content-Disposition",
                        "attachment;fileName=\"" + attachment.getFileOriginalName()+"\"");

                //file typeni berish uchun
                response.setContentType(attachment.getContentType());

                //file contentini berish uchun
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
        }
    }

    @GetMapping("/openFile/{id}")
    public void openFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {

        Optional<Attachment> optionalAttachment = attachmentService.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = contentService.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent = contentOptional.get();

                //fileni nomini berish uchun
                response.setHeader("Content-Disposition",
                        "inline;fileName=\"" + attachment.getFileOriginalName()+"\"");

                //file typeni berish uchun
                response.setContentType(attachment.getContentType());

                //file contentini berish uchun
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());

            }
        }
    }

    @GetMapping("/goToFile/{id}")
    public String goToFile(@PathVariable Integer id, HttpServletResponse response, Model model) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentService.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = contentService.findByAttachmentId(id);
            if (contentOptional.isPresent()){
                AttachmentContent attachmentContent =  contentOptional.get();

            }
            model.addAttribute("file", attachment);
        }

        return "open_file";
    }
}
