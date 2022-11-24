package com.uzvagontamir.File.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileOriginalName;  // tdtu.jpg, tdtu.pdf
    private String contentType;  // application/pdf, image/png  google->file content type
    private Long size;  //fileni necha baytligi
    private String name; // systemga yuklaganda qanday nom bilan papkaga joylash 2ta bir xil nom bob qolmasligi uchun
    private String DepoNomi; //O'vt tex.adel

    private Boolean isOTChecked; //O'vt tex.adel
    private Boolean isOEChecked; //O'vt Ekonomist
    private Boolean isUTChecked; //Upr. tex.adel
    private Boolean isURChecked; //Upr. Remont
    private Boolean isUEChecked; //Upr .ekonomist
    private Boolean isUFChecked; //Upr. Finansist
    private Boolean isUHChecked; //Upr. Hoks
    private String OTDepoIzoh; //O'vt depo
    private String OTIzoh; //O'vt tex.adel
    private String OEIzoh; //O'vt Ekonomist
    private String UTIzoh; //Upr. tex.adel
    private String URIzoh; //Upr. Remont
    private String UEIzoh; //Upr .ekonomist
    private String UFIzoh; //Upr. Finansist
    private String UHIzoh; //Upr. Hoks

}